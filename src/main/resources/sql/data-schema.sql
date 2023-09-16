--creo la base de datos
--create database pasionaria;

do $$
begin
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
		precio float8 not null default 0,
		id_externo varchar null,
		distribuidora_id int null,
		lv_categoria_id int null,
		fecha_creacion timestamp not null default now(),
		fecha_modificacion timestamp null,
		constraint lv_distribuidora_fk foreign key (distribuidora_id) references lookup_valor(id),
		constraint lv_categoria_fk foreign key (lv_categoria_id) references lookup_valor(id),
		constraint row_unique unique (nombre, lv_categoria_id, descripcion)
	);
end $$;

do $$
begin
	--agrego el tipo de las distribuidoras
	insert into lookup_tipo(codigo, descripcion) values ('DISTRIBUIDORAS','Grupo de Distribuidoras');
end $$;


--agrego los grupos de distribuidoras
do $$
	declare lvDistribuidoraId int := (select lt.id from lookup_tipo lt where lt.codigo = 'DISTRIBUIDORAS'); 
begin
	insert into lookup_valor(lookup_tipo_id, codigo, descripcion) values
		(lvDistribuidoraId, 'FACUNDO', 					'FACUNDO'),
		(lvDistribuidoraId, 'VILLARES', 				'VILLARES'),
		(lvDistribuidoraId, 'DON_GASPAR', 				'DON GASPAR'),
		(lvDistribuidoraId, 'MELAR', 					'MELAR'),
		(lvDistribuidoraId, 'LA_GRANJA_DEL_CENTRO', 	'LA GRANJA DEL CENTRO'),
		(lvDistribuidoraId, 'INDIAS', 					'INDIAS');   
end $$;

do $$
begin
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
		password_hash varchar not null,
		usuario varchar not null unique,
		constraint usuarios_perfil_fk foreign key(perfil_id) references perfiles(id)
	);
end $$;


--agrego perfil, roles,
insert into roles(codigo, descripcion) values ('ALL', 'Todos los permisos');
insert into perfiles(codigo, descripcion) values ('DIET_SALESMAN', 'Vendedor de dietetica');
do $$
	declare rolId int 		:= (select id from roles where codigo = 'ALL');
	declare perfilId int 	:= (select id from perfiles where codigo = 'DIET_SALESMAN');
begin
	insert into perfil_tiene_roles(perfil_id, rol_id) values (perfilId, rolId);
end $$;


--creo lookup tipo para categorias de productos
insert into lookup_tipo(codigo, descripcion) values ('CATEGORIA_DIETETICA', 'Categorias para comercio dietetico');

--creo los lookup valores para la categoria de e
do $$
	declare ltCategoryTypeId int := (SELECT id FROM lookup_tipo WHERE codigo = 'CATEGORIA_DIETETICA');
begin
	insert into lookup_valor(lookup_tipo_id, codigo, descripcion) values
		(ltCategoryTypeId, 'CEREALES', 					'CEREALES'),
		(ltCategoryTypeId, 'ESPECIES', 					'ESPECIES'),
		(ltCategoryTypeId, 'SEMILLAS', 					'SEMILLAS'),
		(ltCategoryTypeId, 'LEGUMBRES', 				'LEGUMBRES'),
		(ltCategoryTypeId, 'PERFUMERIA', 				'PERFUMERIA'),
		(ltCategoryTypeId, 'COMPLEMENTOS_ECOLOGICOS', 	'COMPLEMENTOS ECOLOGICOS'),
		(ltCategoryTypeId, 'COMPLEMENTO_DIARIA', 		'COMPLEMENTO DIARIA'),
		(ltCategoryTypeId, 'COSMETICA_NATURAL', 		'COSMETICA NATURAL'),
		(ltCategoryTypeId, 'POMADAS_MEDICINALES', 		'POMADAS MEDICINALES'),
		(ltCategoryTypeId, 'REUTILIZABLES', 			'REUTILIZABLES'),
		(ltCategoryTypeId, 'CONGELADOS', 				'CONGELADOS'),
		(ltCategoryTypeId, 'LECHES_VEGETALES', 			'LECHES VEGETALES'),
		(ltCategoryTypeId, 'REPOSTERIA', 				'REPOSTERIA'),
		(ltCategoryTypeId, 'PANIFICADOS', 				'PANIFICADOS'),
		(ltCategoryTypeId, 'PERMITIDOS', 				'PERMITIDOS	'),
		(ltCategoryTypeId, 'VINOS_ORGANICOS', 			'VINOS ORGANICOS'),
		(ltCategoryTypeId, 'GALLETERIA_SIN_TACC', 		'GALLETERIA SIN TACC'),
		(ltCategoryTypeId, 'SNACK', 					'SNACK'),
		(ltCategoryTypeId, 'HIERBAS_MEDICINALES', 		'HIERBAS MEDICINALES'),
		(ltCategoryTypeId, 'FRUTOS_SECOS', 				'FRUTOS SECOS'),
		(ltCategoryTypeId, 'HARINAS', 					'HARINAS');
