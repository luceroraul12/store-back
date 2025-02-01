-- Creo tabla person
create table cart(
	id serial primary key,
	name varchar not null,
	phone varchar not null,
	email varchar,
	CONSTRAINT person_uq UNIQUE (name, phone)
);

