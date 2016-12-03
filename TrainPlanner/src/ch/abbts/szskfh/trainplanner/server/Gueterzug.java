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
    
    private int mAnzahlWagons;
    private ArrayList<Gueterwagen> mGueterwagen = new ArrayList<Gueterwagen>();
    
    /**
     *
     * @param pWagons Anzahl Wagons
     */
    public Gueterzug(int pWagons){
        mAnzahlWagons = pWagons;
        for(int i=0; i < mAnzahlWagons; i++){
            mGueterwagen.add(new Gueterwagen());
        }
    }
    
    /**
     * Gibt Anzahl Wagons aus.
     * @return mAnzahlWagons, die Anzahl der Güterwagons.
     */
    public int getAnzahlWagons(){
        return mAnzahlWagons;
    }
}

