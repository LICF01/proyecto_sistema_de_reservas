package com.Hotel_booking.WebApp.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository <Reserva,Long>{

    //@Query("SELECT u FROM Reserva u WHERE u.habitacion =?1 and u.fechaIngreso between ?2 and ?3 and u.fechaSalida between ?2 and ?3")
    //Optional<Reserva> findByHabiatacionFechaIngresoFechaSalida(long codHabitacion, String fechaIngreso, String fechaSalida);

    @Query(value = "select * from reserva where habitacion_id = 1 and fecha_ingreso between '2021/03/15' and '2021/03/17' and fecha_salida between '2021/03/15' and '2021/03/17'",
            nativeQuery = true)
    Optional<Reserva> findByHabiatacionFechaIngresoFechaSalida(long codHabitacion, String fechaIngreso, String fechaSalida);



}
