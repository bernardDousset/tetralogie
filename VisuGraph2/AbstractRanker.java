//
//import java.text.DecimalFormat;
//import java.text.Format;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.collections15.Factory;
//import org.apache.commons.collections15.map.LazyMap;
//
//import edu.uci.ics.jung.algorithms.util.IterativeProcess;
//import edu.uci.ics.jung.graph.Graph;
//
///**
// * Abstract class for algorithms that rank nodes or edges by some "importance" metric. Provides a common set of
// * services such as:
// * <ul>
// *  <li> storing rank scores</li>
// *  <li> getters and setters for rank scores</li>
// *  <li> computing default edge weights</li>
// *  <li> normalizing default or user-provided edge transition weights </li>
// *  <li> normalizing rank scores</li>
// *  <li> automatic cleanup of decorations</li>
// *  <li> creation of Ranking list</li>
// * <li>print rankings in sorted order by rank</li>
// * </ul>
// * <p>
// * By default, all rank scores are removed from the vertices (or edges) being ranked.
// */
//public abstract class AbstractRanker<Sommet,Arete> extends IterativeProcess {
//     Graphe mGraph;
//    private List<Ranking<?>> mRankings;
//    private boolean mRemoveRankScoresOnFinalize;
//    private boolean mRankNodes;
//    private boolean mRankEdges;
//    private boolean mNormalizeRankings;
//    protected Map<Object,Map<Sommet, Number>> vertexRankScores = 
//    	LazyMap.decorate(
//    			new HashMap<Object,Map<Sommet,Number>>(),
//    			new Factory<Map<Sommet,Number>>() {
//					public Map<Sommet,Number> create() {
//						return new HashMap<Sommet,Number>();
//					}});
//    protected Map<Object,Map<Arete, Number>> edgeRankScores = 
//    	LazyMap.decorate(
//    			new HashMap<Object,Map<Arete,Number>>(),
//    			new Factory<Map<Arete,Number>>() {
//					public Map<Arete,Number> create() {
//						return new HashMap<Arete,Number>();
//					}});
//    private Map<Arete,Number> edgeWeights = new HashMap<Arete,Number>();
//
//    protected void initialize(Graphe graph, boolean isNodeRanker, 
//        boolean isEdgeRanker) {
//        if (!isNodeRanker && !isEdgeRanker)
//            throw new IllegalArgumentException("Must rank edges, vertices, or both");
//        mGraph = graph;
//        mRemoveRankScoresOnFinalize = true;
//        mNormalizeRankings = true;
//        mRankNodes = isNodeRanker;
//        mRankEdges = isEdgeRanker;
//    }
//    
//    /**
//	 * @return all rankScores
//	 */
//	public Map<Object,Map<Sommet, Number>> getVertexRankScores() {
//		return vertexRankScores;
//	}
//
//	public Map<Object,Map<Arete, Number>> getEdgeRankScores() {
//		return edgeRankScores;
//	}
//
//    /**
//	 * @return the rankScores
//	 */
//	public Map<Sommet, Number> getVertexRankScores(Object key) {
//		return vertexRankScores.get(key);
//	}
//
//	public Map<Arete, Number> getEdgeRankScores(Object key) {
//		return edgeRankScores.get(key);
//	}
//
//	protected Collection<Sommet> getVertices() {
//        return mGraph.sommets;
//    }
//
//	protected int getVertexCount() {
//        return mGraph.sommets.size();
//    }
//
//    protected Graphe getGraph() {
//        return mGraph;
//    }
//
//    @Override
//    public void reset() {
//    }
//
//    /**
//     * Returns <code>true</code> if this ranker ranks nodes, and 
//     * <code>false</code> otherwise.
//     */
//    public boolean isRankingNodes() {
//        return mRankNodes;
//    }
//
//    /**
//     * Returns <code>true</code> if this ranker ranks edges, and 
//     * <code>false</code> otherwise.
//     */
//    public boolean isRankingEdges() {
//        return mRankEdges;
//    }
//    
//    /**
//     * Instructs the ranker whether or not it should remove the rank scores from the nodes (or edges) once the ranks
//     * have been computed.
//     * @param removeRankScoresOnFinalize <code>true</code> if the rank scores are to be removed, <code>false</code> otherwise
//     */
//    public void setRemoveRankScoresOnFinalize(boolean removeRankScoresOnFinalize) {
//        this.mRemoveRankScoresOnFinalize = removeRankScoresOnFinalize;
//    }
//
//    protected void onFinalize(Object e) {}
//    
//    /**
//     * The user datum key used to store the rank score.
//     * @return the key
//     */
//    abstract public Object getRankScoreKey();
//
//
//    @Override
//    protected void finalizeIterations() {
//        List<Ranking<?>> sortedRankings = new ArrayList<Ranking<?>>();
//
//        int id = 1;
//        if (mRankNodes) {
//            for (Sommet currentVertex : getVertices()) {
//                Ranking<Sommet> ranking = new Ranking<Sommet>(id,getVertexRankScore(currentVertex),currentVertex);
//                sortedRankings.add(ranking);
//                if (mRemoveRankScoresOnFinalize) {
//                	this.vertexRankScores.get(getRankScoreKey()).remove(currentVertex);
//                }
//                id++;
//                onFinalize(currentVertex);
//            }
//        }
//        if (mRankEdges) {
//           for (Arete currentEdge : mGraph.getEdges()) {
//
//                Ranking<Arete> ranking = new Ranking<Arete>(id,getEdgeRankScore(currentEdge),currentEdge);
//                sortedRankings.add(ranking);
//                if (mRemoveRankScoresOnFinalize) {
//                	this.edgeRankScores.get(getRankScoreKey()).remove(currentEdge);
//                }
//                id++;
//                onFinalize(currentEdge);
//            }
//        }
//
//        mRankings = sortedRankings;
//        Collections.sort(mRankings);
//    }
//
//    /**
//     * Retrieves the list of ranking instances in descending sorted order by rank score
//     * If the algorithm is ranking edges, the instances will be of type <code>EdgeRanking</code>, otherwise
//     * if the algorithm is ranking nodes the instances will be of type <code>NodeRanking</code>
//     * @return  the list of rankings
//     */
//    public List<Ranking<?>> getRankings() {
//        return mRankings;
//    }
//
//    /**
//     * Return a list of the top k rank scores.
//     * @param topKRankings the value of k to use
//     * @return list of rank scores
//     */
//    public List<Double> getRankScores(int topKRankings) {
//        List<Double> scores = new ArrayList<Double>();
//        int count=1;
//        for (Ranking<?> currentRanking : getRankings()) {
//            if (count > topKRankings) {
//                return scores;
//            }
//            scores.add(currentRanking.rankScore);
//            count++;
//        }
//
//        return scores;
//    }
//
//    /**
//     * Given an edge or node, returns the corresponding rank score. This is a default
//     * implementation of getRankScore which assumes the decorations are of type MutableDouble.
//     * This method only returns legal values if <code>setRemoveRankScoresOnFinalize(false)</code> was called
//     * prior to <code>evaluate()</code>.
//     * @return  the rank score value
//     */
//    public double getVertexRankScore(Sommet v) {
//        Number rankScore = vertexRankScores.get(getRankScoreKey()).get(v);
//        if (rankScore != null) {
//            return rankScore.doubleValue();
//        } else {
//            throw new RuntimeException("setRemoveRankScoresOnFinalize(false) must be called before evaluate().");
//        }
//    }
//    
//    public double getVertexRankScore(Sommet v, Object key) {
//    	return vertexRankScores.get(key).get(v).doubleValue();
//    }
//
//    public double getEdgeRankScore(Arete e) {
//        Number rankScore = edgeRankScores.get(getRankScoreKey()).get(e);
//        if (rankScore != null) {
//            return rankScore.doubleValue();
//        } else {
//            throw new RuntimeException("setRemoveRankScoresOnFinalize(false) must be called before evaluate().");
//        }
//    }
//    
//    public double getEdgeRankScore(Arete e, Object key) {
//    	return edgeRankScores.get(key).get(e).doubleValue();
//    }
//
//    protected void setVertexRankScore(Sommet v, double rankValue, Object key) {
//    	vertexRankScores.get(key).put(v, rankValue);
//    }
//
//    protected void setEdgeRankScore(Arete e, double rankValue, Object key) {
//		edgeRankScores.get(key).put(e, rankValue);
//    }
//
//    protected void setVertexRankScore(Sommet v, double rankValue) {
//    	setVertexRankScore(v,rankValue, getRankScoreKey());
//    }
//
//    protected void setEdgeRankScore(Arete e, double rankValue) {
//    	setEdgeRankScore(e, rankValue, getRankScoreKey());
//    }
//
//    protected void removeVertexRankScore(Sommet v, Object key) {
//    	vertexRankScores.get(key).remove(v);
//    }
//
//    protected void removeEdgeRankScore(Arete e, Object key) {
//    	edgeRankScores.get(key).remove(e);
//    }
//
//    protected void removeVertexRankScore(Sommet v) {
//    	vertexRankScores.get(getRankScoreKey()).remove(v);
//    }
//
//    protected void removeEdgeRankScore(Arete e) {
//    	edgeRankScores.get(getRankScoreKey()).remove(e);
//    }
//
//    protected double getEdgeWeight(Arete e) {
//    	return edgeWeights.get(e).doubleValue();
//    }
//
//    protected void setEdgeWeight(Arete e, double weight) {
//    	edgeWeights.put(e, weight);
//    }
//    
//    public void setEdgeWeights(Map<Arete,Number> edgeWeights) {
//    	this.edgeWeights = edgeWeights;
//    }
//
//    /**
//	 * @return the edgeWeights
//	 */
//	public Map<Arete, Number> getEdgeWeights() {
//		return edgeWeights;
//	}
//
////	protected void assignDefaultEdgeTransitionWeights() {
////
////        for (Sommet currentVertex : getVertices()) {
////
////            Collection<Arete> outgoingEdges = mGraph.getOutEdges(currentVertex);
////
////            double numOutEdges = outgoingEdges.size();
////            for (Arete currentEdge : outgoingEdges) {
////                setEdgeWeight(currentEdge,1.0/numOutEdges);
////            }
////        }
////    }
//
////    protected void normalizeEdgeTransitionWeights() {
////
////        for (Sommet currentVertex : getVertices()) {
////
////        	Collection<Arete> outgoingEdges = mGraph.getOutEdges(currentVertex);
////
////            double totalEdgeWeight = 0;
////            for (Arete currentEdge : outgoingEdges) {
////                totalEdgeWeight += getEdgeWeight(currentEdge);
////            }
////
////            for (Arete currentEdge : outgoingEdges) {
////                setEdgeWeight(currentEdge,getEdgeWeight(currentEdge)/totalEdgeWeight);
////            }
////        }
////    }
//
//    protected void normalizeRankings() {
//        if (!mNormalizeRankings) {
//            return;
//        }
//        double totalWeight = 0;
//
//        for (Sommet currentVertex : getVertices()) {
//            totalWeight += getVertexRankScore(currentVertex);
//        }
//
//        for (Sommet currentVertex : getVertices()) {
//            setVertexRankScore(currentVertex,getVertexRankScore(currentVertex)/totalWeight);
//        }
//    }
//
//    /**
//     * Print the rankings to standard out in descending order of rank score
//     * @param verbose if <code>true</code>, include information about the actual rank order as well as
//     * the original position of the vertex before it was ranked
//     * @param printScore if <code>true</code>, include the actual value of the rank score
//     */
//    public void printRankings(boolean verbose,boolean printScore) {
//            double total = 0;
//            Format formatter = new DecimalFormat("#0.#######");
//            int rank = 1;
//
//            for (Ranking<?> currentRanking : getRankings()) {
//                double rankScore = currentRanking.rankScore;
//                if (verbose) {
//                    System.out.print("Rank " + rank + ": ");
//                    if (printScore) {
//                        System.out.print(formatter.format(rankScore));
//                    }
//                    System.out.print("\tVertex Id: " + currentRanking.originalPos);
//                        System.out.print(" (" + currentRanking.getRanked() + ")");
//                    System.out.println();
//                } else {
//                    System.out.print(rank + "\t");
//                     if (printScore) {
//                        System.out.print(formatter.format(rankScore));
//                    }
//                    System.out.println("\t" + currentRanking.originalPos);
//
//                }
//                total += rankScore;
//                rank++;
//            }
//
//            if (verbose) {
//                System.out.println("Total: " + formatter.format(total));
//            }
//    }
//
//    /**
//     * Allows the user to specify whether or not s/he wants the rankings to be normalized.
//     * In some cases, this will have no effect since the algorithm doesn't allow normalization
//     * as an option
//     * @param normalizeRankings
//     */
//    public void setNormalizeRankings(boolean normalizeRankings) {
//        mNormalizeRankings = normalizeRankings;
//    }
//}
