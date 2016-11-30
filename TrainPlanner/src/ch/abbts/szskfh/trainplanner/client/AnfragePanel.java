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
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private JLabel ausgabeLabel;
    private JTextField firmaTextField;
    private JTextField ankunftTextField;
    private JTextField containerTextField;
    private JTextField ausgabeTextField;
    private JButton anfrageSenden;
    private JComboBox box1;
    private JComboBox box2;
    
    
    
    
    
    public AnfragePanel() {
        initPanel();

        
    }

     private void initPanel() {
        
        this.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        this.setLayout(new BorderLayout());
        addTopPanel();
        addCenterPanel();
        addBottomPanel();
    }

    
    
    
    
    private void addTopPanel() {

        
        JPanel anfragePanel = new JPanel (new GridLayout(3,3,50,10));
        anfragePanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        firmaLabel = new JLabel ("Firma:");
        firmaLabel.setForeground(Color.RED);
        anfragePanel.add(firmaLabel);
        firmaTextField = new JTextField ();
        //firmaTextField.setColumns(15);
         anfragePanel.add(firmaTextField);
         
        JLabel dummieLabel1 = new JLabel ("");
        anfragePanel.add(dummieLabel1);
       
        containerLabel = new JLabel("Anzahl Container:");
        containerLabel.setForeground(Color.RED);
        anfragePanel.add(containerLabel);
        

               
        containerTextField = new JTextField();
        //containerTextField.setColumns(5);
        anfragePanel.add(containerTextField);
        
        JLabel dummieLabel2 = new JLabel ("");
        anfragePanel.add(dummieLabel2);
        
        
        ankunftLabel = new JLabel ("gewünschte Ankunftszeit in 00:00 :");
        ankunftLabel.setForeground(Color.RED);
        anfragePanel.add(ankunftLabel);
        ankunftTextField = new JTextField ();
        //ankunftTextField.setColumns(5);
        anfragePanel.add(ankunftTextField);
        
        anfrageSenden = new JButton ("Anfrage absenden");
        anfragePanel.add(anfrageSenden);
        
        
        add (anfragePanel, BorderLayout.NORTH);   
        
    }

    
        private void addCenterPanel (){
                
        JPanel centerPanel = new JPanel (new GridLayout(2,1));
        centerPanel.setBackground(Color.decode(new Einstellungen().getEinstellung("FrameFarbe")));
        
        ausgabeLabel = new JLabel("Ausgabe:");
        ausgabeLabel.setForeground(Color.RED);
        ausgabeLabel.setHorizontalAlignment(JLabel.CENTER);
        centerPanel.add(ausgabeLabel);
        
        ausgabeTextField = new JTextField();
        centerPanel.add(ausgabeTextField);
        
       
        
        add(centerPanel, BorderLayout.CENTER);
        
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
