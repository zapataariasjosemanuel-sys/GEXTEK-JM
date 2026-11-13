package com.gextekjm.repositorio;

import com.gextekjm.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de productos (capa de acceso a datos).
 * <p>
 * Al extender {@link JpaRepository}, Spring Data genera automáticamente
 * las operaciones CRUD (save, findById, deleteById, findAll...).
 */
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Busca un producto por código sin distinguir mayúsculas.
     *
     * @param codigo código a consultar
     * @return el producto, si existe
     */
    Optional<Producto> findByCodigoIgnoreCase(String codigo);

    /**
     * Busca productos cuyo código, nombre o categoría contengan el texto.
     *
     * @param texto texto a buscar (no debe ser nulo)
     * @return coincidencias, de la más reciente a la más antigua
     */
    @Query("SELECT p FROM Producto p "
            + "WHERE LOWER(p.codigo) LIKE LOWER(CONCAT('%', :texto, '%')) "
            + "OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')) "
            + "OR LOWER(COALESCE(p.categoria, '')) LIKE LOWER(CONCAT('%', :texto, '%')) "
            + "ORDER BY p.id DESC")
    List<Producto> buscar(@Param("texto") String texto);
}
