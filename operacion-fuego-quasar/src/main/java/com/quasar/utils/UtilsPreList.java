package com.quasar.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class UtilsPreList {

    private static Logger log = LoggerFactory.getLogger(UtilsPreList.class);

    @Autowired
    private Environment environment;

    public double[] getLocation(double kenobiDistance, double skywalkerDistance, double satoDistance) {
//        if (null == distances) {
//            throw new IllegalArgumentException("No existen distancias para poder calcular la localización");
//        }
//
//        if (distances.length != 3) {
//            throw new IllegalArgumentException("Deben ser 3 distancias para poder obtener la Localización");
//        }

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

    public String getMessage(String[] kenobiMsg, String[] skywalkerMsg, String[] satoMsg) {
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

    private int sizeMessage(List<String> kenobiMsg, List<String> skywalkerMsg, List<String> satoMsg) {
        return Math.max(Math.max(kenobiMsg.size(), skywalkerMsg.size()), satoMsg.size());
    }

    private List<String> cleanMessage(String[] msj) {
        log.info("Inicia metodo cleanMessage");
        List<String> lstMsg = new ArrayList<String>(Arrays.asList(msj));
        while ("".equals(lstMsg.get(0))) {
            lstMsg.remove(0);
        }
        log.info("Inicia metodo cleanMessage");
        return lstMsg;
    }

    private void completeMessage(List<String> lstMsg, int size) {
        for (int i = lstMsg.size(); i < size; i++) {
            lstMsg.add(0, "");
        }
    }

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

}
