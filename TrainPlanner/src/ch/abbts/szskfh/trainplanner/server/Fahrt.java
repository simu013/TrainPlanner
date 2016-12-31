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
    private int zugNr;

    public Fahrt(Zugtyp zugTyp, LocalTime startZeit, LocalTime endZeit, int zugNr) {
        this.zugTyp = zugTyp;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.zugNr = zugNr;
        if(zugTyp.equals(Zugtyp.GUETERZUG)) {
            gueterzug = new Gueterzug();
        }
    }
    /**
     * Gibt das Güterzug Objekt der Fahrt zurück. 
     * @return Güterzug Objekt. Gibt null zurück wenn Fahrt keine Güterzugfahrt ist. 
     */
    public Gueterzug getGueterzug() {
        return gueterzug;
    }
    public LocalTime getStartZeit() {
        return startZeit;
    }
    public LocalTime getEndZeit() {
        return endZeit;
    }
    public Zugtyp getZugtyp() {
        return zugTyp;
    }
    public int getZugNr() {
        return zugNr;
    }

}
