/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Simon
 */
public class Parser {

    String begrenzer = ";";

    private Parser() {
    }

    public static Parser getInstance() {
        Parser parser = new Parser();
        return parser;
    }

    public String lesen(String socketString) {
        System.out.println("Parser Empfang: " + socketString);
        String antwortString = "Hallo ich bin der Parser";

        String[] gesamtString = null;
        StringTokenizer stringSplit = new StringTokenizer(socketString, begrenzer, true);
        try {
            for (int i = 0; stringSplit.hasMoreTokens() == false; i++) {
                gesamtString[i] = stringSplit.nextToken();
            }
            System.out.println(gesamtString[0]);
            
            switch (gesamtString[0].toUpperCase()) {
                case "REQUEST": {
                    antwortString = "Sie haben eine Transport Anfrage gestellt. ";
                    break;
                }
                case "STATE": {
                    antwortString = "Sie haben eine Status ANfrage gestellt. ";
                    break;
                }
                case "TIME": {
                    antwortString = "Sie haben eine Zeit Anfrage gestellt. ";
                    break;
                }
                default: {
                    antwortString = "ERROR";
                    break;
                }
            }

        } catch (NullPointerException ex) {
            ex.toString();
        }
        return antwortString;
    }

    public void schreibeInLog(String logEintrag) {
        try {
            FileWriter log = new FileWriter("socketLog.txt");
            log.write(logEintrag);
        } catch (IOException ex) {
            System.out.println("Fehler beim Erstellen der Log Datei! ");;
        }

    }
}
