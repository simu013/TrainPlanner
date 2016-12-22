/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

/**
 *
 * @author Sascha
 */
public class ServerGUI extends JFrame{
    
    private MainPanel mainPanel;
    private TopMenuBar topMenuBar;
    private static ServerGUI mainFrame;
    private int frameHoehe = Integer.parseInt(new Einstellungen().getEinstellung("FrameHoehe"));
    private int frameBreite = Integer.parseInt(new Einstellungen().getEinstellung("FrameBreite"));
    
    
    public ServerGUI() {
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
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        topMenuBar = new TopMenuBar();
        
        mainPanel = new MainPanel();
        
        add(topMenuBar);
        setJMenuBar (topMenuBar);
        
        setContentPane(mainPanel);
        setVisible(true);
        
    }

 public static ServerGUI getMainFrame(){
     return mainFrame;
 }
}
