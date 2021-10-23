package com.Hotel_booking.WebApp.Habitacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository <Habitacion,Long>{



}
