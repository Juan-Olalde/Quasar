package com.quasar.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quasar.model.entity.Satellite;
import com.quasar.model.repository.QuasarDao;
import com.quasar.request.SatelliteRequest;
import com.quasar.response.MessageResponse;
import com.quasar.utils.Utils;

/**
 * 
 * @see com.quasar.services.QuasarService
 *
 */
@Service
public class QuasarServiceImpl implements QuasarService {

    private static Logger log = LoggerFactory.getLogger(QuasarServiceImpl.class);

    @Autowired
    private Utils utils;

    @Autowired
    private QuasarDao quasarDao;

    /**
     * @see com.quasar.services.QuasarService#topSecret(List)
     */
    @Override
    public MessageResponse topSecret(List<SatelliteRequest> lstSatellite) {
        log.debug("Entra a metodo topSecret");
        return utils.topSecret(lstSatellite);
    }

    /**
     * @see com.quasar.services.QuasarService#save(String, SatelliteRequest)
     */
    @Override
    @Transactional
    public void save(String name, SatelliteRequest satelliteRequest) {
        log.debug("Entra a metodo save");
        quasarDao.save(utils.parseRequestToEntity(name, satelliteRequest));
    }



    /**
     * @see com.quasar.services.QuasarService#topSecretSplit()
     */
    @Override
    public MessageResponse topSecretSplit() {
        log.debug("Entra a metodo topSecretSplit");
        List<Satellite> lstSatellites = (List<Satellite>) quasarDao.findAll();
        return utils.satellitesToMessageResponse(lstSatellites);
    }
    

}
