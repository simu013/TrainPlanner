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
    
    private Parser() {
    }
    
    public static Parser getInstance() {
        Parser parser = new Parser();
        return parser; 
    }
    public String lesen(String socketString) {
        System.out.println("Parser Empfang: " + socketString);
        String antwortString = "Hallo ich bin der Parser";
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
