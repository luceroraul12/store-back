-- ->  Agrego tabla para descuentos
--GO
	create table discount(
	id serial primary key,
	name varchar not null,
	description varchar,
	client_id  int NOT NULL,
	plain_value float8,
	percentage_value float8,
	constraint discount_unique unique (client_id, name),
	CONSTRAINT discount_client_fk FOREIGN KEY (client_id) REFERENCES client
);
--GO

-- actualizo nombre de columna de precio total de los pedidos
ALTER TABLE cart RENAME COLUMN total_price_customer TO customer_total_price;

-- agrego columna de descuento en los pedidos
alter table cart add discount_id int;
alter table cart add constraint cart_discount_fk foreign key (discount_id) references discount;