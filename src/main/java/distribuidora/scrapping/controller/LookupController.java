package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.LookupValor;
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
    public List<LookupValor> getLookupValoresByTipo(@RequestParam String codigoTipo){
        return lookupService.getLookupValoresPorLookupTipoCodigo(codigoTipo);
    }
}
