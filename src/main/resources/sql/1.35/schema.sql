drop table order_customer cascade

drop table order_has_product 

-- Creo tabla para cart
create table cart(
	id serial primary key,
	date_created timestamp not null,
	status varchar not null,
	client_id int not null,
	constraint cart_client_fk foreign key(client_id) references client(id)
);
-- Creo tabla para los productos de la cart
create table cart_product(
	id serial primary key,
	lv_unit_id int not null,
	cart_id int not null,
	product_id int not null,
	price int not null,
	quantity float8 not null,
	constraint cart_product_cart_fk foreign key(cart_id) references cart(id),
	constraint cart_product_product_fk foreign key(product_id) references productos_internos(id),
	constraint cart_product_unit_fk foreign key(lv_unit_id) references lookup_valor(id)
);

