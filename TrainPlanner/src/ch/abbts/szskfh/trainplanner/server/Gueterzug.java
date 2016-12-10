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
public class Gueterzug {
    
    private int anzahlWagons;
    private int freiePlätze = 49;
    private ArrayList<Container> container;
    
    /**
     *
     * @param container Anzahl Container.
     * @throws ch.abbts.szskfh.trainplanner.server.TrainToSmallException Wenn mehr Container als möglich angefügt werden.
     */
    public Gueterzug(int container) throws TrainToSmallException{
        this.container = new ArrayList<Container>();
        if(container <= 49){
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
        int containers = 49;
        containers -= freiePlätze;
        anzahlWagons = containers/2;
        if((containers%2)!=0){
            anzahlWagons++;
        }
        return anzahlWagons;
    }
}

