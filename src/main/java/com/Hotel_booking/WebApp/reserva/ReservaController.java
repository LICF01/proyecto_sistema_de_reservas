package com.Hotel_booking.WebApp.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> getReserva() {
        return reservaService.getReserva();
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