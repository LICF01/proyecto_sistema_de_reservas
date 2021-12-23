package com.Hotel_booking.WebApp.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsuario() {
        return usuarioService.getUsuarios();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> registerUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addNewUsuario(usuario);
    }


    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam String email) {
        String response = usuarioService.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8080/api/resetPassword?tokenPassword=" + response;
        }
        return response;
    }

    @PutMapping("/resetPassword")
    public String resetPassword(@RequestParam String tokenPassword, @RequestParam String password)
    {
        return usuarioService.resetPassword(tokenPassword, password);
    }

    @PutMapping (path = "{codUsuario}")
    public Object modificarUsuario(
            @PathVariable( value = "codUsuario") Long ID,
            @RequestBody Usuario usu) {

        return usuarioService.modificarUsuario(ID, usu);
    }

    @DeleteMapping(path = "{codUsuario}")
    public void eliminarUsuario(@PathVariable( value = "codUsuario") Long ID) {
        usuarioService.eliminarUsuario(ID);
    }

}
