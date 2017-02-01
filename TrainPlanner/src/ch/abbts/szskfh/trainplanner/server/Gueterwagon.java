/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 * Stellt einen Güterwagen des Typs Transporteinheit zur Verfügung. 
 * @author Simon
 */
public class Gueterwagon extends Transporteinheit {

    ArrayList<Container> containers = new ArrayList<>();

    public Gueterwagon() {
        super(Einstellungen.getFloatProperty("WagonLeerGewicht"), Einstellungen.getFloatProperty("WagonLaenge"),
                Einstellungen.getFloatProperty("WagonMaxLadung"));
    }

    /**
     * Fügt einen Container hinzu
     */
    public void addContainer(Container container) {
        containers.add(container);
    }

    /**
     * Gibt die Container des Güterwagons zurück
     * @return ArrayList mit Container
     */
    public ArrayList<Container> getContainers() {
        return containers;
    }
}
