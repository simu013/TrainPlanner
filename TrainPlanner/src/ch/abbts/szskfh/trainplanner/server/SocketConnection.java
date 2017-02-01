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
 *
 * @author Simon
 */
public class SocketConnection {

    Controller controller;

    public SocketConnection(Controller c) {
        controller = c;
        initSocketConnection();
    }

    /**
     * Hört auf eingehende Verbindungsanfragen. Initialisiert die Socket
     * Verbindung.
     */
    private void initSocketConnection() {
        Parser parser = new Parser(controller);

        // Socket bereitstellen 
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(Einstellungen.getProperty("PortNr")));) {

            while (true) {
                // Auf Client Verbindung warten
                Socket socket = serverSocket.accept();

                // IO Streams verbinden
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Datenaustausch
                do {

                    String empfangsString = in.readLine();      // Daten von CLient empfangen
                    out.write(parser.lesen(empfangsString));
                    out.flush();

                } while (socket.isClosed()); // Verbindung halten bis Socket von Client geschlossen wird. 
                System.out.println("Socket closed");

                // Socket und Streams schliessen. 
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            controller.schreibeInLogDatei("Socket Fehler; " + e.getMessage());
        }
    }
}
