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
 * @author Mark
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
        assertTrue(nodes.get(65).containsNeighbor(nodes.get(23)));
        assertTrue(nodes.get(25).containsNeighbor(nodes.get(18)));
        assertFalse(nodes.get(23).containsNeighbor(nodes.get(44)));
        assertFalse(nodes.get(32).containsNeighbor(nodes.get(50)));
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
    public void numConnections() {
        System.out.println("Testing number of connections");
        assertTrue(graph.getEdgeNodeRatio() - ((double) 7 / 11) < 0.001);
    }

    @Test
    public void kargerMinCut() {
        System.out.println("Testing karger min cut");
        CapGraph graph2 = new CapGraph();
        graph2.addEdge(1, 2);
        graph2.addEdge(1, 3);
        graph2.addEdge(1, 4);
        graph2.addEdge(2, 1);
        graph2.addEdge(2, 3);
        graph2.addEdge(2, 4);
        graph2.addEdge(3, 1);
        graph2.addEdge(3, 2);
        graph2.addEdge(3, 5);
        graph2.addEdge(4, 1);
        graph2.addEdge(4, 2);
        graph2.addEdge(4, 5);
        graph2.addEdge(5, 3);
        graph2.addEdge(5, 4);
        int cuts = graph2.getMinCuts(20);
        assertTrue(4 == cuts);

        cuts = graph.getMinCuts(100);
        assertTrue(1 == cuts);
    }
}
