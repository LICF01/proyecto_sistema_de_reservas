package com.Hotel_booking.WebApp.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional()
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByMail(String mail);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario a " +
            "SET a.enabled = TRUE WHERE a.mail = ?1")
    int enableUsuario(String email);


    @Query("SELECT t FROM Usuario t WHERE t.token = ?1")
    Optional<Usuario> findUsuarioByToken(String tokenPassword);

    @Query("SELECT u FROM Usuario u WHERE u.mail = ?1")
    List<Usuario> findUsuarioConfig(String mail);

}
