package com.Hotel_booking.WebApp.pago;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/pago")
public class PagoController {

    private final PagoService pagoService;

    @Autowired
    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<Pago> getPagos() {
        return pagoService.getPagos();
    }

    @PostMapping
    public ResponseEntity<Object> registrarNuevoPago(@Valid @RequestBody Pago pag) {
        return pagoService.agregarNuevoPago(pag);
    }

    @PutMapping (path = "{codPago}")
    public Object modificarPago(
            @PathVariable( value = "codPago") Long ID,
            @RequestBody Pago pago) {

        return pagoService.modificarPago(ID, pago);
    }

    @DeleteMapping(path = "{codPago}")
    public void eliminarPago(@PathVariable( value = "codPago") Long ID) {

        pagoService.eliminarPago(ID);
    }

}
