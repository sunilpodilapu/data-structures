package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        graph.get(from).addNeighbor(graph.get(to));
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
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

}
