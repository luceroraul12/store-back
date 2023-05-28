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
	fecha_creacion timestamp not null default now(),
	fecha_modificacion timestamp null,
	constraint lv_distribuidora_fk foreign key (distribuidora_id) references lookup_valor(id),
	constraint row_unique unique (nombre, descripcion)
);

--agrego el tipo de las distribuidoras
insert into lookup_tipo(codigo, descripcion) values ('DISTRIBUIDORAS','Grupo de Distribuidoras');

--agrego los grupos de distribuidoras
insert into lookup_valor(lookup_tipo_id, codigo, descripcion)
	values
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'FACUNDO', 'FACUNDO'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'VILLARES', 'VILLARES'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'DON_GASPAR', 'DON GASPAR'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'MELAR', 'MELAR'),
		((select id from lookup_tipo lt where codigo = 'DISTRIBUIDORAS'), 'INDIAS', 'INDIAS');

--creo tablas para seguridad y usuarios
create table perfiles(
	id serial not null unique primary key,
	codigo varchar not null unique,
	descripcion varchar not null unique
);

create table roles(
	id serial not null unique primary key,
	codigo varchar not null unique,
	descripcion varchar not null unique
);

create table perfil_tiene_roles(
	id serial not null unique primary key,
	perfil_id int not null,
	rol_id int not null,
	constraint perfil_tiene_roles_row_unique unique(perfil_id, rol_id),
	constraint perfiles_fk foreign key(perfil_id) references perfiles(id),
	constraint roles_fk foreign key(rol_id) references roles(id));

create table usuarios(
	id serial not null primary key unique,
	email varchar not null unique,
	telefono varchar not null,
	perfil_id int not null,
	constraint usuarios_perfil_fk foreign key(perfil_id) references perfiles(id)
);

--agrego perfil, roles,
insert into roles(codigo, descripcion) values ('ALL', 'Todos los permisos');
insert into perfiles(codigo, descripcion) values ('DIET_SALESMAN', 'Vendedor de dietetica');
insert into perfil_tiene_roles(perfil_id, rol_id) values
	((select id from perfiles where codigo = 'DIET_SALESMAN'), (select id from roles where codigo = 'ALL'));
