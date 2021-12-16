package com.Hotel_booking.WebApp.Habitacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository <Habitacion,Long>{

    @Query("SELECT h FROM Habitacion h WHERE h.nombre = ?1")
    List<Habitacion> findHabitacionConfig(String nombre);

}
