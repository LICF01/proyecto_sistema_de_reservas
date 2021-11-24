package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.Habitacion;
import com.Hotel_booking.WebApp.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor


@Entity
@Table (name = "Reserva")
public class Reserva {

    @Id
    @GeneratedValue (
            strategy = GenerationType.AUTO
    )
    @Column(name = "Reserva_ID")
    private long codReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "Habitacion_ID", referencedColumnName = "Habitacion_ID")
    private Habitacion habitacion;

    @Column(name = "Fecha_Ingreso")
    @NotNull(message = "Ingresar fecha de ingreso")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date fechaIngreso;

    @Column(name = "Fecha_Salida")
    @NotNull(message = "Ingresar fecha de salida")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date fechaSalida;

//    @Column(name = "Cantidad_noches")
//    @Min(value = 1, message = "Debe ingresar un número mayor a 0")
//    private int cantidadNoches;

    @Column(name = "Cantidad_Adultos")
    @Min(value = 1, message = "Debe ingresar un número mayor a 0")
    private int cantidadAdultos;

    @Column(name = "Cantidad_Ninhos")
    private int cantidadNinhos;

//    @Column(name = "Fecha_Reserva")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern="dd/MM/yyyy")
//    private LocalDate fechaReserva;

    @Positive
    @Column(name = "Precio_Total")
    private Integer precioTotal;

    public Reserva(Cliente cliente, Habitacion habitacion, Date fechaIngreso, Date fechaSalida, int cantidadAdultos, int cantidadNinhos, Integer precioTotal) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.cantidadAdultos = cantidadAdultos;
        this.cantidadNinhos = cantidadNinhos;
        this.precioTotal = precioTotal;
    }
}
