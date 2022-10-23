package distribuidora.scrapping.controller;

import distribuidora.scrapping.entities.*;
import distribuidora.scrapping.enums.Distribuidora;
import distribuidora.scrapping.repositories.DatosDistribuidoraRepository;
import distribuidora.scrapping.repositories.UnionRepository;
import distribuidora.scrapping.services.ActualizacionPorWebScrappingServicio;
import distribuidora.scrapping.services.excel.ActualizacionPorDocumentoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    DatosDistribuidoraRepository datosDistribuidoraRepository;

    @Autowired
    UnionRepository<ProductoEspecifico> unionRepository;




    /**
     * Permite actualizar los productos de las distribuidoras que dependan de excel. Actualiza una Distribuidora a la vez
     * @param documento Debe contener todas sus partes.
     * @see PeticionExcel
     * @throws IOException
     */
    @PostMapping("excel")
    public void actualizacionPorExcel(PeticionExcel documento) throws IOException {
        actualizacionPorDocumentoServicio.recibirDocumento(documento);
    }

    /**
     * Permite actualizar los productos de las distribuidoras que dependan de Web Scrapping. Actualiza todas las distribuidoras de este tipo al mismo tiempo.
     */
    @GetMapping("web-scrapping")
    public void actualizacionPorWebScrappingTodos(){
        this.actualizacionPorWebScrappingServicio.actualizarTodasLasDistribuidoras();
    }

    @PostMapping("web-scrapping/individual")
    public ResponseEntity<DatosDistribuidora> actualizacionPorWebScrappingIndividual
            (@RequestParam Distribuidora distribuidora){
        this.actualizacionPorWebScrappingServicio.actualizarPorDistribuidora(distribuidora);
        UnionEntidad<ProductoEspecifico> union = unionRepository.findByDistribuidora(distribuidora);
        return new ResponseEntity<>(DatosDistribuidora.builder()
                .fechaActualizacion(union.getFechaScrap().toString())
                .cantidadDeProductosAlmacenados(union.getCantidadDeProductosAlmacenados())
                .distribuidora(union.getDistribuidora())
                .tipo(union.getTipoDistribuidora())
                .build(),HttpStatus.OK);
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
        List<DatosDistribuidora> datosDistribuidoras = this.datosDistribuidoraRepository.findAll();
        return new ResponseEntity<>(datosDistribuidoras,HttpStatus.OK);
    }

}
