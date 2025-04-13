-- person fk con client
alter table person add client_id int;
alter table person add constraint person_client_fk foreign key (client_id) references client(id);

-- person uq con client y no solo
ALTER TABLE person DROP CONSTRAINT person_uq;
ALTER TABLE person ADD CONSTRAINT person_uq unique (name, phone, client_id);

-- creo tabla de unidades
create table unit(
	id serial primary key,
	name varchar not null,
	description varchar,
	relation float8,
	selectable boolean,
	pdf_show_child boolean,
	unit_parent_id int,
	client_id int not null,
	constraint unit_parent_fk foreign key(unit_parent_id) references unit(id),
	constraint unit_client_fk foreign key(client_id) references client(id),
	constraint unit_unique unique (client_id, name)
);

-- category por client
create table category(
	id serial primary key,
	name varchar not null,
	description varchar,
	unit_id int,
	client_id int,
	constraint category_unit_fk foreign key(unit_id) references unit(id),
	constraint category_client_fk foreign key (client_id) references client(id),
	constraint category_uq unique (name, client_id)
);

-- ALTER TABLE category ADD constraint category_uq unique (name, client_id)

-- Ahora los productos podrian tener una categoria
alter table productos_internos add category_id int;
alter table productos_internos add constraint product_category_fk foreign key (category_id) references category(id);

-- Agrego columnas para unidad de producto
alter table productos_internos add unit_id int;
alter table productos_internos add constraint product_unit_fk foreign key (unit_id) references unit(id);

create table client_module(
	id serial primary key,
	client_id int not null,
	lv_module_id int not null,
	constraint client_module_fk foreign key(client_id) references client(id),
	constraint client_module_lv_fk foreign key (lv_module_id) references lookup_valor(id)
);

