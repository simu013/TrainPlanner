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
import java.awt.Font;
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

    public FahrplanPanel() {
        initPanel();

    }

    private void initPanel() {
        //Einstellungs Panel wird initialisert und weitere Panel hinzugefügt   
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        this.setLayout(new BorderLayout());

        addTopPanel();
        addCenterPanel();
        addBottomPanel();

    }

    private void addTopPanel() {

        JPanel fahrplanPanel = new JPanel(new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        fahrplanPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        JLabel title = new JLabel("Fahrplan");
        title.setForeground(Color.decode(Einstellungen.getProperty("Schriftfarbe")));
        fahrplanPanel.add(title);
        //Top Panel wird am EinstellungsPanel hinzugefügt
        add(fahrplanPanel, BorderLayout.NORTH);
    }

    private void addCenterPanel() {

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        JLabel dummieLabel = new JLabel("");
        dummieLabel.setForeground(Color.decode(Einstellungen.getProperty("Schriftfarbe")));
        ScrollPane scrollPanel = new ScrollPane();
        scrollPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        scrollPanel.add(addZugListPanel());
        centerPanel.add(dummieLabel);
        centerPanel.add(scrollPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addBottomPanel() {

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));

        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    /**
     * Stellt Fahrplan anhand einer Zugliste zur Verfügung.
     *
     * @return JPanel Zugliste
     */
    private JPanel addZugListPanel() {
        JPanel zugList = new JPanel();
        Fahrplan fahrplan = ServerGUI.getMainFrame().getFahrplan();
        zugList.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        zugList.setLayout(new BoxLayout(zugList, BoxLayout.Y_AXIS));

        // Titelzeile
        JPanel zugTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel zugNrTitleLabel = new JLabel("Zug Nummer");
        zugNrTitleLabel.setPreferredSize(new Dimension(100, 20));
        JLabel zugTypTitleLabel = new JLabel("Zugtyp");
        zugTypTitleLabel.setPreferredSize(new Dimension(150, 20));
        JLabel zugStartZeitTitleLabel = new JLabel("Abfahrtszeit");
        zugStartZeitTitleLabel.setPreferredSize(new Dimension(100, 20));
        JLabel zugEndZeitTitleLabel = new JLabel("Ankunftszeit");
        zugEndZeitTitleLabel.setPreferredSize(new Dimension(100, 20));
        JLabel zugStatusTitleLabel = new JLabel("Zugstatus");
        zugStatusTitleLabel.setPreferredSize(new Dimension(100, 20));
        // Titelzeile der Liste zufügen
        zugTitlePanel.add(zugNrTitleLabel);
        zugTitlePanel.add(zugTypTitleLabel);
        zugTitlePanel.add(zugStartZeitTitleLabel);
        zugTitlePanel.add(zugEndZeitTitleLabel);
        zugTitlePanel.add(zugStatusTitleLabel);
        zugList.add(zugTitlePanel);

        // Neue Zeile pro Fahrt generieren und der Zugliste zuweisen. 
        for (Fahrt fahrt : fahrplan.getFahrten()) {
            JPanel zugPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel zugNrLabel = new JLabel(String.valueOf(fahrt.getZugNr()));
            zugNrLabel.setPreferredSize(new Dimension(100, 20));
            JLabel zugTypLabel = new JLabel(fahrt.getZugtyp().name());
            zugTypLabel.setPreferredSize(new Dimension(150, 20));
            JLabel zugStartZeitLabel = new JLabel(String.valueOf(fahrt.getStartZeit()));
            zugStartZeitLabel.setPreferredSize(new Dimension(100, 20));
            JLabel zugEndZeitLabel = new JLabel(String.valueOf(fahrt.getEndZeit()));
            zugEndZeitLabel.setPreferredSize(new Dimension(100, 20));

            zugPanel.add(zugNrLabel);
            zugPanel.add(zugTypLabel);
            zugPanel.add(zugStartZeitLabel);
            zugPanel.add(zugEndZeitLabel);
            zugPanel.add(addZugStatusLabel(fahrt, fahrplan));
            zugPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode(Einstellungen.getProperty("FrameFarbe"))));
            zugList.add(zugPanel);
        }
        return zugList;
    }

    /**
     * Gibt den Zugstatus des Fahrt Objekts zurück
     *
     * @param fahrt Fahrt Objekt für das der Status benötigt wird.
     * @return JLabel mit Zugstatus
     */
    private JLabel addZugStatusLabel(Fahrt fahrt, Fahrplan fahrplan) {
        fahrplan.updateStatus(fahrt);
        return new JLabel(fahrt.getStatus().name());
    }
}
