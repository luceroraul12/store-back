package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.*;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.services.ActualizacionPorWebScrappingServicio;
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
    ActualizacionPorDocumentoServicio actualizacionPorDocumentoServicio;

    @Autowired
    ActualizacionPorWebScrappingServicio actualizacionPorWebScrappingServicio;

    @Autowired
    UnionRepository<ProductoEspecifico> unionRepository;




    /**
     * Permite actualizar los productos de las distribuidoras que dependan de excel. Actualiza una Distribuidora a la vez
     * @param documento Debe contener todas sus partes.
     * @see PeticionFrontEndDocumento
     * @throws IOException
     */
    @PostMapping("excel")
    public void actualizacionPorExcel(PeticionFrontEndDocumento documento) throws IOException {
        actualizacionPorDocumentoServicio.recibirDocumento(documento);
    }

    /**
     * Permite actualizar los productos de las distribuidoras que dependan de Web Scrapping. Actualiza todas las distribuidoras de este tipo al mismo tiempo.
     */
    @GetMapping("web-scrapping")
    public void actualizacionPorWebScrapping(){
        this.actualizacionPorWebScrappingServicio.actualizarTodasLasDistribuidoras();
    }

    //TODO: este metodo de momento va aquedar fijo aca, pero en un futuro habria que crear un servicio o almacenar este dato en algun lado

    /**
     * Devuelve un map con el tipo de busqueda por distribuidora
     * @return map.excel dependen del excel, map.webScrapping dependen de Web Scrapping
     * map.webScrapping es un map de fecha Realizado y Distribuidora
     */
    @GetMapping
    public ResponseEntity<List<DatosDistribuidora>> obtenerTipoyEstadoDeDistribuidora(){
//        TODO: esto esta hecho por momentos, hay que sacar todo del controlador
        List<DatosDistribuidora> datosDistribuidoras = new ArrayList<>();
        this.unionRepository.findAll().forEach(union -> {
            datosDistribuidoras.add(
                    DatosDistribuidora.builder()
                            .distribuidora(union.getDistribuidora())
                            .tipo(union.getTipoDistribuidora())
                            .fechaActualizacion(union.getFechaScrap())
                            .build()
            );
        });


        return new ResponseEntity<>(datosDistribuidoras,HttpStatus.OK);
    }

}
