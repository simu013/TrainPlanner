/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.util.Properties;

/**
 *
 * @author Simon
 */
public class Einstellungen {
    Properties einstellungen = new Properties();
    String wert;
    
    public Einstellungen() {
        setDefaultEinstellungen();
    }
    /**
     * Erwartet Name der Einstellung. Gibt gewünschte Einstellung zurück. 
     * @return 
     */
    public String getEinstellung(String name) {
        return wert = einstellungen.getProperty(name); 
        
    /** 
     * Setzt Einstellungswert
     */
    } 
    public void setEinstellung(String name, String wert) {
        einstellungen.setProperty(name, wert);
    }
    public void setDefaultEinstellungen() {
        einstellungen.setProperty("PortNr", "5555");
        einstellungen.setProperty("IP", "Localhost");
        einstellungen.setProperty("FrameFarbe", "#404040");
        einstellungen.setProperty("FrameHoehe","400");
        einstellungen.setProperty("FrameBreite","750");
    }
}
