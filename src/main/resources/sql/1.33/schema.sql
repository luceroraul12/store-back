--Creo tabla para los clientes
create table customer (
	id serial primary key,
	username varchar not null unique
);
--Creo tabla para las ordenes
create table order_customer (
	id serial primary key,
	customer_id int not null,
	client_id int not null,
	date timestamp not null,
	constraint order_customer_fk foreign key (customer_id) references customer(id),
	constraint order_client_fk foreign key (client_id) references client(id)
);
--Creo tabla para los productos de las ordenes
create table order_has_product(
	id serial primary key,
	unit_name varchar not null,
	unit_value float8 not null,
	unit_price int not null,
	amount float not null,
	order_id int not null,
	product_id int not null,
	constraint order_has_product_order_fk foreign key (order_id) references order_customer(id),
	constraint order_has_product_product_fk foreign key (product_id) references productos_internos(id)
);

--Agrego columna customer para el nombre del archivo del logo
alter table client add filename_logo varchar;
-- Agrego dato del logo para pasionaria
update client set filename_logo = 'pasionaria_logo.png';
