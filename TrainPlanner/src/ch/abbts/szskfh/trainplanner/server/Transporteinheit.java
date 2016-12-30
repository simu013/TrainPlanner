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
    protected float laenge;
    protected float gewicht;
    protected float maxLadung;
    
    public Transporteinheit(){
        
    }
    /**
     * Gibt Gewicht der Transporteinheit zurück. 
     * @return Float Gewicht der Transporteinheit. 
     */
    public float getGewicht(){
        return gewicht;
    }
    /**
     * Gibt Länge der Transporteinheit zurück
     * @return Short Länge der Transporteinheit. 
     */
    public float getLaenge(){
        return laenge;
    }
    public float getLadung() {
        return maxLadung;
    }
}

