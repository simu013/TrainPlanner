/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sascha
 */
public class FahrplanPanel extends JPanel {

    private JLabel title;

    public FahrplanPanel() {
        initPanel();

    }

    private void initPanel() {
        //Einstellungs Panel wird initialisert und weitere Panel hinzugefügt   
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());

        addTopPanel();
        addCenterPanel();

    }

    private void addTopPanel() {

        JPanel fahrplanPanel = new JPanel(new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        fahrplanPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        title = new JLabel("Fahrplan");
        title.setForeground(Color.decode(Config.getProperty("Schriftfarbe")));
        fahrplanPanel.add(title);
        //Top Panel wird am EinstellungsPanel hinzugefügt
        add(fahrplanPanel, BorderLayout.NORTH);
    }

    /*
     * Hinzufügen des CenterPanels 
     */
    private void addCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 1));
        centerPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        ScrollPane scrollPanel = new ScrollPane();
        scrollPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        JPanel zugList = new JPanel();
        zugList.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        zugList.setLayout(new BoxLayout(zugList, BoxLayout.Y_AXIS));

        for (Fahrt fahrt : ServerGUI.getMainFrame().getFahrplan().getFahrten()) {
            JPanel zugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel zugNrLabel = new JLabel(String.valueOf(fahrt.getZugNr()));
            zugNrLabel.setPreferredSize(new Dimension(100, 20));
            JLabel zugTypLabel = new JLabel(fahrt.getZugtyp().name());
            zugTypLabel.setPreferredSize(new Dimension(150, 20));
            JLabel zugStartZeitLabel = new JLabel(String.valueOf(fahrt.getStartZeit()));
            zugStartZeitLabel.setPreferredSize(new Dimension(50, 20));
            JLabel zugEndZeitLabel = new JLabel(String.valueOf(fahrt.getEndZeit()));
            zugEndZeitLabel.setPreferredSize(new Dimension(50, 20));

            JLabel zugStatusLabel;
            if (fahrt.getZugtyp().equals(Zugtyp.GUETERZUG)) {
                ServerGUI.getMainFrame().getFahrplan().updateStatus(fahrt);
                zugStatusLabel = new JLabel(fahrt.getStatus().name());
            } else {
                zugStatusLabel = new JLabel("");
            }
            
            zugPanel.add(zugNrLabel);
            zugPanel.add(zugTypLabel);
            zugPanel.add(zugStartZeitLabel);
            zugPanel.add(zugEndZeitLabel);
            zugPanel.add(zugStatusLabel);
            zugPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode(Config.getProperty("FrameFarbe"))));
            zugList.add(zugPanel);
        }

        scrollPanel.add(zugList);
        centerPanel.add(scrollPanel);
        add(centerPanel, BorderLayout.CENTER);
    }
}
