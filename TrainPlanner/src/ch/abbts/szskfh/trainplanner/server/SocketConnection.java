/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Erstellt eine neue Socket Verbindung. 
 * @author Simon
 */
public class SocketConnection {
    
    public SocketConnection() {
        initSocketConnection();
    }
    private void initSocketConnection() {
        ServerSocket serverSocket = null;
        Parser parser = Parser.getInstance();
        
        // Socket bereitstellen
        try {
            serverSocket = new ServerSocket(Integer.parseInt(new Einstellungen().getEinstellung("PortNr"))); 
        } 
        catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        while (true) {
            // Auf Client Verbindung warten
            try {
                Socket socket = serverSocket.accept();

                // IO Streams verbinden
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Datenaustausch
                do {
                    
                    String empfangsString = in.readLine();      // Daten von CLient empfangen
                    out.write (parser.lesen(empfangsString));
                    out.flush();

                } while (socket.isClosed()); // Verbindung halten bis Socket von Client geschlossen wird. 
                System.out.println("Socket closed");
                
                // Socket und Streams schliessen. 
                in.close();
                out.close();
                socket.close();
            } catch (IOException ex) {
                ex.toString();
            } finally{
            }
        }
    }
}
