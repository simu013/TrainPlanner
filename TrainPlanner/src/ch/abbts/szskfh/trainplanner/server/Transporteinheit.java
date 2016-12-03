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
abstract class Transporteinheit {
    protected double mLeergewicht;
    protected double mLaenge;
    protected double mMaxLadung;
    protected double mMomentangewicht;
    
    Transporteinheit(double pLeergewicht, double pLaenge, double pMaxLadung, double pMomentangewicht){
        mLeergewicht = pLeergewicht;
        mLaenge = pLaenge;
        mMaxLadung = pMaxLadung;
        mMomentangewicht = pMomentangewicht;
    }
    double getGewicht(){
        return mMomentangewicht;
    }
    
    double getLaenge(){
        return mLaenge;
    }
    
    double getLadung(){
        return mMomentangewicht-mLeergewicht;
    }
}

