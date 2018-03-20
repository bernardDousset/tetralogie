import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;

import com.sun.org.apache.bcel.internal.generic.NEW;


public class CentralityControlWindow extends JPanel implements ActionListener{
	JFrame fenetre = new JFrame("Filtre ");
	JSlider slider1 = new JSlider();    // degree
	JSlider slider2 = new JSlider();    // strength
	JSlider slider3 = new JSlider();    // Intermediarity
	JSlider slider4 = new JSlider();    // Proximity
    
	
	
	JPanel panel0 , panel8;
	Font fnt1 = new Font("Time", Font.PLAIN, 6);
	Color b = new Color(128, 191, 212);
	Color c = new Color(200, 130, 100);
	Color d = new Color(150, 130, 220);
	Color e = new Color(200, 0, 150);
	Color f = new Color(150, 130, 200);
	Color g = new Color(150, 200, 250);
	
    CentralityControlWindow(int instance) {
    	fenetre.setSize(180, (instance > 1)?800:600);
    	fenetre.setResizable(true);
    	
        this.fenetre.setLocation(990, 50);
    	Dimension d0 = new Dimension (170,25);
    	JPanel panel0 = new JPanel(new GridLayout(0, 1) );
    	add("Center", panel0 );
        setFont(fnt1);
    	panel0.add( new JLabel("    Degree "), BorderLayout.CENTER );	
    	panel0.setBackground(b);	
	
    	slider1.setPaintTicks(true);	
    	slider1.setPaintLabels(true);
    	slider1.setPreferredSize(d0);		
    	panel0.add(slider1);	
    	
    	
    	panel0.add( new JLabel("    Strength "), BorderLayout.CENTER );	

	
    	slider2.setPaintTicks(true);
    	slider2.setPaintLabels(true);
    	slider2.setPreferredSize(d0);	
    	panel0.add(slider2);
    	
    	
    	panel0.add( new JLabel("    Intermediarity  "), BorderLayout.CENTER );	


    	slider3.setSnapToTicks(true);
    	slider3.setPaintTicks(true);
    	slider3.setPaintTrack(true);
    	slider3.setPaintLabels(true);
    	slider3.setPreferredSize(d0);
    	panel0.add(slider3);
    	
    	
    	panel0.add( new JLabel("    Proximity  "), BorderLayout.CENTER );	


    	slider4.setSnapToTicks(true);
    	slider4.setPaintTicks(true);
    	slider4.setPaintTrack(true);
    	slider4.setPaintLabels(true);
    	slider4.setPreferredSize(d0);
    	panel0.add(slider4);
    	
    	
    	
    	
    	
    	
    	fenetre.getContentPane().add(this, BorderLayout.CENTER );
    	
    }


	@Override
	public void actionPerformed(ActionEvent arg0) {
//       if(checkBox1.isSelected()){
//    	   checkBoxValue = 1;
//       }else if(checkBox2.isSelected()){
//    	  checkBoxValue = 2; 
//       }else if(checkBox4.isSelected()){
//    	   checkBoxValue = 4; 
//       }else if(checkBox125.isSelected()){
//    	   checkBoxValue = 0.125;
//       }else if(checkBox25.isSelected()){
//    	   checkBoxValue = 0.25;
//       }else if(checkBox50.isSelected()){
//    	   checkBoxValue = 0.5;
//       }else if(checkBox8.isSelected()){
//    	   checkBoxValue = 8;
//       }
	}


	
	
	
}
