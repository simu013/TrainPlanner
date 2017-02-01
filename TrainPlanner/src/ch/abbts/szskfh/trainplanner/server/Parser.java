/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

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

    private String begrenzer = Einstellungen.getProperty("SocketTrennzeichen");
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

        String[] splitString = controller.trenneString(socketString);
        controller.schreibeInGui(socketString);

        try {

            switch (AnfrageTypEnum.valueOf(splitString[0].toUpperCase())) {
                case REQUEST: {
                    if (!controller.getEmergencyState()) {
                        try {
                            // Request mit Parameter 'Firma', 'AnzahlContainer', 'Startzeit', 'Prio' an Disponent weitergeben.
                            String nameFirma = splitString[1];
                            short anzahlContainer = Short.parseShort(splitString[2]);
                            LocalTime startZeit = LocalTime.parse(splitString[3]);
                            short prio = Short.parseShort(splitString[4]);
                            Auftrag auftrag = controller.addAuftrag(nameFirma, anzahlContainer, startZeit, prio);
                            if (auftrag == null) {
                                antwortString = Status.NO_TRANSPORT.toString();
                            } else {
                                // Antwort mit 'TransportID', 'Ankunftszeit', 'ZugNr', 'Preis' an Client. 
                                antwortString = auftrag.getTransportID() + begrenzer + controller.getAnkunftszeitByZugNr(auftrag.getZugNr()).toString() + begrenzer + prio + begrenzer + controller.berechnePreis(auftrag);
                            }
                        } catch (NumberFormatException e) {
                            antwortString = AnfrageTypEnum.ERROR.name() + begrenzer + "Nummern Eingabefehler";
                            controller.schreibeInLogDatei(antwortString + begrenzer + e.getMessage());
                        } catch (DateTimeParseException e) {
                            antwortString = AnfrageTypEnum.ERROR.name() + begrenzer + "Datum Eingabefehler";
                            controller.schreibeInLogDatei(antwortString + begrenzer + e.getMessage());
                        }
                        break;
                    }
                    antwortString = Status.EMERGENCY.toString();
                    break;
                }
                case STATE: {
                    // State mit Parameter 'Transport ID' an Disponent übergeben. 
                    try {
                        antwortString = splitString[1] + begrenzer + controller.getStatus(splitString[1]).toString();
                        System.out.println("Antwort: " + antwortString);
                    } catch (NullPointerException e) {
                        antwortString = AnfrageTypEnum.ERROR.name() + begrenzer + "Ungültige TransportID";
                        controller.schreibeInLogDatei(antwortString + begrenzer + e.getMessage());
                    }
                    break;
                }
                case READY: {
                    antwortString = "OK";
                    break;
                }
                case TIME: {
                    antwortString = controller.getZeitFormat().format(new Date());
                    break;
                }
                default: {
                    antwortString = AnfrageTypEnum.ERROR.name() + begrenzer + "Anfrage wird nicht unterstützt";
                    controller.schreibeInLogDatei(antwortString + begrenzer + "Anfrage: " + socketString);
                    break;
                }
            }

        } catch (NullPointerException e) {
            antwortString = AnfrageTypEnum.ERROR.name() + begrenzer + "Leere Anfrage";
            controller.schreibeInLogDatei(antwortString + begrenzer + e.getMessage());
        }
        controller.schreibeInGui(antwortString);
        return antwortString;
    }
}
