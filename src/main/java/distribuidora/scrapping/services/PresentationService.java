package distribuidora.scrapping.services;

import java.util.List;

import distribuidora.scrapping.dto.UnitDto;
import distribuidora.scrapping.entities.Unit;

public interface UnitService {

	Unit getById(Integer id) throws Exception;

	List<UnitDto> getUnits();
}
