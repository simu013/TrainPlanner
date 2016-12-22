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
    private LocalTime ankunftsZeit;
    private short prio = 1;
    
    private boolean nachrichtAngezeigt = false;
    private int ankunftsZeitH;
    private int ankunftsZeitM;
    
    
    public AnfragePanel() {
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
        
        JPanel anfragePanel = new JPanel (new GridLayout(3,3,50,10));
        anfragePanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        firmaLabel = new JLabel ("Firma:");
        firmaLabel.setForeground(Color.RED);
        anfragePanel.add(firmaLabel);
        
        firmaTextField = new JTextField ();
        anfragePanel.add(firmaTextField);
         
        anfragePanel.add(new JLabel (""));
       
        containerLabel = new JLabel("Anzahl Container:");
        containerLabel.setForeground(Color.RED);
        anfragePanel.add(containerLabel);
        
        containerTextField = new JTextField();
        anfragePanel.add(containerTextField);
        
        anfragePanel.add(new JLabel (""));
        
        ankunftLabel = new JLabel ("gewünschte Ankunftszeit in 00:00 :");
        ankunftLabel.setForeground(Color.RED);
        anfragePanel.add(ankunftLabel);
        
        JPanel ankunftPanel = new JPanel (new FlowLayout());
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
       
        anfragePanel.add(ankunftPanel);
        
        anfrageSenden = new JButton ("Anfrage absenden");
        anfrageSenden.setEnabled(true);
        MyActionListener listener = new MyActionListener ();
        anfrageSenden.addActionListener(listener);
        anfragePanel.add(anfrageSenden);
        
        add (anfragePanel, BorderLayout.NORTH);  
         
    }
    
    private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ausgabeLabel = new JLabel("Ausgabe:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        ausgabeTextArea = new JTextArea();
        ausgabeTextArea.setEditable(false);
        centerPanel.add(ausgabeTextArea);

        add(centerPanel, BorderLayout.CENTER);
        
    }
    
    private void addBottomPanel() {
        
        JPanel bottomPanel = new JPanel (new BorderLayout());
        bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);
        
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
                
                Pattern p = Pattern.compile("[A-Za-z]");
                Matcher m = p.matcher("");
                

               
                if (firmaTextField.getText().trim().isEmpty() | containerTextField.getText().trim().isEmpty() | ankunftTextFieldH.getText().trim().isEmpty() | ankunftTextFieldM.getText().trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                    
                }
                else {
                    m = p.matcher(firmaTextField.getText());
                }
                
                if (!firmaTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false & (!m.find() | firmaTextField.getText().contains(";"))){
                    
                    JOptionPane.showMessageDialog(null,
                    "Firmenname muss mindestens aus einem Buchstaben bestehen und darf kein ; enthalten.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                        
                    }
                else {
                    firmenName = firmaTextField.getText();
                }
                
                if (!containerTextField.getText().trim().isEmpty() & nachrichtAngezeigt == false){
                    
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
               
                    
                    try {
                        SocketConnection Socket = new SocketConnection();
                        ausgabeTextArea.append(Socket.sendeTransportanfrage(firmenName, container, ankunftsZeit, prio));
                        ausgabeTextArea.append("\n");
                    }catch (Exception e){
                        ausgabeTextArea.append("keine Verbindung zum Server möglich");
                        ausgabeTextArea.append("\n");
                        ausgabeTextArea.update(ausgabeTextArea.getGraphics());

                         
                    }

 
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