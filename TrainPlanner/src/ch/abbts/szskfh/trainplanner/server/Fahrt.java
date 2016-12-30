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
public class Fahrt {
    
    private Gueterzug gueterzug;
    private LocalTime startZeit;
    private LocalTime endZeit;
    private String status;
    private Zugtyp zugTyp;
    
    public Fahrt() {

    }
    
}
