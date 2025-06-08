package distribuidora.scrapping.util.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Converter<Entidad, Dto> {

	public abstract Dto toDto(Entidad e);
	public abstract Entidad toEntidad(Dto d);



	public List<Dto> toDtoList(List<Entidad> entidadList){
		return entidadList.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	public List<Entidad> toEntidadList(List<Dto> dtoList){
		return dtoList.stream()
				.map(this::toEntidad)
				.collect(Collectors.toList());
	}
}
