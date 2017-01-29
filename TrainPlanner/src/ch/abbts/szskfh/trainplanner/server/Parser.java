/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * Prüft, übersetzt und splittet Nachrichten für Disponent. Setzt
 * Nachrichten-Fragmente vom Disponent zusammen.
 *
 * @author Simon
 */
public class Parser {

    private Einstellungen einstellungen = new Einstellungen();
    private String begrenzer = einstellungen.getEinstellung("SocketTrennzeichen");
    private SimpleDateFormat zeitStempelFormat = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
    private Controller controller;

    public Parser(Controller controller) {
        this.controller = controller;
    }

    /**
     * Liest neue Strings ein. Prüft, übersetzt und splittet zugunsten
     * Disponent.
     *
     * @param socketString String der übersetzt werden soll.
     * @return String Antwort für Socket.
     */
    public String lesen(String socketString) {
        String antwortString = "";

        String[] splitString = socketString.split(begrenzer);
        controller.schreibeInGui(socketString);

        try {

            switch (splitString[0].toUpperCase()) {
                case "REQUEST": {
                    try {
                        // Request mit Parameter 'Firma', 'AnzahlContainer', 'Startzeit', 'Prio' an Disponent weitergeben.
                        String nameFirma = splitString[1];
                        short anzahlContainer = Short.parseShort(splitString[2]);
                        LocalTime startZeit = LocalTime.parse(splitString[3]);
                        short prio = Short.parseShort(splitString[4]);
                        Auftrag auftrag = controller.addAuftrag(nameFirma, anzahlContainer, startZeit, prio);
                        // Antwort mit 'TransportID', 'Ankunftszeit', 'ZugNr', 'Preis' an Client. 
                        antwortString = auftrag.getTransportID() + begrenzer + controller.getAnkunftszeitByZugNr(auftrag.getZugNr()).toString() + begrenzer + prio + begrenzer + (auftrag.getAnzahlContainer() * 25); // Statische Übergabe zu Testzwecken

                    } catch (NumberFormatException e) {
                        antwortString = "ERROR" + begrenzer + "Nummern Eingabefehler";
                        schreibeInLog(antwortString + begrenzer + e.getMessage());
                    } catch (DateTimeParseException e) {
                        antwortString = "ERROR" + begrenzer + "Datum Eingabefehler";
                        schreibeInLog(antwortString + begrenzer + e.getMessage());
                    }
                    break;
                }
                case "STATE": {
                    // State mit Parameter 'Transport ID' an Disponent übergeben. 
                    try {
                        antwortString = splitString[1] + begrenzer + controller.getStatus(splitString[1]).toString();
                        System.out.println("Antwort: " + antwortString);
                    } catch (NullPointerException e) {
                        antwortString = "ERROR" + begrenzer + "Ungültige TransportID";
                        schreibeInLog(antwortString + begrenzer + e.getLocalizedMessage());
                    }
                    break;
                }
                case "READY": {
                    antwortString = "OK";
                    break;
                }
                case "TIME": {
                    antwortString = zeitStempelFormat.format(new Date());
                    break;
                }
                default: {
                    antwortString = "ERROR" + begrenzer + "Anfrage wird nicht unterstützt";
                    schreibeInLog(antwortString);
                    break;
                }
            }

        } catch (NullPointerException e) {
            antwortString = "ERROR" + begrenzer + "Leere Anfrage";
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            schreibeInLog(antwortString + begrenzer + e.getMessage());
        }
        controller.schreibeInGui(antwortString);
        return antwortString;
    }

    /**
     * Schreibt Fehler und Ausnahmen in eine Log-Datei. Der Log-Eintrag besteht
     * jeweils aus dem Zeitstempel im Format dd.MM.YYYY hh:mm:ss sowie dem
     * übergebenen logEintrag.
     *
     * @param logEintrag String Eintrag für Log-Datei.
     */
    public void schreibeInLog(String logEintrag) {

        try {
            BufferedWriter log = new BufferedWriter(new FileWriter("socketLog.txt", true));
            log.write(zeitStempelFormat.format(new Date()) + " " + logEintrag);
            log.newLine();
            log.close();
        } catch (IOException ex) {
            System.out.println(zeitStempelFormat.format(new Date()) + " " + "Fehler beim Schreiben der Log Datei! ");
        }
    }
}
