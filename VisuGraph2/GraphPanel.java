
//*********************************************************************************** 






/* NOTE IMPORTANTE!!!!! Notre transitivité actuelle s'effectue sur le graphe ial. Ce qui veut dire que pour la visualisation par instance
 * individuelle, l'ajout de transitivité se fait par rapport au graphe global et non au graphe en cours. il peut donc y avoir des sommets sans 
 * lien pour un ajout de transitivité, pour une instance (autre que celle du graphe global)*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterGraphics;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
//import java.util.Hashtable;
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.StringTokenizer;
//import java.util.Vector;
import java.util.*;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.SliderUI;

import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.*;

import org.apache.commons.collections15.Transformer;


import com.sun.corba.se.impl.orbutil.DenseIntMapImpl;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class GraphPanel extends JPanel implements Runnable, MouseListener,
		ItemListener, MenuListener, KeyListener, Printable {
	public Graphe gr;
	public Graphe mg;
	public Graphe Cluster = new Graphe(false);
	public Graphe lArbre1 = new Graphe(false);
	public Graphe lArbre2 = new Graphe(false);
	public Graphe cnx = new Graphe(false);
	public Graphe g = new Graphe(false);
	public int init_max = 0;
	/** seuil de transitivité en cours **/
	public boolean centrer = false;
	/**
	 * correspond au clic sur "centrer" dans le menu, effectué par l'utilisateur
	 **/

	public Integer mat[][] = new Integer[801][831];
	/*
	 * variables pour la méthode centrer : en cas de changement de taille de la
	 * fenêtre
	 */
	public int ancienneTailleX = 0;
	public int ancienneTailleY = 0;
	public double fae = 1.5, fac = 0.5;
	public double fre = 2.0, frc = 1.5;
	public int zoom = 0;
	public int valx = 0;
	public int valy = 0;
	public int force_active = 0;
	/**
	 * pour savoir si les forces sont activées lorsque le morphing video se
	 * déroule
	 **/

	public int go = 0;
	/** pour la video **/
	public int go2 = 0;
	/** pour avance **/
	/*
	 * booléen permettant de savoir si un des composants du menu est
	 * selectionné. Ce même booléen est utilisé pour les divers choix offerts
	 * par le menu (ces choix étant exclusifs). Il est mis à true quand le choix
	 * est selectionné est devient false quand on décoche.
	 */
	public static boolean on = false;
	/*
	 * Ce booléen est utilisé pour savoir si "classe" a été coché dans le menu.
	 * On n'a pas recours à "on" car on agit sur des composants du menu
	 * exclusifs. or, classe est compatible avec nuances, cercles, histo...
	 */
	public boolean classon = false;

	/*
	 * Ce booléen est utilisé pour savoir si "nuances", "cercles" ou "histo" a
	 * été coché dans le menu. A savoir que ces trois composants peuvent etre
	 * selectionnés si "classes" ou "masquage a été selectionné.
	 */
	public boolean groupon = false;
	public boolean masq = false; // pour savoir si on est en mode masquage ou
									// non
	public boolean cpton = false;

	public int Cpt_transitivite = 0;
	public int nb_transitivite = 0; // pour compter le nombre de sommets de la
									// transitivité

	public double IntensitePolice = 170; // pour l'intensité de la police des
											// noms
	public double taillePolice = 10; // pour l'intensité de la police des noms
	public double intensiteCouleur = 0.25;
	public double kRep = 0;
	public int DistAttrac = 30; // pour l'intensité de la police des noms
	public boolean centre = false;
	public double graviteX = 0.0;
	public double graviteY = 0.0;
	public Graphe morphing;
	public int force_morph = 0; // indique si les forces doivent prendre en
								// compte les repères temporels ou non
	public int new_force = 0; // indique si on utilise les nouvelles formules
								// pour les forces ou si on utilise celles de
								// Saïd
	public int max = 0;

	public boolean NON = false;

	public int etape = 0; /*
						 * pour savoir à quelle étape nous sommes lors du
						 * morphing évolutif
						 */
	public String chemin = " ";
	public int pas = 0; /*----------------------------------------------------------------------------------------------*/
	/*
	 * Pour le morphing evolutif, ActivMorph nous permet de savoir de déclencher
	 * l'étape de transformation. Nous sommes entre deux instances (il y a 10
	 * étapes entre chaque instance.
	 */
	// public boolean ActivMorph = false;
	public boolean top = false;
	public boolean detail = false; // pour afficher le pop up détail des
									// sommets
	// public double valetap = 0.1;
	public int etap = 0;
	public int CptEv1 = 0;
	public int CptEv2 = 0;
	public int a = 0;
	public int couleur_fond = 1; // couleur du fond d'ecran
	/*
	 * pour la fonction centrerGraphe, ChangeGraphe sert à indiquer que le
	 * graphe a été modifié (au moins une de ses coordonnées a changée)
	 * ValSommetX est le vecteur de référence des coordonnées au niveau abcisse
	 * ValSommetY est le vecteur de réSystem.out.println(v.Metrique[k]);férence
	 * des coordonnées au niveau abcisse
	 */
	public boolean ChangeGraphe = false;
	Integer[] ValSommetX;
	Integer[] ValSommetY;

	public int type_morphing = 0; /*
								 * o-> par le slider / 1-> par les grosses
								 * fleches / 2-> par les petites fleches
								 */

	public int nbs = 0; // nombre d'éléments de la transitivité

	public int larg = 0;
	public int longu = 0;

	public static String param1 = "";
	public static String param2 = "";
	public static String param3 = "";
	public static String param5 = "";
	public static String param6 = "";
	public static String param7 = "";
	public static String param8 = "";
	public static String param9 = "";
	public static String param_sauv = "";
	public static String param_transit = "";
	public String cmd1 = "";
	public String cmd2 = "";
	public String cmd3 = "";
	public String cmd4 = "";
	public String cmd5 = "";
	public String cmd6 = "";
	public String cmd7 = "";
	public String cmd8 = "";
	public String cmd9 = "";

	public int repmorph = 1; // indique si on affiche les repères temporels ou
								// non (gestion du menu)

	/* pour conserver les valeurs passées en parametre */
	public static String a0 = null;
	public static String a1 = null;
	public static String a2 = null;
	public static String a3 = null;

	/* pour les forces */

	public double t = 0.1;
	private boolean editable = true;
	private boolean dragging = false;
	private Point ptDrag;

	public Sommet selection = null;

	Vector Clusters[];

	Color[] CouleursClusters;

	int nbClusters = 0;
	JFrame fmorph = new JFrame();
	JFrame frame = new JFrame();

	 JFrame globalwindow = new JFrame("Graph Visualizer");

	int NbSommetsVisibles;
	int param = 2; /*
					 * pour le boutons à cocher concernant l'affichage des
					 * histogrammes du morphing
					 */

	Thread relaxer, morph, morphev, dort;
    boolean circularCentrality = false;
	boolean circulaire = false;
	boolean cluster2 = false;
	boolean force3 = false;
	boolean random = false;
	boolean mst1 = false, mst2 = false;
	boolean mcl = false;
	boolean mcl2 = false;
	boolean pv = false;
	boolean connexe = false;
	boolean morphingActif = false;
	boolean masqueSeuil = false;
	ForceDirect Force;
	FishEyeMenu fisheye = null;

	/* pour indiquer en haut de la fenêtre l'instance en cours. */
	String titre = null;

	Sommet gravite = new Sommet(0, 0, 0);

	String[] Items;

	NomLongs nomslongs = new NomLongs();
	NomLongs categorie = new NomLongs();
	String nom1;
	String nom2;
	  ControlWindow control = null;

    CentralityControlWindow controlCentrality = null;
	Requetage req = null;
	Aide aide_en_ligne = null;

	JPopupMenu contenu;
	int noyau = 0;
	String individuChoisi;
	boolean flag = true;
	double facteur = 1.4;
	int currentInstance = 0, lastInstance = 0;
	int progress = 0;
	int largeur, longueur;
	int pos; // pour savoir, lors du morphing évolutif si on avance ou recule
				// dans les différentes étapes de morphing
	int pos2; // pour savoir si on avance ou recule de facon générale dans le
				// morphing
	private double alpha;
	public boolean showalpha = false;
	public boolean showNuance = false;
	public static boolean exporterVersFile = false;
	public int degreeNumberFilter = 0;
	public int filtreStrength = 0;
	public int filtreProximity = 0;
	public int filtreIntermediarity= 0;
	public int filtreCombined= 0;
	public int filtreGenIntermediarity= 0;
	public int filtreGenProximity = 0;
    public String mesureAmasquer = "";
    private boolean showTempGenMeasures = false;
    
    
	boolean indicateur = false;
	Stack<Sommet> stack2 ;
	Stack<Sommet> stackVoisin;
	
	Vector<Sommet> selonDegre = new Vector<Sommet>();
	Vector<Sommet> selonStrength = new Vector<Sommet>();
	Vector<Sommet> selonCombine = new Vector<Sommet>();
	Vector<Sommet> selonIntermediarity = new Vector<Sommet>();
	Vector<Sommet> selonProximity = new Vector<Sommet>();
	Vector<Sommet> selonGenProximity = new Vector<Sommet>();
	Vector<Sommet> selonGenIntermediarity = new Vector<Sommet>();
	// //////////////////////////////////////////////////////////////////////////
	/**
	 * Initialisation:
	 * Si voisin alors nbre inter = 0 si non infinity 
	 *
	 * @param gr
	 */
	public void initNombreNoeudintermediaire(Graphe gr){
		 for(int i = 0 ; i<gr.nombreSommets() ; i++){
			 Sommet sommet_i = (Sommet)gr.sommets.get(i);  
			 if(sommet_i.voisins.size() > 0){
			 Vector<Sommet> nodeset = new Vector<Sommet>();
			 nodeset.add(sommet_i);
			 sommet_i.setPathBinaire(nodeset);
			 
			 sommet_i.getLocalPath().add(sommet_i);
			 sommet_i.getMesAltenatives().put(1, sommet_i.getLocalPath());
			
			 for(int j = 0 ; j<gr.nombreSommets(); j++){
				 Sommet sommet_j = (Sommet)gr.sommets.get(j);
				 sommet_i.getMesChemins().put(sommet_j, sommet_i.getMesAltenatives());
			     sommet_i.getChemin().put(sommet_j, null);
	             if(sommet_i.voisins.contains(sommet_j)){  
			        sommet_i.getNombreIntermediaire().put(sommet_j, 0);
			 } else { 
				 sommet_i.getNombreIntermediaire().put(sommet_j, Integer.MAX_VALUE);
			 }
			 }
			 }else{
				 sommet_i.setPathBinaire(null);
				 sommet_i.setChemin(null);
				 sommet_i.setNombreIntermediaire(null);
			 }
		}
		 
	}
	//*********************
	int minDegre(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getCentralite_degre() < smin.getCentralite_degre())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minStrength(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getCentralite_poids() < smin.getCentralite_poids())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minIntermediarity(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getBetweennessCentrality() < smin.getBetweennessCentrality())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minProximity(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getClosenessCentrality() < smin.getClosenessCentrality())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minGenDegre(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getCentralite_combine() < smin.getCentralite_combine())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minGenIntermediarity(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getBetweennessGenCentrality() < smin.getBetweennessGenCentrality())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	int minGenCloseness(Vector<Sommet> sommets, int taille)
	{
	    // on considère que le plus grand élément est le premier
	    int i=0, indice_min=0;
	     
	    while(i < taille)
	    {
	    	Sommet s  = (Sommet) sommets.elementAt(i);
	    	Sommet smin  = (Sommet) sommets.elementAt(indice_min);
	        if(s.getClosenessGenCentrality() < smin.getClosenessGenCentrality())
	            indice_min = i;
	        i++;
	    }
	     
	    return indice_min;
	}
	void echanger(Vector<Sommet> sommets, Sommet s, Sommet ss)
	{
	    Sommet tmp;
	     
	    tmp = s;
	    int indexS = sommets.indexOf((Sommet) s );
	    int indexSS = sommets.indexOf((Sommet) ss);
	    sommets.set(indexS, ss);
	    sommets.set(indexSS, s);
	}
	
	
	void tri_selectionDegre(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minDegre(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.degreeRank = index;
//			System.out.println("Degree Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Degree  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionStrength(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minStrength(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.StrengthRank= index;
		//	System.out.println("Strength Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Strength  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionIntermediarity(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	  
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minIntermediarity(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.intermediarityRank = index;
	//		System.out.println("Intermediarity Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Betweenness  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionCloseness(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minProximity(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.proximityRank = index;
//			System.out.println("proximity Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    proximity  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionGenDegree(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minGenDegre(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.combinedRank= index;
		//	System.out.println("Gen Degree Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Gen Degree  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionGenBetw(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minGenIntermediarity(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.genIntermediarityRank = index;
	//		System.out.println("gen intermediarity Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Gen Intermediarity  :  "  + sommet_i.getCentralite_degre());
		}
	}
	void tri_selectionGenCloseness(Vector<Sommet> sommets, int taille)
	{
	    int indice_min;
	    
	 
	    for(; taille > 1 ; taille--) // tant qu'il reste des éléments non triés
	    {
	        indice_min = minGenCloseness(sommets, taille);
	     
	        echanger(sommets, (Sommet ) sommets.elementAt(taille-1), (Sommet)sommets.elementAt(indice_min)); // on échange le dernier élément avec le plus grand
	    }
	    for(int i = 0; i< sommets.size();  i++){
			Sommet sommet_i = (Sommet) sommets.elementAt(i);
			int index = sommets.indexOf(sommet_i);
			sommet_i.genProximityRank = index;
		//	System.out.println("Gen Proximity Rank   :  "+sommet_i.getNom() + "  :   "+ index + "    Gen proximity :  "  + sommet_i.getCentralite_degre());
		}
	}
	//******************
	
	

	/**
	 * Calcul de centralite de Freeman de degre: degre Simple (Binaire) et degre relatif aux matrices Valués
	 * @param gr
	 */
	public  void degreSimple(Graphe  gr) { 
		Graphe panel = gr;
		double poids = 0;
		int N = gr.nombreSommets();
		double centralite_degre = 0;
		double centralite_poids = 0;
		for (int i = 0; i < N-1; i++) {
			Sommet s = panel.getSommet(i);
			Vector vecteurVoisins = panel.getSommet(i).voisins;
			int nbre_voisin = vecteurVoisins.size();
			double somme_poids = 0;
			for (int j = 0; j < vecteurVoisins.size(); j++) {
				Sommet voisin = (Sommet) vecteurVoisins.elementAt(j);
				int indice = panel.areteDansGraphe(voisin, s);
				if (indice != -1) {
					poids = panel.getArete(indice).getVal();
				}
				somme_poids += poids;

			}
			centralite_poids = ((1 / (double) (N - 1)) * (somme_poids));
			centralite_degre = (1 / (double) (N - 1))* (panel.getSommet(i).voisins.size());
			panel.getSommet(i).setCentralite_degre(centralite_degre);
			panel.getSommet(i).setCentralite_poids(centralite_poids);
			
		}
	
		selonDegre = gr.sommets;
		selonStrength = gr.sommets;
		tri_selectionDegre(selonDegre, selonDegre.size());
		tri_selectionStrength(selonStrength, selonStrength.size());

	}
	
   
	/**
	 * calcul de centralite combine en se basant sur un parametre alpha qu'on
	 * modifie a travers un slider et sur les mesure de degre && de poids simple
	 */
	public void degree_gen(Graphe gr, double alpha) {
		
		double facteur = 0;
		Graphe panel = gr;
		double degre = 0;
		double poids = 0;
		double combine = 0;
		degreSimple(gr);
		for (int i = 0; i < panel.nombreSommets(); i++) {
			facteur = new Double(panel.getSommet(i).getAlpha());
			degre = panel.getSommet(i).getCentralite_degre();
			poids = panel.getSommet(i).getCentralite_poids();
			combine = (Math.pow(degre, 1.0 - alpha))*(Math.pow(poids, alpha));
			panel.getSommet(i).setCentralite_combine(combine);

		}
		selonCombine = gr.sommets;
		tri_selectionDegre(selonCombine, selonCombine.size());        
	}
	public void closenessCentralityGen(GraphPanel gp, double alpha){
		
		
		
		closenessCentralityfreeMan(gp , alpha);
        for(int i = 0; i< gp.getGraphe().nombreSommets(); i++){
        	Sommet sommet_i = gp.getGraphe().getSommet(i);
        	double valeur =  Math.pow(sommet_i.getClosenessCentrality() , alpha);
        	sommet_i.setClosenessGenCentrality(valeur);
        }
		
		
		
//		int N  =  gp.getGraphe().nombreSommets();
//  	//	Dijkstra dijkstra = new Dijkstra(gp , alpha);
//  		for(int i = 0 ; i < N ; i++){
//  			Sommet sommet_i = gp.getGraphe().getSommet(i);
//  			double distance = 0;
//  			double somme  = 0;
//  			double fraction = 0;
//  			if(sommet_i.voisins.size() > 0){
//  			for(int j = 0; j<N ; j++){
//  				Sommet sommet_j = gp.getGraphe().getSommet(j);
//  				if(sommet_j.voisins.size() >0){
//  					distance =Math.pow(sommet_i.getDistance().get(sommet_j), alpha);
//  				}
//  				somme+=distance;
//  			}
//  		   fraction = 1/somme;
//  		   sommet_i.setClosenessGenCentrality(fraction);
//  			}
//  		}
  		//selonProximity = gr.sommets;
  		setSelonProximity(gr.getSommets());
		tri_selectionGenCloseness(getSelonProximity(), getSelonProximity().size());
	}
	public Vector<Sommet> getSelonProximity() {
		return selonProximity;
	}
	public void setSelonProximity(Vector<Sommet> selonProximity) {
		this.selonProximity = selonProximity;
	}
	public void closenessCentralityfreeMan(GraphPanel gp, double alpha){
      		int N  =  gp.getGraphe().nombreSommets();
      		//Dijkstra dijkstra = new Dijkstra(gp.getGraphe() , 1);
      		for(int i = 0 ; i < N ; i++){
      			Sommet sommet_i = gp.getGraphe().getSommet(i);
      			//System.out.println(sommet_i.getNom());
      			double distance = 0;
      			double somme  = 0;
      			double fraction = 0;
      			if(sommet_i.voisins.size() > 0){
      			for(int j = 0; j<N ; j++){
      				Sommet sommet_j = gp.getGraphe().getSommet(j);
      				if((sommet_j.voisins.size() >0) &&(sommet_i.getDistance().get(sommet_j) != Double.MAX_VALUE)){
      					distance = sommet_i.getDistance().get(sommet_j);
          				somme+=distance;

      				}
      			}
      			 
      		   fraction = ((N-1)/somme) ; 
      		   double valeur = 0.1 * Math.abs(fraction);
      		   valeur +=  sommet_i.getCentralite_degre()*6;
      		    sommet_i.setClosenessCentrality(valeur );
   //   		   System.out.println(" closeness centrality of :: " + sommet_i.getNom() + "    est  " + sommet_i.getClosenessCentrality() );
      			}
      		}
      		selonProximity= gr.sommets;
    		tri_selectionCloseness(selonProximity, selonProximity.size());
	}
	public void betweennessCentralityGen(GraphPanel gp , double alpha ){
	//	Dijkstra dijkstra = new Dijkstra(gp,alpha);
		if(alpha == 0){
			//betweennessCentralityFreeMan(gp);
		betweennessFreeMan(gp);
		}else{
			
			betweennessFreeMan(gp);
            for(int i = 0; i< gp.getGraphe().nombreSommets(); i++){
            	Sommet sommet_i = gp.getGraphe().getSommet(i);
            	double valeur =  Math.pow(sommet_i.getBetweennessCentrality() , alpha);
            	sommet_i.setBetweennessGenCentrality(valeur);
            }
		}	
			
//			
//        /**
//         * sommet_i , sommet__j, sommet_k doivent être connecté au graphe: voisins >0
//         * si non on le rend invisible : non intéressant à étudier !
//         */
//		for (int i = 0; i < gp.getGraphe().sommets.size(); i++) {
//			 double nbreCheminTotal = 0;
//	         double sommeNum = 0;
//
//			/**
//			 * sommet_i : sommet en cours de recherche de la betweenness
//			 */
//			Sommet sommet_i = gp.getGraphe().getSommet(i);			
//			if (sommet_i.voisins.size() > 0) {
//				if(sommet_i.voisins.size() == 1) sommet_i.setBetweennessGenCentrality(0);
//				else{
//				Map<Sommet,Vector<Sommet>> pairs  = new  HashMap<Sommet, Vector<Sommet>>();
//                double fraction = 0;
//				for (int j = 0; j < gp.getGraphe().sommets.size(); j++) {
//					Sommet sommet_j = gp.getGraphe().getSommet(j);
//			     		if (sommet_j != sommet_i && sommet_j.voisins.size() > 0) {
//	                      pairs.put(sommet_j, null);
//	                //      System.out.println("sommet_j   = " + sommet_j.getNom());
//	                	  int k = 0;
//	      		          int nominateur = 0;
//	  				      Vector<Sommet> pairsNodes = new Vector<Sommet>();
//							while (k < gp.getGraphe().sommets.size()) {
//		                		  Sommet sommet_k = gp.getGraphe().getSommet(k);
//		                		  if(sommet_k.voisins.size() > 0){
//		                		  if(sommet_k == sommet_i  || sommet_k == sommet_j){ 
//		                			  k++;
//		                		  }else{ 
//		                				if(pairs.containsKey(sommet_k)){
//		                						k++;
//		                		       }else{        
//		                                pairsNodes.add(sommet_k);
//		                		        if((sommet_j.getPredecessors().get(sommet_k) != null)&&(sommet_j.getPredecessors().get(sommet_k).contains(sommet_i)) && (sommet_j.getPredecessors().get(sommet_k) != null ) ){
//		                			       nominateur++;
//		                			       nbreCheminTotal++;
//		                		         }else{
//		                		        	 nbreCheminTotal++;
//		                		         }
//		                		  k++;
//		                		  }
//		                	  }
//		                	  }else{
//		                		  k++;
//		                	  }
//		                	  }
//							 pairs.put(sommet_j, pairsNodes);
//				               // 	  System.out.println("sommet :" + sommet_j.getNom() + "  numerateur  = " + nominateur);
//				                	  sommeNum += nominateur;
//                }
//				
//				}
//				  fraction = sommeNum/nbreCheminTotal;
//					sommet_i.setBetweennessGenCentrality(fraction);
//				}
//		}
//		//	System.out.println("Betweenness de " + sommet_i.getNom() + "  =  " + sommet_i.getBetweennessCentrality() );
//
//			}
//	}
		selonGenIntermediarity = gr.sommets;
		tri_selectionGenBetw(selonGenIntermediarity, selonGenIntermediarity.size());
		}

	
	public void betweennessFreeMan(GraphPanel gp){
		int n = gp.getGraphe().nombreSommets();
		for(int i = 0 ; i< n; i++){
			int compteur_i = 0;
			Sommet sommet_i = gp.getGraphe().getSommet(i);
			//System.out.println("Sommet en cours : " + sommet_i.getNom());
			for(int j = 0 ; j< n; j++){
				Sommet sommet_j = gp.getGraphe().getSommet(j);
				for(int k = 0 ; k < n; k++){
					Sommet sommet_k = gp.getGraphe().getSommet(k);
				//	System.out.println("couple  :" + "(" + sommet_j.getNom() + "," + sommet_k.getNom() + ")");
					if(sommet_j != sommet_k && sommet_j != sommet_i){
//					if(sommet_j.getDistance().get(sommet_k) != -1 && sommet_j.getDistance().get(sommet_k) != 0){
//						if(sommet_j.getPredecessors().get(sommet_k).contains(sommet_i)) compteur_i++;	
//					//	System.out.println("compteur    :" + compteur_i);
//					}
						
						
						
						if (sommet_j.getChemin() != null && (sommet_j.getChemin().get(sommet_k) != null) && sommet_j.getChemin() != null && (sommet_j.getChemin().get(sommet_k).contains(sommet_i))  
								&&  (sommet_j.getNombreIntermediaire().get(sommet_k)< Integer.MAX_VALUE)){
							
							//System.out.println(sommet_i.getNom()+ "    :     "+sommet_j.getNom()+ "//" + sommet_k.getNom());
							
							compteur_i++;
						}
					}
				}
			}
			sommet_i.setBetweennessCentrality(compteur_i*0.1*sommet_i.voisins.size() );
		//	System.out.println("Btw de   :" + sommet_i.getNom() + "     est : " + sommet_i.getBetweennessCentrality());
		}
		selonIntermediarity= gr.getSommets();
		tri_selectionIntermediarity(selonIntermediarity, selonIntermediarity.size());	}
	
	
	
	
	
	
	
	
	
	
	public void betweennessCentralityFreeMan(GraphPanel gp) {
	
           /**
            * sommet_i , sommet__j, sommet_k doivent être connecté au graphe: voisins >0
            * si non on le rend invisible : non intéressant à étudier !
            */
		for (int i = 0; i < gp.getGraphe().sommets.size(); i++) {
			double sommeNum = 0;
			double nbreCheminTotal = 0;
			Sommet sommet_i = gp.getGraphe().getSommet(i);
			if (sommet_i.voisins.size() == 1) {
				sommet_i.setBetweennessCentrality(0);
			} else {
				/**
				 * 
				 */
				int n = gp.getGraphe().nombreSommets();
				
				Map<Sommet, Vector<Sommet>> pairs = new HashMap<Sommet, Vector<Sommet>>();       //?
				if (sommet_i.voisins.size() > 1) {
					double fraction = 0;
					for (int j = 0; j < Math.round((n+1)/2); j++) {
						Sommet sommet_j = gp.getGraphe().getSommet(j);
						if (sommet_j != sommet_i) {
							pairs.put(sommet_j, null);
							if (sommet_j.voisins.size() > 0) {
								int k = Math.round((n+1)/2);
								int nominateur = 0;
								Vector<Sommet> pairsNodes = new Vector<Sommet>();
								while (k < (n-1)) {
									Sommet sommet_k = gp.getGraphe().getSommet(k);
									if (sommet_k.voisins.size() > 0) {
										if (sommet_k == sommet_i|| sommet_k == sommet_j || pairs.containsKey(sommet_k)) {
											k++;
										} else {
//											System.out.println("Sommet_i  :" + sommet_i.getNom()+ ", "+ " " +
//													" Sommet_j  :" + sommet_j.getNom() + ",   " + "Sommet_k  : " + sommet_k.getNom());

//											if (pairs.containsKey(sommet_k)) {      // zeyda ????
//												k++;
//											} else {
												pairsNodes.add(sommet_k);
												if ((sommet_j.getChemin().get(sommet_k) != null) && (sommet_j.getChemin().get(sommet_k).contains(sommet_i))  
														&&  (sommet_j.getNombreIntermediaire().get(sommet_k)< Integer.MAX_VALUE)) {
													nominateur++;
													nbreCheminTotal++;
												} else {
													nbreCheminTotal++;
												}
												k++;
											//}
										}
									} else {
										k++;
									}
								}
								pairs.put(sommet_j, pairsNodes);
								// System.out.println("sommet :" +
								// sommet_j.getNom() + "  numerateur  = " +
								// nominateur);
								sommeNum += nominateur;
							}

						}

					}

					fraction = sommeNum / nbreCheminTotal;
					sommet_i.setBetweennessCentrality(fraction);
				}

			}
//			System.out.println("Betweenness de " + sommet_i.getNom() + "  =  "
//					+ sommet_i.getBetweennessCentrality());

		}
		selonIntermediarity= gr.sommets;
		tri_selectionIntermediarity(selonIntermediarity, selonIntermediarity.size());
		}
		
	
	

	public void init(int periode) {
        
		cmd1 = param1;
		cmd2 = param2;
		cmd3 = param3;
		cmd5 = param5;
		cmd6 = param6;
		cmd7 = param_transit;
		cmd9 = param9;
		
		
		
		
		
		if(periode  == 1){
			
// A faire Combinaison Activation pour les instances temporels.
		//degree
		controlCentrality = new CentralityControlWindow(periode);
		controlCentrality.slider1.setMinimum(0);
		controlCentrality.slider1.setMaximum(gr.nombreSommets());
		controlCentrality.slider1.setValue(0);
		controlCentrality.slider1.setMinorTickSpacing(Math.round((gr.nombreSommets()*10)/100));
		controlCentrality.slider1.addChangeListener(new NodeNumber());
		
		
////		//Strength
//
	
		controlCentrality.slider2.setMinimum(0);
		controlCentrality.slider2.setMaximum(gr.nombreSommets());
		controlCentrality.slider2.setValue(0);
		controlCentrality.slider2.setMinorTickSpacing(Math.round((gr.nombreSommets()*10)/100));
		controlCentrality.slider2.addChangeListener(new NodeStrength());
		
		
	
		//Intermediarity
		
		controlCentrality.slider3.setMinimum(0);
		controlCentrality.slider3.setMaximum(gr.nombreSommets());
		controlCentrality.slider3.setValue(0);
		controlCentrality.slider3.setMinorTickSpacing(Math.round((gr.nombreSommets()*10)/100));
		controlCentrality.slider3.addChangeListener(new NodeIntermediarity());
		controlCentrality.slider3.setEnabled(false);
		//Proximity
		
	
		controlCentrality.slider4.setMinimum(0);
		controlCentrality.slider4.setMaximum(gr.nombreSommets());
		controlCentrality.slider4.setValue(0);
		controlCentrality.slider4.setMinorTickSpacing(Math.round((gr.nombreSommets()*10)/100));
		controlCentrality.slider4.addChangeListener(new NodeProximity());
		controlCentrality.slider4.setEnabled(false);
		
		
		controlCentrality.addKeyListener(this);
		this.controlCentrality.fenetre.setVisible(true); //// uniquement si periode == 1 ?!! et une mesure est coché !!
		}
		
		control = new ControlWindow(periode);
		//setControl(new ControlWindow(instance))
		control.slider1.setMinimum((int) gr.minArete);
		control.slider1.setValue((int) gr.minArete);
		control.slider1.setMaximum((int) gr.maxArete);
		control.slider1.setMinorTickSpacing(1);
		
		
		


		
		
		
		control.slider1.addChangeListener(new nouveauSeuil());
		/** slider1 = Filtrage **/
		control.slider3.addChangeListener(new IntensiteCouleurs());
		/** slider3 = Intensité des liens **/
		control.slider4.addChangeListener(new FacteurClustering());
		/** slider4 = Densité de clustering **/
		control.slider2.addChangeListener(new AppliForceRepuls());
		/** slider2 = Force de répulsion **/
		control.slider7.addChangeListener(new IntensitePolice());
		/** slider7= Intensité de la police des noms **/
		
		
		
		/** 
		 * Class Nodes by their Centrality Level within a graph Circular representation
		 */
		
	
		control.slider77.addChangeListener(new RepartitionCouleurCentrality());
//		if(showNuance == true ) {
////			control.slider77.setVisible(true);
////			control.getPanel3().getComponent(2).setVisible(true);
//			control.getPanel3().getComponent(2).setEnabled(true);
//			control.getPanel3().getComponent(3).setEnabled(true);
//		}
//		
		control.slider6.addChangeListener(new DistanceRepulsion());
		/** slider6= Distance cible de répulsion **/
		control.slider8.addChangeListener(new DistanceAttraction());
		/** slider8= Distance cible d'attraction **/
		control.slider9.addChangeListener(new TaillePolice());
		/** slider9= taille de la police **/
		control.slider10.addChangeListener(new KcoreVoisins());
		/** slider10= Filtrage selon le nombre de voisins **/
		control.slider11.addChangeListener(new nouveauSeuilTransit());
		/** slider11 = transitivité **/
		control.slider11.setMinimum(0);

		control.slider11.setValue(0);
		control.slider11.setMaximum(20);
		control.slider1.setMinorTickSpacing(1);

		control.slider10.setMinimum(0);
		control.slider10.setValue(0);
		control.slider10.setMaximum(gr.maxVoisin(gr));
		control.slider10.setMinorTickSpacing(1);
        intensiteCouleur = control.slider77.getValue();
		if (periode > 1) {
			control.slider5.addChangeListener(new ChoixInstance()); /*
																	 * slider5 =
																	 * Changement
																	 * d
																	 * 'instance
																	 */
			control.recule.addActionListener(new ChoixTypeInstance());
			control.avance.addActionListener(new ChoixTypeInstance());
			control.compteur.addActionListener(new ChoixTypeInstance());
		}

		setLayout(new BorderLayout(800, 830));
		this.setBackground(Color.black);

		cnx.sans = gr.sans;
		cnx.longs = gr.longs;
		cnx.cercle = gr.cercle;
		lArbre1 = lArbre2 = null;
		if (periode > 1)
			gr.typeGraphe = 2;
		else
			gr.typeGraphe = 1;

		frame.addKeyListener(this);
		this.frame.setSize(800, 830);
		this.frame.setLocation(185, 50);

        		
		
		if (!this.frame.getTitle().contains("Morphing")) {
			control.addKeyListener(this);
			final MenuItemListener menu = new MenuItemListener(this);
			this.frame.setJMenuBar(menu.barreMenus);
			this.control.fenetre.setVisible(true);
			this.frame.getContentPane().add(this, BorderLayout.CENTER);
			this.frame.setLocation(185, 50);
		}
		// this.frame.setResizable(true);

		this.frame.setVisible(true);
		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet s = gr.getSommet(i);
			gr.getVoisins(s, false, gr.seuil);
			s.nbvoisins = s.voisins.size();
		}
		/** Initialisation des sliders pour les forces selon le cas d'étude **/
		if (gr.typeMat == 1) {
			control.slider8.setValue(11);
			control.slider6.setValue(6);
		}

	}

	public void initColor() {
		CouleursClusters = new Color[nbClusters];
		int tour = 0;
		CouleursClusters[0] = Color.blue;
		if (nbClusters >= 2)
			CouleursClusters[1] = Color.cyan;
		if (nbClusters >= 3)
			CouleursClusters[2] = Color.green;
		if (nbClusters >= 4)
			CouleursClusters[3] = Color.magenta;
		if (nbClusters >= 5)
			CouleursClusters[4] = Color.orange;
		if (nbClusters >= 6)
			CouleursClusters[5] = Color.pink;
		if (nbClusters >= 7)
			CouleursClusters[6] = Color.red;
		if (nbClusters >= 8)
			CouleursClusters[7] = Color.yellow;
		if (nbClusters >= 9)
			CouleursClusters[8] = Color.lightGray;
		if (nbClusters >= 10)
			CouleursClusters[9] = Color.gray;

		for (int i = 10; i < nbClusters; i++) {
			if (i % 10 == 0)
				tour++;
			CouleursClusters[i] = CouleursClusters[tour * i % 10].darker();
		}
	}

	static void echangerElements(String[] t, int m, int n) {
		String temp = t[m];

		t[m] = t[n];
		t[n] = temp;
	}

	static int partition(String[] t, int m, int n) {
		String v = t[m]; // valeur pivot
		int i = m - 1;
		int j = n + 1; // indice final du pivot

		while (true) {
			do {
				j--;
			} while (t[j].compareTo(v) > 0);
			do {
				i++;
			} while (t[i].compareTo(v) < 0);
			if (i < j) {
				echangerElements(t, i, j);
			} else {
				return j;
			}
		}
	}

	static void triRapide(String[] t, int m, int n) {
		if (m < n) {
			int p = partition(t, m, n);
			triRapide(t, m, p);
			triRapide(t, p + 1, n);
		}
	}

	GraphPanel(Graphe gr, boolean editable) {
		super();
		this.gr = gr;
		this.mg = new Graphe(nbClusters);
		this.editable = editable;
		addMouseListener(this);
	}

	public Graphe getGraphe() {
		return gr;
	}

	public void setGraphe(Graphe gr) {
		this.gr = gr;
		repaint();
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void centrerGraphe(Graphe g) {
		// this.frame.setLocation(185, 50);
		/*
		 * Pour cette fonction, on se base sur une fenetre de référence. Chaque
		 * fois qu'une fonction modifiera l'aspect du graphe (changera au moins
		 * une coordonnée d'un sommet), le booleen ChangeGraphe sera mis à true.
		 * Lorsque ce dernier est mis à true, on enregistre dans un vecteur
		 * toutes les coordonnées des sommets de la figure dans un vecteur de
		 * référence. Dès que l'on redimensionne la fenetre, les nouvelles
		 * coordonnées (dû au redimensionnement) sont calculées par rapport à
		 * notre vecteur de référence. Le calcul s'effectue de la facon suivante
		 * : on prend le point initial de référence, on le divise par la taille
		 * de la fenetre initiale, et on multiplie le tout par la taille de la
		 * nouvelle fenêtre. On forme ainsi une homothétie.
		 */
		// if ((control.slider12.getValue()==0)| g.instance==1){

		if (force_morph != 2 && !mcl) {
			Dimension d = this.getSize();
			double w = d.width;
			double h = d.height;
			double gravx = 0.0;
			double gravy = 0.0;
			int cx = 0;
			int cy = 0;
			int n = 0;

			if ((ancienneTailleX != d.width | ancienneTailleY != d.height))
				gr.CalculRepere2((int) w - 60, (int) h - 15);

			if (circulaire == true) {
				n = 5;
			} else
				n = (int) (10);
			if (ChangeGraphe == true) {
				ValSommetX = new Integer[gr.nombreSommets() + 1];
				ValSommetY = new Integer[gr.nombreSommets() + 1];
				ValSommetX[0] = (int) w;
				ValSommetY[0] = (int) h;

				int inst = 0;

				for (int i = 0; i < gr.nombreSommets() - inst; i++) {
					Sommet s = gr.getSommet(i);
					ValSommetX[i + 1] = s.x;
					ValSommetY[i + 1] = s.y;
				}
				ChangeGraphe = false;
			}

			for (int l = 0; l < g.nombreSommets(); l++) {
				Sommet p = g.getSommet(l);
				if ((ancienneTailleX != d.width | ancienneTailleY != d.height)) {
					p.x = (int) (ValSommetX[l + 1] * w / ValSommetX[0]);
					p.y = (int) (ValSommetY[l + 1] * h / ValSommetY[0]);
					if (p.x < n)
						p.x = n;
					if (p.y < 10)
						p.y = 10;
					if (p.x > w - n)
						p.x = (int) (w) - n;
					if (p.y > h - 10)
						p.y = (int) (h) - 5;
				} else if (!p.nom.contains("virtuel")
						&& !p.nom.contains("Repere")) {
					p.x = (int) (ValSommetX[l + 1] * w / ValSommetX[0]);
					p.y = (int) (ValSommetY[l + 1] * h / ValSommetY[0]);
					if (p.x < n)
						p.x = n;
					if (p.y < 10)
						p.y = 10;
					if (p.x > w - n)
						p.x = (int) (w) - n;
					if (p.y > h - 10)
						p.y = (int) (h) - 5;
				}
			}

			for (int i = 0; i < g.nombreSommets(); i++) {
				Sommet s = g.getSommet(i);
				cx += s.x;
				cy += s.y;
			}
			if ((gr.typeMat == 0 | gr.typeMat == 1) && (NON == false)) {
				if (g.nombreSommets() != 0) {
					gravx = cx / (g.nombreSommets());
					gravy = cy / (g.nombreSommets());
				}
				// if (g.clustering==false){
				if (zoom == 1) {
					if (valx != 0 && valy != 0) {
						gravx = valx;
						gravy = valy;
					}
				}
				if (gravx > (w / 2) | gravy > (h / 2) | gravx < (w / 2)
						| gravy < (h / 2)) {
					for (int i = 0; i < g.nombreSommets(); i++) {
						Sommet s = g.getSommet(i);
						if ((ancienneTailleX != d.width | ancienneTailleY != d.height)) {

							if (gravx > (w / 2))
								s.x -= (gravx - (w / 2));
							if (gravy > (h / 2))
								s.y -= (gravy - (h / 2));
							if (gravx < (w / 2))
								s.x += ((w / 2) - gravx);
							if (gravy < (h / 2))
								s.y += ((h / 2) - gravy);
							if (s.x < n)
								s.x = n;
							if (s.y < 10)
								s.y = 10;
							if (s.x > w - n)
								s.x = (int) (w) - n;
							if (s.y > h - 10)
								s.y = (int) (h) - 5;

						} else {
							if (!s.nom.contains("virtuel")
									&& !s.nom.contains("Repere")) {
								if (gravx > (w / 2))
									s.x -= (gravx - (w / 2));
								if (gravy > (h / 2))
									s.y -= (gravy - (h / 2));
								if (gravx < (w / 2))
									s.x += ((w / 2) - gravx);
								if (gravy < (h / 2))
									s.y += ((h / 2) - gravy);
								if (s.x < n)
									s.x = n;
								if (s.y < 10)
									s.y = 10;
								if (s.x > w - n)
									s.x = (int) (w) - n;
								if (s.y > h - 10)
									s.y = (int) (h) - 5;
							}
						}

					}

				}

			}

			ancienneTailleX = d.width;
			ancienneTailleY = d.height;
			if (NON == true)
				NON = false;
		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		int instance = 0;
//		if(gr.afficherDegreeClass == true){
//			gr.genCircularCentrality(g, intensiteCouleur, gr);
//			for(int i =0; i< gr.sommets.size(); i++){
//				Sommet s = (Sommet)gr.sommets.get(i);
//				//s.afficherDegre(g);
//				if(gr.stressDegree){
//				s.epinglerDegree(g ,  "degre" );
//				}else{
//					s.epinglerDegree(g ,  "" );
//				}
//			}
//			
//		}
		
	
		
		if (control.slider5 != null)
			instance = control.slider5.getValue();
       
		if (morphing != null) {
			centrerGraphe(morphing);
			morphing.paintComponent(g, param, repmorph, couleur_fond,
					IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
		}

		if (!connexe && morphing == null) {
			if (!mst1 && !mst2 && !mcl && instance == 0) {
				if (!this.frame.getTitle().contains("Cluster"))
					centrerGraphe(gr);
				gr.paintComponent(g, param, repmorph, couleur_fond,
						IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
			} else if (mst1 && !mst2 && lArbre1 != null) {
				centrerGraphe(lArbre1);
				lArbre1.paintComponent(g, param, repmorph, couleur_fond,
						IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
			} else if (!mst1 && mst2 && lArbre2 != null) {
				centrerGraphe(lArbre2);
				lArbre2.paintComponent(g, param, repmorph, couleur_fond,
						IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
			} else if (mcl) {
				int gravx = 0;
				int gravy = 0;
				for (int i = 0; i < mg.nombreSommets(); i++) {
					Sommet s = mg.getSommet(i);
					gravx += s.x;
					gravy += s.y;
				}
				gravx /= mg.nombreSommets();
				gravy /= mg.nombreSommets();
				for (int i = 0; i < mg.nombreSommets(); i++) {
					Sommet s = mg.getSommet(i);
					if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
						if (gravx > (this.getHeight() / 2))
							s.x -= (gravx - (this.getHeight() / 2));
						if (gravy > (this.getHeight() / 2))
							s.y -= (gravy - (this.getHeight() / 2));
						if (gravx < (this.getHeight() / 2))
							s.x += ((this.getHeight() / 2) - gravx);
						if (gravy < (this.getHeight() / 2))
							s.y += ((this.getHeight() / 2) - gravy);
					}
				}
				// centrerGraphe(gr);
				mg.paintComponent(g, param, repmorph, couleur_fond,
						IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
			}
		} else if (cnx != null) {
			centrerGraphe(cnx);
			cnx.paintComponent(g, param, repmorph, couleur_fond,
					IntensitePolice, taillePolice, etape + 1 , intensiteCouleur);
		}
		if (dragging) {
			Color oldclr = g.getColor();
			g.setColor(new Color(0, 0, 0));
			g.fillOval(ptDrag.x - Sommet.RAYON, ptDrag.y - Sommet.RAYON,
					2 * Sommet.RAYON, 2 * Sommet.RAYON);
			g.setColor(oldclr);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0 || !dragging)
			return;
		ptDrag = new Point(e.getX(), e.getY());
		ChangeGraphe = true;
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
		if (contenu != null && contenu.isVisible()) {
			Object src = e.getSource();
			System.out.println(src);
		}
	}

	/**
	 * ******************************************************* gestion du clic
	 * de souris **************************************************************
	 */
	public void mouseClicked(MouseEvent e) {
		int count = e.getClickCount();
		int max = 1;
		int maxi = 1;
		int nb_som = 0;
		try {
			ptDrag = new Point(e.getX(), e.getY());
			if (centrer == true) {
				NON = true;
				int gravx = 0;
				int gravy = 0;
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = gr.getSommet(i);
					if (s.getVisible() && !s.nom.contains("virtuel")
							&& !s.nom.contains("Repere")) {
						gravx += s.x;
						gravy += s.y;
						nb_som += 1;
					}
				}

				gravx /= nb_som;
				gravy /= nb_som;
				if (gravx > (ptDrag.x) | gravy > (ptDrag.y)
						| gravx < (ptDrag.x) | gravy < (ptDrag.y)) {
					for (int i = 0; i < gr.nombreSommets(); i++) {
						Sommet s = gr.getSommet(i);
						if (!s.nom.contains("Repere")
								&& !s.nom.contains("virtuel") && s.getVisible()) {
							if (gravx > (ptDrag.x))
								s.x -= (gravx - (ptDrag.x));
							if (gravy > (ptDrag.y))
								s.y -= (gravy - (ptDrag.y));
							if (gravx < (ptDrag.x))
								s.x += ((ptDrag.x) - gravx);
							if (gravy < (ptDrag.y))
								s.y += ((ptDrag.y) - gravy);
						}
					}
					repaint();
					centrer = false;
					gravx = 0;
					gravy = 0;
					nb_som = 0;
					for (int i = 0; i < gr.nombreSommets(); i++) {
						Sommet s = gr.getSommet(i);
						if (s.getVisible()) {
							gravx += s.x;
							gravy += s.y;
							nb_som += 1;
						}
					}
					if (gr.instance > 1)
						nb_som -= gr.instance;
					gravx /= nb_som;
					gravy /= nb_som;
				}
			}
			/** cas où on zoom une zone **/
			if (zoom == 1) {
				valx = e.getX();
				valy = e.getY();
				double h = this.frame.getHeight() / 2;
				double w = this.frame.getWidth() / 2;
				double moinsx = e.getX() - w;
				double moinsy = e.getY() - h;
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = gr.getSommet(i);
					/*
					 * s.x+=moinsx; s.y+=moinsy; if (s.x>this.frame.getWidth() |
					 * s.x<0) s.setInvisible(); if (s.y>this.frame.getHeight() |
					 * s.y<0) s.setInvisible();
					 */
				}

				// zoomer(control.slider12.getValue());
			} else {
				Sommet S_init = gr.hitTest(ptDrag, true);
				/** initialisation de num **/
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = gr.getSommet(i);
					s.num = 0;
				}
				S_init.num = 1;
				this.frame.setTitle("Transitivite de : " + S_init.nom);
				calculeTransit(gr, S_init);
			}
			if (e.getButton() == 1 && count >= 2) {
				/**
				 * bouton gauche et double click -> blocage d'un sommet (on le
				 * fixe)
				 **/

				ptDrag = new Point(e.getX(), e.getY());
				if (morphing == null)
					selection = gr.hitTest(ptDrag, true);
				else
					selection = morphing.hitTest(ptDrag, true);
				if (selection != null) {
					if (selection.fixe)
						selection.fixe = false;
					else {
						selection.fixe = true;

					}
				}
			}
			if (e.getButton() == 3 && !mcl) {
				/**
				 * bouton gauche puis droit -> affichage d'une fenêtre pop-up
				 * indiquant les voisins directs du sommet selectionné
				 **/
				dragging = true;
				Graphics2D g = (Graphics2D) this.getGraphics();
				final JPopupMenu pop = new JPopupMenu("détail du sommet");
				pop.add("Sommet selectionné : " + selection.getNom());
				if (!this.getGraphe().getOriente()) {
//					pop.add("degree centrality   :" + selection.getCentralite_degre());
//					pop.add("link centrality   :" + selection.getCentralite_poids());
//					pop.add("closeness centrality  : " + selection.getClosenessCentrality());
//					pop.add("betweenness centrality  : " + selection.getBetweennessCentrality());
					

				}
                
				pop.add("Voisins directs : " + "(" + selection.voisins.size() + ")" );
				/* on récupère les voisins du sommet en cours */
				gr.getVoisins(selection, false, gr.seuil);
								/* On affiche chaque voisin du sommet selectionné */
				for (int j = 0; j < selection.voisins.size(); j++) {

					Sommet v = (Sommet) selection.voisins.elementAt(j);
					if (!selection.equals(v) && v.getVisible()) {
						pop.add("\n        " + v.getNom());
						String d = v.getNom();
						g.setColor(Color.yellow);
						g.drawString(d, v.getX(), v.getY());
					}
				}
				pop.show(e.getComponent(), e.getX(), e.getY());
			}
			if (e.getButton() == 2) {
				/** bouton central -> affichage des notices du sommet cliqué **/

				param5 = param_sauv;
				String nomcherche = "";
				ptDrag = new Point(e.getX(), e.getY());
				Sommet cherche = gr.hitTest(ptDrag, true);
				String nrech = cherche.nomlong;

				for (int i = 0; i < nrech.length(); i++) {
					char c = nrech.charAt(i);
					if ((c == ' ') | (c == '-') | (c == '_')) {
						nomcherche += "%";
					} else
						nomcherche += c;
				}
				if (cherche.type == 0)
					param8 = nom1;
				else
					param8 = nom2;
				cmd8 = param8;

				System.out.println("tableur2 " + param1 + " " + param2 + " "
						+ param3 + " " + nomcherche + " " + param5 + " "
						+ param6 + " " + param7 + " " + param8 + " " + param9);

				try {
					Runtime.getRuntime().exec(
							"tableur2 " + param1 + " " + param2 + " " + param3
									+ " " + nomcherche + " " + param5 + " "
									+ param6 + " " + param7 + " " + param8
									+ " " + param9);
				} catch (Exception err) {
				}

			}
			if (e.getButton() == 1) {
				ptDrag = new Point(e.getX(), e.getY());
				Sommet srep = gr.hitTest(ptDrag, true);
			}

			if (e.getButton() == 3 && mcl) {
				/**
				 * bouton gauche puis bouton droit APRES GRAPHE DE CLUSTERS ->
				 * affichage d'un pop-up indiquant les composants de la classe
				 **/
				ptDrag = new Point(e.getX(), e.getY());
				selection = mg.hitTest(ptDrag, true);
				boolean trouve = false;
				int i = 0;
				if (selection != null) {
					for (i = 0; i < mg.sommets.size() && !trouve; i++) {
						metaNoeud mn = (metaNoeud) mg.getSommet(i);
						if (mn.nom.equals(selection.nom))
							trouve = true;
					}
					if (trouve) {
						JMenuItem item;

						i--;
						String[] Items = new String[Clusters[i].size()];

						for (int j = 0; j < Clusters[i].size(); j++) {
							int indice = ((Integer) (Clusters[i].elementAt(j)))
									.intValue();
							Sommet s = gr.getSommet(indice);
							if (s.nomlong != null)
								Items[j] = s.nomlong;
							else
								Items[j] = s.nom;
						}
						triRapide(Items, 0, Clusters[i].size() - 1);

						contenu = new JPopupMenu();
						item = new JMenuItem("Cluster " + i + " : "
								+ Clusters[i].size());
						item.addItemListener(this);
						item.addMouseListener(this);
						contenu.add(item);
						contenu.addSeparator();

						fisheye = new FishEyeMenu("Contenu");
						fisheye.addItemListener(this);
						fisheye.addMouseListener(this);

						contenu.addMouseListener(this);
						contenu.add(fisheye);

						for (int j = 0; j < Clusters[i].size(); j++) {
							item = new JMenuItem(Items[j]);
							fisheye.add(item);
						}
					}
					contenu.setLocation(this.frame.getLocation().x
							+ selection.x + 10, this.frame.getLocation().y
							+ selection.y);

					contenu.setVisible(true);
				}
			} else if (e.getButton() == 2 && (mcl || mcl2)) { // Extraction de
																// cluster
				ptDrag = new Point(e.getX(), e.getY());
				selection = mg.hitTest(ptDrag, true);
				if (contenu != null)
					contenu.setVisible(false);
				boolean trouve = false;
				int i = 0;
				if (selection != null) {
					for (i = 0; i < mg.sommets.size() && !trouve; i++) {
						metaNoeud mn = (metaNoeud) mg.getSommet(i);
						if (mn.nom.equals(selection.nom))
							trouve = true;
					}
					if (trouve) {
						i--;
						Cluster = new Graphe(false);
						Cluster.typeGraphe = gr.typeGraphe;
						int cpt = 0;
						for (int j = 0; j < Clusters[i].size(); j++) {
							int indice = ((Integer) (Clusters[i].elementAt(j)))
									.intValue();

							Sommet interne = new Sommet(gr.getSommet(indice).x,
									gr.getSommet(indice).y,
									gr.getSommet(indice).z,
									gr.getSommet(indice).couleur,
									gr.getSommet(indice).nom, 0, 0, 0);

							interne.type = gr.getSommet(indice).type;
							interne.metrique = gr.getSommet(indice).metrique;
							interne.couleur = gr.getSommet(indice).couleur;
							interne.couleurClasse = gr.getSommet(indice).couleurClasse;
							interne.nomlong = gr.getSommet(indice).nomlong;
							interne.nom = gr.getSommet(indice).nom;

							for (int ll = 1; ll <= gr.instance; ll++)
								interne.Metrique[ll] = gr.getSommet(indice).Metrique[ll];
							interne.Metrique[gr.instance + 1] = gr
									.getSommet(indice).Metrique[gr.instance + 1];

							Cluster.ajouterSommet(interne);

							for (int k = 0; k < nbClusters; k++) {
								if (k != i) {
									for (int l = 0; l < Clusters[k].size(); l++) {
										int indiceExterne = ((Integer) (Clusters[k]
												.elementAt(l))).intValue();

										Sommet externe_tmp = gr
												.getSommet(indiceExterne);
										int ind_a = gr.areteDansGraphe(
												externe_tmp, interne);

										if (!gr.getSommet(indice).equals(
												externe_tmp)
												&& ind_a != -1) {
											Sommet externe = new Sommet(
													externe_tmp.x,
													externe_tmp.y,
													externe_tmp.z,
													externe_tmp.couleur,
													externe_tmp.nom, 0, 0, 0);

											externe.type = externe_tmp.type;
											externe.metrique = externe_tmp.metrique;
											externe.couleur = externe_tmp.couleur;
											externe.couleurClasse = externe_tmp.couleurClasse;
											externe.nomlong = externe_tmp.nomlong;
											externe.nom = externe_tmp.nom;
											for (int ll = 1; ll <= gr.instance; ll++)
												externe.Metrique[ll] = externe_tmp.Metrique[ll];
											externe.Metrique[gr.instance + 1] = externe_tmp.Metrique[gr.instance + 1];

											Cluster.ajouterSommet(externe);

											Arete arete_tmp = gr
													.getArete(ind_a);
											Arete arete = new Arete(
													externe_tmp, interne,
													externe_tmp.x, interne.x,
													arete_tmp.getCouleur(),
													arete_tmp.getNom(), cpt++);

											arete.setVal(arete_tmp.getVal());

											Cluster.ajouterArete(arete);
										}
									}
								}
							}
						}
						for (int j = 0; j < Clusters[i].size(); j++) {
							int indice = ((Integer) (Clusters[i].elementAt(j)))
									.intValue();

							for (int k = 0; k < Clusters[i].size(); k++) {
								int ind = ((Integer) (Clusters[i].elementAt(k)))
										.intValue();

								int ind_a = gr
										.areteDansGraphe(gr.getSommet(indice),
												gr.getSommet(ind));
								if (!gr.getSommet(indice).equals(
										gr.getSommet(ind))
										&& ind_a != -1) {

									Arete arete = new Arete(
											gr.getSommet(indice),
											gr.getSommet(ind),
											gr.getSommet(indice).x,
											gr.getSommet(ind).x, gr.getArete(
													ind_a).getCouleur(), gr
													.getArete(ind_a).getNom(),
											Cluster.aretes.size() + 1);

									arete.setVal(gr.getArete(ind_a).getVal());
									for (int ll = 1; ll <= gr.instance; ll++)
										arete.setValInstance(gr.getArete(ind_a)
												.getValInstance(ll), ll);

									Cluster.ajouterArete(arete);
								}
							}
						}
					}
					Cluster.clustering = gr.clustering;
					Cluster.TrierAretes();
					GraphPanel Clone = new GraphPanel(Cluster, true);
					Clone.gr = Cluster;
					Clone.Items = new String[Cluster.nombreSommets()];
					Clone.nomslongs = nomslongs;
					for (int j = 0; j < Cluster.nombreSommets(); j++) {
						Sommet s = Cluster.getSommet(j);
						if (s.nomlong != null)
							Clone.Items[j] = s.nomlong;
						else
							Clone.Items[j] = s.nom;
					}
					triRapide(Clone.Items, 0, Cluster.nombreSommets() - 1);

					Cluster.maximumArete();
					Cluster.maxMetrique1 = gr.maxMetrique1;
					Cluster.maxMetrique2 = gr.maxMetrique2;
					Clone.init(gr.instance);

					Cluster.typeMat = gr.typeMat;
					Cluster.sans = gr.sans;
					Cluster.longs = gr.longs;
					Cluster.cercle = gr.cercle;
					Cluster.instance = gr.instance;
					/*
					 * JFrame f = new JFrame ("changement d'instance"); if
					 * (Cluster.nombreSommets()<2)
					 * JOptionPane.showMessageDialog(f,
					 * "Ce cluster est composé d'un seul sommet");
					 */

					Clone.frame.setTitle("VisuGraph : Cluster " + i);
					Clone.frame.setVisible(true);
					Clone.frame.setLocation(185, 50);
					if (couleur_fond == 0) {
						Clone.setBackground(Color.white);
						Cluster.SetCouleursAretes(ConversionIntensite(control.slider3.getValue()),0);
					} else
						Clone.frame.setBackground(Color.black);

				}
			} else if (e.getButton() == 3 && connexe) {
				// Button 3 : selection
				ptDrag = new Point(e.getX(), e.getY());
				selection = cnx.hitTest(ptDrag, true);

				if (selection != null) {
					selection.marked = true;
					gr.getVoisins(selection, false, cnx.seuil);
					for (int i = 0; i < selection.voisins.size(); i++) {
						Sommet s = (Sommet) selection.voisins.elementAt(i);
						cnx.ajouterSommet(s);
						s.setVisible();

						int indice = gr.areteDansGraphe(selection, s);
						if (indice != -1) {
							Arete a = gr.getArete(indice);
							if (!cnx.aretes.contains(a)) {
								cnx.ajouterArete(a);
								if (a.getVal() >= cnx.seuil)
									a.setVisible();
								else
									a.setInvisible();
							}
						}
					}
					for (int i = 0; i < cnx.nombreSommets(); i++) {
						Sommet si = cnx.getSommet(i);
						for (int j = 0; j < cnx.nombreSommets(); j++) {
							Sommet sj = cnx.getSommet(j);
							if (i != j && si.getVisible() && sj.getVisible()) {
								int indice = gr.areteDansGraphe(si, sj);
								if (indice != -1) {
									Arete a = gr.getArete(indice);
									cnx.ajouterArete(a);
									if (a.getVal() >= cnx.seuil)
										a.setVisible();
									else
										a.setInvisible();
								}
							}
						}
					}
					cnx.initDegre();
					cnx.maximumMetrique();
					cnx.maximumArete();
					cnx.TrierAretes();
					if (cnx.nombreSommets() == gr.nombreSommets()) {
						Toolkit.getDefaultToolkit().beep();
						Toolkit.getDefaultToolkit().beep();
					}
				} else
					Toolkit.getDefaultToolkit().beep();
			} else if (e.getButton() == 2 && connexe) {
				// Button 2 : de-selection
				ptDrag = new Point(e.getX(), e.getY());
				selection = cnx.hitTest(ptDrag, true);

				if (selection != null) {
					cnx.getVoisins(selection, false, cnx.seuil);
					selection.marked = false;
					cnx.supprimerSommet(cnx.indiceSommet(selection));
					cnx.initDegre();
					for (int i = 0; i < selection.voisins.size(); i++) {
						Sommet s = (Sommet) selection.voisins.elementAt(i);
						int indice = cnx.indiceSommet(s);
						s.marked = false;
						cnx.getVoisins(cnx.getSommet(indice), false, cnx.seuil);

						if (s.voisins.size() == 0)
							cnx.supprimerSommet(indice);
						s.niveau = 100;
					}
					cnx.initDegre();
					cnx.maximumMetrique();
					cnx.maximumArete();
					cnx.TrierAretes();
				}
			}
			repaint();
		} catch (Exception n) {
		}
	}

	public void calculeTransit(Graphe gg, Sommet S_init) {
		/** positionnement des voisins de premier degré **/
		int cpt = 0;
		gr.getVoisins(S_init, false, gr.seuil);
		for (int i = 0; i < S_init.voisins.size(); i++) {
			Sommet sv = (Sommet) S_init.voisins.elementAt(i);
			if (sv.num == 0) {
				cpt = S_init.num;
				gr.getVoisins(sv, false, gr.seuil);
				for (int j = 0; j < sv.voisins.size(); j++) {
					Sommet t = (Sommet) sv.voisins.elementAt(j);
					if ((t.num < cpt) && (t.num != 0))
						cpt = t.num;
				}
				sv.num = cpt + 1;
				if (sv.num > init_max)
					init_max = sv.num;
				if (sv.voisins.size() > 1)
					calculeTransit(gr, sv);
			}
		}
	}

	public void voisin(Sommet s, Graphe g) {
		g.getVoisins(s, false, g.seuil);
		for (int j = 0; j < s.voisins.size(); j++) {
			Sommet t = (Sommet) s.voisins.elementAt(j);
			t.num = s.num + 1;
		}
	}

	public void transit(int val, Graphe g) {
		/* on étudie la transitivité par rapport au sommet selectionné */
		for (int i = 0; i < g.nombreSommets(); i++) {
			Sommet s = g.getSommet(i);
			if ((s.num <= val) && (s.num > 0) && (!s.nom.contains("virtuel"))
					&& (!s.nom.contains("Repere"))) {
				s.setVisible();
				s.setCouleur(new Color(255, 0 , 0));
			} else{
				//s.setInvisible();
				s.setCouleur(new Color(128, 128 , 100));
			}
		}
		for (int i = 0; i < g.nombreAretes(); i++) {
			Arete a = g.getArete(i);
			//a.setInvisible();
			a.setCouleur(new Color(128,128,128));
		}
		for (int i = 0; i < g.nombreAretes(); i++) {
			Arete a = g.getArete(i);
			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();
			if (s1.getVisible() && s2.getVisible())
				a.setVisible();
		}
		if (repmorph == 1) {
			for (int i = 0; i < g.nombreSommets(); i++) {
				Sommet s = g.getSommet(i);
				if ((s.nom.contains("virtuel")) | (s.nom.contains("Repere")))
					s.setVisible();
			}
		}
	}

	public void zoomer(int coef) {
		centrerGraphe(gr);
	}

	/**
	 * *************************************************************************
	 * ***********************************************************************
	 */

	public void keyReleased(KeyEvent e) {
		progress = 0;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_ENTER:
			if (progress == 1)
				progress = 0;
			else
				progress = 1;
		}
	}

	// public void Fisheye_Affiche(Graphe g, String nom){
	// dragging = true;
	// Graphics2D gd = (Graphics2D)this.getGraphics();
	// final JPopupMenu pop = new JPopupMenu ("Sommet selectionné : "+nom);
	// pop.add("Sommet selectionné : "+nom);
	//
	// pop.add ("Voisins directs : ");
	// for (int i = 0; i < g.nombreSommets(); i++){
	// Sommet s = g.getSommet(i);
	// if (s.nom.equals(nom)) {selection = s;}
	//
	// }
	// // // on affiche pour la sommet selectionnée les centralités qui lui
	// correspondent
	// if(!g.getOriente()){
	// pop.add("degré du sommet" + selection.getCentralite_degre());
	// pop.add("degré du sommet" + selection.getCentralite_poids());
	// pop.add("degré du sommet" + selection.getCentralite_combine());
	// }
	// /* on récupère les voisins du sommet en cours */
	// g.getVoisins( selection, true, g.seuil );
	// /* On affiche chaque voisin du sommet selectionné */
	// for ( int j = 0; j < selection.voisins.size(); j++ ) {
	// Sommet v = (Sommet)selection.voisins.elementAt(j);
	// if ( !selection.equals(v) && v.getVisible() ) {
	// pop.add("\n        "+v.getNom());
	// String d = v.getNom();
	// gd.setColor(Color.cyan);
	// gd.setFont(new Font("Time", Font.PLAIN, 15));
	// gd.drawString(d,v.getX(), v.getY());
	// }
	//
	// }
	// //g.AugmenteAttirance(selection);
	// // gd.drawString(selection.nom,selection.x,selection.y);
	//
	//
	// }

	public void ExtractionCluster(int i) {
		Cluster = new Graphe(false);
		int cpt = 0;
		for (int j = 0; j < Clusters[i].size(); j++) {
			int indice = ((Integer) (Clusters[i].elementAt(j))).intValue();

			Sommet interne = new Sommet(gr.getSommet(indice).x,
					gr.getSommet(indice).y, gr.getSommet(indice).z,
					gr.getSommet(indice).couleur, gr.getSommet(indice).nom, 0,
					0, 0);

			interne.type = gr.getSommet(indice).type;
			interne.metrique = gr.getSommet(indice).metrique;
			interne.couleur = gr.getSommet(indice).couleur;
			interne.couleurClasse = gr.getSommet(indice).couleurClasse;
			interne.nomlong = gr.getSommet(indice).nomlong;
			interne.nom = gr.getSommet(indice).nom;

			Cluster.ajouterSommet(interne);

			for (int k = 0; k < nbClusters; k++) {
				if (k != i) {
					for (int l = 0; l < Clusters[k].size(); l++) {
						int indiceExterne = ((Integer) (Clusters[k]
								.elementAt(l))).intValue();

						Sommet externe_tmp = gr.getSommet(indiceExterne);
						int ind_a = gr.areteDansGraphe(externe_tmp, interne);

						if (!gr.getSommet(indice).equals(externe_tmp)
								&& ind_a != -1) {
							Sommet externe = new Sommet(externe_tmp.x,
									externe_tmp.y, externe_tmp.z,
									externe_tmp.couleur, externe_tmp.nom, 0, 0,
									0);

							externe.type = externe_tmp.type;
							externe.metrique = externe_tmp.metrique;
							externe.couleur = externe_tmp.couleur;
							externe.couleurClasse = externe_tmp.couleurClasse;
							externe.nomlong = externe_tmp.nomlong;
							externe.nom = externe_tmp.nom;

							Cluster.ajouterSommet(externe);
							Arete arete_tmp = gr.getArete(ind_a);
							Arete arete = new Arete(externe_tmp, interne,
									externe_tmp.x, interne.x,
									arete_tmp.getCouleur(), arete_tmp.getNom(),
									cpt++);

							arete.setVal(arete_tmp.getVal());
							Cluster.ajouterArete(arete);
						}
					}
				}
			}
		}
		for (int j = 0; j < gr.aretes.size(); j++) {
			Arete a = gr.getArete(j);
			Sommet e1 = a.getE1();
			Sommet e2 = a.getE2();
			if (e1.num_cluster == e2.num_cluster && e1.num_cluster == i) {
				Arete intra_arete = new Arete(e1, e2, e1.x, e2.x, null,
						a.getNom(), Cluster.aretes.size() + 1);
				intra_arete.setVal(a.getVal());
				Cluster.aretes.addElement(intra_arete);
			}
		}
		Cluster.TrierAretes();
		GraphPanel Clone = new GraphPanel(Cluster, true);
		Clone.gr = Cluster;
		Clone.Items = new String[Cluster.nombreSommets()];
		Clone.nomslongs = nomslongs;
		for (int j = 0; j < Cluster.nombreSommets(); j++) {
			Sommet s = Cluster.getSommet(j);
			if (s.nomlong != null)
				Clone.Items[j] = s.nomlong;
			else
				Clone.Items[j] = s.nom;
		}
		triRapide(Clone.Items, 0, Cluster.nombreSommets() - 1);

		Cluster.maximumArete();
		Cluster.maxMetrique1 = gr.maxMetrique1;
		Cluster.maxMetrique2 = gr.maxMetrique2;
		Clone.init(1);

		Cluster.typeMat = this.gr.typeMat;
		Cluster.sans = gr.sans;
		Cluster.longs = gr.longs;
		Cluster.cercle = gr.cercle;
		Clone.circulaire = true;
		Clone.circulaire(Cluster);
		Clone.frame.setTitle("VisuGraph : Cluster " + i);
		Clone.frame.setVisible(true);
		Clone.frame.setLocation(180, 50);
		if (couleur_fond == 0) {
			Clone.frame.setBackground(Color.white);
			Cluster.SetCouleursAretes(
					ConversionIntensite(control.slider3.getValue()), 0);
		} else
			Clone.frame.setBackground(Color.black);
		repaint();
		ChangeGraphe = true;

	}

	public void mouseEntered(MouseEvent e) {
		if (contenu != null && contenu.isVisible()) {
			Object src = e.getSource();

			if (src == fisheye) {
				fisheye.menuSelectionChanged(true);
				fisheye.setVisible(true);
			} else
				fisheye.menuSelectionChanged(false);
		}
	}

	public void mouseExited(MouseEvent e) {
		// System.out.println ("mouseExited");
	}

	/*
	 * fonction appliquée pour 1 clic gauche de souris (affichage du nom du
	 * sommet)
	 */
	public void mousePressed(MouseEvent e) {

		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0 || !editable)
			return;
		ptDrag = new Point(e.getX(), e.getY());
		if (morphing != null)
			selection = morphing.hitTest(ptDrag, true);
		if (!mst1 && !mst2 && !mcl)
			selection = gr.hitTest(ptDrag, true);
		if (mst1 && !mst2 && !mcl && lArbre1 != null)
			selection = lArbre1.hitTest(ptDrag, true);
		if (!mst1 && mst2 && !mcl && lArbre2 != null)
			selection = lArbre2.hitTest(ptDrag, true);
		if (!mst1 && !mst2 && mcl) {
			selection = mg.hitTest(ptDrag, true);
			flag = true;
		}
		if (selection != null) {
			dragging = true;
			Graphics2D g = (Graphics2D) this.getGraphics();
		}
	}

	public void mouseReleased(MouseEvent e) {
		/* e.getModifiers(); */
		try {
			FileReader fr = new FileReader("nom");
			int i = 0;
			String l = "";
			while (i != -1) {
				i = fr.read();
				char c = (char) i;
				l += c;
			}
			if (l.length() > 1) {
				l = l.substring(0, l.length() - 1);
				if (gr.typeMat >= 2 && control.slider5.getValue() >= 1) {
					gr = morphing;
				}
				for (int j = 0; j < this.gr.nombreSommets(); j++) {
					Sommet S_init = this.gr.getSommet(j);
					if (!S_init.nomlong.equals(l)) {
						S_init.setInvisible();
						for (int k = 0; k < this.gr.nombreAretes(); k++) {
							Arete a = this.gr.getArete(k);
							a.setInvisible();
							repaint();
						}
					}
					if (repmorph == 1) {
						for (int k = 0; k < gr.nombreSommets(); k++) {
							Sommet s = gr.getSommet(k);
							if ((s.nom.contains("virtuel"))
									| (s.nom.contains("Repere")))
								s.setVisible();
						}
					}
					if (S_init.nomlong.equals(l)) {
						S_init.setVisible();

						for (int k = 0; k < this.gr.nombreSommets(); k++) {
							Sommet s = this.gr.getSommet(k);
							s.num = 0;
						}
						S_init.num = 1;
						Graphe gg = new Graphe(true);

						for (int z = 0; z < this.gr.nombreAretes(); z++) {
							Arete a = this.gr.getArete(z);
							Sommet s1 = a.getE1();
							Sommet s2 = a.getE2();
							if (!s1.nom.contains("virtuel")
									&& (!s2.nom.contains("virtuel")))
								gg.ajouterArete(a);
						}
						this.frame.setTitle("Transitivite de : " + S_init.nom);
						calculeTransit(gg, S_init);

						control.slider11.setValue(0);
						init_max = 0;
						for (int q = 0; q < this.gr.nombreSommets(); q++) {
							Sommet s = this.gr.getSommet(q);
						}
						for (int t = 0; t < gg.nombreAretes(); t++) {
							Arete a = gg.getArete(t);
							gr.ajouterArete(a);
						}
					}
					// else s.setInvisible();
				}
			}
			FileWriter classer = new FileWriter("nom");
			classer.write("");
			classer.close();
		} catch (IOException en) {
		}

		ChangeGraphe = true;
		Dimension d = getSize();
		if (contenu != null && contenu.isVisible()) {
			fisheye.fishEyeWindow.setVisible(false);
			contenu.setVisible(false);
		}

		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) == 0 || !dragging)
			return;

		dragging = false;
		ptDrag = new Point(e.getX(), e.getY());

		while (gr.hitTest(ptDrag, false) != null) {
			ptDrag.x += Sommet.RAYON;
			ptDrag.y += Sommet.RAYON;
		}
		selection.deplacer(e.getX(), e.getY(), d.width, d.height);

		ptDrag = null;
		repaint();
		e.consume();
	}

	class ChoixTypeInstance implements ActionListener {
		/*
		 * pour savoir si le masquage est coché, on crée un objet menu pour lire
		 * le menu masquage
		 */
		public void actionPerformed(ActionEvent e) {
			ChangeGraphe = true;
			/*
			 * on indique les nouvelles valeurs du slider des instances (on
			 * l'augmente si on a cliqué sur avance et on le diminue si on a
			 * cliqué sur recule). currentInstance sera utilisée dans la
			 * fonction animeMorphing pour déplacer le curseur du slider.
			 */
			if (e.getSource() == control.avance
					&& control.slider5.getValue() < control.slider5
							.getMaximum()) {
				go2 = 1;
				pos2 = 1;
				currentInstance = control.slider5.getValue() + 1;
				lastInstance = currentInstance - 1;
				start3();
				pv = false;
			} // pos2 indique que c'est avance qui a été cliquée

			if (e.getSource() == control.recule) {
				go2 = 1;
				pos2 = 2;
				currentInstance = control.slider5.getValue() - 1;
				lastInstance = currentInstance + 1;
				start3();
				pv = false;
			} // pos2 indique que c'est recule qui a été cliquée
			if (e.getSource() == control.compteur) {
				pos2 = 1;
				currentInstance = control.slider5.getValue() + 1;
				start3();
				go = 1;
				// if (force3) {stop
				;// pause(500);System.out.println("on est la");pos2=0;stop2();
					// }
				/*
				 * pos2=0; stop2();
				 */
			}

		}

	}

	/**
	 * **************************************************** gestion du
	 * changement d'instance
	 * **********************************************************
	 */

	class ChoixInstance implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int k = 0;
			while (k != 2) {
				if (go != 1 && go2 != 1) {
					/** si on n'a pas cliqué sur la morphing video **/
					/*
					 * Si on est dans le cas de l'étude de transitivité, on
					 * bloque les boutons, de facon à n'appliquer la
					 * transitivite que sur le graphe global et non pas par
					 * instance
					 */
					control.compteur.setText(String.valueOf(1));
					pv = false;
					etape = 0;
					// Morphing de graphe par paramètre - echelle
					// Actions effectuées lorsqu'on change d'instance par le
					// biais de l'échelle
					// graduée(slider)
					if (!morphingActif) {
						lastInstance = currentInstance;
						int instance = control.slider5.getValue();
						currentInstance = instance;
						morphing = new Morphing(false);
						// morphing.typeGraphe=gr.typeGraphe;
						// morphing.typeMat=gr.typeMat;
						// morphing.masque = gr.masque ;
						morphing.intensite = ConversionIntensite(control.slider3
								.getValue());
						morphing.clustering = gr.clustering;
						morphing.seuil = control.slider1.getValue();
						for (int i = 0; i < gr.nombreSommets(); i++) {
							ChangeGraphe = true;
							Sommet s = gr.getSommet(i);
							morphing.ajouterSommet(s);
							s.setVisible();
							/*
							 * Si notre sommet est de valeur inférieure à celle
							 * du seuil, pour un seuil fixé par l'utilisateur et
							 * pour toute instance alors le sommet est
							 * invisible.
							 */
							if (s.metrique <= 0.0 && instance >= 1) {
								s.setInvisible();
							}
							if (s.metrique <= morphing.seuil
									&& morphing.seuil > 1 && instance >= 1) {
								s.setInvisible();
							}

						}
						morphing.typeGraphe = 1;
						morphing.pre_instance = 0;
						for (int i = 0; i < gr.nombreAretes(); i++) {
							ChangeGraphe = true;
							Arete a = gr.getArete(i);
							double valeur = 0.0;
							/*
							 * Dans le cas où nous revenons au graphe initial,
							 * pour appliquer le filtrage, il nous faut prendre
							 * en compte toutes les aretes du graphe (de toutes
							 * les instances), d'où le recours à un getVal(),
							 * plutôt qu'à un getValInstance(instance).
							 */
							if (instance == 0 && lastInstance == 1) {
								valeur = a.getVal();
								if (valeur < control.slider1.getValue())
									a.setInvisible();
							} else
								valeur = a.getValInstance(instance);
							Sommet s1 = a.getE1();
							Sommet s2 = a.getE2();
							s1.type = a.getE1().type;
							s2.type = a.getE2().type;

							/*
							 * Dans le cas où l'arête a une valeur, on se
							 * demande si on doit l'afficher ou non
							 */
							if (valeur != 0) { // && (valeur >=
												// control.slider1.getValue()))
												// {
								Arete ar = new Arete(s1, s2);
								ar.setVal((int) valeur);
								ar.setNom(String.valueOf((int) valeur));
								ar.setVisible();
								morphing.ajouterArete(ar);
								/*
								 * Si la valeur de l'arête est inférieure au
								 * seuil, on l'efface. on recherche dans tous
								 * les cas la valeur du sommet lui correspondant
								 * et, de la même facon, si la valeur du sommet
								 * est supérieure à la valeur du seuil, on
								 * affiche.
								 */
								if (valeur < morphing.seuil)
									ar.setInvisible();
								s1.metrique = a.getE1().Metrique[instance];
								s2.metrique = a.getE2().Metrique[instance];
								if (s1.metrique >= control.slider1.getValue())
									s1.setVisible();
								if (s2.metrique >= control.slider1.getValue())
									s2.setVisible();

								if (s1.nom.contains("virtuel")
										| s1.nom.contains("Repere")) {
									ar.setInvisible();
								}
								if (s2.nom.contains("virtuel")
										| s2.nom.contains("Repere")) {
									ar.setInvisible();
								}
							} else { /*
									 * Si l'arête n'a pas de valeur, on ne se
									 * pose pas de questions la concernant, on
									 * ne l'affiche pas. On s'interroge juste
									 * pour les sommets (à afficher ou non).
									 */
								s1.metrique = a.getE1().Metrique[instance];
								s2.metrique = a.getE2().Metrique[instance];
								if (s1.metrique >= control.slider1.getValue())
									s1.setVisible();
								if (s2.metrique >= control.slider1.getValue())
									s2.setVisible();
							}
							/*
							 * La première instance étant un cumul des autres
							 * instances, elle n'est pas concernée par la notion
							 * de morphing. par contre toutes les autres le sont
							 * (d'où le instance >=1)
							 */
							if (instance >= 1) {
								morphing.ajouterSommet(s1);
								morphing.ajouterSommet(s2);
							}
						}

						if (control.slider11.getValue() > 0) {
							transit(control.slider11.getValue(), morphing);
							if (control.slider5.getValue() > 0) {
								for (int i = 0; i < morphing.nombreSommets(); i++) {
									Sommet s = morphing.getSommet(i);
									if (s.Metrique[control.slider5.getValue()] == 0)
										s.setInvisible();
								}
							}
						}
						if (instance >= 1) {

							morphing.masque = gr.masque;
							morphing.stress = gr.stress;
							morphing.noms = gr.noms;
							morphing.longs = gr.longs;
							morphing.sans = gr.sans;
							
							morphing.rang = instance;
							// morphing.pre_instance=control.slider5.getValue();
							morphing.maxMetrique1 = gr.maxMetrique1 / 2;
							morphing.maxMetrique2 = gr.maxMetrique2 / 2;
							morphing.maxArete = gr.maxArete;
							morphing.minArete = gr.minArete;
							// if (instance==1 && lastInstance <instance)
							// gr.cercle=2;
							morphing.cercle = gr.cercle;

							repaint();
							morphing.TrierAretes();
						} else
							/*
							 * on se trouve dans le cas ou l'instance est à 0 et
							 * n'est donc pas concernée par le morphing, d'où le
							 * morphing=null.
							 */
							morphing = null;

						/*
						 * Gestion des boutons avance, recule, -> et <-. Gestion
						 * de leur activation et desactivation
						 */
						if (instance == control.slider5.getMaximum()) {
							control.avance.setEnabled(false);
						} else {
							control.avance.setEnabled(true);
						}
						if (instance == 1) {
							control.recule.setEnabled(false);
						}
						if (currentInstance == 1) {
							control.recule.setEnabled(false);
						} else {
							control.recule.setEnabled(true);
						}

						masquage();// Efface();
					}

					etape = 10;
				}
				if (go == 1 | go2 == 1) {
					etape = 8;
				}
				k += 1;
			}
		}

		public void masquage() {
			/*
			 * On gère le cas où on a coché la case "masquage". Dans ce cas la,
			 * on est sur true (cf ActionPerformed(actionEvent e) définie plus
			 * bas). "On" a été activé par un clic sur masquage dans le menu.
			 * Dans notre cas on change d'instance mais le morphing doit
			 * s'adapter à ce changement. on va donc procéder de la manière
			 * suivante : si on a coché masquage, on va agir comme si on le
			 * décochait (pour revenir à la situation initiale), puis on va
			 * remasquer.
			 */

			if (masq == true) {
				masq = false;
				/*
				 * j<2 car on veut passer une première fois pour demasquer
				 * (gr.masque=false), puis une seconde fois pour remasquer
				 * (gr.masque=true). On change la valeur de gr.masque en fin de
				 * boucle for(...)
				 */
				for (int j = 0; j < 2; j++) {
					gr.masque = on;
					/*
					 * Le code est fortement semblable à celui actionné en cas
					 * de (dé)coche de masquage.
					 */
					Graphe graphe = new Graphe(false);
					if (!mst1 && !mst2 && !mcl) {
						gr.masque = on;
						graphe = gr;
					}
					if (mst1 && !mst2 && !mcl) {
						lArbre1.masque = on;
						graphe = lArbre1;
					}
					if (!mst1 && mst2 && !mcl) {
						lArbre2.masque = on;
						graphe = lArbre2;
					}
					if (!mst1 && !mst2 && mcl) {
						mg.masque = on;
						graphe = mg;
					}
					if (connexe) {
						cnx.masque = on;
						graphe = cnx;
					}
					if (morphing != null) {
						morphing.masque = on;
						graphe = morphing;
					}

					/*
					 * Le filtrage ayant été modifié par le masquage (un premier
					 * masquage supprime des aretes, ce qui change donc le seuil
					 * d'affichage des aretes), on va filtrer au maximum, puis
					 * revenir à la valeur initiale, afin que notre masquage
					 * soit efficace et adapté à notre instance.
					 */
					int val0 = control.slider1.getValue();
					/*
					 * on cherche la valeur maximale possible du filtrage et on
					 * l'applique.
					 */
					int val1 = control.slider1.getMaximum();
					control.slider1.setValue(val1);
					int val2 = control.slider1.getMinimum();
					control.slider1.setValue(val2);
					/* On revient à la valeur initiale de filtrage. */
					control.slider1.setValue(val0);

					/* On applique notre (dé)masquage. */
					for (int i = 0; i < graphe.nombreSommets(); i++) {
						Sommet v = graphe.getSommet(i);
						graphe.getVoisins(v, false, graphe.seuil);

						if (graphe.degre(graphe.indiceSommet(v)) == 0
								&& v.getVisible() && on)
							v.setInvisible();

						if (graphe.degre(graphe.indiceSommet(v)) == 0 && !on)
							v.setVisible();
					}
					if (circulaire)
						circulaire(graphe);
					/*
					 * on met "on" à true, afin de pouvoir effectuer dans le
					 * second passage, un masquage.
					 */
					on = true;
				}
			}
		}
	}

	public void InitMorphing(int instance) {
		morphing.aretes = new Vector();
		int instanceMax = control.slider5.getMaximum();
		morphing.seuil = control.slider1.getValue();
		morphing.masque = gr.masque;
		morphing.intensite = ConversionIntensite(control.slider3.getValue());
		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet s = gr.getSommet(i);
			morphing.ajouterSommet(s);

		}
		for (int i = 0; i < gr.nombreAretes(); i++) {
			Arete a = gr.getArete(i);
			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();

			Arete ar = new Arete(s1, s2, s1.x, s2.x, a.getCouleur(),
					a.getValInstance(instance), i);
			ar.setNom(a.getNom());

			for (int j = 1; j <= instanceMax; j++)
				ar.setValInstance(a.getValInstance(j), j);
			morphing.ajouterArete(ar);
			ar.setInvisible();
			// if ( ar.getValInstance(instance) <= morphing.seuil )
			// ar.setInvisible();
			// else ar.setVisible();
		}
	}

	public void sommetProgressif(Sommet s, double valeur1, double valeur2,
			double t1) {
		/*
		 * Nous sommes dans le cas où on a touché au slider de filtrage. On va
		 * donc verifier que l'on affiche des sommets supérieurs où égaux à la
		 * valeur de seuil. Si le sommet est inférieur, on le rend invisible,
		 * sinon on l'affiche
		 */

		if (pos2 == 1 | pos == 2) { // on avance
			s.metrique = Math.round(s.Metrique[currentInstance - 1]
					* Math.round(valeur1) + s.Metrique[currentInstance]
					* Math.round(valeur2));
			s.metrique /= 2;
			/*
			 * Math.round(s.Metrique[currentInstance-1]*Math.round(valeur1) +
			 * s.Metrique[currentInstance]*Math.round(valeur2));
			 */

		}

		if (pos2 == 2 | pos == 1) { // on recule
			s.metrique = Math.round(s.Metrique[currentInstance + 1]
					* Math.round(valeur1) + s.Metrique[currentInstance]
					* Math.round(valeur2));
			s.metrique /= 2;
		}
		if (t1 > 0.5 | currentInstance <= 1) {
			if (s.metrique < morphing.seuil && morphing.seuil > 1)
				s.setInvisible();
			else
				s.setVisible();
			if (s.Metrique[currentInstance] < morphing.seuil)
				s.setInvisible();
		}
	}

	public void areteProgressif(Arete a, double valeur1, double valeur2,
			Sommet s1, Sommet s2, double t1) {
		if (!s1.getVisible() || !s2.getVisible())
			a.setInvisible();
		/* getValInstance retourne la valeur des aretes */
		/* Valeur des aretes de l'instance en cours */
		double d1 = a.getValInstance(currentInstance - 1);
		/*
		 * Valeur des aretes de l'instance suivante (celle qui va s'afficher)
		 */
		double d2 = a.getValInstance(currentInstance);
		/* ajout des valeurs de l'arete pré et post morphing, pondérées. */
		double d = Math.round(d1 * valeur1 + d2 * valeur2);
		if (currentInstance < lastInstance) {
			d1 = a.getValInstance(currentInstance + 1);
			d2 = a.getValInstance(currentInstance);
		}
		d = d1 * valeur1 + d2 * valeur2;
		if (t1 < 0.5) {
			if (d1 != 0 && d2 == 0 && d <= 0.01) // disparition
													// totale
			{
				a.setInvisible();
				if (morphing.masque == true) {
					/*
					 * on effectue le masquage des sommets isolés. Pour ce
					 * faire, si s1 a des voisins et qu'il est invisible, alors
					 * il devient visible. Si s1 a des voisins et qu'il est
					 * visible, il reste visible. Par contre s'il n'a pas de
					 * voisins, il disparait
					 */
					if ((morphing.aDesVoisins(s1, false, morphing.seuil))
							&& (!s1.getVisible()))
						s1.setVisible();
					else if ((morphing.aDesVoisins(s1, false, morphing.seuil) == false)
							&& (s1.getVisible()))
						s1.setInvisible();
					if ((morphing.aDesVoisins(s2, false, morphing.seuil))
							&& (!s2.getVisible()))
						s2.setVisible();
					else if ((morphing.aDesVoisins(s2, false, morphing.seuil) == false)
							&& (s2.getVisible()))
						s2.setInvisible();
				}
			} else if (d1 == 0 && d2 == 0 && a.getVisible()) {
				a.setInvisible();
				if (morphing.masque == true) {
					/*
					 * on effectue le masquage des sommets isolés. Pour ce
					 * faire, si s1 a des voisins et qu'il est invisible, alors
					 * il devient visible. Si s1 a des voisins et qu'il est
					 * visible, il reste visible. Par contre s'il n'a pas de
					 * voisins, il disparait
					 */
					if ((morphing.aDesVoisins(s1, false, morphing.seuil))
							&& (!s1.getVisible()))
						s1.setVisible();
					else if ((morphing.aDesVoisins(s1, false, morphing.seuil) == false)
							&& (s1.getVisible()))
						s1.setInvisible();
					if ((morphing.aDesVoisins(s2, false, morphing.seuil))
							&& (!s2.getVisible()))
						s2.setVisible();
					else if ((morphing.aDesVoisins(s2, false, morphing.seuil) == false)
							&& (s2.getVisible()))
						s2.setInvisible();
				}

			}

			else if (d1 != 0 && d2 == 0 && d != 0) {
				a.setVal((int) (Math.round(d)));
				a.setNom(String.valueOf((int) Math.round(d)));
				a.setVisible();
				if (morphing.masque == true) {
					if (CptEv1 > 1 && CptEv1 < 10)
						control.evolution2.setEnabled(false); /*
															 * on effectue le
															 * masquage des
															 * sommets isolés.
															 * Pour ce faire, si
															 * S1 a des voisins
															 * et qu'il est
															 * invisible, alors
															 * il devient
															 * visible.si S1 a
															 * des voisins et
															 * qu'il est
															 * visible, alors il
															 * reste visible.
															 * Par contre, s'il
															 * n'a pas de
															 * voisins, il
															 * disparait
															 */

					if (masqueSeuil == true) {
						if (((morphing.aDesVoisins(s1, false, morphing.seuil)) && (!s1
								.getVisible())))
							s1.setVisible();
						else if ((morphing.aDesVoisins(s1, false,
								morphing.seuil) == false) && (s1.getVisible()))
							s1.setInvisible();
						if ((morphing.aDesVoisins(s2, false, morphing.seuil))
								&& (!s2.getVisible()))
							s2.setVisible();
						else if ((morphing.aDesVoisins(s2, false,
								morphing.seuil) == false) && (s2.getVisible()))
							s2.setInvisible();
					}
					if ((morphing.aDesVoisins(s1, false, morphing.seuil))
							&& (!s1.getVisible()))
						s1.setVisible();
					else if ((morphing.aDesVoisins(s1, false, morphing.seuil) == false)
							&& (s1.getVisible()))
						s1.setInvisible();
					if ((morphing.aDesVoisins(s2, false, morphing.seuil))
							&& (!s2.getVisible()))
						s2.setVisible();
					else if ((morphing.aDesVoisins(s2, false, morphing.seuil) == false)
							&& (s2.getVisible()))
						s2.setInvisible();
				}

			}
		}
		if (t1 > 0.5) {
			valeur1 = (1 - t1 - 0.5);
			valeur2 = (t1 - 0.5);
			if (t1 > 0.9) {
				valeur1 = (1 - t1);
				valeur2 = (t1);
			}
			d = d1 * valeur1 + d2 * valeur2;
			// nouvelle arete apparait
			if (d1 == 0 && d2 != 0) {

				a.setVal((int) (Math.round(d / 2)));
				a.setNom(String.valueOf((int) Math.round(d)));
				if (!a.getVisible())
					a.setVisible();

				if (morphing.masque == true) {
					/*
					 * on effectueif (CptEv1 > 1 && CptEv1 < 10)
					 * control.evolution2.setEnabled(false); le masquage des
					 * sommets isolés. Pour ce faire, si s1 a des voisins et
					 * qu'il est invisible, alors il devient visible. Si s1 a
					 * des voisins et qu'il est visible, il reste visible. Par
					 * contre s'il n'a pas de voisins, il disparait
					 */
					if ((morphing.aDesVoisins(s1, false, morphing.seuil))
							&& (!s1.getVisible()))
						s1.setVisible();
					else if ((morphing.aDesVoisins(s1, false, morphing.seuil) == false)
							&& (s1.getVisible()))
						s1.setInvisible();
					if ((morphing.aDesVoisins(s2, false, morphing.seuil))
							&& (!s2.getVisible()))
						s2.setVisible();
					else if ((morphing.aDesVoisins(s2, false, morphing.seuil) == false)
							&& (s2.getVisible()))
						s2.setInvisible();
				}
			}
			// maj d'1 arete qui existe deja et evolue ou stable
			else if (d1 != d2 && d1 != 0 && d2 != 0 || d1 == d2 && d1 != 0
					&& d2 != 0) {
				a.setVal((int) (Math.round(d)));
				a.setNom(String.valueOf((int) Math.round(d)));
				if (!a.getVisible()) {
					a.setVisible();
				}
				if (morphing.masque == true) {
					/*
					 * on effectue le masquage des sommets isolés. Pour ce
					 * faire, si s1 a des voisins et qu'il est invisible, alors
					 * il devient visible. Si s1 a des voisins et qu'il est
					 * visible, il reste visible. Par contre s'il n'a pas de
					 * voisins, il disparait
					 */
					if ((morphing.aDesVoisins(s1, false, morphing.seuil))
							&& (!s1.getVisible()))
						s1.setVisible();
					else if ((morphing.aDesVoisins(s1, false, morphing.seuil) == false)
							&& (s1.getVisible()))
						s1.setInvisible();
					if ((morphing.aDesVoisins(s2, false, morphing.seuil))
							&& (!s2.getVisible()))
						s2.setVisible();
					else if ((morphing.aDesVoisins(s2, false, morphing.seuil) == false)
							&& (s2.getVisible()))
						s2.setInvisible();
				}
			}
		}
		if (s1.nom.contains("virtuel")) {
			a.setInvisible();
		}
		if (s2.nom.contains("virtuel")) {
			a.setInvisible();
		}
		/*
		 * On effectue le filtrage. Si on a des aretes en dessous de notre
		 * seuil, on les enlève
		 */
		int delta = (int) ((d / t1) + 0.5);
		int ms = (int) (morphing.seuil);
		if ((delta < ms))
			a.setInvisible();// | (d < ms)
	}

	public void AffichageProgressifMorphing(double t1) {

		double valeur1 = 0.0, valeur2 = 0.0;
		valeur1 = (double) (1 - 2 * t1);
		valeur2 = (double) (2 * t1);

		for (int i = 0; i < morphing.nombreAretes(); i++) {
			Arete a = morphing.getArete(i);
			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();
			if (s1.nom.contains("Repere") | s2.nom.contains("Repere")
					| s1.nom.contains("virtuel") | s2.nom.contains("virtuel"))
				a.setInvisible();
			else
				areteProgressif(a, valeur1, valeur2, s1, s2, t1);
		}
		for (int i = 0; i < morphing.nombreSommets(); i++) {
			Sommet s = morphing.getSommet(i);
			sommetProgressif(s, valeur1, valeur2, t1);
		}
	}

	public void AnimeMorphing() {
		// if (force_active==1) {System.out.println("stooooooooop");stop();} /**
		// si les forces sont appliquées, on les cesse car elle provoque un
		// mauvais rendu dans la video morphing **/
		if (control.slider5.getValue() == control.slider5.getMaximum())
			go = 0;

		double t1 = 0.1;
		ChangeGraphe = true;
		morphing.cercle = gr.cercle;
		if (currentInstance == 0) {
			InitMorphing(1);
		} else
			InitMorphing(control.slider5.getValue());
		/*
		 * cas ou on veut passer d'une étape à l'autre directement (on ne
		 * s'arrête pas à chaque étape : on avance ou on recule
		 */
		if (pos2 == 1 | pos2 == 2) {
			control.recule.setEnabled(true);
			etape = 0;
			/*
			 * pour les 10 étape c.a.d. jusqu'à ce que t1, incrémenté à chaque
			 * fois de 0.1, atteigne 1.0
			 */
			while (t1 < 1.0) {
				control.recule.setEnabled(false);
				control.avance.setEnabled(false);
				/* gestion de l'affichage du numéro de l'étape sur le compteur */
				// if (ActivMorph == true) t1 = valetap;
				etape += 1;
				/*
				 * si on arrive à 11 au compteur, cela veut dire qu'on a
				 * effectué toutes les étapes de l'instance et donc que nous
				 * pouvons passer à l'instance supérieure/inférieure
				 */
				control.compteur.setText(String.valueOf(etape));
				/*
				 * gestion de l'affichage progressif des sommets et aretes
				 * (fonction définie ci-avant)
				 */
				AffichageProgressifMorphing(t1);
				morphing.maxMetrique1 = gr.maxMetrique1 / 2;
				morphing.maxMetrique2 = gr.maxMetrique2 / 2;
				morphing.maxArete = gr.maxArete;
				morphing.minArete = gr.minArete;
				morphing.typeMat = gr.typeMat;
				repaint();
				t1 = t1 + 0.1;
				pause(1500 - (control.slider13.getValue() * 100));
			}
			control.slider5.setValue(currentInstance);
			control.recule.setEnabled(true);
			control.avance.setEnabled(true);
		}
		// if (force_active==1){for(int i =0; i < 1500; i++) start(); stop();}
		// morphing.TrierAretes();
		// morphingActif = true;

		/*
		 * Controle des boutons. Si on avance, le bouton pour passer à
		 * l'instance précédente ne doit pas être actif et vice-versa
		 */
		if (currentInstance == control.slider5.getMaximum() && (etape == 10)) {
			control.avance.setEnabled(false);
		}

		// if (control.slider5.getValue() == 1 && (etape == 0))
		// {control.recule.setEnabled(false);control.evolution1.setEnabled(false);}
		if (control.slider5.getValue() == 1 && (etape == 10)) {
			control.recule.setEnabled(false);
			control.avance.setEnabled(true);
		}
		// morphingActif = false;
		repaint();
		if (go == 0)
			pos2 = 0;
		if (go == 0)
			stop2();
		else {
			control.slider5.setValue(control.slider5.getValue() + 1);
			currentInstance = control.slider5.getValue();
			lastInstance = currentInstance - 1;
			pause(1500);
			morphing.rang += 1;
		}

		go2 = 0;
		if (control.slider5.getValue() == 1)
			control.recule.setEnabled(false);
	}

	/*
	 * Indique le dégradé de gris pour représenter les aretes. Notre fond étant
	 * noir, plus l'arete est important plus elle apparaitra en gris clair.
	 * Inversement, moins elle sera importante, plus sombre elle sera.
	 */
	public double ConversionIntensite(int entree) {
		if (entree == 0)
			return (0.12);
		else if (entree == 1)
			return (0.25);
		else if (entree == 2)
			return (0.5);
		else if (entree == 3)
			return (1.0);
		else if (entree == 4)
			return (2.0);
		else if (entree == 5)
			return (3.0);
		else if (entree == 6)
			return (4.0);
		else if (entree == 7)
			return (5.0);
		else if (entree == 8)
			return (6.0);
		else if (entree == 9)
			return (7.0);
		else if (entree == 10)
			return (8.0);
		else
			return (1.0);
	}
	/////         Remplacer tous les classes listeners par un seul classe 
	//listener qui réagit à tous les variaitionsdes valeurs des sliders
	class GloablListener implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			int combinaison=1;
			if(controlCentrality.slider1.isEnabled() == true) {
				degreeNumberFilter  = controlCentrality.slider1.getValue();;
			}else {
				degreeNumberFilter   = 0;
			}
			if(controlCentrality.slider2.isEnabled() == true) {
				filtreStrength  = controlCentrality.slider2.getValue();
			}else {
				filtreStrength = 0;
			}
			if(controlCentrality.slider3.isEnabled() == true) {
				filtreIntermediarity  = controlCentrality.slider3.getValue();
			}else{
				filtreIntermediarity = 0;
			}
			if(controlCentrality.slider4.isEnabled() == true) {
				filtreProximity  = controlCentrality.slider4.getValue();
			}else {
				filtreProximity = 0;
			}
		}
	}
	class NodeNumber implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			degreeNumberFilter  = controlCentrality.slider1.getValue();
			int combinaison = 1;
			for(int i =0; i< gr.nombreSommets(); i++){
                    if(gr.getSommet(i).degreeRank <= degreeNumberFilter){
                    	gr.getSommet(i).setVisible();
                    }else if(gr.getSommet(i).degreeRank >= degreeNumberFilter){
                    	gr.getSommet(i).setInvisible();
                    }	
			}
			repaint();
		}
	}

	class NodeStrength implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
	
			filtreStrength  = controlCentrality.slider2.getValue();
			for(int i =0; i< gr.sommets.size(); i++){
				
                    if(gr.getSommet(i).StrengthRank<= filtreStrength){
                    	gr.getSommet(i).setVisible();
                    }else if(gr.getSommet(i).StrengthRank >= filtreStrength){
                    	gr.getSommet(i).setInvisible();
                    }
				
				
				
			}
			repaint();
		}
	}	
	
	
	class NodeIntermediarity implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			filtreIntermediarity  = controlCentrality.slider3.getValue();
			for(int i =0; i< gr.sommets.size(); i++){
                    if(gr.getSommet(i).intermediarityRank<= filtreIntermediarity){
                    	gr.getSommet(i).setInvisible();
                    }else if(gr.getSommet(i).intermediarityRank >= filtreIntermediarity){
                    	gr.getSommet(i).setInvisible();
                    }
				
				
				
			}
			repaint();
		}
	}	
	class NodeProximity implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			filtreProximity  = controlCentrality.slider4.getValue();
			for(int i =0; i< gr.sommets.size(); i++){
				if(gr.getSommet(i).getVisible() == true){
                    if(gr.getSommet(i).proximityRank<= filtreProximity){
                    	gr.getSommet(i).setInvisible();
                    }else if(gr.getSommet(i).proximityRank >= filtreProximity){
                    	gr.getSommet(i).setInvisible();
                    }
				
				
				}
			}
			repaint();
		}
	}	
	
	//reste à ajouter les listener % mesures Gen
	
	
	public class RepartitionCouleurCentrality  implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			if (control.slider77.getValue() == 1.0){
				intensiteCouleur = 0.125;
		}else if (control.slider77.getValue() == 2.0)
			intensiteCouleur = 0.25;
			else if (control.slider77.getValue() == 3.0)
				intensiteCouleur = 0.5;
			else if (control.slider77.getValue() == 4.0)
				intensiteCouleur = 1.0;
			else if (control.slider77.getValue() == 5.0)
				intensiteCouleur = 2.0;
			else if (control.slider77.getValue() == 6.0)
				intensiteCouleur = 4.0;
			else if (control.slider77.getValue() == 7.0)
				intensiteCouleur = 8.0;
			intensiteCouleur = control.slider77.getValue();
			gr.classDegreeCentrality(gr , intensiteCouleur);
			gr.classLinkCentrality(gr, intensiteCouleur);
			gr.classGenDegreeCentrality(gr, intensiteCouleur);
		    gr.classIntermediarityCentrality(gr, intensiteCouleur);
		    gr.classGenIntermediarityCentrality(gr, intensiteCouleur);
		    gr.classProximityCentrality(gr, intensiteCouleur);
		    gr.classGenProximityCentrality(gr, intensiteCouleur);
			repaint();

		}

				
	}

	class FacteurClustering implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			double val = control.slider4.getValue();
			facteur = 1.0;
			facteur += (double) val / 10.0;
		}
	}

	class AppliForceRepuls implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			if (control.slider2.getValue() == 1) {
				t = 0.0;
			}
			if (control.slider2.getValue() == 2) {
				t = 0.010;
			}
			if (control.slider2.getValue() == 3) {
				t = 0.03;
			}
			if (control.slider2.getValue() == 4) {
				t = 0.04;
			}
			if (control.slider2.getValue() == 5) {
				t = 0.06;
			}
			if (control.slider2.getValue() == 6) {
				t = 0.08;
			}
			if (control.slider2.getValue() == 7) {
				t = 0.1;
			}
			if (control.slider2.getValue() == 8) {
				t = 0.12;
			}
			if (control.slider2.getValue() == 9) {
				t = 0.14;
			}
			if (control.slider2.getValue() == 10) {
				t = 0.16;
			}
			if (control.slider2.getValue() == 11) {
				t = 0.18;
			}
			if (control.slider2.getValue() == 12) {
				t = 0.20;
			}
			if (control.slider2.getValue() == 13) {
				t = 0.22;
			}
			if (control.slider2.getValue() == 14) {
				t = 0.24;
			}
			if (control.slider2.getValue() == 15) {
				t = 0.26;
			}
			if (control.slider2.getValue() == 16) {
				t = 0.28;
			}
			if (control.slider2.getValue() == 17) {
				t = 0.3;
			}
			if (control.slider2.getValue() == 18) {
				t = 0.32;
			}
			if (control.slider2.getValue() == 19) {
				t = 0.34;
			}
			if (control.slider2.getValue() == 20) {
				t = 0.36;
			}
			if (control.slider2.getValue() == 21) {
				t = 0.38;
			}
			if (control.slider2.getValue() == 22) {
				t = 0.4;
			}
			if (control.slider2.getValue() == 23) {
				t = 0.42;
			}
			if (control.slider2.getValue() == 24) {
				t = 0.44;
			}
			if (control.slider2.getValue() == 25) {
				t = 0.46;
			}
			if (control.slider2.getValue() == 26) {
				t = 0.48;
			}
			if (control.slider2.getValue() == 27) {
				t = 0.50;
			}
			if (control.slider2.getValue() == 28) {
				t = 0.52;
			}
			if (control.slider2.getValue() == 29) {
				t = 0.54;
			}
			if (control.slider2.getValue() == 30) {
				t = 0.56;
			}
			if (control.slider2.getValue() == 31) {
				t = 0.58;
			}
			if (control.slider2.getValue() == 32) {
				t = 0.60;
			}
			if (control.slider2.getValue() == 33) {
				t = 0.62;
			}
			if (control.slider2.getValue() == 34) {
				t = 0.64;
			}
			if (control.slider2.getValue() == 35) {
				t = 0.66;
			}
			if (control.slider2.getValue() == 36) {
				t = 0.68;
			}
			if (control.slider2.getValue() == 37) {
				t = 0.70;
			}
			if (control.slider2.getValue() == 38) {
				t = 0.72;
			}
			if (control.slider2.getValue() == 39) {
				t = 0.74;
			}
			if (control.slider2.getValue() == 40) {
				t = 0.76;
			}

			try {
				Force.time = (1 / t) * 10;
				start();
			} catch (Exception n) {
			}
		}
	}

	class IntensiteCouleurs implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider intensité, la nouvelle valeur
			 * est attribuée aux paramètres correspondants pour une mise à jour de l'intensite des liens
			 */
			double val = ConversionIntensite(control.slider3.getValue());
			if (!mst1 && !mst2 && !mcl && !connexe)
				gr.intensite = val;
			if (mst1 && !mst2 && !mcl && !connexe)
				lArbre1.intensite = val;
			if (!mst1 && mst2 && !mcl && !connexe)
				lArbre2.intensite = val;
			if (!mst1 && !mst2 && mcl && !connexe)
				mg.intensite = val;
			if (connexe)
				cnx.intensite = val;
			if (morphing != null)
				morphing.intensite = val;
			// System.out.println(" intensite = " + val );
			repaint();
		}
	}

	class KcoreVoisins implements ChangeListener {
		public void stateChanged(ChangeEvent e) {

			int seuil = control.slider10.getValue();
			if (seuil != 0)
				System.out.println("Seuil K-Core : " + seuil);
			for (int i = 0; i < gr.nombreAretes(); i++) {
				Arete a = gr.getArete(i);
				a.setInvisible();
				a.getE1().setInvisible();
				a.getE2().setInvisible();
			}
			for (int i = 0; i < gr.nombreSommets(); i++) {
				Sommet s = gr.getSommet(i);
				gr.getVoisins(s, false, gr.seuil);
				if (s.nbvoisins == 0 && seuil > 0 ) {
					s.setInvisible();
				}
				if (s.nbvoisins >= seuil || s.getNom().contains("Rep")) {

					s.setVisible();
					for (int j = 0; j < s.voisins.size(); j++) {
						Sommet s2 = (Sommet) s.voisins.elementAt(j);
						if (s2.getVisible()) {
							int indice = gr.areteDansGraphe(s, s2);
							if (indice != -1) {
								Arete a = gr.getArete(indice);
								a.setVisible();
							}
						}
					}

				}
			}
			repaint();

		}
//		public void stateChanged(ChangeEvent e) {
//
//			int seuil = control.slider10.getValue();
//			if (seuil != 0)
//				System.out.println("Seuil K-Core : " + seuil);
////			for (int i = 0; i < gr.nombreAretes(); i++) {
////				Arete a = gr.getArete(i);
////				a.setInvisible();
////				a.getE1().setInvisible();
////				a.getE2().setInvisible();
//////				a.setCouleur(new Color(20, 20, 20));
//////				a.getE1().setCouleur(new Color(128,  0, 0));
//////				a.getE2().setCouleur(new Color(128,  0, 0));
////			}
//			for (int i = 0; i < gr.nombreSommets(); i++) {
//				Sommet s = gr.getSommet(i);
//				gr.getVoisins(s, false, gr.seuil);
//				if (s.nbvoisins == 0 && seuil > 0) {
//					///s.setInvisible();
//					s.setCouleur(new Color(128, 0, 0));
//				}
//				if (s.nbvoisins >= seuil) {
//
//					s.setVisible();
//					s.setCouleur(new Color(255, 0, 0));
//					for (int j = 0; j < s.voisins.size(); j++) {
//						Sommet s2 = (Sommet) s.voisins.elementAt(j);
//						if (s2.getVisible()) {
//							int indice = gr.areteDansGraphe(s, s2);
//							if (indice != -1) {
//								Arete a = gr.getArete(indice);
//								a.setVisible();
//								a.setCouleur(new Color(128 , 128 , 128));
//							}
//						}
//					}
//
//				}else{
//                     s.setCouleur(new Color(128, 128, 20));
//				}
//			}
//			repaint();
//
//		}

	}

	class TaillePolice implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider intensité, la nouvelle valeur
			 * est attribuée aux paramètres correspondants pour une mise à jour
			 */
			if (control.slider9.getValue() == 0.0)
				taillePolice = 5;
			else if (control.slider9.getValue() == 1.0)
				taillePolice = 6;
			else if (control.slider9.getValue() == 2.0)
				taillePolice = 7;
			else if (control.slider9.getValue() == 3.0)
				taillePolice = 8;
			else if (control.slider9.getValue() == 4.0)
				taillePolice = 9;
			else if (control.slider9.getValue() == 5.0)
				taillePolice = 10;
			else if (control.slider9.getValue() == 6.0)
				taillePolice = 11;
			else if (control.slider9.getValue() == 7.0)
				taillePolice = 12;
			else if (control.slider9.getValue() == 8.0)
				taillePolice = 13;
			else if (control.slider9.getValue() == 9.0)
				taillePolice = 14;
			else if (control.slider9.getValue() == 10.0)
				taillePolice = 15;
			else if (control.slider9.getValue() == 11.0)
				taillePolice = 16;
			else if (control.slider9.getValue() == 12.0)
				taillePolice = 17;

			repaint();
		}
	}

	class IntensitePolice implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider intensité, la nouvelle valeur
			 * est attribuée aux paramètres correspondants pour une mise à jour
			 */
			if (control.slider7.getValue() == 0.0)
				IntensitePolice = 20.0;
			else if (control.slider7.getValue() == 1.0)
				IntensitePolice = 40.0;
			else if (control.slider7.getValue() == 2.0)
				IntensitePolice = 60.0;
			else if (control.slider7.getValue() == 3.0)
				IntensitePolice = 80.0;
			else if (control.slider7.getValue() == 4.0)
				IntensitePolice = 100.0;
			else if (control.slider7.getValue() == 5.0)
				IntensitePolice = 120.0;
			else if (control.slider7.getValue() == 6.0)
				IntensitePolice = 140.0;
			else if (control.slider7.getValue() == 7.0)
				IntensitePolice = 160.0;
			else if (control.slider7.getValue() == 8.0)
				IntensitePolice = 180.0;
			else if (control.slider7.getValue() == 9.0)
				IntensitePolice = 200.0;
			else if (control.slider7.getValue() == 10.0)
				IntensitePolice = 220.0;
			else if (control.slider7.getValue() == 11.0)
				IntensitePolice = 240.0;
			else if (control.slider7.getValue() == 12.0)
				IntensitePolice = 255.0;

			repaint();
		}
	}

	class DistanceRepulsion implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider intensité, la nouvelle valeur
			 * est attribuée aux paramètres correspondants pour une mise à jour
			 */
			// kRep=control.slider6.getValue()/4;
			if (control.slider6.getValue() == 0.0)
				kRep = 0.0;
			if (control.slider6.getValue() == 1.0)
				kRep = 0.3;
			if (control.slider6.getValue() == 2.0)
				kRep = 0.6;
			if (control.slider6.getValue() == 3.0)
				kRep = 0.9;
			if (control.slider6.getValue() == 4.0)
				kRep = 1.2;
			if (control.slider6.getValue() == 5.0)
				kRep = 1.5;
			if (control.slider6.getValue() == 6.0)
				kRep = 1.8;
			if (control.slider6.getValue() == 7.0)
				kRep = 2.1;
			if (control.slider6.getValue() == 8.0)
				kRep = 2.4;
			if (control.slider6.getValue() == 9.0)
				kRep = 2.7;
			if (control.slider6.getValue() == 10.0)
				kRep = 3.0;

			repaint();
		}
	}

	class nouveauSeuilTransit implements ChangeListener {                  // Transitivite
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider transitivité, les voisins de
			 * degré "valeur du slider" apparaissent
			 */

			if (control.slider11.getValue() == 0) {
				transit(control.slider11.getValue() + 1, gr);
			} else
				transit(control.slider11.getValue(), gr);
			if (control.slider11.getValue() > (init_max + 1) && init_max != 0) {
				System.out.println("Maximum de transitivité atteint");
				control.slider11.setValue(init_max);
			}

			repaint();
		}
	}

	class DistanceAttraction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			/*
			 * Quand on change la valeur du slider intensité, la nouvelle valeur
			 * est attribuée aux paramètres correspondants pour une mise à jour
			 */
			if (control.slider8.getValue() == 0.0)
				DistAttrac = 1000;
			else if (control.slider8.getValue() == 1.0)
				DistAttrac = 950;
			else if (control.slider8.getValue() == 2.0)
				DistAttrac = 900;
			else if (control.slider8.getValue() == 3.0)
				DistAttrac = 850;
			else if (control.slider8.getValue() == 4.0)
				DistAttrac = 800;
			else if (control.slider8.getValue() == 5.0)
				DistAttrac = 750;
			else if (control.slider8.getValue() == 6.0)
				DistAttrac = 700;
			else if (control.slider8.getValue() == 7.0)
				DistAttrac = 650;
			else if (control.slider8.getValue() == 8.0)
				DistAttrac = 600;
			else if (control.slider8.getValue() == 9.0)
				DistAttrac = 550;
			else if (control.slider8.getValue() == 10.0)
				DistAttrac = 500;
			else if (control.slider8.getValue() == 11.0)
				DistAttrac = 450;
			else if (control.slider8.getValue() == 12.0)
				DistAttrac = 400;
			else if (control.slider8.getValue() == 13.0)
				DistAttrac = 350;
			else if (control.slider8.getValue() == 14.0)
				DistAttrac = 300;
			else if (control.slider8.getValue() == 15.0)
				DistAttrac = 250;
			else if (control.slider8.getValue() == 16.0)
				DistAttrac = 200;
			else if (control.slider8.getValue() == 17.0)
				DistAttrac = 150;
			else if (control.slider8.getValue() == 18.0)
				DistAttrac = 100;
			else if (control.slider8.getValue() == 19.0)
				DistAttrac = 50;
			else if (control.slider8.getValue() == 20.0)
				DistAttrac = 10;
			repaint();
		}
	}

	class nouveauSeuil implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			double val = control.slider1.getValue();
			Graphe graphe = new Graphe(false);

			NbSommetsVisibles = 0;
			if (force3)
				stop();
			if (!mst1 && !mst2 && !mcl) {
				graphe = gr;
				gr.seuil = val;
			}
			if (mst1 && !mst2 && !mcl) {
				graphe = lArbre1;
				lArbre1.seuil = val;
			}
			if (!mst1 && mst2 && !mcl) {
				graphe = lArbre2;
				lArbre2.seuil = val;
			}
			if (!mst1 && !mst2 && mcl) {
				graphe = mg;
				mg.seuil = val;
			}
			if (connexe) {
				graphe = cnx;
				cnx.seuil = val;
			}
			if (morphing != null) {
				graphe = morphing;
				morphing.seuil = val;
			}

			NbSommetsVisibles = graphe.nombreSommets();

			for (int j = 0; j < graphe.aretes.size(); j++) {
				Arete a = graphe.getArete(j);
				Sommet s1 = a.getE1();
				Sommet s2 = a.getE2();

				if (a.getVal() >= val && !a.getVisible()
						&& (!s1.nom.contains("Repere"))) {
					a.setVisible();
					s1.setVisible();
					s2.setVisible();
					NbSommetsVisibles += 2;
				}
				if (a.getVal() < val && a.getVisible()) {

					a.setInvisible();

					if (graphe.degre(graphe.indiceSommet(s1)) == 0
							&& graphe.masque) {
						s1.setInvisible();
						if (graphe.connexe)

							NbSommetsVisibles -= 1;
					}
					if (graphe.degre(graphe.indiceSommet(s2)) == 0
							&& graphe.masque) {
						s2.setInvisible();
						NbSommetsVisibles -= 1;
					}
				}
			}
			if (force3)
				start();
			repaint();

		}
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
	}

	public void itemStateChanged(ItemEvent e) {
		repaint();
	}

	public void menuSelected(MenuEvent e) {
	}

	public void menuDeselected(MenuEvent e) {
	}

	public void menuCanceled(MenuEvent e) {
	}

	/**
	 * ****************************************************THREAD***************
	 * ************************************************
	 */

	/* Méthodes qui vont appeler créer puis activer les threads */
	
	
	

	
	public void start() {
		relaxer = new Thread(this);
		relaxer.start();
	}

	public void start2() {
		morph = new Thread(this);
		morph.start();
	}

	public void start3() {
		morphev = new Thread(this);
		morphev.start();
	}

	public void start4() {
		dort = new Thread(this);
		dort.start();
	}

	/* Méthodes qui arreteront le thread */
	public void stop() {
		relaxer = null;
	}

	public void stop2() {
		morph = null;
	}

	/*
	 * Méthode run() appelée par les threads. Une fois le thread construit dans
	 * un start(), cette dernière méthode appelle la fonction run. Selon le
	 * thread concu, un traitement spécifique est appliqué
	 */
	public void run() {

		Thread anime = Thread.currentThread();
		

		while (anime == relaxer) {
			if (force3) {
				if (morphing != null) {
					force_directed_placement(morphing, t);
				} else if (!mst1 && !mst2 && !mcl && !connexe) {
					force_directed_placement(gr, t);
				} else if (mst1 && !mst2 && !mcl && !connexe) {
					force_directed_placement(lArbre1, t);
				} else if (!mst1 && mst2 && !mcl && !connexe) {
					force_directed_placement(lArbre2, t);
				} else if (connexe) {
					force_directed_placement(cnx, t);
				} else if (!mst1 && !mst2 && mcl && !connexe) {
					force_directed_placement(mg, t);
				}
			}
			pause(200);
			repaint();
		}
		while (anime == morph) {
			AnimeMorphing();

		}
		if (anime == morphev) { /*
								 * Si notre thread est morphev, on applique un
								 * morphing entre deux instance (l'actuelle et
								 * le suivante/précédente)
								 */
			morphing = new Morphing(false);
			morphing.typeGraphe = 1;
			morphing.rang = currentInstance;
			morphing.pre_instance = control.slider5.getValue();
			morphing.stress = gr.stress;
			morphing.noms = gr.noms;
			morphing.cercle = 2;
			morphing.longs = gr.longs;
			morphing.sans = gr.sans;
			morphing.clustering = gr.clustering;
			morphing.seuil = control.slider1.getValue();
			morphing.intensite = ConversionIntensite(control.slider3.getValue());

			/*
			 * Le start2() va mettre en place le principe de threads. Ces
			 * derniers permettent d'effectuer des taches en parallèle, à partir
			 * d'une attribution de valeur au booléen correspondant. Start2() va
			 * armer le booleen "morph", qui fera appel à la fonction
			 * animeMorphing(),laquelle appliquera le morphing.
			 */
			start2(); /*
					 * on renvoir au cas de animeMorphing (application du
					 * morphing)
					 */

		}
	}

	public void pause(int milliseconds) { /*
										 * Méthode pour interrompre durant
										 * milliseconds le traitement
										 */
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			System.out.println(" Interrupted Exception ");
		}
	}

	public double ValLien(Sommet s1, Sommet s2) {
		for (int i = 0; i < gr.nombreAretes(); i++) {
			Arete a = gr.getArete(i);
			Sommet g1 = a.getE1();
			Sommet g2 = a.getE2();
			if (g1.nom == s1.nom | g1.nom == s2.nom) {
				if (g2.nom == s1.nom | g2.nom == s2.nom) {
					return a.getVal();
				}
			}
		}
		return 0.0;
	}

	public void evite_superpose(int[][] tableau, Graphe g) {
		this.frame.setLocation(185, 50);
		for (int i = 0; i < g.nombreSommets(); i++) {
			Sommet s = g.getSommet(i);
			if (s.nom.contains("virtuel") | s.nom.contains("Repere")) {
				try {
					tableau[s.x][s.y] = 1;

				} catch (Exception n) {
				}
			}
		}
		for (int i = 0; i < g.nombreSommets(); i++) {
			Sommet s = g.getSommet(i);
			if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
				if (s.x >= this.frame.getWidth()) {
					s.x = this.frame.getWidth() - 10;
				}
				if (s.y >= this.frame.getHeight()) {
					s.y = this.frame.getHeight() - 10;
				}
				if (s.x < 0)
					s.x = 0;
				if (s.y < 0)
					s.y = 0;
				try {
					if (tableau[s.x][s.y] == 0) {
						tableau[s.x][s.y] = 1;
					} else {
						while (tableau[s.x][s.y] != 0) {
							s.x += 10 * Math.random();
							s.y += 10 * Math.random();
						}
						tableau[s.x][s.y] = 1;
					}
				} catch (Exception n) {
				}
			}
		}

	}

	// Methode appliquant les forces
	/**
	 * @param graphe
	 * @param fae
	 * @param fac
	 * @param fre
	 * @param frc
	 */
	public synchronized void force_directed_placement(Graphe graphe, double t) {

		this.frame.setLocation(185, 50);
		if (Force.time == 0.0) {
			Force.time = (1 / 0.36) * 10;
			if (graphe.clustering)
				Force.time = (1 / 0.16) * 10;
		}
		if (new_force == 0) {
			/** forces semi paramétrées (ancien said) **/
			double w = this.frame.getSize().getWidth();
			double h = this.frame.getSize().getHeight();
			gravite.x = (int) w / 2;
			gravite.y = (int) h / 2;
			// System.out.println("x = " + gravite.x);
			int nbvisible = 0;

			int ordre = graphe.nombreSommets();
			for (int i = 0; i < ordre; i++) {
				Sommet s = graphe.getSommet(i);
				if (s.getVisible() && !s.nom.contains("virtuel")
						&& !s.nom.contains("Repere"))
					nbvisible++;
			}
			double k = Math.sqrt(w * h / nbvisible);
			double val = control.slider2.getValue();
			Force.K(3 * k * val / 5);

			for (int i = 0; i < graphe.nombreSommets(); i++) {
				Sommet u = graphe.getSommet(i);
				if (u.getVisible()) {
					u.stabilize();
					// ******** Calcul des forces repulsives
					for (int j = 0; j < graphe.nombreSommets(); j++) {
						Sommet v = graphe.getSommet(j);
						if (!u.equals(v) && v.getVisible()) {
							double xx = (double) (u.x - v.x);
							double yy = (double) (u.y - v.y);
							double distance = Math.sqrt(xx * xx + yy * yy); // distance
																			// entre
																			// u
																			// et
																			// v
							if (distance > k)
								continue;
							if ((distance == 0.0) && (u.nom != v.nom))
								distance = 0.0001;
							// if ( ValLien(u,v)>0)
							u.forced(
									v,
									1.0
											/ distance
											* Force.fr(distance,
													((double) (control.slider6
															.getValue())), 2.0),
									true);
						}
						if (!v.getVisible())
							v.stabilize();
						ChangeGraphe = true;
					}
				}

				// Calcul des forces attractives
				try {
					graphe.getTOUSVoisins(u, false, graphe.seuil);
				} catch (Exception n) {
				}
				for (int j = 0; j < u.voisins.size(); j++) {
					Sommet v = (Sommet) u.voisins.elementAt(j);
					double xx = (double) (u.getX() - v.getX());
					double yy = (double) (u.getY() - v.getY());
					double distance = Math.sqrt(xx * xx + yy * yy);
					if (v.getVisible() && !v.nom.contains("virtuel")
							&& !v.nom.contains("Repere")) {
						if ((cluster2 && v.num_cluster != u.num_cluster)
								|| !cluster2)
							u.forced(
									v,
									-(1 + val / 10)
											/ distance
											* Force.fa(distance, 1.5,
													((double) (control.slider8
															.getValue())) / 4),
									true);

						if (cluster2 && v.num_cluster == u.num_cluster)
							u.forced(
									v,
									-(2 + val / 10)
											/ distance
											* Force.fa(distance, 1.5,
													((double) (control.slider8
															.getValue())) / 4),
									true);
					}
					if ((v.nom.contains("virtuel") | (v.nom.contains("Repere")))) {
					}
					if ((u.nom.contains("virtuel") | (u.nom.contains("Repere")))) {
						for (int f = 0; f < control.slider5.getMaximum(); f++) {
							if (u.nom.contains(String.valueOf(f + 1))) {

								u.forced(
										v,
										-1
												/ distance
												* Force.fa(
														distance,
														1.0,
														((double) (control.slider12
																.getValue() * 1) / 3)),
										true);
								v.forced(
										u,
										-1
												/ distance
												* Force.fa(
														distance,
														1.0,
														((double) (control.slider12
																.getValue() * 1) / 3)),
										true);
							}
						}
					}
				}
			}

			int[][] tableau = new int[(int) w][(int) h];
			for (int i = 0; i < this.WIDTH; i++) {
				for (int j = 0; j < this.HEIGHT; j++) {
					tableau[i][j] = 0;
				}
			}
			evite_superpose(tableau, graphe);
			// Deplacement des sommets en fonction de la temperature globale du
			// systeme

			double Temp = Force.temp(Force.time) + Force.minTemp();
			// Mise a jour de la temperature globale du systeme
			Force.Temp(Temp);

			for (int j = 0; j < graphe.nombreSommets(); j++) {
				Sommet v = graphe.getSommet(j);
				if (v.getVisible() && !v.nom.contains("virtuel")
						&& !v.nom.contains("Repere")) {
					double force = v.deltaForce();

					if (force < 0.00001 && Force.time < 100)
						continue;

					if (!v.fixe && force > Force.minTemp()) {
						v.moveDelta(
								1.0 / force * Force.min(force / 5, Temp / 5),
								w, h);
					}
				}
				ChangeGraphe = true;
			}

			if (!cluster2)
				centrerGraphe(graphe);
		} else if (new_force == 1) {
			/** forces paramétrées **/
			int w = (int) this.frame.getSize().getWidth();
			int h = (int) this.frame.getSize().getHeight() - 20;
			gravite.x = (int) w / 2;
			gravite.y = (int) h / 2;
			Sommet s0 = new Sommet(w / 2, h / 2, 0, "s0", 0, 0, 0, 0, 0);

			/*
			 * for ( int i = 0; i < graphe.nombreSommets(); i++ ) { Sommet u =
			 * graphe.getSommet(i); Arete a0 = new Arete(s0,
			 * u,s0.x,u.x,null,50,graphe.nombreAretes()+1);
			 * graphe.ajouterArete(a0); a0.setInvisible(); }
			 */

			int nbvisible = 0;

			int ordre = graphe.nombreSommets();
			for (int i = 0; i < ordre; i++) {
				Sommet s = graphe.getSommet(i);
				if (s.getVisible()) {
					nbvisible++;
				}
			}
			double k = Math.sqrt(w * h / nbvisible);
			double val = control.slider2.getValue();
			Force.K(3 * k * val / 5);

			for (int i = 0; i < graphe.nombreSommets(); i++) {
				Sommet u = graphe.getSommet(i);
				if (u.getVisible()) {
					u.stabilize();
					// ******** Calcul des forces repulsives
					for (int j = 0; j < graphe.nombreSommets(); j++) {
						Sommet v = graphe.getSommet(j);
						if (!u.equals(v) && v.getVisible()) {
							double xx = (double) (u.x - v.x);
							double yy = (double) (u.y - v.y);
							double distance = Math.sqrt(xx * xx + yy * yy); // distance
																			// entre
																			// u
																			// et
																			// v
							if (distance > k)
								continue;
							if ((distance == 0.0) && (u.nom != v.nom))
								distance = 0.0001;
							// if ( ValLien(u,v)>0)

							u.forced(
									v,
									1.0
											/ distance
											* Force.fr(distance, 1.0,
													-((double) (control.slider6
															.getValue() / 9))),
									true);
						}
						if (!v.getVisible())
							v.stabilize();
						// ChangeGraphe=true;
					}
				}
			}
			// Calcul des forces attractives
			for (int j = 0; j < graphe.aretes.size(); j++) {
				Arete a = graphe.getArete(j);
				Sommet v = a.getE1();
				Sommet u = a.getE2();
				double xx = (double) (u.getX() - v.getX());
				double yy = (double) (u.getY() - v.getY());
				double distance = Math.sqrt(xx * xx + yy * yy);
				if (a.getVal() > graphe.seuil && v.getVisible()
						&& u.getVisible() && a.getVisible()
						&& distance > 0.00001 && (force_morph != 2)) {

					if ((cluster2 && v.num_cluster != u.num_cluster)
							|| !cluster2) {
						u.forced(
								v,
								-1
										/ distance
										* Force.fa(distance, 1.0,
												((double) (control.slider8
														.getValue())) / 4),
								true);
						v.forced(
								u,
								-1
										/ distance
										* Force.fa(distance, 1.0,
												((double) (control.slider8
														.getValue())) / 4),
								true);
					}

					if (cluster2 && v.num_cluster == u.num_cluster) {
						u.forced(
								v,
								-2
										/ distance
										* Force.fa(distance, 1.0,
												((double) (control.slider8
														.getValue())) / 4),
								true);
						v.forced(
								u,
								-2
										/ distance
										* Force.fa(distance, 1.0,
												((double) (control.slider8
														.getValue())) / 4),
								true);
					}
					ChangeGraphe = true;
				}
				if ((force_morph == 2)
						&& ((v.nom.contains("virtuel") | (v.nom
								.contains("Repere"))))) {
					if (distance == 0)
						distance += 0.000001;
					u.forced(
							v,
							-1
									/ distance
									* Force.fa(distance, 1.0,
											((double) (control.slider8
													.getValue())) / 4), true);
					v.forced(
							u,
							-1
									/ distance
									* Force.fa(distance, 1.0,
											((double) (control.slider8
													.getValue())) / 4), true);
					u.forced(
							v,
							1.0
									/ distance
									* Force.fr(distance,
											control.slider6.getValue() * 2, 2.0),
							true);
					centrerGraphe(gr);
				}

				if ((v.nom.contains("virtuel") | (v.nom.contains("Repere")))) {
					for (int f = 0; f < control.slider5.getMaximum(); f++) {
						if (v.nom.contains(String.valueOf(f + 1))) {
							u.forced(
									v,
									-1
											/ distance
											* Force.fa(
													distance,
													1.0,
													((double) (control.slider12
															.getValue() * 1) / 3)),
									true);
							v.forced(
									u,
									-1
											/ distance
											* Force.fa(
													distance,
													1.0,
													((double) (control.slider12
															.getValue() * 1) / 3)),
									true);
						}
					}
				}

			}

			int[][] tableau = new int[(int) w][(int) h];
			for (int i = 0; i < this.frame.WIDTH; i++) {
				for (int j = 0; j < this.frame.HEIGHT; j++) {
					tableau[i][j] = 0;
				}
			}
			evite_superpose(tableau, graphe);
			// Deplacement des sommets en fonction de la temperature globale du
			// systeme
			double Temp = Force.temp(Force.time) + Force.minTemp();
			// Mise a jour de la temperature globale du systeme
			Force.Temp(Temp);

			for (int j = 0; j < graphe.nombreSommets(); j++) {
				Sommet v = graphe.getSommet(j);
				if (v.getVisible()) {
					double force = v.deltaForce();

					if (force < 0.00001 && Force.time < 100)
						continue;

					if (!v.fixe && force > Force.minTemp()) {
						v.moveDelta(
								1.0 / force * Force.min(force / 5, Temp / 5),
								w, h);
					}
				}
				ChangeGraphe = true;
			}
			// Force.time += 0.30;
			if (!cluster2)
				centrerGraphe(graphe);
		}

	}

	/*
	 * Placement initial des points selon leur appartenance aux différentes
	 * instances
	 */
	public void placementOriente(Graphe g) {

		int val = 0;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++)
				mat[i][j] = 0;
		}
		int nouvelCoordX = 0;
		int nouvelCoordY = 0;
		double cumulMetrique = 0.0;
		if (g.instance > 1) {
			Integer[] somVirtX = new Integer[g.instance + 1];
			Integer[] somVirtY = new Integer[g.instance + 1];

			for (int i = 0; i < g.nombreSommets(); i++) {
				Sommet s = g.getSommet(i);
				for (int j = 0; j <= g.instance; j++) {
					if ((s.nom.contains("virtuel") | s.nom.contains("Repere"))
							&& s.nom.contains(String.valueOf(j))) {
						somVirtX[j] = s.x;
						somVirtY[j] = s.y;
					}
				}

			}
			if (somVirtX.length != 0) {

				for (int i = 0; i < g.nombreSommets(); i++) {
					Sommet s = g.getSommet(i);
					if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
						cumulMetrique = 0.0;
						nouvelCoordX = 0;
						nouvelCoordY = 0;

						for (int j = 1; j <= g.instance; j++) {
							cumulMetrique += s.Metrique[j];
							nouvelCoordX += (double) (s.Metrique[j] * somVirtX[j]);
							nouvelCoordY += (double) (s.Metrique[j] * somVirtY[j]);
						}

						s.x = (int) ((nouvelCoordX / cumulMetrique));
						s.y = (int) ((nouvelCoordY / cumulMetrique));
						if (s.getVisible() == false)
							s.setVisible();
						int val1 = s.x;
						int val2 = s.y;
						while (verif(val1, val2) == true) {
							if (val == 0) {
								val1 += 1;
								val = 1;
								if (val1 > 800)
									val1 -= (val1 / 8);
							} else {
								val2 += 1;
								val = 0;
								if (val2 > 800)
									val2 -= (val2 / 8);
							}
						}
						s.x = val1;
						s.y = val2;

					}
				}
			}

		}

	}

	public boolean verif(int x, int y) {
		if (!mat[x][y].equals(1)) {
			mat[x][y] = 1;
			return false;
		}
		return true;
	}

	/* Representation circulaire du graphe */
	/* Representation circulaire du graphe */
	public void circulaire(Graphe graphe) {
		double w = getSize().getWidth() - 20;
		double h = getSize().getHeight() - 40;
		double rX, rY;

		rX = w / 2.0;
		rY = h / 2.0;

		int nb_classe0 = 0;
		int nb_classe1 = 0;
		for (int i = 0; i < graphe.nombreSommets(); i++) {
			Sommet s = graphe.getSommet(i);
			if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
				if (s.type == 0 && s.getVisible())
					nb_classe0++;
				if (s.type == 1 && s.getVisible())
					nb_classe1++;
			}
		}

		double theta0 = 3 * Math.PI / 2;
		double delta0 = 2 * Math.PI / nb_classe0;

		double theta1 = 3 * Math.PI / 2;
		double delta1 = 2 * Math.PI / nb_classe1;

		if (graphe.typeMat == 0 || graphe.typeMat == 3) {
			for (int i = 0; i < graphe.nombreSommets(); i++) {
				Sommet s = graphe.getSommet(i);
				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
					if (s.type == 1 && s.getVisible()) {
						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
						theta1 += delta1;
					}
					if (s.type == 0 && s.getVisible()) {
						s.x = (int) (w / 2 + (2 * rX / 3 - 6)
								* Math.cos(theta0));
						s.y = (int) (h / 2 + (2 * rY / 3 - 6)
								* Math.sin(theta0));
						theta0 += delta0;
					}
				}
			}
		}
		if (graphe.typeMat == 1 || graphe.typeMat == 2) {
			if (cluster2) {
				for (int i = 0; i < nbClusters; i++) {
					int indice = ((Integer) (Clusters[i].elementAt(0)))
							.intValue();
					try {
						Sommet s = graphe.getSommet(indice);
						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
						theta1 += delta1;
						for (int j = 1; j < Clusters[i].size(); j++) {
							int ind = ((Integer) (Clusters[i].elementAt(j)))
									.intValue();
							Sommet ss = graphe.getSommet(ind);
							ss.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
							ss.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
							theta1 += delta1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						// System.out.println("\nArrayIndexOutOfBoundsException : "
						// + e );
						continue;
					}
				}
			} else
				for (int i = 0; i < graphe.nombreSommets(); i++) {
					Sommet s = graphe.getSommet(i);
					if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
						if (s.type == 1 && s.getVisible()) {
							s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
							s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
							theta1 += delta1;
						}
					}
				}
			
		}
	}

	/*
	 * fonction permettant après avoir fait une représentation sous forme de
	 * clusters, de faire une présentation avec un représentant par groupe et
	 * tous les autres composants, placés de façon circulaire autour du
	 * représentant.
	 */
	public void circulaireClustered(Graphe gr) {
		if (gr.typeMat == 1 || gr.typeMat == 2) {
			double theta = 0.0;

			for (int i = 0; i < nbClusters; i++) {
				double maxMetrique = 0;
				int rep = 0;
				Sommet noyau = new Sommet(0, 0, 0);
				for (int j = 0; j < Clusters[i].size(); j++) {
					int indice = ((Integer) (Clusters[i].elementAt(j)))
							.intValue();
					noyau = gr.getSommet(indice);
					noyau.fixe = false;
					if (noyau.metrique > maxMetrique) {
						maxMetrique = noyau.metrique;
						rep = indice;
					}
				}
				noyau = gr.getSommet(rep);
				noyau.fixe = true;

				double rayon = 30.0;
				metaNoeud cluster = (metaNoeud) mg.getSommet(i);

				noyau.x = cluster.x;
				noyau.y = cluster.y;
				if (noyau.x < 20)
					noyau.x = 50;
				if (noyau.y < 20)
					noyau.y = 50;
				if (noyau.x > getSize().getWidth() - 20)
					noyau.x = (int) getSize().getWidth() - 20;
				if (noyau.y > getSize().getHeight() - 40)
					noyau.y = (int) getSize().getHeight() - 40;

				double delta = 2 * Math.PI / (Clusters[i].size() - 1);

				for (int j = 0; j < Clusters[i].size(); j++) {
					Sommet s = (Sommet) (cluster.sommets.elementAt(j));
					if (!s.equals(noyau)) {
						s.x = (int) (noyau.x + rayon * Math.cos(theta));
						s.y = (int) (noyau.y + rayon * Math.sin(theta));
						rayon += 0.25;
						theta += delta;
					}
				}
			}
		}

	}

	public void colorerGraphe(Graphe graphe, Color cl) {
		int na = gr.nombreAretes();
		for (int i = 0; i < na; i++) {
			Arete a = gr.getArete(i);
			if (graphe.areteDansGraphe(a.getE1(), a.getE2()) != -1
					|| graphe.areteDansGraphe(a.getE2(), a.getE1()) != -1)
				a.setCouleur(cl);
		}
	}

	public void MCL(String args) { // String cheminin = chemin +
									// "/clusters.out";

		int nbvisible = 0;
		int ordre = gr.nombreSommets();
		Graphe g = new Graphe(false);
		System.out.println("type de matrice : " + gr.typeMat);
		/* Graphe de transition */
		if (gr.typeMat == 1 || gr.typeMat == 2) { // type mat 1 ==
			if (gr.typeMat == 2) { // type matrice 2 ==
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = gr.getSommet(i);
					if (s.nom.contains("virtuel")) {
						gr.supprimerSommet(gr.indiceSommet(s));
						g.ajouterSommet(s);
					}
				}
				ordre = gr.nombreSommets() - g.nombreSommets();
			}

			for (int i = 0; i < ordre; i++) {
				Sommet s = gr.getSommet(i);
				if (s.getVisible() && !s.nom.contains("Repere")
						&& (!s.nom.contains("virtuel")))
					nbvisible++;
			}
			try {
				Writer out = new FileWriter(args);
				out.write("(mclheader\n");
				out.write("mcltype matrix \n");
				out.write("dimensions " + nbvisible + "x" + nbvisible);
				out.write("\n)\n");
				out.write("(mclmatrix\n");
				out.write("begin\n");
				for (int i = 0; i < ordre; i++) {
					Sommet s = gr.getSommet(i);
					if (s.getVisible() && !s.nom.contains("Repere")
							&& (!s.nom.contains("virtuel"))) {
						gr.getVoisins(s, false, gr.seuil);
						out.write(i + "    ");
						for (int j = 0; j < s.voisins.size(); j++) {
							Sommet ss = (Sommet) s.voisins.elementAt(j);
							if (ss.getVisible() && s.getVisible()
									&& !s.nom.contains("Repere")
									&& (!s.nom.contains("virtuel"))) {
								Arete a = gr
										.getArete(gr.areteDansGraphe(s, ss));
								if (a.getVisible())
									out.write(gr.indiceSommet(ss) + ":"
											+ a.getVal() + " ");
							}
						}
						out.write("$\n");
					}
				}
				out.write(")\n");
				out.close();

			} catch (IOException e) {
				System.out.println(" Erreur " + e);
			}
			try {
				Properties p = new Properties(System.getProperties());
				p.setProperty("cheminbin", "/home/bernard/workspace/VisuGraph2");
//				String cheminbin = System.getProperty("cheminBin")
//						+ "/bin/clustering ";
				String cheminbin = p.getProperty("cheminbin");
				p.setProperty("cheminout", "/home/bernard/workspace/VisuGraph2");
//				String cheminout = System.getProperty("cheminResult")
//						+ "/out.clusters";
				String cheminout = p.getProperty("cheminout");
				
				Process pr2 = Runtime.getRuntime().exec(
						cheminbin + args + " -I " + facteur + " -o "
								+ cheminout);
				pr2.waitFor();
				gr.clustering = true;

			} catch (Exception e) {
				System.out.println(" Erreur de Clustering " + e);
			}
		} else {
			System.out.println(" Le Clustering ne s'applique que sur des matrices symétriques");
			gr.clustering = false;
		}
		if (gr.typeMat == 2 || gr.typeMat == 2) {
			/*
			 * On rajoute les sommets temporels qu'on a enlevé pour la
			 * clusterisation
			 */
			for (int i = 0; i < g.nombreSommets(); i++) {
				Sommet t = g.getSommet(i);
				gr.ajouterSommet(t);
			}
		}
		repaint();
	}
    /**
     * Lire à partir out.cluster les cluster et remplir vecteur des cluster Vector cluster[]
     * @param args 
     */
	public void lireClustersMCL(String args) {           //		String cheminout = chemin + "/out.clusters";
 
		char[] ordre;
		nbClusters = 0;
		String texte;
		int nb_clusters;
		try {
			File file = new File(args);
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);

			ligne_doc = line.readLine();
			ligne_doc = line.readLine();
			ligne_doc = line.readLine();

			StringTokenizer ligne = new StringTokenizer(ligne_doc);
			String tt = ligne.nextToken();
			tt = ligne.nextToken();
			StringTokenizer dimension = new StringTokenizer(tt, "x");

			String nbso = dimension.nextToken();
			String clusters = dimension.nextToken();

			nbClusters = Integer.parseInt(clusters);
			int nbsommets = Integer.parseInt(nbso);

			Clusters = new Vector[nbClusters];

			while (ligne_doc != null && ligne_doc.compareTo("begin") != 0)
				ligne_doc = line.readLine();

			ligne_doc = line.readLine();
			int i = line.getLineNumber() - 7;
			while (ligne_doc != null && ligne_doc.compareTo(")") != 0) {
				StringTokenizer cluster = new StringTokenizer(ligne_doc);
				Clusters[i] = new Vector();

				String element_cluster = cluster.nextToken();
				element_cluster = cluster.nextToken();

				while (element_cluster.compareTo("$") != 0) {
					Clusters[i].addElement(new Integer(element_cluster));
					try {
						element_cluster = cluster.nextToken();

					} catch (NoSuchElementException EE) {
						ligne_doc = line.readLine();
						cluster = new StringTokenizer(ligne_doc);
						element_cluster = cluster.nextToken();
					}
				}
				ligne_doc = line.readLine();
				i++;
			}
			line.close();
		} catch (IOException e) {
			System.out.println(" Erreur : " + e);
		}
		control.slider2.setValue(5);
	}
    /**
     * 
     * @param graphe
     */
	public void genereClusters(Graphe graphe) {
		mg.sommets = new Vector(nbClusters);
		mg.aretes = new Vector();
		mg.typeGraphe = gr.typeGraphe;
		mg.typeMat = gr.typeMat;
		mg.instance = gr.instance;
		String nom = null;
		int degre = 1;

		System.out.println("Le graphe est décomposé en " + nbClusters
				+ " classes.");
		System.out.println("Construction du méta-graphe");

		try {
			String fic = param1 + "/VisuClass.txt";
			FileWriter classer = new FileWriter(fic);
			classer.write("RESULTATS DU MARKOV CLUSTERING\n******************************\n");
			for (int i = 0; i < nbClusters; i++) {
				int indice = ((Integer) (Clusters[i].elementAt(0))).intValue();
				Sommet s = graphe.getSommet(indice);
				s.fixe = false;
				metaNoeud mn = new metaNoeud("Cluster " + String.valueOf(i),
						s.x, s.y, s.z);
                
				s.num_cluster = i;
				classer.write("\n\nCluster " + i + "\n**********\n");
				for (int j = 0; j < Clusters[i].size(); j++) {
					int ind = ((Integer) (Clusters[i].elementAt(j))).intValue();
					Sommet ss = graphe.getSommet(ind);
					classer.write("\n" + ss.nomlong);
					//classer.write("\n" + ss.nom);
					ss.type = 1;
					mn.sommets.addElement(ss);
					ss.num_cluster = i;
					mn.metrique += ss.metrique;

					for (int l = 1; l <= graphe.instance; l++)
						mn.Metrique[l] += ss.Metrique[l];

					mn.Metrique[graphe.instance + 1] += ss.Metrique[graphe.instance + 1];
				}

				mn.type = 1;
				mn.couleur = s.couleurClasse;
				mn.couleurClasse = s.couleurClasse;
				mg.sommets.addElement((Sommet) mn);
				// System.out.println(" mn.nom " + mn.couleurClasse + " s.nom "+
				// s.nom +" la couleur de sa classe "+ s.couleurClasse);
				int cpt_intra_aretes = 0;

				for (int k = 0; k < graphe.aretes.size(); k++) {
					Arete a = graphe.getArete(k);

					Sommet e1 = a.getE1();
					Sommet e2 = a.getE2();
					if (e1.num_cluster == e2.num_cluster) {
						Arete intra_arete = new Arete(e1, e2, e1.x, e2.x, null,
								a.getNom(), cpt_intra_aretes++);
						intra_arete.setVal(a.getVal());
						mn.aretes.addElement(intra_arete);
					}
				}

			}
			classer.close();
		} catch (IOException exception) {
			System.out.println("on est sortis");
		}
		mg.maximumMetrique();

		int cpt_meta_aretes = 0;
		for (int i = 0; i < graphe.aretes.size(); i++) {
			Arete a = graphe.getArete(i);
			Sommet e1 = a.getE1();
			Sommet e2 = a.getE2();

			if (e1.num_cluster != e2.num_cluster
					&& (!e1.nom.contains("virtuel"))
					&& (!e2.nom.contains("virtuel"))
					&& (!e1.nom.contains("Repere"))
					&& (!e2.nom.contains("Repere"))) {

				metaNoeud m1 = (metaNoeud) mg.getSommet(e1.num_cluster);
				metaNoeud m2 = (metaNoeud) mg.getSommet(e2.num_cluster);

				Arete meta_arete = new Arete(m1, m2, m1.x, m2.x, null,
						a.getNom(), cpt_meta_aretes++);
				meta_arete.setVal(a.getVal());
				mg.aretes.addElement(meta_arete);
			}
		}
		mg.maximumArete();
		centrerGraphe(mg);
	}

	public void imprimer() {
		PrinterJob prj = PrinterJob.getPrinterJob();
		prj.setPrintable((Printable) this);

		if (prj.printDialog()) {
			try {
				prj.print();
			} catch (PrinterException pe) {
				System.out.println(pe);
			}
		}
	}

	public int print(Graphics g, PageFormat pf, int pageIndex) {
		if (pageIndex == 0) {
			paintComponent(g);
			return PAGE_EXISTS;
		} else
			return NO_SUCH_PAGE;
	}

	// Sauvegarde le trace du graphe pour une eventuelle utilisation
	public void SauvegarderGraphe(Graphe graphe) {
		
		String rep = System.getProperty("user.dir");
		File fichier;
		FileFilter filtre = new FileFilter() {
			public boolean accept(File f1) {
				return (f1.isDirectory() || f1.getName().endsWith(".gr"));
			}

			public String getDescription() {
				return ("Fichier Graph (*" + ".gr" + ")");
			}
		};
		JFileChooser boite = new JFileChooser(rep);
		boite.addChoosableFileFilter(filtre);
		boite.setFileFilter(filtre);
		int option = boite.showSaveDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION) {
			fichier = boite.getSelectedFile();
			if (fichier.getName().endsWith(".gr")) {
				rep = boite.getCurrentDirectory().getAbsolutePath();
				if (fichier.exists()) {
					JOptionPane.showConfirmDialog(null,
							"Le fichier existe déjà, écraser ?", "Attention",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
				}
				// Je transforme en Coordonnees
				transforme(fichier.getAbsolutePath(), graphe);
				JOptionPane.showMessageDialog(frame,
						"Génération du dessin en coordonnees effectuée");
			} else {
				JOptionPane.showMessageDialog(null,
						"Le fichier sélectionné doit etre du type .gr",
						"Type de fichier non valide",
						JOptionPane.INFORMATION_MESSAGE);
			}
			;
		}
	}

	public void ecrire_classe(Graphe gr) throws IOException {//*****************************************************************************************
		Runtime.getRuntime().exec("notepad.exe " + param1 + "/VisuClass.txt");
		

	}

	public void transforme(String nom, Graphe graphe) {
		// Ouverture du fichier en écriture
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(nom));
			try {
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = graphe.getSommet(i);
					out.write(s.nom + " : " + s.x + " " + s.y + "\n");
				}
			} catch (IOException e) {
			}
			out.close();
		} catch (IOException e) {

		}

	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public void exportMeasures(GraphPanel gp){
		if(gp.getGraphe().typeMat == 0 || gp.getGraphe().typeMat == 3){
			
		}
	}
	/**
	 * Report function : all general measures in different periode report
	 * @param tgr2
	 * @param periode
	 * @param type
	 * @param nom_fichier
	 * @param alpha
	 */
	public void periodicGenCentrality(GraphPanel tgr2, int periode , int type, String nom_fichier, double alpha){
		 
		int per = periode;
	for(int compteur = 1; compteur <= per ; compteur++){
		if(type  == 1 || type == 2){ // matrice symetrique : simple ou temporelle
       	Graphe gr = new Graphe(false);
       		gr.enregistre(nom_fichier, compteur);
            gr.ChargerMatSymetrique(nom_fichier, compteur);
            for (int k= 0; k < gr.nombreSommets(); k++) {
    			Sommet s = gr.getSommet(k);
    			gr.getVoisins(s, false, gr.seuil);
    			s.nbvoisins = s.voisins.size();
    		}
            
    		GraphPanel gp= new GraphPanel(gr, true);
        	gp.loadComputeDistance(gp, alpha, nom_fichier);
        	gp.degree_gen(gr, alpha);
        	gp.closenessCentralityGen(gp, alpha);
        	gp.betweennessCentralityGen(gp, alpha);
        	for(int j = 0; j< gr.sommets.size(); j++){
        		if( ! gr.getSommet(j).getNom().contains("virtuel")){
        		gr.getSommet(j).getPeriodicCombinedDegree().put(periode, gr.getSommet(j).getCentralite_combine());
        		gr.getSommet(j).getPeriodicGenIntermediarity().put(periode, gr.getSommet(j).getBetweennessGenCentrality() );
        		gr.getSommet(j).getPeriodicGenProximity().put(periode, gr.getSommet(j).getClosenessGenCentrality());
        	}
		}
        	for(int ll = 0 ; ll< gp.gr.sommets.size() ; ll++){
        		if(gr.getSommet(ll).voisins.size() > 0 &&  !gr.getSommet(ll).getNom().contains("virtuel")){
        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicCombinedDegree().get(periode));
        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicGenIntermediarity().get(periode));
        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicGenProximity().get(periode));
        		} else {
            		System.out.println("noeud " + tgr2.getGraphe().getSommet(ll).getNom() + "   est isolé  !!");
            		
        		}
        	}
        	System.out.println("*************************************************************");
          // }
        	// 
	
        	
		}else if(type == 0 || type == 3){ // pas de mesures de centralités relatifs aux graphe bipartis
			
		}
	}
	
	}
    /**
     * 
     * @param periode 
     * @param type
     * @param nom_fichier
     * @param gp
     */
	public void periodicSimpleCentrality(GraphPanel tgr2 , int periode , int type , String nom_fichier, double alpha ){ 
		int per = periode;
	for(int compteur = 1; compteur <= per ; compteur++){
		if(type  == 1 || type == 2){ // matrice symetrique : simple ou temporelle
       	Graphe gr = new Graphe(false);
       		gr.enregistre(nom_fichier, compteur);
            gr.ChargerMatSymetrique(nom_fichier, compteur);
            for (int k= 0; k < gr.nombreSommets(); k++) {
    			Sommet s = gr.getSommet(k);
    			gr.getVoisins(s, false, gr.seuil);
    			s.nbvoisins = s.voisins.size();
    		}
    		GraphPanel gp= new GraphPanel(gr, true);
    		gp.loadComputeDistance(gp, alpha, "periode"+periode);
        	gp.degreSimple(gr);
        	gp.closenessCentralityfreeMan(gp, alpha);
         	gp.betweennessFreeMan(gp);
        	tgr2.getGraphe().maxDegreeCentrality = gp.gr.maxDegreeCentrality(gr);
        	tgr2.getGraphe().maxStrengthCetrnality =  gp.gr.maxLinkCentrality(gr);
        	tgr2.getGraphe().maxBetweennessCentrality = gp.gr.maxIntermediarityCentrality(gr);
        	tgr2.getGraphe().maxPoximityCentrality= gp.gr.maxProximityCentrality(gr);
        	
        	for(int j = 0; j< gr.sommets.size(); j++){
        		if( ! gr.getSommet(j).getNom().contains("virtuel") &&  ! gr.getSommet(j).getNom().contains("Rep")){ 
        		tgr2.gr.getSommet(j).getPeriodicDegree().put(compteur, gr.getSommet(j).getCentralite_degre());
        		tgr2.gr.getSommet(j).getPeriodicLink().put(compteur, gr.getSommet(j).getCentralite_poids());
        		tgr2.gr.getSommet(j).getPeriodicproximity().put(compteur, gr.getSommet(j).getClosenessCentrality());
        		tgr2.gr.getSommet(j).getPeriodicintermediarity().put(compteur, gr.getSommet(j).getBetweennessCentrality());

        	}
		}

//        	for(int ll = 0 ; ll< gp.gr.sommets.size() ; ll++){
//        		if(gr.getSommet(ll).voisins.size() > 0 &&  !gr.getSommet(ll).getNom().contains("virtuel")){
////        		System.out.println(tgr2.gr.getSommet(ll).getNom() + "  ----> "+"Degree = "+ tgr2.gr.getSommet(ll).getPeriodicDegree().get(compteur));
////        		System.out.println(tgr2.gr.getSommet(ll).getNom() + "  ----> "+"Strength = "+ tgr2.gr.getSommet(ll).getPeriodicLink().get(compteur));
////        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ "BTW = "+tgr2.gr.getSommet(ll).getPeriodicintermediarity().get(compteur));
////        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ "Proximity = "+tgr2.gr.getSommet(ll).getPeriodicproximity().get(compteur));
//
//        		} else {
//            		System.out.println("noeud " + tgr2.getGraphe().getSommet(ll).getNom() + "   est isolé  !!");
//            		
//        		}
//        	
//        	 }
        	
        	System.out.println("***************************fin periode " + compteur);
          
        	
        	
        	
        	
        	
        	
        	
        	
        	
		}else if(type == 0 || type == 3){ 
			
		}
	}
	}
	
	
	public void circularStrengthCentrality(GraphPanel gp, Graphe gr){
		gp.degreSimple(gr);
	//	gr.classDegreeCentrality(gr);//////////////////////////////////////////////////////// slider77 pb
//		double maxStrengthCentrality = gr.maxLinkCentrality(gp);
//		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
//			Sommet s = gr.getSommet(i);
//            double max = maxStrengthCentrality/5;
//            if(s.getCentralite_poids()> 0){
//            if(s.getCentralite_poids() < max){ 
//            	s.setClassPoidCentrality(1);
//            	}
//            else if(( max <= s.getCentralite_poids()) &&(s.getCentralite_poids()< (2)*max)) {
//            	s.setClassPoidCentrality(2);
//            	} 
//            else if(( (2)*max <= s.getCentralite_poids()) &&(s.getCentralite_poids() < (3)*max)){
//            	s.setClassPoidCentrality(3);
//            	}
//            else if(( (3) * max <= s.getCentralite_poids()) &&(s.getCentralite_poids()< (4)*max)){
//            	s.setClassPoidCentrality(4);
//            	}
//            else {
//            	s.setClassPoidCentrality(5);
//            	}
//            }else{
//            	s.setInvisible();
//            }
//		
//		}
		double w = getSize().getWidth() - 20;
		double h = getSize().getHeight() - 40;
		double rX, rY;

		rX = w / 2.0;
		rY = h / 2.0;

		Vector<Sommet> classe1 = new Vector<Sommet>();
		Vector<Sommet> classe2 = new Vector<Sommet>();
		Vector<Sommet> classe3 = new Vector<Sommet>();
		Vector<Sommet> classe4 = new Vector<Sommet>();
		Vector<Sommet> classe5 = new Vector<Sommet>();
		int nb_classe1 = 0;
		int nb_classe2 = 0;
		int nb_classe3 = 0;
		int nb_classe4 = 0;
		int nb_classe5 = 0;
		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet s = gr.getSommet(i);
			if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
				if (s.type == 1 && s.getVisible()){
					s.setCouleur(new Color((12.75f*s.getClassPoidCentrality()) ,0 , 0));
//					if(s.getClassPoidCentrality() == 1){
//					classe1.add(s);
//					s.setCouleur(s.couleurCentral1);
//					nb_classe1++;
//					}else if(s.getClassPoidCentrality() == 2){
//						classe2.add(s);
//						s.setCouleur(s.couleurCentral2);
//						nb_classe2++;
//					}else if(s.getClassPoidCentrality()== 3){
//						classe3.add(s);
//						s.setCouleur(s.couleurCentral3);
//						nb_classe3++;
//					}else if(s.getClassPoidCentrality()== 4){
//						classe4.add(s);
//						s.setCouleur(s.couleurCentral4);
//						nb_classe4++;
//					}else if(s.getClassPoidCentrality()== 5){
//						classe5.add(s);
//						s.setCouleur(s.couleurCentral5);
//						nb_classe5++;
//					}
				}
			}
		}

		double theta1 = 3 * Math.PI / 2;
		double delta1 = 2 * Math.PI / nb_classe1;

		double theta2 = 3 * Math.PI / 2;
		double delta2 = 2 * Math.PI / nb_classe2;
		
		double theta3 = 3 * Math.PI / 2;
		double delta3 = 2 * Math.PI / nb_classe3;
		
		double theta4 = 3 * Math.PI / 2;
		double delta4 = 2 * Math.PI / nb_classe4;
		
		double theta5 = 3 * Math.PI / 2;
		double delta5 = 2 * Math.PI / nb_classe5;

		if (gr.typeMat == 1 || gp.getGraphe().typeMat == 2) {
			for (int i = 0; i < gr.nombreSommets(); i++) {
				Sommet s = gp.getGraphe().getSommet(i);
				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
					if (s.type == 1 && s.getVisible()) {
						if(classe1.contains(s)){
						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
						theta1 += delta1;
						} else if(classe2.contains(s)) {
							s.x = (int) (w / 2 + (rX - 80) * Math.cos(theta2));
							s.y = (int) (h / 2 + (rY - 80) * Math.sin(theta2));
							theta2 += delta2;
							
						} else if(classe3.contains(s)){
							s.x = (int) (w / 2 + (rX - 120) * Math.cos(theta3));
							s.y = (int) (h / 2 + (rY - 120) * Math.sin(theta3));
							theta3 += delta3;
						}else if(classe4.contains(s)){
							s.x = (int) (w / 2 + (rX - 180) * Math.cos(theta4));
							s.y = (int) (h / 2 + (rY - 180) * Math.sin(theta4));
							theta4 += delta4;
						}else if(classe5.contains(s)){
							if(classe5.size() == 1){
							s.x = (int) (w / 2 );//+ (rX - 250) * Math.cos(theta5));
							s.y = (int) (h / 2);// + (rY - 250) * Math.sin(theta5));
							}else{
								
								s.x = (int) ((w / 2 )+ (rX - 250) * Math.cos(theta5));
								s.y = (int) ((h / 2) + (rY - 250) * Math.sin(theta5));
							theta5 += delta5;
							}
						}
					}
				
				}
			}
		}

	
	}
	public boolean isShowTempGenMeasures() {
		return showTempGenMeasures;
	}

	public void setShowTempGenMeasures(boolean showTempGenMeasures) {
		this.showTempGenMeasures = showTempGenMeasures;
	}
