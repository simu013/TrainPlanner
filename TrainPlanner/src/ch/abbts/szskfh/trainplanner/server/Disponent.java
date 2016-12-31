/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verwaltet Firmen, Aufträge und Fahrplan.
 *
 * @author Simon
 */
public class Disponent {

    private Fahrplan fahrplan = new Fahrplan();
    private HashMap<ArrayList<Auftrag>, ArrayList<Container>> auftraege = new HashMap<>();
    private ArrayList<Firma> firmen = new ArrayList<>();
    private static final Disponent disponent = new Disponent();

    /**
     * Iitialisiert den Fahrplan und sorgt für die Umsetzung  des Disponenten als Singleton. 
     */
    private Disponent() {
        initFahrplan();
    }

    /**
     * Stellt Disponent als Singelton zur Verfügung.
     *
     * @return Disponent Objekt
     */
    public static Disponent getInstance() {
        return disponent;
    }

    /**
     * Initialisiert den Fahrplan aus der PersonenFahrten.csv Datei
     */
    public void initFahrplan() {
        String line;
        String dateiName = "PersonenFahrten.csv";
        String csvTrennzeichen = ",";

        try {

            BufferedReader readFile = new BufferedReader(new FileReader(dateiName));

            while ((line = readFile.readLine()) != null) {
                String[] subString = line.split(csvTrennzeichen);

                if (subString[0].equals("IC") | subString[0].equals("EC")) {
                    fahrplan.addFahrt(Zugtyp.PERSONENZUG, LocalTime.parse(subString[1], DateTimeFormatter.ofPattern("HH:mm")), LocalTime.parse(subString[1], DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Datei nicht gefunden. ");
            Logger.getLogger(Disponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Datei konnte nicht gelesen werden. ");
            Logger.getLogger(Disponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException e) {
            System.out.println("Nullpointer Exception");
            Logger.getLogger(Disponent.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * TEST METHODE! Dient dazu bei Tests den Fahrplan zur Verfügung zu stellen.
     *
     * @return Gibt eine ArrayList mit allen Fahrt Objekten des Fahrplans
     * zurück.
     */
    public ArrayList<Fahrt> getFahrten() {
        return fahrplan.getFahrten();
    }

    /**
     * Eröffnet einen neuen Auftrag
     *
     * @param nameFirma String Name der Firma
     * @param anzContainer Short Anzahl zu transportierender Container
     * @param startZeit LocalTime Abfahrtszeit in hh:mm
     * @param prio Short Priorität (Mögliche Zustände: Höchste Prio = 1;
     * mittlere Prio = 2; niedrige Prio = 3)
     * @return String Transport ID
     */
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

    /**
     * Liefert den Transportstatus eines Auftrags
     *
     * @param transportID String Transport ID des Auftrags
     * @return String Status (Mögliche Zustände: PLANNED, TRANSPORTING, DONE,
     * EMERGENCY, DELAYED)
     * @throws NullPointerException Wenn Transport ID nicht existiert.
     */
    public String getState(String transportID) throws NullPointerException {
        String state = null;
        for (int i = 0; i < firmen.size(); i++) {
            for (int j = 0; j < firmen.get(i).getAuftraege().size(); j++) {

                if (transportID.equals(firmen.get(i).getAuftraege().get(j).getTransportID())) {
                    //Status PLANNED wenn Start Zeit nach aktuelle Zeit
                    if (firmen.get(i).getAuftraege().get(j).getStartZeit().isAfter(LocalTime.now())) {
                        state = "PLANNED";
                    }
                    if (firmen.get(i).getAuftraege().get(j).getStartZeit().isBefore(LocalTime.now())) {
                        if (firmen.get(i).getAuftraege().get(j).getStartZeit().plusHours(2).isBefore(LocalTime.now())) {
                            state = "DONE";
                        }
                        if (firmen.get(i).getAuftraege().get(j).getStartZeit().plusHours(2).isAfter(LocalTime.now())) {
                            state = "TRANSPORTING";
                        }
                    }
                }
            }

        }
        return state;
    }
}
