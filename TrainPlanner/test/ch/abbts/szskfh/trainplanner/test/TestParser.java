/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.test;

import ch.abbts.szskfh.trainplanner.server.Parser;

/**
 *
 * @author Simon
 */
public class TestParser {

    Parser parser = new Parser();

    public static void main(String[] args) {
        TestParser testParser = new TestParser();
    }

    public TestParser() {
        String ganzerStringA = parser.lesen("REQUEST;Mueller;25;12:00;1");
        String[] subStringA = ganzerStringA.split(";");

        for (String subStringA1 : subStringA) {
            System.out.print(subStringA1 + " - ");
        }

        String ganzerStringS = parser.lesen("STATE;" + subStringA[0]);
        String[] subStringS = ganzerStringS.split(";");

        for (String subStringS1 : subStringS) {
            System.out.print(subStringS1 + " - ");
        }

    }
}
