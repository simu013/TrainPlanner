/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;

/**
 *  Stellt eine Socket Verbindung mit dem Server her. 
 * @author Simon
 */
public class SocketConnection {

    String begrenzer = Einstellungen.getProperty("SocketTrennzeichen");
    String receiveString = null;

    /**
     * Initialisiert die Socket Verbindung.
     *
     * @param senden String der an Server gesendet werden soll.
     * @return String Antwort des Servers.
     * @throws Exception
     */
    private String initSocketConnection(String senden) throws Exception {

        Socket socket = new Socket(Einstellungen.getProperty("IP"), Integer.parseInt(Einstellungen.getProperty("PortNr")));

        // Streams verbinden
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(senden); // senden
        out.flush();
        receiveString = in.readLine(); // empfangen
        out.println("exit " + new Date().toString());
        out.flush();

        // Streams und Socket schliessen
        in.close();
        out.close();
        socket.close();

        return receiveString;
    }

    /**
     * Sendet eine Transportanfrage (REQUEST) an den Server. Gibt die
     * Transportbestätigung des Servers zurück.
     *
     * @param firma String mit Name der Firma
     * @param anzahlContainer Ganzzahl Anzahl Container
     * @param startZeit Gewünschte Startzeit des Transports (hh:mm)
     * @param prio Ganzzahl zwischen 1 und 3 für Transport Priorität. Prio1 =
     * Höchste; Prio3 = Niedrigste
     * @return Gibt einen String mit Transportbestätigung zurück. TransportID
     * (int);Ankunftszeit (hh:mm);ZugNr (int);Preis (in CHF ohne Rappen) Keine
     * Transportmöglichkeit = NO_TRANSPORT Störungsfall = EMERGENCY
     * @throws java.lang.Exception
     */
    public String sendeTransportanfrage(String firma, short anzahlContainer, LocalTime startZeit, short prio) throws Exception {
        return initSocketConnection("request" + begrenzer + firma + begrenzer + anzahlContainer + begrenzer + startZeit + begrenzer + prio);
    }

    /**
     * Sendet eine Transportzustandsanfrage (STATE) an den Server.
     *
     * @param transportID String Transportauftragsnummer
     * @return String TransportID;Status (Mögliche Zustände: Planned,
     * Transporting, delayed, emergency, done).
     * @throws java.lang.Exception
     */
    public String getTransportStatus(String transportID) throws Exception {
        return initSocketConnection("state" + begrenzer + transportID);
    }

    /**
     * Sendet Statusanfrage an Server
     *
     * @return String Serverstatus
     * @throws Exception
     */
    public String getServerStatus() throws Exception {
        return initSocketConnection("ready");
    }

    /**
     * Sendet Zeitanfrage an Server
     *
     * @return String Serverzeit
     * @throws Exception
     */
    public String getZeit() throws Exception {
        return initSocketConnection("TIME");
    }
}
