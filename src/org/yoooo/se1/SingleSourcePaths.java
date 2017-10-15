package org.yoooo.se1;

import java.util.*;

/**
 * A set of paths starting from a single source.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class SingleSourcePaths<V, E> {
    private Graph<V, E> mGraph;
    private V mSource;
    private Map<V, E> mPredecessorMap;

    /**
     * Constructs a new instance.
     *
     * @param graph the graph
     * @param source the source vertex
     * @param predecessorMap a map contains edge to each vertex
     */
    public SingleSourcePaths(Graph<V, E> graph, V source, Map<V, E> predecessorMap) {
        mGraph = graph;
        mSource = source;
        mPredecessorMap = predecessorMap;
    }

    /**
     * Returns the graph over which this set of paths is defined.
     *
     * @return the graph
     */
    public Graph<V, E> getGraph() {
        return mGraph;
    }

    /**
     * Returns the source vertex.
     *
     * @return the source vertex
     */
    public V getSource() {
        return mSource;
    }

    /**
     * Returns the path from the source vertex to the sink vertex.
     *
     * @param sink the sink vertex
     * @return path from source to sink
     */
    public GraphPath<V, E> getPath(V sink) {
        if (mSource.equals(sink)) {
            return null;
        }
        LinkedList<E> edgeList = new LinkedList<>();
        while (!sink.equals(mSource)) {
            if (!mPredecessorMap.containsKey(sink)) {
                return null;
            }
            E edge = mPredecessorMap.get(sink);
            edgeList.addFirst(edge);
            sink = mGraph.getEdgeSource(edge);
        }
        GraphPath<V, E> path = new GraphPath<>(mGraph);
        for (E edge : edgeList) {
            path.addEdge(edge);
        }
        return path;
    }

    /**
     * Returns the predecessor map of paths, which contains edge to each vertex.
     *
     * @return predecessor map
     */
    public Map<V, E> getPredecessorMap() {
        return mPredecessorMap;
    }
}
