package com.gextekjm.controlador;

import com.gextekjm.dto.AuthResponse;
import com.gextekjm.dto.LoginRequest;
import com.gextekjm.dto.RegistroRequest;
import com.gextekjm.servicio.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para registrar usuarios y validar el inicio de sesión.
 * Todas las respuestas se envían en JSON con codificación UTF-8.
 */
@RestController
@RequestMapping(value = "/api/auth", produces = "application/json;charset=UTF-8")
public class AuthController {

    // Servicio con la lógica de negocio (registro y autenticación)
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/registro
     * Registra un usuario nuevo. Devuelve 201 si se creó y 409 si ya existe.
     */
    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> registrar(
            @Valid @RequestBody RegistroRequest solicitud) {

        try {
            // El servicio cifra la contraseña y guarda el usuario
            authService.registrar(solicitud.getUsername(), solicitud.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Usuario registrado correctamente.", true));

        } catch (IllegalArgumentException e) {
            // Usuario duplicado
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new AuthResponse(e.getMessage(), false));
        }
    }

    /**
     * POST /api/auth/login
     * Valida usuario y contraseña. Devuelve 200 si son correctos y 401 si no.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> iniciarSesion(
            @Valid @RequestBody LoginRequest solicitud) {

        // El servicio compara la contraseña con el hash guardado
        boolean autenticado = authService.autenticar(
            solicitud.getUsername(), solicitud.getPassword());

        if (autenticado) {
            return ResponseEntity.ok(
                new AuthResponse("Autenticación satisfactoria", true));
        }

        // Credenciales incorrectas: 401 No autorizado
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new AuthResponse("Error en la autenticación", false));
    }
}