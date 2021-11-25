package com.Hotel_booking.WebApp.usuario.cambioPassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "api/forgot-password")
public class CambioController
{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CambioService cambioService;

    @PostMapping()
    public String forgotPassword(@RequestParam String email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String response = cambioService.forgotPassword(email);

        if (!response.startsWith("Invalid"))
        {
            response = Utility.getSiteURL(request) + "/api/forgot-password/reset-password?token=" + response;

            sendEmail(email, response);
        }

        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String password)
    {
        return cambioService.resetPassword(token,password);
    }

    public void sendEmail(String email, String enlace) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        helper.setFrom("posadaLapachos@gmail.com", "Soporte Lapachos");
        helper.setTo(email);

        String subject = "Enlace para restablecimiento de contraseña";

        String contenido = "<p> Ha solicitado restablecer su contraseña </p>"
                + "<p> Haga click en el enlace de abajo para poder cambiar su contraseña: </p>"
                + "<p><a href=\"" + enlace + "\"> Cambiar mi contraseña </a></p>" + "<br>" +
                "<p>Ignora este correo si usted no ha solicitado el cambio de contraseña</p>";

        helper.setSubject(subject);

        helper.setText(contenido, true);

        mailSender.send(mensaje);
    }
    
    
    /*Verifica el token en la URL, para asegurar que solo el usuario que recibio el correo pueda cambiar la contraseña, 
    y en caso de que no encuentre mostrara el mensaje de "Invalid token"*/
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model)
    {
        Usuario usuario = cambioService.getByResetPassword(token);
        model.addAttribute("token", token);

        if (usuario == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }
    
    
    /*El token verifica para asegurarse que la solicitud sea legítimo, actualiza la nueva contraseña del usuario y muestra un mensaje */
    
    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model)
    {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        model.addAttribute("title", "Reset your password");

        Usuario usuario = cambioService.getByResetPassword(token);

        if (usuario == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else
        {
            cambioService.updatePassword(usuario, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";

    }

}
