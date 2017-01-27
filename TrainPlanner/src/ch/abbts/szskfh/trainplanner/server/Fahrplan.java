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
     * Stellt einen Fahrplan zur Verfügung. Der Fahrplan besteht aus einer
     * ArrayList mit Fahrt-Objekten.
     */
    public Fahrplan() {
        zugNr = 1;
    }

    public int getZugNr() {
        return zugNr;
    }

    public void addFahrt(Fahrt fahrt) {
        ++zugNr;
        fahrten.add(fahrt);
    }

    public ArrayList<Fahrt> getFahrten() {
        return fahrten;
    }

    public ArrayList<Fahrt> getFahrtenByZugTyp(Zugtyp zugTyp) {
        ArrayList<Fahrt> personenFahrten = new ArrayList<>();
        for (int i = 0; i < fahrten.size(); i++) {
            if (fahrten.get(i).getZugtyp().equals(zugTyp)) {
                personenFahrten.add(fahrten.get(i));
            }
        }
        return personenFahrten;
    }

    public Fahrt getFahrtByZugNr(int zugNr) {
        for (Fahrt fahrt : fahrten) {
            if (fahrt.getZugNr() == zugNr) {
                return fahrt;
            }
        }
        return null;
    }

    private void updateStatus(Fahrt fahrt) {
        if (fahrt.getStatus() == null) {
            fahrt.setStatus(Status.PLANNED);
        }
        if (fahrt.getStatus().equals(Status.PLANNED) && ! fahrt.getStartZeit().isAfter(LocalTime.now())) {
            fahrt.setStatus(Status.TRANSPORTING);
        }
        if (fahrt.getStatus().equals(Status.TRANSPORTING) && ! fahrt.getEndZeit().isAfter(LocalTime.now())) {
            fahrt.setStatus(Status.DONE);
        }
    }
    public Status getStatusByZugNr(int zugNr) {
        Fahrt fahrt = getFahrtByZugNr(zugNr);
        updateStatus(fahrt);
        return fahrt.getStatus();
    }
}
