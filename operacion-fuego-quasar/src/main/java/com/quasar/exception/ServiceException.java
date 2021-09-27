package com.quasar.exception;

/**
 * Clase utilizada para el manejo de exepciones personalizadas
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 5715259296335104609L;

    public ServiceException(String message) {
        super(message);
    }

}
