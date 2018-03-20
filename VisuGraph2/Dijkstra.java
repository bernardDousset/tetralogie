import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;


public class Dijkstra {
	private static boolean trouve;
	Stack<Sommet> stack ;
	boolean indicateur = false;
	Stack<Sommet> stack2 ;
	
/*
 * 
 */
	
	public Dijkstra(Graphe gr, double alpha) {
				
		
		
		
		
//		 gp.initNombreNoeudintermediaire(gp.getGraphe());
//		 for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
//		Sommet sommet_i = (Sommet) gp.getGraphe().sommets.get(i);
//		if(sommet_i.voisins.size()>0){
//		 for ( int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
//	    stack = new Stack<Sommet>();
//		Sommet sommet_j = (Sommet) gp.getGraphe().sommets.get(j);
//		 System.out.println("source  :" + sommet_i.getNom() +"------->"+ sommet_j.getNom());
//		 if(sommet_i.getNombreIntermediaire().get(sommet_j) == Integer.MAX_VALUE){
//		 if(sommet_j.voisins.size() > 0 ){
//		 nombreintermediaire(sommet_i, sommet_j, gp);
//		 sommet_j.getNombreIntermediaire().put(sommet_i, sommet_i.getNombreIntermediaire().get(sommet_j));
//		 System.out.println( sommet_i.getNombreIntermediaire().get(sommet_j));
//		 }else{
//			 System.out.println(sommet_j.getNom()+ "  est isolé !! ") ;	 
//		 }
//		 }
//		 }
//		 }
//		System.out.println( "                          ***********                                ");
//		 }
//        
//		initDistance(gp);
		for (int i = 0; i < gr.nombreSommets(); i++) {
			Sommet sommet_i = (Sommet) gr.sommets.get(i);
			if(sommet_i.voisins.size() > 0 ){
		for ( int j = 0; j < Math.round((gr.nombreSommets()+1)/2); j++) {
			stack2 = new Stack<Sommet>();
			Sommet sommet_j = (Sommet) gr.sommets.get(j);
			if(sommet_j.voisins.size() > 0){
			plusCourChemin2(sommet_i, sommet_j, gr, alpha);
			if(sommet_i.getDistance().get(sommet_j) == Double.MAX_VALUE && sommet_j.getDistance().get(sommet_i) == Double.MAX_VALUE){
				sommet_i.getDistance().put(sommet_j, -1.0);
				sommet_j.getDistance().put(sommet_i, -1.0);
				sommet_i.getPredecessors().put(sommet_j, null);
				sommet_j.getPredecessors().put(sommet_i, null);
			}
			
			System.out.println(" distance   :" +sommet_i.getNom() + "--->" +sommet_j.getNom() + "   est   " + sommet_i.getDistance().get(sommet_j));
			System.out.println("chemin : " + sommet_i.getNom()+"   -->   "+sommet_j.getNom() + "   est :" );
//			if(sommet_i.getDistance().get(sommet_j) != -1){
//			for(int k = 0; k<sommet_i.getPredecessors().get(sommet_j).size(); k++){
//			System.out.println(sommet_i.getPredecessors().get(sommet_j).get(k).getNom() + "\n");
//			}
//			}
			}else{
				System.out.println("Sommet  :"  + sommet_j.getNom() + "   est une destination isolé !");
			}
		}
		
		}else{
			System.out.println("Sommet  :"  + sommet_i.getNom() + "   est isolé !");
		}
			
		}		
		System.out.println("fin");
			}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @param gp
	 */

