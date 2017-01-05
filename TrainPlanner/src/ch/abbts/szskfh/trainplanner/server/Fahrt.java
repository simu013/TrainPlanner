/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 *
 * @author Florian Haeusermann
 */
public class Fahrt {

    private Gueterzug gueterzug;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private String status;
    private Zugtyp zugTyp;

    public Fahrt(Zugtyp zugTyp, LocalTime startZeit, LocalTime endZeit, int zugNr) {
        this.zugTyp = zugTyp;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        float maxGewicht = (float) 3200; // Maximales Gewicht eines Güterzuges
        float maxLaenge = (float) 750; // Maximale Länge eines Güterzuges
        if(zugTyp.equals(Zugtyp.GUETERZUG)) {
           // gueterzug = new Gueterzug(zugNr, maxGewicht, maxLaenge);
        }
    }

    /**
     * Gibt das Güterzug Objekt der Fahrt zurück. 
     * @return Güterzug Objekt. Gibt null zurück wenn Fahrt keine Güterzugfahrt ist. 
     */
    public Gueterzug getGueterzug() {
        return gueterzug;
    }
    /**
     * Gibt die Abfahrtszeit der Fahrt zurück.
     * @return LocalTime Abfahrtszeit im Format HH:mm
     */
    public LocalTime getStartZeit() {
        return startZeit;
    }
    /**
     * Gibt die Ankunftszeit der Fahrt zurück. 
     * @return LocalTime Ankunftszeit im Format HH:mm
     */
    public LocalTime getEndZeit() {
        return endZeit;
    }
    /**
     * Gibt den Zugtyp der Fahrt zurück. 
     * @return Enum Zugtyp (PERSONENZUG, GUETERZUG)
     */
    public Zugtyp getZugtyp() {
        return zugTyp;
    }

}
