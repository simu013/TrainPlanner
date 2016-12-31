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
    ArrayList <Container> containers = new ArrayList<>();
    
    public Gueterwagon(float gewicht, float laenge) {
        super.gewicht = gewicht; // Gewicht in Tonnen
        super.laenge = laenge; // Länge in Meter

    }    
    /**
     * Fügt einen Container hinzu
     * @param laenge Länge des Containers
     * @param gewicht Gewicht des Containers
     * @param maxLadung Maximale Ladung des Containers
     * @return Gibt false zurück wenn kein Platz mehr vorhanden ist.  
     */
    public boolean addContainer(float laenge, float gewicht, float maxLadung ) {
        float a = 0; // Gesamtlänge aller Container
        boolean b = false; // Wird true wenn Container verstaut werden konnte
        for (int i = 0; i < containers.size(); i++ ) {
            a += containers.get(i).getLaenge();
        }
        if (a + laenge <= super.laenge) {
            Container container = new Container(laenge, gewicht, maxLadung);
            containers.add(container);
            super.gewicht += container.getGewicht();
        }
        return b;
    }
}
