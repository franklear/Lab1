package org.yoooo.se1;

import java.util.*;

public class RandomWalk {
    /**
     * Returns a trail by walking in graph randomly until no outgoing edges or visited edge is
     * encountered, represented by vertices joined by spaces.
     *
     * @return random trail represented by vertices joined by spaces.
     */
    public static String randomWalk() {
        Graph<String, Integer> graph = Application.getInstance().getGraph();
        String[] vertices = graph.vertexSet().toArray(new String[0]);
        List<String> result = new ArrayList<>();
        Random random = Application.getInstance().getRandom();
        result.add(vertices[random.nextInt(vertices.length)]);
        Set<Integer> visited = new HashSet<>();
        while (true) {
            Integer[] edges = graph.outgoingEdgesOf(result.get(result.size() - 1))
                    .toArray(new Integer[0]);
            if (edges.length == 0) {
                break;
            }
            Integer edge = edges[random.nextInt(edges.length)];
            result.add(graph.getEdgeTarget(edge));
            if (visited.contains(edge)) {
                break;
            }
            visited.add(edge);
        }
        ShowDirectedGraph.showDirectedGraph(graph, UUID.randomUUID().toString(), new HashSet<>(result), visited);
        return String.join(" ", result);
    }
}
