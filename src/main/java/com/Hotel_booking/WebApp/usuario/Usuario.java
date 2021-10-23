package com.Hotel_booking.WebApp.usuario;

import com.Hotel_booking.WebApp.persona.Persona;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "usuario")
@PrimaryKeyJoinColumn(name = "id")
public class Usuario extends Persona implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @NotBlank(message = "La contraseña debe ingresarse")
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String contrasena;

    @Column(name = "cambio_password")
    private LocalDate fechaCambioContrasena;

    @Column(name = "creacion_usuario")
    private LocalDate fechaCreacionUsuario;

    @Column(name = "ultimo_ingreso")
    private LocalDate fechaUltimoIngreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private UserRole tipoUsuario;

    private Boolean locked = false;
    private Boolean enabled = true;


    public Usuario(String nombre,
                   String apellido,
                   String mail,
                   String telefono,
                   Date fechaNac,
                   String tipoDocumento,
                   String nroDocumento,
                   String contrasena,
                   UserRole tipoUsuario) {
        super(nombre, apellido, mail, telefono, fechaNac, tipoDocumento, nroDocumento);
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(tipoUsuario.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return super.getNombre();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}