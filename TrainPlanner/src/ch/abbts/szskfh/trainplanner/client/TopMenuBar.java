/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Sascha
 */
class TopMenuBar extends JMenuBar {

    private JMenu menu;
    private JMenuItem menuItem;
    
    
    public TopMenuBar() {
        initMenuBar();
        
    }

    private void initMenuBar() {
        //eine TopMenuBar wird erzeugt und weitere Menus hinzugefügt
        menu = new JMenu("Datei");
        this.add(menu);
        
        menuItem = new JMenuItem ("Einstellungen");
        menu.add(menuItem);
        //Panel wird umgeschaltet bei Betätigung des Button 
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            JFrame topFrame = ClientGUI.getMainFrame();
            topFrame.setContentPane(new EinstellungsPanel());
            topFrame.revalidate();
            topFrame.repaint();
            } 
        });

        menuItem = new JMenuItem ("Beenden");
        menu.add(menuItem);
        //Programm wird beendet bei Betätigung des Button
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });

        menu= new JMenu ("Aktion");
        this.add(menu);
        
        menuItem = new JMenuItem("Neue Anfrage");
        menu.add(menuItem);
        //Panel wird umgeschaltet bei Betätigung des Button
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            JFrame topFrame = ClientGUI.getMainFrame();
            topFrame.setContentPane(new AnfragePanel());
            topFrame.revalidate();
            topFrame.repaint();
            } 
        });
        
        menuItem = new JMenuItem ("Statusabfrage");
        menu.add(menuItem);
        //Panel wird umgeschaltet bei Betätigung des Button
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame topFrame = ClientGUI.getMainFrame();
                topFrame.setContentPane(new AbfragePanel());
                topFrame.revalidate();
                topFrame.repaint();

            } 
        });
        
        menuItem = new JMenuItem("Zurück");
        menu.add(menuItem);
        //Panel wird umgeschaltet bei Betätigung des Button
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            JFrame topFrame = ClientGUI.getMainFrame();
            topFrame.setContentPane(new MainPanel());
            topFrame.revalidate();
            topFrame.repaint();
            } 
        });

        menu = new JMenu ("Hilfe");
        this.add(menu);
        
        menuItem = new JMenuItem("?");
        menu.add(menuItem);
        //Panel wird umgeschaltet bei Betätigung des Button
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                     JOptionPane.showMessageDialog(null,
                    "Programm: Trainplanner V0.1", 
                    "über", JOptionPane.PLAIN_MESSAGE);
                }
     
        });
    }
    
}
