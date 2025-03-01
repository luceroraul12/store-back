-- Populo tabla category
insert into category(client_id, name, lv_unit_id)
select distinct
	(select id from client where name = 'PASIONARIA') clientId,
	c.descripcion categoryName,
	r.lv_unit_id lvUnitId
from productos_internos p
	inner join lookup_valor c on c.id  = p.lv_categoria_id  
	inner join lv_category_has_lv_unit r on r.lv_category_id = c.id 
	
-- Arreglo las categorias de los productos
UPDATE 
	productos_internos p
SET 
	category_id = c.id
from lookup_valor lv 
	inner join category c on c.name = lv.descripcion
where lv.id = p.lv_categoria_id

-- Agrego tipo de medidas (Unidad / Fraccionado)
insert into lookup_tipo(codigo, descripcion) values
 	('WEIGHT_TYPE', 'Tipo de peso');
insert into lookup_valor (lookup_tipo_id, codigo, descripcion) values
	((select id from lookup_tipo lt where codigo = 'WEIGHT_TYPE'), 'WHEIGHT_TYPE_UNIT', 'Unidad'),
	((select id from lookup_tipo lt where codigo = 'WEIGHT_TYPE'), 'WHEIGHT_TYPE_GRAMS', 'Gramos'),
	((select id from lookup_tipo lt where codigo = 'WEIGHT_TYPE'), 'WHEIGHT_TYPE_KILOGRAMS', 'Kilogramos'),
	((select id from lookup_tipo lt where codigo = 'WEIGHT_TYPE'), 'WHEIGHT_TYPE_LITER', 'Litros');
-- Agrego los Lookup Parent Child para las unidades de medida
insert into 
	lookup_parent_child(parent_id, child_id) 
values
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_UNIT'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_1U')),
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_KILOGRAMS'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_1000G')),
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_GRAMS'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_100G')),
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_GRAMS'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_250G')),
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_GRAMS'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_500G')),
	((select id from lookup_valor where codigo = 'WHEIGHT_TYPE_GRAMS'),	(SELECT id FROM lookup_valor WHERE codigo = 'MEDIDAS_VENTAS_50G'));


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
	
	
	
	
	
	
	
	
	