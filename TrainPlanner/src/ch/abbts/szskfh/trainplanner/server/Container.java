/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 *  Diese Klasse representiert einen Container mit einer Laenge von 6m und einem Gewicht von 24 Tonnen.
 * @author Florian
 */
public class Container extends Transporteinheit{
    private final short laenge;
    private final float gewicht;

    public Container() {
        this.laenge = 6; //Laenge = 6m.
        this.gewicht = 24; //Gewicht = 24 Tonnen.
    }
    @Override
    public short getLaenge() {
        return laenge; //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public float getGewicht() {
        return gewicht; //To change body of generated methods, choose Tools | Templates.
    }
}