//
//	/**
//	 * affiche sous format circulaire les niveaux de centralités de degré existantes dans un graphe donné  
//	 * @param graphe
//	 */
//	public void circularDegreeCentralityGraph(GraphPanel gp, Graphe gr , String Mesure , float Slider77 ) {
//		//GraphPanel gp  = new GraphPanel(gr, true);
//		gp.degreSimple(gr);
//		double maxDegreeCentrality = gr.maxDegreeCentrality(gr);
//		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
//			Sommet s = gr.getSommet(i);
//            double max = maxDegreeCentrality/5;
//            if(s.getCentralite_degre() > 0){
//            if(s.getCentralite_degre() < max){ 
//            	s.setClassDegreeCentrality(1);
//            	}
//            else if(( max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (2)*max)) {
//            	s.setClassDegreeCentrality(2);
//            	} 
//            else if(( (2)*max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (3)*max)){
//            	s.setClassDegreeCentrality(3);
//            	}
//            else if(( (3) * max < s.getCentralite_degre()) &&(s.getCentralite_degre()< (4)*max)){
//            	s.setClassDegreeCentrality(4);
//            	}
//            else {
//            	s.setClassDegreeCentrality(5);
//            	}
//            }else{
//            	s.setInvisible();
//            }
//		
//		}//		double w = getSize().getWidth() - 20;
//		double h = getSize().getHeight() - 40;
//		double rX, rY;
//
//		rX = w / 2.0;
//		rY = h / 2.0;
//
//		Vector<Sommet> classe1 = new Vector<>();
//		Vector<Sommet> classe2 = new Vector<>();
//		Vector<Sommet> classe3 = new Vector<>();
//		Vector<Sommet> classe4 = new Vector<>();
//		Vector<Sommet> classe5 = new Vector<>();
//		int nb_classe1 = 0;
//		int nb_classe2 = 0;
//		int nb_classe3 = 0;
//		int nb_classe4 = 0;
//		int nb_classe5 = 0;
//		for (int i = 0; i < gr.nombreSommets(); i++) {
//			Sommet s = gr.getSommet(i);
//			if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
//				if (s.type == 1 && s.getVisible()){
//					if(s.getClassDegreeCentrality() == 1){
//					classe1.add(s);
//					s.setCouleur(s.couleurCentral1);
//					nb_classe1++;
//					}else if(s.getClassDegreeCentrality() == 2){
//						classe2.add(s);
//						s.setCouleur(s.couleurCentral2);
//						nb_classe2++;
//					}else if(s.getClassDegreeCentrality()== 3){
//						classe3.add(s);
//						s.setCouleur(s.couleurCentral3);
//						nb_classe3++;
//					}else if(s.getClassDegreeCentrality()== 4){
//						classe4.add(s);
//						s.setCouleur(s.couleurCentral4);
//						nb_classe4++;
//					}else if(s.getClassDegreeCentrality()== 5){
//						classe5.add(s);
//						s.setCouleur(s.couleurCentral5);
//						nb_classe5++;
//					}
//				}
//			}
//		}
//
//		double theta1 = 3 * Math.PI / 2;
//		double delta1 = 2 * Math.PI / nb_classe1;
//
//		double theta2 = 3 * Math.PI / 2;
//		double delta2 = 2 * Math.PI / nb_classe2;
//		
//		double theta3 = 3 * Math.PI / 2;
//		double delta3 = 2 * Math.PI / nb_classe3;
//		
//		double theta4 = 3 * Math.PI / 2;
//		double delta4 = 2 * Math.PI / nb_classe4;
//		
//		double theta5 = 3 * Math.PI / 2;
//		double delta5 = 2 * Math.PI / nb_classe5;
//
//		if (gr.typeMat == 1 || gp.getGraphe().typeMat == 2) {
//			for (int i = 0; i < gr.nombreSommets(); i++) {
//				Sommet s = gp.getGraphe().getSommet(i);
//				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
//					if (s.type == 1 && s.getVisible()) {
//						if(classe1.contains(s)){
//						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
//						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
//						theta1 += delta1;
//						} else if(classe2.contains(s)) {
//							s.x = (int) (w / 2 + (rX - 80) * Math.cos(theta2));
//							s.y = (int) (h / 2 + (rY - 80) * Math.sin(theta2));
//							theta2 += delta2;
//							
//						} else if(classe3.contains(s)){
//							s.x = (int) (w / 2 + (rX - 120) * Math.cos(theta3));
//							s.y = (int) (h / 2 + (rY - 120) * Math.sin(theta3));
//							theta3 += delta3;
//						}else if(classe4.contains(s)){
//							s.x = (int) (w / 2 + (rX - 180) * Math.cos(theta4));
//							s.y = (int) (h / 2 + (rY - 180) * Math.sin(theta4));
//							theta4 += delta4;
//						}else if(classe5.contains(s)){
//							if(classe5.size() == 1){
//							s.x = (int) (w / 2 );//+ (rX - 250) * Math.cos(theta5));
//							s.y = (int) (h / 2);// + (rY - 250) * Math.sin(theta5));
//							}else{
//								
//								s.x = (int) ((w / 2 )+ (rX - 250) * Math.cos(theta5));
//								s.y = (int) ((h / 2) + (rY - 250) * Math.sin(theta5));
//							theta5 += delta5;
//							}
//						}
//					}
//				
//				}
//			}
//		}
////		if (gr.typeMat == 1 || gr.typeMat == 2) {
////			if (cluster2) {
////				for (int i = 0; i < nbClusters; i++) {
////					int indice = ((Integer) (Clusters[i].elementAt(0)))
////							.intValue();
////					try {
////						Sommet s = gr.getSommet(indice);
////						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
////						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
////						theta1 += delta1;
////						for (int j = 1; j < Clusters[i].size(); j++) {
////							int ind = ((Integer) (Clusters[i].elementAt(j)))
////									.intValue();
////							Sommet ss = gr.getSommet(ind);
////							ss.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
////							ss.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
////							theta1 += delta1;
////						}
////					} catch (ArrayIndexOutOfBoundsException e) {
////						// System.out.println("\nArrayIndexOutOfBoundsException : "
////						// + e );
////						continue;
////					}
////				}
////			} else
////				for (int i = 0; i < gr.nombreSommets(); i++) {
////					Sommet s = gr.getSommet(i);
////					if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
////						if (s.type == 1 && s.getVisible()) {
////							s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
////							s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
////							theta1 += delta1;
////						}
////					}
////				}
////		}
//	}
//	public void hierarchicalCentrality(GraphPanel gp, Graphe gr){
//		gp.degreSimple(gr);
//		double maxDegreeCentrality = gr.maxDegreeCentrality(gr);
//		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
//			Sommet s = gr.getSommet(i);
//            double max = maxDegreeCentrality/5;
//            if(s.getCentralite_degre() > 0){
//            if(s.getCentralite_degre() < max){ 
//            	s.setClassDegreeCentrality(1);
//            	}
//            else if(( max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (2)*max)) {
//            	s.setClassDegreeCentrality(2);
//            	} 
//            else if(( (2)*max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (3)*max)){
//            	s.setClassDegreeCentrality(3);
//            	}
//            else if(( (3) * max < s.getCentralite_degre()) &&(s.getCentralite_degre()< (4)*max)){
//            	s.setClassDegreeCentrality(4);
//            	}
//            else {
//            	s.setClassDegreeCentrality(5);
//            	}
//            }else{
//            	s.setInvisible();
//            }
//		
//		}
//		double w = getSize().getWidth() - 20;
//		double h = getSize().getHeight() - 40;
//		double rX, rY;
//
//		rX = w / 2.0;
//		rY = h / 2.0;
//
//		Vector<Sommet> classe1 = new Vector<>();
//		Vector<Sommet> classe2 = new Vector<>();
//		Vector<Sommet> classe3 = new Vector<>();
//		Vector<Sommet> classe4 = new Vector<>();
//		Vector<Sommet> classe5 = new Vector<>();
//		int nb_classe1 = 0;
//		int nb_classe2 = 0;
//		int nb_classe3 = 0;
//		int nb_classe4 = 0;
//		int nb_classe5 = 0;
//		for (int i = 0; i < gr.nombreSommets(); i++) {
//			Sommet s = gr.getSommet(i);
//			if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
//				if (s.type == 1 && s.getVisible()){
//					if(s.getClassDegreeCentrality() == 1){
//					classe1.add(s);
//					s.setCouleur(s.couleurCentral1);
//					nb_classe1++;
//					}else if(s.getClassDegreeCentrality() == 2){
//						classe2.add(s);
//						s.setCouleur(s.couleurCentral2);
//						nb_classe2++;
//					}else if(s.getClassDegreeCentrality()== 3){
//						classe3.add(s);
//						s.setCouleur(s.couleurCentral3);
//						nb_classe3++;
//					}else if(s.getClassDegreeCentrality()== 4){
//						classe4.add(s);
//						s.setCouleur(s.couleurCentral4);
//						nb_classe4++;
//					}else if(s.getClassDegreeCentrality()== 5){
//						classe5.add(s);
//						s.setCouleur(s.couleurCentral5);
//						nb_classe5++;
//					}
//				}
//			}
//		}
//
//		double theta1 = 3 * Math.PI / 2;
//		double delta1 = 2 * Math.PI / nb_classe1;
//
//		double theta2 = 3 * Math.PI / 2;
//		double delta2 = 2 * Math.PI / nb_classe2;
//		
//		double theta3 = 3 * Math.PI / 2;
//		double delta3 = 2 * Math.PI / nb_classe3;
//		
//		double theta4 = 3 * Math.PI / 2;
//		double delta4 = 2 * Math.PI / nb_classe4;
//		
//		double theta5 = 3 * Math.PI / 2;
//		double delta5 = 2 * Math.PI / nb_classe5;
//
//		if (gr.typeMat == 1 || gp.getGraphe().typeMat == 2) {
//			for (int i = 0; i < gr.nombreSommets(); i++) {
//				Sommet s = gp.getGraphe().getSommet(i);
//				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
//					if (s.type == 1 && s.getVisible()) {
//						if(classe1.contains(s)){
//							
//						} else if(classe2.contains(s)){
//							
//						} else if(classe2.contains(s)){
//							
//						} else if(classe3.contains(s)){
//							
//						} else if(classe4.contains(s)){
//							
//						} else if(classe5.contains(s)){
//							
//						}
//					}
//				}
//			}
//		}
//
//	}
	
	/**
	 * initialise map distance au niveau de chaque sommet
	 */
