package distribuidora.scrapping.services.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import distribuidora.scrapping.entities.ProductoInterno;

@SpringBootTest
class PdfServiceImplTest {

    @Autowired
    PdfService service;
    
    // Test calculo de precios
    @Test
    void precioNormal() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(1209, result);
    }
    
    @Test
    void precioConReguladorCero() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setRegulador(0.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(1209, result);
    }
    
    @Test
    void precioConReguladorEntero() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setRegulador(2.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(755, result);
    }
    
    @Test
    void precioConReguladorDecimal() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	p.setRegulador(1.5);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(906, result);
    }
    
    @Test
    void precioSinPrecio() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(301, result);
    }
    
    @Test
    void precioSinGanancia() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(906, result);
    }

    @Test
    void precioSinImpuesto() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(1051, result);
    }

    @Test
    void precioSinEmpaquetado() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioTransporte(100.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(1009, result);
    }

    @Test
    void precioSinTransporte() {
    	ProductoInterno p =  new ProductoInterno();
    	p.setPrecio(500.0);
    	p.setPrecioEmpaquetado(200.0);
    	p.setPorcentajeGanancia(50.0);
    	p.setPorcentajeImpuesto(21.0);
    	
    	int result = service.generateBasePrice(p);
    	assertEquals(1109, result);
    }



}