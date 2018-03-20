import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Object.*;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.plaf.SliderUI;

import java.io.*;

class ControlWindow extends JPanel implements ActionListener {

	JFrame fenetre = new JFrame("Paramètres");
	JFrame vitesse = new JFrame("Vitesse du morphing");

	/* echelle de valeurs selectionnables pour les différentes catégories */
	JSlider slider1 = new JSlider();
	JSlider slider2 = new JSlider();
	/** temperature des forces **/
	JSlider slider3 = new JSlider();
	/** Intensité des liens **/
	JSlider slider4 = new JSlider();
	/** densité de clustering **/
	JSlider slider5 = null;
	/** Changement d'instance **/
	JSlider slider6 = new JSlider();
	/** distance de répulsion **/
	JSlider slider8 = new JSlider();
	/** distance d'attraction **/
	JSlider slider7 = new JSlider();
	
	/**
	 * Color adjustement for centrality class identification
	 */
	JSlider slider77 = new JSlider();
	
	
	/** slider pour l'intensité de la couleur de la police **/
	JSlider slider9 = new JSlider();
	/** slider pour la taille de la couleur de la police **/
	JSlider slider10 = new JSlider();
	/** slider du K-Core **/
	JSlider slider11 = new JSlider();
	/** slider de la transitivité **/
	JSlider slider12 = new JSlider();
	/** slider pour gérer la force d'attirance vers les sommets temporels **/
	JSlider slider13 = new JSlider();
	/** slider pour gérer la vitesse du morphing **/
	JSlider slider14 = new JSlider();
   
	JLabel redLabel;  
	JScrollBar red;
	JPanel colorPanel;
	
	Image imageRelache;
	Image imageEnfonce;
	JPanel panel0, panel, panel1, panel2,  panel6, panel7 , panel8;
	private JPanel panel3; 

