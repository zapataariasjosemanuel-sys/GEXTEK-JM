/**
 * productos.js - Lógica del módulo de productos | GEXTEK JM
 * Evidencia GA7-220501096-AA4-EV03 (componente front-end).
 *
 * Funciones: registrar, buscar y eliminar productos. Los datos se
 * guardan en el navegador (localStorage); la conexión con el servlet
 * y la base de datos corresponde a la evidencia AA2-EV02.
 */
"use strict";

/* ---------- Constantes ---------- */
const CLAVE_ALMACEN = "gextekjm.productos";
const FORMATO_PRECIO = new Intl.NumberFormat("es-CO", {
    style: "currency",
    currency: "COP",
    minimumFractionDigits: 2
});

/* ---------- Estado de la aplicación ---------- */
let productos = [];
let siguienteId = 1;

/* ---------- Referencias al DOM ---------- */
const formulario = document.getElementById("productoForm");
const tabla = document.getElementById("tablaProductos");
const mensaje = document.getElementById("mensaje");
const busqueda = document.getElementById("busqueda");

/**
 * Carga los productos guardados y calcula el siguiente ID.
 */
function cargarProductos() {
    try {
        const guardado = localStorage.getItem(CLAVE_ALMACEN);
        productos = guardado ? JSON.parse(guardado) : [];
    } catch (error) {
        productos = [];
    }
    siguienteId = productos.reduce((mayor, p) => Math.max(mayor, p.id), 0) + 1;
}

/**
 * Guarda la lista actual en el navegador.
 * Si el almacenamiento no está disponible, la lista sigue en memoria.
 */
function guardarProductos() {
    try {
        localStorage.setItem(CLAVE_ALMACEN, JSON.stringify(productos));
    } catch (error) {
        /* Sin almacenamiento: se ignora el error. */
    }
}

/**
 * Muestra un mensaje de estado al usuario.
 * @param {string} texto - Texto del mensaje.
 * @param {string} tipo - "exito" o "error".
 */
function mostrarMensaje(texto, tipo) {
    mensaje.textContent = texto;
    mensaje.className = "mensaje mensaje--" + tipo;
}

/**
 * Valida los datos de un producto nuevo.
 * @param {Object} producto - Datos leídos del formulario.
 * @returns {string} Mensaje de error, o cadena vacía si todo es correcto.
 */
function validarProducto(producto) {
    if (!producto.codigo || !producto.nombre || !producto.categoria) {
        return "Completa código, nombre y categoría.";
    }
    if (!Number.isInteger(producto.cantidad) || producto.cantidad < 0) {
        return "La cantidad debe ser un número entero mayor o igual a 0.";
    }
    if (!Number.isFinite(producto.precio) || producto.precio < 0) {
        return "El precio debe ser un número mayor o igual a 0.";
    }
    const repetido = productos.some(
        (p) => p.codigo.toLowerCase() === producto.codigo.toLowerCase()
    );
    if (repetido) {
        return "Ya existe un producto con ese código.";
    }
    return "";
}

/**
 * Crea una celda de tabla con texto seguro (sin interpretar HTML).
 * @param {string|number} valor - Contenido de la celda.
 * @returns {HTMLTableCellElement}
 */
function crearCelda(valor) {
    const celda = document.createElement("td");
    celda.textContent = valor;
    return celda;
}

/**
 * Crea la fila de la tabla para un producto, con su botón Eliminar.
 * @param {Object} producto - Producto a mostrar.
 * @returns {HTMLTableRowElement}
 */
function crearFila(producto) {
    const fila = document.createElement("tr");

    [producto.id, producto.codigo, producto.nombre, producto.categoria,
        producto.cantidad, FORMATO_PRECIO.format(producto.precio)]
        .forEach((valor) => fila.appendChild(crearCelda(valor)));

    const celdaAccion = document.createElement("td");
    const boton = document.createElement("button");
    boton.type = "button";
    boton.textContent = "Eliminar";
    boton.className = "boton boton--peligro";
    boton.addEventListener("click", () => eliminarProducto(producto.id));

    celdaAccion.appendChild(boton);
    fila.appendChild(celdaAccion);
    return fila;
}

/**
 * Dibuja la tabla aplicando el filtro de búsqueda.
 */
function mostrarProductos() {
    const filtro = busqueda.value.trim().toLowerCase();
    tabla.replaceChildren();

    productos
        .filter((p) => [p.codigo, p.nombre, p.categoria]
            .some((campo) => campo.toLowerCase().includes(filtro)))
        .forEach((p) => tabla.appendChild(crearFila(p)));
}

/**
 * Elimina un producto por su ID y actualiza la vista.
 * @param {number} id - ID del producto a eliminar.
 */
function eliminarProducto(id) {
    productos = productos.filter((p) => p.id !== id);
    guardarProductos();
    mostrarProductos();
    mostrarMensaje("Producto eliminado.", "exito");
}

/**
 * Maneja el envío del formulario: valida, guarda y actualiza la tabla.
 * @param {Event} evento - Evento submit del formulario.
 */
function registrarProducto(evento) {
    evento.preventDefault();

    const producto = {
        id: siguienteId,
        codigo: document.getElementById("codigo").value.trim(),
        nombre: document.getElementById("nombre").value.trim(),
        categoria: document.getElementById("categoria").value.trim(),
        cantidad: Number(document.getElementById("cantidad").value),
        precio: Number(document.getElementById("precio").value)
    };

    const error = validarProducto(producto);
    if (error) {
        mostrarMensaje(error, "error");
        return;
    }

    productos.push(producto);
    siguienteId += 1;
    guardarProductos();
    formulario.reset();
    mostrarMensaje("Producto registrado correctamente.", "exito");
    mostrarProductos();
}

/**
 * Punto de entrada: carga datos y conecta los eventos.
 */
function iniciar() {
    cargarProductos();
    mostrarProductos();
    formulario.addEventListener("submit", registrarProducto);
    busqueda.addEventListener("input", mostrarProductos);
}

iniciar();
