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
public class Gueterzug {
    
    private int anzahlWagons;
    private ArrayList<Gueterwagen> gueterwagen = new ArrayList<Gueterwagen>();
    
    /**
     *
     * @param container Anzahl Wagons
     */
    public Gueterzug(int container){
        anzahlWagons = container/2;
        for(int i=0; i < anzahlWagons; i++){
            gueterwagen.add(new Gueterwagen());
        }
    }
    
    /**
     * Gibt Anzahl Wagons aus.
     * @return anzahlWagons, die Anzahl der Güterwagons.
     */
    public int getAnzahlWagons(){
        return anzahlWagons;
    }
}

