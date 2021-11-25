package com.Hotel_booking.WebApp.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository <Reserva,Long>{

    @Query(value = "select * from reserva where habitacion_id = ?1 and ?2 between fecha_ingreso and fecha_salida and ?3 between fecha_ingreso and fecha_salida", nativeQuery = true)
    Optional<Reserva> findByHabitacionReserva(long codHabitacion, LocalDate fechaIngreso, LocalDate fechaSalida);

}
