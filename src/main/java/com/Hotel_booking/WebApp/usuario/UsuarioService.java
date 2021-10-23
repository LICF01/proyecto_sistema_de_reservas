package com.Hotel_booking.WebApp.usuario;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.GenericValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    public static final String USER_NOT_FOUND_MSG = "User with email %s not found";

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void addNewUsuario(Usuario usuario) {
        Optional<Usuario> usuarioByEmail = usuarioRepository.findUsuarioByEmail(usuario.getMail());
        if(usuarioByEmail.isPresent()) {
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
        boolean existeUsuario =  usuarioRepository.findUsuarioByEmail(usuario.getMail())
                .isPresent();

        // Validacion: mail existente
        if(existeUsuario) {
            throw new IllegalStateException("El mail ingresado ya esta siendo utilizado");
        }

        // Validación: longitud de contraseña minima
        if(usuario.getPassword().length() < 6) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La contraseña debe ser de al menos 6 caracteres");
        }


        LocalDate fechaNac = usuario.getFechaNac().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Validación: Mayor de edad
            int edad = Period.between(fechaNac, LocalDate.now()).getYears();
            if (!(edad > 18)) {
                throw  new CustomErrorException(HttpStatus.UNAUTHORIZED, "Debe ser mayor de edad");
            }


        String contrasenaEncryptada =  bCryptPasswordEncoder.encode(usuario.getPassword());

        usuario.setContrasena(contrasenaEncryptada);

        usuarioRepository.save(usuario);

        // TODO: Enviar Token de confirmacion

        throw new CustomErrorException(HttpStatus.OK, "Usuario registrado");
    }
}
