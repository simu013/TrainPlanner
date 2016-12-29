/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 *
 * @author Florian
 */
public class Gueterzug extends Transporteinheit{
    
    private int anzahlWagons;
    private int freiePlätze = 98;
    private ArrayList<Container> container = new ArrayList<>();
    
    /**
     *  Konstruktor für einen neuen Güterzug.
     * @param container Anzahl Container.
     * @throws TrainToSmallException Wenn mehr Container als möglich hinzugefügt werden.
     */
    public Gueterzug(int container) throws TrainToSmallException{
        super.gewicht = 0;
        super.laenge = 0;
        
        if(container <= 98){
            anzahlWagons = container/2;
            for(int i = 0;  i < container; i++){
                this.container.add(new Container());
                freiePlätze --;
            }
        }
        else{
            throw new TrainToSmallException();
        }
    }
    
    public void addContainer(int container) throws TrainToSmallException{
        if(container <= freiePlätze){
            for(int i = 0 ; i < container ; i++){
                this.container.add(new Container ());
                freiePlätze --;
            }
        }
        else{
            throw new TrainToSmallException();
        }
    }
    
    /**
     *  Gibt das gesamt Gewicht der Zugkomposition in Tonnen aus.
     * @return gewicht
     */
    @Override
    public float getGewicht(){
        float gewichtWagons = 0;
        float gewichtContainers = 0;
        final float gewichtLoks = (float)170.8; //Gewicht von 2 Loks in Tonnen (je 85,4 T).
        gewicht = 0;
        
        gewichtWagons = ((float)getAnzahlWagons())*((float)13.5);
        for(int i=0 ; i<container.size() ; i++){
            gewichtContainers += container.get(i).getGewicht();
        }
        gewicht = gewichtLoks + gewichtWagons + gewichtContainers;
        return gewicht;
    }
    
    /**
     *  Gibt die gesamt Länge der Zugkomposition in Meter aus.
     * @return laenge
     */
    @Override
    public short getLaenge(){
        short laengeWagons = 0;
        final short laengeLoks = (short) 37.8; //Länge von 2 Loks in Meter (je 19,8 M).
        laengeWagons = (short)(getAnzahlWagons()*14.5);
        laenge = (short) (laengeLoks + laengeWagons);
        return laenge;
    }
    
    /**
     *  Gibt die Anzahl freie Plätze auf dem Zug aus
     * @return freiePlätze
     */
    public int getFreiePlätze(){
        return freiePlätze;
    }
    
    /**
     * Gibt Anzahl Wagons des zuges aus.
     * @return anzahlWagons, die Anzahl der Güterwagons.
     */
    public int getAnzahlWagons(){
        int containers = 98;
        containers -= freiePlätze;
        anzahlWagons = containers/2;
        if((containers%2)!=0){
            anzahlWagons++;
        }
        return anzahlWagons;
    }
}

