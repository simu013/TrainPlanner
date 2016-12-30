/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 *
 * @author Simon
 */
public class Gueterwagon extends Transporteinheit {
    ArrayList <Container> container = new ArrayList<>();
    
    public Gueterwagon() {
        super.gewicht = (float) 13.5; // Gewicht in Tonnen
        super.laenge = (float) 14.5; // Länge in Meter

    }    
}
