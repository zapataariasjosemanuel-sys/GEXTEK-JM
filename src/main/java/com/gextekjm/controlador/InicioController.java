package com.gextekjm.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de la página de inicio.
 */
@Controller
public class InicioController {

    /**
     * Muestra la página de inicio (plantilla {@code index.html}).
     *
     * @return nombre de la vista
     */
    @GetMapping("/")
    public String inicio() {
        return "index";
    }
}
