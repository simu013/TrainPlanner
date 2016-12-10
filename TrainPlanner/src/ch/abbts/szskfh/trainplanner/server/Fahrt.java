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
    
    private String zugTyp;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private LocalTime sperrStart;
    private LocalTime sperrEnde;
    
    private final int dauerPZ = 18; //Fahrdauer IC/EC in min.
    private final int dauerGZ = 22; //Fahrdauer GZ in min.
    
    /**
     * Constructor für IC und EC
     * @param zugTyp Zugtyp IC oder EC.
     * @param eingegebeneZeit Startzeit des Zuges in Arth-Goldau
     */
    public Fahrt(String zugTyp, LocalTime eingegebeneZeit) {
             
        if(zugTyp.equals("IC")){
            this.zugTyp = zugTyp;
            this.startZeit = eingegebeneZeit.plusMinutes(17); //Durchfahrtszeit Erstfeld
            this.endZeit = startZeit.plusMinutes(dauerPZ); //Ausfahrtszeit aus GBT
            this.sperrStart = startZeit.minusMinutes(8); //Sperrzeit Vor dem PZ
            this.sperrEnde = startZeit.plusMinutes(3); //Sperrzeit nach dem PZ
        }
        else if(zugTyp.equals("EC")){
            this.zugTyp = zugTyp;
            this.startZeit = eingegebeneZeit.plusMinutes(18); //Durchfahrtszeit Erstfeld
            this.endZeit = startZeit.plusMinutes(dauerPZ); //Ausfahrtszeit aus GBT
            this.sperrStart = startZeit.minusMinutes(8); //Sperrzeit Vor dem PZ
            this.sperrEnde = startZeit.plusMinutes(3); //Sperrzeit Vor dem PZ
        }
        else{
            System.out.println("Falscher Zugtyp");
        }
    }
    
    /**
     * Constructor für GZ.
     * @param zugTyp Zugtyp GZ
     * @param eingegebeneZeit Ankunftszeit des Zuges an der GBT-Südseite..
     * @param containers Anzahl der Container.
     */
    public Fahrt(String zugTyp, LocalTime eingegebeneZeit, int containers) {
        if(zugTyp.equals("GZ")){
            this.zugTyp = zugTyp;
            this.endZeit = eingegebeneZeit; //Durchfahrtszeit Erstfeld
            this.startZeit = startZeit.minusMinutes(dauerGZ); //Ausfahrtszeit aus GBT
            this.sperrStart = startZeit; //Sperrzeit vor GZ = Sperrzeit nach letstem Zug
            this.sperrEnde = startZeit.plusMinutes(3); //Sperrzeit nach GZ = 3min
            //Gueterzug gueterzug = new Gueterzug(containers);
        }
        else{
            System.out.println("Falscher Zugtyp");
        }
    }
    
    public String getZugTyp() {
        return zugTyp;
    }

    public LocalTime getStartZeit() {
        return startZeit;
    }

    public LocalTime getEndZeit() {
        return endZeit;
    }

    public LocalTime getSperrStart() {
        return sperrStart;
    }

    public LocalTime getSperrEnde() {
        return sperrEnde;
    }

    @Override
    public String toString() {
        return "Durchfahrt Erstfeld:   " + startZeit + ";   Ende Tunnel:  " + endZeit+";    Durchfahrt Gesperrt von: " + sperrStart + " bis " + sperrEnde + "    Zug Typ:  "+zugTyp;
    }
    
    @Override
    public int compareTo(Fahrt o) {
        return getStartZeit().compareTo(o.getStartZeit());
    }
    
}