end $$;

--agrego las columnas extras para los productos
alter table productos_internos 
	add precio_transporte float,
	add precio_empaquetado float,
	add porcentaje_ganancia float;
	
--agrego una columna extra a lookup_valor llamada valor que va a ser util cuando un lookup ademas de tener una descripcion textual deba contener una equivalencia numerica o de algun tipo
alter table lookup_valor 
	add valor varchar;
	
--agrego lookup tipo para medidas de ventas
insert into lookup_tipo(codigo, descripcion) values
('MEDIDAS_VENTAS','Medidas para ventas');

--agrego lookup valores para medidas de ventas
do $$
	declare lookupTipoId int := (select lt.id from lookup_tipo lt where lt.codigo = 'MEDIDAS_VENTAS'); 
begin
	insert into lookup_valor(lookup_tipo_id, codigo, descripcion, valor) values
	(lookupTipoId, 'MEDIDAS_VENTAS_50G','x50gr','0.05'),
	(lookupTipoId, 'MEDIDAS_VENTAS_100G','x100gr','0.1'),
	(lookupTipoId, 'MEDIDAS_VENTAS_250G','x250gr','0.25'),
	(lookupTipoId, 'MEDIDAS_VENTAS_500G','x500gr','0.5'),
	(lookupTipoId, 'MEDIDAS_VENTAS_1000G','x1kg','1'),
	(lookupTipoId, 'MEDIDAS_VENTAS_1U','Unidad','1');
end $$;

-- agrego usuarios por defecto
insert into usuarios (email, telefono, perfil_id, password_hash, usuario) values
	('luceroraul12@gmail.com', '2657678661', 1, '$2a$12$yd3lWqNjwKVeVYzpziI/D.KA1li.YpfC8pxc4erMQlV06M4eRDBJy', 'homitowen'),
	('pasionaria@gmail.com', '2664312837', 1, '$2a$12$5n.98mnq24l0Axnqa8d3xua05n4sunJRdkX/R5dzU8kjwZaV1ZYF6', 'pasionaria_juan');

-- SCRIPT DE UNIDADES
-- creo tabla de lookups para las unidades por categoria
create table lv_category_has_lv_unit (
	id serial primary key,
	lv_category_id 	int not null,
	lv_unit_id 		int not null,
	constraint lv_category_fk foreign key (lv_category_id) references lookup_valor(id),
	constraint lv_unit_fk foreign key (lv_unit_id) references lookup_valor(id),
	constraint lv_category_has_lv_unit_row_unique unique (lv_category_id, lv_unit_id)
);

