package com.Hotel_booking.WebApp.registro;

import com.Hotel_booking.WebApp.usuario.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistroRequest {
    private final String name;
    private final String lastName;
    private final String email;
    private final String password;
    private final String telefono;
    private final Date fechaNac;
    private final String tipoDocumento;
    private final String numeroDocumento;
    private final UserRole tipoUsuario;
}
