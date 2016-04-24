package test;

import graph.CapGraph;
import graph.Graph;
import graph.Node;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test suite for Graph class
 */
public class CapGraphTest {

    private static CapGraph graph;

    /**
     * Builds sample graph to test
     */
    @BeforeClass
    public static void load() {
        System.out.println("Loading sample graph");
        graph = new CapGraph();
        graph.addEdge(18, 23);
        graph.addEdge(18, 44);
        graph.addEdge(23, 18);
        graph.addEdge(23, 25);
        graph.addEdge(25, 23);
        graph.addEdge(25, 18);
        graph.addEdge(25, 65);
        graph.addEdge(44, 32);
        graph.addEdge(44, 50);
        graph.addEdge(50, 32);
        graph.addEdge(65, 23);

    }

    @Test
    public void loadGraph() {
        System.out.println("Testing graph load");
        HashMap<Integer, Node> nodes = graph.getNodes();
        assertTrue(nodes.get(65).containsNeighbor(23));
        assertTrue(nodes.get(25).containsNeighbor(18));
        assertFalse(nodes.get(23).containsNeighbor(44));
        assertFalse(nodes.get(32).containsNeighbor(50));

        // need to test reverse neighbors due to issue TODO
    }

    @Test
    public void getEgonet() {
        System.out.println("Testing egonet");
        Graph en = graph.getEgonet(25);
        assertTrue(en.exportGraph().get(65).contains(23));
        assertFalse(en.exportGraph().get(65).contains(18));
    }

    @Test
    public void getSCC() {
        List<Graph> graphs = graph.getSCCs();
        int graphSize = 0;
        Graph largest = new CapGraph();

        // there should be 4 graphs
        System.out.println("Testing num of SCC graphs");
        assertTrue(4 == graphs.size());

        // get the largest graph
        for (Graph g : graphs) {
            if (g.exportGraph().size() > graphSize) {
                graphSize = g.exportGraph().size();
                largest = g;
            }
        }

        // largest graph should have 4 nodes
        System.out.println("Testing size of largest SCC graph");
        assertTrue(4 == largest.exportGraph().size());
    }

    @Test
    public void numConnections() {  //TODO revise to vertices/edges ratio test
        System.out.println("Testing number of connections");
        HashMap<Integer, Integer> connections = graph.getNumNeighbors();
        assertTrue(3 == connections.get(25));
        assertTrue(2 == connections.get(44));
        assertFalse(1 == connections.get(32));
    }

    @Test
    public void kargerMinCut() {


    }
}
