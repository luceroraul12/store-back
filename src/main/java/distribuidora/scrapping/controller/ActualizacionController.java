package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.PeticionFrontEndDocumento;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.services.excel.ActualizacionPorDocumentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Tiene la finalidad de realizar la actualizacion de datos por distribuidora como tambien el estado de cada una.
 * @see ActualizacionPorDocumentoServicio
 */
@RestController
@RequestMapping(value = "actualizar")
public class ActualizacionController {

    @Autowired
    ActualizacionPorDocumentoServicio actualizacionDeDocumentoServicio;


    /**
     * Permite actualizar los productos de las distribuidoras que dependan de excel. Actualiza una Distribuidora a la vez
     * @param documento Debe contener todas sus partes.
     * @see PeticionFrontEndDocumento
     * @throws IOException
     */
    @PostMapping("excel")
    public void actualizacionPorExcel(PeticionFrontEndDocumento documento) throws IOException {
        actualizacionDeDocumentoServicio.recibirDocumento(documento);
    }

    /**
     * Permite actualizar los productos de las distribuidoras que dependan de Web Scrapping. Actualiza todas las distribuidoras de este tipo al mismo tiempo.
     */
    @PostMapping("web-scrapping")
    public void actualizacionPorWebScrapping(){

    }

    //TODO: este metodo de momento va aquedar fijo aca, pero en un futuro habria que crear un servicio o almacenar este dato en algun lado

    /**
     * Devuelve un map con el tipo de busqueda por distribuidora
     * @return map.excel dependen del excel, map.webScrapping dependen de Web Scrapping
     */
    @GetMapping
    public ResponseEntity<Map<String, List<Distribuidora>>> obtenerTipoDeActualizacionPorDistribuidora(){
        Map<String, List<Distribuidora>> distribuidorasDisponibles = new HashMap<>();
        distribuidorasDisponibles.put("excel",
                Arrays.asList(
                        Distribuidora.FACUNDO,
                        Distribuidora.INDIAS)
                );
        distribuidorasDisponibles.put("webScrapping",
                Arrays.asList(
                        Distribuidora.DON_GASPAR,
                        Distribuidora.LA_GRANJA_DEL_CENTRO,
                        Distribuidora.MELAR,
                        Distribuidora.SUDAMERIK)
                );
        return new ResponseEntity<>(distribuidorasDisponibles,HttpStatus.OK);
    }
}
