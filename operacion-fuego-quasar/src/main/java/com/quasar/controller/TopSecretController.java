package com.quasar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.quasar.exception.ServiceException;
import com.quasar.request.MessageRequest;
import com.quasar.request.SatelliteRequest;
import com.quasar.response.MessageResponse;
import com.quasar.services.QuasarService;

/**
 * Clase que expone los endpoint REST de entrada
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
@RestController
public class TopSecretController {

    private static Logger log = LoggerFactory.getLogger(TopSecretController.class);

    @Autowired
    QuasarService quasarService;

    /**
     * Endpoint tipo POST que permite obtener la ubicación de la nave y el mensaje
     * decodificado
     * 
     * @param messageRequest Listado de satelites con su nombre, distancia y
     *                       fragmento de mensaje
     * @return MessageResponse Objeto json con la posicion de la nave y el mensaje
     *         decodificado
     */
    @PostMapping("/topsecret")
    public MessageResponse topSecret(@RequestBody MessageRequest messageRequest) {
        log.debug("Entra a endpoint /topsecret");
        try {
            return quasarService.topSecret(messageRequest.getSatellites());
        } catch (ServiceException ex) {
            log.error("Ocurrio un error: ", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    /**
     * Endpoint tipo POST que permite guardar la informacion de un satelite en
     * especifico
     * 
     * @param satelliteName    nombre del satelite
     * @param satelliteRequest distancia y fragmento de mensaje del satelite
     */
    @PostMapping(path = "/topsecret_split/{satellite_name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void getTopSecretSplit(@PathVariable("satellite_name") String satelliteName,
            @RequestBody SatelliteRequest satelliteRequest) {
        log.debug("Entra a endpoint /topsecret_split/{satellite_name}");
        quasarService.save(satelliteName, satelliteRequest);
    }

    /**
     * Endpoint tipo REST que permite obtener la ubicación de la nave y el mensaje
     * decodificado
     * 
     * @return Objeto json con la posicion de la nave y el mensaje decodificado
     */
    @GetMapping(path = "/topsecret_split")
    public MessageResponse getTopSecretSplit() {
        log.debug("Entra a endpoint /topsecret_split");
        try {
            return quasarService.topSecretSplit();
        } catch (ServiceException ex) {
            log.error("Ocurrio un error: ", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

}
