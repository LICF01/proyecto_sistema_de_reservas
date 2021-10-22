package com.Hotel_booking.WebApp.registro;

import com.Hotel_booking.WebApp.usuario.UserRole;
import com.Hotel_booking.WebApp.usuario.Usuario;
import com.Hotel_booking.WebApp.usuario.UsuarioRepository;
import com.Hotel_booking.WebApp.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistroService {

    private final UsuarioService usuarioService;
    private final EmailValidator emailValidator;

    public String register(RegistroRequest request) {
        boolean isValidEmail =  emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("El mail es invalido");
        }

        return usuarioService.signUpUsuario(new Usuario(
                request.getName(),
                request.getLastName(),
                request.getEmail(),
                request.getTelefono(),
                request.getFechaNac(),
                request.getTipoDocumento(),
                request.getNumeroDocumento(),
                request.getPassword(),
                UserRole.USER
        ));
    }
}
