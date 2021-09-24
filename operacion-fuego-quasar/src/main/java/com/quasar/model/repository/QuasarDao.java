package com.quasar.model.repository;

import org.springframework.data.repository.CrudRepository;

import com.quasar.model.entity.Satellite;

/**
 * Interface que nos permite el acceso a datos de la BD
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
public interface QuasarDao extends CrudRepository<Satellite, Long> {

}
