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

    public String addAuftrag(String nameFirma, Short anzahlContainer, LocalTime startZeit, Short prio) {
        System.out.println(nameFirma + ", " + anzahlContainer + ", " + startZeit + ", " + prio);
        return disponent.addAuftrag(nameFirma, (short) anzahlContainer, (LocalTime) startZeit, (short) prio);
    }

    public String getStatus(String transportID) {
        return disponent.getState((String) transportID);
    }
}