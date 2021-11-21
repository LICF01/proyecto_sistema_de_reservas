package com.Hotel_booking.WebApp.Habitacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/habitacion")
public class HabitacionController {

    private final HabitacionService habitacionService;

    @Autowired
    public HabitacionController(HabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<Habitacion> getHabitaciones() {
        return habitacionService.getHabitaciones();
    }

    @PostMapping
    public String registrarNuevaHabitacion(@Valid @RequestBody Habitacion hab) {
        return habitacionService.agregarNuevaHabitacion(hab);
    }

    @PutMapping (path = "{codHabitacion}")
    public String modificarHabitacion(
            @PathVariable( value = "codHabitacion") Long ID,
            @RequestBody Habitacion hab) {

        return habitacionService.modificarHabitacion(ID, hab);
    }

    @DeleteMapping(path = "{codHabitacion}")
    public void eliminarHabitacion(@PathVariable( value = "codHabitacion") Long ID) {

        habitacionService.eliminarHabitacion(ID);
    }


}