do $$
	declare u50g int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_50G');
	declare u100g int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_100G');
	declare u250g int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_250G');
	declare u500g int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_500G');
	declare u1000g int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_1000G');
	declare unidad int 	= (select id from lookup_valor lv where lv.codigo = 'MEDIDAS_VENTAS_1U');

	declare cereales 			        int = (select id from lookup_valor lv where lv.codigo = 'CEREALES');
	declare especies 			        int = (select id from lookup_valor lv where lv.codigo = 'ESPECIES');
	declare semillas 			        int = (select id from lookup_valor lv where lv.codigo = 'SEMILLAS');
	declare legumbres 			        int = (select id from lookup_valor lv where lv.codigo = 'LEGUMBRES');
	declare cosmeticaNatural 	        int = (select id from lookup_valor lv where lv.codigo = 'COSMETICA_NATURAL');
	declare congelados 			        int = (select id from lookup_valor lv where lv.codigo = 'CONGELADOS');
	declare panificados 		        int = (select id from lookup_valor lv where lv.codigo = 'PANIFICADOS');
	declare vinosOrganicos 		        int = (select id from lookup_valor lv where lv.codigo = 'VINOS_ORGANICOS');
	declare permitidos 			        int = (select id from lookup_valor lv where lv.codigo = 'PERMITIDOS');
	declare lechesVegetales 	        int = (select id from lookup_valor lv where lv.codigo = 'LECHES_VEGETALES');
	declare reposteria 			        int = (select id from lookup_valor lv where lv.codigo = 'REPOSTERIA');
	declare galleteriaSinTacc 	        int = (select id from lookup_valor lv where lv.codigo = 'GALLETERIA_SIN_TACC');
	declare hierbasMedicinales 	        int = (select id from lookup_valor lv where lv.codigo = 'HIERBAS_MEDICINALES');
	declare perfumeria		 	        int = (select id from lookup_valor lv where lv.codigo = 'PERFUMERIA');
	declare complementosEcologicos 		int = (select id from lookup_valor lv where lv.codigo = 'COMPLEMENTOS_ECOLOGICOS');
	declare complementoDiaria 			int = (select id from lookup_valor lv where lv.codigo = 'COMPLEMENTO_DIARIA');
	declare reutilizables	 			int = (select id from lookup_valor lv where lv.codigo = 'REUTILIZABLES');
	declare pomadasMedicinales 			int = (select id from lookup_valor lv where lv.codigo = 'POMADAS_MEDICINALES');
	declare snack 						int = (select id from lookup_valor lv where lv.codigo = 'SNACK');
	declare frutosSecos 				int = (select id from lookup_valor lv where lv.codigo = 'FRUTOS_SECOS');
	declare harinas 					int = (select id from lookup_valor lv where lv.codigo = 'HARINAS');
	
begin
	--agrego todas las relaciones
insert into lv_category_has_lv_unit (lv_category_id, lv_unit_id) values
	(cereales, 			     u250g),
	(cereales, 			     u50g),
	(especies, 			     u50g),
	(semillas, 			     u100g),
	(legumbres, 		     u500g),
	(legumbres, 		     u100g),
	(cosmeticaNatural, 	     unidad),
	(congelados, 		     unidad),
	(panificados, 		     unidad),
	(vinosOrganicos, 	     unidad),
	(permitidos, 		     unidad),
	(lechesVegetales, 	     unidad),
	(reposteria, 		     u100g),
	(galleteriaSinTacc,      unidad),
	(hierbasMedicinales,     u50g),
	(perfumeria, 			 unidad),
	(complementosEcologicos, unidad),
	(complementoDiaria, 	 unidad),
	(reutilizables, 	 	 unidad),
	(pomadasMedicinales, 	 unidad),
	(snack, 				 unidad),
	(frutosSecos, 			 u50g),
	(harinas, 				 u500g),
	(harinas, 				 u100g);
end $$;

-- SCRIPT DE ESTADOS DE PRODUCTOS
create table productos_internos_status(
	id serial primary key,
	producto_interno_id int not null unique,
	is_unit bool not null default true,
	has_stock bool not null default true,
	constraint productos_internos_fk foreign key (producto_interno_id) references productos_internos(id)
);

insert into productos_internos_status (producto_interno_id)
	select id from productos_internos;
	
-- trigger cuando se crea un nuevo producto interno
create or replace function productos_internos_new_status_fn()
	returns trigger 
	language plpgsql
as $$
	declare isUnit bool = (
		select count(*) > 0 from lv_category_has_lv_unit lchlu 
			inner join lookup_valor lvUnit on lvunit.id = lchlu.lv_unit_id  
		where lv_category_id = new.lv_categoria_id
			and lvunit.codigo = 'MEDIDAS_VENTAS_1U'
	);
begin 
	insert into productos_internos_status (producto_interno_id, is_unit) 
		values (new.id, isUnit);
	return new;
end
$$;

create or replace trigger productos_internos_new_status_tr
	after insert 
	on productos_internos
	for each row
	execute procedure productos_internos_new_status_fn();

-- trigger cuando se elimina un producto interno
create or replace function productos_internos_delete_status_fn()
	returns trigger 
	language plpgsql
as $$
begin 
	delete from productos_internos_status where producto_interno_id = old.id;
	return old;
end
$$;

create or replace trigger productos_internos_delete_status_tr
	before delete
	on productos_internos
	for each row
	execute procedure productos_internos_delete_status_fn();












