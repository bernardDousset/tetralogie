import java.awt.Color;
import java.io.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.util.ResourceBundle;
import java.util.Locale;
import java.lang.Object;
import java.net.URLDecoder;

/**
 * Represente un graphe avec un ensemble de sommets et un ensemble d'aretes. le
 * graphe peut etre simple ou complexe, oriente ou non oriente. La classe Graphe
 * contient des methodes pour afficher et manipuler des graphes.
 */
class Graphe extends JPanel implements Runnable {
	private HashMap hm_poids;
	private HashMap hm_degre;
	private HashMap hm_combine;
	private HashMap hm_degreIn;
	private HashMap hm_degreOut;
	private HashMap hm_poidsIn;
	private HashMap hm_poidsOut;
	private HashMap hm_combineIn;
	private HashMap hm_combineOut;
	private HashMap distance_;
	private String[][] pathMatrix;// = new
									// String[nombreSommets()][nombreSommets()];
     
	Thread distance;

	
	public String pathFile = ""; 
	public int largeur = 800;
	public int longueur = 830;
	public Vector sommets;
	public Vector getSommets() {
		return sommets;
	}

	public void setSommets(Vector sommets) {
		this.sommets = sommets;
	}
	public Vector aretes;
	private boolean oriente;
	private int complexite = 0; // indique le nombre d'aretes avec aretes
								// paralleles
	public boolean multi = false;
    public boolean afficherDegreeClass = false;
    public boolean afficherLinkClass = false;
    public boolean afficherProximityClass = false;
    public boolean afficherIntermediarityClass = false;
    public boolean afficherGenProximityClass = false;
    public boolean afficherGenIntermediarityClass = false;
    public boolean afficherGenDegreeClass = false;

    public boolean centralityOn = false;
    
    public boolean tempDegree = false;
    public boolean tempStrength = false;
    public boolean tempProximity = false;
    public boolean tempIntermediarity = false;
    public boolean tempGenIntermediarity = false;
    public boolean tempGenProximity = false;
    public boolean tempGendegree = false;


	public Sommet centre = null;
	
	public double[][] matrice;
	/**
	 * typemat == type du matrice :
	 * 0----> asymétrique
	 * 1----> symetrique
	 * 2--->symétrique temporelle
	 * 3----> asymetrique temporelle 
	 */
	public int typeMat = 0;
	public double seuil;
	public double intensite = 2;
	public boolean stress = false;
	
	public boolean stressDegree = false;
	public boolean stressStrength = false;
	public boolean stressProximity = false;
	public boolean stressIntermedirity = false;
	public boolean stressCombinedDegree = false;
	public boolean stressGenProximity = false;
	public boolean stressGenIntermediarity = false;
	
	public double intensiteCouleur = 0;
	
	
	
	public boolean noms = true;
	public boolean masque = false;
	public boolean connexe = false;
	public boolean clustering = false;
	public double maxar = 0; // Pour les aretes, maximum des instances du
								// morphing.
	public int M = 0;
	public boolean longs = false; // affichage des noms longs des sommets
	public boolean sans = true; // sans affichage des labels des sommets
	/** affichage des centralite ou non **/
//	
	/***********************************************/
	double maxMetrique1 = 0, maxMetrique2 = 0;
	double minMetrique1 = 0, minMetrique2 = 0;
	
	double maxDegreeCentrality = 0 , maxStrengthCetrnality =0 , 
	       maxBetweennessCentrality = 0, maxPoximityCentrality = 0 ,
	       maxGenDegreeCentrality = 0, maxGenBtwCentrality = 0, maxGenProxCentrality = 0;
	       
	
	
	
	double maxArete = 0;
	double maxArete2 = 0;
	double minArete = 0;
	int typeGraphe = 1;
	int rang = 1;
	int pre_instance = 1;
	static final int flag = 0;
	int cercle = 2;
	int instance = 0;
    double puissanceRepartition = 1;
	
	
	// **********************************************
	Graphe(int nbsommets) {
		sommets = new Vector(nbsommets);
		aretes = new Vector();
		seuil = 0.0;
	}

	Graphe(Vector sommet, Vector arete) {
		sommets = sommet;
		aretes = arete;
	}

	Graphe(boolean oriente) {
		sommets = new Vector();
		aretes = new Vector();
		this.oriente = oriente;
		seuil = 0.0;
	}

