/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.server;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



/**
 *
 * @author Sascha
 */
public class TopMenuBar extends JMenuBar{
 
    private JMenu menu;
    private JMenuItem menuItem;
    
    
    public TopMenuBar() {
        initMenuBar();
        
    }

    private void initMenuBar() {
        menu = new JMenu("Datei");
        this.add(menu);
        
        menuItem = new JMenuItem ("Einstellungen");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            JFrame topFrame = ServerGUI.getMainFrame();
            topFrame.setContentPane(new EinstellungsPanel());
            topFrame.revalidate();
            topFrame.repaint();
            } 
        });
       
        
        
        menuItem = new JMenuItem ("Beenden");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            } 
        });
       
        
        
        menu= new JMenu ("Aktion");
        this.add(menu);
        
        
        menuItem = new JMenuItem("Zurück");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            JFrame topFrame = ServerGUI.getMainFrame();
            topFrame.setContentPane(new MainPanel());
            topFrame.revalidate();
            topFrame.repaint();
            } 
        });
                
        
        
    }
}
