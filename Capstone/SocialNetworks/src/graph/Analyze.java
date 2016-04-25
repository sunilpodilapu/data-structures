package graph;

import java.io.File;
import java.util.*;

/**
 * Class used for running main analysis on the CapGraph
 * for Social Networks
 * @author Mark
 */
public class Analyze {

    public static void main(String[] args) {
        List<String> files = getFilenames();
        CapGraph graph;
        Double ratio;
        Graph egonet;
        List<Graph> sccs;
        int minCuts, current;

        for (String file : files) {
            try {
                graph = loadGraph(file);

                if (graph == null)
                    continue;
                System.out.println("Beginning graph " + file + " analysis");
                System.out.println("Total nodes: " + graph.getNodes().size());

                ratio = graph.getEdgeNodeRatio();
                System.out.println("Ratio: " + ratio);

                // get the egonet for each vertex
                int max = 0, sum = 0;
                for (Integer vertex : graph.getNodes().keySet()) {
                    egonet = graph.getEgonet(vertex);
                    current = egonet.exportGraph().size();

                    // track max and running total
                    if (max < current)
                        max = current;

                    sum += current;
                }
                System.out.println("Average egonet: " + (double) sum / graph.getNodes().size());
                System.out.println("Largest egonet: " + max);

                // reset variables
                max = 0;
                sum = 0;

                // get all scc's for the graph
                sccs = graph.getSCCs();
                for (Graph scc : sccs) {
                    current = scc.exportGraph().size();

                    // track max and running total
                    if (max < current)
                        max = current;

                    sum += current;
                }
                System.out.println("Total SCC's: " + sccs.size());
                System.out.println("Average SCC: " + (double) sum / sccs.size());
                System.out.println("Largest SCC: " + max);

                // calculate the min cuts
                minCuts = graph.getMinCuts(graph.getNodes().size() / 4);
                System.out.println("Min cuts: " + minCuts);

                System.out.println("Graph " + file + " completed\n");
            }

            // if any exceptions encountered during processing graph continue
            // processing rest of graphs
            catch(Exception e) {
               System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Slightly modified version of loading a CapGraph
     * @param filename - location of file to load
     * @return CapGraph
     */
    public static CapGraph loadGraph(String filename) {
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

    /**
     * Houses all files to load and utilize in analysis
     * @return list of filenames
     */
    public static List<String> getFilenames() {
        List<String> filenames = new ArrayList<>();
        filenames.add("data/small_test_graph.txt");
        filenames.add("data/facebook_1000.txt");
        filenames.add("data/facebook_2000.txt");
        //filenames.add("data/facebook_ucsd.txt");
        //filenames.add("data/twitter_combined.txt");
        //filenames.add("data/twitter_higgs.txt");
        return filenames;
    }
}
