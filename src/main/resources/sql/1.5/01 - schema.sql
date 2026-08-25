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
	constraint supplier_balance_supplier_fk foreign key (supplier_id) references supplier(id),
	constraint supplier_balance_type_fk foreign key (balance_type_id) references lookup_valor(id),
	constraint supplier_balance_amount_ck check (amount > 0)
);

insert into lookup_tipo(codigo, descripcion)
values ('SUPPLIER_BALANCE_TYPE', 'Supplier balance type');

insert into lookup_valor (lookup_tipo_id, codigo, descripcion)
values
	((select id from lookup_tipo where codigo = 'SUPPLIER_BALANCE_TYPE'), 'STORE_CREDIT', 'TIENDA'),
	((select id from lookup_tipo where codigo = 'SUPPLIER_BALANCE_TYPE'), 'SUPPLIER_CREDIT', 'DISTRIBUIDORA');

insert into lookup_valor (lookup_tipo_id, codigo, descripcion)
values ((select id from lookup_tipo where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_SUPPLIER', 'Suppliers');

insert into client_module (client_id, lv_module_id)
select c.id, lv.id
from client c
cross join lookup_valor lv
where lv.codigo = 'MODULE_TYPE_SUPPLIER'
	and not exists (
		select 1
		from client_module cm
		where cm.client_id = c.id and cm.lv_module_id = lv.id
	);