public void initDistance(GraphPanel gp) {
		
		for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
			Sommet s = gp.getGraphe().getSommet(i);
//			System.out.println("init Distance");
//			System.out.println("Sommet_i   : "  + s.getNom());
			Vector<Sommet> v = new Vector<Sommet>();
			v.add(s);
			for (int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
				Sommet ss = gp.getGraphe().getSommet(j);
//				System.out.println("sommet_j  :" + ss.getNom());
				s.getPredecessors().put(ss, v);
				if(s == ss){
					s.getDistance().put(ss, 0.0);
				}else if(s!=ss){
						if(s.voisins.size() == 0 || ss.voisins.size() == 0){
							s.getDistance().put(ss, -1.0);
						}else if(s.voisins.size() != 0 && ss.voisins.size() != 0){
							s.getDistance().put(ss, Double.MAX_VALUE);
						}
				}
				
			}


		}
	}


// *******************************************************************************



private static boolean trouve;
static Stack<Sommet> stack ;

/***
 * on initialise le nombre des noeuds intermediaires à : 1 : si noeud
 * recherché est voisine 0 : si noeud recherché est isolé(zero voisins)
 * infini : si noeud recherché est contenu dans le graphe
 * 
 * @param gr
 *            : Graphe
 * 
 * 
 * **********/
