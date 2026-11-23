import { useEffect, useState } from "react";
import productoService from "../services/productoService";
import CamisaCard from "./CamisaCard";

function Catalogo({ onVer, onEditar, recargar }) {
    const [productos, setProductos] = useState([]);
    const [cargando, setCargando] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {
        const cargarProductos = async () => {
            try {
                setCargando(true);
                const datos = await productoService.listar();
                setProductos(datos);
                setError("");
            } catch (err) {
                setError("No fue posible cargar los productos. Verifica que Spring Boot esté funcionando.");
            } finally {
                setCargando(false);
            }
        };

        cargarProductos();
    }, [recargar]);

    const eliminarProducto = async (id) => {
        if (!window.confirm("¿Deseas eliminar este producto?")) return;

        try {
            await productoService.eliminar(id);
            setProductos((actuales) =>
                actuales.filter((producto) => producto.id !== id)
            );
        } catch (err) {
            alert("No fue posible eliminar el producto.");
        }
    };

    if (cargando) return <p>Cargando productos...</p>;
    if (error) return <p className="mensaje-error">{error}</p>;

    return (
        <section className="catalogo">
            <h2>Inventario de productos</h2>

            {productos.length === 0 ? (
                <p>No hay productos registrados.</p>
            ) : (
                <div className="productos-grid">
                    {productos.map((producto) => (
                        <CamisaCard
                            key={producto.id}
                            producto={producto}
                            onVer={onVer}
                            onEditar={onEditar}
                            onEliminar={eliminarProducto}
                        />
                    ))}
                </div>
            )}
        </section>
    );
}

export default Catalogo;
