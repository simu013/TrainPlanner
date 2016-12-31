/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Florian Haeusermann
 */
public class Fahrplan {

    private ArrayList<Fahrt> fahrten = new ArrayList<>();
    private int zugNr = 0;
    
    /**
     * Stellt einen Fahrplan zur Verfügung. 
     * Der Fahrplan besteht aus einer ArrayList mit Fahrt-Objekten. 
     */
    public Fahrplan() {

    }

    public int addFahrt(Zugtyp zugTyp, LocalTime startZeit, LocalTime endZeit) {
        ++zugNr;
        if (zugTyp.equals(Zugtyp.GUETERZUG)) {
            fahrten.add(new Fahrt(zugTyp, startZeit, endZeit, zugNr));
        }
        if (zugTyp.equals(Zugtyp.PERSONENZUG)) {
            LocalTime effektiveAbfahrtszeit = startZeit.plusMinutes(18);
            LocalTime effektiveAnkunftzeit = effektiveAbfahrtszeit.plusMinutes(18);
            fahrten.add(new Fahrt(zugTyp, effektiveAbfahrtszeit, effektiveAnkunftzeit, zugNr));
        }
        return zugNr;
    }
    
    public ArrayList<Fahrt> getFahrten() {
        return fahrten;
    }
    public ArrayList<Fahrt> getPersonenFahrten() {
        ArrayList<Fahrt> personenFahrten = new ArrayList<>();
        for (int i = 0; i < fahrten.size(); i++) {
            if (fahrten.get(i).getZugtyp().equals(Zugtyp.PERSONENZUG)) {
                personenFahrten.add(fahrten.get(i));
            }
        }
        return personenFahrten;
    }
}
