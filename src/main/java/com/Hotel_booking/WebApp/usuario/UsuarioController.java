package com.Hotel_booking.WebApp.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getUsuario() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    public void registerUsuario(@RequestBody Usuario usuario) {
        usuarioService.addNewUsuario(usuario);
    }

}
