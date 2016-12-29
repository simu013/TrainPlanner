/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.server.Controller;
import java.time.LocalTime;

/**
 *
 * @author Simon
 */
public class TestController {
    
    public static void main(String[] args) {
        Controller c = new Controller();
        System.out.println(c.addAuftrag("Meier", (short) 21, LocalTime.of(8, 0), (short) 1));
    }
}
