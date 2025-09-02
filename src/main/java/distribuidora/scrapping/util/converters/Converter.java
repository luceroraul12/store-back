package distribuidora.scrapping.util.converters;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Converter<Entity, Dto> {

	public abstract Dto toDto(Entity e);
	public abstract Entity toEntidad(Dto d);



	public List<Dto> toDtoList(List<Entity> entidadList){
		return entidadList.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	public List<Entity> toEntidadList(List<Dto> dtoList){
		return dtoList.stream()
				.map(this::toEntidad)
				.collect(Collectors.toList());
	}
}
