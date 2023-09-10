package distribuidora.scrapping.controller;

import distribuidora.scrapping.dto.LookupValueDto;
import distribuidora.scrapping.services.general.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lookup/")
public class LookupController {
    @Autowired
    LookupService lookupService;

    @GetMapping
    public List<LookupValueDto> getLookupValoresByTipo(@RequestParam String code){
        return lookupService.getLookupValueDtoListByLookupTypeCode(code);
    }
}
