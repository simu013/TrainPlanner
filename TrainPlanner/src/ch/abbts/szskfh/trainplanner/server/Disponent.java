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
    private HashMap<String, ArrayList<Container>> auftraege = new HashMap<String, ArrayList<Container>>();
    private HashMap<String, Firma> firmen = new HashMap<String, Firma>();
    private static final Disponent disponent = new Disponent();

    /**
     * Iitialisiert den Fahrplan und sorgt für die Umsetzung des Disponenten als
     * Singleton.
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
        Auftrag auftrag = addAuftragZuFirma(nameFirma, anzContainer, startZeit, prio);
        return auftrag.getTransportID();
    }

    public Auftrag addAuftragZuFirma(String nameFirma, short anzContainer, LocalTime startZeit, short prio) {
        Auftrag auftrag = new Auftrag(anzContainer, startZeit, prio);
        getFirma(nameFirma).addAuftrag(auftrag);
        return auftrag;
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

    /**
     * Erstellt eine ArrayList mit der angegebenen Anzahl Container.
     *
     * @param anzahlContainer int Anzahl Container die angelegt werden sollen.
     * @return ArrayList mit Container Objekten.
     */
    private ArrayList<Container> createContainers(int anzahlContainer, float laenge, float gewicht, float maxLadung) {
        ArrayList<Container> containers = new ArrayList<>();
        for (int i = 0; i < anzahlContainer; i++) {
            containers.add(new Container(laenge, gewicht, maxLadung));
        }
        return containers;
    }

    private void verladeGueter(int zugNr, ArrayList<Container> containers) {
        float wagonGewicht = (float) 13.5; // Gewicht eines Güterwagons in Tonnen
        float wagonLaenge = (float) 14.5; // Länge eines Güterwagons in Meter

        for (Fahrt fahrt : fahrplan.getFahrten()) {
            if (fahrt.getGueterzug().getZugNr() == zugNr) {
                for (int containerCounter = containers.size(); containerCounter > 0; containerCounter--) {
                    Gueterzug gueterzug = fahrt.getGueterzug();
                    ArrayList<Gueterwagon> gueterwagons = gueterzug.getGueterwagons();

                    if (gueterwagons != null) {
                        for (int i = 0; i < gueterwagons.size(); i++) {
                            boolean b;
                            do {
                                b = gueterwagons.get(i).addContainer(wagonLaenge, wagonGewicht, wagonLaenge);
                                if (b == true) {
                                    --containerCounter;
                                }
                            } while (b);
                        }
                    }
                    Gueterwagon wagon = new Gueterwagon(wagonGewicht, wagonLaenge);
                    boolean a;
                    do {
                        a = wagon.addContainer(wagonLaenge, wagonGewicht, wagonLaenge);
                        if (a == true) {
                            --containerCounter;
                        }
                    } while (a);
                    gueterwagons.add(wagon);

                }
            }
        }
    }

}