	public JPanel getPanel3() {
		return panel3;
	}

	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}

	/*
	 * déclaration des boutons pour le morphing, permettant de passer d'une
	 * instance à l'autre.
	 */
	JButton avance, recule, reset, compteur, evolution1, evolution2, reglage;
	Icon icon1, icon2, icon3;
	Color b = new Color(128, 191, 212);
	Color c = new Color(200, 130, 100);
	Color d = new Color(150, 130, 220);
	Color e = new Color(200, 0, 150);
	Color f = new Color(150, 130, 200);
	Color g = new Color(150, 200, 250);
	JLabel T;
	Font fnt1 = new Font("Time", Font.PLAIN, 6);
	String cpt = "0";
    

	
    ControlWindow(int instance) {


	fenetre.setSize(180, (instance > 1)?800:600);
	fenetre.setResizable(true);
	vitesse.setBackground(Color.lightGray);
    this.fenetre.setLocation(0, 50);
	Dimension d0 = new Dimension (170,25);
	JPanel panel0 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel0 );
    setFont(fnt1);
	panel0.add( new JLabel("    Température : t"), BorderLayout.CENTER );	// slider pour la force de répulsion entre sommets
	panel0.setBackground(b);	// couleur du panneau
	slider2.setMinimum(1);	// valeur minimale du slider
	slider2.setValue(20);
	slider2.setMaximum(40);	// Graduation maximale du slider
	slider2.setPaintTicks(true);	// pour afficher les graduations du slider
	slider2.setPaintLabels(true);
	slider2.setMinorTickSpacing(1);	// espacement entre deux graduations du slider
	slider2.setPreferredSize(d0);		// couleur du label
	panel0.add(slider2);	// fixage du slider sur le panneau

	panel0.add( new JLabel("    Coefficient répulsion"), BorderLayout.CENTER );
	slider6.setValue(10);
	slider6.setMaximum(20);
	slider6.setMinimum(1);
	slider6.setValue(3);
	slider6.setMinorTickSpacing(1);
	slider6.setSnapToTicks(true);
	slider6.setPaintTicks(true);
	slider6.setPaintTrack(true);
	slider6.setPaintLabels(true);
	slider6.setPreferredSize(d0);
	panel0.add(slider6);
     
	

 	panel0.add( new JLabel("    Distance cible attraction"), BorderLayout.CENTER );
	slider8.setValue(10);
	slider8.setMaximum(20);
	slider8.setValue(14);
	slider8.setMinorTickSpacing(1);
	slider8.setSnapToTicks(true);
	slider8.setPaintTicks(true);
	slider8.setPaintTrack(true);
	slider8.setPaintLabels(true);
	slider8.setPreferredSize(d0);
	panel0.add(slider8);
	
	if (instance>1){
		panel0.add( new JLabel("    Force repères temporels"), BorderLayout.CENTER );
		slider12.setValue(0);
		slider12.setMaximum(15);
		slider12.setMinimum(0);
		slider12.setMinorTickSpacing(1);
		slider12.setSnapToTicks(true);
		slider12.setPaintTicks(true);
		slider12.setPaintTrack(true);
		slider12.setPaintLabels(true);
		slider12.setPreferredSize(d0);
		panel0.add(slider12);
	    /* recours à un FlowLayout pour permettre d'afficher les deux flèches sur la même ligne et non pas l'une sous l'autre.*/
	    panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 1));
	    add("Center", panel2);
	}
	JPanel panel = new JPanel(new GridLayout(0, 1) );
	add("Center", panel );
	panel.setBackground(c);
	panel.add( new JLabel("    Filtrage  "), BorderLayout.CENTER );	// slider pour le filtrage des arêtes
	slider1.setPaintTicks(true);
	slider1.setPaintLabels(true);
	slider1.setPreferredSize(d0);
	slider1.setValue(0);
	panel.add(slider1);

	/* KCORE*/
	JPanel panel5 = new JPanel(new GridLayout(0, 1) );		
	add("Center", panel5 );
	panel5.setBackground( new Color (200,130,150));
	panel5.add( new JLabel("    KCore  "), BorderLayout.CENTER );	// slider pour le filtrage des sommets		 gp.control.panel7.set
	slider10.setPaintTicks(true);
	slider10.setPaintLabels(true);
	slider10.setValue(0);
	slider10.setPreferredSize(d0);
	
	panel5.add(slider10);
	JPanel panel7 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel7 );
	/** TRANSITIVITE **/
	panel7.setBackground( new Color (200,130,150));
	panel7.add( new JLabel("    Transitivité  "), BorderLayout.CENTER );	
	slider11.setMinorTickSpacing(1);
	slider11.setSnapToTicks(true);
	slider11.setPaintTicks(true);
	slider11.setPaintTrack(true);
	slider11.setPaintLabels(true);
	slider11.setValue(0);
	slider11.setMinimum(0);
	slider11.setPreferredSize(d0);
	
	panel7.add(slider11);
	//panel7.setVisible(false);
	
	// slider pour l'intensité des arêtes
	JPanel panel6 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel6 );
 	panel6.add( new JLabel("    Intensité des liens"), BorderLayout.CENTER );
 	panel6.setBackground(f);
	slider3.setValue(4);
	slider3.setMaximum(10);
	slider3.setMinorTickSpacing(1);
	slider3.setSnapToTicks(true);
	slider3.setPaintTicks(true);
	slider3.setPaintTrack(true);
	slider3.setPaintLabels(true);
	slider3.setPreferredSize(d0);
	panel6.add(slider3);
	
	JPanel panel3 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel3 );
 	panel3.add( new JLabel("    Intensité de la police"), BorderLayout.CENTER );
 	panel3.setBackground(e);
	slider7.setValue(6);
	slider7.setMaximum(12);
	slider7.setMinorTickSpacing(1);
	slider7.setSnapToTicks(true);
	slider7.setPaintTicks(true);
	slider7.setPaintTrack(true);
	slider7.setPaintLabels(true);
	//slider3.setBackground(Color.lightGray);
	//slider7.setPreferredSize(d0);
	panel3.add(slider7);
	
	panel3.add( new JLabel("    Répartition Couleur"), BorderLayout.CENTER );
	slider77.setValue(1);
	slider77.setMinimum(1);
	slider77.setMaximum(7);
	slider77.setMinorTickSpacing(1);
	slider77.setSnapToTicks(true);
	slider77.setPaintTicks(true);
	slider77.setPaintTrack(true);
	slider77.setPaintLabels(true);
	slider77.setBackground(Color.WHITE);
	slider77.setSize(new Dimension(170, 50));
	slider77.setPaintLabels(true);
	 Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
	    table.put (1, new JLabel("8"));
	    table.put (2, new JLabel("4"));
	    table.put (3, new JLabel("2"));
	    table.put (4, new JLabel("1"));
	    table.put (5, new JLabel("1/2"));
	    table.put (6, new JLabel("1/4"));
	    table.put (7, new JLabel("1/8"));
	slider77.setLabelTable(table);
    panel3.add(slider77);
    panel3.getComponent(3).setEnabled(true);
    panel3.getComponent(2).setEnabled(true);
