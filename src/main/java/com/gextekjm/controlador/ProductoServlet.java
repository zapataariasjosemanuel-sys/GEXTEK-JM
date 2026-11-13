package com.gextekjm.controlador;

import com.gextekjm.dao.ProductoDAO;
import com.gextekjm.modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "nuevo":
                    request.getRequestDispatcher("/productos/formulario.jsp").forward(request, response);
                    break;
                case "editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("producto", dao.buscarPorId(idEditar));
                    request.getRequestDispatcher("/productos/formulario.jsp").forward(request, response);
                    break;
                case "eliminar":
                    dao.eliminar(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect(request.getContextPath() + "/productos?msg=eliminado");
                    break;
                default:
                    request.setAttribute("productos", dao.listar());
                    request.getRequestDispatcher("/productos/listar.jsp").forward(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/productos/listar.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Producto p = new Producto();
        String idParam = request.getParameter("id");

        try {
            if (idParam != null && !idParam.isEmpty()) {
                p.setId(Integer.parseInt(idParam));
            }
            p.setCodigo(request.getParameter("codigo").trim());
            p.setNombre(request.getParameter("nombre").trim());
            p.setCategoria(request.getParameter("categoria").trim());
            p.setCantidad(Integer.parseInt(request.getParameter("cantidad").trim()));
            p.setPrecio(new BigDecimal(request.getParameter("precio").trim()));

            if (p.getId() == 0) {
                dao.insertar(p);
                response.sendRedirect(request.getContextPath() + "/productos?msg=creado");
            } else {
                dao.actualizar(p);
                response.sendRedirect(request.getContextPath() + "/productos?msg=actualizado");
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("producto", p);
            request.setAttribute("error", "No se pudo guardar: " + e.getMessage());
            request.getRequestDispatcher("/productos/formulario.jsp").forward(request, response);
        }
    }
}
