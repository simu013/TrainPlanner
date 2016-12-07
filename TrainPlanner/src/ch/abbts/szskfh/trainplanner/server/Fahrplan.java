/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Florian Haeusermann
 */
public class Fahrplan {
    
    private ArrayList<Fahrt> fahrten = new ArrayList<Fahrt>();
    private Boolean sperrtest; //Wenn true verstöst die neue fahrt gegen keine Sperrzeit
    private LocalTime eingegebeneZeit;
    private ArrayList<Fahrt> persohnenFahrten = new ArrayList<Fahrt>();
    private ArrayList<Fahrt> gueterFahrten = new ArrayList<Fahrt>();
    BufferedReader br = null;
    
    public Fahrplan(){
        iniFahrplan();
    }
    
    public void iniFahrplan(){
        
        String line = "";
        LocalTime csvEingegebeneZeit;
        int csvWagons = 0;
        
        try(BufferedReader br = new BufferedReader(new FileReader("Fahrten.csv"))){
            while ((line = br.readLine())!= null){
                
                String[] wert = null;
                wert = line.split(",");
                String[] part = wert[1].split(":");
                csvEingegebeneZeit = LocalTime.of(Integer.parseInt(part[0]), Integer.parseInt(part[1]));
                
                if(wert.length == 3){
                    csvWagons = Integer.parseInt(wert[2]);
                    fahrten.add(new Fahrt(wert[0], csvEingegebeneZeit, csvWagons));
                
                }
                else{
                    fahrten.add(new Fahrt(wert[0], csvEingegebeneZeit));
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if(br != null){
                try{
                    br.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(fahrten);
    }
    
    /**
     * Fügt der ArrayList fahrten eine neue Fahrt hinzu.
     * @param pZugTyp: Zugtyp ("IC","EC" oder "GZ").
     * @param pStartStunde: Stunde der Startzeit (z.B.: 07:XX).
     * @param pStartMinute: Minute der Startzeit (z.B.: XX:47).
     */
    public void addFahrt(String pZugTyp, int pStartStunde, int pStartMinute){
        sperrtest = true;
        eingegebeneZeit = LocalTime.of(pStartStunde, pStartMinute);
        
        for(int i=0; i<fahrten.size(); i++){
            if((eingegebeneZeit.isAfter(fahrten.get(i).getmSperrStart())&&(eingegebeneZeit.isBefore(fahrten.get(i).getmSperrEnde())))){
                sperrtest = false;
                break;
            }
            else {
                continue;
            }
        }
        if(sperrtest == true){
            fahrten.add(new Fahrt(pZugTyp, eingegebeneZeit));
            Collections.sort(fahrten);
            System.out.println("Fahrt erfolgreich hinzugefügt.");
        }
        else{
            System.out.println("Verstöst gegen Sperrzeit");
        }
    }
    
    /**
     * Fügt der ArrayList fahrten eine neue Fahrt hinzu.
     * @param pZugTyp: Zugtyp ("IC","EC" oder "GZ").
     * @param pStartStunde: Stunde der Startzeit (z.B.: 07:XX).
     * @param pStartMinute: Minute der Startzeit (z.B.: XX:47).
     * @param pWagons Anzahl Wagons des Güterzuges.
     */
    public void addFahrt(String pZugTyp, int pStartStunde, int pStartMinute, int pWagons){
        sperrtest = true;
        eingegebeneZeit = LocalTime.of(pStartStunde, pStartMinute);
        
        for(int i=0; i<fahrten.size(); i++){
            if((eingegebeneZeit.isAfter(fahrten.get(i).getmSperrStart())&&(eingegebeneZeit.isBefore(fahrten.get(i).getmSperrEnde())))){
                sperrtest = false;
                break;
            }
            else {
                continue;
            }
        }
        if(sperrtest == true){
            fahrten.add(new Fahrt(pZugTyp, eingegebeneZeit, pWagons));
            Collections.sort(fahrten);
            System.out.println("Fahrt erfolgreich hinzugefügt.");
        }
        else{
            System.out.println("Verstöst gegen Sperrzeit");
        }
    }
    
    /**
     *Löscht eine beliebige Fahrt aus der ArrayList fahrten.
     * @param i Index der zu löschenden Fahrt.
     */
    public void deleteFahrt(int i){
        fahrten.remove(i);
    }
    
    /**
     * Gibt ein gewünstes Objekt des Typs Fahrt aus.
     * @param i Index der Auzugebenden Fahrt
     * @return Das gewünste Objekt des Typs Fahrt.
     */
    public Fahrt getFahrt(int i){
        return fahrten.get(i);
    }
    
    /**
     * Gibt die ArrayList fahrten aus.
     * @return ArrayList fahrten.
     */
    public ArrayList<Fahrt> getFahrten(){
        return fahrten;
    }
    
    /**
     * Gibt alle Persohnenfahrten aus
     * @return ArrayList persohnenFahrten mit allen erfassten Persohnenfahrten.
    */
    public ArrayList<Fahrt> getPersonenFahrten(){
        persohnenFahrten.clear();
        for(int i=0; i<fahrten.size(); i++){
            if((fahrten.get(i).getmZugTyp() == "IC")|(fahrten.get(i).getmZugTyp()== "EC")){
                persohnenFahrten.add(fahrten.get(i));
            }
            else{
                continue;
            }
        }
        return persohnenFahrten;
    }
    
    /**
     * Gibt alle Güterfahrten aus.
     * @return ArrayList gueterFahrten mit allen erfassten Persohnenfahrten.
     */
    public ArrayList<Fahrt> getGueterFahrten(){
        gueterFahrten.clear();
        for(int i=0; i<fahrten.size(); i++){
            if((fahrten.get(i).getmZugTyp() == "GZ")){
                gueterFahrten.add(fahrten.get(i));
            }
            else{
                continue;
            }
        }
        return gueterFahrten;
    }
    
}