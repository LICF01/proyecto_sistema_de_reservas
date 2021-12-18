package com.Hotel_booking.WebApp.pago;

import com.Hotel_booking.WebApp.reserva.Reserva;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor

@Entity
@Table (name = "Pago")
public class Pago {

    @Id
    @GeneratedValue (
            strategy = GenerationType.AUTO
    )
    @Column(name = "Pago_ID")
    private long codPago;

    @Column(name = "Fecha_Pago")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaPago;

    @Positive
    @Column(name = "Monto_Pago")
    private Integer montoPago;

    @Column(name = "Metodo_Pago")
    @Enumerated (value = EnumType.STRING)
    private MetodoPago metodoPago;

    @Column(name = "Estado_Pago")
    @Enumerated (value = EnumType.STRING)
    private EstadoPago estado;

    @ManyToOne()
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Pago(Integer montoPago, MetodoPago metodoPago, EstadoPago estado, Reserva reserva) {
        this.fechaPago = LocalDate.now();
        this.montoPago = montoPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.reserva = reserva;
    }

    public Pago(EstadoPago estado) {
        this.estado = estado;
    }
}
