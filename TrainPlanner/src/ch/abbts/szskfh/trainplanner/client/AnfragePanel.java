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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Sascha
 */
class AnfragePanel extends JPanel {

    private JLabel firmaLabel;
    private JLabel ankunftLabel;
    private JLabel containerLabel;
    private JTextField firmaTextField;
    private JTextField ankunftTextField;
    private JTextField containerTextField;
    private JButton anfrageSenden;
    
    
    
    
    public AnfragePanel() {
        initPanel();

        
    }

     private void initPanel() {
        
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new BorderLayout());
        addTopPanel();
        addCenterPanel();
        addBottomPanel();
    }

    
    
    
    
    private void addTopPanel() {

        
        JPanel anfragePanel = new JPanel (new FlowLayout());
        anfragePanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        firmaLabel = new JLabel ("Firma:");
        firmaLabel.setForeground(Color.RED);
        anfragePanel.add(firmaLabel);
        firmaTextField = new JTextField ();
        firmaTextField.setColumns(15);
        anfragePanel.add(firmaTextField);
        containerLabel = new JLabel("Anzahl Container:");
        containerLabel.setForeground(Color.RED);
        anfragePanel.add(containerLabel);
        containerTextField = new JTextField();
        containerTextField.setColumns(5);
        anfragePanel.add(containerTextField);
        ankunftLabel = new JLabel ("gewünschte Ankunftszeit:");
        ankunftLabel.setForeground(Color.RED);
        anfragePanel.add(ankunftLabel);
        ankunftTextField = new JTextField ();
        ankunftTextField.setColumns(5);
        anfragePanel.add(ankunftTextField);
        add (anfragePanel, BorderLayout.NORTH);   
        
    }

    
        private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new BorderLayout());
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
 

        
                anfrageSenden = new JButton ("Anfrage absenden");

        centerPanel.add(anfrageSenden, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        
    }

    
    
    
    
    
    private void addBottomPanel() {
        
        JPanel bottomPanel = new JPanel (new BorderLayout());
          bottomPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
                JLabel label1 = new JLabel("Label 1");
        final LineBorder lineBorder2 = (LineBorder) BorderFactory
                .createLineBorder(Color.black);
        label1.setBorder(lineBorder2);
        bottomPanel.add(label1);
 
        JLabel label2 = new JLabel("Anfrage");
        Border border = BorderFactory.createLineBorder(Color.black);
        Border margin = new EmptyBorder(10, 10, 10, 10);
        label2.setBorder(new CompoundBorder(border, margin));
        bottomPanel.add(label2);
 
       
        add(bottomPanel, BorderLayout.SOUTH);
        
        
        
      

    }

   

    
}
