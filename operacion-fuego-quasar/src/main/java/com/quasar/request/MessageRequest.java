package com.quasar.request;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto de entrada con el listado de los satelites
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public class MessageRequest implements Serializable {

    private static final long serialVersionUID = -411603711886531094L;

    private List<SatelliteRequest> satellites;

    public List<SatelliteRequest> getSatellites() {
        return satellites;
    }

    public void setSatellites(List<SatelliteRequest> satellites) {
        this.satellites = satellites;
    }

    @Override
    public String toString() {
        return "MessageRequest [satellites=" + satellites + "]";
    }

}
