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
public class TransportNotPossibleException extends Exception{

    public TransportNotPossibleException() {
        super("Der Transport ist zur angegebenen Zeit nicht möglich!");
    }
}
