package distribuidora.scrapping.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.BasePriceDto;
import distribuidora.scrapping.entities.LookupValor;
import distribuidora.scrapping.entities.ProductoInterno;

@Component
public class CalculatorUtil {

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

	public List<BasePriceDto> getBasePriceList(ProductoInterno p, List<LookupValor> units) {
		List<BasePriceDto> result = new ArrayList<BasePriceDto>();
		for (LookupValor u : units) {
			result.add(getBasePrice(p, u));
		}
		return result;
	}

	private BasePriceDto getBasePrice(ProductoInterno p, LookupValor unit) {
		DecimalFormat df = new DecimalFormat("#.##"); // Define el formato con 2 decimales
		BasePriceDto dto = new BasePriceDto();
		Double relation = Double.parseDouble(calculateCustomerPrice(p).toString());
		Double priceLabel = relation * Double.parseDouble(unit.getValor());
		String label = String.format("[%s] %s", unit.getDescripcion(), df.format(priceLabel));
		dto.setLabel(label);
		dto.setLabelPrice(Double.valueOf(df.format(priceLabel)));
		dto.setRelation(relation);
		return dto;
	}
}
