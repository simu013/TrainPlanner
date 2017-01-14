/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.client.SocketConnection;
import java.net.ConnectException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class TestServer {

    SocketConnection socket = new SocketConnection();

    public static void main(String[] args) {
        TestServer testServer = new TestServer();
    }

    public TestServer() {
        try {
            String ganzerStringA = socket.sendeTransportanfrage("Mueller", (short) 25, LocalTime.of(12, 0), (short) 1);

            String[] subStringA = ganzerStringA.split(";");

            for (String subStringA1 : subStringA) {
                System.out.print(subStringA1 + " - ");
            }
            System.out.println(" \n Transport ID: " + subStringA[0]);
            

            String ganzerStringS = socket.getTransportStatus(subStringA[0]);
            String[] subStringS = ganzerStringS.split(";");
            System.out.println("");
            for (String subStringS1 : subStringS) {
                System.out.print(subStringS1 + " - ");
            }
            
        } catch (ConnectException ex) {
            System.out.println("Keine Antwort vom Server");

        } catch (Exception ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

