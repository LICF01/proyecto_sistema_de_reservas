package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> getReserva() {
        return reservaRepository.findAll();
    }

    public String agregarNuevaReserva(Reserva res) {
            //AGREGAR VALIDACIONES
            String fechaIngreso = dateFormat.format(res.getFechaIngreso());
            String fechaSalida = dateFormat.format(res.getFechaSalida());
            long codHabitacion = res.getHabitacion().getCodHabitacion();
            buscarReserva(fechaIngreso, fechaSalida, codHabitacion);

            throw new CustomErrorException(HttpStatus.OK, "Reserva correctamente agregada");
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

    public boolean buscarReserva(String fechaIngreso, String fechaSalida, long codHabitacion) {
        System.out.println(reservaRepository.findByFechaIngreso(fechaIngreso, fechaSalida, codHabitacion));
        return false;
    }

    private String mensaje() {
        throw new CustomErrorException(HttpStatus.NOT_FOUND, "Número de reserva inexistente");
    }




}
