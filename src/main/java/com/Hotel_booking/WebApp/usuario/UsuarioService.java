package com.Hotel_booking.WebApp.usuario;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import com.Hotel_booking.WebApp.registro.token.ConfirmationToken;
import com.Hotel_booking.WebApp.registro.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    public static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void addNewUsuario(Usuario usuario) {
        Optional<Usuario> usuarioByEmail = usuarioRepository.findUsuarioByEmail(usuario.getMail());
        if (usuarioByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUsuario(Usuario usuario) {
        boolean existeUsuario = usuarioRepository.findUsuarioByEmail(usuario.getMail())
                .isPresent();

        // Validacion: mail existente
        if (existeUsuario) {
            throw new IllegalStateException("El mail ingresado ya esta siendo utilizado");
        }

        // Validación: longitud de contraseña minima
        if (usuario.getPassword().length() < 6) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La contraseña debe ser de al menos 6 caracteres");
        }


        LocalDate fechaNac = usuario.getFechaNac().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Validación: Mayor de edad
        int edad = Period.between(fechaNac, LocalDate.now()).getYears();
        if (!(edad > 18)) {
            throw new CustomErrorException(HttpStatus.UNAUTHORIZED, "Debe ser mayor de edad");
        }


        String contrasenaEncryptada = bCryptPasswordEncoder.encode(usuario.getPassword());

        usuario.setContrasena(contrasenaEncryptada);

        usuarioRepository.save(usuario);

        // Se genera un String que sera usado como token
        String token = UUID.randomUUID().toString();

        // Se crea un nuevo token de confirmacion
        // Se pasa el string, la hora de cracion, la hora de expiracion y el usuario
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        throw new CustomErrorException(HttpStatus.OK, "Usuario registrado" + " token: " + token);

        return token;

    }
    public int enableUsuario(String email) {
        return usuarioRepository.enableUsuario(email);
    }
}
