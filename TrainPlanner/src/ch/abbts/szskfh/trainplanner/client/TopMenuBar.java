/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.abbts.szskfh.trainplanner.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Sascha
 */
class TopMenuBar {

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    
    
    public TopMenuBar() {
        initMenuBar();
        
    }

    private void initMenuBar() {
        
        menuBar = new JMenuBar();
        
        menu= new JMenu ("Anfrage");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Neu");
        menu.add(menuItem);
    }
    
}
