-- genero columnas para el carrito
alter table cart add column discount float8 default 0;
alter table cart add column total_price_customer float8 default 0;
-- actualizo los carritos existentes con zero descuento y mismo precio final
update cart set total_price_customer = total_price;