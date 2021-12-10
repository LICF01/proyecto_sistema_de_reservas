package com.Hotel_booking.WebApp.pago;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRespository extends JpaRepository<Pago,Long> {

    @Query(value = "SELECT * FROM pago WHERE reserva_id=?1 and estado_pago='ACTIVO'", nativeQuery = true)
    List<Pago> findAllByReservaID(long codReserva);

}