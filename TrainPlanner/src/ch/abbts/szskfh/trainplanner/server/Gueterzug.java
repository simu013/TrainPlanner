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
    private ArrayList<Container> container = new ArrayList<Container>();
    
    /**
     *
     * @param container Anzahl Wagons
     */
    public Gueterzug(int container){
        anzahlWagons = container/2;
    }
    
    /**
     * Gibt Anzahl Wagons aus.
     * @return anzahlWagons, die Anzahl der Güterwagons.
     */
    public int getAnzahlWagons(){
        return anzahlWagons;
    }
}

