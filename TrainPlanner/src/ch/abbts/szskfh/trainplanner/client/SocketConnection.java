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

/**
 *
 * @author Simon
 */
public class SocketConnection {
    public SocketConnection() {
        initSocketConnection();
    }
    String receiveString = null;
    private void initSocketConnection() {
        try {
            Socket socket = new Socket(new Einstellungen().getEinstellung("IP"), Integer.parseInt(new Einstellungen().getEinstellung("PortNr")));

            // Streams verbinden
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println("Hallo ich bin der Client"); // senden
            receiveString = in.readLine(); // empfangen
            System.out.println("empfangen: " + receiveString); // Daten vom Server zu Testzwecken auf Console ausgeben. 
            out.println("exit");
            out.flush();            
            
            // Streams und Socket schliessen
            in.close();
            out.close();
            socket.close();
            
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        }
}
