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
    private ArrayList<Fahrt> persohnenFahrten = new ArrayList<Fahrt>();
    private ArrayList<Fahrt> gueterFahrten = new ArrayList<Fahrt>();
    BufferedReader br = null;
    
    /**
     * Konstruktor initialisiert den Fahrplan durch ausführen von iniFahrplan();
     */
    public Fahrplan(){
        iniFahrplan();
    }
    
    /**
     * Initialisiert den Fahrplan aus der datei Fahrten.csv.
     */
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
     * Fügt der ArrayList fahrten eine neue Persohnenfahrt hinzu.
     * @param ZugTyp Zugtyp ("IC" oder "EC").
     * @param eingegebeneZeit gewünste Abfahrtszeit
     * @throws TransportNotPossibleException wenn hinzufügen nicht erfolgreich.
     */
    public void addFahrt(String ZugTyp, LocalTime eingegebeneZeit) throws TransportNotPossibleException{
        
        if(sperrTest(eingegebeneZeit) == true){
            fahrten.add(new Fahrt(ZugTyp, eingegebeneZeit));
            Collections.sort(fahrten);
        }
        else{
            throw new TransportNotPossibleException();
        }
    }
    
    /**
     * Fügt der ArrayList fahrten eine neue Güterfahrt hinzu.
     * @param eingegebeneZeit gewünste Ankunftszeit des Güterzuges.
     * @param wagons Anzahl Wagons des Güterzuges.
     * @throws ch.abbts.szskfh.trainplanner.server.TransportNotPossibleException
     */
    public void addFahrt(LocalTime eingegebeneZeit, int wagons) throws TransportNotPossibleException{
        LocalTime sperrTest = eingegebeneZeit.minusMinutes(22);
        
        if(sperrTest(sperrTest) == true){
            fahrten.add(new Fahrt("GZ",eingegebeneZeit, wagons));
            Collections.sort(fahrten);
        }
        else{
            throw new TransportNotPossibleException();
        }
    }
    
    /**
     * Kontrolliert ob die eingegebene Abfahrtszeit gegen keine Sperrzeit verstöst..
     * @param eingegebeneZeit gewünste Abfahrtszeit des zu prüfenden Zuges.
     * @return sperrTest wenn true verstöst die zeit gegen keine Sperrzeit.
     */
    private boolean sperrTest(LocalTime eingegebeneZeit){
        boolean sperrTest = false;
        for(int i=0; i<fahrten.size(); i++){
            if((eingegebeneZeit.isAfter(fahrten.get(i).getSperrStart())&&(eingegebeneZeit.isBefore(fahrten.get(i).getSperrEnde())))){
                sperrTest = false;
                break;
            }
            else{
                continue;
            }
        }
        return sperrTest;
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
            if((fahrten.get(i).getZugTyp() == "IC")|(fahrten.get(i).getZugTyp()== "EC")){
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
            if((fahrten.get(i).getZugTyp() == "GZ")){
                gueterFahrten.add(fahrten.get(i));
            }
            else{
                continue;
            }
        }
        return gueterFahrten;
    }
    
}