/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 * Stellt eine Firma zur Verfügung.
 *
 * @author Simon
 */
public class Firma {

    private String name;
    private ArrayList<Auftrag> auftraege = new ArrayList();

    /**
     * Stellt ein Firma Objekt zur Verfügung.
     *
     * @param name Name der Firma als String
     */
    public Firma(String name) {
        this.name = name;
    }

    /**
     * Gibt den Namen der Firma zurück
     *
     * @return String Name der Firma
     */
    public String getName() {
        return name;
    }

    /**
     * Fügt der Firma einen neuen Auftrag hinzu
     *
     * @param auftrag Erwartet ein Auftrag Objekt.
     */
    public void addAuftrag(Auftrag auftrag) {
        auftraege.add(auftrag);
    }

    /**
     * Gibt alle Aufträge einer Firma zurück
     *
     * @return ArrayList mit Auftrag Objekten
     */
    public ArrayList<Auftrag> getAuftraege() {
        return auftraege;
    }
}
