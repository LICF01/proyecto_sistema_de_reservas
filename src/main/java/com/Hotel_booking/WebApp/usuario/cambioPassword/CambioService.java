package com.Hotel_booking.WebApp.usuario.cambioPassword;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import com.Hotel_booking.WebApp.usuario.Usuario;
import com.Hotel_booking.WebApp.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CambioService
{

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String forgotPassword(String email)
    {
        Optional<Usuario> userOptional = Optional
                .ofNullable(usuarioRepository.findByEmail(email));

        if(!userOptional.isPresent())
        {
            return "Email invalido";
        }

        Usuario usuario = userOptional.get();
        usuario.setTokenPassword(generateToken());
        usuario.setFechaCambioContrasena(LocalDate.now());

        usuario = usuarioRepository.save(usuario);

        return usuario.getTokenPassword();
    }

    private String generateToken()
    {
        String token = UUID.randomUUID().toString();

        return token;
    }

    public String resetPassword(String tokenPassword, String password) {
        Optional<Usuario> userOptional = Optional
                .ofNullable(usuarioRepository.findUsuarioByToken(tokenPassword));

        if (!userOptional.isPresent())
        {
            return "Token inválido";
        }


        Usuario user = userOptional.get();

        user.setContrasena(password);

        if (user.getPassword().length() < 6)
        {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La contraseña debe ser de al menos 6 caracteres");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        user.setContrasena(encodedPassword);

        user.setTokenPassword(null);
        user.setFechaCambioContrasena(null);

        usuarioRepository.save(user);

        return "Se ha actualizado su contraseña";
    }

}
