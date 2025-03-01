-- person fk con client
alter table person add client_id int;
alter table person add constraint person_client_fk foreign key (client_id) references client(id);

-- person uq con client y no solo
ALTER TABLE person DROP CONSTRAINT person_uq;
ALTER TABLE person ADD CONSTRAINT person_uq unique (name, phone, client_id);

-- category por client
create table category(
	id serial primary key,
	name varchar not null,
	description varchar,
	lv_unit_id int,
	client_id int,
	constraint category_lv_unit_fk foreign key(lv_unit_id) references lookup_valor(id),
	constraint category_client_fk foreign key (client_id) references client(id),
	constraint category_uq unique (name, client_id)
);

-- ALTER TABLE category ADD constraint category_uq unique (name, client_id)

-- Ahora los productos podrian tener una categoria
alter table productos_internos add category_id int;
alter table productos_internos add constraint product_category_fk foreign key (category_id) references category(id);

-- lookup_parent_child (Ejemplo de uso: Unidades fraccionarias, cuentan con una unidad de medida 100GR esta en Gramos, 250ml esta en Mililitros, U es Unidad, KG es Kilogramo)
create table lookup_parent_child(
	id serial primary key,
	parent_id int not null,
	child_id int not null,
	constraint lookup_parent_fk foreign key(parent_id) references lookup_valor(id),
	constraint lookup_child_fk foreign key (child_id) references lookup_valor(id)
);

create table client_module(
	id serial primary key,
	client_id int not null,
	lv_module_id int not null,
	constraint client_module_fk foreign key(client_id) references client(id),
	constraint client_module_lv_fk foreign key (lv_module_id) references lookup_valor(id)
);
