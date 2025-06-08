alter table cart_product  drop column lv_unit_id;

-- elimino tabla producto_interno_status
DROP TABLE productos_internos_status CASCADE;


-- elimino trigger
DROP TRIGGER productos_internos_delete_status_tr ON public.productos_internos;
DROP TRIGGER productos_internos_new_status_tr ON public.productos_internos;

DROP FUNCTION public.productos_internos_delete_status_fn();
DROP FUNCTION public.productos_internos_new_status_fn();
