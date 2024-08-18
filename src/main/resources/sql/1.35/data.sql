-- Creo la tienda de prueba
insert into
	client ("name",
	address,
	address_link,
	phone,
	phone_link,
	instagram,
	instagram_link,
	facebook,
	facebook_link,
	filename_logo)
values(
	'HOMITOWEN-TEST',
	'Av. 25 de mayo 1018',
	'https://goo.gl/maps/4K6m4uivZY6CHYeH7',
	'+542657678661',
	'https://api.whatsapp.com/send/?phone=542657678661&text&type=phone_number&app_absent=0',
	'luceroraul12',
	'https://www.instagram.com/luceroraul12',
	'Lucero Raul',
	'https://www.facebook.com/Totorolife/',
	'homitowen-logo.png'
);

-- Asocio el usuario a la tienda de prueba
update client_has_users  r
	set client_id = (select id from client where name = 'HOMITOWEN-TEST')
where user_id = (select id from usuarios where email = 'luceroraul12@gmail.com')



