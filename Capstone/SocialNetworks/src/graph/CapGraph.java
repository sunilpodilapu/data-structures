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
        if (!graph.get(from).containsNeighbor(graph.get(to)))
            graph.get(from).addNeighbor(graph.get(to));
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
        vertices = dfsSCC(vertices, markers, false);

        // second pass through graph
        markers = new ArrayList<>();
        vertices = dfsSCC(vertices, markers, true);

        // build graphs and return
		return buildSCCGraphs(vertices, markers);
	}

    /**
     * Initiates depth first search on graph in order to find Strongly Connected Components.
     * First pass sorts the vertices in the order the SCC's exist.  Second pass should be searched
     * in reverse and places the vertices in the correct order of their SCC and notates those
     * locations in the markers list
     * @param vertices - list of vertices in graph to search
     * @param markers - list points where SCC's could exist
     * @param reverse - boolean whether to reverse search the graph (flip directional edges)
     * @return order list of vertices
     */
    private List<Integer> dfsSCC(List<Integer> vertices, List<Integer> markers, boolean reverse) {
        List<Integer> visited = new ArrayList<>();
        List<Integer> finished = new ArrayList<>();
        int vertex;

        // loop through all vertices popping from the stack
        while (vertices.size() > 0) {
            vertex = vertices.remove(vertices.size() - 1);

            // only explore a vertex if its not in visited
            if (!visited.contains(vertex)) {
                dfsSCCVisited(vertex, visited, finished, reverse);

                // one a search completes set a marker to signal a SCC
                markers.add(finished.size() - 1);
            }
        }

        return finished;
    }

    /**
     * Helper method for DFS-SCC.  Recursively searches a vertex's neighbor for more
     * neighbors and is where vertices are added to both the visited list and the
     * finished list for their appropiate ordering
     * @param vertex - vertex whos neighbors are to be searched
     * @param visited - list of visited vertices
     * @param finished - list of finished vertices
     * @param reverse - boolean whether or not to flip the edge directions
     */
    private void dfsSCCVisited(int vertex, List<Integer> visited,
                               List<Integer> finished, boolean reverse) {
        Set<Node> neighbors;

        // add to visited if not already there
        visited.add(vertex);

        // depending if this is forward pass or reverse, get vertex's neighbors
        if (reverse)
            neighbors = graph.get(vertex).getRevNeighbors();
        else
            neighbors = graph.get(vertex).getNeighbors();

        // for each neighbor do a depth first search of its neighbors
        for (Node neighbor : neighbors) {
            if (!visited.contains(neighbor.getSelf()))
                dfsSCCVisited(neighbor.getSelf(), visited, finished, reverse);
        }

        // add vertex to finished
        if (!finished.contains(vertex))
            finished.add(vertex);
    }

    /**
     * Rebuild graphs based on the DFS-SCC results. Assumes markers have correct location
     * of where one SCC stops and another begins.  Connects vertices only if they are
     * within the same mini graph.
     * @param vertices - list of vertices to connect (in order)
     * @param markers - list of locations for corresponding vertices in SCC's
     * @return list of graphs based on SCC's in main graph
     */
    private List<Graph> buildSCCGraphs(List<Integer> vertices, List<Integer> markers) {
        List<Graph> graphs = new ArrayList<>();
        Graph newGraph = new CapGraph();
        int currentPosition = markers.remove(0);
        int lastPosition = 0;

        // check each vertex and add it to a graph
        for (int i = 0; i < vertices.size(); i++) {
            newGraph.addVertex(vertices.get(i));

            // grab all verticex between last and current position and build edges
            for (int j = lastPosition; j < currentPosition; j++) {
                if (graph.get(vertices.get(i)).containsNeighbor(graph.get(vertices.get(j))))
                    newGraph.addEdge(vertices.get(i), vertices.get(j));
            }

            // move all position markers and create new graph
            if (i == currentPosition) {
                lastPosition = currentPosition;
                graphs.add(newGraph);
                newGraph = new CapGraph();

                if (markers.size() > 0)
                    currentPosition = markers.remove(0);
            }
        }

        return graphs;
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

    public int getMinCuts(int numTrials) {
        int min_cut = Integer.MAX_VALUE;
        int current_cut;
        CapGraph graph_copy;

        // perform specified number of trials
        System.out.print("Trial");
        for (int numTrial = 0; numTrial < numTrials; numTrial++) {
            System.out.print(numTrial + "...");

            // copy graph, initialize karger min, and find min cuts
            graph_copy = copy();
            graph_copy.initKargerMinCut();
            current_cut = kargerMinCut(graph_copy);

            // set min cut if current cut is lower
            if (current_cut < min_cut)
                min_cut = current_cut;
        }

        System.out.println("finished");
        return min_cut;
    }

    private int kargerMinCut(CapGraph g) {
        int graph_size = g.getNodes().size();
        Node node_u, node_v;
        Set<Node> neighbors;

        // keep contracting
        while (graph_size > 2) {

            // get a random edge and add contracted node_u to node_v karger list
            node_u = getRandomChoice(g.getNodes().values());
            node_v = getRandomChoice(node_u.getNeighbors());
            node_v.absorbNode(node_u);

            // get all outgoing edges
            neighbors = node_u.getNeighbors();
            for (Node n : neighbors) {

                // if the nodes are the same skip
                if (n.getSelf() == node_u.getSelf() || n.getSelf() == node_v.getSelf())
                    continue;

                // add outgoing connection to node_v
                node_v.addNeighbor(n);
            }

            // grab all incoming edges
            neighbors = node_u.getRevNeighbors();
            for (Node n : neighbors) {

                // if nodes are the same then skip
                if (n.getSelf() == node_u.getSelf() || n.getSelf() == node_v.getSelf())
                    continue;

                // add incoming connectings to node_v
                n.addNeighbor(node_v);
            }

            // remove node from graph and get new graph size
            g.removeNode(node_u);
            graph_size = g.getNodes().size();
        }

        return getRandomChoice(g.getNodes().values()).getNodesAbsorbed().size();
    }

    private Node getRandomChoice(Collection<Node> vertices) {
        Random rand = new Random();
        Iterator<Node> iterator = vertices.iterator();

        // set random value to get and iterate till its found
        int index = rand.nextInt(vertices.size());
        for (int i = 0; i < index; i++)
            iterator.next();

        return iterator.next();
    }

    public void removeNode(Node n) {

        for (Integer vertex : graph.keySet()) {

            if (vertex == n.getSelf())
                continue;

            n.removeNeighbor(graph.get(vertex));
            graph.get(vertex).removeNeighbor(n);
        }
    }

    public CapGraph copy() {
        CapGraph g = new CapGraph();

        for (Integer vertex : graph.keySet()) {
            for (Node n : graph.get(vertex).getNeighbors())
                g.addEdge(vertex, n.getSelf());
        }

        return g;
    }

    private void initKargerMinCut() {
        for (int vertex : graph.keySet())
            graph.get(vertex).initNodesAbsorbed();
    }
}
