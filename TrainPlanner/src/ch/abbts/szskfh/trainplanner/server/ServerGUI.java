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
    
    private Controller controller;
    private MainPanel mainPanel;
    private TopMenuBar topMenuBar;
    private static ServerGUI mainFrame;
    //Initialisiert die Grösse des Rahmens aus der Einstellungsklasse
    private int frameHoehe = Integer.parseInt(Einstellungen.getProperty("FrameHoehe"));
    private int frameBreite = Integer.parseInt(Einstellungen.getProperty("FrameBreite"));
    
    
    public ServerGUI(Controller controller) {
        this.controller = controller;
        initGUI ();          
    }

    private void initGUI() {
        mainFrame = this;

        setTitle("Trainplanner");
        //Bildschirm- breite und -höhe wird auf Variablen gespeichert
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth =gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();
        //Frame wird in die Mitte des Bildschirmes angezeigt
        setLocation((screenWidth-frameBreite)/2, (screenHeight-frameHoehe)/2);
        setSize(frameBreite,frameHoehe);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Main Panel und TopMenuBar wird erstellt und hinzugefügt
        mainPanel = new MainPanel();
        topMenuBar = new TopMenuBar();

        add(topMenuBar);
        setJMenuBar (topMenuBar);
        
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    public static ServerGUI getMainFrame(){
        return mainFrame;
    }
    /**
     * Stellt sicher, dass ins GUI Log im MainPanel geschrieben werden kann. 
     * @param text String Text welcher im GUI MainPanel angezeigt werden soll. 
     */
    public void schreibeInGui(String text) {
        mainPanel.setAusgabeText(text);
    }
    /**
     * Stellt kontrollierten Zugriff auf den Fahrplan sicher. 
     * @return Fahrplan mit allen aktuellen Zugverbindungen
     */
    public Fahrplan getFahrplan() {
        return controller.getFahrplan();
    }
    /**
     * Setzt den Störungs-Status zurück. 
     */
    public void unsetEmergencyState() {
        controller.unsetEmergencyState();
    }
    /**
     * Setzt den Störungs Status. 
     */
    public void setEmergencyState() {
        controller.setEmergencyState();
    }
    /**
     * Gibt den aktuellen Störungsstatus zurück. 
     * @return true wenn Emergency State aktiv, falls wenn inaktiv. 
     */
    public boolean getEmergencyState() {
        return mainPanel.getEmergencyState();
    }
}
