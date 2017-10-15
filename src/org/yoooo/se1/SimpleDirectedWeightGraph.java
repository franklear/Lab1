package org.yoooo.se1;

import com.sun.istack.internal.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of simple directed weighted using adjacency list.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class SimpleDirectedWeightGraph<V, E> implements WeightedGraph<V, E> {
    private Map<V, Set<E>> mOutgoingEdges = new HashMap<>();
    private Map<E, V> mSourceVertexMap = new HashMap<>();
    private Map<E, V> mTargetVertexMap = new HashMap<>();
    private Map<E, Double> mEdgeWeightMap = new HashMap<>();
    private Map<V, Map<V, E>> mVertexOutEdgeMap = new HashMap<>();
    private EdgeFactory<V, E> mEdgeFactory;

    /**
     * Creates a new simple directed graph.
     *
     * @param edgeFactory edge factory of the new graph.
     */
    public SimpleDirectedWeightGraph(@NotNull EdgeFactory<V, E> edgeFactory) {
        mEdgeFactory = edgeFactory;
    }

    /**
     * @see Graph#outgoingEdgesOf(Object)
     */
    @Override
    public Set<E> outgoingEdgesOf(V v) {
        if (mOutgoingEdges.containsKey(v)) {
            return Collections.unmodifiableSet(mOutgoingEdges.get(v));
        } else {
            return null;
        }
    }

    /**
     * @see WeightedGraph#setEdgeWeight(Object, double)
     */
    @Override
    public void setEdgeWeight(E e, double weight) {
        if (mEdgeWeightMap.containsKey(e)) {
            mEdgeWeightMap.put(e, weight);
        } else {
            throw new IllegalArgumentException("Edge does not exist");
        }
    }

    /**
     * @see Graph#getEdge(Object, Object)
     */
    @Override
    public E getEdge(V source, V target) {
        if (!mOutgoingEdges.containsKey(source) || !mOutgoingEdges.containsKey(target)) {
            return null;
        }
        return mVertexOutEdgeMap.get(source).get(target);
    }

    /**
     * @see Graph#addEdge(Object, Object)
     */
    @Override
    public E addEdge(V source, V target) {
        if (!mOutgoingEdges.containsKey(source) || !mOutgoingEdges.containsKey(target)) {
            return null;
        }
        E edge = mEdgeFactory.createEdge(source, target);
        mOutgoingEdges.get(source).add(edge);
        mSourceVertexMap.put(edge, source);
        mTargetVertexMap.put(edge, target);
        mEdgeWeightMap.put(edge, 1.0);
        mVertexOutEdgeMap.get(source).put(target, edge);
        return edge;
    }

    /**
     * @see Graph#addVertex(Object)
     */
    @Override
    public boolean addVertex(V v) {
        if (mOutgoingEdges.containsKey(v)) {
            return false;
        }
        mOutgoingEdges.put(v, new HashSet<>());
        mVertexOutEdgeMap.put(v, new HashMap<>());
        return true;
    }

    /**
     * @see Graph#getEdgeSource(Object)
     */
    @Override
    public V getEdgeSource(E e) {
        return mSourceVertexMap.get(e);
    }

    /**
     * @see Graph#getEdgeTarget(Object)
     */
    @Override
    public V getEdgeTarget(E e) {
        return mTargetVertexMap.get(e);
    }

    /**
     * @see Graph#getEdgeWeight(Object)
     */
    @Override
    public double getEdgeWeight(E e) {
        return mEdgeWeightMap.get(e);
    }

    /**
     * @see Graph#vertexSet()
     */
    @Override
    public Set<V> vertexSet() {
        return Collections.unmodifiableSet(mOutgoingEdges.keySet());
    }

    /**
     * @see Graph#outDegreeOf(Object)
     */
    @Override
    public int outDegreeOf(V v) {
        return 0;
    }
}
