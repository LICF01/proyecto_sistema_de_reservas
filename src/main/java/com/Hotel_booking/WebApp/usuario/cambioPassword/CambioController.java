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
    public String forgotPassword(@RequestParam String email, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException
    {
        String response = cambioService.forgotPassword(email);

        if (!response.startsWith("Email invalido"))
        {
            response = Utility.getSiteURL(request) + "/api/forgot-password/reset-password?tokenPassword=" + response;

            sendEmail(email, response);
        }
        else
        {
            response = response + "\nEl email no pertenece a un usuario";
        }

        return response;
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String tokenPassword, @RequestParam String password)
    {
        return cambioService.resetPassword(tokenPassword,password);
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

}
