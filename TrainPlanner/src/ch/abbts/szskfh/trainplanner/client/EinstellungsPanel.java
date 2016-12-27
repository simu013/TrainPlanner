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
    
    private boolean nachrichtAngezeigt = false;
    
    

    public EinstellungsPanel() {
       initPanel();        
        
    }
    
    private void initPanel() {
                
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        
        addTopPanel();
        addCenterPanel();
        addBottomPanel();
        
      
    }

    private void addTopPanel() {
        
        JPanel einstellungsPanel = new JPanel (new FlowLayout());
        einstellungsPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
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
        
        add(einstellungsPanel, BorderLayout.NORTH);
        
        
    }

    private void addCenterPanel() {
       
        JPanel centerPanel = new JPanel (new FlowLayout());
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        aktuelleIP = new JLabel("aktuell eigestellte IP: " + new Einstellungen().getEinstellung("IP"));
        aktuelleIP.setForeground(Color.red);
        centerPanel.add(aktuelleIP);
        
        aktuellerPort = new JLabel("aktuell eingestellter Port: " + new Einstellungen().getEinstellung("PortNr"));
        aktuellerPort.setForeground(Color.red);
        centerPanel.add(aktuellerPort);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addBottomPanel() {
        
    }


   class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)e.getSource();
            
            if (button == einstellenButton){
                
                if (ipTextField.getText().trim().isEmpty() | portTextField.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);   
                    nachrichtAngezeigt = true;
                }
                
                if (!ipTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    
                    
                    String [] ip = ipTextField.getText().split("[.]");

                    try {
                        
                        if (ip.length != 4 | ipTextField.getText().lastIndexOf(".") == ipTextField.getText().length()-1){
                            throw new NumberFormatException();
                        }
                        
                        for (int i=0; i<ip.length ; i++){
                           int ipPart = Integer.parseInt(ip[i]);
                           
                           if (ipPart > 255 | ipPart < 0){
                               throw new NumberFormatException();
                           }
                        }

                        
                    } catch (NumberFormatException a){
                        JOptionPane.showMessageDialog(null,
                        "Bitte gültige IP Adresse (z.B. 192.168.0.1) eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                    }
                }
                
                if (!portTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    
                    try {
                        Short.parseShort(portTextField.getText()); 
                        
                        if (Short.parseShort(portTextField.getText())<=1023 | Short.parseShort(portTextField.getText())>65536){
                            throw new NumberFormatException();
                        }
                        
                        Einstellungen Einstellungen = new Einstellungen ();
                        Einstellungen.setEinstellung("IP", ipTextField.getText());
                        Einstellungen.setEinstellung("PortNr", portTextField.getText());
                        ipTextField.setText("");
                        portTextField.setText("");
                    } 
                    catch(NumberFormatException a){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Port zwischen 1023 und 65536 eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                        
                    }
                }
                nachrichtAngezeigt = false;

                //Aktualisierung der IP und Port
                aktuelleIP.setText("aktuell eigestellte IP: " + new Einstellungen().getEinstellung("IP"));
                aktuellerPort.setText("aktuell eingestellter Port: " + new Einstellungen().getEinstellung("PortNr"));
            }

                
                
                
            
        }
    }
}
            

        
    

    


    
    

