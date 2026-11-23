import axios from "axios";

const API_URL = "http://localhost:8081/api/productos";

const productoService = {
    listar: async () => {
        const respuesta = await axios.get(API_URL);
        return respuesta.data;
    },

    buscarPorId: async (id) => {
        const respuesta = await axios.get(`${API_URL}/${id}`);
        return respuesta.data;
    },

    registrar: async (producto) => {
        const respuesta = await axios.post(API_URL, producto);
        return respuesta.data;
    },

    actualizar: async (id, producto) => {
        const respuesta = await axios.put(`${API_URL}/${id}`, producto);
        return respuesta.data;
    },

    eliminar: async (id) => {
        await axios.delete(`${API_URL}/${id}`);
    }
};

export default productoService;
