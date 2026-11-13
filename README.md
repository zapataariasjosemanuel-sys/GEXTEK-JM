# GEXTEK JM - Módulo de productos (Spring Boot)

Evidencia **GA7-220501096-AA3-EV01** - Codificación de módulos del software.

Módulo de gestión de productos del inventario construido con **Spring Boot 3**
(Spring MVC + Spring Data JPA + Thymeleaf) sobre **PostgreSQL**.

## Arquitectura por capas

| Capa | Clase | Responsabilidad |
|---|---|---|
| Modelo | `modelo/Producto` | Entidad JPA con validaciones |
| Repositorio | `repositorio/ProductoRepository` | Acceso a datos (Spring Data) |
| Servicio | `servicio/ProductoService` | Reglas de negocio (código único) |
| Controlador | `controlador/ProductoController` | Rutas web GET y POST |
| Vistas | `templates/productos/*.html` | Thymeleaf |

## Funcionalidades

- Listar productos y buscar por código, nombre o categoría.
- Registrar un producto con validación de campos.
- Editar y eliminar productos.
- Validación de código duplicado.

## Requisitos

- Java 17 y Maven 3.9 o superior
- PostgreSQL con la base `gextek_jm` (tabla `productos`)

## Ejecución (PowerShell)

```powershell
$env:DB_PASSWORD = "TU_CONTRASEÑA_DE_POSTGRES"
mvn spring-boot:run
```

Abrir: http://localhost:8081/

## Estándares de codificación

- Nombres de clases en PascalCase y métodos/variables en camelCase.
- Un paquete por capa; inyección de dependencias por constructor.
- Javadoc en todas las clases y métodos públicos.
- La contraseña de la base de datos no se guarda en el código (variable de entorno).
- Eliminación por POST y patrón Post/Redirect/Get.
