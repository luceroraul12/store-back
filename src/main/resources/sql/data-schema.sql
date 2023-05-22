--creo la base de datos
create database pasionaria;

--creo tablas genericas
create table lookup_tipo(
	id serial not null primary key,
	codigo varchar not null,
	descripcion varchar not null,
	constraint lv_tipo_row_unique unique (codigo)
);

create table lookup_valor(
	id serial not null primary key,
	codigo varchar not null,
	descripcion varchar not null,
	lookup_tipo_id int not null,
	constraint lt_fk foreign key (lookup_tipo_id) references lookup_tipo(id),
	constraint lv_row_unique unique (codigo, lookup_tipo_id)
);

--creo la tabla para almacenar los productos del local
create table productos_internos(
	id serial not null primary key,
	nombre varchar null,
	descripcion varchar null,
	precio float8 not null,
	id_externo varchar null,
	distribuidora_id int null,
	fecha_creacion timestamp not null,
	fecha_modificacion timestamp null,
	constraint lv_distribuidora_fk foreign key (distribuidora_id) references lookup_valor(id),
	constraint row_unique unique (id_externo, distribuidora_id)
);

--agrego el tipo de las distribuidoras
insert into lookup_tipo(codigo, descripcion) values ('DISTRIBUIDORAS','Grupo de Distribuidoras');

--agrego los grupos de distribuidoras
insert into lookup_valor(lookup_tipo_id, codigo, descripcion)
	values
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DISTRIBUIDORA_FACUNDO', 'FACUNDO'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DISTRIBUIDORA_VILLARES', 'VILLARES'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DISTRIBUIDORA_DON_GASPAR', 'DON GASPAR'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DISTRIBUIDORA_MELAR', 'MELAR'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DISTRIBUIDORA_INDIAS', 'INDIAS');
