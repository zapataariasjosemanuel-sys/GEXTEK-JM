package com.gextekjm.controlador;

import com.gextekjm.modelo.Producto;
import com.gextekjm.servicio.CodigoDuplicadoException;
import com.gextekjm.servicio.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final ProductoService servicio;

    public ProductoRestController(ProductoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> listar(
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(servicio.listar(q));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return servicio.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Producto no encontrado.")));
    }

    @PostMapping
    public ResponseEntity<?> registrar(
            @Valid @RequestBody Producto producto) {

        producto.setId(null);

        try {
            Producto guardado = servicio.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (CodigoDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody Producto producto) {

        if (servicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Producto no encontrado."));
        }

        producto.setId(id);

        try {
            Producto actualizado = servicio.guardar(producto);
            return ResponseEntity.ok(actualizado);
        } catch (CodigoDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {

        if (servicio.buscarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", "Producto no encontrado."));
        }

        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
