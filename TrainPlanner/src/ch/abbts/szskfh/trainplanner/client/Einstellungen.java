/*
 * To change this license header, choose License Headers in Project Einstellungen.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class Einstellungen {

    private static Properties properties;
    private static String fileName = "clientconfig.xml";

    /**
     * Erwartet Name der Einstellung. Gibt gewünschte Einstellung zurück.
     *
     * @return String Gibt den Wert zum angegebenen Eintrag zurück.
     */
    public static String getProperty(String key) {
        checkPropertyState();
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        checkPropertyState();
        properties.setProperty(key, value);
        try {
            properties.storeToXML(new FileOutputStream(fileName), "Client Einstellungen", StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            Logger.getLogger(ch.abbts.szskfh.trainplanner.server.Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void checkPropertyState() {
        if (properties == null) {
            loadProperties();
        }

    }

    public static void loadProperties() {
        properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream(fileName));
        } catch (IOException ex) {
            loadDefaultProperties();
        }

    }

    public static void loadDefaultProperties() {
        try {
            properties.setProperty("PortNr", "5555");
            properties.setProperty("IP", "Localhost");
            properties.setProperty("SocketTrennzeichen", ";");
            properties.setProperty("FrameFarbe", "#404040");
            properties.setProperty("FrameHoehe", "400");
            properties.setProperty("FrameBreite", "750");
            properties.setProperty("SchriftFarbe", "#FF0000");
            properties.setProperty("SchriftFarbeTitel", "#808080");

            properties.storeToXML(new FileOutputStream(fileName), "Client Einstellungen", StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            Logger.getLogger(Einstellungen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
