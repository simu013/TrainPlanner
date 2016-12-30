/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Florian Haeusermann
 */
public class Fahrplan {
    
    private ArrayList<Fahrt> fahrten = new ArrayList<>();
    
    /**
     * Konstruktor initialisiert den Fahrplan durch ausführen von iniFahrplan();
     * 
     */
    public Fahrplan(){
        
            initFahrplan();
        
    }
    
    /**
     * Initialisiert den Fahrplan aus der datei Fahrten.csv.
     * 
     */
    public void initFahrplan() {
        
        String line = "";
        LocalTime startZeit;
        
        try(BufferedReader br = new BufferedReader(new FileReader("PersonenFahrten.csv"))){
            while ((line = br.readLine())!= null){
                
                String[] subString = line.split(",");
                String[] zeitString = subString[1].split(":");
                startZeit = LocalTime.of(Integer.parseInt(zeitString[0]), Integer.parseInt(zeitString[1]));
                
                fahrten.add(new Fahrt());
            }
        } catch (FileNotFoundException e) {
            new File("PersonenFahrten.csv");
        } catch (IOException e){
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }        
        
        try(BufferedReader br = new BufferedReader(new FileReader("GueterFahrten.csv"))){
            while ((line = br.readLine())!= null){
                
                String[] wert = null;
                wert = line.split(",");
                String[] part = wert[0].split(":");
                startZeit = LocalTime.of(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
               
            }
        }
        catch (FileNotFoundException e){
            new File("GueterFahrten.csv");
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }    
}