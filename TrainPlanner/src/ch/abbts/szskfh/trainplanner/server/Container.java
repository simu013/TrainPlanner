/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 *
 * @author Florian
 */
public class Container extends Transporteinheit {
    
    public Container(double pLeergewicht, double pLaenge, double pMaxLadung, double pMomentangewicht) {
        super(pLeergewicht, pLaenge, pMaxLadung, pMomentangewicht);
    }
    
    
    @Override
    double getLadung() {
        return super.getLadung(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    double getLaenge() {
        return super.getLaenge(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    double getGewicht() {
        return super.getGewicht(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
