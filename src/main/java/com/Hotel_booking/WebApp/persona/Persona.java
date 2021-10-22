package com.Hotel_booking.WebApp.persona;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persona", uniqueConstraints = {@UniqueConstraint(name = "persona_mail_unique", columnNames = "mail")})
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "Nombre", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Debe ingresar el nombre")
    private String nombre;

    @Column(name = "Apellido", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "El apellido no puede dejarse en blanco")
    private String apellido;

    @Column(name = "Mail", nullable = false, columnDefinition = "TEXT")
    @Email(message = "El mail debe ser válido")
    private String mail;

    @Column(name = "Telefono", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Se debe ingresar un número de contacto")
    private String telefono;

    @Column(name = "Nacimiento")
    @NotNull(message = "Fecha de nacimiento debe ser ingresada")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date fechaNac;

    @Column(name = "Tipo_Documento", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Debe ingresarse el tipo de documento")
    private String tipoDocumento;

    @Column(name = "Nro_Documento", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Debe ingresarse el numero de documento")
    private String nroDocumento;

    public Persona(String nombre, String apellido, String mail, String telefono, Date fechaNac, String tipoDocumento, String nroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.telefono = telefono;
        this.fechaNac = fechaNac;
        this.tipoDocumento = tipoDocumento;
        this.nroDocumento = nroDocumento;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNac=" + fechaNac +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", nroDocumento='" + nroDocumento + '\'' +
                '}';
    }
}
