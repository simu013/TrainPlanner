/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author Simon
 */
public class SocketConnection {
    Einstellungen einstellungen = new Einstellungen();
    String begrenzer = einstellungen.getEinstellung("SocketTrennzeichen");

    public SocketConnection() {

    }
    
    String receiveString = null;

    private String initSocketConnection(String senden) {
        try {
                Socket socket = new Socket(einstellungen.getEinstellung("IP"), Integer.parseInt(einstellungen.getEinstellung("PortNr")));

                // Streams verbinden
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                
                out.println(senden); // senden
                out.flush();
                receiveString = in.readLine(); // empfangen
                System.out.println("empfangen: " + receiveString); // Daten vom Server zu Testzwecken auf Console ausgeben. 
                out.println("exit " + new Date().toString());
                out.flush();

                // Streams und Socket schliessen
                in.close();
                out.close();
                socket.close();

        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return receiveString;
    }
    /**
     * Sendet eine Transportanfrage (REQUEST) an den Server. 
     * Gibt die Transportbestätigung des Servers zurück. 
     * @param firma             String mit Name der Firma
     * @param anzahlContainer   Ganzzahl Anzahl Container
     * @param startZeit         Gewünschte Startzeit des Transports (hh:mm)
     * @param prio              Ganzzahl zwischen 1 und 3 für Transport Priorität. 
     *                          Prio1 = Höchste; Prio3 = Niedrigste
     * @return                  Gibt einen String mit Transportbestätigung zurück. 
     *                          TransportID (int);Ankunftszeit (hh:mm);ZugNr (int);Preis (in CHF ohne Rappen)
     *                          Keine Transportmöglichkeit = NO_TRANSPORT
     *                          Störungsfall = EMERGENCY
     */
    public String sendeTransportanfrage(String firma, short anzahlContainer, LocalTime startZeit, short prio) {
        return initSocketConnection("request" + begrenzer + firma + begrenzer + anzahlContainer + begrenzer + startZeit + begrenzer + prio);
    }
    /**
     * Sendet eine Transportzustandsanfrage (STATE) an den Server. 
     * @param transportID       String Transportauftragsnummer
     * @return                  String TransportID;Status (Mögliche Zustände: Planned, Transporting, delayed, emergency, done). 
     */
    public String getTransportStatus(String transportID) {
        return initSocketConnection("state" + begrenzer + transportID);
    }
    
    public String getServerStatus() {
        return initSocketConnection("ready");
    }
    
    public String getZeit() {
        return initSocketConnection("TIME");
    }
}
