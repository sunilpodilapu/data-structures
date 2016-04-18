package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mark
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	private HashMap<Integer, Node> graph;

    public CapGraph() {
        graph = new HashMap<>();
    }

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
        if (!graph.containsKey(num))
            graph.put(num, new Node(num));
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
        // create the nodes first, if not already there
        addVertex(from);
        addVertex(to);

        // create edge between nodes
        if (!graph.get(from).containsNeighbor(to))
            graph.get(from).getNeighbors().add(graph.get(to));
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
        CapGraph g = new CapGraph();

        // if node doesn't exist then return empty graph
        if (!graph.containsKey(center))
            return g;

        // add the center's outgoing connections
        for (Node n : graph.get(center).getNeighbors())
            g.addEdge(center, n.getSelf());

        // add the center's incoming connections
        for (Node n : graph.get(center).getRevNeighbors())
            g.addEdge(n.getSelf(), center);

        // check all added nodes for connections between them
        Set<Integer> gNodes = g.getNodes().keySet();
        for (Integer i : gNodes) {

            // center's edges already added
            if (i == center)
                continue;

            for (Node n : graph.get(i).getNeighbors()) {
                if (gNodes.contains(n.getSelf()))
                    g.addEdge(i, n.getSelf());
            }
        }

		return g;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		List<Graph> graphList = new ArrayList<>();



		return graphList;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
        HashMap<Integer, HashSet<Integer>> g = new HashMap<>();
        HashSet<Integer> neighbors;

        // build graph to return
        for (Integer num : graph.keySet()) {
            neighbors = new HashSet<>();

            // build set of neighbors for the node
            neighbors.addAll(graph.get(num).getNeighbors().stream().map(
                    neighbor -> neighbor.getSelf()).collect(Collectors.toList()));

            // place in newly built graph
            g.put(num, neighbors);
        }

		return g;
	}

    /**
     * Get all nodes currently in the graph
     * @return hashmap of nodes
     */
    public HashMap<Integer, Node> getNodes() {
        return this.graph;
    }

}
