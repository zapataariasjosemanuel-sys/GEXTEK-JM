function CamisaCard({ producto, onVer, onEditar, onEliminar }) {
    return (
        <article className="producto-card">
            <div className="producto-icono">📦</div>

            <div className="producto-info">
                <h3>{producto.nombre}</h3>
                <p><strong>Código:</strong> {producto.codigo}</p>
                <p><strong>Categoría:</strong> {producto.categoria}</p>
                <p><strong>Existencias:</strong> {producto.cantidad}</p>
                <p className="producto-precio">
                    ${Number(producto.precio).toLocaleString("es-CO")}
                </p>
            </div>

            <div className="producto-acciones">
                <button onClick={() => onVer(producto)}>Ver</button>
                <button onClick={() => onEditar(producto)}>Editar</button>
                <button onClick={() => onEliminar(producto.id)}>Eliminar</button>
            </div>
        </article>
    );
}

export default CamisaCard;
