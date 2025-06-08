-- UNIDADES
insert into unit(name, symbol, relation, unit_parent_id) values
	('Unidad', 'U', 1, null),
	('Kilogramos', 'KG', 1, null),
	('Gramos', 'GR',0.001, null),
	('Metro cubico', 'M3', 1, null);
-- primero las independientes

-- PRESENTACIONES
insert into presentation(name, description, relation, unit_id) values
	('Unidad', 'Unidad', 1, (select id from unit where symbol = 'U')),
	('x1kg', 'Kilogramo', 1, (select id from unit where symbol = 'KG')),
	('x500gr', 'Medio kilo', 0.5, (select id from unit where symbol = 'GR')),
	('x250gr', 'Cuarto kilo', 0.25, (select id from unit where symbol = 'GR')),
	('x100gr', '', 0.1, (select id from unit where symbol = 'GR')),
	('x50gr', 'Paquete', 0.05, (select id from unit where symbol = 'GR')),
	('1/2 M3', 'Medio Metro Cúbico', 0.5, (select id from unit where symbol = 'M3'));

-- ASIGNACION DE PRESENTACIONES A CLIENTES
insert into client_presentation(client_id, presentation_id) values 
	(1, (select id from presentation where name = 'Unidad')),
	(1, (select id from presentation where name = 'x1kg')),
	(1, (select id from presentation where name = 'x500gr')),
	(1, (select id from presentation where name = 'x250gr')),
	(1, (select id from presentation where name = 'x100gr')),
	(1, (select id from presentation where name = 'x50gr'));

-- Populo tabla category
insert into category(client_id, name)
select distinct
	(select id from client where name = 'PASIONARIA') clientId,
	c.descripcion categoryName
from productos_internos p
	inner join lookup_valor c on c.id  = p.lv_categoria_id;
	
-- Arreglo las categorias de los productos
UPDATE 
	productos_internos p
SET 
	category_id = c.id
from lookup_valor lv 
	inner join category c on c.name = lv.descripcion
where lv.id = p.lv_categoria_id;

-- Arreglo las presentaciones de los productos para que no dependan de la categoria
update productos_internos p
	set presentation_id = (
		select 
			case 
				when r.is_unit 
				then (select id from presentation where name = 'Unidad')
				else pres.id
			end
		from productos_internos_status r where r.producto_interno_id = p.id
	)
from category c
	inner join lookup_valor lc on lc.descripcion = c.name
	inner join lv_category_has_lv_unit r on r.lv_category_id = lc.id
	inner join lookup_valor lu on lu.id = r.lv_unit_id
	inner join presentation pres on lu.descripcion = pres."name" 
where c.id = p.category_id;


-- Agrego modulos de la aplicación
insert into lookup_tipo(codigo, descripcion) values
 	('MODULE_TYPE', 'Tipo de modulos de aplicación');
insert into lookup_valor (lookup_tipo_id, codigo, descripcion) values
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_SEARCH', 'Buscador'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_SEARCH_CALCULATOR', 'Marcador'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_SEARCH_UPDATER', 'Actualizador'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_INVENTORY', 'Inventario'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_INVENTORY_STATUS', 'Control de Estados'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_CATEGORY', 'Categorias'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_PERSON', 'Personas'),
	((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_CART', 'Pedidos');

-- Agrego modulos para pasionaria
insert into client_module (client_id, lv_module_id) values 
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH_CALCULATOR')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH_UPDATER')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_INVENTORY')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_INVENTORY_STATUS')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_CATEGORY')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_PERSON')),
((select id from client where name = 'PASIONARIA'),(select id from lookup_valor where codigo = 'MODULE_TYPE_CART'));
-- Agrego modulos para PRUEBA
insert into client_module (client_id, lv_module_id) values 
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH_CALCULATOR')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_SEARCH_UPDATER')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_INVENTORY')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_INVENTORY_STATUS')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_CATEGORY')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_PERSON')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_CART'));

--Agrego las unidades a los cart_product de dicho momento en base a las unidades que tienen los productos actualmente
-- Arreglo las unidades de los productos para que no dependan de la categoria
update cart_product cp
	set unit_id = (
		select u.id from productos_internos p 
			inner join presentation pre on pre.id = p.presentation_id 
			inner join unit u on u.id = pre.unit_id 
		where cp.product_id = p.id
	);
	
-- tengo que asignar las personas al pasionaria
update person set client_id = 1;


-- Actualización de datos de PASIONARIA -> NATIVO
UPDATE public.client 
	SET "name"='NATIVO', 
address='Av. 25 de mayo 1006', 
address_link='https://goo.gl/maps/4K6m4uivZY6CHYeH7',
phone='+542664312837', 
phone_link='https://api.whatsapp.com/send/?phone=542664312837&text&type=phone_number&app_absent=0',
instagram='nativo.vm.sl',
instagram_link='https://www.instagram.com/nativo.vm.sl', 
facebook='Nativo Almacen Natural', 
facebook_link='https://www.facebook.com/profile.php?id=100070005324554', 
filename_logo='nativo-logo.jpg' 
WHERE id=1;


-- Agrego config para la url de las 
insert into config(code,value, description) values ('IMAGE_FILE_PATH','/home/homitowen/store/images', 'Ruta de la carpeta de imagenes');

-- Actualizo el stock de los productos en base de la tabla auxiliar
update productos_internos p
	set available = (select has_stock from productos_internos_status r where r.producto_interno_id = p.id);


	
	
	
	
	