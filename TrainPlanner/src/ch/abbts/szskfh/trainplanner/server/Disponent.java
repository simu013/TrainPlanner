/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Simon
 */
public class Disponent {

    Fahrplan fahrplan = new Fahrplan();
    HashMap<ArrayList<Auftrag>, ArrayList<Container>> auftragsBuch = new HashMap<>();
    ArrayList<Firma> firmen = new ArrayList<>();

    public String addAuftrag(String nameFirma, short anzContainer, LocalTime startZeit, short prio) {
        Auftrag auftrag = new Auftrag(anzContainer, startZeit, prio);
        boolean firmaExistiert = false;
        for (int i = 0; i < firmen.size(); i++) {
            if (firmen.get(i).getName().equals(nameFirma)) {
                firmen.get(i).addAuftrag(auftrag);
                firmaExistiert = true;
            }
        }
        if (firmaExistiert == false) {
            firmen.add(new Firma(nameFirma, auftrag));
        }
        return auftrag.getTransportID();
    }

    public String getState(String transportID) throws NullPointerException {
        String state = null;
        for (int i = 0; i < firmen.size(); i++) {
            for (int j = 0; j < firmen.get(i).getAuftraege().size(); j++) {
                if (transportID.equals(firmen.get(i).getAuftraege().get(j).getTransportID())) {
                    state = "PLANNED";
                }
            }

        }
        return state;
    }
}
