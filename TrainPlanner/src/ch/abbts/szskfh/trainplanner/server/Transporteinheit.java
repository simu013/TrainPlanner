/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 *
 * @author Florian Häusermann
 */
public class Transporteinheit {
    protected short laenge;
    protected float gewicht;
    
    Transporteinheit(){
        
    }
    float getGewicht(){
        return gewicht;
    }
    
    short getLaenge(){
        return laenge;
    }
}

