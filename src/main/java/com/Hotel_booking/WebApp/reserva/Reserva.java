package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.Habitacion;
import com.Hotel_booking.WebApp.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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

    @ManyToOne()
    @JsonBackReference
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @NotNull(message = "Debe ingresar un nro de habitacion")
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

    @Column(name = "Fecha_Reserva")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate fechaReserva;

    @Column(name = "Precio_Total")
    private Integer precioTotal;

    @Column(name = "PagoSiNo")
    @NotNull(message = "Debes seleccionar si se realizo el pago")
    private Boolean pagoSiNo;

    public Reserva(Cliente cliente, Habitacion habitacion, LocalDate fechaIngreso, LocalDate fechaSalida, int cantidadAdultos, int cantidadNinhos, int precioTotal, Boolean pagoSiNo) {
        this.cliente = cliente;
        this.habitacion = habitacion;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.cantidadAdultos = cantidadAdultos;
        this.cantidadNinhos = cantidadNinhos;
        this.pagoSiNo = pagoSiNo;
        this.precioTotal = precioTotal;
        this.fechaReserva = LocalDate.now();
    }
}

