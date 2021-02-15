# todo1-kardex-store
Aplicación de control de Inventario utilizando Spring Boot 2.4.2 y H2 como base de datos interna

La aplicación se encuentra desplegada en el puerto por defecto:
 - http://localhost:8080/producto/

Existen dos usuarios creados previamente con 2 roles distintos:
- **Administrador**, con todos los privilegios administrativos para manejo de Clientes, Productos e Inventario. **username:** admin **password:** 123
- **Cliente**, puede unicamente realizar nuevos pedidos/facturas de los productos de la tienda. **username:** user **password:** 123

Tambien existen APIS Rest protegidas en los siguientes URLS, pero no se las utilizarón ya que los controles para las vistas desarrollados utilizaron el patrón MVC
http://localhost:8080/facturas
http://localhost:8080/roles
http://localhost:8080/clientes
