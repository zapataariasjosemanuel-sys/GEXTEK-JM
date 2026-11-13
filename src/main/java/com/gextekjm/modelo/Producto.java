
package com.gextekjm.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Entidad Producto: representa un registro de la tabla productos.
 * Incluye validaciones para proteger la información del inventario.
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El código es obligatorio.")
    @Size(max = 50, message = "El código admite máximo 50 caracteres.")
    @Column(nullable = false, length = 50)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 150, message = "El nombre admite máximo 150 caracteres.")
    @Column(nullable = false, length = 150)
    private String nombre;

    @Size(max = 100, message = "La categoría admite máximo 100 caracteres.")
    @Column(length = 100)
    private String categoria;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 0, message = "La cantidad no puede ser negativa.")
    @Column(nullable = false)
    private Integer cantidad = 0;

    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo.")
    @Digits(integer = 10, fraction = 2,
            message = "El precio admite hasta 2 decimales.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio = BigDecimal.ZERO;

    // Constructor vacío requerido por JPA.
    public Producto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
