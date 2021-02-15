/* Llenar tablas con datos iniciales*/
INSERT INTO clientes (nombre, apellido, email,  foto, username, password) VALUES('Administrador', 'master', 'admin@gmail.com','', 'admin', '$2a$10$JuMONwn2iK.TeFFzgu4MrezicAEWWZFAMAlpbC.D.DcNEDEXSbLO2');
INSERT INTO clientes (nombre, apellido, email,  foto, username, password) VALUES('Gabriel', 'Eguiguren', 'gabo.egui@gmail.com','', 'user', '$2a$10$AqpelY67IP1jn.r1ew7ENuHq2nyhL09KLozhdxD0cZekWDXIpB49G');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('John', 'Doe', 'john.doe@gmail.com','','user1');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com','','user2');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Jane', 'Doe', 'jane.doe@gmail.com','','user3');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com','','user4');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com','','user5');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Richard', 'Helm', 'richard.helm@gmail.com','','user6');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com','','user7');
INSERT INTO clientes (nombre, apellido, email,  foto, username) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com','','user8');

INSERT INTO roles (nombre) VALUES('ROLE_USER');
INSERT INTO roles (nombre) VALUES('ROLE_ADMIN');
INSERT INTO usuarios_roles (user_id, role_id) VALUES(1, 1);
INSERT INTO usuarios_roles (user_id, role_id) VALUES(1, 2);
INSERT INTO usuarios_roles (user_id, role_id) VALUES(2, 1);

INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel The Avengers DVD', 15.99 , 'producto1.jpg', 'AB-111', 'Descripción detallada y características del Producto', 40);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Studios Avengers: Endgame DVD', 15.99, 'producto2.jpg', 'AB-112', 'Descripción detallada y características del Producto', 50);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Studios Captain Marvel DVD', 15.99, 'producto3.jpg', 'AB-113', 'Descripción detallada y características del Producto', 100);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Studios Captain America: The First Avenger DVD', 12.99, 'marvel.jpg', 'AB-114', 'Descripción detallada y características del Producto', 25 );
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Studios Thor DVD', 12.99, 'marvel.jpg', 'AB-116', 'Descripción detallada y características del Producto', 150);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Studios Iron Man 2 (4K UHD) DVD', 15.99, 'marvel.jpg', 'AB-115', 'Descripción detallada y características del Producto',50);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Marvel Encyclopedia, New Edition (Inglés) Tapa dura – 2 Abril 2019', 21.99, 'marvel.jpg', 'BB-111', 'Descripción detallada y características del Producto', 0);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('RoomMates RMK4048GM Avengers: Endgame Peel and Stick - Adhesivos de pared', 21.99, 'marvel.jpg', 'BB-112', 'Descripción detallada y características del Producto', 0);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Smash Up: Marvel | Officially Licensed by Alderac Entertainment Group (AEG) | Collectible Marvel Card Game', 21.99, 'marvel.jpg', 'BB-113', 'Descripción detallada y características del Producto', 0 );
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Hasbro Marvel Legends Series Venom - Figura de acción coleccionable (6.0 in)', 21.99, 'marvel.jpg', 'BB-114', 'Descripción detallada y características del Producto', 0);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('LEGO Marvel Avengers Truck Take-Down 76143 (477 piezas)', 21.99, 'marvel.jpg', 'CC-111', 'Descripción detallada y características del Producto', 0);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Jay Franco Marvel Avengers Fight Club Juego de cama de 5 piezas', 21.99, 'marvel.jpg', 'CC-112', 'Descripción detallada y características del Producto', 0);
INSERT INTO productos (nombre, precio, foto, sku_number, descripcion, cantidad_stock) VALUES('Fantasy Flight Games FFGMC01 Marvel Champions: El juego de cartas', 21.99, 'marvel.jpg', 'CC-113', 'Descripción detallada y características del Producto', 0);

INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, '2020-02-14', 10, 50, 'Entrada', 500, 1);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (10, NOW(), 10, 40, 'Salida', 100, 1);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, NOW(), 10, 50, 'Entrada', 500, 2);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, NOW(), 10, 100, 'Entrada', 1000, 3);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, NOW(), 10, 25, 'Entrada', 250, 4);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, NOW(), 10, 150, 'Entrada', 1500, 5);
INSERT INTO kardex(cantidad_movimiento, fecha_registro, precio_de_costo, saldo_cantidad, tipo_operacion, valor_total_de_costo, producto_id) VALUES (0, NOW(), 10, 50, 'Entrada', 500, 6);

/* Creamos algunas facturas */
INSERT INTO facturas (direccion, observacion, cliente_id) VALUES('De Los Rosales N46-113', null, 1);
INSERT INTO item_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO item_facturas (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO item_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO item_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (direccion, observacion, cliente_id) VALUES('De Los Rosales N46-113', 'Cancelada con tarjeta de credito', 1);
INSERT INTO item_facturas (cantidad, factura_id, producto_id) VALUES(3, 2, 6);
