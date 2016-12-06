/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.time.LocalTime;

/**
 *
 * @author Florian Haeusermann
 */
public class Fahrt implements Comparable<Fahrt>{
    
    private String mZugTyp;
    private LocalTime mStartZeit;
    private LocalTime mEndZeit;
    private LocalTime mSperrStart;
    private LocalTime mSperrEnde;
    
    private final int dauerPZ = 18; //Fahrdauer IC/EC in min.
    private final int dauerGZ = 22; //Fahrdauer GZ in min.
    
    /**
     * Constructor für IC und EC
     * @param pZugTyp Zugtyp IC oder EC.
     * @param pEingegebeneZeit Startzeit des Zuges in Arth-Goldau
     */
    public Fahrt(String pZugTyp, LocalTime pEingegebeneZeit) {
             
        if(pZugTyp.equals("IC")){
            mZugTyp = pZugTyp;
            mStartZeit = pEingegebeneZeit.plusMinutes(17); //Durchfahrtszeit Erstfeld
            mEndZeit = mStartZeit.plusMinutes(dauerPZ); //Ausfahrtszeit aus GBT
            mSperrStart = mStartZeit.minusMinutes(8); //Sperrzeit Vor dem PZ
            mSperrEnde = mStartZeit.plusMinutes(3); //Sperrzeit nach dem PZ
        }
        else if(pZugTyp.equals("EC")){
            mZugTyp = pZugTyp;
            mStartZeit = pEingegebeneZeit.plusMinutes(18); //Durchfahrtszeit Erstfeld
            mEndZeit = mStartZeit.plusMinutes(dauerPZ); //Ausfahrtszeit aus GBT
            mSperrStart = mStartZeit.minusMinutes(8); //Sperrzeit Vor dem PZ
            mSperrEnde = mStartZeit.plusMinutes(3); //Sperrzeit Vor dem PZ
        }
        else{
            System.out.println("Falscher Zugtyp");
        }
    }
    
    /**
     * Constructor für GZ.
     * @param pZugTyp Zugtyp GZ
     * @param pEingegebeneZeit Startzeit des Zuges in Arth-Goldau.
     * @param pWagons Anzahl der Wagons des Güterzuges.
     */
    public Fahrt(String pZugTyp, LocalTime pEingegebeneZeit, int pWagons) {
        if(pZugTyp.equals("GZ")){
            mZugTyp = pZugTyp;
            mStartZeit = pEingegebeneZeit; //Durchfahrtszeit Erstfeld
            mEndZeit = mStartZeit.plusMinutes(dauerGZ); //Ausfahrtszeit aus GBT
            mSperrStart = mStartZeit; //Sperrzeit vor GZ = Sperrzeit nach letstem Zug
            mSperrEnde = mEndZeit.plusMinutes(3); //Sperrzeit nach GZ = 3min
            Gueterzug mGZ = new Gueterzug(pWagons);
        }
        else{
            System.out.println("Falscher Zugtyp");
        }
    }
    
    public String getmZugTyp() {
        return mZugTyp;
    }

    public LocalTime getmStartZeit() {
        return mStartZeit;
    }

    public LocalTime getmEndZeit() {
        return mEndZeit;
    }

    public LocalTime getmSperrStart() {
        return mSperrStart;
    }

    public LocalTime getmSperrEnde() {
        return mSperrEnde;
    }

    @Override
    public String toString() {
        return "Durchfahrt Erstfeld:   " + mStartZeit + ";   Ende Tunnel:  " + mEndZeit+";    Durchfahrt Gesperrt von: " + mSperrStart + " bis " + mSperrEnde + "    Zug Typ:  "+mZugTyp;
    }
    
    @Override
    public int compareTo(Fahrt o) {
        return getmStartZeit().compareTo(o.getmStartZeit());
    }
    
}
