/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 *
 * @author Simon
 */
public class Einstellungen {

    public Einstellungen() {

    }

    /**
     * Erwartet Name der Einstellung. Gibt gewünschte Einstellung zurück.
     *
     * @return String Gibt den Wert zum angegebenen Eintrag zurück.
     */
    public String getEinstellung(String name) {
        return Config.getProperty(name);

    }

    public void setEinstellung(String name, String wert) {
        Config.setProperty(name, wert);
    }
}

