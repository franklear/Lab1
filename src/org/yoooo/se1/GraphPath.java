package org.yoooo.se1;

import java.util.*;

/**
 * A GraphPath represents a path in a graph.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class GraphPath<V, E> {
    private Graph<V, E> mGraph;
    private List<E> mEdgeList = new ArrayList<>();
    private boolean mWeightAssigned = false;
    private double mWeight;

    /**
     * Creates a new graph path over the specified graph.
     *
     * @param graph graph over which the path is created
     */
    public GraphPath(Graph<V, E> graph) {
        mGraph = graph;
    }

    /**
     * Returns the graph over which this path is defined.
     *
     * @return containing graph
     */
    public Graph<V, E> getGraph() {
        return mGraph;
    }

    /**
     * Returns the start vertex in this path.
     *
     * @return start vertex
     */
    public V getStartVertex() {
        if (mEdgeList.isEmpty()) {
            return null;
        }
        return mGraph.getEdgeSource(mEdgeList.get(0));
    }

    /**
     * Returns the end vertex in this path.
     *
     * @return end vertex
     */
    public V getEndVertex() {
        if (mEdgeList.isEmpty()) {
            return null;
        }
        return mGraph.getEdgeTarget(mEdgeList.get(mEdgeList.size() - 1));
    }

    /**
     * Returns the edges making up this path.
     *
     * @return list of edges
     */
    public List<E> getEdgeList() {
        return Collections.unmodifiableList(mEdgeList);
    }

    /**
     * Returns this path as a sequence of vertices.
     *
     * @return list of vertices
     */
    public List<V> getVertexList() {
        List<V> list = new ArrayList<>();
        if (mEdgeList.isEmpty()) {
            return list;
        }
        list.add(mGraph.getEdgeSource(mEdgeList.get(0)));
        for (E edge : mEdgeList) {
            list.add(mGraph.getEdgeTarget(edge));
        }
        return list;
    }

    /**
     * Returns the weight assigned to this path.
     *
     * @return weight of this path
     */
    public double getWeight() {
        if (mWeightAssigned) {
            return mWeight;
        }
        mWeight = 0;
        for (E edge : mEdgeList) {
            mWeight += mGraph.getEdgeWeight(edge);
        }
        mWeightAssigned = true;
        return mWeight;
    }

    /**
     * Sets the weight of this path.
     *
     * @param weight weight of this path
     */
    public void setWeight(double weight) {
        mWeight = weight;
        mWeightAssigned = true;
    }

    /**
     * Returns the length of this path, measured in the number of edges.
     *
     * @return length of this path
     */
    public int getLength() {
        return mEdgeList.size();
    }

    /**
     * Adds an edge to this path.
     *
     * @param edge edge to be added
     */
    public void addEdge(E edge) {
        mEdgeList.add(edge);
        mWeightAssigned = false;
    }
}