	// ///////////////////////////////////////////////////////////////////////////
	public void initPathMatrix(Graphe gr) {
		int nombresommet = gr.nombreSommets();
		pathMatrix = new String[nombreSommets()][nombreSommets()];
		int j = 0;
		for (int i = 0; i < this.nombreSommets(); i++) {
			Sommet s = (Sommet) this.sommets.elementAt(i);
			this.pathMatrix[i][j] = s.getNom();
		}
		for (int i = 0; i < this.nombreSommets(); i++) {
			Sommet s = (Sommet) this.sommets.elementAt(i);
			this.pathMatrix[j][i] = s.getNom();
		}
		for (int i = 1; i < this.nombreSommets(); i++) {
			for (int k = 1; k < this.nombreSommets(); k++) {
				this.pathMatrix[i][k] = null;
			}
		}
	}
	public double maxDegreeCentrality(Graphe gr ){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getCentralite_degre() > max1){
				max1 = gr.getSommet(i).getCentralite_degre() ; 
			}
			
		}
		return max1;
	}
	public double maxLinkCentrality(Graphe  gr ){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getCentralite_poids()> max1){
				max1 = gr.getSommet(i).getCentralite_poids(); 
			}
			
		}
		return max1;
	}
	public double maxProximityCentrality(Graphe gr ){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getClosenessCentrality() > max1){
				max1 = gr.getSommet(i).getClosenessCentrality(); 
			}
			
		}
		return max1;
	}
	
	public double maxGenProximityCentrality(Graphe gr ){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getClosenessGenCentrality() > max1){
				max1 = gr.getSommet(i).getClosenessGenCentrality(); 
			}
			
		}
		return max1;
	}
	public double maxIntermediarityCentrality(Graphe gr ){
		double maxim  = 0.0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(maxim <= gr.getSommet(i).getBetweennessCentrality() ){
				maxim = gr.getSommet(i).getBetweennessCentrality(); 
			}
			
		}
		return maxim;
	}
	
	public double maxGenIntermediarityCentrality(Graphe gr){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getBetweennessGenCentrality() > max1){
				max1 = gr.getSommet(i).getBetweennessGenCentrality(); 
			}
			
		}
		return max1;
	}
	public double maxGenDegreeCentrality(Graphe gr ){
		double max1 = 0;
		for(int i = 0 ; i<gr.sommets.size(); i++){
			if(gr.getSommet(i).getCentralite_combine() > max1){
				max1 = gr.getSommet(i).getCentralite_combine(); 
			}
			
		}
		return max1;
	}
	public float repartition(double f , double n ){
	  return( (float)(0.5f * (1+ Math.pow(f, 1/n) - (Math.pow((1 - f) , n)))));
		
	}
	public void classDegreeCentrality(Graphe gr ,  double puissance){

		double maxDegreeCentrality = maxDegreeCentrality(gr);
		if(afficherDegreeClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherDegreeClass = true;
			s.maxDegree = gr.maxDegreeCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
			System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getCentralite_degre() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getCentralite_degre()/maxDegreeCentrality);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassDegreeCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherDegreeClass = false;				
			}
		}
		}
		
  
	
	public void classLinkCentrality(Graphe gr, double puissance){

		double maxLinkCentrality = maxLinkCentrality(gr);
		if(afficherLinkClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherLinkClass = true;
			s.maxPoids = gr.maxDegreeCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
		//	System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getCentralite_poids() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getCentralite_degre()/maxLinkCentrality);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassPoidCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherLinkClass= false;				
			}
		}
		}
	
	
	
	public void classGenDegreeCentrality(Graphe gr ,  double puissance){

		double maxGenDegreeCentrality = maxGenDegreeCentrality(gr);
		if(afficherGenDegreeClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherGenDegreeClass = true;
			s.maxGenDegree = gr.maxDegreeCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
			System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getCentralite_combine() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getCentralite_combine()/maxGenDegreeCentrality);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassCombinedCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherGenDegreeClass = false;				
			}
		}
		}
	public void classIntermediarityCentrality(Graphe gr , double puissance){

		double maxIntermediarity = maxIntermediarityCentrality(gr);
		if(afficherIntermediarityClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherIntermediarityClass = true;
			s.maxBetweenness = gr.maxIntermediarityCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
			System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getBetweennessCentrality() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getBetweennessCentrality()/maxIntermediarity);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassIntermediarityCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherIntermediarityClass = false;				
			}
		}
		}

	
	public void classGenIntermediarityCentrality(Graphe gr, double puissance ){

		double maxGenIntermediarity = maxGenIntermediarityCentrality(gr);
		if(afficherGenIntermediarityClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherGenIntermediarityClass = true;
			s.maxGenBetweenness = gr.maxGenIntermediarityCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
		//	System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getBetweennessGenCentrality()>= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getBetweennessGenCentrality()/maxGenIntermediarity);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassGenIntermediarityCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherGenIntermediarityClass = false;				
			}
		}
		}

	
	public void classGenProximityCentrality(Graphe gr , double puissance ){

		double maxGenProximity= maxGenProximityCentrality(gr);
		if(afficherGenProximityClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherGenProximityClass = true;
			s.maxGenProximity = gr.maxGenProximityCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
			System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getClosenessGenCentrality() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getBetweennessCentrality()/maxGenProximity);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassGenProximityCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherGenProximityClass = false;				
			}
		}
		}
	
	public void classProximityCentrality(Graphe gr , double puissance ){

		double maxProximity = maxProximityCentrality(gr);
		if(afficherProximityClass){
		for(int i = 0; i< gr.sommets.size(); i++){
			Sommet s = gr.getSommet(i);
			s.afficherProximityClass = true;
			s.maxProximity = gr.maxProximityCentrality(gr);	
		}
		
		for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
			Sommet s = gr.getSommet(i);
	//		System.out.println("sommet en cours   :"  + s.getNom());
			if(s.getClosenessCentrality() >= 0){
            //double max = maxDegreeCentrality/slider77;
            float Xi = (float)(s.getClosenessCentrality()/maxProximity);
            float fct = repartition(Xi , puissance); 
            	double k =0;
            	while(k <20){
            	//	if(( repartition(k/puissance, puissance) <= Xi)&&(Xi< repartition(((k+1)/puissance), puissance)) ) {
            		if((repartition(k/20, puissance) <=Xi && (repartition((k+1)/20,puissance) >=Xi))){
            			s.setClassProximityCentrality((int)k);
            			s.setIntensiteC((int)k);
            			break;
            		}else
            	k++;
            	}
            	
            	
            }else{
            	s.setInvisible();
            }
		}
    	
		}else{
			for(int i = 0; i< gr.sommets.size(); i++){
				Sommet s = gr.getSommet(i);
				s.afficherProximityClass = false;				
			}
		}
		}
	
	
	public void Verifie_format_matrice(File file) {
		try {
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);
			line = new LineNumberReader(rd_result);
			ligne_doc = line.readLine();

			/*
			 * while (file.canWrite()){
			 * 
			 * PrintStream pfos= new PrintStream(fos);
			 * pfos.println(ligne_doc+" * *"); }
			 */

			// ligne_doc = line.readLine();

			if (!ligne_doc.contains("CLASSE") | !ligne_doc.contains("POIDS")) {
				ligne_doc += "CLASSE POIDS \n";
				JOptionPane
						.showMessageDialog(
								null,
								"La matrice n'est pas conforme. veuillez l'enregistrer de nouveau sous le tableur 2D",
								"ATTENTION", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				/*
				 * String nom_mat=file.getName();
				 * System.out.println("on cree un nouveau fichier");
				 * FileOutputStream fos = new FileOutputStream(file);
				 * PrintStream pfos= new PrintStream(fos);
				 * pfos.println(ligne_doc+" aaaaaaaaaaaaaa"); fos.close();
				 */

				// FileWriter correction = new FileWriter(nom_mat+"BON");
				// correction.write(ligne_doc);
				/*
				 * FileWriter correct =new FileWriter(file);
				 * correct.write(ligne_doc); ligne_doc = line.readLine(); while
				 * ( (ligne_doc = line.readLine()) != null) { if
				 * (ligne_doc.charAt(ligne_doc.length()-1)==' ') ligne_doc
				 * +="0 2 \n"; else ligne_doc +=" 0 2\n";
				 * correct.write(ligne_doc); } // line.close(); line.close();
				 * correct.close();
				 */
			}
		}

		catch (IOException e2) {
			System.err.println(e2);
			System.exit(1);

		} catch (NullPointerException e3) {
			System.out.println("\nNullPointerException dans isntance 0: " + e3);
		}
	}

	public void enregistre(String nom_fichier, int periode) {
		boolean non_enregistre = true;
		try {
			String InstanceFile = nom_fichier.concat(".") + periode; // créer
																		// instance
																		// du
																		// fichier
																		// input
																		// (renommer
																		// .periode)
			instance = periode;
			File file = new File(InstanceFile); // cloner fichier input en
												// fichier file
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);
			// Lecture de la premiere ligne du fichier matrice pour generer les
			// sommets
			ligne_doc = line.readLine();
			StringTokenizer labc = new StringTokenizer(ligne_doc); // lecture
																	// ligne par
																	// tokenizer
																	// ,
																	// separateur
																	// par
																	// defaut
																	// est
																	// l'espace
			String lab = labc.nextToken();
			int nb = labc.countTokens();
			int i = 0;
			while (labc.hasMoreTokens()) {
				i += 1;
				if (i == nb && !labc.nextToken().equals("POIDS")) {
					non_enregistre = true;
				} // signification de ce separateur POIDS ???
				else
					labc.nextToken(); // pkoi ce double test nbre de tokens &&
										// POIDS ??
			}
			if (non_enregistre == true) { // on a lu tout la ligne et on a token
											// courant = "POIDS"
				for (int t = 1; t < periode + 1; t++) {
					File f = new File(nom_fichier.concat(".") + t); // création
																	// de
																	// fichier
																	// par
																	// période :
																	// f.1 , F.2
																	// ...
					Reader r = new FileReader(f);
					File f2 = new File(nom_fichier.concat(".") + t + t); // Renommer
																			// fichier
																			// f.1
																			// en
																			// f.11
					Writer out = new FileWriter(f2); //
					String ligne = null;
					LineNumberReader lin = new LineNumberReader(r);
					ligne = lin.readLine();
					out.write(ligne + " CLASSE " + "POIDS" + "\n");
					while (ligne != null) {
						ligne = lin.readLine();
						if (ligne != null)
							out.write(ligne + " 0 " + "1" + "\n");
					}
					lin.close();
					out.close();
					f.delete();
					f2.renameTo(f);
				}
			}
		} catch (Exception n) {
		}

	}

	/**
	 * Construit un graphe a partir de sa matrice d'adjacence sous forme d'un
	 * fichier texte.
	 */
	public void ChargerMatriceSymetrique(String nom_fichier, int periode) {
		// System.out.println("Largeur : "+largeur+" longueur : "+longueur);
		int nb_aretes = 0;
		int ptx = 0;
		int pty = 0;
		int ptz = 0;
		String InstanceFile = nom_fichier.concat(".") + periode;
		instance = periode;
		System.out.println("Lecture des sommets ....");

		try {

			File file = new File(InstanceFile);
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);
			// Lecture de la premiere ligne du fichier matrice pour generer les
			// sommets
			ligne_doc = line.readLine();
			StringTokenizer labc = new StringTokenizer(ligne_doc);
			String lab = labc.nextToken();
			while (lab != null && !lab.equals("CLASSE")) {
				// Creation du sommet correspondant a la colonne en cours
				/*
				 * pt.x et pt.y sont les valeurs des coordonnï¿½ï¿½es qui seront
				 * attribuï¿½ï¿½es aux sommets. En prenant comme valeur fixe 520
				 * (largeur) et 550 (hauteur), on s'assure de laisser de
				 * l'espace pour afficher les noms des sommets.
				 */
				ptx = 10 + (int) (520 * Math.random());
				pty = 10 + (int) (550 * Math.random());
				ptz = 10 + (int) (550 * Math.random());

				Sommet s = new Sommet(ptx, pty, ptz, lab.toLowerCase(), 0, 0,
						0, 0, 0);

				s.type = 1;
				s.metrique = 0;
				ajouterSommet(s);

				try {
					lab = labc.nextToken();
				} catch (NoSuchElementException ex) {
					break;
				}
				M++;
			}

			line.close(); // fin de lecture du fichier

			// int perimetre=2300/(instance);
			int perimetre = ((largeur + longueur) * 2) / (instance);
			int p = perimetre;
			int res = 0;
			CalculRepere(largeur, longueur, perimetre);
			if (instance > 1)
				M += instance; // /// ????????? nombre sommet + = periode
			matrice = new double[M][M];

			// a ce stade, nous avons cree tous les (M) sommets du graphe
			// prochaine etape : creation des aretes entre ces sommets
			// lecture du fichier ligne par ligne
			if (instance > 1)
				multi = true;

			for (int inst = 1; inst <= instance; inst++) {
				InstanceFile = nom_fichier.concat(".") + inst;
				file = new File(InstanceFile);
				rd_result = new FileReader(file);
				line = new LineNumberReader(rd_result);

				ligne_doc = line.readLine();
				ligne_doc = line.readLine();

				int l = 0; // compteur sur les lignes
				// Traitement d'une ligne du fichier
				//
				int T = 1;
				while (ligne_doc != null && l < M + 1 - instance) {
					Sommet s = getSommet(l);
					s.Metrique[inst] = 0.0; // initialisation du metrique du sommet s dans la periode courante
					int j = 0; // compteur sur les colonnes
					StringTokenizer label = new StringTokenizer(ligne_doc);
					String str = label.nextToken();
					int arret = 0;
					if (instance > 1)
						arret = M + 1 - instance - 2;
					else
						arret = M + 1 - instance;
					while (label.hasMoreTokens() && j < arret) {
						// System.out.println(j);
						// lecture des valeurs dans la ligne courante <=> des
						// liens
						Sommet ss = getSommet(j);
						// System.out.println("sommet: " + ss.getNom());
						str = label.nextToken();
						str = str.replace(',', '.');
						try {
							double val = Double.parseDouble(str);

							if (val != 0.0) {
								if (s.Metrique[inst] <= val
										&& !s.nom.contains("virtuel")) {
									s.Metrique[inst] = val;
								}
								if (j <= l) {

									int indice = areteDansGraphe(s, ss);

									if (indice != -1) {
										Arete a = getArete(indice);
										a.addVal(val);
										a.setValInstance(val, inst);
										if (val == 16.0 && inst != 1)
											System.out
													.println("on a atteind 16");
										for (int i = 0; i <= inst; i++) {
											if (maxar < val)
												maxar = val;
										}
										String nom;
										val = a.getVal();
										if (val < 1.0)
											nom = String.valueOf(val);
										else
											nom = ""
													+ (String
															.valueOf((int) val));
										a.setNom(nom);

									} else if (!s.equals(ss)) {
										Arete a = new Arete(s, ss, s.x, ss.x,
												null, val, nb_aretes++);
										ajouterArete(a);
										a.setValInstance(val, inst);
										String nom;
										val = a.getVal();
										if (val < 1.0)
											nom = String.valueOf(val);
										else
											nom = String.valueOf((int) val);
										a.setNom(nom);

									}
								}

							}

						}

						catch (NumberFormatException ex) {
							System.out
									.println("Lecture de la matrice : Format des valeurs incompatible");
						}
						j++;
					}
					l++;
					ligne_doc = line.readLine();
					T += 1;
				}
				line.close(); // fin de lecture du fichier
			}
		} catch (IOException e2) {
			System.err.println(e2);
			System.exit(1);

		} catch (NullPointerException e3) {
			System.out.println("\nNullPointerException dans multi: " + e3);
		}
		// *********************
		double max = 0.0;
		int k = 1;

		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.metrique = 0.0;
			Sommet SVirtuel;
			for (k = 1; k <= periode; k++) {
				SVirtuel = getSommet(M - (periode - k + 1));
				if (v.metrique <= v.Metrique[k])
					v.metrique = v.Metrique[k];
				if (max <= v.Metrique[k])
					max = v.Metrique[k];
				if (periode > 1) {
					if (v.Metrique[k] != 0) {
						Arete aVirtuelle = new Arete(SVirtuel, v, SVirtuel.x,
								v.x, null, v.Metrique[k], nb_aretes++);
						ajouterArete(aVirtuelle);
						aVirtuelle.setValInstance(v.Metrique[k], k);
						aVirtuelle.setInvisible();
						aVirtuelle.setValInstance(v.Metrique[k], k);
					}
				}
			}
		}

		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.Metrique[periode + 1] = max;
		}
		System.out.println("\n\n");
		cercle = 1;
		if (periode > 1)
			cercle = 2;
		System.gc();
		maximumArete();

	}

	public void CalculRepere(int largeur, int longueur, int perimetre) {
		int pt1 = 0;
		int pt2 = 0;

		if (instance > 1) {
			int n = 3;
			double teta = (Math.PI / instance);
			for (int i = 1; i < instance + 1; i++) {
				pt1 = (int) (((largeur - 50) / 2) * (1 + (0.95 * Math.sin(teta))));
				pt2 = (int) (((longueur - 75) / 2) * (1 - 0.95 * Math.cos(teta)));
				Sommet s = new Sommet(pt1, pt2, 10, "sommet_virtuel_" + i, 0,
						0, 0, 0, 0);
				ajouterSommet(s);
				s.fixe = true;
				teta = n * (Math.PI / instance);
				n += 2;
			}
		}

	}

	public void CalculRepere2(int large, int longu) {
		int pt1 = 0;
		int pt2 = 0;

		if (instance > 1) {
			int n = 3;
			double teta = (Math.PI / instance);
			for (int i = 1; i < instance + 1; i++) {
				String st = "" + i;
				pt1 = (int) (((large - 50) / 2) * (1 + (0.95 * Math.sin(teta))));
				pt2 = (int) (((longu - 75) / 2) * (1 - 0.95 * Math.cos(teta)));
				for (int j = 0; j < nombreSommets(); j++) {
					Sommet s = getSommet(j);

					if (s.nom.contains(st)
							&& (s.nom.contains("virtuel") | s.nom
									.contains("Repere"))) {
						s.x = pt1;
						s.y = pt2;
					}
				}

				teta = n * (Math.PI / instance);
				n += 2;
				st = "";
			}
		}

	}

	/**
	 * Construit un graphe a partir de sa matrice sous forme d'un fichier texte.
	 */
	public void ChargerMatriceAsymetrique(String nom_fichier, int periode) {
		int M = 0;
		int N = 0;
		int ptx = 0;
		int pty = 0;
		int ptz = 0;
		int nb_aretes = 0;
		double max = 0.0;
		String InstanceFile = nom_fichier.concat(".") + periode;
		instance = periode;
		try {
			File file = new File(InstanceFile);
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);
			ligne_doc = line.readLine();

			StringTokenizer labc = new StringTokenizer(ligne_doc);
			String lab = labc.nextToken();
			/* Lecture des sommets en colonne */
			while (lab != null && !lab.equals("CLASSE")) {
				ptx = 10 + (int) (730 * Math.random());
				pty = 10 + (int) (750 * Math.random());
				ptz = 10 + (int) (750 * Math.random());

				// Creation du sommet correspondant a la colonne en cours
				Sommet s = new Sommet(ptx, pty, ptz, lab, 0, 0, 0, 0, 0);
				s.type = 0;
				s.metrique = 0;
				ajouterSommet(s);
				try {
					lab = labc.nextToken();
				} catch (NoSuchElementException ex) {
					break;
				}
				N++;
			}
			System.out.println("Fin de lecture des " + N
					+ " sommets en colonnes.... ");

			// Création des sommets en ligne
			while ((ligne_doc = line.readLine()) != null) {
				labc = new StringTokenizer(ligne_doc);
				lab = labc.nextToken();
				ptx = 10 + (int) (730 * Math.random());
				pty = 10 + (int) (750 * Math.random());
				ptz = 10 + (int) (750 * Math.random());
				Sommet s = new Sommet(ptx, pty, ptz, lab, 0, 0, 0, 0, 0);
				s.type = 1;
				s.metrique = 0;
				ajouterSommet(s);
				// s.setInvisible();
				M++;
			}
			line.close();
			int perimetre = ((largeur + longueur) * 2) / (instance);
			int p = perimetre;
			CalculRepere(largeur, longueur, perimetre);
			if (instance > 1) {
				M += instance;
			}
			System.out.println("Fin de lecture des " + M
					+ " sommets en lignes.... ");
			// matrice = new double[M][N];

			// a ce stade, nous avons cree tous les (N) sommets "colonnes" et
			// sommets "lignes"
			// prochaine etape : creation des aretes entre ces sommets

			// lecture des lignes de la matrice ligne par ligne
			for (int inst = 1; inst <= instance; inst++) {
				InstanceFile = nom_fichier.concat(".") + inst;
				// System.out.println("Traitement du fichier : " +
				// InstanceFile);

				file = new File(InstanceFile);
				rd_result = new FileReader(file);
				line = new LineNumberReader(rd_result);

				ligne_doc = line.readLine();
				ligne_doc = line.readLine();
				int l = 0; // leme ligne <=> leme sommet du graphe

				// lecture des cases de chaque ligne (case par case)
				while (ligne_doc != null && l < M + 1 - instance) {
					StringTokenizer ligne = new StringTokenizer(ligne_doc);
					lab = ligne.nextToken();
					// System.out.println("autre: "+ lab);
					int j = 0;
					Sommet s;
					if (instance > 1)
						s = getSommet(N + l + instance); // N+l sommet dont le
															// label=lab
					else
						s = getSommet(N + l);

					while (ligne.hasMoreTokens() && j < N
							&& j < N + 1 - instance) {
						Sommet ss = getSommet(j); // jieme colonne

						String str = ligne.nextToken();
						str = str.replace(',', '.');
						try {

							double val = Double.parseDouble(str);
							// une case non nulle => creation d'une arete
							if (val != 0.0) {
								if (instance > 1) {
									s.Metrique[inst] += val;
									ss.Metrique[inst] += val;
									if (max <= ss.Metrique[inst])
										max = ss.Metrique[inst];
									if (max <= s.Metrique[inst])
										max = s.Metrique[inst];

								} else {
									if (s.Metrique[inst] <= val)
										s.Metrique[inst] = val;
									if (ss.Metrique[inst] <= val)
										ss.Metrique[inst] = val;
									if (max <= val)
										max = val;

								}

								int indice = areteDansGraphe(s, ss);
								if (indice != -1) {
									Arete a = getArete(indice);
									a.addVal(val);
									a.setValInstance(val, inst);
									String nom;
									val = a.getVal();
									if (val < 1.0)
										nom = String.valueOf(val);
									else
										nom = String.valueOf((int) val);
									a.setNom(nom);
								} else {
									Arete a = new Arete(s, ss, s.x, ss.x, null,
											val, nb_aretes++);
									ajouterArete(a);
									a.setValInstance(val, inst);

									String nom;
									val = a.getVal();
									if (val < 1.0)
										nom = String.valueOf(val);
									else
										nom = String.valueOf((int) val);
									a.setNom(nom);
								}
							}
						} catch (NumberFormatException ex) {
							System.out.println("Lecture de la matrice : Format des valeurs incompatible");
						}
						j++;
					}
					l++;
					ligne_doc = line.readLine();
				}
				line.close(); // fin de lecture du fichier
			}
		} catch (IOException e2) {
			System.err.println(e2);
			System.exit(1);
		} catch (NullPointerException e3) {
			System.out.println("\nNullPointerException : " + e3);
		}
		int k = 1;
		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.metrique = 0.0;
			Sommet SVirtuel;
			for (k = 1; k <= periode; k++) {
				SVirtuel = getSommet(M + N - (k));
				if (v.metrique <= v.Metrique[k])
					v.metrique = v.Metrique[k];
				// if ( max <= v.Metrique[k] ) max = v.Metrique[k];
				if (periode > 1) {
					if (v.Metrique[k] != 0) {
						Arete aVirtuelle = new Arete(SVirtuel, v, SVirtuel.x,
								v.x, null, v.Metrique[k], nb_aretes++);
						ajouterArete(aVirtuelle);
						aVirtuelle.setValInstance(v.Metrique[k], k);
						aVirtuelle.setInvisible();
						aVirtuelle.setValInstance(v.Metrique[k], k);
					}
				}
			}
		}

		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.Metrique[periode + 1] = max;
		}
		cercle = 1;
		if (periode > 1)
			cercle = 2;
		System.gc();
		maximumArete();
	}

	public boolean isEmpty() {
		return (sommets.size() == 0);
	}

	public int nombreSommets() {
		return sommets.size();
	}

	public int nombreAretes() {
		return aretes.size();
	}

	

	public Sommet getSommet(int i) {
		return (Sommet) sommets.elementAt(i);
	}

	public Arete getArete(int i) {
		return (Arete) aretes.elementAt(i);
	}

	public boolean getOriente() {
		return oriente;
	}

	/**
	 * ajoute un sommet dans le graphe. La methode verifie si aucun sommet de
	 * l'ensemble des sommets du graphe n'est equivalent au sommet a ajouter,
	 * puis fait l'ajout si ce n'est pas le cas.
	 */

	public void ajouterSommet(Sommet sommet) // si sommet existe dans vecteur
												// sommets alors break, sinon
												// ajouter le dans le vecteur
	{
		for (int i = 0; i < sommets.size(); i++)
			if (sommet.equals((Sommet) sommets.elementAt(i)))
				return;
		sommets.addElement(sommet);
	}

	/**
	 * ajoute un ensemble de sommets.
	 * 
	 * @param sommetsA
	 *            sommets a ajouter
	 */
	public void ajouterSommets(Sommet[] sommetsA) {
		for (int i = 0; i < sommetsA.length; i++)
			ajouterSommet(sommetsA[i]);
	}

	/**
	 * Ajoute une arete dans le graphe. La methode verifie si l'arete se trouve
	 * deja dans le graphe avant d'effectuer l'ajout.
	 * 
	 * @param arete
	 *            arete a ajouter
	 */
	public void ajouterArete(Arete arete) {

		for (int i = 0; i < aretes.size(); i++)
			if ((oriente && arete.equalsOriented((Arete) aretes.elementAt(i)))
					|| (!oriente && arete.equals((Arete) aretes.elementAt(i))))
				return;
		ajouterSommet(arete.getE1());
		ajouterSommet(arete.getE2());
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = (Sommet) sommets.elementAt(i);
			if (s.equals(arete.getE1()))
				arete.setE1(s);
			else if (s.equals(arete.getE2()))
				arete.setE2(s);
		}
		Sommet s1 = arete.getE1();
		Sommet s2 = arete.getE2();
		int mx = (s1.getX() + s2.getX()) / 2;
		int my = (s1.getY() + s2.getY()) / 2;
		int p = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (a.paralleleA(arete, false) && a.getID() != arete.getID())
				p++;
		}
		my += 4 * Sommet.RAYON * p * (int) Math.pow(-1.0, p + 1);
		arete.setXYZ(mx, my, 0);
		aretes.addElement(arete);

		if (p > 0)
			complexite++;
	}

	/**
	 * ajoute un ensemble d'aretes.
	 * 
	 * @param aretesA
	 *            aretes a ajouter
	 */
	public void ajouterAretes(Arete[] aretesA) {
		for (int i = 0; i < aretesA.length; i++)
			ajouterArete(aretesA[i]);
	}

	/**
	 * ajoute un graphe dans ce graphe.
	 * 
	 * @param graphe
	 *            le graphe a ajouter
	 */
	public void ajouterGraphe(Graphe graphe) {
		int ns = graphe.nombreSommets();
		for (int i = 0; i < ns; i++)
			ajouterSommet(graphe.getSommet(i));

		int na = graphe.nombreAretes();
		for (int i = 0; i < na; i++)
			ajouterArete(graphe.getArete(i));
	}

	/**
	 * Calcule le degre d'un sommet d'indice "indice" dans le graphe. Le degre
	 * constitue le nombre d'aretes liees a ce sommet.
	 * 
	 * @param indice
	 *            l'indice du sommet dont on cherche le degre
	 */
	public int degre(int indice) {
		Sommet s = (Sommet) sommets.elementAt(indice);
		int deg = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (a.getVisible()) {
				Sommet s1 = a.getE1();
				Sommet s2 = a.getE2();

				if (s.equals(s1))
					deg++;
				if (s.equals(s2))
					deg++;
			}
		}
		return deg;
	}

	public void initDegre() {
		int ns = this.nombreSommets();
		for (int i = 0; i < ns; i++) {
			Sommet s = this.getSommet(i);
			s.setDegre((int) degre(i) / 2);
		}
	}

	/**
	 * Calcule le demi-degre interieur d'un sommet. Ce qui constitue le nombre
	 * d'aretes entrant dans ce sommet. La methode lance une exception si le
	 * graphe n'est pas oriente.
	 * 
	 * @param indice
	 *            indice du sommet dont on cherche le demi-degre interieur
	 * @exception NotOrientedGraphException
	 */
	public int demiDegreInterieur(int indice) {
		if (!oriente)
			throw new NotOrientedGraphException("Cannot calculate half-degrees");

		Sommet s = (Sommet) sommets.elementAt(indice);
		int deg = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (s.equals(a.getE2()))
				deg++;
		}
		return deg;
	}

	/**
	 * Calcule le demi-degre exterieur d'un sommet. Ce qui constitue le nombre
	 * d'aretes sortant dans ce sommet. La methode lance une exception si le
	 * graphe n'est pas oriente.
	 * 
	 * @param indice
	 *            indice du sommet dont on cherche le demi-degre exterieur
	 * @exception NotOrientedGraphException
	 */
	public int demiDegreExterieur(Sommet s) {
		if (!oriente)
			throw new NotOrientedGraphException(" graphe non oriente !!");

		// Sommet s = (Sommet)sommets.elementAt(indice);
		int deg = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (s.equals(a.getE1()))
				deg++;
		}
		return deg;
	}

	public Sommet pere(Sommet s) {
		Sommet pere = null;
		if (!oriente)
			throw new NotOrientedGraphException("graphe non oriente !!");
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();

			if (s.equals(s2))
				pere = s1;

		}
		return pere;

	}

	/**
	 * Retire le sommet du graphe ainsi que toutes ses aretes incidentes.
	 * 
	 * @param indice
	 *            indice du sommet a supprimer
	 */
	public void supprimerSommet(int indice) {
		Sommet s = (Sommet) sommets.elementAt(indice);
		int[] ind = new int[aretes.size()];
		int indi = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (s.equals(a.getE1()) || s.equals(a.getE2()))
				ind[indi++] = i;
		}
		for (int i = indi - 1; i >= 0; i--)
			supprimerArete(ind[i]);

		sommets.removeElementAt(indice);
	}

	/**
	 * Retire l'arete du graphe.
	 * 
	 * @param indice
	 *            indice de l'arete a retirer
	 */
	public void supprimerArete(int indice) {
		Arete a = (Arete) aretes.elementAt(indice);
		int p = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a2 = (Arete) aretes.elementAt(i);
			if (a == a2)
				continue;
			if (a.paralleleA(a2, false) && a.getID() != a2.getID())
				p++;
		}
		if (p == 1)
			complexite--;
		aretes.removeElementAt(indice);
	}

	/**
	 * Teste si le graphe est simple ou non. Pour ce faire, la methode verifie
	 * s'il y a des aretes paralleles ou des boucles. S'il n'y en a aucune, le
	 * graphe est simple
	 * 
	 * @return le resultat du test de simplicite du graphe
	 */
	public boolean grapheSimple() {
		if (complexite > 0)
			return false;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			if (a.getE1().equals(a.getE2()))
				return false; // Une boucle a ete trouvee.
		}
		return true;
	}

	/**
	 * Convertit le graphe en chaine affichable
	 * 
	 * @return chaine representant l'objet
	 */
	public String toString() {
		String str = "Graphe: (";
		str += "Sommets: (";
		for (int i = 0; i < sommets.size(); i++) {
			if (i > 0)
				str += ", ";
			str += sommets.elementAt(i);
		}
		str += "), Aretes: (";
		for (int i = 0; i < aretes.size(); i++) {
			if (i > 0)
				str += ", ";
			str += aretes.elementAt(i);
		}
		str += "))";
		return str;
	}

	/**
	 * Retourne le graphe sous-jacent d'un graphe oriente ou un graphe non
	 * oriente construit a partir d'un graphe non oriente.
	 * 
	 * @return objet Graphe resultant de la methode
	 */
	public Graphe sousJacent() {
		Graphe gr = new Graphe(!oriente);
		gr.ajouterGraphe(this);
		return gr;
	}

	/**
	 * Retourne l'indice d'un sommet. Si ce sommet ne fait pas partie du graphe,
	 * retourne -1. Les comparaisons sont effectuees avec le test d'egalite et
	 * non pas avec l'operateur ==.
	 */
	public int indiceSommet(Sommet s) {
		if (s == null)
			return -1;
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s2 = (Sommet) sommets.elementAt(i);
			if (s2.equals(s))
				return i;
		}
		return -1;
	}

	/*
	 * Retourne l'indice d'une arete du graphe. Si l'arete ne fait pas partie du
	 * graphe, la methode retourne -1.
	 */
	public int indiceArete(Arete a) {
		if (a == null)
			return -1;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a2 = (Arete) aretes.elementAt(i);
			if (a2.equals(a))
				return i;
		}
		return -1;
	}

	/**
	 * Teste si une arete definie par deux sommets fait partie du graphe
	 */
	public int areteDansGraphe(Sommet s1, Sommet s2) {
		for (int i = 0; i < nombreAretes(); i++) {
			Arete arete = getArete(i);
			Sommet s_or = arete.getE1();
			Sommet s_ar = arete.getE2();
			if ((s1.equals(s_or) && s2.equals(s_ar))
					|| (s2.equals(s_or) && s1.equals(s_ar)))
				return i;

		}
		return -1;
	}

	public int areteDansGraphe(Arete a) {
		for (int i = 0; i < nombreAretes(); i++) {
			if (a.equals(aretes))
				return i;
		}
		return -1;
	}
	/**
	 * 
	 * @param sommet1
	 * @param sommet2
	 * @return l'inverse du valeur du lien sommet1__sommet2 s'il existe sinon -1
	 */
	
	
	public double distance(Sommet sommet1 , Sommet sommet2){
		double distance = 0; 
		int indice = this.areteDansGraphe(sommet1,sommet2);
	    if(indice != -1){
	    	distance = 1/this.getArete(indice).getVal();
	    }else{
	    	distance = -1;
	    	System.out.println("Arete Inexistante !! " +sommet1.getNom()+"//"+sommet2.getNom() );
	    }
		 return distance;
	 }
	/**
	 * methode utilisee pour obtenir un objet Point qui contiendra des
	 * coordonnes aleatoires non occupees par un autre sommet du graphe.
	 * 
	 * @param maxw
	 *            longueur de la zone de selection
	 * @param maxh
	 *            largeur de la zone de selection
	 * @return point choisi aleatoirement
	 */
	public Point nouvellePosition(int maxw, int maxh) {

		if (maxw <= 0 || maxh <= 0) {
			maxw = getSize().width;
			maxh = getSize().height;
			throw new IllegalArgumentException(
					"la taille de la fenï¿½ï¿½tre doit ï¿½ï¿½tre positive.");

		}
		Random generator = new Random();
		Point pt;

		do {
			int a = Math.abs(generator.nextInt()) % maxw;
			if (a < 100)
				a += 50;
			if (a > (maxw - 100))
				a -= 10;
			int b = Math.abs(generator.nextInt()) % maxh;
			if (b < 100)
				b += 50;
			if (b > (maxh - 100))
				b -= 10;
			pt = new Point(a, b);
		} while (hitTest(pt, true) != null);
		return pt;
	}

	/**
	 * Effectue un test de tous les sommets afin de savoir quel sommet se trouve
	 * en un point donne, ou dans son voisinage.
	 * 
	 * @param pt
	 *            point a tester
	 * @param large
	 *            teste au voisinage des coordonnees du sommet, permettant ainsi
	 *            une gestion d'un clic de souris
	 * @return sommet se trouvant au point teste, null s'il ne s'y trouve aucun
	 *         sommet
	 */
	public Sommet hitTest(Point pt, boolean large) {
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			if (s.getVisible()) {
				if (large) {
					double dist = Math.sqrt(Math.pow(pt.x - s.getX(), 2.0)
							+ Math.pow(pt.y - s.getY(), 2.0));
					if (dist <= 10)
						return s;
				} else {
					if (s.getX() == pt.x && s.getY() == pt.y)
						return s;
				}
			}
		}
		return null;
	}

	/**
	 * retourne l'etiquette d'une arete du graphe apres l'avoir convertie en une
	 * valeur numerique.
	 * 
	 * @param indice
	 *            indice de l'arete a traiter
	 * @return etiquette convertie en valeur numerique, -1 si la conversion
	 *         n'est pas possible.
	 */
	public double nGetArete(int indice) {
		try {
			// return Integer.parseInt(getArete(indice).getNom());
			return getArete(indice).getVal();
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * retourne l'etiquette d'un sommet du graphe apres l'avoir convertie en
	 * valeur numerique.
	 * 
	 * @param indice
	 *            indice du sommet a traiter
	 * @return etiquette du sommet en valeur numerique, -1 si la conversion est
	 *         impossible.
	 */
	public int nGetSommet(int indice) {
		try {
			return Integer.parseInt(getSommet(indice).getNom());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Affiche le graphe sur un contexte d'affichage, sans toutefois verifier si
	 * la surface d'affichage est suffisamment grande pour contenir tout le
	 * graphe. Aucune verification n'est faite pour savoir si du texte des
	 * etiquettes se chevauchent.
	 * 
	 * @param g
	 *            contexte d'affichage a utiliser
	 */
	public void reordonner(Sommet s, int w, int h) {
		int x = s.getX();
		int y = s.getY();

		if (x <= 0 && y <= 0) {
			s.x = 40;
			s.y = 10;
		} else if (x <= 0 && y > h - 80) {
			s.x = 40;
			s.y = h - 80;
		} else if (x > w && y > h - 80) {
			s.x = w - 40;
			s.y = h - 80;
		} else if (x > w && y < 0) {
			s.x = w - 40;
			s.y = 10;
		} else if (x > 0 && x < w && y <= 0) {
			s.x = x;
			s.y = 10;
		} else if (x <= 0 && y > 0 && y < h) {
			s.x = 40;
			s.y = y;
		} else if (x > 0 && x < w && y > h) {
			s.x = x;
			s.y = h - 80;
		} else if (x > w && y < h) {
			s.x = w - 20;
			s.y = y;
		} else {
			s.x = x;
			s.y = y;
		}

	}

	public void maximumMetrique(int instance) {
		maxMetrique1 = 0.0;
		maxMetrique2 = 0.0;
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			if (maxMetrique1 <= s.Metrique[instance] && s.type == 1
					&& s.getVisible()) // maximum sur les lignes
				maxMetrique1 = s.Metrique[instance];
			if (maxMetrique2 <= s.Metrique[instance] && s.type == 0
					&& s.getVisible()) // maximum sur les colonnes
				maxMetrique2 = s.Metrique[instance];
		}
	}

	public void maximumMetrique() {
		double max1 = 0.0, max2 = 0.0;
		double cl = 0.0;

		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			if (max1 <= s.metrique && s.type == 1 && s.getVisible()) // maximum
																		// sur
																		// les
																		// lignes
				max1 = s.metrique;
			if (max2 <= s.metrique && s.type == 0 && s.getVisible()) // maximum
																		// sur
																		// les
																		// colonnes
				max2 = s.metrique;
		}

		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			if (s.getVisible()) {
				if (typeMat == 0) {
					if (s.type == 1) {
						cl = (double) (s.metrique) / (double) (max1);
						cl = (double) (1 + Math.pow(cl, 1 / 2) - Math.pow(
								(1 - cl), 2)) / 2;
						s.couleur = new Color((int) (255 * cl), 0, 0);
					} else {
						cl = (double) (s.metrique) / (double) (max2);
						cl = (double) (1 + Math.pow(cl, 1 / 2) - Math.pow(
								(1 - cl), 2)) / 2;
						s.couleur = new Color(0, (int) (255 * cl), 0);
					}
				} else if (s.type == 1) {
					cl = (double) ((double) (s.metrique) / (double) max1);
					cl = (double) (1 + Math.pow(cl, 1 / 2) - Math.pow((1 - cl),
							2)) / 2;
					s.couleur = new Color((int) (255 * cl), 0, 0);
				}
			}
		}
		maxMetrique1 = max1;
		maxMetrique2 = max2;

	}

	public void maximumArete() {

		double maxi = 0;
		double min = 999999;
		// if (instance == 1){
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = getArete(i);
			if (maxi < a.getVal() && a.getVisible())
				maxi = a.getVal();
			if (min > a.getVal() && a.getVisible())
				min = a.getVal();
		}

		maxArete = maxi;
		minArete = min;
	}

	/* Calcule le degre d'un sommet dans un graphe donne */

	public int getDegre(Sommet s) {
		this.getVoisins(s, true, this.seuil);
		return (s.voisins.size());

	}

	public int maxVoisin(Graphe g) {
		int max = 0;
		for (int i = 0; i < g.nombreSommets(); i++) {
			Sommet s = g.getSommet(i);
			if (!s.nom.contains("virtuel") && !s.nom.contains("repere")) {
				if (aDesVoisins(s, true, 0)) {
					if (s.voisins.size() > max)
						max = s.voisins.size();
				}
			}
		}
		return max;
	}
	
	
	/**
	 * Dynamic representation of the centrality class : each class will be represented by circle 
	 * @param intensiteCouleur
	 * @param font
	 */
	
	
    public  void genCircularCentrality(Graphics g, double intensiteCouleur, Graphe gr ){
    	//gp.degreSimple(gp.gr);
    	
    	Map<Integer , Vector<Sommet>> colorClass = new HashMap<Integer, Vector<Sommet>>();
		double maxDegreeCentrality = gr.maxDegreeCentrality(gr);
		for(int i = 1; i< intensiteCouleur; i++){
            Vector<Sommet> list = new Vector<Sommet>();
			for(int j =0; j<sommets.size(); j++){
				Sommet s = (Sommet) sommets.get(j);
				if(s.getVisible() == true){
				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
				if(s.getCentralite_degre() > 0){
                  double val1 = maxDegreeCentrality*i/intensiteCouleur;
                  double val2 = (maxDegreeCentrality*(i+1))/intensiteCouleur;
                  if(s.getCentralite_degre() < val2 && s.getCentralite_degre() >= val1){
                	  list.add(s);
                  }
				}else{
					s.setInvisible();
				}
				}else{
					s.setInvisible();
				}
				  				
				}
			}
		
			colorClass.put(i, list);
		}
		
		double w = getSize().getWidth() - 20;
		double h = getSize().getHeight() - 40;
		double rX, rY;

		rX = w / 2.0;
		rY = h / 2.0;

		if (typeMat == 1 || typeMat == 2) {
			for(int i = 1; i< intensiteCouleur; i++){
				double theta1 = 3 * Math.PI / 2;
				double delta1 = 2 * Math.PI /colorClass.get(i).size() ;
			for (int j = 0; j < nombreSommets(); j++) {
				Sommet s = (Sommet) sommets.get(j);
				if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
					if (s.type == 1 && s.getVisible()) {
						if(colorClass.get(i).contains(s)){
						s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
						s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
						theta1 += delta1;
					 //  s.setCouleur(new Color((int) intensiteCouleur, 0, 0) );
						double d=   ((intensiteCouleur*15)/i);
						g.setColor(new Color((int)d, 0, 0));
						double cl1 = s.getCentralite_degre()/maxDegreeCentrality;
						double quantite = 10 * Math.sqrt(cl1);
						if (quantite < 1.0) // c.a.d cl < 0.1
							quantite = 1.0;

						g.fillOval((int) (s.x - quantite - 1), (int) (s.y - quantite - 1),
								2 * Math.round((float) quantite) + 1,
								2 * Math.round((float) quantite) + 1);

						}
					}	
				}
			}
		}
		}
	    }
	/*
	 * Attribue une couleur pour chaque arete en fonction de sa valeur dans
	 * l'intervalle des valeurs [min, max] et de l'intensitï¿½ï¿½ utilisee. Gere
	 * aussi la couleur des aretes dans le morphing
	 */
	public synchronized void SetCouleursAretes(double intensite, int fond) {
		double cl = 0.0;
		double metrique = 0.0;

		int v = 0;
		int val1 = 0;
		int val2 = 0;
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = getArete(i);
			if (maxArete < a.getVal())
				maxArete = a.getVal();
		}

		for (int i = 0; i < aretes.size(); i++) {
			/*
			 * Pour chaque arete, on va prendre la valeur de l'arete (y compris
			 * celle additionnant les autres instances) et on va la normaliser
			 * de facon ï¿½ï¿½ trouver un juste ï¿½ï¿½quilibre dans l'affichage,
			 * au niveau intensitï¿½ï¿½
			 */
			Arete a = (Arete) aretes.elementAt(i);
			if (a.getVisible()) {
				metrique = (double) (a.getVal() / maxArete);

				/*
				 * on regle l'intensitï¿½ï¿½ d'affichage. C'est ici que se
				 * gï¿½ï¿½re la difference d'intensite du slider
				 * intensite
				 */
				// int v = (int)(250 - 200*cl);
				// int v = (int)(240 - 200*cl);

				if (fond == 0) {
					cl = (double) (1.0 + Math.pow(metrique, 1 / intensite) - Math
							.pow(1 - metrique, intensite)) / 2.0;
					val1 = 230;
					val2 = 225;
				} else {
					cl = (double) (1.0 + Math.pow(1 - metrique, intensite) - Math
							.pow(metrique, 1 / intensite)) / 2.0;
					val1 = 240;
					val2 = 200;
				}

				v = (int) (val1 - val2 * cl);
				a.setCouleur(new Color(v, v, v));
				Sommet S1 = a.getE1();
				Sommet S2 = a.getE2();
				if ((S1.nom.contains("virtuel") | S2.nom.contains("virtuel")))
					a.setInvisible();
				if (S1.nom.contains("virtuel"))
					S1.setInvisible();
				if (S2.nom.contains("virtuel"))
					S2.setInvisible();

			}
		}
	}

	public void AfficheRepere(int repmorph) {
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = (Sommet) sommets.elementAt(i);
			if (repmorph == 1) {
				if (s.nom.contains("virtuel")) {
					String inter = s.nom.substring(15, 16);
					s.setNom("Repere" + inter);
					s.setVisible();
					repaint();
				}
			}
			if (repmorph == 0) {
				if (s.nom.contains("Repere")) {
					String inter = s.nom.substring(6, 7);
					s.setNom("Sommet_virtuel " + inter);
					s.setInvisible();
					repaint();
				}
			}
		}

	}
