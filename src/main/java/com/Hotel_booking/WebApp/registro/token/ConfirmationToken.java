package com.Hotel_booking.WebApp.registro.token;

import com.Hotel_booking.WebApp.usuario.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.AnyMetaDefs;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAT;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "Id_Usuario"
    )
    private Usuario usuario;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAT, Usuario usuario) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAT = expiresAT;
        this.usuario = usuario;
    }
}
