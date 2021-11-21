package com.Hotel_booking.WebApp.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository <Reserva,Long>{

}
