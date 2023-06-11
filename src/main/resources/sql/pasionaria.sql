--agrego productos de categoria cereales
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'CEREALES'), 'Almohaditas Rellenas', 'Avellanas/Limon/Frutilla'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Chocolate', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Quinoa pop', 'Con algarrobo'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Copos', 'con azucar'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Copos', 'sin azucar'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Fibra de salvado', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Aritos', 'Frutales'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Aritos', 'Miel'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Trigo inflado', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Chocobolas', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Quinoa pop', 'Simple'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Granola', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Tutucas', '');

--agrego productos de categoria semillas
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Chia', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Zapallo', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Sesamo', 'Negro'),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Cardo Mariano', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Sesamo', 'Integral'),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Sesamo', 'Blanco'),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Mix', 'Semillas'),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Amaranto', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Fenogreco', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Girasol', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Hinojo', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Eneldo', ''),
	((select id from lookup_valor where codigo = 'SEMILLAS'), 'Lino', '');

--agrego productos de categoria legumbres
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Lenteja', ''),
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Garbanzo', ''),
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Porotos', 'Mung'),
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Porotos', 'Negros'),
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Porotos', 'Alubia'),
	((select id from lookup_valor where codigo = 'LEGUMBRES'), 'Arvejas', '');

--agrego productos de categoria especies
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Paprika', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Romero', 'Organico'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Pimienta', 'Negra molida'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Adobo', 'Para pizza'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Provenzal', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Kumel', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Comino', 'Molido'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Pimenton', 'Dulce'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Ajo', 'En polvo'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Chimichurri', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Curry', 'Suave'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Tomillo', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Albhaca', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Curcuma', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Jengibre', 'En polvo'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Nuez moscada', 'Molida'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Cebolla', 'En escamas'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Cebolla', 'En polvo'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Pimenton', 'Ahumado'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Aji', 'Molido'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Curry', 'Picante'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Oregano', ''),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Canela', 'Molida'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Pimenton', 'Espaniol'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Mostaza', 'Molida'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Canela', 'En rama'),
	((select id from lookup_valor where codigo = 'ESPECIES'), 'Ajo', 'En escamas');

--agrego productos de categoria perfumeria
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'PERFUMERIA'), 'Agua Florida', 'Memorias del agua');

--agrego productos de categoria complementos ecologicos
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'COMPLEMENTOS_ECOLOGICOS'), 'Cepillo de dientes', 'De Bamboo'),
	((select id from lookup_valor where codigo = 'COMPLEMENTOS_ECOLOGICOS'), 'Peine', 'De madera'),
	((select id from lookup_valor where codigo = 'COMPLEMENTOS_ECOLOGICOS'), 'Esponja', 'Lufa');

--agrego productos de categoria complementos diarios
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Balsamo', 'Labial'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Gel', 'Arnica'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Desodorante', 'Natural'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Repelente', 'Natural'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Manteca', 'De Karite'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Agua rosada', 'Pura'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Shampoo', 'Solido'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Acondicionador', 'Solido'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Tonico', 'Revitalizante'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Jabon', 'De Coco'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Splash', 'Aurico'),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Polvo dental', ''),
	((select id from lookup_valor where codigo = 'COMPLEMENTO_DIARIA'), 'Bifasico', '');

--agrego productos de categoria pomadas medicinales
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Jarilla y Romero', ''),
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Calendula y Manzanilla', ''),
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Enebro', ''),
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Llanten y Contrayerba', ''),
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Eucaliptus y tomillo', ''),
	((select id from lookup_valor where codigo = 'POMADAS_MEDICINALES'), 'Lavando y Romero', '');

--agrego productos de categoria reutilizables
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'REUTILIZABLES'), 'Protectores de tela', 'x3'),
	((select id from lookup_valor where codigo = 'REUTILIZABLES'), 'Toallas de tela', 'x3'),
	((select id from lookup_valor where codigo = 'REUTILIZABLES'), 'Pad reutilizable', 'x2');

--agrego productos de categoria congelados
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'CONGELADOS'), 'Hamburgesas Veganas', 'Garbanzo, espinaca, salasa blanca'),
	((select id from lookup_valor where codigo = 'CONGELADOS'), 'Hamburgesas Veganas', 'Soja, morron, chia, queso'),
	((select id from lookup_valor where codigo = 'CONGELADOS'), 'Hamburgesas Veganas', 'Lenteja, morron, zanahoria, chia, queso'),
	((select id from lookup_valor where codigo = 'CONGELADOS'), 'Quinoa', 'Zapallo, morron, choclo, chia');

--agrego productos de categoria leches reposteria
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Azucar', 'impalpable'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Bicarbonato de sodio', 'impalpable'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Agua de azhar', 'x30cc'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Cacao', 'Amargo (Indias)'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Cobertura de chocolate', 'Negro'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Extracto de Vainilla', '100cc'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Polvo para hornear', ''),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Gelatina', 'sin sabor'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Fruta abrillantada', ''),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'Coco', 'rallado'),
	((select id from lookup_valor where codigo = 'REPOSTERIA'), 'rocklets', '');

