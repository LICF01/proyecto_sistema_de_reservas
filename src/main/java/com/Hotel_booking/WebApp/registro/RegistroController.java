package com.Hotel_booking.WebApp.registro;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/registro")
public class RegistroController {

    private RegistroService registroService;

    @PostMapping
    public String register(@RequestBody RegistroRequest request) {
        return registroService.register(request);
    }

}
