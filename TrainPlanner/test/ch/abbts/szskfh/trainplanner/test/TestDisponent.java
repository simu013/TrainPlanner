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
        Disponent dispo = Disponent.getInstance();

        String transportID1 = dispo.addAuftrag("Meier", (short) 21, LocalTime.of(10, 00), (short) 1);
        System.out.println(transportID1);
        try {
            Thread.sleep(1000);
            String transportID2 = dispo.addAuftrag("Meier", (short) 35, LocalTime.of(17, 00), (short) 1);
            System.out.println(transportID2);

            Thread.sleep(1000);
            String transportID3 = dispo.addAuftrag("Mueller", (short) 21, LocalTime.of(11, 00), (short) 1);
            System.out.println(transportID3);

            Thread.sleep(1000);
            String transportID4 = dispo.addAuftrag("Acklin", (short) 21, LocalTime.of(21, 00), (short) 1);
            System.out.println(transportID4);

            System.out.println(dispo.getState(transportID1));
            System.out.println(dispo.getState(transportID2));
            System.out.println(dispo.getState(transportID3));
            System.out.println(dispo.getState(transportID4));
            System.out.println(dispo.getState("blablabla"));

        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(TestDisponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.out.println(ex.toString());
            Logger.getLogger(TestDisponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
