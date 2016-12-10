/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

/**
 *
 * @author Florian
 */
public class TrainToSmallException extends Exception {
    TrainToSmallException(){
        super("Der Zug ist zu klein!");
    }
}
