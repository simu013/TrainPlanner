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
     * @throws ch.abbts.szskfh.trainplanner.server.TrainToSmallException
     */
    public Fahrplan(){
        try{
            iniFahrplan();
        }catch(Exception e){
            System.out.println("TrainToSmallException bei initialisierung.");
        }
        
    }
    
    /**
     * Initialisiert den Fahrplan aus der datei Fahrten.csv.
     * @throws ch.abbts.szskfh.trainplanner.server.TrainToSmallException
     */
    public void iniFahrplan() throws TrainToSmallException{
        
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
     * @param zugTyp Zugtyp ("IC" oder "EC").
     * @param eingegebeneZeit gewünste Abfahrtszeit
     * @throws TransportNotPossibleException wenn hinzufügen nicht erfolgreich.
     */
    public void addFahrt(String zugTyp, LocalTime eingegebeneZeit) throws TransportNotPossibleException{
        
        if(sperrTest(eingegebeneZeit) == true){
            fahrten.add(new Fahrt(zugTyp, eingegebeneZeit));
            Collections.sort(fahrten);
        }
        else{
            throw new TransportNotPossibleException();
        }
    }
    
    /**
     * Fügt der ArrayList fahrten eine neue Güterfahrt hinzu.
     * @param ankunftsZeit gewünste Ankunftszeit des Güterzuges.
     * @param container Anzahl Wagons des Güterzuges.
     * @throws ch.abbts.szskfh.trainplanner.server.TransportNotPossibleException
     * @throws ch.abbts.szskfh.trainplanner.server.TrainToSmallException
     */
    public void addFahrt(LocalTime ankunftsZeit, int container) throws TransportNotPossibleException, TrainToSmallException{
        LocalTime abfahrtsZeit = ankunftsZeit.minusMinutes(22); //Die zur Ankunftszeit gehörende Abfahrtszeit
        
        if(sperrTest(abfahrtsZeit) == true){
            fahrten.add(new Fahrt("GZ",ankunftsZeit, container));
            Collections.sort(fahrten);
        }
        else{
            throw new TransportNotPossibleException();
        }
    }
    
    /**
     * Mit Hilfe dieser Methode, kann man einen Auftrag hizufügen. Fals der Auftrag hinzugefügt wird, wird die Ankunftszeit ausgegeben.
     * Ist es nicht möglich den Auftrag hinzuzufügen, wird eine TransportNotPossibleException geworfen.
     * @param ankunftsZeit die gewünste ankunftszeit des Auftrages.
     * @param container die Anzahl zu Transportierende Container.
     * @return ankunftGueterZug die definitive Ankunftszeit des Auftrages.
     * @throws TransportNotPossibleException Fals kein Transport im gewünsten Zeitraum möglich ist.
     * @throws TrainToSmallException Falls mehr als 98 Container hinzugefügt werden.
     */
    public LocalTime addAuftrag(LocalTime ankunftsZeit, int container) throws TransportNotPossibleException, TrainToSmallException{
        boolean auftragHinzugefügt = false;
        LocalTime ankunftGueterZug = ankunftsZeit;
        
        //Probiere Auftrag bestehender Fahrt zuzuordnen;
        try{
            ankunftGueterZug = tryToAddContainers(ankunftsZeit,container);
            auftragHinzugefügt = true;
        }
        catch(TransportNotPossibleException e){
            for(int i = 0; i < 40; i++){
                try{
                    addFahrt(ankunftGueterZug, container);
                    auftragHinzugefügt = true;
                    break;
                }
                catch(TransportNotPossibleException ex){
                    ankunftGueterZug = ankunftGueterZug.minusMinutes(3);
                    continue;
                }
            }
        }
        
        if(!auftragHinzugefügt){
            throw new TransportNotPossibleException();
        }
        return ankunftGueterZug;
    }
    
    /**
     * Kontrolliert ob die eingegebene Abfahrtszeit gegen keine Sperrzeit verstöst.
     * @param eingegebeneZeit gewünste Abfahrtszeit des zu prüfenden Zuges.
     * @return abfahrtsZeit wenn true verstöst die Zeit gegen keine Sperrzeit.
     */
    private boolean sperrTest(LocalTime eingegebeneZeit){
        boolean sperrTest = true;
        
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
     * Überprüft, ob bereits ein Güterzug mit der nötigen Kapazität,
     * im Zeitraum von der gewünsten Ankunftszeit bis 2 Stunden früher vorhanden ist.
     * Wenn möglich, gibt diese Methode den Index der entsprechenden Fahrt aus.
     * Wenn nicht möglich, gibt diese Methode einen int aus der gleich gross wie die länge der ArrayList fahrten ist (IndexOutOfBound)
     * @param eingegebeneZeit Die gewünste Ankunftszeit des Güterzuges.
     * @param container Anzahl zu transportierender container
     * @return ankunftsZeit Die Ankunftszzeit des Zuges.
     */
    public LocalTime tryToAddContainers(LocalTime eingegebeneZeit, int container) throws TransportNotPossibleException{
        LocalTime endeZeitraum = eingegebeneZeit.minusMinutes(22);
        LocalTime startZeitraum = endeZeitraum.minusHours(2);
        LocalTime zugZeit; //Startzeit des Jeweiligen zu testenden Zuges
        LocalTime ankunftsZeit = null;
        int zugIndex = fahrten.size()*3;
        
        for(int i = 0; i<fahrten.size(); i++){
            zugZeit = fahrten.get(i).getStartZeit();
            if ((zugZeit.isAfter(startZeitraum))&(zugZeit.isBefore(endeZeitraum))){
                if(fahrten.get(i).getZugTyp().equals("GZ")){
                    try{
                        fahrten.get(i).getGueterZug().addContainer(container);
                        ankunftsZeit = fahrten.get(i).getEndZeit();
                        break;
                    }
                    catch(TrainToSmallException e){
                        continue;
                    }
                }
                else{
                    continue;
                }
            }
            else{
                continue;
            }
        }
        if(ankunftsZeit == null){
            throw new TransportNotPossibleException();
        }
        return ankunftsZeit;
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