<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Formulario de producto</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f4f6f8; margin:30px; }
        form { background:#fff; padding:24px; max-width:420px; border-radius:8px; }
        label { display:block; margin-top:12px; font-weight:bold; }
        input { width:100%; padding:8px; margin-top:4px; box-sizing:border-box; }
        button { margin-top:18px; padding:10px 20px; background:#1f6feb; color:#fff; border:0; border-radius:4px; cursor:pointer; }
        .error { color:#cf222e; }
    </style>
</head>
<body>
    <h1><c:out value="${producto.id > 0 ? 'Editar producto' : 'Nuevo producto'}"/></h1>

    <c:if test="${not empty error}"><p class="error">${error}</p></c:if>

    <form action="${pageContext.request.contextPath}/productos" method="post">
        <c:if test="${producto.id > 0}"><input type="hidden" name="id" value="${producto.id}"></c:if>

        <label for="codigo">C&oacute;digo</label>
        <input type="text" id="codigo" name="codigo" value="<c:out value='${producto.codigo}'/>" required>

        <label for="nombre">Nombre</label>
        <input type="text" id="nombre" name="nombre" value="<c:out value='${producto.nombre}'/>" required>

        <label for="categoria">Categor&iacute;a</label>
        <input type="text" id="categoria" name="categoria" value="<c:out value='${producto.categoria}'/>">

        <label for="cantidad">Cantidad</label>
        <input type="number" id="cantidad" name="cantidad" min="0" value="${empty producto ? 0 : producto.cantidad}" required>

        <label for="precio">Precio</label>
        <input type="number" id="precio" name="precio" min="0" step="0.01" value="${empty producto ? '0.00' : producto.precio}" required>

        <button type="submit">Guardar</button>
        <a href="${pageContext.request.contextPath}/productos">Cancelar</a>
    </form>
</body>
</html>
