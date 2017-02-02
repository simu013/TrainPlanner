/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

/**
 * Kontrolliert und verwaltet die Server Instanzen.
 *
 * @author Simon
 */
public class Controller {

    private Disponent disponent = Disponent.getInstance();
    private ServerGUI gui = new ServerGUI(this);
    private SocketConnection socket = new SocketConnection(this);
    
    public Auftrag addAuftrag(String nameFirma, Short anzahlContainer, LocalTime startZeit, Short prio) {
        return disponent.addAuftrag(nameFirma, (short) anzahlContainer, (LocalTime) startZeit, (short) prio);
    }

    public StatusEnum getStatus(String transportID) {
        return disponent.getState(transportID);
    }

    public LocalTime getAnkunftszeitByZugNr(int zugNr) {
        return disponent.getAnkunftszeitByZugNr(zugNr);
    }

    public void schreibeInGui(String message) {
        gui.schreibeInGui(getZeitFormat().format(new Date()) + " " + message);
    }

    /**
     * Schreibt Fehler und Ausnahmen in eine Log-Datei. Der Log-Eintrag besteht
     * jeweils aus dem Zeitstempel im Format dd.MM.YYYY hh:mm:ss sowie dem
     * übergebenen logEintrag.
     *
     * @param logEintrag String Eintrag für Log-Datei.
     */
    public void schreibeInLogDatei(String logEintrag) {
        try {
            BufferedWriter log = new BufferedWriter(new FileWriter("socketLog.txt", true));
            log.write(getZeitFormat().format(new Date()) + " " + logEintrag);
            log.newLine();
            log.close();
        } catch (IOException ex) {
            schreibeInGui("Fehler beim Schreiben der Log Datei! ");
        }
    }

    public SimpleDateFormat getZeitFormat() {
        return new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
    }

    public String[] trenneString(String string) {
        return string.split(Einstellungen.getProperty("SocketTrennzeichen"));
    }

    public Fahrplan getFahrplan() {
        return disponent.getFahrplan();
    }

    public void unsetEmergencyState() {
        disponent.unsetEmergencyState();
    }
    public void setEmergencyState() {
        disponent.setEmergencyState();
    }
    public boolean getEmergencyState() {
        return gui.getEmergencyState();
    }
    public double berechnePreis(Auftrag auftrag) {
        return disponent.berechnePreis(auftrag);
    }
}
