package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.HabitacionService;
import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionService   habitacionService;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, HabitacionService habitacionService) {
        this.reservaRepository = reservaRepository;
        this.habitacionService = habitacionService;
    }

    public List<Reserva> getReserva() {
        return reservaRepository.findAll();
    }

    public String agregarNuevaReserva(Reserva res) {

        //Calcular precio total de acuerdo al precio de la habitacion y a la cantidad de adultos y niños
        res.setPrecioTotal(calcularMontoReserva(res));

        //Agregar la fecha que se realiza la reserva
        res.setFechaReserva(LocalDate.now());

        //Comprobar que fecha de ingreso sea menor a la fecha de salida
        if(!comprobarConcurrenciaFechaInicioFin(res))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error: La fecha de ingreso debe ser menor a la fecha de salida");

        //Verificar que no se ingrese una reserva con fecha de ingreso pasada
        if(!verificarFechaInicio(res))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "No se puede agregar una reserva de una fecha pasada");

        //Buscar disponibilidad de Habitacion en fechas solicitadas y verificar que cantidad de personas coincidan con cantidad de camas disponibles en la habitacion
        Optional<Reserva> disponible = reservaRepository.findByHabitacionReserva(res.getHabitacion().getCodHabitacion(), res.getFechaIngreso(), res.getFechaSalida());
           if(disponible.isPresent()) {
               throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La habitacion no se encuentra disponible en la fecha solicitada");
                }
            else {
                if(verificarCantidadPersonasHabitacion(res)) {

                    reservaRepository.save(res);
                    throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente agregada");
                }
                else {
                    throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La habitacion no tiene disponibilidad para esa cantidad de personas");
                }

            }

    }

    @Transactional
    public String modificarReserva(Long ID, Reserva resNewInfo) {

        //Buscar nro de reserva
        Reserva res = reservaRepository.findById(ID).orElseThrow(() -> new IllegalStateException (mensaje()));

        //Comprobar que no sea posible modificar una reserva con fecha de fin pasada
        if(!verificarFechaFinModificacion(res))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "No se puede modificar una reserva de fecha pasada");

        //Comprobar que fecha de ingreso sea menor a la fecha de salida
        if(!comprobarConcurrenciaFechaInicioFin(resNewInfo))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error: La fecha de ingreso debe ser menor a la fecha de salida");

        //Comprobar que la fecha de entrada no sea una fecha pasada
        if(!verificarFechaInicio(resNewInfo))
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "No se puede agregar una fecha pasada como fecha de entrada");

        //Buscar disponibilidad de Habitacion en fechas solicitadas y verificar que cantidad de personas coincidan con cantidad de camas disponibles en la habitacion
        Optional<Reserva> disponible = reservaRepository.findByHabitacionReserva(resNewInfo.getHabitacion().getCodHabitacion(), resNewInfo.getFechaIngreso(), resNewInfo.getFechaSalida());
        if(disponible.isPresent()) {
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La habitacion no se encuentra disponible en la fecha solicitada");
        }
        else {
            if(verificarCantidadPersonasHabitacion(resNewInfo)) {


                res.setHabitacion(resNewInfo.getHabitacion());
                res.setFechaIngreso(resNewInfo.getFechaIngreso());
                res.setFechaSalida(resNewInfo.getFechaSalida());
                res.setCantidadAdultos(resNewInfo.getCantidadAdultos());
                res.setCantidadNinhos(resNewInfo.getCantidadNinhos());
                res.setPrecioTotal(calcularMontoReserva(resNewInfo));
                res.setPagoSiNo(resNewInfo.getPagoSiNo());

                reservaRepository.save(resNewInfo);
                throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente modificada");
            }
            else {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "La habitacion no tiene disponibilidad para esa cantidad de personas");
            }

        }

    }

    public void eliminarReserva(Long IDReserva) {

        //Verificar que el nro de reserva exista
        Reserva res = reservaRepository.findById(IDReserva).orElseThrow(() -> new IllegalStateException (mensaje()));

        //Verificar que no se elimine una reserva de fecha pasada
        if(verificarFechaInicio(res)) {
            reservaRepository.deleteById(IDReserva);
            throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente eliminada");
        } else {
            throw new CustomErrorException(HttpStatus.OK, "No se puede eliminar una reserva con fecha de inicio pasada");
        }
    }

    private boolean verificarFechaInicio(Reserva res) {
        return res.getFechaIngreso().compareTo(LocalDate.now()) >= 0;
    }

    private boolean verificarFechaFinModificacion(Reserva res) {
        return res.getFechaSalida().compareTo(LocalDate.now()) >= 0;
    }

    private boolean comprobarConcurrenciaFechaInicioFin(Reserva res) {
        return res.getFechaIngreso().compareTo(res.getFechaSalida()) < 0;
    }

    private Integer calcularMontoReserva(Reserva res) {

        int montoAdultos = res.getCantidadAdultos() * habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getPrecioAdulto();

        int montoNinhos = res.getCantidadNinhos() * habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getPrecioNinho();

        int montoTotal = montoAdultos + montoNinhos;

        if (montoTotal < habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getPrecioMinimo())
            montoTotal = habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getPrecioMinimo();

        return montoTotal;
    }

    private boolean verificarCantidadPersonasHabitacion(Reserva res) {
        int cantidadPersonasReserva;
        int cantidadPersonasHabitacion;

        if (habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getCamaExtraSN())
            cantidadPersonasHabitacion = habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getCantidadPersonas() + 1;
        else
            cantidadPersonasHabitacion = habitacionService.getHabitacionbyID(res.getHabitacion().getCodHabitacion()).getCantidadPersonas();

        cantidadPersonasReserva = res.getCantidadAdultos() + res.getCantidadNinhos();

        return (cantidadPersonasHabitacion >= cantidadPersonasReserva);
    }


    private String mensaje() {
        throw new CustomErrorException(HttpStatus.NOT_FOUND, "Número de reserva inexistente");
    }




}
