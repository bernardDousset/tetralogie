//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.util.Vector;
//
//import javax.swing.*;
//import javax.swing.tree.*;
//import javax.swing.event.*;
//
//
//public class SelectableTree extends JFrame
//                            implements TreeSelectionListener  {
////  public static void main(String[] args) {
////    
////	  new SelectableTree();
////  }
//
//  private JTree tree;
//  private JTextField currentSelectionField;
//  
//  public SelectableTree(GraphPanel gp , int nbreCluster, Vector a[]){	  
//    super("JTree Selections");
//	  try {
//		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//	} catch (ClassNotFoundException | InstantiationException
//			| IllegalAccessException | UnsupportedLookAndFeelException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//	  WindowListener exitListener = new WindowAdapter() {
//          public void windowClosing(WindowEvent e) {
//                  System.exit(1);
//              }
//      };
//    addWindowListener(exitListener);
//    
//    Container content = getContentPane();
//    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
//    DefaultMutableTreeNode child;
//    DefaultMutableTreeNode grandChild;
//    
//	
//
//    
//    // Remplissage du peres
//     for(int i = 0; i<nbreCluster; i++) {
//      child = new DefaultMutableTreeNode("Cluster " + i);
//      root.add(child);
//      //remplissage des fils
//      for(int j = 0; j< a[i].size() ; j++) {
//    	  Sommet s = (Sommet )a[i].elementAt(j);
//        grandChild =new DefaultMutableTreeNode(s.getNom() );
//        child.add(grandChild);
//      }
//    }
//    tree = new JTree(root);
//    tree.addTreeSelectionListener(this);
//    content.add(new JScrollPane(tree), BorderLayout.CENTER);
//    currentSelectionField = new JTextField("Current Selection: NONE");
//    content.add(currentSelectionField, BorderLayout.SOUTH);
//    setSize(250, 275);
//    setVisible(true);
//  }
//
//  public void valueChanged(TreeSelectionEvent event) {
//    currentSelectionField.setText
//      ("Current Selection: " +
//       tree.getLastSelectedPathComponent().toString());
//  }
//  
//  
//  
//  
//}
//
//
//
//
//
//
///////////////////////////////////////////////////////////////////////////////////////////:
////import java.util.ArrayList;
////import java.util.Collections;
////import java.util.HashMap;
////import java.util.HashSet;
////import java.util.Iterator;
////import java.util.LinkedList;
////import java.util.List;
////import java.util.Map;
////import java.util.Set;
////import java.util.Stack;
////import java.util.Vector;
////
////
////public class Dijkstra {
////	private static boolean trouve;
////	Stack<Sommet> stack ;
////	boolean indicateur = false;
////	Stack<Sommet> stack2 ;
////
////	public Dijkstra(GraphPanel gp, double alpha) {
////		 gp.initNombreNoeudintermediaire(gp.getGraphe());
////		 for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
////		Sommet sommet_i = (Sommet) gp.getGraphe().sommets.get(i);
////		if(sommet_i.voisins.size()>0){
////		 for ( int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
////			  stack = new Stack<Sommet>();
////		Sommet sommet_j = (Sommet) gp.getGraphe().sommets.get(j);
////		 System.out.println("source  :" + sommet_i.getNom() +"------->"+ sommet_j.getNom());
////		 if(sommet_j.voisins.size() > 0 ){
////		 nombreintermediaire(sommet_i, sommet_j, gp);
////		 System.out.println( sommet_i.getNombreIntermediaire().get(sommet_j));
////		 }else{
////			 System.out.println(sommet_j.getNom()+ "  est isolé !! ") ;	 
////		 }
////		 }
////		 }
////		System.out.println( "                          ***********                                ");
////		 }
////        
////		initDistance(gp);
////		for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
////			Sommet sommet_i = (Sommet) gp.getGraphe().sommets.get(i);
////			if(sommet_i.voisins.size() > 0 ){
////		for ( int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
////			stack2 = new Stack<Sommet>();
////			Sommet sommet_j = (Sommet) gp.getGraphe().sommets.get(j);
////			if(sommet_j.voisins.size() > 0){
////			plusCourChemin(sommet_i, sommet_j, gp, alpha);
////			System.out.println(" distance   :" +sommet_i.getNom() + "--->" +sommet_j.getNom() + "   est   " + sommet_i.getDistance().get(sommet_j));
////			System.out.println("chemin : " + sommet_i.getNom()+"   -->   "+sommet_j.getNom() + "   est :" );
////			for(int k = 0; k<sommet_i.getPredecessors().get(sommet_j).size(); k++){
////			System.out.println(sommet_i.getPredecessors().get(sommet_j).get(k).getNom() + "\n");
////			}
////			}else{
////				System.out.println("Sommet  :"  + sommet_j.getNom() + "   est une destination isolé !");
////			}
////		}
////		
////		}else{
////			System.out.println("Sommet  :"  + sommet_i.getNom() + "   est isolé !");
////		}
////
////		}		
////			}
////
////	/**
////	 * 
////	 * @param source
////	 * @param destination
////	 * @param gp
////	 */
////
////	public void nombreintermediaire(Sommet source, Sommet destination,GraphPanel gp) {
////		int comp = 0;
////		if (!stack.contains(source))
////			stack.push(source);
////		source.setMarqued(true);
////		while (true) {
////			/**
////			 * // tous les noeuds voisins sont marqué && destination trouvé =>  enlever tete du pile et
////			 */
////			if (checkVoisins(source) == 1 && trouve == true) { 
////				stack.remove(source); //
////				break;
////			} else {
////				// enlever les marques des sommets parcourues
////				trouve = false;
////			}
////			while (trouve == false && comp < source.voisins.size()) {
////				if (source == destination) {
////					source.getNombreIntermediaire().put(source, 0);
////					break;
////				} else {
////					if (source.voisins.contains(destination) == true) {
////						source.getNombreIntermediaire().put(destination, 1);
////						// System.out.println("Sommet   :" + source.getNom() +
////						// "   est voisin de  : " + destination.getNom() );
////						trouve = true;
////						break;
////					} else if (source.voisins.contains(destination) == false
////							&& (destination.voisins.size() > 0)) {
////						// source.marked = true;
////						// source.getPathBinaire().add(source);
////						// System.out.println(" path binaire du sommet  : " +
////						// source.getNom() + "est : " );
////						// for(Sommet ssssss : source.getPathBinaire()){
////						// System.out.println(ssssss.getNom() + "\n");
////						// }
////						while (comp < source.voisins.size()) {
////							Sommet s = (Sommet) source.voisins.get(comp);
////							// stack.push(s);
////
////							if (s.isMarqued() == false) {
////								if (s.voisins.size() > 1) {
////									nombreintermediaire(s, destination, gp);
////									comp++;
////								} else {
////									comp++;
////									// stack.pop();
////									// source.getPathBinaire().remove(source);
////									continue;
////								}
////							} else {
////								// stack.pop();
////								// source.getPathBinaire().remove(source);
////								comp++;
////								continue;
////							}
////
////						}
////						continue;
////
////					}
////				}
////
////				if (comp >= source.voisins.size() && trouve == false) {
////
////					System.out.println("compteur depasse nombre de voisins de  : "+ source.getNom()+ "   et destination non atteinte  :"+ destination.getNom());
////					System.out.println("source     : " + source.getNom()+ "       destination  : " + destination.getNom());
////					stack.remove(source);
////					break;
////
////				}
////			}
////			if (trouve == true) {
////				// on remplit le path binaire de tous les sommets du stack :
////				// sommets du path identifié vers la destination
////				int stackindex = 0;
////				int compteur = 1;
////				int nbre = 1;
////				while (stackindex < stack.size()) {
////					Sommet sommet = stack.elementAt(stackindex);
////					nbre = stack.size() - (stackindex + 1);
////					// on remplit le nombre de sommet intermédiaire
////					if (getShortestNodePath(sommet, destination) > nbre) { 
////						/**
////						 * Si le nombre d'intermediaire dans le chemin calculé< nbre existant:
////						 */
////						sommet.getNombreIntermediaire().put(destination, nbre); // on
////																				// ecrit
////																				// ce
////																				// nbre
////																				// et
////																				// on
////																				// sauvegarde
////																				// le
//////																				// chemin
//////						System.out.println("********************************  Nbre Intermediaire  **************************************");
//////						System.out.println(sommet.getNom() + "---- "+ destination.getNom() + "  :" + nbre);
//////						System.out.println("********************************    Sommets intermediaire        **************************************");
//////						System.out.println("Noeud intermediaire :"+ sommet.getNom() + "---"+ destination.getNom() + "   :");
//////						System.out.println("**********************************************************************");
////
////						//
////						while (compteur < stack.size()) {
////							Sommet pathNode = stack.elementAt(compteur);
////							sommet.getPathBinaire().add(pathNode);
////							compteur++;
////						}
//////						System.out.println(sommet.getNom()+ "___"+ destination.getNom()+ "------->"+ sommet.getNombreIntermediaire().get(destination));
//////						System.out.println("********************************    Sommets intermediaire        **************************************");
//////						System.out.println("Noeud intermediaire :"+ sommet.getNom() + "---"+ destination.getNom() + "   :");
//////						for(int k = 0 ; k< sommet.getPathBinaire().size(); k++){
//////							System.out.println(sommet.getPathBinaire().get(k).getNom() + "\n");
//////						}
////						stackindex++;
////						compteur = stackindex + 1;
////						sommet.getChemin().put(destination,sommet.getPathBinaire());
////
////						continue;
////					}
////					stackindex++;
////					compteur = stackindex + 1;
////				}
////
////			}
//////			if (trouve == false && comp >= source.voisins.size()) { // si on se trouve
//////															// coincé et qu'on
//////															// n'a pas trouvé
//////															// encore le
//////															// destination
//////				stack.removeElement(source);
//////				break;
//////			}
//////			if (checkVoisins(source) == 1) { // tous les noeuds voisins sont
//////												// marqué && destination trouvé
//////				stack.remove(source);
//////				comp++;
//////				trouve = false;
//////				continue;
//////			}
////
////			break;
////		}
////		 if(source == stack.elementAt(0))
////         for(int i = 0 ; i < gp.getGraphe().nombreSommets(); i++){
////        	 Sommet s = gp.getGraphe().getSommet(i) ; 
////        	 s.setMarqued(false);
////         }
////	}
////
////	/***
////	 * on initialise le nombre des noeuds intermediaires à : 1 : si noeud
////	 * recherché est voisine 0 : si noeud recherché est isolé(zero voisins)
////	 * infini : si noeud recherché est contenu dans le graphe
////	 * 
////	 * @param gr
////	 *            : Graphe
////	 * 
////	 * 
////	 * **********/
////	public int getShortestNodePath(Sommet source, Sommet destination) {
////		int nbre = source.getNombreIntermediaire().get(destination);
////		return nbre;
////	}
////
////	/**
////	 * 
////	 * @param node
////	 * @return 1 si tous les voisins sont marqués 0 sinon
////	 */
////	public int checkVoisins(Sommet node) {
////		int settled = 0;
////		int sett = 0;
////		for (int i = 0; i < node.voisins.size(); i++) {
////			Sommet s = (Sommet) node.voisins.elementAt(i);
////			if (s.isMarqued() == true) {
////				sett = 1;
////			}
////		}
////		settled *= sett;
////		return settled;
////	}
////
////	// ******************************************************************************************************************//
////	
////	/**
////	 * 
////	 * @param source
////	 * @param destination
////	 * @return Retourne la valeur existante de la distance source/destination
////	 *         dans le map distance du source
////	 */
////	public double getShortestDistance(Sommet source, Sommet destination) {
////		return source.getDistance().get(destination);
////	}
////
////	/**
////	 * 
////	 * @param sommet
////	 * @param gp
////	 * @return la sommet la plus proche du source non marqué(en inverse de la valeur du
////	 *         lien )
////	 */
////	public Sommet getMinimum(Sommet sommet, GraphPanel gp) {
////		Vector<Sommet> v = new Vector<Sommet>();
////		v = sommet.voisins;
////		Sommet minimum = null;
////		double valeur = Double.MAX_VALUE;
//////		for(int i = 0 ; i<v.size(); i++){
//////			Sommet ss = (Sommet) v.elementAt(i);
//////			if(ss.isChecked() == true) v.remove(ss);
//////		}
////		int i = 0 ; 
////		if(v != null){
////		while ( i < v.size()) {
////			Sommet s = (Sommet ) v.elementAt(i);
////			//System.out.println(s.getNom()+" => "  + s.isChecked() );
////			if (s.isChecked() == false) {
////				int index = gp.getGraphe().areteDansGraphe(sommet, s);
////				double poids = 1 / gp.getGraphe().getArete(index).getVal();
////				if(poids <= valeur) {
////					valeur = poids;
////					minimum = s;
////				}
////				i++;
////		}else{
////			i++;
////			continue;
////		}
////		}
////		}
////		
////		
////		return minimum;
////	}
////
////	/**
////	 * Initialise la map distance de chaque sommet : soit -1 si la destination
////	 * est isolé soit infini pour le reste des sommets du graphe
////	 * 
////	 * @param gp
////	 */
////	public void initDistance(GraphPanel gp) {
////		
////		for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
////			Sommet s = gp.getGraphe().getSommet(i);
//////			Vector<Sommet> v = new Vector<Sommet>();
//////			v.add(s);
////			for (int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
////				Sommet ss = gp.getGraphe().getSommet(j);
////				//s.getPredecessors().put(ss, v);
////				if (ss.voisins.size() >= 1) {
////					s.getDistance().put(ss, Double.MAX_VALUE);
////				} else if(s == ss ){
////					s.getDistance().put(ss, 0.0);
////				}else{
////					s.getDistance().put(ss, -1.0);
////				}
////			}
////
////		}
////	}
////	
////	/**
////	 * Si retourne 1 alors  tous les voisins sont checked Si non il Existe un voisin non checked
////	 * @param node
////	 * @return
////	 */
////	
////	public int checkVoisins1(Sommet node) {
////		int settled = 1;
////		int sett = 1;
////		for (int i = 0; i < node.voisins.size(); i++) {
////			Sommet s = (Sommet) node.voisins.elementAt(i);
////			if (s.isChecked() == true) {
////				sett = 1;
////				settled *= sett;
////
////			}else{
////				sett = 0;
////				settled *= sett;
////
////			}
////				
////		}
////		return settled;
////	}
////
////	/**
////	 * Pour chaque sommet source , on détermine le plus court chemin lui
////	 * séparant au destination en au niveau de chaque noeud intermédiaire du
////	 * chemin trouvé , la distance && les noeuds qui séparent du destination
////	 * 
////	 * @param source
////	 * @param destination
////	 * @param gp
////	 */
////	public void plusCourChemin(Sommet source, Sommet destination, GraphPanel gp, double alpha ) {
////        while(true){
////		source.setChecked(true);
////		if( ! stack2.contains(source) ) stack2.push(source);
////		if (source.voisins.size() == 0) {
////			System.out.println("la sommet  " + source.getNom() + "   isole !!!");
////			stack2.remove(source);
////			source.setChecked(false);
////			break;
////		} else if (destination.voisins.size() == 0) {
////			System.out.println("source   " + destination.getNom()+ "    est isole !!!");
////			source.getDistance().put(destination, 0.0);
////			break;
////		}else if(source == destination){
////			System.out.println("distance   : " + source.getNom() + "____" + source.getNom() + "    est nulle  ") ;
////			source.getDistance().put(destination, 0.0);
////			Vector v = new Vector<>();
////			v.add(source);
////            source.getPredecessors().put(destination, v);
////			break ;
////		} else if (source.voisins.size() > 0 && destination.voisins.size() > 0) {
////			int compteurVoisin = 0;
////			 //en cas ou cible trouvé ou non trouvé on doit chercher tous les possibilités
////			// du noeud en cours => tester la valeur du compteur voisin
////			// Dans ce cas , chaque voisin traité est checked !
////			while (indicateur == false|| compteurVoisin < source.voisins.size()) {
////				double poids = 0;                                                     
////   			if(checkVoisins1(source ) == 1) break ;                                  
////				if (source.voisins.contains(destination)&& indicateur == false) {
////					/**
////				    * stack2.push(destination);
////					*
////					* il faut prévoir cas de depassement du
////                    * compteur , et le cas indicateur =  true chacun a part
////					**/
////				//	Vector<Sommet> v = new Vector<Sommet>();
////					int indice = gp.getGraphe().areteDansGraphe(source,
////							destination);
////				//	poids = 1 / gp.getGraphe().getArete(indice).getVal();
////					poids = 1/Math.pow(gp.getGraphe().distance(source, destination) , alpha);
////					if (getShortestDistance(source, destination) >= poids) {
////						source.getDistance().put(destination, poids);
////						Vector<Sommet> v = new Vector<Sommet>();
////						v.add(source);
//////						if( ! source.getPredecessors().get(destination).contains(source))
//////						source.getPredecessors().get(destination).add(source);
////					source.getPredecessors().put(destination, v);
////					}
////					if(stack2.size()  > 1) indicateur = true;
////					
////					// une fois ecrit la nouvelle valeur de distance koi faire
////					// ??
////				} else { 
////					// recherche normal du cible :
////					// 1er cas cible non repéré et pas de depassement de nombre
////					// de voisin du cible
////					while (((compteurVoisin < source.voisins.size())&& (indicateur == false)) || checkVoisins(source) ==0 ) {
////						Sommet s = getMinimum(source, gp);
//////						if(s == null ) break ; 
////				//		System.out.println("source  : " + source.getNom() + " &&   "+ "  voisin minimum  : " + s.getNom());
////						if (s!= destination && s != null && s.voisins.size() > 1) { // s est non
////																	// marqué &&
////							if(indicateur == true) indicateur = false;										// non null
////							plusCourChemin(s, destination, gp, alpha); 
////							compteurVoisin++;
////						} else {
////							// cas ou tous les sommets voisins sont marqués ou
////							// que s a un seul voisin : on passe au voisin
////							// suivant
////							if(s != null){
////							s.setChecked(true);
////							//source.setChecked(true);
////							compteurVoisin++;
////							continue;
////							}else{
////								break;
////							}
////						}
////					}
////				}
//////				if (compteurVoisin >= source.voisins.size() && indicateur  == false) {
//////					stack2.remove(source);
//////					break; // on passe au sommet antecedent(en dessous) du pile
//////				}
//////				if(checkVoisins1(source ) == 1){
//////					stack2.remove(source);
//////					break; // on passe au sommet antecedent(en dessous) du pile
//////				}
////				if (indicateur == true) {
////					/**
////					 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
////					 *  puis on continue l'execution pour le reste des voisins du source 
////					 */
////					if(stack2.size() == 1)
////						break;
////					int compteurStack = 0;
////					while (compteurStack < stack2.size()) {        // 
////						double somme = Math.pow(gp.getGraphe().distance(source, destination) , alpha);/////////////////////******************************* problem declaration and cumulation
////						Vector<Sommet> v = new Vector<Sommet>(); // path  = { sommet contenus dans le stack2}
////						int index0 = 0, index1 = 1;
////						Sommet sommet = (Sommet) stack2.elementAt(compteurStack);
////						while (index1 <stack2.size()) {
////							Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
////							Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
////							if (gp.getGraphe().distance(sommet1, sommet2) != -1)
////								somme += Math.pow(gp.getGraphe().distance(sommet1,sommet2), alpha);
////							v.add(sommet2);
//// 							index0++;
////							index1++;
////						}
////
////						if (getShortestDistance(sommet, destination) > somme) {
////						sommet.getDistance().put(destination, somme);
////						sommet.getPredecessors().put(destination, v);
////						}
////						
//////						for(int k=compteurStack; k<v.size();k++){
//////							Sommet voisin  = (Sommet) v.elementAt(k);
//////							
//////						}
////						compteurStack++;
////					}
////					compteurVoisin++;
////					indicateur = true;
////					continue;
////				}
////                   break;
////			}
////		}
////		indicateur = false;
////		break;
////	}
//// if(stack2.size() >0){
//// if (stack2.elementAt(0) == source){
////		// initialiser les sommets trace du parcours de recherche du destination traité 
////        for(int i = 0 ; i< gp.getGraphe().sommets.size() ; i++){
////        	Sommet s  = (Sommet) gp.getGraphe().sommets.elementAt(i);
////        	s.setChecked(false);
////        }
////      }
//// }
//// if(indicateur == false ){
//// 	stack2.remove(source);
//// }
//// 
////	}
////	
////
////
////}