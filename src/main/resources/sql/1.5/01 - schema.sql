-- Suppliers are managed independently from product distributor lookups.
create table supplier(
	id serial primary key,
	name varchar not null,
	phone varchar,
	email varchar,
	observations varchar,
	client_id int not null,
	constraint supplier_client_fk foreign key (client_id) references client(id),
	constraint supplier_name_uq unique (client_id, name)
);

create table supplier_balance(
	id serial primary key,
	supplier_id int not null,
	balance_type_id int not null,
	amount float8 not null,
	created_at timestamp not null default now(),
	updated_at timestamp not null default now(),
	cart_id int,
	constraint supplier_balance_cart_fk foreign key (cart_id) references cart(id),
	constraint supplier_balance_supplier_fk foreign key (supplier_id) references supplier(id),
	constraint supplier_balance_type_fk foreign key (balance_type_id) references lookup_valor(id),
	constraint supplier_balance_amount_ck check (amount > 0)
);

alter table cart add supplier_id int;
alter table cart add constraint cart_supplier_fk foreign key (supplier_id) references supplier(id);

-- Payment methods per cart. The customerTotalPrice is split into one or more payments,
-- each one using a payment method lookup (eg. CASH/EFECTIVO, TRANSFER/TRANSFERENCIA).
create table cart_payment(
	id serial primary key,
	cart_id int not null,
	lv_payment_method_id int not null,
	amount float8 not null,
	constraint cart_payment_cart_fk foreign key (cart_id) references cart(id),
	constraint cart_payment_method_fk foreign key (lv_payment_method_id) references lookup_valor(id),
	constraint cart_payment_amount_ck check (amount > 0)
);
