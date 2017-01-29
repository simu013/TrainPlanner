/*
 * To change this license header, choose License Headers in Project Config.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.Color;
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
public class Config {

    private static Properties properties;
    private static String fileName = "serverconfig.xml";

    public static String getProperty(String key) {
        checkPropertyState();
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
        checkPropertyState();
        properties.setProperty(key, value);
    }

    public static float getFloatProperty(String key) {
        return Float.parseFloat(getProperty(key));
    }
    
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    private static void checkPropertyState() {
        if (properties == null) {
            loadProperties();
        }
    }

    private static void loadDefaultProperties() {
        try {
            // Socket Properties
            properties.setProperty("PortNr", "5555");
            properties.setProperty("SocketTrennzeichen", ";");
            // GUI Properties
            properties.setProperty("FrameFarbe", "#404040");
            properties.setProperty("FrameHoehe", "400");
            properties.setProperty("FrameBreite", "750");
            properties.setProperty("Schriftfarbe", "#FF0000");
            // Züge
            properties.setProperty("ContainerLeerGewicht", "2.3");
            properties.setProperty("ContainerLaenge", "6");
            properties.setProperty("ContainerMaxLadung", "21.7");
            properties.setProperty("WagonLeerGewicht", "13.5");
            properties.setProperty("WagonLaenge", "14.5");
            properties.setProperty("WagonMaxLadung", "48");
            properties.setProperty("ZugLeerGewicht", "170.8"); //Gewicht von 2 Loks in Tonnen (je 85,4 T).
            properties.setProperty("ZugLaenge", "37.8"); //Länge von 2 Loks in Meter (je 19,8 M).
            properties.setProperty("ZugGesamtGewicht", "3029.2"); // Zugelassenes Gesamtgewicht abzüglich Loks (3200 T - 170.8 T)
            properties.setProperty("ZugMaxLaenge", "710.4"); // Zugelassene Zuglänge abzüglich Loks (750m - 39.6m)
            properties.setProperty("PersonenzugDauer", "18");
            properties.setProperty("GueterzugDauer", "22");
            properties.setProperty("SicherheitsabstandsZeit", "3");

            properties.storeToXML(new FileOutputStream(fileName), "Client Einstellungen", StandardCharsets.UTF_8.name());
        } catch (IOException ex) {
            Logger.getLogger(ch.abbts.szskfh.trainplanner.server.Config.class.getName()).log(Level.SEVERE, null, ex);
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
}
