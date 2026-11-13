package com.gextekjm.servicio;

/**
 * Se lanza cuando se intenta guardar un producto con un código que
 * ya pertenece a otro producto.
 */
public class CodigoDuplicadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * @param codigo código que ya existe
     */
    public CodigoDuplicadoException(String codigo) {
        super("Ya existe un producto con el código " + codigo + ".");
    }
}
