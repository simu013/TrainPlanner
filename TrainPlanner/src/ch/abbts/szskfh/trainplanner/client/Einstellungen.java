/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class Einstellungen {

    Properties einstellungen = new Properties();
    String wert;
    String dateiName = "clientconfig.xml";

    public Einstellungen() {
        initEinstellungen();
    }

    /**
     * Erwartet Name der Einstellung. Gibt gewünschte Einstellung zurück.
     *
     * @return String Gibt den Wert zum angegebenen Eintrag zurück.
     */
    public String getEinstellung(String name) {
        return wert = einstellungen.getProperty(name);

        /**
         * Setzt Einstellungswert name Erwartet Bezeichnung der Einstellung wert
         * Erwartet Wert für Einstellung
         */
    }

    public void setEinstellung(String name, String wert) {
        einstellungen.setProperty(name, wert);
        try {
            einstellungen.storeToXML(new FileOutputStream(dateiName), wert);
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initialisiert die Einstellungen aus dem XML File. Existiert kein XML
     * File, so wird mit den Standardeinstellungen ein neues erzeugt.
     */
    private void initEinstellungen() {
        try {
            einstellungen.loadFromXML(new FileInputStream(dateiName));
        } catch (IOException ex) {
            // Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
            setDefaultEinstellungen();
        }

    }

    public void setDefaultEinstellungen() {
        try {
            einstellungen.setProperty("PortNr", "5555");
            einstellungen.setProperty("IP", "Localhost");
            einstellungen.setProperty("SocketTrennzeichen", ";");
            einstellungen.setProperty("FrameFarbe", "#404040");
            einstellungen.setProperty("FrameHoehe", "400");
            einstellungen.setProperty("FrameBreite", "750");
            einstellungen.setProperty("SchriftFarbe", "#FF0000");
            einstellungen.setProperty("SchriftFarbeTitel", "#808080");
            
            
            einstellungen.storeToXML(new FileOutputStream(dateiName), "Client Einstellungen");
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
