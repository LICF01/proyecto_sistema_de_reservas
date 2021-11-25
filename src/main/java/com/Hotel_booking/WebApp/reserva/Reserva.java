package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.Habitacion;
import com.Hotel_booking.WebApp.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cliente_id")
//    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "Habitacion_ID", referencedColumnName = "Habitacion_ID")
    private Habitacion habitacion;

    @Column(name = "Fecha_Ingreso")
    @NotNull(message = "Ingresar fecha de ingreso")
    @JsonFormat(pattern="yyyy/MM/dd")
    private LocalDate fechaIngreso;

    @Column(name = "Fecha_Salida")
    @NotNull(message = "Ingresar fecha de salida")
    @JsonFormat(pattern="yyyy/MM/dd")
    private LocalDate fechaSalida;

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

//    public Reserva(Cliente cliente, Habitacion habitacion, Date fechaIngreso, Date fechaSalida, int cantidadAdultos, int cantidadNinhos, Integer precioTotal) {
//        this.cliente = cliente;
//        this.habitacion = habitacion;
//        this.fechaIngreso = fechaIngreso;
//        this.fechaSalida = fechaSalida;
//        this.cantidadAdultos = cantidadAdultos;
//        this.cantidadNinhos = cantidadNinhos;
//        this.precioTotal = precioTotal;
//    }


    public Reserva(Habitacion habitacion, LocalDate fechaIngreso, LocalDate fechaSalida, int cantidadAdultos, int cantidadNinhos, Integer precioTotal) {
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.cantidadAdultos = cantidadAdultos;
        this.cantidadNinhos = cantidadNinhos;
        this.precioTotal = precioTotal;
    }
}

