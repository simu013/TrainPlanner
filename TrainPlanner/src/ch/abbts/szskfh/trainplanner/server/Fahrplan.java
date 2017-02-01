/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Stellt einen Fahrplan mit allen aktuellen Zugverbindungen zur Verfügung.
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

    /**
     * Gibt die Zug Nummer zurück
     *
     * @return int Zug Nummer
     */
    public int getZugNr() {
        return zugNr;
    }

    /**
     * Fügt dem Fahrplan eine neue Fahrt hinzu.
     *
     * @param fahrt Fahrt Objekt Zugfahrt.
     */
    public void addFahrt(Fahrt fahrt) {
        ++zugNr;
        fahrten.add(fahrt);
    }

    /**
     * Gibt alle Zugfahrten des Fahrplans zurück.
     *
     * @return ArrayList mit Fahrt Objekten.
     */
    public ArrayList<Fahrt> getFahrten() {
        return fahrten;
    }

    /**
     * Gibt alle Fahrten eines Zugtyps zurück.
     *
     * @param zugTyp ZugtypEnum Zugtyp
     * @return ArrayList mit Fahrt Objekten.
     */
    public ArrayList<Fahrt> getFahrtenByZugTyp(ZugtypEnum zugTyp) {
        ArrayList<Fahrt> personenFahrten = new ArrayList<>();
        for (int i = 0; i < fahrten.size(); i++) {
            if (fahrten.get(i).getZugtyp().equals(zugTyp)) {
                personenFahrten.add(fahrten.get(i));
            }
        }
        return personenFahrten;
    }

    /**
     * Gibt eine bestimmte Fahrt anhand der ZugNummer zurück
     *
     * @param zugNr Zug Nummer
     * @return Bestehende Fahrt welche die übergebene Zug Nummer hat. null wenn
     * diese nicht existiert.
     */
    public Fahrt getFahrtByZugNr(int zugNr) {
        for (Fahrt fahrt : fahrten) {
            if (fahrt.getZugNr() == zugNr) {
                return fahrt;
            }
        }
        return null;
    }

    /**
     * Aktualisiert den Zugstatus anhand der aktuellen Zeit und anderer
     * Planungsfaktoren.
     *
     * @param fahrt Fahrt von welcher der Status aktualisiert werden soll
     */
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

    /**
     * Gibt den Status anhand der Zugnummer zurück.
     *
     * @param zugNr Zug Nummer
     * @return Status Enum Status der Fahrt. null wenn diese nicht existiert.
     */
    public StatusEnum getStatusByZugNr(int zugNr) {
        Fahrt fahrt = getFahrtByZugNr(zugNr);
        updateStatus(fahrt);
        return fahrt.getStatus();
    }
}
