package distribuidora.scrapping.util.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import distribuidora.scrapping.dto.DatosDistribuidoraDto;
import distribuidora.scrapping.entities.DatosDistribuidora;

@Component
public class DatosDistribuidoraConverter
		extends
			Converter<DatosDistribuidora, DatosDistribuidoraDto> {

	@Autowired
	private LookupValueDtoConverter lookupValueDtoConverter;

	@Override
	public DatosDistribuidoraDto toDto(DatosDistribuidora entidad) {
		DatosDistribuidoraDto dto = new DatosDistribuidoraDto();
		dto.setId(entidad.getId());
		dto.setDistribuidora(
				lookupValueDtoConverter.toDto(entidad.getDistribuidora()));
		dto.setExcel(entidad.isExcel());
		dto.setWeb(entidad.isWeb());
		dto.setDateLastUpdate(entidad.getFechaActualizacion());
		dto.setSize(entidad.getCantidadDeProductosAlmacenados());
		return dto;
	}

	@Override
	public DatosDistribuidora toEntidad(DatosDistribuidoraDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
