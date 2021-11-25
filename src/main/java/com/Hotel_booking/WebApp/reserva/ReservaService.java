package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.HabitacionService;
import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HabitacionService habitacionService;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository, HabitacionService habitacionService) {
        this.reservaRepository = reservaRepository;
        this.habitacionService = habitacionService;
    }

    public List<Reserva> getReserva() {
        return reservaRepository.findAll();
    }

    public String agregarNuevaReserva(Reserva res) {

        res.setPrecioTotal(calcularMontoReserva(res));

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
    public String modificarReserva(Long ID,
                                      Reserva resNewInfo) {

        Reserva res = reservaRepository.findById(ID).orElseThrow(() -> new IllegalStateException (mensaje()));
        //AGREGAR VALIDACIONES

        throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente modificada");

    }

    public void eliminarReserva(Long IDReserva) {
        boolean existe = reservaRepository.existsById(IDReserva);
        if(!existe)
            mensaje();
        else {
            reservaRepository.deleteById(IDReserva);
            throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente eliminada");
        }
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
