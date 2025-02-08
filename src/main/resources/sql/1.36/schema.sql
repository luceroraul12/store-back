-- Creo tabla person
create table person(
	id serial primary key,
	name varchar not null,
	phone varchar not null,
	email varchar,
	CONSTRAINT person_uq_1 UNIQUE (name, phone)
);

alter table cart add total_price float8 not null;
ALTER TABLE public.cart_product ALTER COLUMN price TYPE float8 USING price::float8;
