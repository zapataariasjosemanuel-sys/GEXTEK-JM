package com.gextekjm.repositorio;

import com.gextekjm.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para consultar y administrar usuarios.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Busca un usuario por su nombre de acceso.
    Optional<Usuario> findByUsername(String username);

    // Comprueba si el nombre de usuario ya existe.
    boolean existsByUsername(String username);
}
