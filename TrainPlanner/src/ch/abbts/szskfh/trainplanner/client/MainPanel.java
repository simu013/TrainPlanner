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
import javax.swing.JButton;
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
        
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        this.addTopPanel();
        this.addCenterPanel();
        this.addBottomPanel();
        
    }
    
    private void addTopPanel (){
        

        JPanel topPanel = new JPanel (new FlowLayout());
        topPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));

        
       
        ImageIcon tunnel = new ImageIcon ("TitelBild.jpg");
        Image titelBild = tunnel.getImage().getScaledInstance(400, 300, 400);
 
        topPanel.add(new JLabel(new ImageIcon(titelBild)));
        
        add(topPanel, BorderLayout.WEST);
        
        
    }
    
    private void addCenterPanel (){
        JPanel centerPanel = new JPanel (new BorderLayout());
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        title = new JLabel ("WILLKOMMEN BEI");
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
        centerPanel.add(title, BorderLayout.NORTH);

        title = new JLabel ("TRAINPLANNER");
        title.setForeground(Color.lightGray);
        title.setFont(new Font("Arial", Font.HANGING_BASELINE, 30));
        centerPanel.add(title, BorderLayout.CENTER);
        
        
        add(centerPanel, BorderLayout.CENTER);

        
    }
    
    private void addBottomPanel (){
       
        JPanel bottomPanel = new JPanel (new FlowLayout());

        bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        title = new JLabel ("Planung von Heute!");
        title.setForeground(Color.RED);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(title);
    }

    
    
    
}
