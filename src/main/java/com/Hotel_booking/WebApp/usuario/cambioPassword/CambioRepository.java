package com.Hotel_booking.WebApp.usuario.cambioPassword;

import com.Hotel_booking.WebApp.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CambioRepository extends JpaRepository<Usuario,Long>
{
    @Query("SELECT e FROM Usuario e WHERE e.mail = ?1")
    public Usuario findByEmail(String email);

    @Query("SELECT t FROM Usuario t WHERE t.tokenPassword = ?1")
    public Usuario findUsuarioByToken(String tokenPassword);
}
