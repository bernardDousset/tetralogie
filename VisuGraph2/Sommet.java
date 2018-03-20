import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.RepaintManager;

/**
 * La classe Sommet represente un sommet dans un graphe. Elle comporte ce qu'il
 * faut pour positionner et afficher le sommet sur le plan, avec ou sans
 * etiquette de sommet. Les sommets sont representes dans le graphe par des
 * cercles de rayon RAYON, une constante.
 */
public class Sommet implements Runnable{
	double centralite_poids = 0;
	double centralite_degre = 0;
	double centralite_combine = 0;
	double closenessCentrality = 0;
	double betweennessCentrality = 0;
	double closenessGenCentrality = 0;
	double betweennessGenCentrality = 0;
	
	int degreeRank = 0;
	int StrengthRank = 0;
	int intermediarityRank = 0;
	int proximityRank = 0;
	int combinedRank = 0;
	int genIntermediarityRank = 0;
	int genProximityRank = 0;
	
	/**
	 *  les variables stressDegree, stressStrength ... indiquent si on veut afficher la valeur num�rique de la mesure ou non 
	 */
	public boolean stressDegree = false;
	public boolean stressStrength = false;
	public boolean stressProximity = false;
	public boolean stressIntermedirity = false;
	public boolean stressCombinedDegree = false;
	public boolean stressGenProximity = false;
	public boolean stressGenIntermediarity = false;
	
	 public boolean afficherDegreeClass = false;
	    public boolean afficherLinkClass = false;
	    public boolean afficherProximityClass = false;
	    public boolean afficherIntermediarityClass = false;
	    public boolean afficherGenProximityClass = false;
	    public boolean afficherGenIntermediarityClass = false;
	    public boolean afficherGenDegreeClass = false;
	    public double intensiteC = 0;
	//************************************************
	Map<Integer , Double> periodicDegree = new HashMap<Integer, Double>();
	Map<Integer , Double> periodicLink= new HashMap<Integer, Double>();
	Map<Integer , Double> periodicproximity= new HashMap<Integer, Double>();
	Map<Integer , Double> periodicintermediarity= new HashMap<Integer, Double>();
   
	Map<Integer , Double> periodicCombinedDegree = new HashMap<Integer, Double>();
	Map<Integer , Double> periodicGenIntermediarity = new HashMap<Integer, Double>();
	Map<Integer , Double> periodicGenProximity= new HashMap<Integer, Double>();
	
	double intensiteCouleur = 0;
    
	private int  classDegreeCentrality = 0;
	private int classPoidCentrality = 0;
	private int classProximityCentrality = 0;
	private int classIntermediarityCentrality = 0;
	private int classGenIntermediarityCentrality = 0;
	private int classGenProximityCentrality = 0;
	private int classCombinedCentrality = 0;
	
    


    /**
     * Variable booleen pour contr�ler l'affichage des menus de calcul de centralite 
     */

	
	
	
	
	
	public double maxDegree = 0;
	public double maxPoids = 0;
	public double maxProximity = 0;
	public double maxBetweenness = 0;
	public double maxGenProximity = 0;
	public double maxGenBetweenness = 0 ;
	public double maxGenDegree= 0 ;
	
	
	
	
	/*
	 * Distance = pour chaque sommet on definit un map dans laquelle on stoque les plus courtt chemins 
	 * menant � tous les noeuds du graphe
	 * 
	 * **/
	private Map<Sommet, Double> distance = new HashMap<Sommet, Double>();
	private Map<Sommet, Vector<Sommet>> predecessors = new HashMap<Sommet, Vector<Sommet>>();
    /*** path binaire simple : path moyenant le nombre de sommet uniquement ***/
    
    private Map<Sommet , Integer> nombreIntermediaire = new HashMap<Sommet, Integer>();
    private Map<Sommet ,Vector<Sommet>> chemin = new HashMap<Sommet,Vector<Sommet> >();
    private boolean marqued = false;
    private boolean checked = false ; 
    private Vector<Sommet> pathBinaire ;
	
    private Map<Sommet,Map<Integer , Vector<Sommet>>> mesChemins = new HashMap<Sommet,Map<Integer , Vector<Sommet>>>(); 
	private Map<Integer,Vector<Sommet>> mesAltenatives = new HashMap<Integer,Vector<Sommet>>();
	private Vector<Sommet> localPath = new Vector<Sommet>();
//	
	
	
	public int x;
	public int y;
	public int z;
	int valR = 255;
	int valV = 0;
	public int x0;
	public int y0;
	public int z0;

	public Color couleur; // couleur en fonction de la metrique
	public Color couleurClasse = Color.blue; // couleur apres clustering
	public Color couleurTranst = Color.white; // couleur dans la SR obtenue par
												// transitivit�
	
	public static Color couleurTexte = Color.white;
	public Color[] CouleurTab = { Color.red, Color.blue, Color.magenta,
			Color.orange, Color.green, Color.white };

	public Color[] CouleurTab2 = { Color.red.darker(), Color.blue.darker(),
			Color.magenta.darker(), Color.orange.darker(),
			Color.green.darker(), Color.gray };

	ControlWindow control = null;
	public String nom; // nom court : peut etre null si pas d'etiquette
	public String nomlong; // nom long : peut etre null si pas d'etiquette
	public int nbvoisins;
	public int seuil_voisin;
	public int num;
	public int modifx;
	public int modify;
	public static final int RAYON = 5;
	public static int larg = 20;
	public static int haut = 10;
	public int classe;
	public int type;

	private boolean visible = true;
	private int degre = 0;
	public boolean fixe = false;

	public Vector<Sommet> voisins = new Vector<Sommet>();
    private  double alpha = 0; 
	public int num_cluster = 0;
	public double metrique;
	public double Metrique[];
	public int niveau = 100;
	Font fnt1 = new Font("Time", Font.PLAIN, 9);
	// pour les effets d'attraction/repulsion
	public double dx, dy, dz;

	// pour figer la periode (le nombre d'instances)
	public int per;

