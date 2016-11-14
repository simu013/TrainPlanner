/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Simon
 */
public class Server {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        short portNr = 5432;
        String receiveString = null;
        String connect = null;
        
        // Socket bereitstellen
        try {
            serverSocket = new ServerSocket(portNr); 
        } 
        catch (IOException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        
        // Auf Client Verbindung warten
        try {
            System.out.println("READY");
            Socket socket = serverSocket.accept();
            
            if (socket.isBound()) {
                System.out.println("OK");
            }
                
            // IO Streams verbinden
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            
            // Datenaustausch
            do {
                out.println("Hallo ich bin der Server"); // senden
                out.flush();
                receiveString = in.readLine(); // Daten von CLient empfangen
                
                System.out.println("empfangen: " + receiveString); // Daten von Client zum testen auf Console ausgeben. 
                
            } 
            while(receiveString.equals("exit") == false); // Verbindung halten bis EXIT geliefert wird. 
            
            // Socket und Streams schliessen. 
            in.close();
            out.close();
            socket.close();
            
        } 
        catch (IOException ex) { // Ausnahmen abfangen 
            System.out.println("Exception: " + ex.toString());
        }
    }
}
