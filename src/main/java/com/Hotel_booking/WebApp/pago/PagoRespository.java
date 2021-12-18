package com.Hotel_booking.WebApp.pago;


import com.Hotel_booking.WebApp.reserva.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRespository extends JpaRepository<Pago,Long> {

    @Query(value = "SELECT * FROM pago WHERE reserva_id=?1 and estado_pago='ACTIVO'", nativeQuery = true)
    List<Pago> findAllByReservaIDActivos(long codReserva);

    @Query(value = "SELECT * FROM pago WHERE reserva_id=?1", nativeQuery = true)
    List<Pago> findAllPagosByReservaID(long codReserva);


}