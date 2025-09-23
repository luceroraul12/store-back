package distribuidora.scrapping.util.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public abstract class Converter<Entity, Dto> {

	public abstract Dto toDto(Entity e);

	public abstract Entity toEntidad(Dto d);

	public List<Dto> toDtoList(List<Entity> entidadList) {
		return entidadList.stream().map(this::toDto).collect(Collectors.toList());
	}

	public Page<Dto> toPage(Page<Entity> entityPage) {
		List<Entity> sourceList = entityPage.getContent();

		// Usamos el método de conversión abstracto para transformar cada elemento.
		List<Dto> targetList = sourceList.stream().map(this::toDto).collect(Collectors.toList());

		// Obtenemos los metadatos de la página original.
		Pageable pageable = entityPage.getPageable();
		long totalElements = entityPage.getTotalElements();

		// Creamos una nueva instancia de PageImpl con los datos y metadatos
		// convertidos.
		return new PageImpl<>(targetList, pageable, totalElements);
	}

	public List<Entity> toEntidadList(List<Dto> dtoList) {
		return dtoList.stream().map(this::toEntidad).collect(Collectors.toList());
	}
}
