package com.gextekjm.servicio;

import com.gextekjm.modelo.Usuario;
import com.gextekjm.repositorio.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio encargado del registro y autenticación de usuarios.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    // Cifrador BCrypt: la contraseña nunca se guarda en texto plano
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Registra un usuario nuevo con la contraseña cifrada.
     * Lanza IllegalArgumentException si el nombre de usuario ya existe.
     */
    @Transactional
    public Usuario registrar(String username, String password) {

        // Verifica que el nombre de usuario no esté repetido
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                "El nombre de usuario ya está registrado.");
        }

        // Crea la entidad y guarda el hash de la contraseña
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPasswordHash(passwordEncoder.encode(password));

        return usuarioRepository.save(usuario);
    }

    /**
     * Comprueba las credenciales. Devuelve true solo si el usuario existe
     * y la contraseña coincide con el hash guardado.
     */
    public boolean autenticar(String username, String password) {

        return usuarioRepository.findByUsername(username)
            .map(usuario -> passwordEncoder.matches(
                password, usuario.getPasswordHash()))
            .orElse(false);
    }
}