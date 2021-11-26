package com.Hotel_booking.WebApp.usuario.cambioPassword;

import com.Hotel_booking.WebApp.registro.token.ConfirmationTokenRepository;
import com.Hotel_booking.WebApp.usuario.Usuario;
import com.Hotel_booking.WebApp.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CambioService
{
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public String forgotPassword(String email)
    {
        Optional<Usuario> userOptional = Optional
                .ofNullable(usuarioRepository.findByEmail(email));

        if(!userOptional.isPresent())
        {
            return "Email invalido";
        }

        Usuario usuario = userOptional.get();
        usuario.setToken(generateToken());
        usuario.setFechaCambioContrasena(LocalDate.now());

        usuario = usuarioRepository.save(usuario);

        return usuario.getToken();
    }

    private String generateToken()
    {
        StringBuilder token = new StringBuilder();
        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public String resetPassword(String token, String password)
    {
        Optional<Usuario> userOptional = Optional
                .ofNullable(usuarioRepository.findyByToken(token));

        if (!userOptional.isPresent())
        {
            return "Token inválido";
        }

        LocalDate tokenCreationDate = userOptional.get().getFechaCambioContrasena();

        if (isTokenExpired(tokenCreationDate))
        {
            return "El tiempo para actualizar el password ha expirado";
        }

        Usuario user = userOptional.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        user.setContrasena(encodedPassword);

        user.setTokenPassword(null);
        user.setFechaCambioContrasena(null);

        usuarioRepository.save(user);

        return "Se ha actualizado su contraseña";
    }

    private boolean isTokenExpired(final LocalDate tokenCreationDate)
    {
        LocalDate now = LocalDate.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

}
