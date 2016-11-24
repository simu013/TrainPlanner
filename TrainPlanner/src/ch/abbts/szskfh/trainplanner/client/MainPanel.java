/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sascha
 */
public class MainPanel extends JPanel{
    
    private JLabel title;
    private JButton anfrage;
    private TopMenuBar topMenuBar;

    public MainPanel() {
        initMainPanel ();
        
    }

    private void initMainPanel() {
        
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new BorderLayout());
        this.addTopPanel();
        this.addCenterPanel();
        this.addBottomPanel();
        
    }
    
    private void addTopPanel (){
        
        JPanel topPanel = new JPanel (new FlowLayout());
        topPanel.setBackground(Color.DARK_GRAY);
        title = new JLabel ("Main");
        title.setForeground(Color.RED);
        add (topPanel, BorderLayout.NORTH);    
        
        
    }
    
    private void addCenterPanel (){
        JPanel centerPanel = new JPanel (new FlowLayout());
        centerPanel.setBackground(Color.DARK_GRAY);
        title = new JLabel ("Mitte");
        title.setForeground(Color.RED);
        add(centerPanel, BorderLayout.CENTER);
        
    }
    
    private void addBottomPanel (){
        
    }

    
    
    
}
