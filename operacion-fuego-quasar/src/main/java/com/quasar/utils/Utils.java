package com.quasar.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.quasar.model.entity.Message;
import com.quasar.model.entity.Satellite;
import com.quasar.request.SatelliteRequest;
import com.quasar.response.MessageResponse;
import com.quasar.response.PositionResponse;

/**
 * Clase de utilerias a utilizar en la aplicacion
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
@Component
public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";

    @Autowired
    private Environment environment;

    /**
     * 
     * @see <a href=
     *      "https://github.com/lemmingapex/Trilateration">https://github.com/lemmingapex/Trilateration</a>
     * 
     *      Permite obtener la locacizacion de la nave por medio de Trilateration
     * 
     * @param kenobiDistance    Distancia de la nave al satelite Kenobi
     * @param skywalkerDistance Distancia de la nave al satelite skywalker
     * @param satoDistance      Distancia de la nave al satelite sato
     * 
     * @return locacion de la nave
     */
    public double[] getLocation(double kenobiDistance, double skywalkerDistance, double satoDistance) {
        double[][] positions = new double[3][];
        String[] kenobi = environment.getProperty("satellites.kenobi.position").split(",");
        String[] skywalker = environment.getProperty("satellites.skywalker.position").split(",");
        String[] sato = environment.getProperty("satellites.sato.position").split(",");

        positions[0] = Arrays.stream(kenobi).mapToDouble(Double::parseDouble).toArray();
        positions[1] = Arrays.stream(skywalker).mapToDouble(Double::parseDouble).toArray();
        positions[2] = Arrays.stream(sato).mapToDouble(Double::parseDouble).toArray();

        double[] distances = { kenobiDistance, skywalkerDistance, satoDistance };

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction,
                new LevenbergMarquardtOptimizer());
        return nSolver.solve().getPoint().toArray();
    }

    /**
     * Decodifica el mensaje con respecto a los mensajes indifiduales de cada
     * satelite
     * 
     * @param kenobiMsg    Listado de mensajes del satelite kenobi
     * @param skywalkerMsg Listado de mensajes del satelite skywalker
     * @param satoMsg      Listado de mensajes del satelite sato
     * @return El mensaje tal cual lo genera los emisores del mensaje
     */
    public String getMessage(List<String> kenobiMsg, List<String> skywalkerMsg, List<String> satoMsg) {
        List<String> lstKenobiMsg = cleanMessage(kenobiMsg);
        List<String> lstSkywalkerMsg = cleanMessage(skywalkerMsg);
        List<String> lstSatoMsg = cleanMessage(satoMsg);

        log.info("lstKenobiMsg: {}", lstKenobiMsg);
        log.info("lstSkywalkerMsg: {}", lstSkywalkerMsg);
        log.info("lstSatoMsg: {}", lstSatoMsg);

        int max = sizeMessage(lstKenobiMsg, lstSkywalkerMsg, lstSatoMsg);

        log.info("max: {}", max);

        completeMessage(lstKenobiMsg, max);
        completeMessage(lstSkywalkerMsg, max);
        completeMessage(lstSatoMsg, max);

        log.info("lstKenobiMsg:     {}", lstKenobiMsg);
        log.info("lstSkywalkerMsg:  {}", lstSkywalkerMsg);
        log.info("lstSatoMsg:       {}", lstSatoMsg);

        if (!validMessage(lstKenobiMsg, lstSkywalkerMsg, lstSatoMsg)) {
            log.error("Mensaje no valido");
        }
        return createMsg(lstKenobiMsg, lstSkywalkerMsg, lstSatoMsg);
    }

    /**
     * Remueve los elementos iniciales de las listas que esten en blanco ""
     * 
     * @param lstMsg lista de mensajes
     * @return lista de mensajes que empieza con una palabra
     */
    private List<String> cleanMessage(List<String> lstMsg) {
        log.info("Inicia metodo cleanMessage");
        while ("".equals(lstMsg.get(0))) {
            lstMsg.remove(0);
        }
        log.info("Inicia metodo cleanMessage");
        return lstMsg;
    }

    /**
     * Obtiene el tamaño maximo de las listas de mensajes
     * 
     * @param kenobiMsg    Listado de mensajes del satelite kenobi
     * @param skywalkerMsg Listado de mensajes del satelite skywalker
     * @param satoMsg      Listado de mensajes del satelite sato
     * @return tamaño maximo de los mansajes
     */
    private int sizeMessage(List<String> kenobiMsg, List<String> skywalkerMsg, List<String> satoMsg) {
        return Math.max(Math.max(kenobiMsg.size(), skywalkerMsg.size()), satoMsg.size());
    }

    /**
     * Completa las listas con palabras vacias al incio de la lista hasta alcanzar
     * el tamaño del mensaje
     * 
     * @param lstMsg lista de mensajes
     * @param size   tamaño del mensaje
     */
    private void completeMessage(List<String> lstMsg, int size) {
        for (int i = lstMsg.size(); i < size; i++) {
            lstMsg.add(0, "");
        }
    }

    /**
     * Verifica que las listas de mensajes sean validas, la logica a seguir es que
     * si en la misma posicion todas las palabras estan en blanco
     * 
     * @param kenobiMsg    listado de mesajes de Kenobi
     * @param skywalkerMsg Listado de mensajes de Skywalker
     * @param satoMsg      listado de mensajes de sato
     * @return true/false
     */
    private boolean validMessage(List<String> kenobiMsg, List<String> skywalkerMsg, List<String> satoMsg) {
        log.info("Inicia metodo validMessage");
        boolean valid = Boolean.TRUE;
        for (int i = 0; i < kenobiMsg.size(); i++) {
            if ("".equals(kenobiMsg.get(i).trim()) && "".equals(skywalkerMsg.get(i).trim())
                    && "".equals(satoMsg.get(i).trim())) {
                log.info("Mensaje no valido: " + i);
                valid = Boolean.FALSE;
                break;
            }
        }
        log.info("Termina metodo validMessage");
        return valid;
    }

    /**
     * Conjunta los listados de mensajes fracmentados en un solo mensaje
     * decodificado
     * 
     * @param kenobiMsg    listado de mesajes de Kenobi
     * @param skywalkerMsg Listado de mensajes de Skywalker
     * @param satoMsg      listado de mensajes de sato
     * @return Mensaje decodificado
     */
    private String createMsg(List<String> kenobiMsg, List<String> skywalkerMsg, List<String> satoMsg) {
        log.info("Inicia metodo createMsg");
        StringBuilder msgFinal = new StringBuilder("");
        for (int i = 0; i < kenobiMsg.size(); i++) {
            log.info("posibles {} | {} | {} | {}", i, kenobiMsg.get(i), skywalkerMsg.get(i), satoMsg.get(i));
            String palabra = !"".equals(kenobiMsg.get(i)) ? kenobiMsg.get(i)
                    : !"".equals(skywalkerMsg.get(i)) ? skywalkerMsg.get(i) : satoMsg.get(i);
            log.info("palabra {}: {}", i, palabra);
            msgFinal.append(palabra);
            msgFinal.append(" ");
        }
        log.info("Termina metodo createMsg");
        return msgFinal.toString().trim();
    }

    /**
     * Permite obtener la ubicación de la nave y el mensaje decodificado
     * 
     * @param lstSatellite Listado de satelites con su nombre, distancia y fragmento
     *                     de mensaje
     * @return Objeto con la posicion de la nave y el mensaje decodificado
     */
    public MessageResponse topSecret(List<SatelliteRequest> lstSatellite) {
        log.info("Entra a topSecret");

        double kenobiDistance = 0;
        double skywalkerDistance = 0;
        double satoDistance = 0;

        List<String> kenobiMsg = new ArrayList<>();
        List<String> skywalkerMsg = new ArrayList<>();
        List<String> satoMsg = new ArrayList<>();

        for (SatelliteRequest satellite : lstSatellite) {
            if (KENOBI.equals(satellite.getName())) {
                kenobiDistance = satellite.getDistance();
                kenobiMsg.addAll(satellite.getMessage());
            } else if (SKYWALKER.equals(satellite.getName())) {
                skywalkerDistance = satellite.getDistance();
                skywalkerMsg.addAll(satellite.getMessage());
            } else {
                satoDistance = satellite.getDistance();
                satoMsg.addAll(satellite.getMessage());
            }
        }
        log.info("Asignamos objetos");

        MessageResponse message = new MessageResponse();
        PositionResponse position = new PositionResponse();

        message.setMessage(getMessage(kenobiMsg, skywalkerMsg, satoMsg));
        double[] arrayPosition = getLocation(kenobiDistance, skywalkerDistance, satoDistance);

        position.setX(arrayPosition[0]);
        position.setY(arrayPosition[1]);
        message.setPosition(position);

        return message;
    }

    /**
     * Realiza el parseo de SatelliteRequest a Satellite
     * 
     * @param name             nombre del satelite
     * @param satelliteRequest distancia y fragmento de mensaje del satelite
     * @return entidad Satellite
     */
    public Satellite parseRequestToEntity(String name, SatelliteRequest satelliteRequest) {
        log.info("Entra a parseRequestToEntity");
        Satellite satellite = new Satellite();
        satellite.setName(name);
        satellite.setDistance(satelliteRequest.getDistance());
        List<Message> lstMessages = new ArrayList<>();
        for (String messageRequest : satelliteRequest.getMessage()) {
            Message message = new Message();
            message.setWord(messageRequest);
            message.setSatellite(satellite);
            lstMessages.add(message);
        }
        satellite.setMessages(lstMessages);
        log.info("sale de parseRequestToEntity");
        return satellite;
    }

    /**
     * Realiza el parseo del listado de Satelites a objeto de respuesta para el
     * servicio REST
     * 
     * @param lstSatellites listado de satelites
     * @return Objeto con la respuesta para el servicio rest con la posicion y
     *         mensaje decodificado
     */
    public MessageResponse satellitesToMessageResponse(List<Satellite> lstSatellites) {
        MessageResponse messageResponse = new MessageResponse();
        List<SatelliteRequest> lstSatelliteRequest = new ArrayList<>();
        SatelliteRequest satelliteRequest;
        for (Satellite satellite : lstSatellites) {
            satelliteRequest = new SatelliteRequest();
            satelliteRequest.setName(satellite.getName());
            satelliteRequest.setDistance(satellite.getDistance());
            satelliteRequest.setMessage(parseLstMessageTolstString(satellite.getMessages()));
            lstSatelliteRequest.add(satelliteRequest);
        }
        log.info("lstSatelliteRequest: " + lstSatelliteRequest);
        messageResponse = topSecret(lstSatelliteRequest);
        return messageResponse;
    }

    /**
     * Realiza el parseo de la entidad de Message a String
     * 
     * @param lstMessage listado de mensajes
     * @return listado de mensajes del tipo String
     */
    public List<String> parseLstMessageTolstString(List<Message> lstMessage) {
        List<String> lstStrings = new ArrayList<>();
        for (Message message : lstMessage) {
            lstStrings.add(message.getWord());
        }
        return lstStrings;
    }

}