public static int getShortestNodePath(Sommet source, Sommet destination) {
	int nbre = source.getNombreIntermediaire().get(destination);
	return nbre;
}

/**
 * 
 * @param node
 * @return 1 si tous les voisins sont marqués 0 sinon
 */
public static int checkVoisins(Sommet node) {
	int settled = 0;
	int sett = 0;
	for (int i = 0; i < node.voisins.size(); i++) {
		Sommet s = (Sommet) node.voisins.elementAt(i);
		if (s.isMarqued() == true) {
			sett = 1;
		}
	}
	settled *= sett;
	return settled;
}


public static void nombreintermediaire(Sommet source, Sommet destination,GraphPanel gp) {
	int comp = 0;
	if (!stack.contains(source))
		stack.push(source);
	source.setMarqued(true);
	while (true) {
		/**
		 * // tous les noeuds voisins sont marqué && destination trouvé =>  enlever tete du pile et
		 */
		if (checkVoisins(source) == 1 && trouve == true) { 
			stack.remove(source); //
			break;
		} else {
			// enlever les marques des sommets parcourues
			trouve = false;
		}
		while (trouve == false && comp < source.voisins.size()) {
			if (source == destination) {
				source.getNombreIntermediaire().put(source, 0);
				break;
			} else {
				if (source.voisins.contains(destination) == true) {
					source.getNombreIntermediaire().put(destination, 1);
					trouve = true;
					break;
				} else if (source.voisins.contains(destination) == false
						&& (destination.voisins.size() > 0)) {
					while (comp < source.voisins.size()) {
						Sommet s = (Sommet) source.voisins.get(comp);
						// stack.push(s);

						if (s.isMarqued() == false) {
							if (s.voisins.size() > 1) {
								nombreintermediaire(s, destination, gp);
								comp++;
							} else {
								comp++;
								continue;
							}
						} else {
							comp++;
							continue;
						}

					}
					continue;

				}
			}

			if (comp >= source.voisins.size() && trouve == false) {

//				System.out.println("compteur depasse nombre de voisins de  : "+ source.getNom()+ "   et destination non atteinte  :"+ destination.getNom());
//				System.out.println("source     : " + source.getNom()+ "       destination  : " + destination.getNom());
				stack.remove(source);
				break;

			}
		}
		int compteurChemin = 1;
		if (trouve == true) {
			// on remplit le path binaire de tous les sommets du stack :
			// sommets du path identifié vers la destination
			int stackindex = 0;
			int compteur = 1;
			int nbre = 1;
			//////////////////////////////////////////////////////////////////////////////////////
//			Sommet sss = (Sommet)stack.elementAt(0);
//			Vector<Sommet> vv = new Vector<>();
//			int compteurstack2 = 1;
//			while(compteurstack2 < stack.size()){
//				sss.getLocalPath().add(stack.elementAt(compteurstack2));
//				compteurstack2++;
//			}
//			if(source != destination) sss.getLocalPath().remove(sss);
//            sss.getMesAltenatives().put(compteurChemin, sss.getLocalPath());
//			sss.getMesChemins().put(destination, sss.getMesAltenatives());
			///////////////////////////////////////////////////////////////////////////:
			while (stackindex < stack.size()) {
				Sommet sommet = stack.elementAt(stackindex);
				nbre = stack.size() - (stackindex + 1);
				// on remplit le nombre de sommet intermédiaire
				if (getShortestNodePath(sommet, destination) > nbre) { 
					/**
					 * Si le nombre d'intermediaire dans le chemin calculé< nbre existant:
					 */
					Vector<Sommet> v = new Vector<Sommet>();
					sommet.getNombreIntermediaire().put(destination, nbre); 
					while (compteur < stack.size()) {
						Sommet pathNode = stack.elementAt(compteur);
						v.add(pathNode);
						compteur++;
					}
					stackindex++;
					compteur = stackindex + 1;
					
					///////////////////////////////////////////////////////////////////////::
					sommet.getChemin().put(destination,v);
                    ///////////////////////////////////////////////////////////////////////
					continue;
				}
				stackindex++;
				compteur = stackindex + 1;
			}
            compteurChemin++;
		}
		break;
	}
	 
	 if((source == stack.elementAt(0)) ){
//      for(int k = 1; k< source.getMesChemins().get(destination).size(); k++ ){
//    	  int l=1;
//    	    while(l<source.getMesChemins().get(destination).get(l).size()){
//		//	 System.out.println(source.getMesChemins().get(destination).get(l).elementAt(k).getNom() + "\n");
//			 l++;
//    	    }
//		 }
     for(int i = 0 ; i < gp.getGraphe().nombreSommets(); i++){
    	 Sommet s = gp.getGraphe().getSommet(i) ; 
    	 s.setMarqued(false);
     }
		 }
	
}
// ****************************************************************************************************************

