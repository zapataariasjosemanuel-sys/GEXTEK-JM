package com.gextekjm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación GEXTEK JM.
 * <p>
 * {@code @SpringBootApplication} activa la configuración automática,
 * el escaneo de componentes (controladores, servicios, repositorios)
 * y la configuración de Spring.
 */
@SpringBootApplication
public class GextekJmApplication {

    /**
     * Punto de entrada: arranca el servidor embebido (puerto 8081).
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(GextekJmApplication.class, args);
    }
}
