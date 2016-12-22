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
public class AbfragePanel extends JPanel{
       
    private JLabel trpIdLabel;
    private JLabel ausgabeLabel;
    private JLabel ankunftAbstandLabel;
    
    private JTextField trpIdTextField;

    private JTextArea statusTextArea;
    
    private JButton abfrageSenden;
    
    private boolean nachrichtAngezeigt = false;

    
    
    public AbfragePanel() {
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
        
        JPanel abfragePanel = new JPanel (new FlowLayout());
        abfragePanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        trpIdLabel = new JLabel ("Transport ID:");
        trpIdLabel.setForeground(Color.RED);
        abfragePanel.add(trpIdLabel);
        
        trpIdTextField = new JTextField ();
        trpIdTextField.setColumns(15);
        abfragePanel.add(trpIdTextField);

        
        JPanel ankunftPanel = new JPanel (new FlowLayout());
        ankunftPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        

        abfrageSenden = new JButton ("Anfrage absenden");
        abfrageSenden.setEnabled(true);
        MyActionListener listener = new MyActionListener ();
        abfrageSenden.addActionListener(listener);
        abfragePanel.add(abfrageSenden);
        
        add (abfragePanel, BorderLayout.NORTH);  
         
    }
    
    private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ausgabeLabel = new JLabel("Status:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);
        centerPanel.add(statusTextArea);

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
    
    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent a) {
            
            JButton button = (JButton)a.getSource();
           
            
            if (button == abfrageSenden){
                
                Pattern p = Pattern.compile("[0-9]");
                Matcher m = p.matcher("");
                

               
                if (trpIdTextField.getText().trim().isEmpty()){
                    
                    JOptionPane.showMessageDialog(null,
                    "Bitte alle Felder ausfüllen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                    
                }
                else {
                    m = p.matcher(trpIdTextField.getText());
                }
                if(!trpIdTextField.getText().trim().isEmpty()& nachrichtAngezeigt ==false & !m.find()){
                                       
                    JOptionPane.showMessageDialog(null,
                    "Transport ID kann nur aus Zahlen bestehen.", 
                    "Fehler", JOptionPane.ERROR_MESSAGE);
                    nachrichtAngezeigt = true;
                    
                }
                if (nachrichtAngezeigt == false){
                    abfrageSenden.setEnabled(false);
                    statusTextArea.append("Transportstatusabfrage wird gesendet...");
                    statusTextArea.append("\n");
                    try {
                        
                        SocketConnection Socket = new SocketConnection();
                        statusTextArea.append(Socket.getTransportStatus(trpIdTextField.getText()));
                        statusTextArea.append("\n");
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
