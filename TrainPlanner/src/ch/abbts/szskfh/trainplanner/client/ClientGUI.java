/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import static java.awt.SystemColor.menu;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Sascha
 */
public class ClientGUI extends JFrame {

    private MainPanel mainPanel;
    private TopMenuBar topMenuBar;
    private static ClientGUI mainFrame;
    private int frameHoehe;
    private int frameBreite;
    
    
    public ClientGUI() {
        initGUI ();
                
    }

    private void initGUI() {
        mainFrame = this;
             

        
        
        setTitle("Trainplanner");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth =gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        setLocation((screenWidth-frameBreite)/2, (screenHeight-frameHoehe)/2);
        setSize(frameBreite,frameHoehe);
        setBackground(Color.DARK_GRAY);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        topMenuBar = new TopMenuBar();
        
        mainPanel = new MainPanel();
        
        setContentPane(mainPanel);
        setVisible(true);
        
    }

 public static ClientGUI getMainFrame(){
     return mainFrame;
 }
    
}
