/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Sascha
 */
public class EinstellungsPanel extends JPanel{
     
    private JLabel portLabel;
    private JTextField portTextField;
    private JButton einstellenButton;
    
    private JLabel aktuellerPort;
    
    private boolean nachrichtAngezeigt = false;
    
    

    public EinstellungsPanel() {
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
                
        JPanel einstellungsPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        einstellungsPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        portLabel = new JLabel("Port Nummer:");
        portLabel.setForeground(Color.RED);
        einstellungsPanel.add(portLabel);
        
        portTextField = new JTextField();
        portTextField.setColumns(5);
        einstellungsPanel.add(portTextField);
        
        einstellenButton = new JButton("einstellen");
        einstellenButton.setEnabled(true);
        einstellungsPanel.add(einstellenButton);
        EinstellungsPanel.MyActionListener listener = new EinstellungsPanel.MyActionListener ();
        einstellenButton.addActionListener(listener);
        //Top Panel wird am EinstellungsPanel hinzugefügt
        add(einstellungsPanel, BorderLayout.NORTH);   
    }
    
    private void addCenterPanel() {
        
        JPanel centerPanel = new JPanel (new FlowLayout());      
        //Farbe wird aus der Einstellungsklasse gelesen
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt, IP Adresse wird aus Einstellungsklasse gelesen und angezeigt
        //Port wird aus Einstellungsklasse gelesen und angezeigt
        aktuellerPort = new JLabel("aktuell eingestellter Port: " + new Einstellungen().getEinstellung("PortNr"));
        aktuellerPort.setForeground(Color.red);
        centerPanel.add(aktuellerPort);
        //Center Panel wird am EinstellungsPanel hinzugefügt
        add(centerPanel, BorderLayout.CENTER);
    }

 

   class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)e.getSource();
            
            if (button == einstellenButton){
                //überprüft ob Textfeld leer ist und gibt allenfalls eine Meldung
                if (portTextField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,
                    "Bitte Feld ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);   
                    nachrichtAngezeigt = true;
                }
                
                if (!portTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    //Port wird versucht in einen Int zu parsen 
                    try {
                        Short.parseShort(portTextField.getText()); 
                        //Port Adresse wird auf gewünschtes Format geprüft
                        if (Short.parseShort(portTextField.getText())<=1023 | Short.parseShort(portTextField.getText())>65536){
                            throw new NumberFormatException();
                        }
                        //Wenn das Format stimmt, werden die IP und der Port in die Einstellungen geschrieben und somit gespeichert
                        Einstellungen Einstellungen = new Einstellungen();
                        Einstellungen.setEinstellung("PortNr", portTextField.getText());
                    } 
                    //Meldung für das gewünschte Format des Ports
                    catch(NumberFormatException a){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Port zwischen 1023 und 65536 eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                    }
                }
                nachrichtAngezeigt = false;
                
                //Aktualisierung der IP und Port auf der Anzeige
                aktuellerPort.setText("aktuell eingestellter Port: " + new Einstellungen().getEinstellung("PortNr"));
            }  
        }
    }
}
