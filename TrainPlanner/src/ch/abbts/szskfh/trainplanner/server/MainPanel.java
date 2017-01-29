/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Sascha
 */
public class MainPanel extends JPanel {

    private JLabel ausgabeLabel;
    private JTextArea logTextArea;
    private ScrollPane logScrollPane;
    private boolean nachrichtAngezeigt = false;

    public MainPanel() {
        initPanel();
    }

    private void initPanel() {
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addCenterPanel();
        addBottomPanel();
    }

    private void addCenterPanel() {

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

        ausgabeLabel = new JLabel("Log History:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        logScrollPane = new ScrollPane();
        logScrollPane.add(logTextArea);
        centerPanel.add(logScrollPane);

        add(centerPanel, BorderLayout.CENTER);

    }

    private void addBottomPanel() {

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

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
}
