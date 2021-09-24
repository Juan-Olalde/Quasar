package com.quasar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.quasar.utils.Utils;

@SpringBootTest
@ContextConfiguration(classes = Utils.class)
class OperacionFuegoQuasarApplicationTests {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    @Autowired
    private Utils utils;

//    @Test
    public void getLocationTest() {
        double[] expectedPosition = new double[] { -107.60396587789076, -87.61883403572328 };
        double[] calculatedPosition = utils.getLocation(10.0, 15.0, 20.0);
        for (int i = 0; i < calculatedPosition.length; i++) {
            log.info("expectedPosition: {}", expectedPosition[i]);
            log.info("calculatedPosition: {}", calculatedPosition[i]);
            assertEquals(expectedPosition[i], calculatedPosition[i]);
        }
    }

    @Test
    public void getMessageTest() {
        String[] kenobi = { "", "este", "es", "un", "mensaje" };
        String[] skywalker = { "este", "", "un", "mensaje" };
        String[] sato = { "", "", "es", "", "mensaje" };

        String message = utils.getMessage(new ArrayList<String>(Arrays.asList(kenobi)),
                new ArrayList<String>(Arrays.asList(skywalker)), new ArrayList<String>(Arrays.asList(sato)));
        log.info("message: {}", message);
        String expectedMsg = "este es un mensaje";
        assertEquals(message, expectedMsg);
    }

//    @Test
    public void getMessageTest2() {
        String[] kenobi = { "este", "", "", "mensaje", "" };
        String[] skywalker = { "", "es", "", "", "secreto" };
        String[] sato = { "este", "", "un", "", "" };

        String message = utils.getMessage(new ArrayList<String>(Arrays.asList(kenobi)),
                new ArrayList<String>(Arrays.asList(skywalker)), new ArrayList<String>(Arrays.asList(sato)));
        log.info("message: {}", message);
        String expectedMsg = "este es un mensaje secreto";
        assertEquals(message, expectedMsg);
    }

}
