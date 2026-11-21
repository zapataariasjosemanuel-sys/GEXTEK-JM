package com.gextekjm.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Entidad que representa un producto del inventario GEXTEK JM.
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El código es obligatorio.")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String nombre;

    @Size(max = 100)
    @Column(length = 100)
    private String categoria;

    @NotNull(message = "La cantidad es obligatoria.")
    @Min(value = 0, message = "La cantidad no puede ser negativa.")
    @Column(nullable = false)
    private Integer cantidad = 0;

    @NotNull(message = "El precio es obligatorio.")
    @DecimalMin(value = "0.00")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio = BigDecimal.ZERO;

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
