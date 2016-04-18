package test;

import graph.CapGraph;
import graph.Graph;
import graph.Node;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

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

        System.out.println("Sample graph loaded");
    }

    @Test
    public void loadGraph() {
        HashMap<Integer, Node> nodes = graph.getNodes();
        assertTrue(nodes.get(65).containsNeighbor(23));
        assertTrue(nodes.get(25).containsNeighbor(18));
        assertFalse(nodes.get(23).containsNeighbor(44));
        assertFalse(nodes.get(32).containsNeighbor(50));
        System.out.println("Load graph tests passed");
    }

    @Test
    public void getEgonet() {
        Graph en = graph.getEgonet(25);
        assertTrue(en.exportGraph().get(65).contains(23));
        assertFalse(en.exportGraph().get(65).contains(18));
    }

    @Ignore
    public void getSCC() {

    }

    @Ignore
    public void numConnections() {

    }
}
