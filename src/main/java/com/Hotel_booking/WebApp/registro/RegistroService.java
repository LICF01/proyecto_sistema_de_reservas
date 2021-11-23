package com.Hotel_booking.WebApp.registro;

import com.Hotel_booking.WebApp.registro.token.ConfirmationToken;
import com.Hotel_booking.WebApp.registro.token.ConfirmationTokenService;
import com.Hotel_booking.WebApp.usuario.UserRole;
import com.Hotel_booking.WebApp.usuario.Usuario;
import com.Hotel_booking.WebApp.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistroService {

    private final UsuarioService usuarioService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistroRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("El mail es invalido");
        }

        String token = usuarioService.signUpUsuario(new Usuario(
                        request.getName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getTelefono(),
                        request.getFechaNac(),
                        request.getTipoDocumento(),
                        request.getNumeroDocumento(),
                        request.getPassword(),
                        UserRole.USER
                )
        );

        return token;
    }


    @Transactional
    public String confirmToken (String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("El token no se encuentra"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("El correo ya ha sido confirmado");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAT();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("El token ha expirado");
        }

        confirmationTokenService.setConfirmedAt(token);
        usuarioService.enableUsuario(
                confirmationToken.getUsuario().getMail());
        return "confirmado";
    }
}