public  void plusCourChemin4(Sommet source, Sommet destination, GraphPanel gp, double alpha ) {
    // while(true){
		double checkDistanceSource = source.getDistance().get(destination);
		while(checkDistanceSource != -1.0 && checkDistanceSource!=0 ){
		source.setChecked(true);
		if( ! stack2.contains(source) ) stack2.push(source);
		if (source.voisins.size() == 0) {
//			System.out.println("la sommet  " + source.getNom() + "   isole !!!");
			stack2.remove(source);
			source.setChecked(false);
			break;
		} else if (destination.voisins.size() == 0) {
//			System.out.println("source   " + destination.getNom()+ "    est isole !!!");
			source.getDistance().put(destination, 0.0);
			break;
		}else if(source == destination){
//			System.out.println("distance   : " + source.getNom() + "____" + source.getNom() + "    est nulle  ") ;
			source.getDistance().put(destination, 0.0);
			Vector v = new Vector<Sommet>();
			v.add(source);
         source.getPredecessors().put(destination, v);
			break ;
		} else if (source.voisins.size() > 0 && destination.voisins.size() > 0) {
			int compteurVoisin = 0;
			 //en cas ou cible trouvé ou non trouvé on doit chercher tous les possibilités
			// du noeud en cours => tester la valeur du compteur voisin
			// Dans ce cas , chaque voisin traité est checked !
			while (indicateur == false|| compteurVoisin < source.voisins.size() || checkVoisins1(source) == 0) {
				double poids = 0;                                                     
		//	if(checkVoisins1(source ) == 1) break ;          
				boolean found = false; // ceci servira a indiquer si un voisin connaisse la dest
				if (source.voisins.contains(destination)&& indicateur == false ) {
					/**
				    * stack2.push(destination);
					*
					* il faut prévoir cas de depassement du
                 * compteur , et le cas indicateur =  true chacun a part
					**/
				//	Vector<Sommet> v = new Vector<Sommet>();
					int indice = gp.getGraphe().areteDansGraphe(source,
							destination);
					//poids = 1 / gp.getGraphe().getArete(indice).getVal();
					poids = 1/Math.pow(gp.getGraphe().getArete(indice).getVal() , alpha);
					if (getShortestDistance(source, destination) >= poids) {
						source.getDistance().put(destination, poids);
						Vector<Sommet> v = new Vector<Sommet>();
						v.add(source);
					source.getPredecessors().put(destination, v);
					}
					if(stack2.size()  > 1) indicateur = true;
					
					// une fois ecrit la nouvelle valeur de distance koi faire
					// ??
				} else { 
					// recherche normal du cible :
					// 1er cas cible non repéré et pas de depassement de nombre
					// de voisin du cible
					while (((compteurVoisin < source.voisins.size())&& (indicateur == false)) || checkVoisins(source) ==0 ) {
						Sommet s = getMinimum(source, gp , stack2);
						if (s!= destination && s != null && s.voisins.size() > 1 
								&& s.getDistance().get(destination) != -1.0 && s.getDistance().get(destination) !=0) { // s est non
																	// marqué && non null*
							if(s.getDistance().get(destination) != Double.MAX_VALUE){
								indicateur = true;
								int indiceArete = gp.getGraphe().areteDansGraphe(s,
										source);
								double distance =  1/Math.pow(gp.getGraphe().getArete(indiceArete).getVal() , alpha);
								source.getDistance().put(destination, distance + s.getDistance().get(destination));
								Vector<Sommet> pred = s.getPredecessors().get(destination);
								pred.add(0, s);
								source.getPredecessors().put(destination, pred);
								found = true;
								break;
							}
							if(indicateur == true) indicateur = false;										
							plusCourChemin4(s, destination, gp, alpha); 
							compteurVoisin++;
						} else {
							// cas ou tous les sommets voisins sont marqués ou
							// que s a un seul voisin : on passe au voisin
							// suivant
							if(s != null){
							s.setChecked(true);
							//source.setChecked(true);
							compteurVoisin++;
							continue;
							}else{
								break;
							}
						}
						if(checkVoisins1(source) == 0){
							continue;
						}
					}
				}
				if(found == true) break;
				if (indicateur == true ) {
					/**
					 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
					 *  puis on continue l'execution pour le reste des voisins du source 
					 */
					if(stack2.size() == 1)
						break;
					int compteurStack = 0;
					
					
					
					
					
					
					
					Vector<Sommet> v = new Vector<Sommet>();
					double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);
					int index0 = 0, index1 = 1;
					Sommet sommet = (Sommet) stack2.elementAt(0);
					while (index1 <stack2.size()) {
						Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
						Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
						if (gp.getGraphe().distance(sommet1, sommet2) != -1)
							somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
						v.add(sommet2);
							index0++;
						index1++;
					}
					
					if (getShortestDistance(sommet, destination) > somme || 
							(getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
						
						
						sommet.getDistance().put(destination, somme);
						sommet.getPredecessors().put(destination, v);
						destination.getDistance().put(source, somme);
						destination.getPredecessors().put(source, v);
						
					}
					
       
					
					
					
					
					
					
					
					
					
					
					
					
					
//					while (compteurStack < stack2.size()) {        // 
//						double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);///
//						Vector<Sommet> v = new Vector<Sommet>(); // path  = { sommet contenus dans le stack2}
//						int index0 = 0, index1 = 1;
//						Sommet sommet = (Sommet) stack2.elementAt(compteurStack);
//						while (index1 <stack2.size()) {
//							Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
//							Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
//							if (gp.getGraphe().distance(sommet1, sommet2) != -1)
//								somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
//							v.add(sommet2);
//							index0++;
//							index1++;
//						}
//
//						if (getShortestDistance(sommet, destination) > somme || (getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
//						sommet.getDistance().put(destination, somme);
//						sommet.getPredecessors().put(destination, v);
//						}
//						compteurStack++;
//					}
					compteurVoisin++;
					indicateur = true;
					continue;
				}
                break;
			}
		}
		
		indicateur = false;
		break;
		
	}
if(stack2.size() >0){
if (stack2.elementAt(0) == source){
		// initialiser les sommets trace du parcours de recherche du destination traité 
     for(int i = 0 ; i< gp.getGraphe().sommets.size() ; i++){
     	Sommet s  = (Sommet) gp.getGraphe().sommets.elementAt(i);
     	s.setChecked(false);
     }
   }
}
if(indicateur == false ){
	stack2.remove(source);
}

	}
	


