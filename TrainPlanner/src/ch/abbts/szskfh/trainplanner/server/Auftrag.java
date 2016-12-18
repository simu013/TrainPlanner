/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Simon
 */
public class Auftrag {

    private short anzahlContainer;
    private LocalTime startZeit;
    private short prioritaet;
    private String transportID;

    public Auftrag(short anzahlContainer, LocalTime startZeit, short prio) {
        this.anzahlContainer = anzahlContainer;
        this.startZeit = startZeit;
        prioritaet = prio;
        
        // Transport ID erzeugen
        SimpleDateFormat zeitStempelFormat = new SimpleDateFormat("YYYYMMddHHmmssSS");
        transportID = zeitStempelFormat.format(new Date());
    }

    public short getAnzahlContainer() {
        return anzahlContainer;
    }

    public short getPrioritaet() {
        return prioritaet;
    }

    public String getTransportID() {
        return transportID;
    }

    public LocalTime getStartZeit() {
        return startZeit;
    }
}
