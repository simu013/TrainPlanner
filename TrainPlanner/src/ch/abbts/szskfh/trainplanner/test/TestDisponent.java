/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.server.Auftrag;
import ch.abbts.szskfh.trainplanner.server.Disponent;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class TestDisponent {

    public static void main(String[] args) {
        Disponent dispo = new Disponent();
        String auftrag1 = dispo.addAuftrag("Meier", (short) 21, LocalTime.of(10, 00), (short) 1);
        System.out.println(auftrag1);
        try {
            Thread.sleep(3000);
            String auftrag2 = dispo.addAuftrag("Meier", (short) 35, LocalTime.of(10, 00), (short) 1);
            System.out.println(auftrag2);

            Thread.sleep(3000);
            String auftrag3 = dispo.addAuftrag("Mueller", (short) 21, LocalTime.of(11, 00), (short) 1);
            System.out.println(auftrag3);

            Thread.sleep(2000);
            String auftrag4 = dispo.addAuftrag("Acklin", (short) 21, LocalTime.of(12, 00), (short) 1);
            System.out.println(auftrag4);

            System.out.println(dispo.getState(auftrag1));
            System.out.println(dispo.getState(auftrag2));
            System.out.println(dispo.getState(auftrag3));
            System.out.println(dispo.getState(auftrag4));
            System.out.println(dispo.getState("blablabla"));

        } catch (InterruptedException ex) {
            Logger.getLogger(TestDisponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
