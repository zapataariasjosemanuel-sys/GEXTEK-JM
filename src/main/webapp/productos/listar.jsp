<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Productos</title>
    <style>
        body { font-family: Arial, sans-serif; margin:30px; background:#f4f6f8; }
        table { border-collapse:collapse; width:100%; background:#fff; }
        th, td { border:1px solid #ddd; padding:10px; text-align:left; }
        th { background:#1f6feb; color:#fff; }
        .btn { padding:6px 12px; border-radius:4px; text-decoration:none; color:#fff; }
        .nuevo { background:#2da44e; } .editar { background:#d29922; } .eliminar { background:#cf222e; }
        .ok { color:#2da44e; } .error { color:#cf222e; }
    </style>
</head>
<body>
    <h1>Listado de productos</h1>

    <c:if test="${param.msg == 'creado'}"><p class="ok">Producto creado.</p></c:if>
    <c:if test="${param.msg == 'actualizado'}"><p class="ok">Producto actualizado.</p></c:if>
    <c:if test="${param.msg == 'eliminado'}"><p class="ok">Producto eliminado.</p></c:if>
    <c:if test="${not empty error}"><p class="error">${error}</p></c:if>

    <p><a class="btn nuevo" href="${pageContext.request.contextPath}/productos?accion=nuevo">+ Nuevo producto</a></p>

    <table>
        <tr>
            <th>ID</th><th>C&oacute;digo</th><th>Nombre</th><th>Categor&iacute;a</th><th>Cantidad</th><th>Precio</th><th>Acciones</th>
        </tr>
        <c:forEach var="p" items="${productos}">
            <tr>
                <td>${p.id}</td>
                <td><c:out value="${p.codigo}"/></td>
                <td><c:out value="${p.nombre}"/></td>
                <td><c:out value="${p.categoria}"/></td>
                <td>${p.cantidad}</td>
                <td>${p.precio}</td>
                <td>
                    <a class="btn editar" href="${pageContext.request.contextPath}/productos?accion=editar&id=${p.id}">Editar</a>
                    <a class="btn eliminar" href="${pageContext.request.contextPath}/productos?accion=eliminar&id=${p.id}"
                       onclick="return confirm('Eliminar este producto?');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty productos}">
            <tr><td colspan="7">No hay productos registrados.</td></tr>
        </c:if>
    </table>

    <p><a href="${pageContext.request.contextPath}/index.jsp">&larr; Inicio</a></p>
</body>
</html>
