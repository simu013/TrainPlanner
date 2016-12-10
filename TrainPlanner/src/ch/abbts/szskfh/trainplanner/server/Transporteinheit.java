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
    protected short mLaenge;
    protected float mGewicht;
    
    Transporteinheit(short pLaenge, float pGewicht){
        mLaenge = pLaenge;
        mGewicht = pGewicht;
    }
    double getGewicht(){
        return mGewicht;
    }
    
    double getLaenge(){
        return mLaenge;
    }
}