//public  void plusCourChemin2(Sommet source, Sommet destination, GraphPanel gp, double alpha ) {
//    // while(true){
//		double checkDistanceSource = source.getDistance().get(destination);
//		while(checkDistanceSource != -1.0 && checkDistanceSource!=0 ){
//		source.setChecked(true);
//		if( ! stack2.contains(source) ) stack2.push(source);
//		if (source.voisins.size() == 0) {
//			System.out.println("la sommet  " + source.getNom() + "   isole !!!");
//			stack2.remove(source);
//			source.setChecked(false);
//			break;
//		} else if (destination.voisins.size() == 0) {
//			System.out.println("source   " + destination.getNom()+ "    est isole !!!");
//			source.getDistance().put(destination, 0.0);
//			break;
//		}else if(source == destination){
//			System.out.println("distance   : " + source.getNom() + "____" + source.getNom() + "    est nulle  ") ;
//			source.getDistance().put(destination, 0.0);
//			Vector v = new Vector<Sommet>();
//			v.add(source);
//         source.getPredecessors().put(destination, v);
//			break ;
//		} else if (source.voisins.size() > 0 && destination.voisins.size() > 0) {
//			int compteurVoisin = 0;
//			 //en cas ou cible trouvé ou non trouvé on doit chercher tous les possibilités
//			// du noeud en cours => tester la valeur du compteur voisin
//			// Dans ce cas , chaque voisin traité est checked !
//			while (indicateur == false|| compteurVoisin < source.voisins.size() || checkVoisins1(source) == 0) {
//				double poids = 0;                                                     
//		//	if(checkVoisins1(source ) == 1) break ;          
//				boolean found = false; // ceci servira a indiquer si un voisin connaisse la dest
//				if (source.voisins.contains(destination)&& indicateur == false ) {
//					/**
//				    * stack2.push(destination);
//					*
//					* il faut prévoir cas de depassement du
//                 * compteur , et le cas indicateur =  true chacun a part
//					**/
//				//	Vector<Sommet> v = new Vector<Sommet>();
//					int indice = gr.areteDansGraphe(source,
//							destination);
//					//poids = 1 / gp.getGraphe().getArete(indice).getVal();
//					poids = 1/Math.pow(gr.getArete(indice).getVal() , alpha);
//					if (getShortestDistance(source, destination) >= poids) {
//						source.getDistance().put(destination, poids);
//						Vector<Sommet> v = new Vector<Sommet>();
//						v.add(source);
//					source.getPredecessors().put(destination, v);
//					}
//					if(stack2.size()  > 1) indicateur = true;
//					
//					// une fois ecrit la nouvelle valeur de distance koi faire
//					// ??
//				} else { 
//					// recherche normal du cible :
//					// 1er cas cible non repéré et pas de depassement de nombre
//					// de voisin du cible
//					while (((compteurVoisin < source.voisins.size())&& (indicateur == false)) || checkVoisins(source) ==0 ) {
//						Sommet s = getMinimum(source, gp);
//						if (s!= destination && s != null && s.voisins.size() > 1 
//								&& s.getDistance().get(destination) != -1.0 && s.getDistance().get(destination) !=0) { // s est non
//																	// marqué && non null*
//							if(s.getDistance().get(destination) != Double.MAX_VALUE){
//								indicateur = true;
//								int indiceArete = gr.areteDansGraphe(s,
//										source);
//								double distance =  1/Math.pow(gr.getArete(indiceArete).getVal() , alpha);
//								source.getDistance().put(destination, distance + s.getDistance().get(destination));
//								Vector<Sommet> pred = s.getPredecessors().get(destination);
//								pred.add(0, s);
//								source.getPredecessors().put(destination, pred);
//								found = true;
//								break;
//							}
//							if(indicateur == true) indicateur = false;										
//							plusCourChemin2(s, destination, gp, alpha); 
//							compteurVoisin++;
//						} else {
//							// cas ou tous les sommets voisins sont marqués ou
//							// que s a un seul voisin : on passe au voisin
//							// suivant
//							if(s != null){
//							s.setChecked(true);
//							//source.setChecked(true);
//							compteurVoisin++;
//							continue;
//							}else{
//								break;
//							}
//						}
//						if(checkVoisins1(source) == 0){
//							continue;
//						}
//					}
//				}
//				if(found == true) break;
//				if (indicateur == true) {
//					/**
//					 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
//					 *  puis on continue l'execution pour le reste des voisins du source 
//					 */
//					if(stack2.size() == 1)
//						break;
//					int compteurStack = 0;
//					
//					
//					
//					
//					
//					
//					
//					Vector<Sommet> v = new Vector<Sommet>();
//					double somme = Math.pow(gr.distance(source, destination) , alpha);
//					int index0 = 0, index1 = 1;
//					Sommet sommet = (Sommet) stack2.elementAt(0);
//					while (index1 <stack2.size()) {
//						Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
//						Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
//						if (gr.distance(sommet1, sommet2) != -1)
//							somme += Math.pow(gr.distance(sommet1,sommet2), alpha);
//						v.add(sommet2);
//							index0++;
//						index1++;
//					}
//					
//					if (getShortestDistance(sommet, destination) > somme || 
//							(getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
//						sommet.getDistance().put(destination, somme);
//						sommet.getPredecessors().put(destination, v);
//						destination.getDistance().put(source, somme);
//						destination.getPredecessors().put(source, v);
//						}
//					stack2.remove(0);
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
////					while (compteurStack < stack2.size()) {        // 
////						double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);///
////						Vector<Sommet> v = new Vector<Sommet>(); // path  = { sommet contenus dans le stack2}
////						int index0 = 0, index1 = 1;
////						Sommet sommet = (Sommet) stack2.elementAt(compteurStack);
////						while (index1 <stack2.size()) {
////							Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
////							Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
////							if (gp.getGraphe().distance(sommet1, sommet2) != -1)
////								somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
////							v.add(sommet2);
////							index0++;
////							index1++;
////						}
////
////						if (getShortestDistance(sommet, destination) > somme || (getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
////						sommet.getDistance().put(destination, somme);
////						sommet.getPredecessors().put(destination, v);
////						}
////						compteurStack++;
////					}
//					compteurVoisin++;
//					indicateur = true;
//					continue;
//				}
//                break;
//			}
//		}
//		
//		indicateur = false;
//		break;
//		
//	}
//if(stack2.size() >0){
//if (stack2.elementAt(0) == source){
//		// initialiser les sommets trace du parcours de recherche du destination traité 
//     for(int i = 0 ; i< gr.sommets.size() ; i++){
//     	Sommet s  = (Sommet) gr.sommets.elementAt(i);
//     	s.setChecked(false);
//     }
//   }
//}
//if(indicateur == false ){
//	stack2.remove(source);
//}
//
//	}
	





