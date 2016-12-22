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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Sascha
 */
public class MainPanel extends JPanel {
        
   
    private JLabel ausgabeLabel;
    private JTextField trpIdTextField;
    private JTextArea logTextArea;
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
    

    
    private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ausgabeLabel = new JLabel("Log History:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        centerPanel.add(logTextArea);

        add(centerPanel, BorderLayout.CENTER);
        
    }
    
    public void setAusgabeText (String text){
        logTextArea.append(text);
        logTextArea.append("\n");
    }
    
    private void addBottomPanel() {
        
        JPanel bottomPanel = new JPanel (new BorderLayout());
        bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

        JLabel dummieLabel4 = new JLabel("  ");
        dummieLabel4.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));

        bottomPanel.add(dummieLabel4);
        
        add(bottomPanel, BorderLayout.SOUTH);
       
    }
}
