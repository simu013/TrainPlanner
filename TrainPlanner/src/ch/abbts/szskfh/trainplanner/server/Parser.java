/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Prüft und übersetzt, splittet Nachrichten zugunsten Disponent. Setzt
 * Nachrichten-Fragemente vom Disponent zusammen.
 *
 * @author Simon
 */
public class Parser {

    Einstellungen einstellungen = new Einstellungen();
    Disponent disponent = new Disponent();

    private Parser() {
    }

    public static Parser getInstance() {
        Parser parser = new Parser();
        return parser;
    }

    public String lesen(String socketString) {
        String antwortString = " ";
        System.out.println("Parser Empfang: " + socketString);

        String[] splitString = socketString.split(einstellungen.getEinstellung("SocketTrennzeichen"));
        try {
            for (int i = 0; i < splitString.length; i++) {

                System.out.println(splitString[i]);

            }

            switch (splitString[0].toUpperCase()) {
                case "REQUEST": {
                    try {
                        // Request mit Parameter 'Firma', 'AnzahlContainer', 'Startzeit', 'Prio' an Disponent weitergeben.
                        String nameFirma = splitString[1];
                        short anzahlContainer = Short.parseShort(splitString[2]);
                        LocalTime startZeit = LocalTime.parse(splitString[3]);
                        short prio = Short.parseShort(splitString[4]); 
                        antwortString = disponent.addAuftrag(nameFirma, anzahlContainer, startZeit, prio);

                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                    } catch (DateTimeParseException dtpe) {
                        dtpe.printStackTrace();
                    }
                    break;
                }
                case "STATE": {
                    antwortString = "Sie haben eine Status ANfrage gestellt. ";
                    // State mit Parameter 'Transport ID' an Disponent übergeben. 
                    break;
                }
                case "READY": {
                    antwortString = "OK";
                    break;
                }
                case "TIME": {
                    SimpleDateFormat zeitStempelFormat = new SimpleDateFormat("dd.MM.YYYY mm:ss");
                    antwortString = zeitStempelFormat.format(new Date());
                    break;
                }
                default: {
                    antwortString = "ERROR";
                    break;
                }
            }

        } catch (NullPointerException ex) {
            ex.toString();
        } 
        return antwortString;
    }

    public void schreibeInLog(String logEintrag) {
        try {
            FileWriter log = new FileWriter("socketLog.txt");
            log.write(logEintrag);
        } catch (IOException ex) {
            System.out.println("Fehler beim Erstellen der Log Datei! ");;
        }

    }
}
