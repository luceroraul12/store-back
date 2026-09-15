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

insert into lookup_tipo(codigo, descripcion)
values ('PAYMENT_METHOD', 'Forma de pago');

insert into lookup_valor (lookup_tipo_id, codigo, descripcion)
values
	((select id from lookup_tipo where codigo = 'PAYMENT_METHOD'), 'CASH', 'EFECTIVO'),
	((select id from lookup_tipo where codigo = 'PAYMENT_METHOD'), 'TRANSFER', 'TRANSFERENCIA');
