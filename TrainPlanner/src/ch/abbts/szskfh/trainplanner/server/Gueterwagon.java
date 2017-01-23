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
                Config.getFloatProperty("WagonMaxLadung"));
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
