package test;

import graph.Node;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Test suite for Node class
 */
public class NodeTest {

    /**
     * Tests creating a node and retrieving itself
     */
    @Test
    public void createNode() {
        System.out.println("Testing node creation");

        // create node and assert it is itself
        Node n = new Node(1);
        assertEquals(1, n.getSelf());
    }

    /**
     * Create a few nodes and add edges between them
     * then test the nodes were added correctly along with
     * their reverse directions.
     */
    @Test
    public void addNeighbor() {
        System.out.println("Testing adding node neighbors");

        // create the nodes to use
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        // add all the neighbors
        node1.addNeighbor(node2);
        node2.addNeighbor(node3);
        node3.addNeighbor(node4);
        node4.addNeighbor(node1);
        node4.addNeighbor(node2);

        // test edge validity
        assertEquals(1, node1.numNeighbors());
        assertEquals(1, node3.numNeighbors());
        assertTrue(node2.containsNeighbor(node3));
        assertTrue(node4.containsNeighbor(node2));

        // test reverse edge validity
        assertEquals(2, node2.numReverseNeighbors());
        assertEquals(1, node4.numReverseNeighbors());
        assertTrue(node1.getRevNeighbors().contains(node4));
        assertTrue(node3.getRevNeighbors().contains(node2));
    }
}
