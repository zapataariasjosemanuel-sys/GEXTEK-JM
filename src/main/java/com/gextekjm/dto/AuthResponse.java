package com.gextekjm.dto;

/**
 * Respuesta que devuelve el servicio de autenticación.
 */
public class AuthResponse {

    // Texto que se muestra al cliente
    private String mensaje;
    // true si la operación fue correcta
    private boolean exitoso;

    public AuthResponse() {
    }

    public AuthResponse(String mensaje, boolean exitoso) {
        this.mensaje = mensaje;
        this.exitoso = exitoso;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public boolean isExitoso() { return exitoso; }
    public void setExitoso(boolean exitoso) { this.exitoso = exitoso; }
}