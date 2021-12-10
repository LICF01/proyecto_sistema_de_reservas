package com.Hotel_booking.WebApp.pago;

import com.Hotel_booking.WebApp.Utility.ResponseHandler;
import com.Hotel_booking.WebApp.exceptions.CustomErrorException;
import com.Hotel_booking.WebApp.reserva.Reserva;
import com.Hotel_booking.WebApp.reserva.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class PagoService {

    private final PagoRespository pagoRespository;
    private final ReservaRepository reservaRepository;

    @Autowired
    public PagoService(PagoRespository pagoRespository, ReservaRepository reservaRepository) {
        this.pagoRespository = pagoRespository;
        this.reservaRepository = reservaRepository;
    }



    public List<Pago> getPagos() {
        return pagoRespository.findAll();
    }

    public Pago getPagoID(Long PagoID) {
        return pagoRespository.findById(PagoID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Object> agregarNuevoPago(Pago pag) {

        if(validarMontoPago(pag)){
            pag.setFechaPago(LocalDate.now());
            pag.setEstado(EstadoPago.ACTIVO);
            pagoRespository.save(pag);
            return ResponseHandler.generateResponse("Pago correctamente ingresado", HttpStatus.CREATED, pag );
        }
            throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Datos ingresados incorrectos");
    }

    @Transactional
    public Object modificarPago(Long ID, Pago newInfoPago) {

        Pago pag = pagoRespository.findById(ID).orElseThrow(() -> new IllegalStateException (mensaje()));

        //Modificar Estado de Pago
        if(newInfoPago.getEstado().equals(EstadoPago.INACTIVO) || newInfoPago.getEstado().equals(EstadoPago.ACTIVO)) {
                pag.setEstado(newInfoPago.getEstado());
                pagoRespository.save(pag);
                return ResponseHandler.generateResponse("Pago correctamente modificado", HttpStatus.OK, pag );
            }

        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error: no se puede modificar el pago");
    }

    public void eliminarPago(Long IDPago) {

        //Buscar Nro de Pago
        Pago pag = pagoRespository.findById(IDPago).orElseThrow(() -> new IllegalStateException (mensaje()));
        //Verificar que el pago este inactivo
        if(pag.getEstado().equals(EstadoPago.INACTIVO)) {
            pagoRespository.deleteById(IDPago);
            throw new CustomErrorException(HttpStatus.OK, "Pago correctamente eliminado");
        }
        throw new CustomErrorException(HttpStatus.BAD_REQUEST, "No se puede eliminar un pago activo");
    }

    public boolean validarMontoPago(Pago pag) {
        int pagoTotal = 0;
        //Buscar reserva
        Reserva res = reservaRepository.findByReservaID(pag.getReserva().getCodReserva())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //Traer lista de pagos anteriores
        List <Pago> pagos = pagoRespository.findAllByReservaID(pag.getReserva().getCodReserva());

        //Iterar sobre la lista de pagos para acumular en monto total de pagos anteriores
        for (Pago pago : pagos) {
            pagoTotal += pago.getMontoPago();
        }

            //Validar que la suma total de pagos no exceda el monto total de la reserva
            if (res.getPrecioTotal() >= pagoTotal + pag.getMontoPago()) {
                return true;
            } else {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Error: Monto de pago ingresado es mayor al monto total de reserva");
            }

    }

    private String mensaje() {
        throw new CustomErrorException(HttpStatus.NOT_FOUND, "Número de pago inexistente");
    }
}
