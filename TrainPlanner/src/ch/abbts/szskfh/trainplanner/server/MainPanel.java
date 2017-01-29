/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Sascha
 */
public class MainPanel extends JPanel {

    private JCheckBox emergencyCheckBox;
    private JTextArea logTextArea;
    private ScrollPane logScrollPane;

    public MainPanel() {
        initPanel();
    }

    private void initPanel() {
        this.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addTopPanel();
        addCenterPanel();
        addBottomPanel();
    }

    private void addTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        topPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        JLabel title = new JLabel("Log History");
        title.setForeground(Color.decode(Config.getProperty("Schriftfarbe")));
        topPanel.add(title);
        //Top Panel wird am EinstellungsPanel hinzugefügt
        add(topPanel, BorderLayout.NORTH);
    }

    private void addCenterPanel() {

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));

        emergencyCheckBox = new JCheckBox("Störung");
        emergencyCheckBox.setFocusPainted(false);
        emergencyCheckBox.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        emergencyCheckBox.setForeground(Color.decode(Config.getProperty("Schriftfarbe")));
        emergencyCheckBox.setHorizontalAlignment(JLabel.CENTER);
        if (ServerGUI.getMainFrame().getEmergencyState()) {
            emergencyCheckBox.setSelected(true);
        }
        centerPanel.add(emergencyCheckBox);
        centerPanel.add(emergencyCheckBox);
        MainPanel.MyItemListener listener = new MainPanel.MyItemListener();
        emergencyCheckBox.addItemListener(listener);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logScrollPane = new ScrollPane();
        logScrollPane.add(logTextArea);
        centerPanel.add(logScrollPane);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void addBottomPanel() {

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));

        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    /**
     * Schreibt Text in scrollbares Log-Textfeld. Ab einer gewissen Zeilenanzahl
     * werden automatisch die ältesten Einträge entfernt.
     *
     * @param text String der ausgegeben werden soll.
     */
    public void setAusgabeText(String text) {
        // Überlaufschutz Log Textfeld. Älteste Einträge löschen. 
        int count = -1, maxLines = 20;
        try {
            count = logTextArea.getLineCount();
            if (count > maxLines) {
                logTextArea.replaceRange("", logTextArea.getLineStartOffset(0), logTextArea.getLineEndOffset(3));
            }
        } catch (BadLocationException e1) {

        } catch (IllegalArgumentException e2) {

        }
        logTextArea.append(text + "\n");
    }
    
    class MyItemListener implements ItemListener {

            @Override
            public void itemStateChanged(ItemEvent e) {
                JCheckBox checkbox = (JCheckBox) e.getSource();

                if (checkbox == emergencyCheckBox) {
                    //überprüft ob Textfeld leer ist und gibt allenfalls eine Meldung
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        ServerGUI.getMainFrame().setEmergencyState(true);
                    } else {
                        ServerGUI.getMainFrame().setEmergencyState(false);
                    }
                }
            }
        }
}
