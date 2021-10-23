package com.Hotel_booking.WebApp.cliente;

import com.Hotel_booking.WebApp.Genero;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class ClienteConfig {

    @Bean
    CommandLineRunner clienteConfigRunner(ClienteRepository clienteRepository) {
        return args -> {
            Cliente Alex = new Cliente(
                    "Alexandra",
                    "Nolastname",
                    "mail@mail.com",
                    "1234123512",
                    new SimpleDateFormat("dd/MM/yyyy").parse("20/04/1996"),
                    "CI",
                    "31232353",
                    Genero.Femenino,
                    "Ingles",
                    "Canadiense", "Ottawa",
                    "Canada",
                    "3"
            );

            clienteRepository.saveAll(
                    List.of(Alex)
            );

        };
    }
}
