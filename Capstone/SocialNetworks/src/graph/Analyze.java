package graph;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Class used for running main analysis on the CapGraph
 * for Social Networks
 */
public class Analyze {

    public void run() {

    }

    /**
     * Slightly modified version of loading a CapGraph
     * @param filename - location of file to load
     * @return CapGraph
     */
    public CapGraph loadGraph(String filename) {
        Scanner sc;
        CapGraph graph = new CapGraph();
        int vertex1, vertex2;
        Set<Integer> seen = new HashSet<>();

        // try to open file
        try { sc = new Scanner(new File(filename)); }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        while (sc.hasNextInt()) {
            vertex1 = sc.nextInt();
            vertex2 = sc.nextInt();

            if (!seen.contains(vertex1)) {
                graph.addVertex(vertex1);
                seen.add(vertex1);
            }

            if (!seen.contains(vertex2)) {
                graph.addVertex(vertex2);
                seen.add(vertex2);
            }

            graph.addEdge(vertex1, vertex2);
        }

        sc.close();
        return graph;
    }
}
// version 2 - whole graph analysis
// num edges / num vertices
// average egonet size and largest
// SCC - largest or average or both
// number of cuts for min