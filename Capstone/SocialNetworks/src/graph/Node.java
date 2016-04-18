package graph;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Node class for graph
 */
public class Node {

    // instance variable for node's descriptor
    private int node;

    // set of incoming and reversed neighbors
    private SortedSet<Node> neighbors;
    private SortedSet<Node> reverseNeighbors;

    /**
     * Constructor for node class.  Initializes sets and sets the node variable.
     * @param node - int descriptor
     */
    public Node(int node) {
        this.node = node;
        neighbors = new TreeSet<>();
        reverseNeighbors = new TreeSet<>();
    }

    /**
     * Get the int descriptor for this node
     * @return int descriptor
     */
    public int getSelf() {
        return this.node;
    }

    /**
     * Adds a neighbor to a node and in turn adds to that nodes
     * reversed neighbor list
     * @param n - node to add
     */
    public void addNeighbor(Node n) {
        neighbors.add(n);
        n.addRevNeighbor(this);
    }

    /**
     * Get the set of neighbors for this node.
     * @return set of neighbors, sorted
     */
    public SortedSet<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Adds the reverse neighbor for a node
     * @param n - node to add
     */
    public void addRevNeighbor(Node n) {
        reverseNeighbors.add(n);
    }

    /**
     * Get the set of reverse neighbors for this node.
     * @return set of reverse neighbors, sorted
     */
    public SortedSet<Node> getRevNeighbors() {
        return reverseNeighbors;
    }

    /**
     * Get the number of neighbors for this node
     * @return int of num neighbors
     */
    public int getNumNbrs() {
        return neighbors.size();
    }

    /**
     * Get the number of reversed neighbors for this node
     * @return int of num reversed neighbors
     */
    public int getNumRevNbrs() {
        return reverseNeighbors.size();
    }
}
