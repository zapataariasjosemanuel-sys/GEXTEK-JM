package com.gextekjm.servicio;

import com.gextekjm.modelo.Producto;
import com.gextekjm.repositorio.ProductoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de productos (capa de lógica de negocio).
 * <p>
 * Aquí viven las reglas del módulo, por ejemplo que el código del
 * producto no se repita. El controlador solo se encarga de la web.
 */
@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    /**
     * Inyección de dependencias por constructor.
     *
     * @param repositorio repositorio de productos
     */
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Lista los productos, filtrando por texto si se indica.
     *
     * @param texto texto de búsqueda (puede ser nulo o vacío)
     * @return lista de productos, los más recientes primero
     */
    @Transactional(readOnly = true)
    public List<Producto> listar(String texto) {
        if (texto == null || texto.isBlank()) {
            return repositorio.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return repositorio.buscar(texto.trim());
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto
     * @return el producto, si existe
     */
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    /**
     * Crea o actualiza un producto, verificando que el código no esté
     * repetido en otro registro.
     *
     * @param producto producto a guardar
     * @return el producto guardado
     * @throws CodigoDuplicadoException si el código pertenece a otro producto
     */
    @Transactional
    public Producto guardar(Producto producto) {
        producto.setCodigo(producto.getCodigo().trim());
        producto.setNombre(producto.getNombre().trim());
        if (producto.getCategoria() != null) {
            producto.setCategoria(producto.getCategoria().trim());
        }

        repositorio.findByCodigoIgnoreCase(producto.getCodigo())
                .filter(existente -> !existente.getId().equals(producto.getId()))
                .ifPresent(existente -> {
                    throw new CodigoDuplicadoException(producto.getCodigo());
                });

        return repositorio.save(producto);
    }

    /**
     * Elimina un producto si existe.
     *
     * @param id identificador del producto
     */
    @Transactional
    public void eliminar(Integer id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
        }
    }
}
