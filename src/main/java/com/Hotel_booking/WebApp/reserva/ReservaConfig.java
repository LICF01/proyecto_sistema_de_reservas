package com.Hotel_booking.WebApp.reserva;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;


@Configuration
public class ReservaConfig {

    @Bean
    CommandLineRunner reservaConfigRunner(ReservaRepository reservaRepository) {
        return args -> {
            Reserva reserva = new Reserva(
                    new SimpleDateFormat("dd/MM/yyyy").parse("15/03/2021"),
                    1,
                    2,
                    0,
                    500000
            );

            reservaRepository.saveAll(Arrays.asList(reserva));

        };
    }

}