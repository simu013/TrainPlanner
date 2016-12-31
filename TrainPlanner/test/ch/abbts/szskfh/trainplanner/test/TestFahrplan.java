/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.server.Disponent;
import ch.abbts.szskfh.trainplanner.server.Fahrplan;
import ch.abbts.szskfh.trainplanner.server.Fahrt;
import java.util.ArrayList;


/**
 *
 * @author Simon
 */
public class TestFahrplan {

    public static void main(String[] args) {
        Disponent disponent = Disponent.getInstance();
        String begrenzer = " / ";
        
        ArrayList<Fahrt> fahrten;
        fahrten = disponent.getFahrten();
        
        for (int i = 0; i < fahrten.size(); i++ ) {
            System.out.println(fahrten.get(i).getZugNr() + begrenzer + fahrten.get(i).getZugtyp() + begrenzer + fahrten.get(i).getStartZeit() 
            + begrenzer + fahrten.get(i).getEndZeit() + begrenzer + fahrten.get(i).getGueterzug());
            
        }

    }
}
