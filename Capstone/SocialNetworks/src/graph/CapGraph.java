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
        List<Integer> vertices = new ArrayList<>();
        List<Integer> markers = new ArrayList<>();

        // first pass through graph
        vertices.addAll(this.getNodes().keySet());
        vertices = dfsSCC(this, vertices, markers, false);

        // second pass through graph
        markers = new ArrayList<>();
        vertices = dfsSCC(this, vertices, markers, true);

        // build graphs and return
		return buildSCCGraphs(vertices, markers);
	}

    private List<Integer> dfsSCC(CapGraph g, List<Integer> vertices, List<Integer> markers, boolean reverse) {
        List<Integer> visited = new ArrayList<>();
        List<Integer> finished = new ArrayList<>();
        int vertex;

        // loop through all vertices popping from the stack
        while (vertices.size() > 0) {
            vertex = vertices.remove(vertices.size() - 1);

            // only explore a vertex if its not in visited
            if (!visited.contains(vertex))
                dfsSCCVisited(g, vertex, visited, finished, reverse);

            // one a search completes set a marker to signal a SCC
            markers.add(finished.size() - 1);
        }

        return finished;
    }

    private void dfsSCCVisited(CapGraph g, int vertex, List<Integer> visited,
                               List<Integer> finished, boolean reverse) {
        Set<Node> neighbors;

        // add to visited if not already there
        if (!visited.contains(vertex))
            visited.add(vertex);

        // depending if this is forward pass or reverse, get vertex's neighbors
        if (reverse)
            neighbors = g.getNodes().get(vertex).getRevNeighbors();
        else
            neighbors = g.getNodes().get(vertex).getNeighbors();

        // for each neighbor do a depth first search of its neighbors
        for (Node neighbor : neighbors) {
            if (!visited.contains(neighbor.getSelf()))
                dfsSCCVisited(g, neighbor.getSelf(), visited, finished, reverse);
        }

        // finally add to finished if its not there already
        if (!finished.contains(vertex))
            finished.add(vertex);
    }



    private List<Graph> buildSCCGraphs(List<Integer> vertices, List<Integer> markers) {


        return null;
    }

    /**
     * Get all nodes currently in the graph
     * @return hashmap of nodes
     */
    public HashMap<Integer, Node> getNodes() {
        return this.graph;
    }

    /**
     * Builds hash of each vertex's total number of outgoing edges
     * @return hashmap
     */
    public HashMap<Integer, Integer> getNumNeighbors() {
        HashMap<Integer, Integer> connections = new HashMap<>();

        // get total number of outgoing neighbors for each vertex
        for (int vertex : graph.keySet())
            connections.put(vertex, graph.get(vertex).getNeighbors().size());

        return connections;
    }

    public void minCut() {

    }
}
