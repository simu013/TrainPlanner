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
    private Gueterzug gueterZug = null;
    
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
    public Fahrt(String zugTyp, LocalTime eingegebeneZeit, int containers) throws TrainToSmallException {
        this.zugTyp = zugTyp;
        this.endZeit = eingegebeneZeit; //Durchfahrtszeit Erstfeld
        this.startZeit = this.endZeit.minusMinutes(dauerGZ); //Ausfahrtszeit aus GBT
        this.sperrStart = startZeit.minusMinutes(3); //Sperrzeit vor GZ = Sperrzeit nach letstem Zug
        this.sperrEnde = startZeit.plusMinutes(3); //Sperrzeit nach GZ = 3min
        gueterZug = new Gueterzug(containers);
    }
    
    /**
     * Gibt den Zugtyp aus.
     * @return zugTyp
     */
    public String getZugTyp() {
        return zugTyp;
    }

    /**
     * Gibt die Startzeit des Zuges aus.
     * @return startZeit die Startzeit des Zuges.
     */
    public LocalTime getStartZeit() {
        return startZeit;
    }

    /**
     * Gibt die Ankunftszeit des Zuges aus.
     * @return endZeit die Ankunftszeit des Zuges.
     */
    public LocalTime getEndZeit() {
        return endZeit;
    }

    /**
     * Gibt den Anfang der Sperrzeit des Zuges aus.
     * @return sperrStart der start der Sperrzeit des Zuges.
     */
    public LocalTime getSperrStart() {
        return sperrStart;
    }

    /**
     * Gibt das Ende der Sperrzeit des Zuges aus.
     * @return sperrEnde das ende der Sperrzeit des Zuges.
     */
    public LocalTime getSperrEnde() {
        return sperrEnde;
    }
    
    public Gueterzug getGueterZug(){
        return gueterZug;
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
