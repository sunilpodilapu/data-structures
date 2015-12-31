package roadgraph;

import geography.GeographicPoint;

import java.util.HashSet;
import java.util.Set;


/**
 * MapNode class to be used in MapGraph
 *
 * @author Mark
 */
public class MapNode {
    GeographicPoint location;
    Set<MapEdge> edges;

    public MapNode(GeographicPoint location) {
        this.location = location;
        edges = new HashSet<>();
    }

    /**
     * Add an edge which is connected to node
     * @param edge MapEdge connected to node
     * @return boolean
     */
    public boolean addEdge(MapEdge edge) {
        return this.edges.add(edge);
    }

    /**
     * Check whether a node contains an edge
     * @param edge MapEdge to check
     * @return boolean
     */
    public boolean containsEdge(MapEdge edge) {
        return edges.contains(edge);
    }

    /**
     * Return all edges connected to node
     * @return boolean
     */
    public Set<MapEdge> getEdges() {
        return this.edges;
    }
}
