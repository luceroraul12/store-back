package distribuidora.scrapping.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.BasePriceDto;
import distribuidora.scrapping.entities.Category;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;
import distribuidora.scrapping.entities.Unit;
import distribuidora.scrapping.services.UnitDtoConverter;
import distribuidora.scrapping.util.converters.LookupValueDtoConverter;

@Component
public class CalculatorUtil {
	
	@Autowired
	private UnitDtoConverter unitDtoConverter;

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
		Category category = p.getCategory();
		// Calculo para unidad principal
		result.add(getBasePrice(p, category.getUnit()));
		// Calculo para unidad padre en caso de tenerlo
		// TODO: Revisar flag pdf
		if(category.hasUnitParent() && category.getUnit().getPdfShowChild())
			result.add(getBasePrice(p, category.getUnitParent()));
		return result;
	}

	private BasePriceDto getBasePrice(ProductoInterno p, Unit unit) {
		DecimalFormat df = new DecimalFormat("#.##"); // Define el formato con 2 decimales
		BasePriceDto dto = new BasePriceDto();
		Double price = Double.parseDouble(calculateCustomerPrice(p).toString());
		Double relation = price * unit.getRelation();
		String label = String.format("[%s] %s", unit.getName(), df.format(relation));
		dto.setLabel(label);
		dto.setRelation(relation);
		dto.setUnit(unitDtoConverter.toDto(unit));
		return dto;
	}
}
