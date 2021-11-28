package com.Hotel_booking.WebApp.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> getTodasReservas() {
        return reservaService.getTodasReservas();
    }

    @GetMapping(path = "/habitacion/{codHabitacion}")
    public List<Reserva> getReservaHabitacion(@PathVariable( value = "codHabitacion") Long ID) {
        return reservaService.getReservaHabitacion(ID);
    }

    @PostMapping
    public String registrarNuevaReserva(@Valid @RequestBody Reserva res) {
        return reservaService.agregarNuevaReserva(res);
    }

    @PutMapping (path = "{codReserva}")
    public String modificarReserva(
            @PathVariable( value = "codReserva") Long ID,
            @RequestBody Reserva res) {

        return reservaService.modificarReserva(ID, res);
    }

    @DeleteMapping(path = "{codReserva}")
    public void eliminarReserva(@PathVariable( value = "codReserva") Long ID) {
        reservaService.eliminarReserva(ID);
    }


}