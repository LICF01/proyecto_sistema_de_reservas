
package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.HabitacionService;
import com.Hotel_booking.WebApp.cliente.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;


@Configuration
public class ReservaConfig {

    @Bean
    CommandLineRunner reservaConfigRunner(ReservaRepository reservaRepository, ClienteService clienteService, HabitacionService habitacionService) {
        return args -> {
            Reserva reserva1 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(2L),
                    LocalDate.parse("2021-11-29"),
                    LocalDate.parse("2021-11-30"),
                    2,
                    0,
                    350000
            );
            Reserva reserva2 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(2L),
                    LocalDate.parse("2021-12-20"),
                    LocalDate.parse("2021-12-25"),
                    2,
                    2,
                    550000
            );

            Reserva reserva3 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(2L),
                    LocalDate.parse("2021-11-03"),
                    LocalDate.parse("2021-11-10"),
                    5,
                    0,
                    850000
            );

            reservaRepository.saveAll(Arrays.asList(reserva1, reserva2, reserva3));

        };
    }

}