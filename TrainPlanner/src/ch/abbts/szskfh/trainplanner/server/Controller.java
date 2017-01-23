/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 * Kontrolliert und verwaltet die Server Instanzen.
 *
 * @author Simon
 */
public class Controller {

    private Disponent disponent = Disponent.getInstance();
    private ServerGUI gui = new ServerGUI();
    private SocketConnection socket = new SocketConnection(this);

    public Controller() {

    }

    public Auftrag addAuftrag(String nameFirma, Short anzahlContainer, LocalTime startZeit, Short prio) {
        System.out.println(nameFirma + ", " + anzahlContainer + ", " + startZeit + ", " + prio);
        return disponent.addAuftrag(nameFirma, (short) anzahlContainer, (LocalTime) startZeit, (short) prio);
    }

    public Status getStatus(String transportID) {
        return disponent.getState(transportID);
    }
    
    public LocalTime getAnkunftszeitByZugNr(int zugNr){
        return disponent.getAnkunftszeitByZugNr(zugNr);
    }
}
