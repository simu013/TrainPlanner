/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sascha
 */
public class MainPanel extends JPanel{
    
    private JLabel title;
    private TopMenuBar topMenuBar;

    public MainPanel() {
        initMainPanel ();
        
    }

    private void initMainPanel() {
        //Main Panel wird initialisert und weitere Panel hinzugefügt
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        this.setLayout(new BorderLayout());
        this.addTopPanel();
        this.addCenterPanel();
        this.addBottomPanel();
        
    }
    
    private void addTopPanel (){
        
        //neues Panel wird erzeugt
        JPanel topPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        topPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));

        //Bild wird hinzugefügt
        ImageIcon tunnel = new ImageIcon ("TitelBild.jpg");
        Image titelBild = tunnel.getImage().getScaledInstance(400, 300, 400);
 
        topPanel.add(new JLabel(new ImageIcon(titelBild)));
        
        //Panel wird hinzugefügt
        add(topPanel, BorderLayout.WEST);
        
        
    }
    
    private void addCenterPanel (){
        //neues Panel wird erzeugt
        JPanel centerPanel = new JPanel (new FlowLayout());
        centerPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        
        //Components werde am Panel hinzugefügt und lokalisiert
        title = new JLabel ("WILLKOMMEN BEI ");
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
        centerPanel.add(title);

        title = new JLabel ("TRAINPLANNER");
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
        centerPanel.add(title);
        
        add(centerPanel, BorderLayout.CENTER);

        
    }
    
    private void addBottomPanel (){
        //neues Panel wird erzeugt
        JPanel bottomPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        bottomPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        
        title = new JLabel ("Planung von Heute!");
        title.setForeground(Color.DARK_GRAY);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(title);
    }
    
}
