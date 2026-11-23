import { useEffect, useState } from "react";
import productoService from "./services/productoService";
import Catalogo from "./components/Catalogo";
import FichaProducto from "./components/FichaProducto";
import CarritoCheckout from "./components/CarritoCheckout";
import "./App.css";

function App() {
    const [productoSeleccionado, setProductoSeleccionado] = useState(null);
    const [productoEditar, setProductoEditar] = useState(null);
    const [recargar, setRecargar] = useState(0);
    const [productos, setProductos] = useState([]);

    const [formulario, setFormulario] = useState({
        codigo: "",
        nombre: "",
        categoria: "",
        cantidad: 0,
        precio: 0
    });

    useEffect(() => {
        productoService.listar()
            .then(setProductos)
            .catch(() => setProductos([]));
    }, [recargar]);

    const manejarCambio = (e) => {
        setFormulario({
            ...formulario,
            [e.target.name]: e.target.value
        });
    };

    const limpiarFormulario = () => {
        setFormulario({
            codigo: "",
            nombre: "",
            categoria: "",
            cantidad: 0,
            precio: 0
        });
        setProductoEditar(null);
    };

    const guardarProducto = async (e) => {
        e.preventDefault();

        const datos = {
            ...formulario,
            cantidad: Number(formulario.cantidad),
            precio: Number(formulario.precio)
        };

        try {
            if (productoEditar) {
                await productoService.actualizar(productoEditar.id, datos);
                alert("Producto actualizado correctamente.");
            } else {
                await productoService.registrar(datos);
                alert("Producto registrado correctamente.");
            }

            limpiarFormulario();
            setRecargar((valor) => valor + 1);
        } catch (error) {
            alert(error.response?.data?.mensaje || "No fue posible guardar el producto.");
        }
    };

    const editarProducto = (producto) => {
        setProductoEditar(producto);
        setFormulario({
            codigo: producto.codigo,
            nombre: producto.nombre,
            categoria: producto.categoria || "",
            cantidad: producto.cantidad,
            precio: producto.precio
        });

        window.scrollTo({ top: 0, behavior: "smooth" });
    };

    return (
        <main className="aplicacion">
            <header className="encabezado">
                <div className="marca">
                    <h1>GEXTEK JM</h1>
                    <p>Sistema de gestión de productos e inventario</p>
                </div>
                <span className="estado">Sistema conectado</span>
            </header>

            <section className="panel-principal">
                <h2>{productoEditar ? "Actualizar producto" : "Registrar producto"}</h2>

                <form className="formulario-producto" onSubmit={guardarProducto}>
                    <input
                        name="codigo"
                        placeholder="Código del producto"
                        value={formulario.codigo}
                        onChange={manejarCambio}
                        required
                    />

                    <input
                        name="nombre"
                        placeholder="Nombre del producto"
                        value={formulario.nombre}
                        onChange={manejarCambio}
                        required
                    />

                    <input
                        name="categoria"
                        placeholder="Categoría"
                        value={formulario.categoria}
                        onChange={manejarCambio}
                    />

                    <input
                        name="cantidad"
                        type="number"
                        min="0"
                        placeholder="Cantidad"
                        value={formulario.cantidad}
                        onChange={manejarCambio}
                        required
                    />

                    <input
                        name="precio"
                        type="number"
                        min="0"
                        step="0.01"
                        placeholder="Precio"
                        value={formulario.precio}
                        onChange={manejarCambio}
                        required
                    />

                    <button type="submit">
                        {productoEditar ? "Guardar cambios" : "Registrar producto"}
                    </button>

                    {productoEditar && (
                        <button type="button" className="boton-cancelar" onClick={limpiarFormulario}>
                            Cancelar edición
                        </button>
                    )}
                </form>
            </section>

            <Catalogo
                recargar={recargar}
                onVer={setProductoSeleccionado}
                onEditar={editarProducto}
            />

            <CarritoCheckout productos={productos} />

            <FichaProducto
                producto={productoSeleccionado}
                onCerrar={() => setProductoSeleccionado(null)}
            />

            <footer className="pie">
                <p>GEXTEK JM © 2026 - Gestión de inventario</p>
            </footer>
        </main>
    );
}

export default App;
