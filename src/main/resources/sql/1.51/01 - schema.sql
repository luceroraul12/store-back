-- Agrego lookup tipo para diferenciar categorías de producto de las de gasto
insert into lookup_tipo(codigo, descripcion) values ('CATEGORY_TYPE', 'Tipo de categoría');

insert into lookup_valor(lookup_tipo_id, codigo, descripcion)
select lt.id, v.codigo, v.descripcion
from lookup_tipo lt
cross join (values ('PRODUCT', 'Producto'), ('EXPENSE', 'Gasto')) as v(codigo, descripcion)
where lt.codigo = 'CATEGORY_TYPE';

-- Agrego columna de tipo de categoría
alter table category add lv_category_type_id int;

-- Migración: las categorías existentes son de PRODUCTO
update category set lv_category_type_id = (
    select lv.id from lookup_valor lv
    inner join lookup_tipo lt on lt.id = lv.lookup_tipo_id
    where lt.codigo = 'CATEGORY_TYPE' and lv.codigo = 'PRODUCT'
) where lv_category_type_id is null;

alter table category alter column lv_category_type_id set not null;
alter table category add constraint category_lv_type_fk foreign key (lv_category_type_id) references lookup_valor(id);

-- Unicidad por nombre, cliente y tipo
alter table category drop constraint category_uq;
alter table category add constraint category_uq unique (name, client_id, lv_category_type_id);

-- Tabla de gastos del negocio
create table expense(
	id serial primary key,
	creation_date timestamp not null default now(),
	client_id int not null,
	amount float8 not null,
	category_id int not null,
	payment_method_id int not null,
	observation varchar(1000),
	constraint expense_client_fk foreign key (client_id) references client(id),
	constraint expense_category_fk foreign key (category_id) references category(id),
	constraint expense_payment_method_fk foreign key (payment_method_id) references lookup_valor(id)
);
