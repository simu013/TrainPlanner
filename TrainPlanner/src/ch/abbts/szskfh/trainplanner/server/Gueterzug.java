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
    private float maxGewicht; // Maximales Gewicht in Tonnen
    private float maxLaenge; // Maximale Länge in Meter
    private int zugNr; // Zug Nummer
    private ArrayList<Gueterwagon> gueterwagons = new ArrayList<>();
    /**
     * Konstruktor für einen neuen Güterzug.
     * @param zugNr int Zugnummmer des Gueterzuges
     * @param maxGewicht float Maximales Gewicht in Tonnen
     * @param maxLaenge float Maximale Länge in Meter
     */
    public Gueterzug(int zugNr, float maxGewicht, float maxLaenge) {
        super.gewicht = (float) 170.8; //Gewicht von 2 Loks in Tonnen (je 85,4 T).
        super.laenge = (float) 37.8; //Länge von 2 Loks in Meter (je 19,8 M).
        this.maxGewicht = maxGewicht;
        this.maxLaenge = maxLaenge;
        this.zugNr = zugNr;
    }
    /**
     * Fügt dem Gueterzug einen neuen Gueterwagon hinzu.
     * @param gewicht float Gewicht in Tonnen
     * @param laenge float Länge in Meter
     * @return Gibt false zurück wenn kein Platz mehr vorhanden ist. 
     */
    public boolean addWagon(float gewicht, float laenge) {
        boolean b = false; // Wird true wenn Wagon angehängt werden konnte
        if (((super.gewicht + gewicht) <= maxGewicht) && ((super.laenge + laenge) <= maxLaenge)) {
        Gueterwagon gueterwagon = new Gueterwagon(gewicht, laenge);
        super.gewicht += gueterwagon.getGewicht();
        super.laenge += gueterwagon.getLaenge();
        gueterwagons.add(gueterwagon);
        }
        return b;
    }
    public ArrayList<Gueterwagon> getGueterwagons() {
        return gueterwagons;
    }
    public void setGueterwagons(ArrayList<Gueterwagon> gueterwagons) {
        this.gueterwagons = gueterwagons;
    }
    /**
     * Gibt die Zugnummer des Gueterzuges zurück
     * @return int ZugNummer
     */
    public int getZugNr() {
        return zugNr;
    }
    public float getMaxGewicht() {
        return maxGewicht;
    }
    public float getMaxLaenge() {
        return maxLaenge;
    }
}
