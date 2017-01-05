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

    ArrayList<Container> containers = new ArrayList<>();

    public Gueterwagon() {
        super(Config.getFloatProperty("WagonLeerGewicht"), Config.getFloatProperty("WagonLaenge"),
                Config.getFloatProperty("WagonGesamtGewicht"));
    }

    /**
     * Fügt einen Container hinzu
     * @return Gibt false zurück wenn kein Platz mehr vorhanden ist.
     */
    public boolean addContainer() {
        float a = 0; // Gesamtlänge aller Container
        boolean b = false; // Wird true wenn Container verstaut werden konnte
        for (int i = 0; i < containers.size(); i++) {
            a += containers.get(i).getLaenge();
        }
        if (a + laenge <= super.laenge) {
            containers.add(new Container());
        }
        return b;
    }
}