--agrego productos de categoria vinos organicos
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'VINOS_ORGANICOS'), 'Nietas de Lipari: Rosado dulce natural', 'x750cc'),
	((select id from lookup_valor where codigo = 'VINOS_ORGANICOS'), 'Nietas de Lipari: Tinto malbec seleccion', 'x750cc'),
	((select id from lookup_valor where codigo = 'VINOS_ORGANICOS'), 'Nietas de Lipari: Malbec botellon', '1.5L');

--agrego productos de categoria galleteria sin tacc
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'GALLETERIA_SIN_TACC'), 'Tostadas de arroz "Dos Hermanos"', 'Dulces/Saladas/Sin sal x120gr'),
	((select id from lookup_valor where codigo = 'GALLETERIA_SIN_TACC'), 'Tostadas de arroz "Lulemuu"', 'Clasicas x120gr'),
	((select id from lookup_valor where codigo = 'GALLETERIA_SIN_TACC'), 'Galletas de arroz "Dos Hermanos"', 'x110gr'),
	((select id from lookup_valor where codigo = 'GALLETERIA_SIN_TACC'), 'Tostadas de arroz "Santa Maria"', 'Limon/Chocolate/Scones/Marmoladas/Coco x220gr');

--agrego productos de categoria hierbas medicinales
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cascarrilla de cacao', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Yerba del pollo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Angelica raiz', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Bardana', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Origa', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Sen', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Poleo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Eneldo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Te rojo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Te verde Bancha', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cascara sagrada', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cascara de granada', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Eucalipto Mentol', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Pulmonaria', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Ambay', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Malva', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Jarilla', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Moringa', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Palo Azul', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Llanten', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Rud', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Hinojo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Munia Munia', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Lapacho Corteza', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Artemisa Annua', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Salvia Blanca', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Contrayerba', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Caussia', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Borraja', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Paico', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Menta', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Melisa', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cedron', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Te negro', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Pasionaria', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Tilo', 'Bulgaria'),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cardo Mariano', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Diente de leon', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Carqueja', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Boldo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Cola de caballo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Espina colorada', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Pezunia de vaca', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Rompepiedra', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Uva ursi', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Burro', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Matico', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Peperina', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Incayuyo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Ajenjo amargo', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Manzanilla Flor', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Te verde', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Regaliz', ''),
	((select id from lookup_valor where codigo = 'HIERBAS_MEDICINALES'), 'Stevia', '');

--agrego productos de categoria cereales segunda parte
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'CEREALES'), 'Mijo pelado', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Cebada perlada', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Quinoa Real Boliviana', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Maiz blanco pisado partido', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Trigo burgol', 'Fino/Grueso'),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Trigo Candeal', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Arroz Yamani', ''),
	((select id from lookup_valor where codigo = 'CEREALES'), 'Avena', '');

--agrego productos de categoria furtos secos
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Nuez light', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Pistachos', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Ciruelas', 'Sin carozo'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Almendras', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Bayas de goji', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Mix clasico', 'Pasas, mani, almendras, nuez'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Mix europeo', 'Almendras, nuez, mani, castanias'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Pasas morochas sultaninas', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Mix tropical', 'Pasas, mani, almendras, nuez, papaya, mango, anana, manzana, coco ...'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Banana disecada', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Castanias de caju', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Higos negros', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Mani', 'Salado'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Mani', 'Sin sal'),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Avellanas', ''),
	((select id from lookup_valor where codigo = 'FRUTOS_SECOS'), 'Datiles', '');

--agrego productos de categoria Harinas
insert into productos_internos (lv_categoria_id, nombre, descripcion) values
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de lino', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de coco', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de Algarrobo', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de mandioca', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de algarrobo', 'blanco'),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina integral organica "Brotes"', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de almendras', 'Sin piel'),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de garbanzos', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Fecula de mandioca', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de avena', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Harina de arroz', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Fecula de maiz', ''),
	((select id from lookup_valor where codigo = 'HARINAS'), 'Gluten puro', '');

--agrego productos de categoria otros == sin categoria
insert into productos_internos (nombre, descripcion) values
	('Miel pura', 'x1kg'),
	('Miel pura', 'x1/2kg'),
	('Levadura de cerveza', 'x100gr'),
	('Mix para ensaladas y sopas', 'x250gr'),
	('Aceite de oliva Libanti extravirgen', 'x1l'),
	('Aceite de coco neutro', 'x300cc'),
	('Aceite de oliva panochia extra virgen', 'x1/2l'),
	('Sal sin sodio', 'Natura/hierbas/4 pimientas x70gr'),
	('Pasta de mani', 'natural/ahumada/crocante/coco/cacao/stevia x370cc'),
	('Sal marina Macrozen', 'Fina x1/2kg'),
	('Jugo de arandanos', 'Con chia y stevia x1.5l'),
	('Sal marina Macrozen', 'Gruesa x1/2kg'),
	('Yerba organica Legado', 'x1/2kg'),
	('Yerba organica roapipo', 'x1/2kg'),
	('Aceitunas Nadescar', 'x100gr'),
	('Sal de l Himalaya', 'x100gr'),
	('Stevia en polvo', 'x100gr'),
	('Stevia Liquida', 'x100gr'),
	('Maca', 'x100gr');
