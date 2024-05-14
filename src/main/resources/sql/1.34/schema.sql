
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