//	public void periodicCentrality(Graphe graphe, int periode , int type , String nom_fichier, double alpha ){ 
//		if(type  == 1 || type == 2){ // matrice symetrique : simple ou temporelle
//       		Graphe gr = new Graphe(false);
//       		gr.enregistre(nom_fichier, periode);
//            gr.ChargerMatSymetrique(nom_fichier, periode);
//            for (int k= 0; k < gr.nombreSommets(); k++) {
//    			Sommet s = gr.getSommet(k);
//    			gr.getVoisins(s, false, gr.seuil);
//    			s.nbvoisins = s.voisins.size();
//    		}
//            
//    		GraphPanel gp= new GraphPanel(gr, true);
//        	gp.degreSimple(gr);
//        	gp.degree_gen(gr);
//        	//gp.closenessCentralityfreeMan(gp, alpha);
//        	//gp.closenessCentralityGen(gp, alpha);
////        	gp.betweennessCentralityGen(gp, 0);
////        	gp.betweennessCentralityGen(gp, alpha);
//        	for(int j = 0; j< gr.sommets.size(); j++){
//        		if( ! gr.getSommet(j).getNom().contains("virtuel")){
//        		//System.out.println(gr.getSommet(j).getNom() + "  ----> "+ gr.getSommet(j).getCentralite_degre()); 
//        		graphe.getSommet(j).getPeriodicDegree().put(periode, gr.getSommet(j).getCentralite_degre());
//        		graphe.getSommet(j).getPeriodicLink().put(periode, gr.getSommet(j).getCentralite_poids());
////        		graphe.getSommet(j).getPeriodicproximity().put(periode, gr.getSommet(j).getClosenessCentrality());
////        		graphe.getSommet(j).getPeriodicintermediarity().put(periode, gr.getSommet(j).getBetweennessCentrality());
////        		graphe.getSommet(j).getPeriodicCombinedDegree().put(periode, gr.getSommet(j).getCentralite_combine());
////        		graphe.getSommet(j).getPeriodicGenIntermediarity().put(periode, gr.getSommet(j).getBetweennessGenCentrality() );
////        		graphe.getSommet(j).getPeriodicGenProximity().put(periode, gr.getSommet(j).getClosenessGenCentrality());
//        	}
//		}
//        	for(int ll = 0 ; ll< gr.sommets.size() ; ll++){
//        		if(gr.getSommet(ll).voisins.size() > 0 &&  !gr.getSommet(ll).getNom().contains("virtuel")){
//        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicDegree().get(periode));
//        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicLink().get(periode));
//        		//System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicCombinedDegree().get(periode));
//        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicintermediarity().get(periode));
//        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicproximity().get(periode));
////        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicGenIntermediarity().get(periode));
////        		System.out.println(gr.getSommet(ll).getNom() + "  ----> "+ gr.getSommet(ll).getPeriodicGenProximity().get(periode));
//        		} else {
//            		System.out.println("noeud " + gr.getSommet(ll).getNom() + "   est isolé  !!");
//            		
//        		}
//        	}
//        	System.out.println("*************************************************************");
//          // }
//        	// 
//		}else if(type == 0 || type == 3){ // pas de mesures de centralités relatifs aux graphe bipartis
//			
//		}
//	}
	 public synchronized void afficherTempCentrality(Sommet s , Graphics g,String Mesure ,  boolean Etiquette,
	 			boolean Longs,  int periode, boolean clustering,
	 			int couleur_fond, int repmorph, double IntensitePolice,
	 			double taillePolice){
            int per = periode;
	 		Color old = g.getColor();
	 		double quantite = 0.0;
	 		Graphics2D g2 = (Graphics2D) g;
	 		if (s.getVisible()) {
	 			// System.out.println("on est dans afficher instance");
	 			// instances sous forme de barres
	 			for (int i = 1; i <= periode; i++) {
	 				if(Mesure == "Degree"){
	 				if(!s.getNom().contains("Rep") && s.getPeriodicDegree().get(i) != null )
	 				quantite = 20 * s.getPeriodicDegree().get(i) / maxDegreeCentrality;
	 				else quantite = 0;
	 				} else  if(Mesure == "Strength"){
	 					if(!s.getNom().contains("Rep") && s.getPeriodicLink().get(i) != null )
	 					quantite = 20 * s.getPeriodicLink().get(i) / maxStrengthCetrnality;
	 					else quantite = 0;
	 				}
	 				else if(Mesure == "Proximity"){
	 					if(!s.getNom().contains("Rep") && s.getPeriodicproximity().get(i) != null )
	 					quantite = 20 * s.getPeriodicproximity().get(i) / maxPoximityCentrality;
	 					else quantite = 0;
	 				}else if(Mesure == "Intermediarity"){
	 					if(!s.getNom().contains("Rep") && s.getPeriodicintermediarity().get(i) != null )
	 					quantite = 20 * s.getPeriodicintermediarity().get(i) / maxBetweennessCentrality; // probleme des valeurs nullllll nom && denom
	 					else quantite = 0;
	 				}
	 				if (s.type == 1 && !clustering) {
	 					g2.setColor(new Color(255, 0, 0));
	 				}
//	 			
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

	 				if (quantite == 0.0 && !s.nom.contains("virtuel")) {

	 					g2.setColor(Color.gray);
	 					g.drawLine(s.x - 5 + 5 * (i - 1), s.y, s.x - 5 + 5 * i, s.y);
	 				} else {
	 					if (Math.round(quantite) != 0) {
	 						g.fill3DRect(s.x - 5 + 5 * (i - 1),
	 								s.y - 2 * (int) Math.round(quantite), 5,
	 								2 * (int) Math.round(quantite), true);
	 					} else
	 						g.fill3DRect(s.x - 5 + 5 * (i - 1), s.y - 1, 5, 1, true);
	 				}
	 				if (s.type == 1 && clustering) {
	 					g.setColor(s.couleurClasse);
	 				}
	 				if (s.type == 0 && !s.nom.contains("virtuel")) {
	 					g2.setColor(Color.green);
	 				}
	 				if ((quantite == 0.0) && !s.nom.contains("virtuel")) {
	 					g2.setColor(Color.gray);
	 					g.drawLine(s.x - 5 + 5 * (i - 1), s.y, s.x - 5 + 5 * i, s.y);
	 				} else {
	 					if (Math.round(quantite) != 0 && !s.nom.contains("virtuel"))
	 						g.fill3DRect(s.x - 5 + 5 * (i - 1),
	 								s.y - 2 * (int) Math.round(quantite), 5,
	 								2 * (int) Math.round(quantite), true);
	 					else
	 						g.fill3DRect(s.x - 5 + 5 * (i - 1), s.y - 1, 5, 1, true);
	 				}
	 			}
	 			int tail = (int) (taillePolice);
	 			Font ft = new Font("Time", Font.PLAIN, tail);
	 			if (repmorph == 1) {
	 				g.setFont(ft);
	 				g.setColor(Color.red);
	 				String Nom = (Longs && s.nomlong != null) ? s.nomlong : s.nom;
	 				if (Nom.contains("Repere"))
	 					g.drawString(Nom, s.x + 5, s.y + 3 * Sommet.RAYON);
	 			}
	 			if (!Etiquette) { //  sans etiquette 
	 				String Nom = (Longs && s.nomlong != null) ? s.nomlong : s.nom;
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
	 					g.drawString(Nom, s.x + 5, s.y + 3 * Sommet.RAYON);
	 				}

	 			}
	 		}
	 		g.setColor(old); // permet de ne pas alterer l'etat du contexte tel
	 							// qu'il est recu
	 		// clustering=false;
	 	
	    	 
	     }
	 int getDistributionClass(Graphe gr , int classe){
		 int compteur = 0;
		 for(int i = 0; i<gr.nombreSommets() ; i++){
			 Sommet s = gr.getSommet(i);
			 if(s.getClassDegreeCentrality() == classe )
				 compteur++;
		 }
		 
		 
		 return compteur;
		 
	 }
	 public void circularDegreeCentralityGraph( Graphe gr) {
			//GraphPanel gp  = new GraphPanel(gr, true);
			//gp.degreSimple(gr);
			double maxDegreeCentrality = gr.maxDegreeCentrality(gr);
			for(int i = 0 ; i<gr.sommets.size() ; i++){ 		
				Sommet s = gr.getSommet(i);
	            double max = maxDegreeCentrality/5;
	            if(s.getCentralite_degre() > 0){
	            if(s.getCentralite_degre() < max){ 
	            	s.setClassDegreeCentrality(1);
	            	}
	            else if(( max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (2)*max)) {
	            	s.setClassDegreeCentrality(2);
	            	} 
	            else if(( (2)*max < s.getCentralite_degre()) &&(s.getCentralite_degre() < (3)*max)){
	            	s.setClassDegreeCentrality(3);
	            	}
	            else if(( (3) * max < s.getCentralite_degre()) &&(s.getCentralite_degre()< (4)*max)){
	            	s.setClassDegreeCentrality(4);
	            	}
	            else {
	            	s.setClassDegreeCentrality(5);
	            	}
	            }else{
	            	s.setInvisible();
	            }
			
			}
	//	   classDegreeCentrality(gr);
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
						if(s.getClassDegreeCentrality() == 1){
						classe1.add(s);
						//s.setCouleur(s.couleurCentral1);
						nb_classe1++;
						}else if(s.getClassDegreeCentrality() == 2){
							classe2.add(s);
							//s.setCouleur(s.couleurCentral2);
							nb_classe2++;
						}else if(s.getClassDegreeCentrality()== 3){
							classe3.add(s);
						//	s.setCouleur(s.couleurCentral3);
							nb_classe3++;
						}else if(s.getClassDegreeCentrality()== 4){
							classe4.add(s);
							//s.setCouleur(s.couleurCentral4);
							nb_classe4++;
						}else if(s.getClassDegreeCentrality()== 5){
							classe5.add(s);
							//s.setCouleur(s.couleurCentral5);
							nb_classe5++;
						}
						
						//s.setCouleur(new Color((12 * s.getClassDegreeCentrality()), 0, 0));

						
						
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

			if (typeMat == 1 ||typeMat == 2) {
				for (int i = 0; i < gr.nombreSommets(); i++) {
					Sommet s = gr.getSommet(i);
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
//			if (gr.typeMat == 1 || gr.typeMat == 2) {
//				if (cluster2) {
//					for (int i = 0; i < nbClusters; i++) {
//						int indice = ((Integer) (Clusters[i].elementAt(0)))
//								.intValue();
//						try {
//							Sommet s = gr.getSommet(indice);
//							s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
//							s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
//							theta1 += delta1;
//							for (int j = 1; j < Clusters[i].size(); j++) {
//								int ind = ((Integer) (Clusters[i].elementAt(j)))
//										.intValue();
//								Sommet ss = gr.getSommet(ind);
//								ss.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
//								ss.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
//								theta1 += delta1;
//							}
//						} catch (ArrayIndexOutOfBoundsException e) {
//							// System.out.println("\nArrayIndexOutOfBoundsException : "
//							// + e );
//							continue;
//						}
//					}
//				} else
//					for (int i = 0; i < gr.nombreSommets(); i++) {
//						Sommet s = gr.getSommet(i);
//						if (!s.nom.contains("virtuel") && !s.nom.contains("Repere")) {
//							if (s.type == 1 && s.getVisible()) {
//								s.x = (int) (w / 2 + (rX - 16) * Math.cos(theta1));
//								s.y = (int) (h / 2 + (rY - 16) * Math.sin(theta1));
//								theta1 += delta1;
//							}
//						}
//					}
//			}
		}
	 
	 //***********************************************************************
	 public void start() {
			distance = new Thread(this);
			distance.start();
		}

	 @Override
		public void run() {

			Thread anime = Thread.currentThread();
			while (anime == distance) {
				Dijkstra d = new Dijkstra(this, 1);
			}

		 
		}
	 
	 //*************************************************************************
	public void paintComponent(Graphics g, int param, int repmorph,
			int couleur_fond, double IntensitePolice, double taillePolice,
			int compteur,  double puissance ) {
		super.paintComponent(g);
		int k= 1;
//		while(k<= instance){
//			periodicCentrality(this , k, 1,pathFile , 1);
//			k++;
//			}
		AfficheRepere(repmorph);
		// SetCouleursAretes(intensite, Couleur_fond, affichage morphing);
		if (couleur_fond == 1)
			SetCouleursAretes(intensite, 1);
		if (couleur_fond == 0)
			SetCouleursAretes(intensite, 0);

		for (int i = 0; i < aretes.size(); i++) {
			Arete a = (Arete) aretes.elementAt(i);
			a.afficher(g, false, oriente, a.getCouleur());

		}

		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = (Sommet) sommets.elementAt(i);
			if(s.getNom() == "CLASS" || s.getNom() == "class") s.setInvisible();
			if(s.getNom() == "POIDS") s.setInvisible();
			if (s.nom.contains("virtuel"))
				s.setInvisible();
			if (typeGraphe == 1) { 
				if (s.type == 1){ // sommet sous forme ciculaire
					if(! afficherDegreeClass && !afficherGenDegreeClass && ! afficherGenIntermediarityClass
							&& !afficherIntermediarityClass && !afficherProximityClass && !afficherGenProximityClass && !afficherLinkClass){
						s.afficher(g, sans, maxMetrique1, longs, cercle,
								clustering, rang, param, couleur_fond, repmorph,
								IntensitePolice, taillePolice, compteur,
								pre_instance);	
					}   
                      // on afficher par défaut les nuances de couleurs suivant la mesure choisit par User
					else if(afficherDegreeClass){
						//classDegreeCentrality(this, slider77);
						s.afficherDegre(g, maxMetrique1, cercle, maxDegreeCentrality(this) );
						if(stressDegree){
						s.epinglerDegree(g ,  "degre", maxDegreeCentrality(this) );
						}else{
							s.epinglerDegree(g ,  "", 1 );
						}

					}
					else if(afficherIntermediarityClass){
						s.afficherBetweennessFreeMan(g, maxMetrique1, cercle , maxIntermediarityCentrality(this) );
						
						if(stressIntermedirity){
							s.epinglerDegree(g ,  "intermediarity" , maxIntermediarityCentrality(this));
						}else{
							s.epinglerDegree(g ,  "" , 1);
						}
					}else if(afficherGenIntermediarityClass){
						s.afficherBetweennessGen(g , maxMetrique1, cercle , maxGenIntermediarityCentrality(this) );
						
						if(stressGenIntermediarity){
							s.epinglerDegree(g ,  "genIntermediarity"  , maxGenIntermediarityCentrality(this));
						}else{
							s.epinglerDegree(g ,  "" , 1);
						}
					}else if(afficherGenDegreeClass){
						s.afficherDegreCombine(g , maxMetrique1, cercle, maxGenDegreeCentrality(this) );
						
						if(stressCombinedDegree){
							s.epinglerDegree(g ,  "CombinedDegre" , maxGenDegreeCentrality(this));
						}else{
							s.epinglerDegree(g ,  "" ,1);
						}
					}else if(afficherProximityClass){
						s.afficherClosenessFreeMan(g , maxMetrique1, cercle, maxProximityCentrality(this) );
					
						if(stressProximity){
							s.epinglerDegree(g ,  "proximity" , maxProximityCentrality(this));
						}else{
							s.epinglerDegree(g ,  ""  ,1 );
						}
					}else if(afficherGenProximityClass){
						s.afficherClosenessGen(g, maxMetrique1, cercle , maxGenProximityCentrality(this));
						if(stressGenProximity){
							s.epinglerDegree(g ,  "genProximity" , maxGenProximityCentrality(this));
						}else{
							s.epinglerDegree(g ,  "" , 1);
						}
					}else if(afficherLinkClass){
						s.afficherpoids(g , maxMetrique1, cercle , maxLinkCentrality(this) );
					
						if(stressStrength){
							s.epinglerDegree(g ,"strength" , maxLinkCentrality(this));
						}else{
							s.epinglerDegree(g ,  "" , 1);
						}
					}
 				}
				if (s.type == 0)
					s.afficher(g, sans, maxMetrique2, longs, cercle,
							clustering, rang, param, couleur_fond, repmorph,
							IntensitePolice, taillePolice, compteur,
							pre_instance);
			} else { // typeGraphe == 2     => graph temporel
				if (s.type == 1){
					if(centralityOn == false){
					s.afficherInstances(g, sans, longs, maxMetrique1, instance,
							clustering, couleur_fond, repmorph,
							IntensitePolice, taillePolice);
					}else {
						
						
						if(tempDegree){
							afficherTempCentrality(s, g,"Degree", sans ,longs, instance , clustering , couleur_fond , repmorph , IntensitePolice , taillePolice);
						}else if(tempStrength){
							afficherTempCentrality( s, g,"Strength", sans ,longs, instance , clustering , couleur_fond , repmorph , IntensitePolice , taillePolice);
						}else if(tempProximity){
							afficherTempCentrality(s, g,"Proximity", sans ,longs, instance , clustering , couleur_fond , repmorph , IntensitePolice , taillePolice);
						}else if(tempIntermediarity){
							afficherTempCentrality(s, g,"Intermediarity", sans ,longs, instance , clustering , couleur_fond , repmorph , IntensitePolice , taillePolice);
						}
						
					}
					
				}
				if (s.type == 0){
					s.afficherInstances(g, sans, longs, maxMetrique2, instance,
							clustering, couleur_fond, repmorph,
							IntensitePolice, taillePolice);
				}
				}
			
			
		}
		if (stress)
			for (int i = 0; i < aretes.size(); i++) {
				Arete a = (Arete) aretes.elementAt(i);
				a.afficherValeur(g);
			}
		


	}

	public void getVoisins(Sommet autre, boolean flag, double seuil) {
		int na = aretes.size();
		int j = 0;
		autre.voisins = new Vector();

		for (int i = 0; i < na; i++) {
			Arete a = (Arete) aretes.elementAt(i);

			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();
			if (a.getVal() >= seuil) {
				if (!flag) { // graphe non orientï¿½ï¿½
					if (autre.equals(s1) && !autre.voisins.contains(s2)
							&& (!autre.nom.contains("virtuel"))
							&& (!s2.nom.contains("virtuel"))
							&& (!autre.nom.contains("Repere"))
							&& (!s2.nom.contains("Repere"))) {
						autre.voisins.addElement(s2);
						j++;
					} else if (autre.equals(s2) && !autre.voisins.contains(s1)
							&& (!autre.nom.contains("virtuel"))
							&& (!s1.nom.contains("virtuel"))
							&& (!autre.nom.contains("Repere"))
							&& (!s1.nom.contains("Repere"))) {
						j++;
						autre.voisins.addElement(s1);
					}
					autre.voisins.setSize(j);
				} else { // graphe orientï¿½ï¿½
					if (autre.equals(s1) && !autre.equals(s2)
							&& !autre.voisins.contains(s2)
							&& (!autre.nom.contains("virtuel"))
							&& (!s2.nom.contains("virtuel"))) {
						autre.voisins.addElement(s2);
						j++;
					}
					autre.voisins.setSize(j);
				}
			}
		}
	} // fin getVoisins

	public void getTOUSVoisins(Sommet autre, boolean flag, double seuil) {
		int na = aretes.size();
		int j = 0;
		autre.voisins = new Vector();

		for (int i = 0; i < na; i++) {
			Arete a = (Arete) aretes.elementAt(i);

			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();
			if (a.getVal() >= seuil) {
				if (!flag) { // graphe non orienté
					if (autre.equals(s1)) {
						autre.voisins.addElement(s2);
						j++;
					} else if (autre.equals(s2)) {
						j++;
						autre.voisins.addElement(s1);
					}
					autre.voisins.setSize(j);
				} else { // graphe orienté
					if (autre.equals(s1) && !autre.equals(s2)) {
						autre.voisins.addElement(s2);
						j++;
					}
					autre.voisins.setSize(j);
				}
			}
		}
	} // fin getVoisins

	public boolean aDesVoisins(Sommet autre, boolean flag, double seuil) {
		int na = aretes.size();
		int j = 0;
		autre.voisins = new Vector();

		for (int i = 0; i < na; i++) {
			Arete a = (Arete) aretes.elementAt(i);

			Sommet s1 = a.getE1();
			Sommet s2 = a.getE2();
			if (a.getVal() >= seuil) {
				if (!flag) { // graphe non orientï¿½ï¿½
					if (autre.equals(s1) && !autre.voisins.contains(s2)) {
						autre.voisins.addElement(s2);
						j++;
					} else if (autre.equals(s2) && !autre.voisins.contains(s1)) {
						j++;
						autre.voisins.addElement(s1);
					}
					autre.voisins.setSize(j);
				} else { // graphe orientï¿½ï¿½
					if (autre.equals(s1) && !autre.equals(s2)
							&& !autre.voisins.contains(s2)) {
						autre.voisins.addElement(s2);
						j++;
					}
					autre.voisins.setSize(j);
				}
			}
		}
		if (j != 0)
			return true;
		else
			return false;
	} // fin getVoisins

	// Algorithme Prim pour determiner un MST : Maximal Spanning Tree
	public Graphe determineArbrePrim() {
		Graphe arbre = new Graphe(false);
		Vector sommets_depart = new Vector();
		Vector sommets_arrivee = new Vector();
		double maxi = 0.0;
		Arete grande_arete = null;

		// recherche de la plus grande arete
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = ((Arete) aretes.elementAt(i));
			if (a.getVal() > maxi) {
				maxi = a.getVal();
				grande_arete = a;
			}
		}
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			sommets_arrivee.addElement(s);
		}

		Sommet s1 = grande_arete.getE1();
		Sommet s2 = grande_arete.getE2();

		sommets_depart.addElement(s1);
		sommets_depart.addElement(s2);

		sommets_arrivee.removeElement(s1);
		sommets_arrivee.removeElement(s2);

		grande_arete.marked = true;
		arbre.ajouterArete(grande_arete);
		// s1.fixe = true;
		// s2.fixe = true;

		while (!sommets_arrivee.isEmpty()) {
			Arete areteMax = null;
			int sens = 0;
			double max = 0.0;
			for (int i = 0; i < nombreAretes(); i++) {
				Arete arete = getArete(i);

				if (arete.getVisible() && !arete.marked) {
					s1 = arete.getE1();
					s2 = arete.getE2();
					if (sommets_depart.contains(s1)
							&& !sommets_arrivee.contains(s1)
							&& sommets_arrivee.contains(s2)
							&& !sommets_depart.contains(s2) && s1.getVisible()
							&& s2.getVisible() && arete.getVal() >= max) {
						max = arete.getVal();
						areteMax = arete;
						sens = 1;
					} else if (sommets_depart.contains(s2)
							&& !sommets_depart.contains(s1)
							&& sommets_arrivee.contains(s1)
							&& !sommets_arrivee.contains(s2) && s1.getVisible()
							&& s2.getVisible() && arete.getVal() >= max) {
						max = arete.getVal();
						areteMax = arete;
						sens = 2;
					}
				}
			}

			if (areteMax != null) {
				if (sens == 1) {
					sommets_depart.addElement(areteMax.getE2());
					sommets_arrivee.removeElement(areteMax.getE2());

				}
				if (sens == 2) {
					sommets_depart.addElement(areteMax.getE1());
					sommets_arrivee.removeElement(areteMax.getE1());
				}
				areteMax.marked = true;
				areteMax.setVisible();
				arbre.ajouterArete(areteMax);

			} else {
				System.out.println(" Le Graphe n'est pas connexe");
				return (this);
			}
		}
		arbre.maximumArete();
		arbre.stress = this.stress;
		arbre.noms = this.noms;

		return (arbre);
	}

	// Algorithme Prim pour determiner un MST : Minimal Spanning Tree
	public Graphe determineArbrePrim2() {
		Graphe arbre = new Graphe(false);
		Vector sommets_depart = new Vector();
		Vector sommets_arrivee = new Vector();
		double mini = 999999.0;
		Arete petite_arete = null;
		// recherche de la plus petite arete
		for (int i = 0; i < aretes.size(); i++) {
			Arete a = ((Arete) aretes.elementAt(i));
			if (a.getVal() < mini) {
				mini = a.getVal();
				petite_arete = a;
			}
		}
		for (int i = 0; i < sommets.size(); i++) {
			Sommet s = getSommet(i);
			sommets_arrivee.addElement(s);
		}
		Sommet s1 = petite_arete.getE1();
		Sommet s2 = petite_arete.getE2();

		sommets_depart.addElement(s1);
		sommets_depart.addElement(s2);

		sommets_arrivee.removeElement(s1);
		sommets_arrivee.removeElement(s2);

		petite_arete.marked = true;
		arbre.ajouterArete(petite_arete);
		// s1.fixe = true;
		// s2.fixe = true;

		while (!sommets_arrivee.isEmpty()) {

			Arete areteMin = null;
			int sens = 0;
			double min = 999999.0;
			for (int i = 0; i < nombreAretes(); i++) {
				Arete arete = getArete(i);
				s1 = arete.getE1();
				s2 = arete.getE2();
				if (sommets_depart.contains(s1)
						&& !sommets_arrivee.contains(s1)
						&& sommets_arrivee.contains(s2)
						&& !sommets_depart.contains(s2) && s1.getVisible()
						&& s2.getVisible() && arete.getVal() < min) {
					min = arete.getVal();
					areteMin = arete;
					sens = 1;
				} else if (sommets_depart.contains(s2)
						&& !sommets_depart.contains(s1)
						&& sommets_arrivee.contains(s1)
						&& !sommets_arrivee.contains(s2) && s1.getVisible()
						&& s2.getVisible() && arete.getVal() < min) {
					min = arete.getVal();
					areteMin = arete;
					sens = 2;
				}
			}
			if (areteMin != null) {
				if (sens == 1) {
					sommets_depart.addElement(areteMin.getE2());
					sommets_arrivee.removeElement(areteMin.getE2());
				}
				if (sens == 2) {
					sommets_depart.addElement(areteMin.getE1());
					sommets_arrivee.removeElement(areteMin.getE1());
				}

				areteMin.setVisible();
				arbre.ajouterArete(areteMin);
			} else {
				System.out.println(" Le Graphe n'est pas connexe");
				return (this);
			}
		}
		arbre.maximumArete();
		arbre.stress = this.stress;
		arbre.noms = this.noms;

		return (arbre);
	}

	static void echangerElements(double[] t, int m, int n) {
		double temp = t[m];

		t[m] = t[n];
		t[n] = temp;
	}

	static int partition(double[] t, int m, int n) {
		double v = t[m]; // valeur pivot
		int i = m - 1;
		int j = n + 1; // indice final du pivot

		while (true) {
			do {
				j--;
			} while (t[j] > v);
			do {
				i++;
			} while (t[i] < v);
			if (i < j) {
				echangerElements(t, i, j);
			} else {
				return j;
			}
		}
	}

	static void triRapide(double[] t, int m, int n) {
		if (m < n) {
			int p = partition(t, m, n);
			triRapide(t, m, p);
			triRapide(t, p + 1, n);
		}
	}

	// Algorithme Kruskal pour determiner un MST : Maximal Spanning Tree
	public Graphe Kruskal() {
		Graphe arbre = new Graphe(false);

		return arbre;
	}

	public void visite() {
		for (int i = 0; i < nombreSommets(); i++) {
			Sommet s = getSommet(i);
			if (s.marked())
				System.out.println("sommet " + i + " déjà vu");
			else {
				System.out.println("sommet " + i + " atteint");
				s.mark();
			}
		}
	}

	public void AugmenteAttirance(Sommet srep) {
		for (int i = 0; i < nombreAretes(); i++) {
			Arete arete = getArete(i);
			Sommet u = arete.getE1();
			Sommet v = arete.getE2();
			if ((v.nom == srep.nom) | (u.nom == srep.nom)) {
				v.metrique *= 2;
				u.metrique *= 2;

				for (int j = 0; j < instance; j++) {
					double val = arete.getVal();
					double val2 = arete.getValInstance(j);
					arete.setValInstance(val2 * 2, j);
					arete.setVal(val * 2);
					repaint();
				}
			}
		}
	}

	/*
	 * retire du graphe tous les sommets de degre 0. Ceci reduit la taille du
	 * graphe par consequent accelere les differents algorithmes utilises
	 */
	public void nettoyage() {
		for (int i = 0; i < sommets.size(); i++) {
			Sommet v = this.getSommet(i);
			getVoisins(v, false, this.seuil);

			if (this.degre(this.indiceSommet(v)) == 0 && v.getVisible()) {
				v.setInvisible();
				System.out.println(this.indiceSommet(v) + " visible = "
						+ v.getVisible() + "  nom : " + v.nom + "  degre =  "
						+ this.degre(this.indiceSommet(v)));
			}
			if (this.degre(this.indiceSommet(v)) == 0 && !v.getVisible()) {
				v.setVisible();
				System.out.println(this.indiceSommet(v) + " visible = "
						+ v.getVisible() + "  nom : " + v.nom + "  degre =  "
						+ this.degre(this.indiceSommet(v)));
			}
		}
	}

	static String tronque(String chaine, int nbDecimales) {
		String avant;
		String apres;

		StringTokenizer st = new StringTokenizer(chaine, ",");
		avant = st.nextToken();
		if (st.hasMoreTokens())
			apres = st.nextToken();
		else
			return avant;

		if (nbDecimales <= 0) {
			if (avant.equals("-0"))
				avant = "0";
			return avant;
		} else if (apres.length() <= nbDecimales)
			return chaine;
		return chaine.substring(0, chaine.length() - apres.length()
				+ nbDecimales);
	}

	/**
	 * Trier l'ensemble des aretes en fonction de leurs poids
	 */
	public void TrierAretes() {
		Vector aretesTriees = new Vector();
		aretesTriees = triInsertion(aretes);
		aretes = (Vector) aretesTriees.clone();

	}

	public static Vector triInsertion(Vector v) {
		Vector res = new Vector(); // le vecteur rï¿½ï¿½sultat
		int i; // indice pour le parcours de v
		int p; // indice pour la position d'insertion dans res
		for (i = 0; i < v.size(); i++) {
			// on range dans elti le iï¿½ï¿½me ï¿½ï¿½lï¿½ï¿½ment de v
			Arete a1 = (Arete) v.elementAt(i);
			Double elti = new Double(a1.getVal());
			// recherche de la position p de elti dans le vecteur resultat
			for (p = 0; p < res.size(); p++) {
				Arete a2 = (Arete) res.elementAt(p);
				Double el = new Double(a2.getVal());
				if (elti.doubleValue() < el.doubleValue())
					break;
			}
			// insertion de elti ï¿½ï¿½ la position p dans le vecteur resultat
			res.insertElementAt(a1, p);
		}
		return res;
	}

	public void SauvegarderPosition() {
		for (int i = 0; i < nombreSommets(); i++) {
			Sommet s = getSommet(i);
			s.x0 = s.x;
			s.y0 = s.y;
		}
	}

	public HashMap getHm_poids() {
		return hm_poids;
	}

	public void setHm_poids(HashMap hm_poids) {
		this.hm_poids = hm_poids;
	}

	public HashMap getHm_degre() {
		return hm_degre;
	}

	public void setHm_degre(HashMap hm_degre) {
		this.hm_degre = hm_degre;
	}

	public HashMap getHm_combine() {
		return hm_combine;
	}

	public void setHm_combine(HashMap hm_combine) {
		this.hm_combine = hm_combine;
	}

	public HashMap getHm_degreIn() {
		return hm_degreIn;
	}

	public void setHm_degreIn(HashMap hm_degreIn) {
		this.hm_degreIn = hm_degreIn;
	}

	public HashMap getHm_degreOut() {
		return hm_degreOut;
	}

	public void setHm_degreOut(HashMap hm_degreOut) {
		this.hm_degreOut = hm_degreOut;
	}

	public HashMap getHm_poidsIn() {
		return hm_poidsIn;
	}

	public void setHm_poidsIn(HashMap hm_poidsIn) {
		this.hm_poidsIn = hm_poidsIn;
	}

	public HashMap getHm_poidsOut() {
		return hm_poidsOut;
	}

	public void setHm_poidsOut(HashMap hm_poidsOut) {
		this.hm_poidsOut = hm_poidsOut;
	}

	public HashMap getHm_combineIn() {
		return hm_combineIn;
	}

	public void setHm_combineIn(HashMap hm_combineIn) {
		this.hm_combineIn = hm_combineIn;
	}

	public HashMap getHm_combineOut() {
		return hm_combineOut;
	}

	public void setHm_combineOut(HashMap hm_combineOut) {
		this.hm_combineOut = hm_combineOut;
	}

	public HashMap getDistance_() {
		return distance_;
	}

	public void setDistance_(HashMap distance_) {
		this.distance_ = distance_;
	}

	public String[][] getPathMatrix() {
		return pathMatrix;
	}
	public void ChargerMatSymetrique(String nom_fichier, int periode) {
		// System.out.println("Largeur : "+largeur+" longueur : "+longueur);
		int nb_aretes = 0;
		int ptx = 0;
		int pty = 0;
		int ptz = 0;
		String InstanceFile = nom_fichier.concat(".") + periode;
		instance = periode;
		System.out.println("Lecture des sommets ....");

		try {

			File file = new File(InstanceFile);
			Reader rd_result = new FileReader(file);
			String ligne_doc = null;
			LineNumberReader line = new LineNumberReader(rd_result);
			// Lecture de la premiere ligne du fichier matrice pour generer les
			// sommets
			ligne_doc = line.readLine();
			StringTokenizer labc = new StringTokenizer(ligne_doc);
			String lab = labc.nextToken();
			while (lab != null && !lab.equals("CLASSE")) {
				// Creation du sommet correspondant a la colonne en cours
				/*
				 * pt.x et pt.y sont les valeurs des coordonnï¿½ï¿½es qui seront
				 * attribuï¿½ï¿½es aux sommets. En prenant comme valeur fixe 520
				 * (largeur) et 550 (hauteur), on s'assure de laisser de
				 * l'espace pour afficher les noms des sommets.
				 */
				ptx = 10 + (int) (520 * Math.random());
				pty = 10 + (int) (550 * Math.random());
				ptz = 10 + (int) (550 * Math.random());

				Sommet s = new Sommet(ptx, pty, ptz, lab.toLowerCase(), 0, 0,
						0, 0, 0);

				s.type = 1;
				s.metrique = 0;
				ajouterSommet(s);

				try {
					lab = labc.nextToken();
				} catch (NoSuchElementException ex) {
					break;
				}
				M++;
			}

			line.close(); // fin de lecture du fichier

			// int perimetre=2300/(instance);
			int perimetre = ((largeur + longueur) * 2) / (instance);
			int p = perimetre;
			int res = 0;
			CalculRepere(largeur, longueur, perimetre);
			if (instance > 1)
				M += instance; // /// ????????? nombre sommet + = periode
			matrice = new double[M][M];

			// a ce stade, nous avons cree tous les (M) sommets du graphe
			// prochaine etape : creation des aretes entre ces sommets
			// lecture du fichier ligne par ligne
			if (instance > 1)
				multi = true;

			//for (int inst = 1; inst <= instance; inst++) {
			    int inst = instance;
				InstanceFile = nom_fichier.concat(".") + inst;
				file = new File(InstanceFile);
				rd_result = new FileReader(file);
				line = new LineNumberReader(rd_result);

				ligne_doc = line.readLine();
				ligne_doc = line.readLine();

				int l = 0; // compteur sur les lignes
				// Traitement d'une ligne du fichier
				//
				int T = 1;
				while (ligne_doc != null && l < M + 1 - instance) {  // l : compteur nbre ligne
					Sommet s = getSommet(l);
					s.Metrique[inst] = 0.0; // initialisation du metrique du sommet s dans la periode courante
					int j = 0; // compteur sur les colonnes
					StringTokenizer label = new StringTokenizer(ligne_doc);
					String str = label.nextToken();
					int arret = 0;
					if (instance > 1)
						arret = M + 1 - instance - 2;
					else
						arret = M + 1 - instance;
					while (label.hasMoreTokens() && j < arret) { //arret : nbre de colonne 
						// System.out.println(j);                  // j : compteur sur le nbre de tokenizer de chaque ligne
						// lecture des valeurs dans la ligne courante <=> des
						// liens
						Sommet ss = getSommet(j);
						// System.out.println("sommet: " + ss.getNom());
						str = label.nextToken();
						str = str.replace(',', '.');
						try {
							double val = Double.parseDouble(str);
							if (val != 0.0) {
								if (s.Metrique[inst] <= val&& !s.nom.contains("virtuel")) {
									s.Metrique[inst] = val;
								}
								if (j <= l) {

									int indice = areteDansGraphe(s, ss);

									if (indice != -1) {
										Arete a = getArete(indice);
										a.addVal(val);
										a.setValInstance(val, inst);
										if (val == 16.0 && inst != 1)
											System.out.println("on a atteind 16");
										for (int i = 0; i <= inst; i++) {
											if (maxar < val)
												maxar = val;
										}
										String nom;
										val = a.getVal();
										if (val < 1.0)
											nom = String.valueOf(val);
										else
											nom = ""+ (String.valueOf((int) val));
										a.setNom(nom);

									} else if (!s.equals(ss)) {
										Arete a = new Arete(s, ss, s.x, ss.x,
												null, val, nb_aretes++);
										ajouterArete(a);
										a.setValInstance(val, inst);
										String nom;
										val = a.getVal();
										if (val < 1.0)
											nom = String.valueOf(val);
										else
											nom = String.valueOf((int) val);
										a.setNom(nom);

									}
								}

							}

						}

						catch (NumberFormatException ex) {
							System.out
									.println("Lecture de la matrice : Format des valeurs incompatible");
						}
						j++;
					}
					l++;
					ligne_doc = line.readLine();
					T += 1;
				}
				line.close(); // fin de lecture du fichier
		//	}
		} catch (IOException e2) {
			System.err.println(e2);
			System.exit(1);

		} catch (NullPointerException e3) {
			System.out.println("\nNullPointerException dans multi: " + e3);
		}
		// *********************
		double max = 0.0;
		int k = 1;

		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.metrique = 0.0;
			Sommet SVirtuel;
			for (k = 1; k <= periode; k++) {
				SVirtuel = getSommet(M - (periode - k + 1));
				if (v.metrique <= v.Metrique[k])
					v.metrique = v.Metrique[k];
				if (max <= v.Metrique[k])
					max = v.Metrique[k];
				if (periode > 1) {
					if (v.Metrique[k] != 0) {
						Arete aVirtuelle = new Arete(SVirtuel, v, SVirtuel.x,
								v.x, null, v.Metrique[k], nb_aretes++);
						ajouterArete(aVirtuelle);
						aVirtuelle.setValInstance(v.Metrique[k], k);
						aVirtuelle.setInvisible();
						aVirtuelle.setValInstance(v.Metrique[k], k);
					}
				}
			}
		}

		for (int j = 0; j < nombreSommets(); j++) {
			Sommet v = getSommet(j);
			v.Metrique[periode + 1] = max;
		}
		System.out.println("\n\n");
		cercle = 1;
		if (periode > 1)
			cercle = 2;
		System.gc();
		maximumArete();

	}

	

}
