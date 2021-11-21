package com.Hotel_booking.WebApp.cliente;

import com.Hotel_booking.WebApp.Genero;
import com.Hotel_booking.WebApp.persona.Persona;
import com.Hotel_booking.WebApp.reserva.Reserva;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "sexo")
    private Genero sexo;

    @Column(name = "idioma", columnDefinition = "TEXT")
    private String idioma;

    @Column(name = "nacionalidad", columnDefinition = "TEXT")
    private String nacionalidad;

    @Column(name = "ciudad", columnDefinition = "TEXT")
    private String ciudad;

    @Column(name = "pais", columnDefinition = "TEXT")
    private String pais;

    @Column(name = "nro_ruc", columnDefinition = "TEXT")
    private String nroRuc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
    private List<Reserva> reservas;



    public Cliente(String nombre,
                   String apellido,
                   String mail,
                   String telefono,
                   Date fechaNac,
                   String tipoDocumento,
                   String nroDocumento,
                   Genero sexo,
                   String idioma,
                   String nacionalidad,
                   String ciudad,
                   String pais,
                   String nroRuc) {
        super(nombre,
                apellido,
                mail,
                telefono,
                fechaNac,
                tipoDocumento,
                nroDocumento);
        this.sexo = sexo;
        this.idioma = idioma;
        this.nacionalidad = nacionalidad;
        this.ciudad = ciudad;
        this.pais = pais;
        this.nroRuc = nroRuc;
    }
}
