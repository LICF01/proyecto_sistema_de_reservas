package com.Hotel_booking.WebApp.usuario;

import com.Hotel_booking.WebApp.email.EmailSender;
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

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    public static final String USER_NOT_FOUND_MSG = "User with email %s not found";
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 15;

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public void addNewUsuario(Usuario usuario) {
        Optional<Usuario> usuarioByEmail = usuarioRepository.findByMail(usuario.getMail());
        if (usuarioByEmail.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByMail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUsuario(Usuario usuario) {
        boolean existeUsuario = usuarioRepository.findByMail(usuario.getMail())
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


    public String forgotPassword(String email)
    {
        //Busca si el email esta asociado a un usuario
        Optional<Usuario> userOptional = usuarioRepository.findByMail(email);

        //En caso de que no se encuentre presente retornara "Email invalido"
        if(!userOptional.isPresent())
        {
            return "Invalid Email";
        }

        Usuario usuario = userOptional.get();
        // A "usuario" se le cargara el valor del token de acuerdo a lo que devuelva el método "generateToken"
        usuario.setToken(generateToken());
        //A la fecha de cambio de contraseña se le asigna la fecha en el que suceda dicha acción
        usuario.setTokenCreationDate(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);


        String link = "http://localhost:8080/api/resetPassword/confirm?token=" + usuario.getToken();
        emailSender.send(
                usuario.getMail(),
                buildEmail(usuario.getUsername(), link));

//        Retorna el token para utilizar en el cambio de contraseña
        return usuario.getToken();
    }



    public String resetPassword(String tokenPassword, String password) {
        Optional<Usuario> userOptional = usuarioRepository.findUsuarioByToken(tokenPassword);

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        Usuario usuario = userOptional.get();

        if (usuario.getPassword().length() < 6) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La contraseña debe ser de al menos 6 caracteres");
        }

        //Se encripta la nueva contraseña
        String contrasenaEncryptada = bCryptPasswordEncoder.encode(password);
        usuario.setContrasena(contrasenaEncryptada);
        usuario.setToken(null);
        usuario.setTokenCreationDate(null);

        usuarioRepository.save(usuario);

        return "Se ha actualizado su contraseña";
    }

        //Se encarga de generar el Token de forma pseudo criptográfica
        private String generateToken()
        {
            String token = UUID.randomUUID().toString();

            return token;
        }

     // Check whether the created token expired or not.
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirma tu registro</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola! " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Gracias por elegirnos. Por favor, haz click en el link de abajo para confirmar tu registro y habilitar tu cuenta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Confirmar registro</a> </p></blockquote>\n El link caducará en 15 minutos. <p>Nos vemos pronto!</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
