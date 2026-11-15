-- Modulo de gastos
insert into lookup_valor (lookup_tipo_id, codigo, descripcion)
values ((select id from lookup_tipo lt where codigo = 'MODULE_TYPE'), 'MODULE_TYPE_EXPENSE', 'Gastos');

insert into client_module (client_id, lv_module_id) values
((select id from client where name = 'NATIVO'),(select id from lookup_valor where codigo = 'MODULE_TYPE_EXPENSE')),
((select id from client where name = 'HOMITOWEN-TEST'),(select id from lookup_valor where codigo = 'MODULE_TYPE_EXPENSE'));