	/*
	 * On reattribue des coordonn�es � nos sommets dans le cas o� on a activ�
	 * les forces
	 */
	public final void forced(Sommet other, double factor, boolean colle) {
		// System.out.println("le sommet "+this.nom+" a pour coordonnees : ("+this.x+" ; "+this.y+"; "+this.z+") et ses nouvelles coordonnees sont : ("+dx+" ; "+dy+"; "+dz+")");
		if (colle) {
			dx += ((x - 0.00000000000001) - other.x) * factor;
			dy += ((y - 0.000000000000001) - other.y) * factor;
			dz += ((z + 0.000000000000001) - other.z) * factor;
			colle = false;
		} else {
			dx += (x - other.x) * factor;
			dy += (y - other.y) * factor;
			dz += (z - other.z) * factor;
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////:
	public synchronized void epinglerDegree(Graphics g,  String mesure, double maxMesure){
		Graphics2D g2 = (Graphics2D) g;
		//Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			g2.setStroke(new BasicStroke(1.3F));
			/* coordonn�es d'o� nous allons afficher les valeurs des centralit�s */
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
			if(mesure == "degre")
			valeurafficher =(double )Math.round(this.centralite_degre * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			
			if(mesure == "strength")
				valeurafficher =(double )Math.round(this.centralite_poids * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure == "intermediarity")
				valeurafficher =(double )Math.round(this.betweennessCentrality * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure == "proximity")
				valeurafficher =(double )Math.round(this.closenessCentrality* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure == "CombinedDegre")
				valeurafficher =(double )Math.round(this.centralite_combine * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure == "genProximity")
				valeurafficher =(double )Math.round(this.closenessGenCentrality* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure == "genIntermediarity")
				valeurafficher =(double )Math.round(this.betweennessGenCentrality* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			if(mesure != ""){
			double v = valeurafficher/maxMesure;
			g.drawString("" + v , textPtX, textPtY);
			g2.setStroke(new BasicStroke(1.0F));
			}else{
				g.drawString("", textPtX, textPtY);
			}
			}
	}

	public synchronized void afficherDegre(Graphics g, double max , int cercle, double maxDegreCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
//			if(stressDegree){                        
//			valeurafficher =(double )Math.round(this.centralite_degre* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
//			g.drawString("" + valeurafficher , textPtX, textPtY);
//			g2.setStroke(new BasicStroke(0.0F));
//			}
			float  a =  0f; 
				if (cercle == 0) {                               // Nuance de couleur
					a = (float) (intensiteC);
					Color c = new Color((int) (a * 12.75), 0, 0);
					g2.setColor(c);
					double cl1 = metrique / max;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1),
							(int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
				}else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getCentralite_degre()/maxDegreCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getCentralite_degre()/maxDegreCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		
			
			
		}
			
			}
		
	
	

	public synchronized void afficherpoids(Graphics g , double max, int cercle, double maxLinkCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		//Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
//			g2.setStroke(new BasicStroke(1.3F));
			/* coordonn�es d'o� nous allons afficher les valeurs des centralit�s */
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
			if(stressStrength){
			valeurafficher =(double )Math.round(this.centralite_poids* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			double v = valeurafficher/maxLinkCentrality;
			g.drawString("" + valeurafficher , textPtX, textPtY);
			g2.setStroke(new BasicStroke(0.0F));
			}
			float  a =  0f; 
			if(cercle == 0){
				
                // Nuance de couleur
				a = (float) (intensiteC);
				Color c = new Color((int) (a * 12.75), 0, 0);
				g2.setColor(c);
				double cl1 = metrique / max;
				double quantite = 10 * Math.sqrt(cl1);
				if (quantite < 1.0) // c.a.d cl < 0.1
					quantite = 1.0;

				g.fillOval((int) (x - quantite - 1),
						(int) (y - quantite - 1),
						2 * Math.round((float) quantite) + 1,
						2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getCentralite_poids()/maxLinkCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getCentralite_poids()/maxLinkCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		
			
			
			}
			}

	

	public synchronized void afficherDegreCombine(Graphics g, double max, int cercle, double maxCombineCentrality ) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			g2.setStroke(new BasicStroke(1.3F));
			/* coordonn�es d'o� nous allons afficher les valeurs des centralit�s */
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
			if(stressCombinedDegree){
			valeurafficher =(double )Math.round(this.centralite_combine * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			double v = valeurafficher/maxCombineCentrality;
		     g.drawString("" + valeurafficher , textPtX, textPtY);
			g2.setStroke(new BasicStroke(0.0F));
			}
			float a = 0f;
			if(cercle == 0){
				a = (float) (intensiteC);
		    Color c = new Color((int) (a * 12.75), 0, 0);
			g2.setColor(c);
			double cl1 = metrique/max;
			double quantite = 10 * Math.sqrt(cl1);
			if (quantite < 1.0) // c.a.d cl < 0.1
				quantite = 1.0;

			g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
					2 * Math.round((float) quantite) + 1,
					2 * Math.round((float) quantite) + 1);
					
		
			
			}else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getCentralite_combine()/maxCombineCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getCentralite_combine()/maxCombineCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		}	
			}
			

	
	
	public synchronized void afficherClosenessGen(Graphics g , double max, int cercle , double maxGenClosenessCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			g2.setStroke(new BasicStroke(1.3F));
			/* coordonn�es d'o� nous allons afficher les valeurs des centralit�s */
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
			if(stressGenProximity){
			valeurafficher =(double )Math.round(this.closenessGenCentrality * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			g.drawString("" + valeurafficher , textPtX, textPtY);
			g2.setStroke(new BasicStroke(0.0F));
			}
			float a = 0;
			if(cercle == 0){
				a = (float) (intensiteC);
		    Color c = new Color((int) (a * 12.75), 0, 0);
			g2.setColor(c);
			double cl1 = metrique/max;
			double quantite = 10 * Math.sqrt(cl1);
			if (quantite < 1.0) // c.a.d cl < 0.1
				quantite = 1.0;

			g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
					2 * Math.round((float) quantite) + 1,
					2 * Math.round((float) quantite) + 1);
					
		      }		else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getClosenessGenCentrality()/maxGenClosenessCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getClosenessGenCentrality()/maxGenClosenessCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }

		
			

			
		}	
			
			}



