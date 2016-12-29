/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.server.Fahrplan;
import ch.abbts.szskfh.trainplanner.server.TrainToSmallException;
import ch.abbts.szskfh.trainplanner.server.TransportNotPossibleException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class TestFahrplan {

    public static void main(String[] args) {
        Fahrplan fahrplan = new Fahrplan();
        
        try {
            fahrplan.addFahrt(LocalTime.of(12, 00), 25);
        } catch (TransportNotPossibleException ex) {
            Logger.getLogger(TestFahrplan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TrainToSmallException ex) {
            Logger.getLogger(TestFahrplan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i<fahrplan.getGueterFahrten().size(); i++){
            
        System.out.println(fahrplan.getGueterFahrten().get(i).getGueterZug().getLaenge());
        }
    }
}