public void plusCourtChemin1(Sommet source , Sommet destination , GraphPanel gp , double alpha){
	double checkDistanceSource = source.getDistance().get(destination);
	while(checkDistanceSource != -1.0 && checkDistanceSource!=0 ){
	source.setChecked(true);
	if( ! stack2.contains(source) ) stack2.push(source);
	if (source.voisins.size() == 0) {
		System.out.println("la sommet  " + source.getNom() + "   isole !!!");
		stack2.remove(source);
		source.setChecked(false);
		break;
	} else if (destination.voisins.size() == 0) {
		System.out.println("source   " + destination.getNom()+ "    est isole !!!");
		source.getDistance().put(destination, 0.0);
		break;
	}else if(source == destination){
		System.out.println("distance   : " + source.getNom() + "____" + source.getNom() + "    est nulle  ") ;
		source.getDistance().put(destination, 0.0);
		Vector v = new Vector<Sommet>();
		v.add(source);
     source.getPredecessors().put(destination, v);
		break ;
	} else if (source.voisins.size() > 0 && destination.voisins.size() > 0) {
		int compteurVoisin = 1;
		while (indicateur == false|| compteurVoisin < source.voisins.size() || checkVoisins1(source) == 0) {
			double poids = 0;                                                               
			boolean found = false;
			
			if (source.voisins.contains(destination)&& indicateur == false ) { ////////////////////////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				int indice = gp.getGraphe().areteDansGraphe(source,destination);
				poids = 1/Math.pow(gp.getGraphe().getArete(indice).getVal() , alpha);
				if (getShortestDistance(source, destination) >= poids) {
					source.getDistance().put(destination, poids);
					Vector<Sommet> v = new Vector<Sommet>();
					v.add(source);
				source.getPredecessors().put(destination, v);
				}
				if(stack2.size()  > 1) indicateur = true;/////////////////////////////////////////////////:$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			} else { 
				
				
				
			
				stackVoisin = new Stack<Sommet>();				
				
				
				

				while (((compteurVoisin <= source.voisins.size())&& (indicateur == false)) || checkVoisins1(source) ==0 ) {
					Sommet s = getMinimum(source, gp, stack2);
					if(s != null)
					System.out.println("appel de minimum  : "   + source.getNom()+"  ------> "+ s.getNom() );
					if ( s != null && s.voisins.size() > 1 && s != destination ) { 
						if(s.getDistance().get(destination) != Double.MAX_VALUE &&  ! s.getPredecessors().get(destination).contains(source)){
							indicateur = true;
							int indiceArete = gp.getGraphe().areteDansGraphe(s,
									source);
							double distance =  1/Math.pow(gp.getGraphe().getArete(indiceArete).getVal() , alpha);
							source.getDistance().put(destination, distance + s.getDistance().get(destination));
							Vector<Sommet> pred = s.getPredecessors().get(destination);
							pred.add(0, s);
							source.getPredecessors().put(destination, pred);
							found = true;
							break;
						}
						
						if(indicateur == true) indicateur = false;										
						plusCourtChemin1(s, destination, gp, alpha); 
						compteurVoisin++;
					} else {

						if(s != null){
						s.setChecked(true);
						compteurVoisin++;
						continue;
						}else{
							break;
						}
					}
				
					if(checkVoisins1(source) == 0){
						continue;
					}
				}
			}
			if(found == true) break;     /////////////////////////////////////////////////////////////////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			if (indicateur == true ) {
				/**
				 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
				 *  puis on continue l'execution pour le reste des voisins du source 
				 */
				if(stack2.size() == 1)
					break;
				int compteurStack = 0;
				
				
				
				
				// simplifier recursivité !!!!!!!!!!
				
				
				Vector<Sommet> v = new Vector<Sommet>();
				double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);
				int index0 = 0, index1 = 1;
				Sommet sommet = (Sommet) stack2.elementAt(0);
				while (index1 <stack2.size()) {
					Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
					Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
					if (gp.getGraphe().distance(sommet1, sommet2) != -1)
						somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
					v.add(sommet2);
						index0++;
					index1++;
				}
				
				if (getShortestDistance(sommet, destination) > somme || 
						(getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
					
					
					sommet.getDistance().put(destination, somme);
					sommet.getPredecessors().put(destination, v);
					destination.getDistance().put(source, somme);
					destination.getPredecessors().put(source, v);
					
				}
				compteurVoisin++;
				indicateur = true;
				continue;
			}
            break;
		}
	}
	
	indicateur = false;
	break;
	
}
if(stack2.size() >0){
if (stack2.elementAt(0) == source ){
	// initialiser les sommets trace du parcours de recherche du destination traité 
 for(int i = 0 ; i< gp.getGraphe().sommets.size() ; i++){
 	Sommet s  = (Sommet) gp.getGraphe().sommets.elementAt(i);
 	s.setChecked(false);
 }
}
}
if(indicateur == false ){
stack2.remove(source);
}


	
	
	
	
	
	
	
	
}
public  void plusCourChemin(Sommet source, Sommet destination, GraphPanel gp, double alpha ) {
		double checkDistanceSource = source.getDistance().get(destination);
		while(checkDistanceSource != -1.0 && checkDistanceSource!=0 ){
		source.setChecked(true);
		if( ! stack2.contains(source) ) stack2.push(source);
		if (source.voisins.size() == 0) {
			System.out.println("la sommet  " + source.getNom() + "   isole !!!");
			stack2.remove(source);
			source.setChecked(false);
			break;
		} else if (destination.voisins.size() == 0) {
			System.out.println("source   " + destination.getNom()+ "    est isole !!!");
			source.getDistance().put(destination, 0.0);
			break;
		}else if(source == destination){
			System.out.println("distance   : " + source.getNom() + "____" + source.getNom() + "    est nulle  ") ;
			source.getDistance().put(destination, 0.0);
			Vector v = new Vector<Sommet>();
			v.add(source);
         source.getPredecessors().put(destination, v);
			break ;
		} else if (source.voisins.size() > 0 && destination.voisins.size() > 0) {
			int compteurVoisin = 1;
			 //en cas ou cible trouvé ou non trouvé on doit chercher tous les possibilités
			// du noeud en cours => tester la valeur du compteur voisin
			// Dans ce cas , chaque voisin traité est checked !
			while (indicateur == false|| compteurVoisin < source.voisins.size() || checkVoisins1(source) == 0) {
				double poids = 0;                                                     
		//	if(checkVoisins1(source ) == 1) break ;          
				boolean found = false; // ceci servira a indiquer si un voisin connaisse la dest
				if (source.voisins.contains(destination)&& indicateur == false ) { ////////////////////////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
					/**
				    * stack2.push(destination);
					*
					* il faut prévoir cas de depassement du
                 * compteur , et le cas indicateur =  true chacun a part
					**/
				//	Vector<Sommet> v = new Vector<Sommet>();
					int indice = gp.getGraphe().areteDansGraphe(source,
							destination);
					//poids = 1 / gp.getGraphe().getArete(indice).getVal();
					poids = 1/Math.pow(gp.getGraphe().getArete(indice).getVal() , alpha);
					if (getShortestDistance(source, destination) >= poids) {
						source.getDistance().put(destination, poids);
						Vector<Sommet> v = new Vector<Sommet>();
						v.add(source);
					source.getPredecessors().put(destination, v);
					}
					if(stack2.size()  > 1) indicateur = true;/////////////////////////////////////////////////:$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
					
					// une fois ecrit la nouvelle valeur de distance koi faire
					// ??
				} else { 
					// recherche normal du cible :
					// 1er cas cible non repéré et pas de depassement de nombre
					// de voisin du cible
					
					
					System.out.println("Source   :  " +  source.getNom());
					System.out.println("destination    :  " +  destination.getNom());
					while (((compteurVoisin < source.voisins.size())&& (indicateur == false)) || checkVoisins(source) ==0 ) {
						
						Sommet s = getMinimum(source, gp , stack2);

						if(s != null)
				///		System.out.println("minimum  : " + s.getNom() );
						if (s!= destination && s != null && s.voisins.size() > 1 ) { // s est non
																	// marqué && non null*
							if(s.getDistance().get(destination)  != null && s.getDistance().get(destination)!= Double.MAX_VALUE &&   s.getPredecessors().get(destination) != null &&
									!s.getPredecessors().get(destination).contains(source)){
								indicateur = true;
								int indiceArete = gp.getGraphe().areteDansGraphe(s,
										source);
								double distance =  1/Math.pow(gp.getGraphe().getArete(indiceArete).getVal() , alpha);
								source.getDistance().put(destination, distance + s.getDistance().get(destination));
								Vector<Sommet> pred = s.getPredecessors().get(destination);
								pred.add(0, s);
								source.getPredecessors().put(destination, pred);
								found = true;
								break;
							}
							if(indicateur == true) indicateur = false;										
							plusCourChemin(s, destination, gp, alpha); 
							compteurVoisin++;
						} else {
							// cas ou tous les sommets voisins sont marqués ou
							// que s a un seul voisin : on passe au voisin
							// suivant
							if(s != null){
							s.setChecked(true);
							//source.setChecked(true);
							compteurVoisin++;
							continue;
							}else{
								break;
							}
						}
						if(checkVoisins1(source) == 0){
							continue;
						}
					}
				}
				if(found == true) break;     /////////////////////////////////////////////////////////////////$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				if (indicateur == true ) {
					/**
					 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
					 *  puis on continue l'execution pour le reste des voisins du source 
					 */
					if(stack2.size() == 1)
						break;
					int compteurStack = 0;
					
					
					
					
					// simplifier recursivité !!!!!!!!!!
					
					
					Vector<Sommet> v = new Vector<Sommet>();
					double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);
					int index0 = 0, index1 = 1;
					Sommet sommet = (Sommet) stack2.elementAt(0);
					while (index1 <stack2.size()) {
						Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
						Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
						if (gp.getGraphe().distance(sommet1, sommet2) != -1)
							somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
						v.add(sommet2);
							index0++;
						index1++;
					}
					
					if (getShortestDistance(sommet, destination) > somme || 
							(getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
						
						
						sommet.getDistance().put(destination, somme);
						sommet.getPredecessors().put(destination, v);
						destination.getDistance().put(source, somme);
						destination.getPredecessors().put(source, v);
						
					}
					
       
					
					
					
					
					
					
					
					
					
					
					
					
					
//					while (compteurStack < stack2.size()) {        // 
//						double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);///
//						Vector<Sommet> v = new Vector<Sommet>(); // path  = { sommet contenus dans le stack2}
//						int index0 = 0, index1 = 1;
//						Sommet sommet = (Sommet) stack2.elementAt(compteurStack);
//						while (index1 <stack2.size()) {
//							Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
//							Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
//							if (gp.getGraphe().distance(sommet1, sommet2) != -1)
//								somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
//							v.add(sommet2);
//							index0++;
//							index1++;
//						}
//
//						if (getShortestDistance(sommet, destination) > somme || (getShortestDistance(sommet, destination) >= somme)&&(sommet.getPredecessors().get(destination).size() > v.size()) ) {
//						sommet.getDistance().put(destination, somme);
//						sommet.getPredecessors().put(destination, v);
//						}
//						compteurStack++;
//					}
					compteurVoisin++;
					indicateur = true;
					continue;
				}
                break;
			}
		}
		
		indicateur = false;
		break;
		
	}
