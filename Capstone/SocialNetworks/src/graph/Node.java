package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Node class for graph
 */
public class Node {

    // instance variable for node's descriptor
    private int node;

    // set of incoming and reversed neighbors
    private Set<Node> neighbors;
    private Set<Node> reverseNeighbors;
    private List<Node> kargerNeighbors;

    /**
     * Constructor for node class.  Initializes sets and sets the node variable.
     * @param node - int descriptor
     */
    public Node(int node) {
        this.node = node;
        neighbors = new HashSet<>();
        reverseNeighbors = new HashSet<>();
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
    public Set<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * Checks if a there is an edge between this node and given node
     * @param n - Node to check
     * @return boolean
     */
    public boolean containsNeighbor(Node n) {
        for (Node neighbor : neighbors) {
            if (n.getSelf() == neighbor.getSelf())
                return true;
        }

        return false;
    }

    /**
     * Adds the reverse neighbor for a node
     * @param n - node to add
     */
    private void addRevNeighbor(Node n) {
        reverseNeighbors.add(n);
    }

    /**
     * Get the set of reverse neighbors for this node.
     * @return set of reverse neighbors, sorted
     */
    public Set<Node> getRevNeighbors() {
        return reverseNeighbors;
    }

    /**
     * Get the number of neighbors for this node
     * @return int of num neighbors
     */
    public int numNeighbors() {
        return neighbors.size();
    }

    /**
     * Get the number of reversed neighbors for this node
     * @return int of num reversed neighbors
     */
    public int numReverseNeighbors() {
        return reverseNeighbors.size();
    }

    public void removeNeighbor(Node n) {
        if (neighbors.contains(n)) {
            neighbors.remove(n);
            n.removeRevNeighbor(this);
        }
    }

    private void removeRevNeighbor(Node n) {
        if (reverseNeighbors.contains(n))
            reverseNeighbors.remove(n);
    }

    public Node copy() {
        return null;
    }

    public void initKarger() {
        kargerNeighbors = new ArrayList<>();
    }

    public void contractNeighbor(Node n) {
        if (kargerNeighbors != null)
            kargerNeighbors.add(n);
    }
}
