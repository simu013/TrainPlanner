/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 *
 * @author Florian
 */
public class Gueterzug extends Transporteinheit {

    private ArrayList<Gueterwagon> gueterwagons = new ArrayList<>();

    /**
     * Konstruktor für einen neuen Güterzug.
     * @param zugNr int Zugnummmer des Gueterzuges
     */
    public Gueterzug() {
        super(Einstellungen.getFloatProperty("ZugLeerGewicht"), Einstellungen.getFloatProperty("ZugMaxLaenge"), Einstellungen.getFloatProperty("ZugGesamtGewicht"));
    }

    public void addGueterwagon(Gueterwagon gueterwagon) {
        gueterwagons.add(gueterwagon);
    }

    public void removeGueterwagon(Gueterwagon gueterwagon) {
        gueterwagons.remove(gueterwagon);
    }

    public ArrayList<Gueterwagon> getGueterwagons() {
        return gueterwagons;
    }
}