if(stack2.size() >0){
if (stack2.elementAt(0) == source ){
		// initialiser les sommets trace du parcours de recherche du destination traité 
     for(int i = 0 ; i< gp.getGraphe().sommets.size() ; i++){
     	Sommet s  = (Sommet) gp.getGraphe().sommets.elementAt(i);
     	s.setChecked(false);
     }
   }
}
if(indicateur == false ){
	stack2.remove(source);
}

	}
	

/**
 * Si retourne 1 alors  tous les voisins sont checked Si non il Existe un voisin non checked (Distance)
 * @param node
 * @return
 */

public int checkVoisins1(Sommet node) {
	int settled = 1;
	int sett = 1;
	for (int i = 0; i < node.voisins.size(); i++) {
		Sommet s = (Sommet) node.voisins.elementAt(i);
		if (s.isChecked() == true) {
			sett = 1;
			settled *= sett;

		}else{
			sett = 0;
			settled *= sett;

		}
			
	}
	return settled;
}


public int voisinTest(Stack<Sommet> stack , Sommet source){
	int trouve = 1;
	for(int j = 1; j< stack.size() ; j++){
		Sommet stackNode = (Sommet) stack.elementAt(j);
		
		System.out.println("noeud du stack  : "  +  stackNode.getNom());
		if(source != null && !stackNode.voisins.contains(source)){
		 trouve *= 1;
		}else{
			trouve = 0;
		}
	}
	return trouve;
}
/**
 * 
 * @param sommet
 * @param gp
 * @return la sommet la plus proche du source non marqué(en inverse de la valeur du
 *         lien )
 */
public Sommet getMinimum(Sommet sommet, GraphPanel gp, Stack<Sommet> stack) {
	Vector<Sommet> v = new Vector<Sommet>();
	v = sommet.voisins;
	Sommet minimum = null;
	double valeur = Double.MAX_VALUE;

	int i = 0 ; 
	if(v != null){
	while ( i < v.size()) {
		Sommet s = (Sommet ) v.elementAt(i);
		//System.out.println(s.getNom()+" => "  + s.isChecked() );
//		if (s.isChecked() == false && voisinTest(stack, s) == 1) {
		
		if (s.isChecked() == false){
			int index = gp.getGraphe().areteDansGraphe(sommet, s);
			double poids = 1 / gp.getGraphe().getArete(index).getVal();
			if(poids <= valeur) {
				valeur = poids;
				minimum = s;
			}
			i++;
	}else{
		i++;
		continue;
	}
		
		
	}
	}
	
	
	return minimum;
}
/**
 * 
 * @param source
 * @param destination
 * @return Retourne la valeur existante de la distance source/destination
 *         dans le map distance du source
 */
public double getShortestDistance(Sommet source, Sommet destination) {
	return source.getDistance().get(destination);
}
//*************************************************************************************************************************
/**
 * créer la matrice des distances du graphe 
 */
public void initTable(GraphPanel gp , String table[][]){
	int n = gp.getGraphe().nombreSommets();
	for(int i = 0; i < n; i++){
		Sommet sommet_i = gp.getGraphe().getSommet(i);
		table[i][0] = sommet_i.getNom();

	}
		for(int j = 0; j<n ; j++){
			Sommet sommet_j = gp.getGraphe().getSommet(j);
		table[0][j] = sommet_j.getNom();
		
		}
	for(int i = 1; i< n ; i++){
		for(int j = 1; j< n ; j++){
			table[i][j] = "0";
		}

	}
}

public void saveIntoFile(String tab[][] , int n ,String fileName) throws IOException{	
	 BufferedWriter writer = null;
	    try {
	        writer = new BufferedWriter(new FileWriter(fileName));
			for (int i = 0; i < n; i++) {
				 String lineTable = "";
				for (int j = 0; j < n; j++) {
					lineTable += tab[i][j]+" ";
				}
				lineTable += "\n";
			//	System.out.println("ligne :" + i + lineTable  );
				
				writer.write(lineTable);
				writer.newLine();
				writer.flush();
			}
	    } catch(IOException ex) {
	        ex.printStackTrace();
	    } finally{
	        if(writer!=null){
	            writer.close();
	        }  
	    }
}

/**
 * créer ,s'il n'existe pas , le fichier distance 
 * @param gp
 * @param alpha
 */
public synchronized void loadComputeDistance(GraphPanel gp, Double alpha, String filename){	
	try {
		String pathName1 = filename;
         File f = new File(pathName1);
         if(f.exists() == true ){
             f.delete();
         } 
         if(f.exists() == false){ 
         int n = gp.getGraphe().nombreSommets();
         String tabDistance[][] = new String[n][n];
         String tabPred[][] = new String[n][n];
         initDistance(gp);
         initTable(gp, tabDistance);
         
     	for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
     		Sommet sommet_i = (Sommet) gp.getGraphe().sommets.get(i);
//     		System.out.println("Source  : "  + sommet_i.getNom());
     		if(sommet_i.voisins.size() > 0 ){
     		for ( int j = 0; j <gp.getGraphe().nombreSommets(); j++) {
     		stack2 = new Stack<Sommet>();
     		Sommet sommet_j = (Sommet) gp.getGraphe().sommets.get(j);
//     		System.out.println("Destination  : "  + sommet_j.getNom());
     		if(sommet_j.voisins.size() > 0){
     		plusCourChemin4(sommet_i, sommet_j, gp, alpha);
     		if(sommet_i.getDistance().get(sommet_j) == Double.MAX_VALUE && sommet_j.getDistance().get(sommet_i) == Double.MAX_VALUE){
     			sommet_i.getDistance().put(sommet_j, -1.0);
     			sommet_j.getDistance().put(sommet_i, -1.0);
     			sommet_i.getPredecessors().put(sommet_j, null);
     			sommet_j.getPredecessors().put(sommet_i, null);
     		}
     		
//     		System.out.println(" distance   :" +sommet_i.getNom() + "--->" +sommet_j.getNom() + "   est   " + sommet_i.getDistance().get(sommet_j));
//     		if(sommet_i.getDistance().get(sommet_j) != -1 && sommet_i.getDistance().get(sommet_j) != Double.MAX_VALUE){
//     			for(int k = 0; k<sommet_i.getPredecessors().get(sommet_j).size(); k++){
//     	     		System.out.println(sommet_i.getPredecessors().get(sommet_j).get(k).getNom() + "\n");
//     	     		}
//     		}
//     		System.out.println("chemin : " + sommet_i.getNom()+"   -->   "+sommet_j.getNom() + "   est :" );
//     		if(sommet_i.getDistance().get(sommet_j) != -1){
//     		for(int k = 0; k<sommet_i.getPredecessors().get(sommet_j).size(); k++){
//     		System.out.println(sommet_i.getPredecessors().get(sommet_j).get(k).getNom() + "\n");
//     		}
//     		}
     		}
//     		else{
//     			System.out.println("Sommet  :"  + sommet_j.getNom() + "   est une destination isolé !");
//     		}
     	}
     	
     	}
//     		else{
//     		System.out.println("Sommet  :"  + sommet_i.getNom() + "   est isolé !");
//     	}
     		
     	}		
     	System.out.println("fin calcul distance");
     		//initialisation tableau tabDistance
				for (int i = 0; i < n; i++) {
					Sommet sommet_i = gp.getGraphe().getSommet(i);
					if (tabDistance[i][0] == sommet_i.getNom()) {
						for (int j = 1; j < n; j++) {
							Sommet sommet_j = gp.getGraphe().getSommet(j);
							double value = sommet_i.getDistance().get(sommet_j);
							tabDistance[i][j] = String.valueOf(Math.round(value));
							if (tabDistance[i][j].equals("-1.0") == true|| tabDistance[i][j].equals("-1") == true || Double.valueOf(tabDistance[i][j]) >= Long.MAX_VALUE) {
								tabDistance[i][j] = "0";
							}
						
						}
					}
				}
				for (int i = 0; i < n; i++) {
					Sommet sommet_i = gp.getGraphe().getSommet(i);
					if (tabPred[i][0] == sommet_i.getNom()) {
						for (int j = 0; j < n; j++) {
							Sommet sommet_j = gp.getGraphe().getSommet(j);
							
							
							
							double value = sommet_i.getDistance().get(sommet_j);
							tabDistance[i][j] = String.valueOf(Math.round(value));
							if (tabDistance[i][j].equals("-1.0") == true || Double.valueOf(tabDistance[i][j]) >= Long.MAX_VALUE) {
								tabDistance[i][j] = "0";
							}
							
							
							
							
							
						
						}
					}
				}
        
        saveIntoFile(tabDistance, n, pathName1);
        
         }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	
			
			gp.initNombreNoeudintermediaire(gp.getGraphe());
    		for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
    			Sommet sommet_i = (Sommet) gp.getGraphe().sommets.get(i);
    			if(sommet_i.voisins.size()>0){
    			 for ( int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
    		    stack = new Stack<Sommet>();
    			Sommet sommet_j = (Sommet) gp.getGraphe().sommets.get(j);
//    			System.out.println("source  :" + sommet_i.getNom() +"------->"+ sommet_j.getNom());
    			 if(sommet_i.getNombreIntermediaire().get(sommet_j) == Integer.MAX_VALUE){
    			 if(sommet_j.voisins.size() > 0 ){
    			 nombreintermediaire(sommet_i, sommet_j, gp);
    			 sommet_j.getNombreIntermediaire().put(sommet_i, sommet_i.getNombreIntermediaire().get(sommet_j));
//    			 System.out.println("nombre interm");
//    			 System.out.println( sommet_i.getNom() +" ----> " +sommet_j.getNom()+ "  : " +  sommet_i.getNombreIntermediaire().get(sommet_j));
    			 }
//    			 else{
//    				 System.out.println(sommet_j.getNom()+ "  est isolé !! ") ;	 
//    			 }
    			 }
    			 }
    			 }
//    			System.out.println( "                          ***** fin nombre intermediaire ******                                ");
    			 }
			
			
			
			
			
			
			
}


public void retrieveDistanceFromFile(GraphPanel gp, String tab[][]  , String fileName){
	initTable(gp, tab);
	try {
		
		Reader rd_result = new FileReader(fileName);
		/**
		 * ligne du fichier
		 */
		String ligne_doc = null;
		/**
		 * compteur ligne
		 */
		LineNumberReader line = new LineNumberReader(rd_result);
		ligne_doc = line.readLine();
		/**
		 * tokenizer
		 */
		StringTokenizer labc = new StringTokenizer(ligne_doc);
		/**
		 * token
		 */
		String lab = labc.nextToken();
		tab[0][0] = "";
		int i=1;
		//initialise le tableau avec les noms des sommets qui existent dans fichiers
		while (lab != null && !lab.equals("\n")) {
			
		}
		
			
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}

/**
 * retrives measures from file into table 
 * @param gp
 * @param tab
 * @param alpha
 */
public void retrieveMesuresFromFile(GraphPanel gp ,String tab[][]){
int n = gp.getGraphe().nombreSommets();
tab[0][0] = "";
tab[0][1] = "Degree";
tab[0][2] = "Strength";
tab[0][3] = "Intermediarity";
tab[0][4] = "Proximity";
tab[0][5] = "Gen Degree " ;
tab[0][5] = "Gen Intermdiarity ";
tab[0][5] = "Gen Proximity";

    try {
		File file = new File("bilanMesure");
		Reader rd_result = new FileReader(file);
		String ligne_doc = null;
		LineNumberReader line = new LineNumberReader(rd_result);
		ligne_doc = line.readLine();

		StringTokenizer labc = new StringTokenizer(ligne_doc);
		String lab = labc.nextToken();
		// Lecture en colonne des mesures
		while ((ligne_doc = line.readLine()) != null) {
			labc = new StringTokenizer(ligne_doc);
			lab = labc.nextToken();
            while(lab != "\n"){
            	
            }
		
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
for(int i = 1; i< n; i++){
	Sommet sommet_i = gp.getGraphe().getSommet(i);
	tab[i][0] = sommet_i.getNom();
	for(int j = 1; j < 7 ; j++){
		tab[i][j] = "0.0"; 
	}
}

}
public void computeMeasures(GraphPanel gp, double alpha) throws IOException{

gp.degreSimple(gp.getGraphe());
//gp.degree_gen(gp.getGraphe() , alpha);
//gp.betweennessFreeMan(gp);
//gp.betweennessCentralityGen(gp, alpha);
//gp.closenessCentralityfreeMan(gp, alpha);
//gp.closenessCentralityGen(gp, alpha);


//verif existence du fichier !

int n = gp.getGraphe().nombreSommets();
File f = new File("bilanMesure");

if(f.exists() == true ){
    f.delete();
} 
BufferedWriter writer = null;
try {
    writer = new BufferedWriter(new FileWriter(f));
    String line  = "  " + " Degree" + " " + "Strength" + " " + "Intermediarity"  + " "+ "Proximity" + " " + "Gen Degree" + " " + 
                   "Gen Intermediarity" + "\n";
    writer.write(line);
	writer.newLine();
	writer.flush();
    
	for (int i = 0; i < n; i++) {
		 Sommet sommet_i = gp.getGraphe().getSommet(i);
		 String ligne= sommet_i.getNom()+ " " + String.valueOf(sommet_i.getCentralite_degre()) + " " + String.valueOf(sommet_i.getCentralite_poids()) + " " + String.valueOf(sommet_i.getBetweennessCentrality()) + " " + 
				String.valueOf(sommet_i.getClosenessCentrality()) + " " + String.valueOf(sommet_i.getCentralite_combine()) + " " + String.valueOf(sommet_i.getBetweennessGenCentrality()) + " " 
				+ String.valueOf(sommet_i.getClosenessGenCentrality()) + "\n " ;
		writer.write(ligne);
		writer.newLine();
		writer.flush();
	}
} catch(IOException ex) {
    ex.printStackTrace();
} finally{
    if(writer!=null){
        writer.close();
    }  
}


}
public void computePeriodicMeasures(GraphPanel gp, double alpha ,String fileName , int instance) throws IOException{

gp.degreSimple(gp.getGraphe());
gp.degree_gen(gp.getGraphe() , alpha);
gp.betweennessFreeMan(gp);
gp.betweennessCentralityGen(gp, alpha);
gp.closenessCentralityfreeMan(gp, alpha);


//verif existence du fichier !

int n = gp.getGraphe().nombreSommets();
File f = new File("bilanMesure");

if(f.exists() == true ){
    f.delete();
} 
BufferedWriter writer = null;
try {
    writer = new BufferedWriter(new FileWriter(f));
    String line  = "  " + " Degree" + " " + "Strength" + " " + "Intermediarity"  + " "+ "Proximity" + " " + "Gen Degree" + " " + 
                   "Gen Intermediarity" +  "\n";
    writer.write(line);
	writer.newLine();
	writer.flush();
    
	for (int i = 0; i < n; i++) {
		 Sommet sommet_i = gp.getGraphe().getSommet(i);
		 String ligne= sommet_i.getNom()+ " " + String.valueOf(sommet_i.getCentralite_degre()) + " " + String.valueOf(sommet_i.getCentralite_poids()) + " " + String.valueOf(sommet_i.getBetweennessCentrality()) + " " + 
				String.valueOf(sommet_i.getClosenessCentrality()) + " " + String.valueOf(sommet_i.getCentralite_combine()) + " " + String.valueOf(sommet_i.getBetweennessGenCentrality()) + " " 
				+ String.valueOf(sommet_i.getClosenessGenCentrality()) + "\n " ;
		writer.write(ligne);
		writer.newLine();
		writer.flush();
	}
} catch(IOException ex) {
    ex.printStackTrace();
} finally{
    if(writer!=null){
        writer.close();
    }  
}

}

	public static void main(String[] argv) {

		a0 = argv[0];
		a1 = argv[1];
		a2 = argv[2];
		a3 = argv[3];
		String a = a0;

		
		
		
		/* pour obtenir le chemin d'acces */
		int top = 0;
		int top2 = 0;
		int cpt = 0;
		boolean trouve = false;
		for (int i = a.length() - 1; i > 0; i--) {
			char c = a.charAt(i);
			if ((cpt == 1) && (c == '/') && (trouve == true)) {
				top2 = i;
				cpt += 1;
			}
			if ((c == '/') && (trouve == false)) {
				top = i;
				trouve = true;
				cpt += 1;
			}
		}

		param1 = a0;
		param2 = a0.substring(top + 1);
		param3 = "1";
		param5 = a0.substring(0, top) + a0.substring(top2, top) + ".not";
		param6 = a0.substring(0, top + 1) + "basededonnees";
		param7 = a0.substring(0, top) + "/" + param2.substring(0, 2) + ".Syn";
		param_sauv = param5;
		param_transit = a0.substring(0, top);
		int cpt2 = 0;
		for (int i = 0; i < a.length(); i++) {
			char b = a.charAt(i);
			if ((b == '/') && (cpt2 < 3)) {
				cpt2 += 1;
				top = i;
			}
		}

		param9 = a0.substring(0, top) + "/resultats/temporaire";

		// System.out.println("Les parametres sont : \n*********************\n param1 :"+param1+"\n param2 :"+param2+"\nparam3 :"+param3+"\nparam5 :"+param5+"\nparam6 :"+param6+"\nparam7 :"+param7+"\nparam9 :"+param9);
		/* fin recherche chemin d'acces */

		int type = Integer.parseInt(argv[2]);
		int periode = Integer.parseInt(argv[3]);
		Graphe gr = new Graphe(false);
		/**
		 * type : 0 -> matrice asymétrique 1 -> matrice symétrique 2 -> matrice
		 * symétrique temporelle 3 -> matrice asymétrique temporelle
		 */
		gr.enregistre(argv[1], periode);
		if (type == 1 || type == 2) {
			{
				gr.ChargerMatriceSymetrique(argv[1], periode);
			}
		}
		if (type == 0 || type == 3)
			gr.ChargerMatriceAsymetrique(argv[1], periode);

		GraphPanel tgr2 = new GraphPanel(gr, true);
		tgr2.placementOriente(gr);
		StringTokenizer chemin = new StringTokenizer(argv[0], "/");
		StringTokenizer chemin2 = new StringTokenizer(argv[1], "/");
		String repertoire = null;
		String fichier2 = null;
		gr.typeMat = type;
		while (chemin.hasMoreTokens())
			repertoire = chemin.nextToken();

		while (chemin2.hasMoreTokens())
			fichier2 = chemin2.nextToken();

		fichier2 = fichier2.substring(0, fichier2.length());

		tgr2.titre = fichier2;
		tgr2.frame.setTitle("VisuGraph : " + fichier2
				+ "                 Graphe initial");
		tgr2.frame.setLocation(185, 50);
		String repertoire2 = argv[0].concat("/");

		if (type == 0) {
			String fichier = repertoire2.concat(repertoire.concat(".varA"));
			tgr2.nomslongs.LireNomsLongs(fichier, type);
			tgr2.categorie.LireNomsLongs(fichier, type);
			tgr2.nom1 = tgr2.categorie.nom1;
			fichier = repertoire2.concat(repertoire.concat(".indA"));
			NomLongs nomslongsbis = new NomLongs();
			tgr2.categorie.LireNomsLongs(fichier, type);
			tgr2.nom2 = tgr2.categorie.nom1;
			nomslongsbis.LireNomsLongs(fichier, type);
			tgr2.nomslongs.data.putAll(nomslongsbis.data);
		} else {
			String fichier = repertoire2.concat(repertoire.concat(".indA"));
			tgr2.nomslongs.LireNomsLongs(fichier, type);
			tgr2.categorie.LireNomsLongs(fichier, type);
			tgr2.nom2 = tgr2.categorie.nom1;
		}
		tgr2.cmd8 = tgr2.nom2;

		if (tgr2.nom2 != (null)) {
			tgr2.cmd8 += "%";
			tgr2.cmd8 += tgr2.nom1;
		}

		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet s = gr.getSommet(i);
			s.nomlong = (String) tgr2.nomslongs.data.get((Object) (s.nom));
		}

		tgr2.ChangeGraphe = true;
		tgr2.Items = new String[gr.nombreSommets()];

		tgr2.NbSommetsVisibles = gr.nombreSommets();
		double sx = 0.0;
		double sy = 0.0;

		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet s = gr.getSommet(i);
			sx += s.x;
			sy += s.y;
			if (s.nomlong != null)
				tgr2.Items[i] = s.nomlong;
			else {
				tgr2.Items[i] = s.nom;
				s.nomlong = s.nom;
			}
		}

		tgr2.graviteX = (sx / gr.nombreSommets());
		tgr2.graviteY = (sy / gr.nombreSommets());
		tgr2.triRapide(tgr2.Items, 0, gr.nombreSommets() - 1);
		tgr2.gr.maximumMetrique();
		gr.TrierAretes();
		tgr2.init(periode);
		tgr2.centrerGraphe(gr);
		tgr2.gr.centre = gr.getSommet(0);
		tgr2.gr.SetCouleursAretes(tgr2.gr.intensite, 1);

		// tgr2.Force = new ForceDirect( gr, 10 );
		tgr2.getGraphe().pathFile = argv[1];


		
		System.out.println("nombre Sommet   : " + tgr2.getGraphe().nombreSommets() );
		
		
	if(periode == 1){	
		
//		tgr2.initNombreNoeudintermediaire(tgr2.getGraphe());
//		for (int i = 0; i < tgr2.getGraphe().nombreSommets(); i++) {
//			Sommet sommet_i = (Sommet) tgr2.getGraphe().sommets.get(i);
//			if(sommet_i.voisins.size()>0){
//			 for ( int j = 0; j < tgr2.getGraphe().nombreSommets(); j++) {
//		    stack = new Stack<Sommet>();
//			Sommet sommet_j = (Sommet) tgr2.getGraphe().sommets.get(j);
//			// System.out.println("source  :" + sommet_i.getNom() +"------->"+ sommet_j.getNom());
//			 if(sommet_i.getNombreIntermediaire().get(sommet_j) == Integer.MAX_VALUE){
//			 if(sommet_j.voisins.size() > 0 ){
//			 nombreintermediaire(sommet_i, sommet_j, tgr2);
//			 sommet_j.getNombreIntermediaire().put(sommet_i, sommet_i.getNombreIntermediaire().get(sommet_j));
//			// System.out.println( sommet_i.getNombreIntermediaire().get(sommet_j));
//			 }
////			 else{
////				 System.out.println(sommet_j.getNom()+ "  est isolé !! ") ;	 
////			 }
//			 }
//			 }
//			 }
//			 }
		tgr2.degreSimple(tgr2.getGraphe());
        tgr2.loadComputeDistance(tgr2, 1.0 , "distance.txt");
		tgr2.betweennessFreeMan(tgr2);
		tgr2.closenessCentralityfreeMan(tgr2, 1);
	}else{
		tgr2.periodicSimpleCentrality(tgr2 , periode, 1, argv[1] , 1);
//		if(tgr2.isShowTempGenMeasures() ){
//			 String name = JOptionPane.showInputDialog("Entrez le paramètre Alpha");
//				tgr2.periodicGenCentrality(tgr2 , periode, 1, argv[1] , new Double( name));
//		}
		}
		
		
		//tgr2.getGraphe().start();

		tgr2.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}

}
