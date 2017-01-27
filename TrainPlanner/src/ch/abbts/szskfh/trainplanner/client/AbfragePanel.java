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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Sascha
 */
public class AbfragePanel extends JPanel{
       
    private JLabel trpIdLabel;
    private JLabel ausgabeLabel;
    private JLabel ankunftAbstandLabel;
    
    private JTextField trpIdTextField;

    private JTextArea statusTextArea;
    
    private JButton abfrageSenden;
    //Variable zur überprüfung, ob schon einmal eine Nachricht angezeigt wurde bei einer Falscheingabe
    private boolean nachrichtAngezeigt = false;

    
    
    public AbfragePanel() {
        initPanel(); 
    }
    
    private void initPanel() {
        //Main Panel wird initialisert und weitere Panel hinzugefügt
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addTopPanel();
        addCenterPanel();
        addBottomPanel();        
    }
    
    private void addTopPanel() {

        JPanel abfragePanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        abfragePanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        trpIdLabel = new JLabel ("Transport ID:");
        trpIdLabel.setForeground(Color.RED);
        abfragePanel.add(trpIdLabel);
        
        trpIdTextField = new JTextField ();
        trpIdTextField.setColumns(15);
        abfragePanel.add(trpIdTextField);

        abfrageSenden = new JButton ("Anfrage absenden");
        abfrageSenden.setEnabled(true);
        MyActionListener listener = new MyActionListener ();
        abfrageSenden.addActionListener(listener);
        abfragePanel.add(abfrageSenden);
        //Top Panel wird am Abfrage Panel hinzugefügt
        add (abfragePanel, BorderLayout.NORTH);  
         
    }
    
    private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        //Farbe wird aus der Einstellungsklasse gelesen
        centerPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        //Komponenten werden erstellt und am Panel hinzugefügt
        ausgabeLabel = new JLabel("Status:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        centerPanel.add(statusTextArea);
        //Center Panel wird am EinstellungsPanel hinzugefügt
        add(centerPanel, BorderLayout.CENTER);
        
    }
    
    private void addBottomPanel() {
        
        JPanel bottomPanel = new JPanel (new BorderLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        bottomPanel.setBackground(Color.decode(Config.getProperty("FrameFarbe")));
        //Ein DummieLabel wird erstellt und am Panel hinzugefügt
        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);
        //Bottom Panel wird am EinstellungsPanel hinzugefügt
        add(bottomPanel, BorderLayout.SOUTH);
       
    }
    
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            
            JButton button = (JButton)a.getSource();
           
            
            if (button == abfrageSenden){
                
                //Text in TextArea wird zurückgesetzt
                statusTextArea.setText(" ");

               //überprüft, ob das Textfeld leer ist und gibt allenfalls eine Meldung
                if (trpIdTextField.getText().trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                    
                }
 
                if(!trpIdTextField.getText().trim().isEmpty()& nachrichtAngezeigt ==false ){
                    //versucht die Eingabe in einen Long zu parsen um somit überprüfen zu können, dass die Eingabe nur aus Zahlen besteht               
                    try {
                        long trpID = Long.parseLong(trpIdTextField.getText());
 
                    } 
                    catch(NumberFormatException e){
                        
                        JOptionPane.showMessageDialog(null,
                        "Transport ID kann nur aus Zahlen bestehen.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                    
                    }

                }
                if (nachrichtAngezeigt == false){
                    abfrageSenden.setEnabled(false);
                    statusTextArea.append("Transportstatusabfrage wird gesendet...");
                    statusTextArea.append("\n");
                    //Versucht eine Anfrage zu senden, bei allfälligen Exceptions wird eine Meldung auf der TextArea ausgegeben
                    try {
                        
                        SocketConnection Socket = new SocketConnection();
                        //Der Rückgabe String wird in ein Array gespeichert und bei ; gesplitet
                        String [] trpStatus = (Socket.getTransportStatus(trpIdTextField.getText())).split(";");
                        //Ausgabe der einzelnen Werten des Array auf der TextArea
                        statusTextArea.setText("Transport ID: " + trpStatus[0] + "\n");
                        statusTextArea.setText("Status: " + trpStatus[1] + "\n");
                        
                        abfrageSenden.setEnabled(true);

                    }catch (Exception e){
                        statusTextArea.append("keine Verbindung zum Server möglich");
                        statusTextArea.append("\n");
                        statusTextArea.update(statusTextArea.getGraphics());
                    }  
                }
              
            }
            nachrichtAngezeigt = false;
        }
    }
    
}
