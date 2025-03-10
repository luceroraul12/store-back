-- UNIDADES
-- primero las independientes
--pasionaria
insert into unit(client_id,  selectable, pdf_show_child,  "name", description, relation) values
	((select id from client where name = 'PASIONARIA'), true, true, 'x1kg', 'Kilogramo', 1),
	((select id from client where name = 'PASIONARIA'), true, true, 'Unidad', 'Unidad de producto', 1);
--prueba
insert into unit(client_id,  selectable, pdf_show_child,  "name", description, relation) values
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'x1kg', 'Kilogramo', 1),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'Unidad', 'Unidad de producto', 1),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'M3', 'Metro cúbico', 1);
-- luego las que dependen de algo mas
--pasionaria
insert into unit(client_id,  selectable, pdf_show_child,  "name", description, relation, unit_parent_id) values
	((select id from client where name = 'PASIONARIA'), true, true, 'x500gr', 'Medio kilogramo', 0.5, 
		(select id from unit 
			where client_id = (select id from client where name = 'PASIONARIA') 
			and name = 'x1kg')),
	((select id from client where name = 'PASIONARIA'), true, true, 'x250gr', 'Cuarto kilogramo', 0.25, 
		(select id from unit 
			where client_id = (select id from client where name = 'PASIONARIA') 
			and name = 'x1kg')),
	((select id from client where name = 'PASIONARIA'), true, true, 'x100gr', '', 0.1, 
		(select id from unit 
			where client_id = (select id from client where name = 'PASIONARIA') 
			and name = 'x1kg')),
	((select id from client where name = 'PASIONARIA'), true, true, 'x50gr', 'Paquete', 0.05, 
		(select id from unit 
			where client_id = (select id from client where name = 'PASIONARIA') 
			and name = 'x1kg'));
--prueba
insert into unit(client_id,  selectable, pdf_show_child,  "name", description, relation, unit_parent_id) values
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'x500gr', 'Medio kilogramo', 0.5, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'x1kg')),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'x250gr', 'Cuarto kilogramo', 0.25, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'x1kg')),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'x100gr', '', 0.1, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'x1kg')),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'x50gr', 'Paquete', 0.05, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'x1kg')),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, '1/2 M3', 'Medio Metro Cúbico', 0.5, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'M3')),
	((select id from client where name = 'HOMITOWEN-TEST'), true, true, 'Bolsita', 'Bolsita de albañil', 0.05, 
		(select id from unit 
			where client_id = (select id from client where name = 'HOMITOWEN-TEST') 
			and name = 'M3'));

-- Populo tabla category
insert into category(client_id, name, unit_id)
select distinct
	(select id from client where name = 'PASIONARIA') clientId,
	c.descripcion categoryName,
	(select u.id from unit u
		inner join client c on c.id = u.client_id
		where c.name = 'PASIONARIA'
			and u.name = lu.descripcion)
from productos_internos p
	inner join lookup_valor c on c.id  = p.lv_categoria_id  
	inner join lv_category_has_lv_unit r on r.lv_category_id = c.id 
	inner join lookup_valor lu on lu.id = r.lv_unit_id 
	
-- Arreglo las categorias de los productos
UPDATE 
	productos_internos p
SET 
	category_id = c.id
from lookup_valor lv 
	inner join category c on c.name = lv.descripcion
where lv.id = p.lv_categoria_id

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


	
	
	
	
	