/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.util.ArrayList;

/**
 *
 * @author Simon
 */
public class Firma {
    private String name;
    private ArrayList<Auftrag> auftraege = new ArrayList();

    public Firma(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void addAuftrag(Auftrag auftrag) {
        auftraege.add(auftrag);
    }
    public ArrayList<Auftrag> getAuftraege() {
        return auftraege;
    }
}
