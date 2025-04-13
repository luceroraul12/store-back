
-- Triger al momento de crear un producto
CREATE OR REPLACE FUNCTION public.productos_internos_new_status_fn()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
begin 
	insert into productos_internos_status (producto_interno_id, has_stock, amount) 
		values (new.id, TRUE, 0);
	return new;
end
$function$
;