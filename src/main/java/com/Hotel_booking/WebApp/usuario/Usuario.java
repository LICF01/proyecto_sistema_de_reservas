package com.Hotel_booking.WebApp.usuario;

import com.Hotel_booking.WebApp.persona.Persona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "usuario")
@PrimaryKeyJoinColumn(name = "id")
public class Usuario extends Persona implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "usuarioID", updatable = false)
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
    private Boolean enabled = false;

    private String token;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime tokenCreationDate;

    public Usuario(String nombre,
                   String apellido,
                   String mail,
                   String telefono,
                   Date fechaNac,
                   String tipoDocumento,
                   String nroDocumento,
                   String contrasena,
                   UserRole tipoUsuario
    ) {
        super(nombre, apellido, mail, telefono, fechaNac, tipoDocumento, nroDocumento);
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(String nombre, String apellido, String telefono, String tipoDocumento, String nroDocumento) {
        super(nombre, apellido, telefono, tipoDocumento, nroDocumento);
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

    public void setTokenCreationDate(LocalDateTime now) {
        this.tokenCreationDate = now;
    }
}
