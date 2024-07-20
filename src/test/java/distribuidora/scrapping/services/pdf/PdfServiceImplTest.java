package distribuidora.scrapping.services.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import distribuidora.scrapping.entities.ProductoInterno;

class PdfServiceImplTest {

	PdfService service = new PdfServiceImpl();

	// Test sobre calculo de precios con los parametros del producto y sin
	// redondeos

	@Test
	void precioNormal() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 1209 redondeado 1250
		assertEquals(1209, result);
	}

	@Test
	void precioConReguladorCero() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setRegulador(0.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 1209 redondeado 1250
		assertEquals(1209, result);
	}

	@Test
	void precioConReguladorEntero() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setRegulador(2.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 755 redondeado 800
		assertEquals(755, result);
	}

	@Test
	void precioConReguladorDecimal() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);
		p.setRegulador(1.5);

		int result = service.generateBasePrice(p);
		// 906 redondeado 950
		assertEquals(906, result);
	}

	@Test
	void precioSinPrecio() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 301 redondeado 350
		assertEquals(301, result);
	}

	@Test
	void precioSinGanancia() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 906 redondeado 950
		assertEquals(906, result);
	}

	@Test
	void precioSinImpuesto() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);

		int result = service.generateBasePrice(p);
		// 1051 redondeado 1100
		assertEquals(1051, result);
	}

	@Test
	void precioSinEmpaquetado() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioTransporte(100.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 1009 redondeado 1050
		assertEquals(1009, result);
	}

	@Test
	void precioSinTransporte() {
		ProductoInterno p = new ProductoInterno();
		p.setPrecio(500.0);
		p.setPrecioEmpaquetado(200.0);
		p.setPorcentajeGanancia(50.0);
		p.setPorcentajeImpuesto(21.0);

		int result = service.generateBasePrice(p);
		// 1109 se redondea 1150
		assertEquals(1109, result);
	}

	// Test sobre la forma de redondear valores finales

	@Test
	void roundRangeOneDefault() {
		int valor = 25;
		int result = service.round(valor);
		assertEquals(50, result);
	}

	@Test
	void roundRangeOneCommon() {
		int valor = 1215;
		int result = service.round(valor);
		assertEquals(1200, result);
	}

	@Test
	void roundRangeTwoLess() {
		int valor = 1342;
		int result = service.round(valor);
		assertEquals(1350, result);
	}
	
	@Test
	void roundRangeTwoLessProx() {
		int valor = 1335;
		int result = service.round(valor);
		assertEquals(1350, result);
	}

	@Test
	void roundRangeTwoHigh() {
		int valor = 1760;
		int result = service.round(valor);
		assertEquals(1750, result);
	}
	
	@Test
	void roundRangeTwoHighProx() {
		int valor = 1778;
		int result = service.round(valor);
		assertEquals(1750, result);
	}

	@Test
	void roundRangeThree() {
		int valor = 1499;
		int result = service.round(valor);
		assertEquals(1500, result);
	}

	@Test
	void roundRangeThreeExact() {
		int valor = 300;
		int result = service.round(valor);
		assertEquals(300, result);
	}
}