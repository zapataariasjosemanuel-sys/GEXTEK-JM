package com.gextekjm.controlador;

import com.gextekjm.modelo.Producto;
import com.gextekjm.servicio.CodigoDuplicadoException;
import com.gextekjm.servicio.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador web del módulo de productos.
 * Gestiona las vistas HTML de GEXTEK JM.
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String VISTA_LISTA = "productos/lista";
    private static final String VISTA_FORMULARIO = "productos/formulario";
    private static final String REDIRECCION_LISTA = "redirect:/productos";

    private final ProductoService servicio;

    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("productos", servicio.listar(q));
        model.addAttribute("q", q);
        return VISTA_LISTA;
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return VISTA_FORMULARIO;
    }

    @GetMapping("/{id}/editar")
    public String editar(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes flash) {

        Optional<Producto> producto = servicio.buscarPorId(id);

        if (producto.isEmpty()) {
            flash.addFlashAttribute("error", "El producto solicitado no existe.");
            return REDIRECCION_LISTA;
        }

        model.addAttribute("producto", producto.get());
        return VISTA_FORMULARIO;
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("producto") Producto producto,
            BindingResult resultado,
            RedirectAttributes flash) {

        if (resultado.hasErrors()) {
            return VISTA_FORMULARIO;
        }

        boolean esNuevo = producto.getId() == null;

        try {
            servicio.guardar(producto);
        } catch (CodigoDuplicadoException e) {
            resultado.rejectValue("codigo", "codigo.duplicado", e.getMessage());
            return VISTA_FORMULARIO;
        }

        flash.addFlashAttribute("exito", esNuevo
                ? "Producto registrado correctamente."
                : "Producto actualizado correctamente.");

        return REDIRECCION_LISTA;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(
            @PathVariable Integer id,
            RedirectAttributes flash) {

        servicio.eliminar(id);
        flash.addFlashAttribute("exito", "Producto eliminado correctamente.");
        return REDIRECCION_LISTA;
    }
}
