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
     *
     */
    public Gueterzug() {
        super.gewicht = (float) 170.8; //Gewicht von 2 Loks in Tonnen (je 85,4 T).
        super.laenge = (float) 37.8; //Länge von 2 Loks in Meter (je 19,8 M).

    }
}
