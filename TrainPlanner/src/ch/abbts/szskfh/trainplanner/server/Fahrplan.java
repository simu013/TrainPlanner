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

    public ArrayList<Fahrt> getFahrtenByZugTyp(ZugtypEnum zugTyp) {
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

    public void updateStatus(Fahrt fahrt) {
        if (fahrt.getStatus() == StatusEnum.EMERGENCY) {
            fahrt.setStatus(StatusEnum.EMERGENCY);
        } else {
            if (fahrt.getStatus() == null) {
                fahrt.setStatus(StatusEnum.PLANNED);
            }
            if (fahrt.getStatus().equals(StatusEnum.PLANNED) && !fahrt.getStartZeit().isAfter(LocalTime.now())) {
                fahrt.setStatus(StatusEnum.TRANSPORTING);
            }
            if (fahrt.getStatus().equals(StatusEnum.TRANSPORTING) && !fahrt.getEndZeit().isAfter(LocalTime.now())) {
                fahrt.setStatus(StatusEnum.DONE);
            }
        }
    }

    public StatusEnum getStatusByZugNr(int zugNr) {
        Fahrt fahrt = getFahrtByZugNr(zugNr);
        updateStatus(fahrt);
        return fahrt.getStatus();
    }
}
