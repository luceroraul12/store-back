-- person fk con client
alter table person add client_id int;
alter table person add constraint person_client_fk foreign key (client_id) references client(id);

-- person uq con client y no solo
ALTER TABLE person DROP CONSTRAINT person_uq;
ALTER TABLE person ADD CONSTRAINT person_uq unique (name, phone, client_id);

-- creo la tabla de unidades
create table unit(
	id serial primary key,
	name varchar not null,
	symbol varchar not null,
	relation float8 not null,
	unit_parent_id int,
	constraint unit_parent_fk foreign key(unit_parent_id) references unit(id),
	constraint presentation_unique unique (name)
);

-- creo tabla de presentaciones
create table presentation(
	id serial primary key,
	name varchar not null,
	description varchar,
	relation float8,
	unit_id int not null,
	constraint presentation_unit_fk foreign key(unit_id) references unit(id)
);

-- creo tabla para indicar que unidades tiene el cliente
create table client_presentation(
	id serial primary key,
	client_id int not null,
	presentation_id int not null,
	selectable boolean default true not null,
	pdf_show_child boolean default true not null,
	constraint client_presentation_client_fk foreign key(client_id) references client(id),
	constraint client_presentation_presentation_fk foreign key(presentation_id) references presentation(id)
);

-- category por client
create table category(
	id serial primary key,
	name varchar not null,
	description varchar,
	client_id int,
	constraint category_client_fk foreign key (client_id) references client(id),
	constraint category_uq unique (name, client_id)
);

-- Ahora los productos podrian tener una categoria
alter table productos_internos add category_id int;
alter table productos_internos add constraint product_category_fk foreign key (category_id) references category(id);

-- Agrego columnas para unidad de producto
alter table productos_internos add presentation_id int;
alter table productos_internos add constraint product_presentation_fk foreign key (presentation_id) references presentation(id);

-- Agrego columna de disponibilidad a los productos
alter table productos_internos add available boolean not null default true;

create table client_module(
	id serial primary key,
	client_id int not null,
	lv_module_id int not null,
	constraint client_module_fk foreign key(client_id) references client(id),
	constraint client_module_lv_fk foreign key (lv_module_id) references lookup_valor(id)
);

--agrego columna para presentacion en productCart
alter table cart_product add unit_id int;
alter table cart_product drop constraint cart_product_unit_fk;
alter table cart_product add constraint cart_product_unit_fk foreign key (unit_id) references unit(id);

-- Creo tabla para configuroaciones
create table config(
	id serial primary key,
	code varchar not null unique,
	value varchar not null,
	description varchar
);