//    panel3.getComponent(2).setVisible(false);
//    slider77.setVisible(false);
	
 	panel3.add( new JLabel("    Taille de la police"), BorderLayout.CENTER );
 	panel3.setBackground(e);
	slider9.setValue(6);
	slider9.setMaximum(12);
	slider9.setMinorTickSpacing(1);
	slider9.setSnapToTicks(true);
	slider9.setPaintTicks(true);
	slider9.setPaintTrack(true);
	slider9.setPaintLabels(true);
	slider9.setPreferredSize(d0);
	panel3.add(slider9);

	// slider pour le clustering
	JPanel panel4 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel4 );
	panel4.setBackground(g);
	panel4.add(new JLabel("   Densité de clustering"), BorderLayout.CENTER );
	slider4.setMinimum(2);
	slider4.setValue(4);
	slider4.setMaximum(10);
	slider4.setMinorTickSpacing(1);
	slider4.setSnapToTicks(true);
	slider4.setPaintTicks(true);
	slider4.setPaintTrack(true);
	slider4.setPaintLabels(true);
	//slider4.setBackground(Color.lightGray);
	slider4.setPreferredSize(d0);
	panel4.add(slider4);
	
	
	JPanel panel1 = new JPanel(new GridLayout(0, 1) );
	add("Center", panel1 );
	panel1.setBackground(b);

	if ( instance > 1 ) {

	    panel0.add(new JLabel("   Choix d'instances"), BorderLayout.CENTER );
	    slider5 = new JSlider();
	    slider5.setMinimum(0);
	    slider5.setValue(0);
	    slider5.setMaximum(instance);
	    slider5.setMinorTickSpacing(1);
	    slider5.setSnapToTicks(true);
	    slider5.setPaintTicks(true);
	    slider5.setPaintLabels(true);
	    slider5.setPreferredSize(d0);
	    panel0.add(slider5);

	    panel0.add(new JLabel("   Vitesse morphing"), BorderLayout.CENTER );
	    slider13 = new JSlider();
	    slider13.setMinimum(1);
	    slider13.setValue(5);
	    slider13.setMaximum(15);
	    slider13.setMinorTickSpacing(1);
	    slider13.setSnapToTicks(true);
	    slider13.setPaintTicks(true);
	    slider13.setPaintLabels(true);
	    slider13.setPreferredSize(d0);
	    panel0.add(slider13);
	    


	    /* Chemins d'accès pour atteindre l'image de la flèche : à modifier si on installe cette version sur une autre machine!!!*/
	    icon2 = new ImageIcon("reset.gif");
	    reset = new JButton(icon2);
	    Dimension d = new Dimension( icon2.getIconWidth(), icon2.getIconHeight()  );
	    reset.setPreferredSize(d);
	   //panel2.add(reset);
	    String s=System.getenv("TETRALOGIEBIN");
	    s+="/VisuGraph2";
	    File n=new File(s);
	    icon3 = new ImageIcon(n.getAbsolutePath()+"/previous.gif");
	    recule = new JButton(icon3);
	    d = new Dimension(icon3.getIconWidth(), icon3.getIconHeight() );
	    recule.setPreferredSize(d);
	    panel2.add(recule);
	    
	    icon1 = new ImageIcon(n.getAbsolutePath()+"/next.gif");
	    avance = new JButton(icon1);
	    d = new Dimension( icon1.getIconWidth(), icon1.getIconHeight()  );
	    avance.setPreferredSize(d);
	    panel2.add(avance);

	    compteur = new JButton(cpt);
	    panel2.add(compteur );

	    avance.addActionListener(this);
	    recule.addActionListener(this);
	    add("Center", new JPanel());
	    recule.setEnabled(false);
	    reset.setEnabled(false);
	    JPanel pvitesse = new JPanel();
	    pvitesse.setBackground(Color.lightGray);
	    JSlider jvitesse =  new JSlider();
	    vitesse.setBackground(Color.lightGray);
	    jvitesse.setInverted(true);
	    jvitesse.setMinimum(100);
	    jvitesse.setValue(500);
	    jvitesse.setMaximum(1000);
	    jvitesse.setPaintTicks(true);
	    jvitesse.setPaintLabels(true);
	    jvitesse.setMinorTickSpacing(10);

	    pvitesse.add(jvitesse);
	    vitesse.setSize(210,100);
	    vitesse.setResizable(false);
	    vitesse.getContentPane().add(pvitesse);

	}
	fenetre.getContentPane().add(this, BorderLayout.CENTER );

    }

	public void actionPerformed(ActionEvent e) {
	}

}
