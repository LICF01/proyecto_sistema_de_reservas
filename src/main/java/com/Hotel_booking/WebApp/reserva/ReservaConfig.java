
package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.Habitacion.HabitacionService;
import com.Hotel_booking.WebApp.cliente.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Configuration
public class ReservaConfig {

    @Bean
    CommandLineRunner reservaConfigRunner(ReservaRepository reservaRepository, ClienteService clienteService, HabitacionService habitacionService) {
        return args -> {
            Reserva reserva1 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(1L),
                    LocalDate.parse("2022-01-05"),
                    LocalDate.parse("2022-01-07"),
                    2,
                    0,
                    350000
            );
            Reserva reserva2 = new Reserva(
                    clienteService.get(3L),
                    habitacionService.getHabitacionbyID(2L),
                    LocalDate.parse("2022-02-20"),
                    LocalDate.parse("2022-02-25"),
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

            List<Reserva> reservaInicial = reservaRepository.findReservaConfig(reserva1.getFechaIngreso());
            if (reservaInicial.isEmpty()) {
                reservaRepository.save(reserva1);
                reservaInicial.clear();
            }

            reservaInicial = reservaRepository.findReservaConfig(reserva2.getFechaIngreso());
            if (reservaInicial.isEmpty()) {
                reservaRepository.save(reserva2);
                reservaInicial.clear();
            }

            reservaInicial = reservaRepository.findReservaConfig(reserva3.getFechaIngreso());
            if (reservaInicial.isEmpty()) {
                reservaRepository.save(reserva3);
                reservaInicial.clear();
            }

//            reservaRepository.saveAll(Arrays.asList(reserva1, reserva2, reserva3));

        };
    }

}