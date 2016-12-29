/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.client.Einstellungen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class TestSocket {
        Einstellungen einstellungen = new Einstellungen();
        String begrenzer = einstellungen.getEinstellung("SocketTrennzeichen");
        String receiveString = null;
        
    public static void main(String[] args) throws IOException {
        new TestSocket();
    }

    private TestSocket() {
            try {
                System.out.println(initSocketConnection("STATE" + begrenzer +  "20161224133001172"));
            } catch (IOException ex) {
                Logger.getLogger(TestSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    private String initSocketConnection(String senden) throws IOException {

        Socket socket = new Socket(einstellungen.getEinstellung("IP"), Integer.parseInt(einstellungen.getEinstellung("PortNr")));

        // Streams verbinden
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(senden); // senden
        out.flush();
        receiveString = in.readLine(); // empfangen
        System.out.println("Client Empfang: " + receiveString); // Daten vom Server zu Testzwecken auf Console ausgeben. 
        out.println("exit " + new Date().toString());
        out.flush();

        // Streams und Socket schliessen
        in.close();
        out.close();
        socket.close();
        
        return receiveString;
    }

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