	public void nombreintermediaire(Sommet source, Sommet destination,GraphPanel gp) {
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

//					System.out.println("compteur depasse nombre de voisins de  : "+ source.getNom()+ "   et destination non atteinte  :"+ destination.getNom());
//					System.out.println("source     : " + source.getNom()+ "       destination  : " + destination.getNom());
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
//				Sommet sss = (Sommet)stack.elementAt(0);
//				Vector<Sommet> vv = new Vector<>();
//				int compteurstack2 = 1;
//				while(compteurstack2 < stack.size()){
//					sss.getLocalPath().add(stack.elementAt(compteurstack2));
//					compteurstack2++;
//				}
//				if(source != destination) sss.getLocalPath().remove(sss);
//                sss.getMesAltenatives().put(compteurChemin, sss.getLocalPath());
//				sss.getMesChemins().put(destination, sss.getMesAltenatives());
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
//	      for(int k = 1; k< source.getMesChemins().get(destination).size(); k++ ){
//	    	  int l=1;
//	    	    while(l<source.getMesChemins().get(destination).get(l).size()){
//			//	 System.out.println(source.getMesChemins().get(destination).get(l).elementAt(k).getNom() + "\n");
//				 l++;
//	    	    }
//			 }
         for(int i = 0 ; i < gp.getGraphe().nombreSommets(); i++){
        	 Sommet s = gp.getGraphe().getSommet(i) ; 
        	 s.setMarqued(false);
         }
			 }
		
	}

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
	public int getShortestNodePath(Sommet source, Sommet destination) {
		int nbre = source.getNombreIntermediaire().get(destination);
		return nbre;
	}

	/**
	 * 
	 * @param node
	 * @return 1 si tous les voisins sont marqués 0 sinon
	 */
	public int checkVoisins(Sommet node) {
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

	// ******************************************************************************************************************//
	
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

	/**
	 * 
	 * @param sommet
	 * @param gp
	 * @return la sommet la plus proche du source non marqué(en inverse de la valeur du
	 *         lien )
	 */
	public Sommet getMinimum(Sommet sommet, Graphe gr) {
		Vector<Sommet> v = new Vector<Sommet>();
		v = sommet.voisins;
		Sommet minimum = null;
		double valeur = Double.MAX_VALUE;
//		for(int i = 0 ; i<v.size(); i++){
//			Sommet ss = (Sommet) v.elementAt(i);
//			if(ss.isChecked() == true) v.remove(ss);
//		}
		int i = 0 ; 
		if(v != null){
		while ( i < v.size()) {
			Sommet s = (Sommet ) v.elementAt(i);
			//System.out.println(s.getNom()+" => "  + s.isChecked() );
			if (s.isChecked() == false) {
				int index = gr.areteDansGraphe(sommet, s);
				double poids = 1 / gr.getArete(index).getVal();
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
	 * Initialise la map distance de chaque sommet : soit -1 si la destination
	 * est isolé soit infini pour le reste des sommets du graphe
	 * 
	 * @param gp
	 */
	public void initDistance(GraphPanel gp) {
		
		for (int i = 0; i < gp.getGraphe().nombreSommets(); i++) {
			Sommet s = gp.getGraphe().getSommet(i);
			Vector<Sommet> v = new Vector<Sommet>();
			v.add(s);
			for (int j = 0; j < gp.getGraphe().nombreSommets(); j++) {
				Sommet ss = gp.getGraphe().getSommet(j);
				s.getPredecessors().put(ss, v);
				if (ss.voisins.size() >= 1) {
					s.getDistance().put(ss, Double.MAX_VALUE);
				} else if(s == ss ){
					s.getDistance().put(ss, 0.0);
				}else{
					s.getDistance().put(ss, -1.0);
				}
			}

		}
	}
	
	/**
	 * Si retourne 1 alors  tous les voisins sont checked Si non il Existe un voisin non checked
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

	/**
	 * Pour chaque sommet source , on détermine le plus court chemin lui
	 * séparant au destination en au niveau de chaque noeud intermédiaire du
	 * chemin trouvé , la distance && les noeuds qui séparent du destination
	 * 
	 * @param source
	 * @param destination
	 * @param gp
	 */
	public  void plusCourChemin2(Sommet source, Sommet destination, Graphe gr, double alpha ) {
       // while(true){
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
					int indice = gr.areteDansGraphe(source,
							destination);
					//poids = 1 / gp.getGraphe().getArete(indice).getVal();
					poids = 1/Math.pow(gr.getArete(indice).getVal() , alpha);
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
						Sommet s = getMinimum(source, gr);
						if (s!= destination && s != null && s.voisins.size() > 1 
								&& s.getDistance().get(destination) != -1.0 && s.getDistance().get(destination) !=0) { // s est non
																	// marqué && non null*
							if(s.getDistance().get(destination) != Double.MAX_VALUE){
								indicateur = true;
								int indiceArete = gr.areteDansGraphe(s,
										source);
								double distance =  1/Math.pow(gr.getArete(indiceArete).getVal() , alpha);
								source.getDistance().put(destination, distance + s.getDistance().get(destination));
								Vector<Sommet> pred = s.getPredecessors().get(destination);
								pred.add(0, s);
								source.getPredecessors().put(destination, pred);
								found = true;
								break;
							}
							if(indicateur == true) indicateur = false;										
							plusCourChemin2(s, destination, gr, alpha); 
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
				if (indicateur == true) {
					/**
					 *  on remplit les maps des distance à condition qu'elle soit meilleur !!!!!!!
					 *  puis on continue l'execution pour le reste des voisins du source 
					 */
					if(stack2.size() == 1)
						break;
					int compteurStack = 0;
					
					
					
					
					
					
					
					Vector<Sommet> v = new Vector<Sommet>();
					double somme = Math.pow(gr.distance(source, destination) , alpha);
					int index0 = 0, index1 = 1;
					Sommet sommet = (Sommet) stack2.elementAt(0);
					while (index1 <stack2.size()) {
						Sommet sommet1 = (Sommet)stack2.elementAt(index0); // index=1
						Sommet sommet2 = (Sommet)stack2.elementAt(index1); // index2= 0 
						if (gr.distance(sommet1, sommet2) != -1)
							somme += Math.pow(gr.distance(sommet1,sommet2), alpha);
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
					stack2.remove(0);
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
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
// 							index0++;
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
        for(int i = 0 ; i< gr.sommets.size() ; i++){
        	Sommet s  = (Sommet) gr.sommets.elementAt(i);
        	s.setChecked(false);
        }
      }
 }
 if(indicateur == false ){
 	stack2.remove(source);
 }
 
	}
	


}