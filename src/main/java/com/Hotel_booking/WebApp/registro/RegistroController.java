package com.Hotel_booking.WebApp.registro;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/registro")
public class RegistroController {

    private final RegistroService registroService;

    @PostMapping
    public ResponseEntity<Object> register(@RequestBody RegistroRequest request) {
        return registroService.register(request);
    }

    // El servicio recibe un token a confirmar
    @GetMapping(path = "confirm")
    public ResponseEntity<Object> confirm(@RequestParam("token") String token) {
        return registroService.confirmToken(token);
    }
}
