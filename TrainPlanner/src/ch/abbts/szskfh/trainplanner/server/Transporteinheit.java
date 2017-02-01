/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 * Stellt eine Transporteinheit mit Länge und Gewicht zur Verfügung. 
 * @author Florian Häusermann
 */
public class Transporteinheit {
    
    protected float leerGewicht;
    protected float laenge;
    protected float maxLadung;
    /**
     * Stellt eine Transporteinheit zur Verfügung. 
     * @param leerGewicht Leergewicht des Transportbjekts
     * @param laenge Länge des Transportobjekts
     * @param maxLadung Maximal mögliche Ladung des Transportobjekts
     */
    public Transporteinheit(float leerGewicht, float laenge, float maxLadung) {
        this.leerGewicht = leerGewicht;
        this.laenge = laenge;
        this.maxLadung = maxLadung;
    }
    /**
     * Gibt das Leergewicht der Transporteinheit zurück. 
     * @return Float Leergewicht der Transporteinheit. 
     */
    public float getLeerGewicht(){
        return leerGewicht;
    }
    /**
     * Gibt Länge der Transporteinheit zurück
     * @return Short Länge der Transporteinheit. 
     */
    public float getLaenge(){
        return laenge;
    }
    /**
     * Gibt die maximale Ladung der Transporteinheit zurück
     * @return float maximale Zuladung
     */
    public float getMaxLadung() {
        return maxLadung;
    }
    /**
     * Gibt das maximale Gesamtgewicht zurück. 
     * @return float maximales Gesamtgewicht
     */
    public float getMaxGesamtGewicht() {
        return leerGewicht + maxLadung;
    }
}

