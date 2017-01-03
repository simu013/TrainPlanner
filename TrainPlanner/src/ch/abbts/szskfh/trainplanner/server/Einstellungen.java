/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    String dateiName = "serverconfig.xml";
    
    public Einstellungen() {
        setDefaultEinstellungen();
    }
    /**
     * Erwartet Name der Einstellung. Gibt gewünschte Einstellung zurück. 
     * @return 
     */
    public String getEinstellung(String name) {
        return wert = einstellungen.getProperty(name); 
    }
    /** 
     * Setzt Einstellungswert
     */
    public void setEinstellung(String name, String wert) {
        einstellungen.setProperty(name, wert);
        try {
            einstellungen.storeToXML(new FileOutputStream(dateiName), "Server Einstellungen", "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initEinstellungen() {
        try {
            einstellungen.loadFromXML(new FileInputStream(dateiName));
        } catch (IOException ex) {
            setDefaultEinstellungen();
        }
        
    }
    public void setDefaultEinstellungen() {
        einstellungen.setProperty("PortNr", "5555");
        einstellungen.setProperty("SocketTrennzeichen", ";");
        einstellungen.setProperty("FrameFarbe", "#404040");
        einstellungen.setProperty("FrameHoehe", "400");
        einstellungen.setProperty("FrameBreite", "750");
        
        try {
            einstellungen.storeToXML(new ObjectOutputStream(new FileOutputStream(dateiName)), "Server Einstellungen");
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
