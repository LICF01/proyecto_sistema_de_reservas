package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.Habitacion;
import com.Hotel_booking.WebApp.Habitacion.HabitacionService;
import com.Hotel_booking.WebApp.cliente.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;


@Configuration
public class ReservaConfig {

    @Bean
    CommandLineRunner reservaConfigRunner(ReservaRepository reservaRepository, ClienteService clienteService, HabitacionService habitacionService) {
        return args -> {
            Reserva reserva1 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(1L),
                    new SimpleDateFormat("dd/MM/yyyy").parse("15/03/2021"),
                    new SimpleDateFormat("dd/MM/yyyy").parse("17/03/2021"),
                    2,
                    0,
                    500000
            );

            reservaRepository.saveAll(Arrays.asList(reserva1));

        };
    }

}