	public synchronized void afficherClosenessFreeMan(Graphics g , double max , int cercle , double maxClosenessCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			g2.setStroke(new BasicStroke(1.3F));
			/* coordonn�es d'o� nous allons afficher les valeurs des centralit�s */
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
			if(stressProximity){
			valeurafficher =(double )Math.round(this.closenessCentrality * 1000 )/1000;    // arrondissement � 3 chiffres pr�s
			double v = valeurafficher/maxClosenessCentrality;
			g.drawString("" + v , textPtX, textPtY);
			g2.setStroke(new BasicStroke(0.0F));
			}
			float a = 0;
			if(cercle == 0){

				a = (float) (intensiteC);
			//	System.out.println(" a   =  "   + a );
		    Color c = new Color((int) (a * 12.75), 0, 0);
			g2.setColor(c);
			double cl1 = metrique/max;
			double quantite = 10 * Math.sqrt(cl1);
			if (quantite < 1.0) // c.a.d cl < 0.1
				quantite = 1.0;

			g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
					2 * Math.round((float) quantite) + 1,
					2 * Math.round((float) quantite) + 1);
					
		      }		else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getClosenessCentrality()/maxClosenessCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getClosenessCentrality()/maxClosenessCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		
			
		}	
			
			}

	

	public synchronized void afficherBetweennessGen(Graphics g , double max , int cercle , double maxBetweennessGenCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
//			if(stressDegree){                        
//			valeurafficher =(double )Math.round(this.centralite_degre* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
//			g.drawString("" + valeurafficher , textPtX, textPtY);
//			g2.setStroke(new BasicStroke(0.0F));
//			}
			float  a =  0f; 
				if (cercle == 0) {                               // Nuance de couleur
					a = (float) (intensiteC);
					Color c = new Color((int) (a * 12.75), 0, 0);
					g2.setColor(c);
					double cl1 = metrique / max;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1),
							(int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
				}else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getBetweennessGenCentrality()/maxBetweennessGenCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getBetweennessGenCentrality()/maxBetweennessGenCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		
			
			
		}
			
			}
			
		
	

	public synchronized void afficherBetweennessFreeMan(Graphics g , double max, int cercle , double maxBetweennessCentrality) {
		Graphics2D g2 = (Graphics2D) g;
		Color old = g.getColor();
		double valeurafficher = 0;
		if (this.visible) {
			
			int textPtX = this.getX()  ;
			int textPtY = this.getY()  - 10;
//			if(stressDegree){                        
//			valeurafficher =(double )Math.round(this.centralite_degre* 1000 )/1000;    // arrondissement � 3 chiffres pr�s
//			g.drawString("" + valeurafficher , textPtX, textPtY);
//			g2.setStroke(new BasicStroke(0.0F));
//			}
			float  a =  0f; 
				if (cercle == 0) {                               // Nuance de couleur
					a = (float) (intensiteC);
					Color c = new Color((int) (a * 12.75), 0, 0);
					g2.setColor(c);
					double cl1 = metrique / max;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1),
							(int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
				}else if(cercle == 1) { // Sommet circulaire && taille = metrique d'affichage
		    	  g2.setColor(Color.red);
		    	  double cl1 = this.getBetweennessCentrality()/maxBetweennessCentrality;
					double quantite = 10 * Math.sqrt(cl1);
					if (quantite < 1.0) // c.a.d cl < 0.1
						quantite = 1.0;

					g.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
							2 * Math.round((float) quantite) + 1,
							2 * Math.round((float) quantite) + 1);
		      }else if(cercle == 2){ // Sommet en barre et sa taille est la metrique d'affichage
				double	quantite = 20 * this.getBetweennessCentrality()/maxBetweennessCentrality;
				if (!nom.contains("virtuel")) {
					g.drawLine(x, y, x + 5, y);
					g2.setColor(Color.GREEN);
				}
				
				g.fill3DRect(x - 5 + 5,
						y - 2 * (int) Math.round(quantite), 5,
						2 * (int) Math.round(quantite), true);
				
		      }
		
			
			
		}
			
			}
			

	
	// /////////////////////////////////////////

	public final void scaleDelta(double factor) {
		dx = dx * factor;
		dy = dy * factor;
		dz = dz * factor;
	}

	public final void stabilize() {
		dx = dy = dz = 0.0;
	}

	public final double deltaForce() {
		return Math.sqrt(dx * dx + dy * dy);
	}

	public final void moveDelta(double factor, double w, double h) {
		x += dx * factor;
		y += dy * factor;
		z += dz * factor;
		if (x > w - 20)
			x = (int) w - 20;
		if (y > h - 100)
			y = (int) h - 100;
		if (x < 5)
			x = 5;
		if (y < 20)
			y = 20;
	}

	protected boolean marked = false;

	protected final void mark() {
		marked = true;
	}

	protected final void unmark() {
		marked = false;
	}

	protected final boolean marked() {
		return marked;
	}

	/**
	 * Construit un objet Sommet. La couleur est noire et il n'y a pas
	 * d'etiquette de sommet.
	 * 
	 * @param x
	 *            abcisse du sommet
	 * @param y
	 *            ordonnee du sommet
	 */
	Sommet(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		couleur = Color.red;
		nom = null;
		Metrique = new double[10];
	}

	/*
	 * Construit un objet Sommet. Il n'y a pas d'etiquette de sommet.
	 * 
	 * @param x abcisse du sommet
	 * 
	 * @param y ordonnee du sommet
	 * 
	 * @param couleur couleur du sommet
	 */
	Sommet(int x, int y, int z, Color couleur) {
		this(x, y, z);
		if (couleur == null)
			this.couleur = Color.white;
		else
			this.couleur = couleur;
	}

	/**
	 * Construit un objet Sommet. La couleur est par defaut noire.
	 * 
	 * @param x
	 *            abcisse du sommet
	 * @param y
	 *            ordonnee du sommet
	 * @param nom
	 *            etiquette du sommet
	 */
	Sommet(int x, int y, int z, String nom, int nbvoisins, int seuil_voisin,
			int num, int modifx, int modify) {
		this(x, y, z);
		this.nom = nom;
		this.nbvoisins = nbvoisins;
		this.seuil_voisin = seuil_voisin;
		this.num = num;
		this.modifx = modifx;
		this.modify = modify;
	}

	/**
	 * Construit un objet Sommet.
	 * 
	 * @param x
	 *            abcisse du sommet
	 * @param y
	 *            ordonnee du sommet
	 * @param couleur
	 *            couleur du sommet
	 * @param nom
	 *            etiquette du sommet
	 */
	Sommet(int x, int y, int z, Color couleur, String nom, int nbvoisins,
			int seuil_voisin, int num) {
		this(x, y, z, couleur);
		this.nom = nom;
		this.nbvoisins = nbvoisins;
		this.seuil_voisin = seuil_voisin;
		this.num = num;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Color getCouleur() {
		return couleur;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

	public void setCouleurTxt(Color couleur) {
		this.couleurTexte = couleur;
	}

	public void setInvisible() {
		this.visible = false;
	}

	public void setVisible() {
		this.visible = true;
	}

	public boolean getVisible() {
		return this.visible;
	}

	public void setDegre(int degre) {
		this.degre = degre;
	}

	public int getDegre() {
		return this.degre;
	}

	/**
	 * Convertit l'objet Sommet en chaine affichable.
	 * 
	 * @return un objet String contenant une chaine decrivant l'objet Sommet
	 */
	public String toString() {
		String str = "Sommet: (centre en (" + x + "," + y + "," + z
				+ "), de couleur " + couleur;
		str += nom == null ? " ne portant pas d'etiquette)"
				: (" avec " + nom + " comme etiquette)");
		return str;
	}

    
	
	
	/**
	 * Affiche le sommet sur un contexte graphique Java, en respectant les
	 * coordonnees du sommet, sa couleur et l'etiquette.
	 * 
	 * Cette methode ne connaissant pas la taille de la zone d'affichage, aucune
	 * verification n'est effectuee pour savoir si toutes les informations
	 * entrent sur la zone ou ecrasent une etiquette d'un autre sommet.
	 * 
	 * @param g
	 *            le contexte d'affichage Java a utiliser. Cette fonction permet
	 *            de tracer les histogrammes. Pour ce faire, periode correspond
	 *            � l'instance de morphing, max correspond au maxm�trique
	 */
	public synchronized void afficherInstances(Graphics g, boolean Etiquette,
			boolean Longs, double max, int periode, boolean clustering,
			int couleur_fond, int repmorph, double IntensitePolice,
			double taillePolice) {
		per = periode;
		Color old = g.getColor();
		double quantite = 0.0;
		Graphics2D g2 = (Graphics2D) g;

		if (this.getVisible()) {
			// System.out.println("on est dans afficher instance");
			// instances sous forme de barres
			for (int i = 1; i <= periode; i++) {
				quantite = 20 * Metrique[i] / Metrique[periode + 1];
				if (type == 1 && !clustering) {
					g2.setColor(new Color(255, 0, 0));
				}
			
				if (periode == 2) {
					if (i == 2) {
						g2.setColor(new Color(0, 255, 0));
					}
				} else {
					if (i == 2) {
						g2.setColor(new Color(255, 180, 0));
					}
					if (periode == 3) {
						if (i == 3) {
							g2.setColor(new Color(0, 255, 0));
						}
					} else if (i == 3) {
						g2.setColor(new Color(248, 214, 5));
					}
					if (periode == 4) {
						if (i == 4) {
							g2.setColor(new Color(0, 255, 0));
						}
					} else if (i == 4) {
						g2.setColor(new Color(243, 255, 0));
					}
					if (periode == 5) {
						if (i == 5) {
							g2.setColor(new Color(0, 255, 0));
						}
					} else if (i == 5) {
						g2.setColor(new Color(214, 255, 0));
					}
					if (periode == 6) {
						if (i == 6) {
							g2.setColor(new Color(0, 255, 0));
						}
					} else if (i == 6) {
						g2.setColor(new Color(200, 222, 80));
					}
					if (periode == 7) {
						if (i == 7) {
							g2.setColor(new Color(0, 255, 0));
						}
					} else if (i == 7) {
						g2.setColor(new Color(153, 222, 80));
					}
					if (i == 8) {
						g2.setColor(new Color(0, 255, 0));
					}
				}

				if (quantite == 0.0 && !nom.contains("virtuel")) {

					g2.setColor(Color.gray);
					g.drawLine(x - 5 + 5 * (i - 1), y, x - 5 + 5 * i, y);
				} else {
					if (Math.round(quantite) != 0) {
						g.fill3DRect(x - 5 + 5 * (i - 1),
								y - 2 * (int) Math.round(quantite), 5,
								2 * (int) Math.round(quantite), true);
					} else
						g.fill3DRect(x - 5 + 5 * (i - 1), y - 1, 5, 1, true);
				}
				if (type == 1 && clustering) {
					g.setColor(couleurClasse);
				}
				if (type == 0 && !nom.contains("virtuel")) {
					g2.setColor(Color.green);
				}
				if ((quantite == 0.0) && !nom.contains("virtuel")) {
					g2.setColor(Color.gray);
					g.drawLine(x - 5 + 5 * (i - 1), y, x - 5 + 5 * i, y);
				} else {
					if (Math.round(quantite) != 0 && !nom.contains("virtuel"))
						g.fill3DRect(x - 5 + 5 * (i - 1),
								y - 2 * (int) Math.round(quantite), 5,
								2 * (int) Math.round(quantite), true);
					else
						g.fill3DRect(x - 5 + 5 * (i - 1), y - 1, 5, 1, true);
				}
			}
			int tail = (int) (taillePolice);
			Font ft = new Font("Time", Font.PLAIN, tail);
			if (repmorph == 1) {
				g.setFont(ft);
				g.setColor(Color.red);
				String Nom = (Longs && nomlong != null) ? nomlong : nom;
				if (Nom.contains("Repere"))
					g.drawString(Nom, x + 5, y + 3 * Sommet.RAYON);
			}
			if (!Etiquette) { //  sans etiquette 
				String Nom = (Longs && nomlong != null) ? nomlong : nom;
				g.setFont(ft);
				if (!Nom.contains("Repere")) {
					if (couleur_fond == 1) {
						g.setColor(new Color((int) IntensitePolice,
								(int) IntensitePolice, (int) IntensitePolice));
					} else {
						g.setColor(new Color(255 - (int) IntensitePolice,
								255 - (int) IntensitePolice,
								255 - (int) IntensitePolice));
					}
					g.drawString(Nom, x + 5, y + 3 * Sommet.RAYON);
				}

			}
		}
		g.setColor(old); // permet de ne pas alterer l'etat du contexte tel
							// qu'il est recu
		// clustering=false;
	}

	
	public synchronized void afficher(Graphics g, boolean Etiquette,
			double max, boolean Longs, int forme, boolean clustering, int rang,
			int param, int couleur_fond, int repmorph, double IntensitePolice,
			double taillePolice, int compteur, int pre_instance) {
		Color old = g.getColor();
		double quantite = 0.0, cl = 0.0;
		// on r�cup�re le nombre d'instances de notre morphing
		int periode = per;
		Graphics2D g2 = (Graphics2D) g;
		// si on a coch� "afficher repere", on rend bien visible les reperes
		// pour chaque instance visualis�e ind�pendement.
		if (this.nom.contains("Repere"))
			this.setVisible();
		if (this.getVisible()) {
			if (max != 0.0)
				cl = metrique / max;
			if (forme == 0) { // sommet sous forme circulaire avec nuances de couleur
				if (clustering) {
					g.setColor(new Color(
							(int) (Math.abs(couleurClasse.getRed())),
							(int) (Math.abs(couleurClasse.getGreen())),
							(int) (Math.abs(couleurClasse.getBlue()))));
					g.fillOval(x - Sommet.RAYON, y - Sommet.RAYON,
							2 * Sommet.RAYON, 2 * Sommet.RAYON);
				} else {
					g.setColor(couleur);
					g.fillOval(x - Sommet.RAYON, y - Sommet.RAYON,
							2 * Sommet.RAYON, 2 * Sommet.RAYON);
				}
			
			} else if (forme == 1) {                 // Taille = Metrique , on a deux repr�sentation : barre et cercle suivant la valeur dy "type"
			if(type == 1){           			// sommet sous forme de cercle dont la taille depend de sa metrique
				if ( !clustering) {
					g.setColor(couleur);
				}
				if (clustering)
					g.setColor(couleurClasse);  
				if (type == 0 && !nom.contains("virtuel")) {
					g.setColor(Color.green);
      			}
				
					
					
				quantite = 10 * Math.sqrt(cl);
				if (quantite < 1.0) // c.a.d cl < 0.1
					quantite = 1.0;
				g2.fillOval((int) (x - quantite - 1), (int) (y - quantite - 1),
						2 * Math.round((float) quantite) + 1,
						2 * Math.round((float) quantite) + 1);
				
				
				
			} 
			}else if(forme == 2) {  			// sommet sous forme de barre

			if (this.getVisible()) {
				// instances sous forme de barres
				if (param == 1) {

					for (int i = 1; i <= periode; i++) {
						quantite = 20 * Metrique[i] / Metrique[periode + 1];
						if (periode < 2) {
							quantite = 20 * metrique / max;
						}
						if (type == 1 && !clustering) {
							g2.setColor(Color.red);
							// la barre de l'histogramme correspondant � la
							// deuxi�me instance sera bleue
							if (i == 2)
								g2.setColor(Color.blue);
							// la barre de l'histogramme correspondant � la
							// deuxi�me instance sera blanche
							if (i == 3)
								g2.setColor(Color.white);
							// la barre de l'histogramme correspondant � la
							// deuxi�me instance sera orange
							if (i == 4)
								g2.setColor(Color.orange);
							if (i == 5)
								g2.setColor(Color.magenta);
							if (i == 6)
								g2.setColor(Color.cyan);
							if (i == 7)
								g2.setColor(Color.yellow);
							if (i == 8)
								g2.setColor(Color.green);
							if (i == 9)
								g2.setColor(Color.orange);
							if (type == 1 && clustering)
								g.setColor(couleurClasse);
							if (type == 0)
								g2.setColor(Color.green);
						}

						if (type == 1 && clustering) {
							g2.setColor(couleurClasse);
						}

						if (quantite == 0.0 && !nom.contains("virtuel")) {
							g2.setColor(Color.gray);
							g.drawLine(x - 5 + 5 * (i - 1), y, x - 5 + 5 * i, y);
						} else {
							if (Math.round(quantite) != 0) {
								g.fill3DRect(x - 5 + 5 * (i - 1), y - 2
										* (int) Math.round(quantite), 5,
										2 * (int) Math.round(quantite), true);
							} else
								g.fill3DRect(x - 5 + 5 * (i - 1), y - 1, 5, 1,
										true);
						}
					}
				}
				if (param == 2) {
					int degre = 255 / (periode - 1);
					for (int i = 1; i <= periode; i++) {
						quantite = 20 * Metrique[i] / Metrique[periode + 1];

						if (periode < 2) {
							quantite = 20 * metrique / max;
						}
						if (type == 1 && !clustering) {
							int col = 0;
							if (couleur_fond == 0) {
								g2.setColor(new Color(240, 240, 240));
								col = 240;
							} // la barre de l'histogramme correspondant � la
								// premi�re instance sera rouge
							if (couleur_fond == 1) {
								g2.setColor(new Color(50, 50, 50));
								col = 50;
							}
							// System.out.println(compteur);
							if (param == 2 && compteur > 9)
								compteur = 9;
							try {

								if (type == 1 && i == rang && !clustering) {
									g2.setColor(new Color(
											255 / (5 - (compteur / 2)), 0, 0));
								}
								if (periode == 2) {
									if (i == 2 && i == rang) {
										if (compteur < 5)
											g2.setColor(new Color(
													255 / (compteur), 0, 0));
										else
											g2.setColor(new Color(0, 0,
													255 / (5 - (compteur / 2))));
									}
								} else {
									if (i == 2 && i == rang) {
										/*
										 * if (compteur<5)g2.setColor(new
										 * Color(255/(compteur),0,0)); else
										 */{
											g2.setColor(new Color(
													255 / (5 - (compteur / 2)),
													180 / (5 - (compteur / 2)),
													0));
										}
									}
								}
								int n1 = 255;
								int n2 = 0;
								int n3 = 0;
								if (pre_instance == 0 && i == pre_instance) {
									n1 = 255 / (compteur);
									if (n1 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 1 && i == pre_instance) {
									n1 = 255 / (compteur);
									if (n1 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 2 && i == pre_instance) {
									n1 = 255 / (compteur);
									n2 = 180 / (compteur);
									if (n1 < col && n2 < col && n3 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 3 && i == pre_instance) {
									n1 = 248 / (compteur);
									n2 = 214 / (compteur);
									n3 = 5 / (compteur);
									if (n1 < col && n2 < 50 && n3 < 50) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 4 && i == pre_instance) {
									n1 = 243 / (compteur);
									n2 = 255 / (compteur);
									if (n1 < col && n2 < col && n3 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 5 && i == pre_instance) {
									n1 = 214 / (compteur);
									n2 = 255 / (compteur);
									if (n1 < col && n2 < col && n3 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 6 && i == pre_instance) {
									n1 = 200 / (compteur);
									n2 = 222 / (compteur);
									n3 = 80 / (compteur);
									if (n1 < col && n2 < col && n3 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (pre_instance == 7 && i == pre_instance) {
									n1 = 153 / (compteur);
									n2 = 222 / (compteur);
									n3 = 80 / (compteur);
									if (n1 < col && n2 < col && n3 < col) {
										n1 = col;
										n2 = col;
										n3 = col;
									}
									g2.setColor(new Color(n1, n2, n3));
								}
								if (periode == 3 && i == rang) {
									if (i == 3 && i == rang) {
										/*
										 * if (compteur<5)g2.setColor(new
										 * Color(255
										 * /(compteur),180/(compteur),0)); else
										 */g2.setColor(new Color(0,
												255 / (5 - (compteur / 2)), 0));
									}
								} else if (i == 3 && i == rang) {
									/*
									 * if (compteur<5)g2.setColor(new
									 * Color(255/(compteur),180/(compteur),0));
									 * else
									 */g2.setColor(new Color(
											248 / (5 - (compteur / 2)),
											214 / (5 - (compteur / 2)),
											5 / (5 - (compteur / 2))));
								}

								if (periode == 4 && i == rang) {
									if (i == 4 && i == rang) {
										if (compteur < 5)
											g2.setColor(new Color(
													248 / (compteur),
													214 / (compteur),
													5 / (compteur)));
										else
											g2.setColor(new Color(0,
													255 / (5 - (compteur / 2)),
													0));
									}
								} else if (i == 4 && i == rang) {
									/*
									 * if (compteur<5)g2.setColor(new
									 * Color(248/(
									 * compteur),214/(compteur),5/(compteur)));
									 * else
									 */{
										g2.setColor(new Color(
												243 / (5 - (compteur / 2)),
												255 / (5 - (compteur / 2)), 0));
									}
								}

								if (periode == 5 && i == rang) {
									if (i == 5 && i == rang) {
										if (compteur < 5)
											g2.setColor(new Color(
													243 / (compteur),
													255 / (compteur), 0));
										else {
											g2.setColor(new Color(0,
													255 / (5 - (compteur / 2)),
													0));
										}
									}
								} else if (i == 5 && i == rang) {
									/*
									 * if (compteur<5)g2.setColor(new
									 * Color(243/(compteur),255/(compteur),0));
									 * else
									 */g2.setColor(new Color(
											214 / (5 - (compteur / 2)),
											255 / (5 - (compteur / 2)), 0));
								}

								if (periode == 6 && i == rang) {
									if (i == 6 && i == rang) {
										if (compteur < 5)
											g2.setColor(new Color(
													214 / (compteur),
													255 / (compteur), 0));
										else
											g2.setColor(new Color(0,
													255 / (5 - (compteur / 2)),
													0));
									}
								} else if (i == 6 && i == rang) {
									/*
									 * if (compteur<5)g2.setColor(new
									 * Color(214/(compteur),255/(compteur),0));
									 * else
									 */g2.setColor(new Color(
											200 / (5 - (compteur / 2)),
											222 / (5 - (compteur / 2)),
											80 / (5 - (compteur / 2))));
								}

								if (periode == 7 && i == rang) {
									if (i == 7 && i == rang) {
										if (compteur < 5)
											g2.setColor(new Color(
													200 / (compteur),
													222 / (compteur),
													80 / (compteur)));
										else
											g2.setColor(new Color(0,
													255 / (5 - (compteur / 2)),
													0));
									}
								} else if (i == 7 && i == rang) {
									/*
									 * if (compteur<5)g2.setColor(new
									 * Color(200/(
									 * compteur),222/(compteur),80/(compteur)));
									 * else
									 */g2.setColor(new Color(
											153 / (5 - (compteur / 2)),
											222 / (5 - (compteur / 2)),
											80 / (5 - (compteur / 2))));
								}

								if (i == 8 && i == rang) {
									/*
									 * if (compteur <5)g2.setColor(new
									 * Color(153/
									 * (compteur),222/(compteur),80/(compteur
									 * ))); else
									 */g2.setColor(new Color(0,
											255 / (5 - (compteur / 2)), 0));
								}
							}

							catch (Exception n) {
							}
						}
						if (type == 1 && clustering)
							g.setColor(couleurClasse);
						if (type == 0 && !nom.contains("virtuel"))
							g2.setColor(Color.green);
						if (quantite == 0.0 && !nom.contains("virtuel")) {
							g2.setColor(Color.gray);
							g.drawLine(x - 5 + 5 * (i - 1), y, x - 5 + 5 * i, y);
						} else {
							if (Math.round(quantite) != 0) {
								g.fill3DRect(x - 5 + 5 * (i - 1), y - 2
										* (int) Math.round(quantite), 5,
										2 * (int) Math.round(quantite), true);
							} else {
								g.fill3DRect(x - 5 + 5 * (i - 1), y - 1, 5, 1,
										true);
							}
						}
					}
				}
				if (param == 3) {
					double ancienne_quantite = 0.0;
					double ancien_rang = 0.0;
					/*
					 * System.out.println("max : "+max);
					 * System.out.println("type : "+type);
					 */
					if (type == 0) {
						quantite = 10 * metrique / max;
					} else {
						quantite = 10 * metrique / max;
					}
					if (periode < 2) {
					
						quantite = 20 * metrique / max;
					}
					// *************/** souci niveau compteur (au detail des 10
					// �tapes, barres trop grandes) ************/
					// System.out.println(compteur);
					// if (ancienne_quantite>quantite && ancien_rang!=rang)
					// quantite=ancienne_quantite;2
					// quantite = ((quantite)*(compteur)/20);222
					if (!nom.contains("virtuel")) {
						g2.setColor(Color.green);
					}
					if (type == 1 && clustering)
						g.setColor(couleurClasse);
					if (type == 0 && !nom.contains("virtuel"))
						g2.setColor(Color.green);
					{
						g2.setColor(Color.gray);
						if (!nom.contains("virtuel")) {
							g.drawLine(x, y, x + 5, y);
							g2.setColor(Color.green);
						}
						/******/
						g.fill3DRect(x - 5 + 5,
								y - 2 * (int) Math.round(quantite), 5,
								2 * (int) Math.round(quantite), true);
						ancienne_quantite = quantite;
						ancien_rang = rang;
					}
				}

				if (forme == 3) {
					g.setColor(couleurClasse);
					g.fillOval(x - Sommet.RAYON, y - Sommet.RAYON,
							2 * Sommet.RAYON, 2 * Sommet.RAYON);
				}

			}
				
			

			if (!Etiquette) { // avec ou sans etiquette ?
				int tail = (int) (taillePolice);
				Font ft = new Font("Time", Font.PLAIN, tail);
				g.setFont(ft);
				String Nom = (Longs && nomlong != null) ? nomlong : nom;
				if (!Nom.contains("Repere")) {
					if (couleur_fond == 1) {
						g.setColor(new Color((int) IntensitePolice,
								(int) IntensitePolice, (int) IntensitePolice));
					} else {
						g.setColor(new Color(255 - (int) IntensitePolice,
								255 - (int) IntensitePolice,
								255 - (int) IntensitePolice));
					}
					g.drawString(Nom, x + 5, y + 3 * Sommet.RAYON);
				}
			}
			if (repmorph == 1) {
				String Nom2 = (Longs && nomlong != null) ? nomlong : nom;
				if (Nom2.contains("Repere")) {
					int tail = (int) (taillePolice);
					Font ft = new Font("Time", Font.PLAIN, tail);
					g.setFont(ft);
					g.setColor(Color.red);
					g.drawString(Nom2, x + 5, y + 3 * Sommet.RAYON);
				}
			}

			g.setColor(old); // permet de ne pas alterer l'etat du contexte tel
								// qu'il est recu
		}
		}
	}

	/**
	 * Deplace le sommet en changeant ses coordonnees.
	 * 
	 * @param newX
	 *            nouvelle abcisse
	 * @param newY
	 *            nouvelle ordonnee
	 * @param w
	 *            largeur de la fenetre
	 * @param h
	 *            hauteur de la fenetre
	 */
	public synchronized void deplacer(int newX, int newY, int w, int h) {
		if (newX < 0 && newY < 0) {
			x = 10;
			y = 10;
		}

		else if (newX < 0 && newY > h - 6) {
			x = 10;
			y = h - 60;
		}

		else if (newX > w && newY > h - 6) {
			x = w - 10;
			y = h - 6;
		}

		else if (newX > w && newY < 0) {
			x = w - 10;
			y = 10;
		}

		else if (newX > 0 && newX < w && newY < 0) {
			x = newX;
			y = 10;
		}

		else if (newX <= 0 && newY > 0 && newY < h) {
			x = 10;
			y = newY;
		}

		else if (newX > 0 && newX < w && newY > h - 6) {
			x = newX;
			y = h - 6;
		}

		else if (newX > w && newY < h) {
			x = w - 10;
			y = newY;
		} else {
			x = newX;
			y = newY;
		}
	}

	/**
	 * Change la couleur du sommet.
	 * 
	 * @param newColor
	 *            nouvelle couleur
	 */
	public synchronized void colorer(Color newColor) {
		couleur = newColor;
	}

	/**
	 * Change l'etiquette du sommet.
	 * 
	 * @param newName
	 *            nouvelle etiquette
	 */
	public synchronized void renommer(String newName) {
		nom = newName;
	}

	/**
	 * Deux sommets sont egaux si et seulement si leurs coordonnes sont
	 * identiques.
	 */
	public boolean equals(Sommet autre) {
		return x == autre.x && y == autre.y && nom == autre.nom;
	}

	public double getCentralite_poids() {
		return centralite_poids;
	}

	public void setCentralite_poids(double centralite_poids) {
		this.centralite_poids = centralite_poids;
	}

	public double getCentralite_degre() {
		return centralite_degre;
	}

	public void setCentralite_degre(double centralite_degre) {
		this.centralite_degre = centralite_degre;
	}

	public double getCentralite_combine() {
		return centralite_combine;
	}

	public void setCentralite_combine(double centralite_combine) {
		this.centralite_combine = centralite_combine;
	}


	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public Map<Sommet, Double> getDistance() {
		return distance;
	}

	public void setDistance(Map<Sommet, Double> distance) {
		this.distance = distance;
	}

	


	

	public Map<Sommet, Integer> getNombreIntermediaire() {
		return nombreIntermediaire;
	}

	public void setNombreIntermediaire(Map<Sommet, Integer> nombreIntermediaire) {
		this.nombreIntermediaire = nombreIntermediaire;
	}

	public Map<Sommet, Vector<Sommet>> getChemin() {
		return chemin;
	}

	public void setChemin(Map<Sommet, Vector<Sommet>> chemin) {
		this.chemin = chemin;
	}

	public Vector<Sommet> getPathBinaire() {
		return pathBinaire;
	}

	public void setPathBinaire(Vector<Sommet> pathBinaire) {
		this.pathBinaire = pathBinaire;
	}

	public boolean isMarqued() {
		return marqued;
	}

	public void setMarqued(boolean marqued) {
		this.marqued = marqued;
	}

	

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<Sommet, Vector<Sommet>> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(Map<Sommet, Vector<Sommet>> predecessors) {
		this.predecessors = predecessors;
	}

	public Map<Integer, Double> getPeriodicDegree() {
		return periodicDegree;
	}

	public Map<Integer, Double> getPeriodicCombinedDegree() {
		return periodicCombinedDegree;
	}

	public void setPeriodicDegree(Map<Integer, Double> periodicDegree) {
		this.periodicDegree = periodicDegree;
	}

	public void setPeriodicCombinedDegree(
			Map<Integer, Double> periodicCombinedDegree) {
		this.periodicCombinedDegree = periodicCombinedDegree;
	}

	public Map<Integer, Double> getPeriodicLink() {
		return periodicLink;
	}

	public void setPeriodicLink(Map<Integer, Double> periodicLink) {
		this.periodicLink = periodicLink;
	}

	public double getClosenessCentrality() {
		return closenessCentrality;
	}

	public double getBetweennessCentrality() {
		return betweennessCentrality;
	}

	public void setClosenessCentrality(double closenessCentrality) {
		this.closenessCentrality = closenessCentrality;
	}

	public void setBetweennessCentrality(double betweennessCentrality) {
		this.betweennessCentrality = betweennessCentrality;
	}

	public Map<Integer, Double> getPeriodicproximity() {
		return periodicproximity;
	}

	public Map<Integer, Double> getPeriodicintermediarity() {
		return periodicintermediarity;
	}

	public void setPeriodicproximity(Map<Integer, Double> periodicproximity) {
		this.periodicproximity = periodicproximity;
	}

	public void setPeriodicintermediarity(
			Map<Integer, Double> periodicintermediarity) {
		this.periodicintermediarity = periodicintermediarity;
	}

	public double getClosenessGenCentrality() {
		return closenessGenCentrality;
	}

	public double getBetweennessGenCentrality() {
		return betweennessGenCentrality;
	}

	public void setClosenessGenCentrality(double closenessGenCentrality) {
		this.closenessGenCentrality = closenessGenCentrality;
	}

	public void setBetweennessGenCentrality(double betweennessGenCentrality) {
		this.betweennessGenCentrality = betweennessGenCentrality;
	}

	

	public Map<Sommet, Map<Integer, Vector<Sommet>>> getMesChemins() {
		return mesChemins;
	}

	public Map<Integer, Vector<Sommet>> getMesAltenatives() {
		return mesAltenatives;
	}

	public Vector<Sommet> getLocalPath() {
		return localPath;
	}

	public void setMesChemins(Map<Sommet, Map<Integer, Vector<Sommet>>> mesChemins) {
		this.mesChemins = mesChemins;
	}

	public void setMesAltenatives(Map<Integer, Vector<Sommet>> mesAltenatives) {
		this.mesAltenatives = mesAltenatives;
	}

	public void setLocalPath(Vector<Sommet> localPath) {
		this.localPath = localPath;
	}

//	

	public Map<Integer, Double> getPeriodicGenIntermediarity() {
		return periodicGenIntermediarity;
	}

	public Map<Integer, Double> getPeriodicGenProximity() {
		return periodicGenProximity;
	}

	public void setPeriodicGenIntermediarity(
			Map<Integer, Double> periodicGenIntermediarity) {
		this.periodicGenIntermediarity = periodicGenIntermediarity;
	}

	public void setPeriodicGenProximity(Map<Integer, Double> periodicGenProximity) {
		this.periodicGenProximity = periodicGenProximity;
	}

	public float getClassDegreeCentrality() {
		return classDegreeCentrality;
	}

	public int getClassPoidCentrality() {
		return classPoidCentrality;
	}

	public int getClassProximityCentrality() {
		return classProximityCentrality;
	}

	public int getClassIntermediarityCentrality() {
		return classIntermediarityCentrality;
	}

	public int getClassGenIntermediarityCentrality() {
		return classGenIntermediarityCentrality;
	}

	public int getClassGenProximityCentrality() {
		return classGenProximityCentrality;
	}

	public int getClassCombinedCentrality() {
		return classCombinedCentrality;
	}

	public void setClassDegreeCentrality(int classDegreeCentrality) {
		this.classDegreeCentrality = classDegreeCentrality;
	}

	public void setClassPoidCentrality(int classPoidCentrality) {
		this.classPoidCentrality = classPoidCentrality;
	}

	public void setClassProximityCentrality(int classProximityCentrality) {
		this.classProximityCentrality = classProximityCentrality;
	}

	public void setClassIntermediarityCentrality(int classIntermediarityCentrality) {
		this.classIntermediarityCentrality = classIntermediarityCentrality;
	}

	public void setClassGenIntermediarityCentrality(
			int classGenIntermediarityCentrality) {
		this.classGenIntermediarityCentrality = classGenIntermediarityCentrality;
	}

	public void setClassGenProximityCentrality(int classGenProximityCentrality) {
		this.classGenProximityCentrality = classGenProximityCentrality;
	}

	public void setClassCombinedCentrality(int classCombinedCentrality) {
		this.classCombinedCentrality = classCombinedCentrality;
	}

	public void setIntensiteCouleur(int intensiteCouleur) {
		this.intensiteC = intensiteCouleur;
	}

	

	public void setIntensiteC(int intensiteC) {
		this.intensiteC = intensiteC;
	}

	public void setIntensiteCouleur(double intensiteCouleur) {
		this.intensiteCouleur = intensiteCouleur;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	



	

	
	
}
