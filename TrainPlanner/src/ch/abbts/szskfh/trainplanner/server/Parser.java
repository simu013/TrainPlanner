/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.FileWriter;
import java.io.IOException;

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
        String antwortString = " ";
        System.out.println("Parser Empfang: " + socketString);

        String[] splitString = socketString.split(";");
        try {
            for (int i = 0; i < splitString.length; i++) {
                
                System.out.println(splitString[i]);
                
            }
            
            switch (splitString[0].toUpperCase()) {
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
