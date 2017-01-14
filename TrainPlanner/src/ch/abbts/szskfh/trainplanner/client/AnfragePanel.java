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
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
class AnfragePanel extends JPanel {
    
    private JLabel firmaLabel;
    private JLabel ankunftLabel;
    private JLabel containerLabel;
    private JLabel ausgabeLabel;
    private JLabel ankunftAbstandLabel;
    
    private JTextField firmaTextField;
    private JTextField ankunftTextFieldH;
    private JTextField ankunftTextFieldM;
    private JTextField containerTextField;
    
    private JTextArea ausgabeTextArea;
    
    private JButton anfrageSenden;
    
    private String firmenName;
    private short container;
    private int ankunftsZeitH;
    private int ankunftsZeitM;
    private LocalTime ankunftsZeit;
    private short prio = 1;

    //Variable zur überprüfung, ob schon einmal eine Nachricht angezeigt wurde bei einer Falscheingabe
    private boolean nachrichtAngezeigt = false;
    
    
    public AnfragePanel() {
        initPanel(); 
    }
    
    private void initPanel() {
        //Main Panel wird initialisert und weitere Panel hinzugefügt
        //Farbe wird aus der Einstellungsklasse gelesen
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addTopPanel();
        addCenterPanel();
        addBottomPanel();        
    }
    
    private void addTopPanel() {
        
        JPanel anfragePanel = new JPanel (new GridLayout(3,3,50,10));
        //Farbe wird aus der Einstellungsklasse gelesen
        anfragePanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        //Komponenten werden am Panel hinzugefügt 
        firmaLabel = new JLabel ("Firma:");
        firmaLabel.setForeground(Color.RED);
        anfragePanel.add(firmaLabel);
        
        firmaTextField = new JTextField ();
        anfragePanel.add(firmaTextField);
        //DummieLabel wird erzeugt und hinzugefügt um einen leeren Platz im GridLayout zu besetzen 
        anfragePanel.add(new JLabel (""));
       
        containerLabel = new JLabel("Anzahl Container:");
        containerLabel.setForeground(Color.RED);
        anfragePanel.add(containerLabel);
        
        containerTextField = new JTextField();
        anfragePanel.add(containerTextField);
        //DummieLabel wird erzeugt und hinzugefügt um einen leeren Platz im GridLayout zu besetzen
        anfragePanel.add(new JLabel (""));
        
        ankunftLabel = new JLabel ("gewünschte Startzeit in 00:00 :");
        ankunftLabel.setForeground(Color.RED);
        anfragePanel.add(ankunftLabel);
        
        //neues Panel wird erzeugt um die Komponenten für die Eingabe der Ankunftszeit wie gewünscht darstellen zu können
        JPanel ankunftPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        ankunftPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ankunftTextFieldH = new JTextField ();
        ankunftTextFieldH.setColumns(5);
        ankunftPanel.add(ankunftTextFieldH);
        
        ankunftAbstandLabel = new JLabel(":");
        ankunftAbstandLabel.setForeground(Color.RED);
        ankunftPanel.add(ankunftAbstandLabel);
        
        ankunftTextFieldM = new JTextField ();
        ankunftTextFieldM.setColumns(5);
        ankunftPanel.add(ankunftTextFieldM);
        //Ankunft Panel wird am Anfrage Panel (GridLayout) hinzugefügt
        anfragePanel.add(ankunftPanel);
        //neues Panel wird erzeugt, damit die Grösse des Button definiert werden kann
        JPanel buttonPanel = new JPanel (new FlowLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        buttonPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        anfrageSenden = new JButton ("Anfrage absenden");
        anfrageSenden.setEnabled(true);
        anfrageSenden.setSize(30, 20);
        MyActionListener listener = new MyActionListener ();
        anfrageSenden.addActionListener(listener);
        buttonPanel.add(anfrageSenden);
        anfragePanel.add(buttonPanel);
        //Top Panel wird am Anfrage Panel hinzugefügt
        add (anfragePanel, BorderLayout.NORTH);  
         
    }
    
    private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        //Farbe wird aus der Einstellungsklasse gelesen
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        //Komponenten werden am Panel hinzugefügt 
        ausgabeLabel = new JLabel("Ausgabe:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        ausgabeTextArea = new JTextArea();
        ausgabeTextArea.setEditable(false);
        centerPanel.add(ausgabeTextArea);
        //Center Panel wird am Abfrage Panel hinzugefügt
        add(centerPanel, BorderLayout.CENTER);
        
    }
    
    private void addBottomPanel() {
        
        JPanel bottomPanel = new JPanel (new BorderLayout());
        //Farbe wird aus der Einstellungsklasse gelesen
        bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);
        //Bottom Panel wird am Abfrage Panel hinzugefügt
        add(bottomPanel, BorderLayout.SOUTH);
       
    }
    
