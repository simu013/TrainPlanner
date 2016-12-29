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

    public Container() {
        super.laenge = 6; //Laenge = 6m.
        super.gewicht = 24; //Gewicht = 24 Tonnen.
    }
}
