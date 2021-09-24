package com.quasar.request;

import java.io.Serializable;
import java.util.List;

/**
 * Objeto de entrada para los satelites
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public class SatelliteRequest implements Serializable {

    private static final long serialVersionUID = -6753006293775657199L;

    private String name;

    private double distance;

    private List<String> message;

    public SatelliteRequest() {
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Satellite [distance=" + distance + ", name=" + name + ", message=" + message + "]";
    }

}
