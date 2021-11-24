package com.Hotel_booking.WebApp.reserva;

import com.Hotel_booking.WebApp.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository <Reserva,Long>{

    @Query("select u from Reserva u where u.habitacion =?1 and u.fechaIngreso between ?2 and ?3")
    Optional<Reserva> verificarDisponibilidad(long codHabitacion, String fechaIngreso, String fechaSalida);

}
