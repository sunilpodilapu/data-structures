/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//Add your member variables here in WEEK 2
    private HashMap<GeographicPoint, MapNode> nodes;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		//Implement in this constructor in WEEK 2
        this.nodes = new HashMap<>();
    }
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		// Implement this method in WEEK 2
		return this.nodes.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//Implement this method in WEEK 2
		return this.nodes.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		// Implement this method in WEEK 2
		Set<MapEdge> edges = new HashSet<>();

		for(MapNode node : nodes.values()) {
			edges.addAll(node.getEdges());
		}

		return edges.size();
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// Implement this method in WEEK 2
        // try to insert otherwise return false
        boolean inserted = false;

        if (!nodes.containsKey(location)) {
            MapNode node = new MapNode(location);
            this.nodes.put(location, node);
            inserted = true;
        }

        return inserted;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		// Implement this method in WEEK 2
        // check if nodes exist
        MapNode fromNode = nodes.get(from);
        MapNode toNode = nodes.get(to);
        if (fromNode == null || toNode == null)
            throw new IllegalArgumentException("Node did not exist yet");

        // create new edge, validates params are good
        MapEdge edge = new MapEdge(from, to, roadName, roadType, length);

        // add edge to nodes, validate they have not been added yet
        if (fromNode.containsEdge(edge)) {
            throw new IllegalArgumentException("Edge already part of node");
        } else {
            fromNode.addEdge(edge);
        }
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Implement this method in WEEK 2

        // create a queue with starting node and visited list
		Queue<MapNode> queue = new ArrayDeque<>();
        Set<MapNode> visited = new HashSet<>();
        HashMap<MapNode, MapNode> parentMap = new HashMap<>();

        // starting node put in queue path
		queue.add(nodes.get(start));

        // visit each node in queue and add any new nodes until hit goal if do
        while (!queue.isEmpty()) {
            MapNode currentNode = queue.remove();

            // if the current node is the goal return the path
            if (currentNode == nodes.get(goal))
                return pathBuilder(nodes.get(start), nodes.get(goal), parentMap);

            // loop through current nodes edges to get next points
            for (MapEdge edge : currentNode.getEdges()) {
                MapNode next = nodes.get(edge.getTo());
                if (!visited.contains(next)) {
                    // add next node to queue and visited
                    visited.add(next);
                    queue.add(next);

                    // add next node and its parent to parent map
                    parentMap.put(next, currentNode);

                    // Hook for visualization.  See writeup.
                    nodeSearched.accept(next.getLocation());
                }
            }
        }

        // no path was found
		return null;
	}
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// Implement this method in WEEK 3
        Set<MapNode> visited = new HashSet<>();
        HashMap<MapNode, MapNode> parentMap = new HashMap<>();
        HashMap<MapNode, Double> distances = new HashMap<>();
        MapNode startNode = nodes.get(start);
        MapNode goalNode = nodes.get(goal);

        // priority queue and comparator implementation
        Queue<MapNode> queue = new PriorityQueue<>(new Comparator<MapNode>() {
            public int compare(MapNode node1, MapNode node2) {
                return (distances.get(node1) > distances.get(node2)) ? 1 : -1;
            }
        });

        // insert starting point into queue and distance hash
        queue.add(startNode);
        distances.put(startNode, 0.0);

        // cycle through queue until no more points
        while (!queue.isEmpty()) {

            // remove first node and check if visited yet
            MapNode currentNode = queue.remove();
            if (visited.contains(currentNode))
                continue;
            else
                visited.add(currentNode);

            // if current is the end goal node build path and return
            if (currentNode == goalNode)
                return pathBuilder(startNode, goalNode, parentMap);

            // loop through current nodes neighbors
            for (MapEdge edge : currentNode.getEdges()) {
                MapNode neighbor = nodes.get(edge.getTo());

                // if neighbor already visited skip
                if (visited.contains(neighbor))
                    continue;

                // if neighbor doesn't exist or if shorter distance update distances
                if (!distances.containsKey(neighbor) ||
                        distances.get(currentNode) + edge.getLength() < distances.get(neighbor)) {
                    distances.put(neighbor, edge.getLength() + distances.get(currentNode));
                    parentMap.put(neighbor, currentNode);
                    queue.add(neighbor);
                }

                // Hook for visualization.  See writeup.
                nodeSearched.accept(neighbor.getLocation());
            }
        }

        // no path was found
		return null;
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
        // Implement this method in WEEK 3
        Set<MapNode> visited = new HashSet<>();
        HashMap<MapNode, MapNode> parentMap = new HashMap<>();
        HashMap<MapNode, Double> distances = new HashMap<>();
        MapNode startNode = nodes.get(start);
        MapNode goalNode = nodes.get(goal);

        // priority queue and comparator implementation
        Queue<MapNode> queue = new PriorityQueue<>(new Comparator<MapNode>() {
            public int compare(MapNode node1, MapNode node2) {
                if (distances.get(node1) + node1.getLocation().distance(goal) >
                        distances.get(node2) + node2.getLocation().distance(goal))
                    return 1;
                else
                    return -1;
            }
        });

        // insert starting point into queue and distance hash
        queue.add(startNode);
        distances.put(startNode, 0.0);

        // cycle through queue until no more points
        while (!queue.isEmpty()) {

            // remove first node and check if visited yet
            MapNode currentNode = queue.remove();
            if (visited.contains(currentNode))
                continue;
            else
                visited.add(currentNode);

            // if current is the end goal node build path and return
            if (currentNode == goalNode)
                return pathBuilder(startNode, goalNode, parentMap);

            // loop through current nodes neighbors
            for (MapEdge edge : currentNode.getEdges()) {
                MapNode neighbor = nodes.get(edge.getTo());

                // if neighbor already visited skip
                if (visited.contains(neighbor))
                    continue;

                // if neighbor doesn't exist or if shorter distance update distances
                if (!distances.containsKey(neighbor) ||
                        distances.get(currentNode) + edge.getLength() < distances.get(neighbor)) {
                    distances.put(neighbor, edge.getLength() + distances.get(currentNode));
                    parentMap.put(neighbor, currentNode);
                    queue.add(neighbor);
                }

                // Hook for visualization.  See writeup.
                nodeSearched.accept(neighbor.getLocation());
            }
        }

        // no path was found
        return null;
	}


	/**
	 * Transforms parent map into list with correct path
	 *
	 * @param start MapNode starting point
	 * @param goal MapNode ending point
	 * @param parentMap HashMap with relationships
	 * @return List of GeographicPoints
	 */
	private List<GeographicPoint> pathBuilder(
			MapNode start, MapNode goal,
			HashMap<MapNode, MapNode> parentMap) {

		// initiate the path to build
		List<GeographicPoint> path = new LinkedList<>();
		MapNode currentNode = goal;
		path.add(currentNode.getLocation());

		// cycle through parent map and build path with GeographicPoints
		while(currentNode != start) {
			currentNode = parentMap.get(currentNode);
			path.add(currentNode.getLocation());
		}

		// reverse the path built and return
		Collections.reverse(path);
		return path;
	}
	
	
	public static void main(String[] args)
	{
        /*
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		*/

		// You can use this method for testing.  
		
		// Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
        System.out.println(route);

        /*
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
