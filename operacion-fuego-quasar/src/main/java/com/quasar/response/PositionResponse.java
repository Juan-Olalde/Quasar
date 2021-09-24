package com.quasar.response;

import java.io.Serializable;

/**
 * Objeto de respuesta con la posicion nave
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public class PositionResponse implements Serializable {

    private static final long serialVersionUID = 1074446276299009250L;

    private double x;

    private double y;

    public PositionResponse() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + "]";
    }

}
