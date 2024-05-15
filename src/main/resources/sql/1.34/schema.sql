
-- Creo tabla para guardar los datos de las distribuidoras a trabajar
create table datos_distribuidora (
	id serial primary key,
	active bool,
	lv_distribuidora_id int not null,
	fecha_actualizacion timestamp,
	size int,
	web bool,
	excel bool,
	web_url varchar,
	has_paginator bool,
	constraint datos_distribuidora_distribuidora_fk foreign key(lv_distribuidora_id) references lookup_valor(id)
);

-- Agrego la columna para saber a quien pertenece el producto
alter table productos_internos 
	add client_id int not null default 1
	constraint productos_internos_client references client(id);
