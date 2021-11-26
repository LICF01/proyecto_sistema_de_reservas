package com.Hotel_booking.WebApp.usuario.cambioPassword;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import com.Hotel_booking.WebApp.usuario.Usuario;
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
    private CambioRepository cambioRepository;

    public String forgotPassword(String email)
    {
        //Busca si el email esta asociado a un usuario
        Optional<Usuario> userOptional = Optional
                .ofNullable(cambioRepository.findByEmail(email));

        //En caso de que no se encuentre presente retornara "Email invalido"
        if(!userOptional.isPresent())
        {
            return "Email invalido";
        }

        Usuario usuario = userOptional.get();

        // A "usuario" se le cargara el valor del token de acuerdo a lo que devuelva el método "generateToken"
        usuario.setTokenPassword(generateToken());

        //A la fecha de cambio de contraseña se le asigna la fecha en el que suceda dicha acción
        usuario.setFechaCambioContrasena(LocalDate.now());

        usuario = cambioRepository.save(usuario);

        //Retorna el token para utilizar en el cambio de contraseña
        return usuario.getTokenPassword();
    }

    //Se encarga de generar el Token de forma pseudo criptográfica
    private String generateToken()
    {
        String token = UUID.randomUUID().toString();

        return token;
    }

    public String resetPassword(String tokenPassword, String password) {
        Optional<Usuario> userOptional = Optional
                .ofNullable(cambioRepository.findUsuarioByToken(tokenPassword));

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

        //Se encripta la nueva contraseña

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        user.setContrasena(encodedPassword);

        user.setTokenPassword(null);

        cambioRepository.save(user);

        return "Se ha actualizado su contraseña";
    }

}
