package com.quasar.response;

import java.io.Serializable;

/**
 * Objeto de respuesta con la posicion y el mensaje de la nave
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 2568184976847698916L;

    private PositionResponse position;

    private String message;

    public MessageResponse() {
    }

    public PositionResponse getPosition() {
        return position;
    }

    public void setPosition(PositionResponse position) {
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message [position=" + position + ", message=" + message + "]";
    }

}
