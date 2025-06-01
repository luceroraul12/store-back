package distribuidora.scrapping.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.BasePriceDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.Presentation;
import distribuidora.scrapping.services.PresentationDtoConverter;

@Component
public class CalculatorUtil {
	
	@Autowired
	private PresentationDtoConverter presentationDtoConverter;

	public Integer calculateCustomerPrice(ProductoInterno p) {
		int result;
		double precio = p.getPrecio() != null ? p.getPrecio() : 0;
		double transporte = p.getPrecioTransporte() != null ? p.getPrecioTransporte() : 0.0;
		double empaquetado = p.getPrecioEmpaquetado() != null ? p.getPrecioEmpaquetado() : 0.0;
		double ganancia = (100 + (p.getPorcentajeGanancia() != null ? p.getPorcentajeGanancia() : 0)) / 100;
		double impuesto = (100 + (p.getPorcentajeImpuesto() != null ? p.getPorcentajeImpuesto() : 0)) / 100;
		double regulador = p.getRegulador() != null && p.getRegulador() != 0.0 ? p.getRegulador() : 1;
		double precioPorcentual = (precio * ganancia * impuesto) / regulador;
		result = (int) (precioPorcentual + transporte + empaquetado + ganancia);
		return result;
	}

	public List<BasePriceDto> getBasePriceList(ProductoInterno p) {
		List<BasePriceDto> result = new ArrayList<BasePriceDto>();
		// Calculo para unidad principal
		result.add(getBasePrice(p));
		return result;
	}

	private BasePriceDto getBasePrice(ProductoInterno p) {
		Presentation presentation = p.getPresentation();
		DecimalFormat df = new DecimalFormat("#.##"); // Define el formato con 2 decimales
		BasePriceDto dto = new BasePriceDto();
		Double price = Double.parseDouble(calculateCustomerPrice(p).toString());
		Double relation = price * presentation.getRelation();
		String label = String.format("[%s] %s", presentation.getName(), df.format(relation));
		dto.setLabel(label);
		dto.setPrice(relation);
		dto.setPresentation(presentationDtoConverter.toDto(presentation));
		return dto;
	}
}
