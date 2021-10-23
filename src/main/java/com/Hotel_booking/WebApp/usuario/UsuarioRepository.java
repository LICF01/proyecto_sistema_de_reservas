package com.Hotel_booking.WebApp.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional()
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT s FROM Usuario s WHERE s.mail = ?1")
    Optional<Usuario> findUsuarioByEmail(String mail);

}