    public void setAusgabeText (String text){
        ausgabeTextArea.setText(text);
    }
    
    
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            
            JButton button = (JButton)a.getSource();
           
            
            if (button == anfrageSenden){
                //Pattern dient zur Überprüfung, dass die Eingabe des Firmennamen mindestens aus einem Buchstaben besteht und nicht nur aus Zahlen und Sonderzeichen
                Pattern p = Pattern.compile("[A-Za-z]");
                Matcher m = p.matcher("");
                ausgabeTextArea.setText("");

                //überprüft ob die Textfelder leer sind und gibt allenfalls eine Meldung
                if (firmaTextField.getText().trim().isEmpty() | containerTextField.getText().trim().isEmpty() | ankunftTextFieldH.getText().trim().isEmpty() | ankunftTextFieldM.getText().trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                    
                }
                //Firmennamen wird am Matcher hinzugefügt
                else {
                    m = p.matcher(firmaTextField.getText());
                }
                //überprüft den Firmennamen, ob mindestens einen Buchtaben und kein ; eingeben wurden und gibt sonst eine Meldung
                if (!firmaTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false & (!m.find() | firmaTextField.getText().contains(";"))){
                    
                    JOptionPane.showMessageDialog(null,
                    "Firmenname muss mindestens aus einem Buchstaben bestehen und darf kein ; enthalten.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                        
                }
                //Wenn alles im richtigen Format ist, wird der Firmannamen in die Variable gespeichert
                else {
                    firmenName = firmaTextField.getText();
                }
                
                if (!containerTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    //Containerzahl wird veruscht in einen Int zu parsen, wenn das Format stimmt, wird die Zahl auf die Variable gespeichert
                    try {
                        container = Short.parseShort(containerTextField.getText()); 
                        
                        if (Short.parseShort(containerTextField.getText())<=0){
                            throw new NumberFormatException();
                        }
                    } 
                    catch(NumberFormatException e){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Container als positive Ganzzahl (>=1) eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;       
                    }
                }
                
                if (!ankunftTextFieldH.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    //ankunftszeit Stunden wird versucht in einen Int zu parsen, wenn das Format stimmt, wird die Zahl auf die Variable gespeichert
                    try {
                        ankunftsZeitH = Integer.parseInt(ankunftTextFieldH.getText());
                        
                        if (Integer.parseInt(ankunftTextFieldH.getText())< 0 | Integer.parseInt(ankunftTextFieldH.getText())> 23){
                            throw new NumberFormatException();
                        }
                    } 
                    catch(NumberFormatException e){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Stunden als positive Ganzzahl von 00-23 eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true;
                    }
                }
                
                if (!ankunftTextFieldM.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    //ankunftszeit Minuten wird versucht in einen Int zu parsen, wenn das Format stimmt, wird die Zahl auf die Variable gespeichert
                    try {
                        ankunftsZeitM = Integer.parseInt(ankunftTextFieldM.getText());
                        
                        if (Integer.parseInt(ankunftTextFieldM.getText())< 0 | Integer.parseInt(ankunftTextFieldM.getText())> 59){
                            throw new NumberFormatException();
                        }
                    } 
                    catch(NumberFormatException e){
                        
                        JOptionPane.showMessageDialog(null,
                        "Bitte Minuten als positive Ganzzahl von 00-59 eingeben.", 
                        "Fehler", JOptionPane.ERROR_MESSAGE);
                        nachrichtAngezeigt = true; 
                    }
                }
                if (nachrichtAngezeigt == false){
                    anfrageSenden.setEnabled(false);
                    ausgabeTextArea.append("Transportanfrage wird gesendet...");
                    ausgabeTextArea.append("\n");
                    ankunftsZeit = LocalTime.of(ankunftsZeitH, ankunftsZeitM);
                    ausgabeTextArea.update(ausgabeTextArea.getGraphics());
                    //Versucht eine Anfrage zu senden, bei allfälligen Exceptions wird eine Meldung auf der TextArea ausgegeben
                    try {
                        SocketConnection Socket = new SocketConnection();
                        //Der Rückgabe String wird in ein Array gespeichert und bei ; gesplitet
                        String [] anfrage = Socket.sendeTransportanfrage(firmenName, container, ankunftsZeit, prio).split(";");
                        //Ausgabe der einzelnen Werten des Array auf der TextArea            
                        ausgabeTextArea.append("Transport ID: " + anfrage[0] + "\n");
                        ausgabeTextArea.append("Akunftszeit: " + anfrage[1] + "\n");
                        ausgabeTextArea.append("Priorität: " + anfrage[2] + "\n");
                        ausgabeTextArea.append("Preis: " + anfrage[3] + "CHF" +"\n");
  
                        anfrageSenden.setEnabled(true);
                        
                    }catch (Exception e){
                        ausgabeTextArea.append("keine Verbindung zum Server möglich");
                        ausgabeTextArea.append("\n");
                        ausgabeTextArea.update(ausgabeTextArea.getGraphics());                         
                    }
                    //Textfelder werden zurückgesetzt
                    firmaTextField.setText("");
                    containerTextField.setText("");
                    ankunftTextFieldH.setText("");
                    ankunftTextFieldM.setText("");
                }
                nachrichtAngezeigt = false;    
            }
            
        }
    }
}