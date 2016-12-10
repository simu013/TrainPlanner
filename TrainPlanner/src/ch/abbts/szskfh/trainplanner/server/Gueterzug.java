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
    private int plätze = 49;
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
                plätze --;
            }
        }
        else{
            throw new TrainToSmallException();
        }
    }
    
    public void addContainer(int container) throws TrainToSmallException{
        if(container <= plätze){
            for(int i = 0 ; i < container ; i++){
                this.container.add(new Container ());
                plätze --;
            }
        }
        else{
            throw new TrainToSmallException();
        }
    }
    /**
     * Gibt Anzahl Wagons aus.
     * @return anzahlWagons, die Anzahl der Güterwagons.
     */
    public int getAnzahlWagons(){
        int kapazität = 49;
        anzahlWagons = kapazität - plätze;
        return anzahlWagons;
    }
}

