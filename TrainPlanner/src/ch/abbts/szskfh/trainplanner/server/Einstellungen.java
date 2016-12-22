/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
        einstellungen.setProperty("SocketTrennzeichen", ";");
        einstellungen.setProperty("FrameFarbe", "#404040");
        einstellungen.setProperty("FrameHoehe", "400");
        einstellungen.setProperty("FrameBreite", "750");
        
        try {
            einstellungen.storeToXML(new ObjectOutputStream(new FileOutputStream("srv_einstellungen.xml")), wert);
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
