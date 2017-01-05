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

    private int zugNr; // Zug Nummer
    private ArrayList<Gueterwagon> gueterwagons = new ArrayList<>();

    /**
     * Konstruktor für einen neuen Güterzug.
     * @param zugNr int Zugnummmer des Gueterzuges
     */
    public Gueterzug(int zugNr) {
        super(Config.getFloatProperty("ZugLeerGewicht"), Config.getFloatProperty("ZugLaenge"), Config.getFloatProperty("ZugGesamtGewicht"));
        this.zugNr = zugNr;
    }

    /**
     * Fügt dem Gueterzug einen neuen Gueterwagon hinzu.
     * @return Gibt false zurück wenn kein Platz mehr vorhanden ist.
     
    public boolean addWagon() {
        boolean b = false; // Wird true wenn Wagon angehängt werden konnte
        if (((super.gewicht + gewicht) <= maxGewicht) && ((super.laenge + laenge) <= maxLaenge)) {
            Gueterwagon gueterwagon = new Gueterwagon(gewicht, laenge);
            super.gewicht += gueterwagon.getGewicht();
            super.laenge += gueterwagon.getLaenge();
            gueterwagons.add(gueterwagon);
        }
        return b;
    }*/

    public ArrayList<Gueterwagon> getGueterwagons() {
        return gueterwagons;
    }

    public void setGueterwagons(ArrayList<Gueterwagon> gueterwagons) {
        this.gueterwagons = gueterwagons;
    }

    /**
     * Gibt die Zugnummer des Gueterzuges zurück
     *
     * @return int ZugNummer
     */
    public int getZugNr() {
        return zugNr;
    }

    public float getGesamtGewicht() {
        return super.leerGewicht + super.maxLadung;
    }

    /*public float getMaxLaenge() {
        return maxLaenge;
    }*/
}
