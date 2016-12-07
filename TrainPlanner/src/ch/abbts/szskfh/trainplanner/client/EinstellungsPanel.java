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
    
    private boolean nachrichtAngezeigt = false;
    
    

    public EinstellungsPanel() {
       initPanel();
        
    }
    
    private void initPanel() {
                
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addPanel();
                
    }

    
    private void addPanel() {
        
        JPanel einstellungsPanel = new JPanel (new FlowLayout());
        einstellungsPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ipLabel = new JLabel ("IP Adresse:");
        ipLabel.setForeground(Color.RED);
        einstellungsPanel.add(ipLabel);
        
        ipTextField = new JTextField ();
        ipTextField.setColumns(15);
        einstellungsPanel.add(ipTextField);
         
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
        
        add(einstellungsPanel);
        
        
        
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
                        Einstellungen Einstellungen = new Einstellungen ();
                        Einstellungen.setEinstellung("IP", ipTextField.getText());
                        
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
                        
                        if (Short.parseShort(portTextField.getText())<=1023 | Short.parseShort(portTextField.getText())>5222){
                            throw new NumberFormatException();
                        }
                        
                        Einstellungen Einstellungen = new Einstellungen();
                        Einstellungen.setEinstellung("PortNr", portTextField.getText());
                    } 
                    catch(NumberFormatException a){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Port zwischen 1023 und 5222 eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                        
                    }
                }
                nachrichtAngezeigt = false;
            }

                
                
                
            
        }
    }
}
            

        
    

    


    
    

