alter table productos_internos add column status varchar;

update productos_internos set status = 'ACTIVE'