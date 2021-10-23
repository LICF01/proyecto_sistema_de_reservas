package com.Hotel_booking.WebApp.Habitacion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class HabitacionConfig {

    @Bean
    CommandLineRunner habitacionConfigRunner(HabitacionRepository habitacionRepository) {
        return args -> {
            Habitacion guayabo = new Habitacion(
                    "Guayabo",
                    2,
                    TipoHabitacion.MATRIMONIAL,
                    true,
                    true,
                    false,
                    130000,
                    75000,
                    350000
            );
            Habitacion losLapachos = new Habitacion(
                    "Los Lapachos",
                    6, TipoHabitacion.SUITE,
                    true,
                    true,
                    true,
                    150000,
                    75000,
                    500000
            );

            habitacionRepository.saveAll(Arrays.asList(guayabo, losLapachos));

        };
    }

}
