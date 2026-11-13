package com.gextekjm.controlador;

import com.gextekjm.modelo.Producto;
import com.gextekjm.servicio.CodigoDuplicadoException;
import com.gextekjm.servicio.ProductoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador web del módulo de productos (Spring MVC).
 * <p>
 * Rutas:
 * <ul>
 *   <li>GET  /productos              lista y búsqueda</li>
 *   <li>GET  /productos/nuevo        formulario vacío</li>
 *   <li>GET  /productos/{id}/editar  formulario con datos</li>
 *   <li>POST /productos/guardar      crea o actualiza</li>
 *   <li>POST /productos/{id}/eliminar elimina</li>
 * </ul>
 * Después de guardar o eliminar se redirige a la lista (patrón
 * Post/Redirect/Get) para evitar reenvíos del formulario.
 */
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private static final String VISTA_LISTA = "productos/lista";
    private static final String VISTA_FORMULARIO = "productos/formulario";
    private static final String REDIRECCION_LISTA = "redirect:/productos";

    private final ProductoService servicio;

    /**
     * @param servicio servicio con la lógica de productos
     */
    public ProductoController(ProductoService servicio) {
        this.servicio = servicio;
    }

    /**
     * Muestra la lista de productos, con búsqueda opcional.
     *
     * @param q     texto de búsqueda (opcional)
     * @param model modelo de la vista
     * @return vista de la lista
     */
    @GetMapping
    public String listar(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("productos", servicio.listar(q));
        model.addAttribute("q", q);
        return VISTA_LISTA;
    }

    /**
     * Muestra el formulario para registrar un producto nuevo.
     *
     * @param model modelo de la vista
     * @return vista del formulario
     */
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return VISTA_FORMULARIO;
    }

    /**
     * Muestra el formulario con los datos de un producto existente.
     *
     * @param id    identificador del producto
     * @param model modelo de la vista
     * @param flash mensajes que sobreviven a la redirección
     * @return vista del formulario, o la lista si el producto no existe
     */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes flash) {
        Optional<Producto> producto = servicio.buscarPorId(id);
        if (producto.isEmpty()) {
            flash.addFlashAttribute("error", "El producto solicitado no existe.");
            return REDIRECCION_LISTA;
        }
        model.addAttribute("producto", producto.get());
        return VISTA_FORMULARIO;
    }

    /**
     * Recibe el formulario (POST), valida y guarda el producto.
     *
     * @param producto  datos del formulario
     * @param resultado resultado de la validación
     * @param flash     mensajes que sobreviven a la redirección
     * @return la lista si se guardó, o el formulario si hay errores
     */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
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

    /**
     * Elimina un producto (POST, porque modifica datos).
     *
     * @param id    identificador del producto
     * @param flash mensajes que sobreviven a la redirección
     * @return redirección a la lista
     */
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id, RedirectAttributes flash) {
        servicio.eliminar(id);
        flash.addFlashAttribute("exito", "Producto eliminado correctamente.");
        return REDIRECCION_LISTA;
    }
}
