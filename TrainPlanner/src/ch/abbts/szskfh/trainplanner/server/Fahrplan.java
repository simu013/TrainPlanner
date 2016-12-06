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
    
    private ArrayList<Fahrt> mFahrten = new ArrayList<Fahrt>();
    private Boolean mSperrtest; //Wenn true verstöst die neue fahrt gegen keine Sperrzeit
    private LocalTime eingegebeneZeit;
    private ArrayList<Fahrt> mPersohnenFahrten = new ArrayList<Fahrt>();
    private ArrayList<Fahrt> mGueterFahrten = new ArrayList<Fahrt>();
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
                    mFahrten.add(new Fahrt(wert[0], csvEingegebeneZeit, csvWagons));
                
                }
                else{
                    mFahrten.add(new Fahrt(wert[0], csvEingegebeneZeit));
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
        Collections.sort(mFahrten);
    }
    
    /**
     * Fügt der ArrayList mFahrten eine neue Fahrt hinzu.
     * @param pZugTyp: Zugtyp ("IC","EC" oder "GZ").
     * @param pStartStunde: Stunde der Startzeit (z.B.: 07:XX).
     * @param pStartMinute: Minute der Startzeit (z.B.: XX:47).
     */
    public void addFahrt(String pZugTyp, int pStartStunde, int pStartMinute){
        mSperrtest = true;
        eingegebeneZeit = LocalTime.of(pStartStunde, pStartMinute);
        
        for(int i=0; i<mFahrten.size(); i++){
            if((eingegebeneZeit.isAfter(mFahrten.get(i).getmSperrStart())&&(eingegebeneZeit.isBefore(mFahrten.get(i).getmSperrEnde())))){
                mSperrtest = false;
                break;
            }
            else {
                continue;
            }
        }
        if(mSperrtest == true){
            mFahrten.add(new Fahrt(pZugTyp, eingegebeneZeit));
            Collections.sort(mFahrten);
            System.out.println("Fahrt erfolgreich hinzugefügt.");
        }
        else{
            System.out.println("Verstöst gegen Sperrzeit");
        }
    }
    
    /**
     * Fügt der ArrayList mFahrten eine neue Fahrt hinzu.
     * @param pZugTyp: Zugtyp ("IC","EC" oder "GZ").
     * @param pStartStunde: Stunde der Startzeit (z.B.: 07:XX).
     * @param pStartMinute: Minute der Startzeit (z.B.: XX:47).
     * @param pWagons Anzahl Wagons des Güterzuges.
     */
    public void addFahrt(String pZugTyp, int pStartStunde, int pStartMinute, int pWagons){
        mSperrtest = true;
        eingegebeneZeit = LocalTime.of(pStartStunde, pStartMinute);
        
        for(int i=0; i<mFahrten.size(); i++){
            if((eingegebeneZeit.isAfter(mFahrten.get(i).getmSperrStart())&&(eingegebeneZeit.isBefore(mFahrten.get(i).getmSperrEnde())))){
                mSperrtest = false;
                break;
            }
            else {
                continue;
            }
        }
        if(mSperrtest == true){
            mFahrten.add(new Fahrt(pZugTyp, eingegebeneZeit, pWagons));
            Collections.sort(mFahrten);
            System.out.println("Fahrt erfolgreich hinzugefügt.");
        }
        else{
            System.out.println("Verstöst gegen Sperrzeit");
        }
    }
    
    /**
     *Löscht eine beliebige Fahrt aus der ArrayList mFahrten.
     * @param i Index der zu löschenden Fahrt.
     */
    public void deleteFahrt(int i){
        mFahrten.remove(i);
    }
    
    /**
     * Gibt ein gewünstes Objekt des Typs Fahrt aus.
     * @param i Index der Auzugebenden Fahrt
     * @return Das gewünste Objekt des Typs Fahrt.
     */
    public Fahrt getmFahrt(int i){
        return mFahrten.get(i);
    }
    
    /**
     * Gibt die ArrayList mFahrten aus.
     * @return ArrayList mFahrten.
     */
    public ArrayList<Fahrt> getmFahrten(){
        return mFahrten;
    }
    
    /**
     * Gibt alle Persohnenfahrten aus
     * @return ArrayList mPersohnenFahrten mit allen erfassten Persohnenfahrten.
    */
    public ArrayList<Fahrt> getPersonenFahrten(){
        mPersohnenFahrten.clear();
        for(int i=0; i<mFahrten.size(); i++){
            if((mFahrten.get(i).getmZugTyp() == "IC")|(mFahrten.get(i).getmZugTyp()== "EC")){
                mPersohnenFahrten.add(mFahrten.get(i));
            }
            else{
                continue;
            }
        }
        return mPersohnenFahrten;
    }
    
    /**
     * Gibt alle Güterfahrten aus.
     * @return ArrayList mGueterFahrten mit allen erfassten Persohnenfahrten.
     */
    public ArrayList<Fahrt> getGueterFahrten(){
        mGueterFahrten.clear();
        for(int i=0; i<mFahrten.size(); i++){
            if((mFahrten.get(i).getmZugTyp() == "GZ")){
                mGueterFahrten.add(mFahrten.get(i));
            }
            else{
                continue;
            }
        }
        return mGueterFahrten;
    }
    
}