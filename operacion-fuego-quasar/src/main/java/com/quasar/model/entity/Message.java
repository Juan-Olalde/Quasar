package com.quasar.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad para almacenar las palabras de los mensajes para los satelites
 * 
 * @author <a href="mailto:jpolalde@gmail.com">Juan Paulino Olalde Granados</a>
 * @version 1.0.0
 *
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = -4055613005305516544L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "word")
    private String word;

    @ManyToOne(fetch = FetchType.LAZY)
    private Satellite satellite;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Satellite getSatellite() {
        return satellite;
    }

    public void setSatellite(Satellite satellite) {
        this.satellite = satellite;
    }

}
