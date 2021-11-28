package com.Hotel_booking.WebApp.Habitacion;


//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Positive;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor


@Entity
@Table (name = "Habitacion")
public class Habitacion {

    @Id
    @GeneratedValue (
            strategy = GenerationType.AUTO
    )
    @Column(name = "Habitacion_ID")
    private long codHabitacion;

    @Column(name = "Nombre_Habitacion")
    @NotBlank(message = "Debe ingresar el nombre de la habitación")
    private String nombre;

    @Column(name = "Cantidad_Personas")
    @Min(value = 1, message = "Debe ingresar un número mayor a 0")
    private int cantidadPersonas;

    @Column(name = "Tipo_Habitacion")
    @Enumerated (value = EnumType.STRING)
    private TipoHabitacion tipoHabitacion;


    @Column(name = "TV_SiNo")
    @NotNull(message = "Debes seleccionar una opción de TV")
    private Boolean TVHabitacion;


    @Column(name = "Cocina_SiNo")
    @NotNull (message = "Debes seleccionar una opción de cocina")
    private Boolean cocinaSN;


    @Column(name = "Cama_Extra_SiNo")
    @NotNull (message = "Debes seleccionar una opción de cama extra")
    private Boolean camaExtraSN;

    @Positive
    @Column(name = "Precio_Adulto")
    private Integer precioAdulto;

    @Positive
    @Column(name = "Precio_Ninho")
    private Integer precioNinho;

    @Positive
    @Column(name = "Precio_Base")
    private Integer precioMinimo;

    public Habitacion(String nombre, Integer cantidadPersonas, TipoHabitacion tipoHabitacion, Boolean TVHabitacion, Boolean cocinaSN, Boolean camaExtraSN, Integer precioAdulto, Integer precioNinho, Integer precioMinimo) {
        this.nombre = nombre;
        this.cantidadPersonas = cantidadPersonas;
        this.tipoHabitacion = tipoHabitacion;
        this.TVHabitacion = TVHabitacion;
        this.cocinaSN = cocinaSN;
        this.camaExtraSN = camaExtraSN;
        this.precioAdulto = precioAdulto;
        this.precioNinho = precioNinho;
        this.precioMinimo = precioMinimo;
    }
}
