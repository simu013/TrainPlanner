/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

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
    
    private JLabel ipLabel;
    private JLabel portLabel;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JButton einstellenButton;
    
    private JLabel aktuelleIP;
    private JLabel aktuellerPort;
    //Variable zur überprüfung, ob schon einmal eine Nachricht angezeigt wurde bei einer Falscheingabe
    private boolean nachrichtAngezeigt = false;
    
    

    public EinstellungsPanel() {
       initPanel();        
        
    }
    
    private void initPanel() {
        //Einstellungs Panel wird initialisert und weitere Panel hinzugefügt   
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        this.setLayout(new BorderLayout());
        
        addTopPanel();
        addCenterPanel();
    }

    private void addTopPanel() {

        JPanel einstellungsPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        einstellungsPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        ipLabel = new JLabel ("IP Adresse:");
        ipLabel.setForeground(Color.RED);
        einstellungsPanel.add(ipLabel);
        
        ipTextField = new JTextField ();
        ipTextField.setColumns(15);
        ipTextField.setText("127.0.0.1");
        einstellungsPanel.add(ipTextField);
         
        portLabel = new JLabel("Port Nummer:");
        portLabel.setForeground(Color.RED);
        einstellungsPanel.add(portLabel);
        
        portTextField = new JTextField();
        portTextField.setColumns(5);
        portTextField.setText("5555");
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
        centerPanel.setBackground(Color.decode(Einstellungen.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt, IP Adresse wird aus Einstellungsklasse gelesen und angezeigt
        aktuelleIP = new JLabel("aktuell eigestellte IP: " + Einstellungen.getProperty("IP"));
        aktuelleIP.setForeground(Color.red);
        centerPanel.add(aktuelleIP);
        //Port wird aus Einstellungsklasse gelesen und angezeigt
        aktuellerPort = new JLabel("aktuell eingestellter Port: " + Einstellungen.getProperty("PortNr"));
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
                //überprüft ob Textfelder leer sind und gibt allenfalls eine Meldung
                if (ipTextField.getText().trim().isEmpty() | portTextField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);   
                    nachrichtAngezeigt = true;
                }
                
                if (!ipTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    
                    //String wird beim Punkt gesplitet
                    String [] ip = ipTextField.getText().split("[.]");

                    try {
                        //überprüft ob die IP Adresse das richtige Format hat
                        if (ip.length != 4 | ipTextField.getText().lastIndexOf(".") == ipTextField.getText().length()-1){
                            throw new NumberFormatException();
                        }
                        
                        for (int i=0; i<ip.length ; i++){
                           int ipPart = Integer.parseInt(ip[i]);
                           //überprüft ob die einzelnen Parts der IP Adresse im gewünschten Format sind, sonst wird eine Exception geworfen
                           if (ipPart > 255 | ipPart < 0){
                               throw new NumberFormatException();
                           }
                        }

                    //Meldung für das gewünschte Format der IP Adresse   
                    } catch (NumberFormatException a){
                        JOptionPane.showMessageDialog(null,
                        "Bitte gültige IP Adresse (z.B. 192.168.0.1) eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                    }
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
                        Einstellungen.setProperty("IP", ipTextField.getText());
                        Einstellungen.setProperty("PortNr", portTextField.getText());
                        ipTextField.setText("");
                        portTextField.setText("");
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
                aktuelleIP.setText("aktuell eigestellte IP: " + Einstellungen.getProperty("IP"));
                aktuellerPort.setText("aktuell eingestellter Port: " + Einstellungen.getProperty("PortNr"));
            }  
        }
    }
}
            

        
    

    


    
    

