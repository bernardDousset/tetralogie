//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Stack;
//
//import org.apache.commons.collections15.Buffer;
//import org.apache.commons.collections15.Factory;
//import org.apache.commons.collections15.buffer.UnboundedFifoBuffer;
//import org.apache.commons.collections15.map.LazyMap;
//
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.UndirectedGraph;
//
///**
// * Computes betweenness centrality for each vertex and edge in the graph. The result is that each vertex
// * and edge has a UserData element of type MutableDouble whose key is 'centrality.BetweennessCentrality'.
// * Note: Many social network researchers like to normalize the betweenness values by dividing the values by
// * (n-1)(n-2)/2. The values given here are unnormalized.<p>
// *
// * A simple example of usage is:
// * <pre>
// * BetweennessCentrality ranker = new BetweennessCentrality(someGraph);
// * ranker.evaluate();
// * ranker.printRankings();
// * </pre>
// *
// * Running time is: O(n^2 + nm).
// * @see "Ulrik Brandes: A Faster Algorithm for Betweenness Centrality. Journal of Mathematical Sociology 25(2):163-177, 2001."
// * @author Scott White
// * @author Tom Nelson converted to jung2
// */
//
//public class BetweennessCentrality<Sommet , Arete>{ //extends AbstractRanker<Sommet,Arete> {
//    public static final String CENTRALITY = "centrality.BetweennessCentrality";
//	 Graphe mGraph;
//	    private List<Ranking<?>> mRankings;
//	    private boolean mRemoveRankScoresOnFinalize;
//	    private boolean mRankNodes;
//	    private boolean mRankEdges;
//	    private boolean mNormalizeRankings;
//	    protected Map<Object,Map<Sommet, Number>> vertexRankScores = 
//	    	LazyMap.decorate(
//	    			new HashMap<Object,Map<Sommet,Number>>(),
//	    			new Factory<Map<Sommet,Number>>() {
//						public Map<Sommet,Number> create() {
//							return new HashMap<Sommet,Number>();
//						}});
//	    protected Map<Object,Map<Arete, Number>> edgeRankScores = 
//	    	LazyMap.decorate(
//	    			new HashMap<Object,Map<Arete,Number>>(),
//	    			new Factory<Map<Arete,Number>>() {
//						public Map<Arete,Number> create() {
//							return new HashMap<Arete,Number>();
//						}});
//	    private Map<Arete,Number> edgeWeights = new HashMap<Arete,Number>();
//
//	protected void initialize(Graphe graph, boolean isNodeRanker, 
//	        boolean isEdgeRanker) {
//	        if (!isNodeRanker && !isEdgeRanker)
//	            throw new IllegalArgumentException("Must rank edges, vertices, or both");
//	        mGraph = graph;
//	        mRemoveRankScoresOnFinalize = true;
//	        mNormalizeRankings = true;
//	        mRankNodes = isNodeRanker;
//	        mRankEdges = isEdgeRanker;
//	    }
//
//
//    /**
//     * Constructor which initializes the algorithm
//     * @param g the graph whose nodes are to be analyzed
//     */
//    public BetweennessCentrality(Graphe g) {
//        initialize(g, true, true);
//    }
//
//    public BetweennessCentrality(Graphe g, boolean rankNodes) {
//        initialize(g, rankNodes, true);
//    }
//
//    public BetweennessCentrality(Graphe g, boolean rankNodes, boolean rankEdges)
//    {
//        initialize(g, rankNodes, rankEdges);
//    }
//    
//	protected void computeBetweenness(Graphe graph) {
//
//    	Map<Sommet,BetweennessData> decorator = new HashMap<Sommet,BetweennessData>();
//    	Map<Sommet,Number> bcVertexDecorator = 	vertexRankScores.get(getRankScoreKey());
//    	bcVertexDecorator.clear();
//    	Map<Arete,Number> bcEdgeDecorator = edgeRankScores.get(getRankScoreKey());
//    	bcEdgeDecorator.clear();
//        
//        Collection<Sommet> vertices = graph.sommets;
//        
//        for (Sommet s : vertices) {
//
//            initializeData(graph,decorator);
//
//            decorator.get(s).numSPs = 1;
//            decorator.get(s).distance = 0;
//
//            Stack<Sommet> stack = new Stack<Sommet>();
//            Buffer<Sommet> queue = new UnboundedFifoBuffer<Sommet>();
//            queue.add(s);
//
//            while (!queue.isEmpty()) {
//                Sommet v = queue.remove();
//                stack.push(v);
//                for(int i = 0 ; i< graph.sommets.size(); i++){
//                	
//                Sommet w  = (Sommet) graph.sommets.elementAt(i);
//                //for(Sommet w : getGraph().getSuccessors(v)) {
//
//                    if (decorator.get(w).distance < 0) {
//                        queue.add(w);
//                        decorator.get(w).distance = decorator.get(v).distance + 1;
//                    }
//
//                    if (decorator.get(w).distance == decorator.get(v).distance + 1) {
//                        decorator.get(w).numSPs += decorator.get(v).numSPs;
//                        decorator.get(w).predecessors.add(v);
//                    }
//                }
//            }
//            
//            while (!stack.isEmpty()) {
//                Sommet w = stack.pop();
//
//                for (Sommet v : decorator.get(w).predecessors) {
//
//                    double partialDependency = (decorator.get(v).numSPs / decorator.get(w).numSPs);
//                    partialDependency *= (1.0 + decorator.get(w).dependency);
//                    decorator.get(v).dependency +=  partialDependency;
//                    int indice = graph.areteDansGraphe(v, w);
//                    double edgeValue = bcEdgeDecorator.get(currentEdge).doubleValue();
//                    edgeValue += partialDependency;
//                    bcEdgeDecorator.put(currentEdge, edgeValue);
//                }
//                if (w != s) {
//                	double bcValue = bcVertexDecorator.get(w).doubleValue();
//                	bcValue += decorator.get(w).dependency;
//                	bcVertexDecorator.put(w, bcValue);
//                }
//            }
//        }
//
//        if(graph instanceof UndirectedGraph) {
//            for (Sommet v : vertices) { 
//            	double bcValue = bcVertexDecorator.get(v).doubleValue();
//            	bcValue /= 2.0;
//            	bcVertexDecorator.put(v, bcValue);
//            }
//            for (Arete e : graph.getEdges()) {
//            	double bcValue = bcEdgeDecorator.get(e).doubleValue();
//            	bcValue /= 2.0;
//            	bcEdgeDecorator.put(e, bcValue);
//            }
//        }
//
//        for (Sommet vertex : vertices) {
//            decorator.remove(vertex);
//        }
//    }
//
//	private void initializeData(Graphe g, Map<Sommet, BetweennessData> decorator) {
//		for (int i = 0; i < g.sommets.size(); i++) {
//			Sommet vertex = (Sommet) g.sommets.elementAt(i);
//			Map<Sommet, Number> bcVertexDecorator = vertexRankScores
//					.get(getRankScoreKey());
//			if (bcVertexDecorator.containsKey(vertex) == false) {
//				bcVertexDecorator.put(vertex, 0.0);
//			}
//			decorator.put(vertex, new BetweennessData());
//		}
//		for(int i =0; i< g.aretes.size(); i++){
//          Arete e = (Arete) g.aretes.elementAt(i);
//			Map<Arete, Number> bcEdgeDecorator = edgeRankScores
//					.get(getRankScoreKey());
//			if (bcEdgeDecorator.containsKey(e) == false) {
//				bcEdgeDecorator.put(e, 0.0);
//			}
//		}
//	}
//    
//    /**
//     * the user datum key used to store the rank scores
//     * @return the key
//     */
//    @Override
//    public String getRankScoreKey() {
//        return CENTRALITY;
//    }
//
//    @Override
//    public void step() {
//        computeBetweenness(getGraph());
//    }
//    /**
//     * 
//     * 
//     *
//     */
//    class BetweennessData {
//        double distance;
//        double numSPs;
//        List<Sommet> predecessors;
//        double dependency;
//
//        BetweennessData() {
//            distance = -1;
//            numSPs = 0;
//            predecessors = new ArrayList<Sommet>();
//            dependency = 0;
//        }
//    }
//}
