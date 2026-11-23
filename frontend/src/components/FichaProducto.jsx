function FichaProducto({ producto, onCerrar }) {
    if (!producto) return null;

    return (
        <div className="modal-fondo">
            <section className="ficha-producto">
                <h2>Detalle del producto</h2>

                <p><strong>ID:</strong> {producto.id}</p>
                <p><strong>Código:</strong> {producto.codigo}</p>
                <p><strong>Nombre:</strong> {producto.nombre}</p>
                <p><strong>Categoría:</strong> {producto.categoria}</p>
                <p><strong>Cantidad disponible:</strong> {producto.cantidad}</p>
                <p>
                    <strong>Precio:</strong>{" "}
                    ${Number(producto.precio).toLocaleString("es-CO")}
                </p>

                <button onClick={onCerrar}>Cerrar</button>
            </section>
        </div>
    );
}

export default FichaProducto;
