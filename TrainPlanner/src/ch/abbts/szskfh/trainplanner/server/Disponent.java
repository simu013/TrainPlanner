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
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Verwaltet Firmen, Aufträge und Fahrplan.
 *
 * @author Simon
 */
public class Disponent {

    private Fahrplan fahrplan = new Fahrplan();
    private HashMap<String, Firma> firmen = new HashMap<>(); // Name der Firma, Firma Objekt
    private static final Disponent disponent = new Disponent(); // Disponent als Singleton

    /**
     * Initialisiert den Fahrplan und sorgt für die Umsetzung des Disponenten
     * als Singleton.
     */
    private Disponent() {
        initFahrplan();
    }

    /**
     * Stellt Disponent als Singleton zur Verfügung.
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
                    fahrplan.addFahrt(new Fahrt(Zugtyp.PERSONENZUG, LocalTime.parse(subString[1], DateTimeFormatter.ofPattern("HH:mm")),
                            LocalTime.parse(subString[1], DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(Config.getIntProperty("PersonenzugDauer")), fahrplan.getZugNr()));
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
     * Eröffnet einen neuen Auftrag
     *
     * @param nameFirma String Name der Firma
     * @param anzContainer Short Anzahl zu transportierender Container
     * @param startZeit LocalTime Abfahrtszeit in hh:mm
     * @param prio Short Priorität (Mögliche Zustände: Höchste Prio = 1;
     * mittlere Prio = 2; niedrige Prio = 3)
     * @return String Transport ID
     */
    public Auftrag addAuftrag(String nameFirma, short anzContainer, LocalTime startZeit, short prio) {
        Auftrag auftrag = addAuftragZuFirma(nameFirma, anzContainer, startZeit, prio);
        if (!new DisponentHelper().planeAuftrag(auftrag, fahrplan)) {
            deleteAuftragVonFirma(auftrag);
            return null;
        }
        return auftrag;
    }

    public Auftrag addAuftragZuFirma(String nameFirma, short anzContainer, LocalTime startZeit, short prio) {
        Auftrag auftrag = new Auftrag(anzContainer, startZeit, prio);
        getFirma(nameFirma).addAuftrag(auftrag);
        return auftrag;
    }

    public void deleteAuftragVonFirma(Auftrag auftrag) {
        for (Entry<String, Firma> entry : firmen.entrySet()) {
            if (entry.getValue().getAuftraege().contains(auftrag)) {
                entry.getValue().getAuftraege().remove(auftrag);
            }
        }
    }

    private Firma addFirma(String name) {
        Firma firma = new Firma(name);
        firmen.put(name, firma);
        return firma;
    }

    private Firma getFirma(String name) {
        if (firmen.containsKey(name)) {
            return firmen.get(name);
        }
        return addFirma(name);
    }

    /**
     * Liefert den Transportstatus eines Auftrags
     *
     * @param transportID String Transport ID des Auftrags
     * @return String Status (Mögliche Zustände: PLANNED, TRANSPORTING, DONE,
     * EMERGENCY, DELAYED)
     * @throws NullPointerException Wenn Transport ID nicht existiert.
     */
    public Status getState(String transportID) throws NullPointerException {
        Status state = null;
        for (Entry<String, Firma> firma : firmen.entrySet()) {
            for (Auftrag auftrag : firma.getValue().getAuftraege()) {
                if (auftrag.getTransportID().equals(transportID)) {
                    state = fahrplan.getStatusByZugNr(auftrag.getZugNr());
                }
            }
        }
        return state;
    }

    public LocalTime getAnkunftszeitByZugNr(int zugNr) {
        return fahrplan.getFahrtByZugNr(zugNr).getEndZeit();
    }
    public Fahrplan getFahrplan() {
        return fahrplan;
    }
}
