package com.Hotel_booking.WebApp.usuario;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class UsuarioConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UsuarioConfig(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    CommandLineRunner usuarioConfigRunner(UsuarioRepository usuarioRepository) {
        return args -> {
        Usuario lucas = new Usuario(
                "Lucas",
                "Cubilla",
                "usuario1@gmail.com",
                "09213412",
                new SimpleDateFormat("dd/MM/yyyy").parse("19/01/1994"),
                "CI",
                "1234523",
                bCryptPasswordEncoder.encode("user1"),
//                LocalDate.now(),
//                LocalDate.now(),
//                LocalDate.now(),
                UserRole.ADMIN
        );
            Usuario ivan = new Usuario(
                    "Ivan",
                    "Cubilla",
                    "usuario2@gmail.com",
                    "03412231",
                    new SimpleDateFormat("dd/MM/yyyy").parse("10/04/1990"),
                    "CI",
                    "3123523",
                    bCryptPasswordEncoder.encode("user2"),
//                    LocalDate.now(),
//                    LocalDate.now(),
//                    LocalDate.now(),
                    UserRole.USER
            );

        usuarioRepository.saveAll(
                List.of(lucas, ivan)
        );

        };
    }

}
