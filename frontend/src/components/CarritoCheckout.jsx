import { useState } from "react";

function CarritoCheckout({ productos = [] }) {
    const [seleccionados, setSeleccionados] = useState([]);

    const agregarProducto = (producto) => {
        setSeleccionados((actuales) => {
            const existe = actuales.find((item) => item.id === producto.id);

            if (existe) {
                return actuales.map((item) =>
                    item.id === producto.id
                        ? { ...item, cantidad: item.cantidad + 1 }
                        : item
                );
            }

            return [...actuales, { ...producto, cantidad: 1 }];
        });
    };

    const quitarProducto = (id) => {
        setSeleccionados((actuales) =>
            actuales.filter((item) => item.id !== id)
        );
    };

    const total = seleccionados.reduce(
        (suma, item) => suma + Number(item.precio) * item.cantidad,
        0
    );

    return (
        <section className="carrito">
            <h2>Resumen de productos seleccionados</h2>

            <div className="carrito-opciones">
                {productos.map((producto) => (
                    <button
                        key={producto.id}
                        type="button"
                        onClick={() => agregarProducto(producto)}
                    >
                        Agregar {producto.nombre}
                    </button>
                ))}
            </div>

            {seleccionados.length === 0 ? (
                <p>No hay productos seleccionados.</p>
            ) : (
                <ul>
                    {seleccionados.map((item) => (
                        <li key={item.id}>
                            {item.nombre} - Cantidad: {item.cantidad}
                            <button
                                type="button"
                                onClick={() => quitarProducto(item.id)}
                            >
                                Quitar
                            </button>
                        </li>
                    ))}
                </ul>
            )}

            <h3>
                Total: ${total.toLocaleString("es-CO")}
            </h3>
        </section>
    );
}

export default CarritoCheckout;
