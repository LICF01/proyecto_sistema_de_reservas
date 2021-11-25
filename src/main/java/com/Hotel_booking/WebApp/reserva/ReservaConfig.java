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
                    //clienteService.get(3L),
                    habitacionService.getHabitacionbyID(1L),
                    LocalDate.parse("2021-03-20"),
                    LocalDate.parse("2021-03-28"),
                    2,
                    0,
                    500000
            );
            Reserva reserva2 = new Reserva(
                    //clienteService.get(3L),
                    habitacionService.getHabitacionbyID(1L),
                    LocalDate.parse("2021-03-17"),
                    LocalDate.parse("2021-03-19"),
                    3,
                    1,
                    650000
            );
            reservaRepository.saveAll(Arrays.asList(reserva1, reserva2));

        };
    }

}