package com.quasar.services;

import java.util.List;

import com.quasar.request.SatelliteRequest;
import com.quasar.response.MessageResponse;


/**
 * Intereface que expone los servicios con la logica de negocio del aplicativo
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public interface QuasarService {

    /**
     * Permite obtener la ubicación de la nave y el mensaje decodificado
     * 
     * @param lstSatellite Listado de satelites con su nombre, distancia y fragmento de mensaje
     * @return Objeto con la posicion de la nave y el mensaje decodificado
     * 
     */
    MessageResponse topSecret(List<SatelliteRequest> lstSatellite);
    
    /**
     * Permite guardar la informacion de un satelite en especifico
     * 
     * 
     * @param name nombre del satelite
     * @param satelliteRequest distancia y fragmento de mensaje del satelite
     */
    void save(String name, SatelliteRequest satelliteRequest);
    
    /**
     * Permite obtener la ubicación de la nave y el mensaje decodificado
     * 
     * @return Objeto con la posicion de la nave y el mensaje decodificado
     */
    MessageResponse topSecretSplit();
    
    
}
