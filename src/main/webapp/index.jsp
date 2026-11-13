<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>GEXTEK JM</title>
    <style>
        body { font-family: Arial, sans-serif; background:#f4f6f8; text-align:center; padding-top:80px; }
        a.btn { background:#1f6feb; color:#fff; padding:12px 24px; border-radius:6px; text-decoration:none; }
    </style>
</head>
<body>
    <h1>GEXTEK JM</h1>
    <p>Hoy es <%= new java.util.Date() %></p>
    <a class="btn" href="<%= request.getContextPath() %>/productos">M&oacute;dulo de productos</a>
</body>
</html>
