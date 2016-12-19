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
                        String transportID = disponent.addAuftrag(nameFirma, anzahlContainer, startZeit, prio);
                        // Antwort mit 'TransportID', 'Ankunftszeit', 'ZugNr', 'Preis' an Client. 
                        antwortString = transportID + einstellungen.getEinstellung("SocketTrennzeichen") + (startZeit.plusHours(2)) 
                                + einstellungen.getEinstellung("SocketTrennzeichen") + "01" + einstellungen.getEinstellung("SocketTrennzeichen") + (anzahlContainer*25);

                    } catch (NumberFormatException e) {
                        System.out.println(e.toString());
                        antwortString = "ERROR" + einstellungen.getEinstellung("SocketTrennzeichen") + "Nummern Eingabefehler";
                    } catch (DateTimeParseException e) {
                        System.out.println(e.toString());
                        antwortString = "ERROR" + einstellungen.getEinstellung("SocketTrennzeichen") + "Datum Eingabefehler";
                    }
                    break;
                }
                case "STATE": {
                    // State mit Parameter 'Transport ID' an Disponent übergeben. 
                    try {
                        antwortString = splitString[1] + einstellungen.getEinstellung("SocketTrennzeichen") + disponent.getState(splitString[1]);
                    } catch (NullPointerException e) {
                        System.out.println(e.toString());
                        antwortString = "ERROR" + einstellungen.getEinstellung("SocketTrennzeichen") + "Ungültige TransportID";
                    }
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
                    antwortString = "ERROR" + einstellungen.getEinstellung("SocketTrennzeichen") + "Anfrage wird nicht unterstützt";
                    break;
                }
            }

        } catch (NullPointerException e) {
            System.out.println(e.toString());
            antwortString = "ERROR" + einstellungen.getEinstellung("SocketTrennzeichen") + "Leere Anfrage";
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
