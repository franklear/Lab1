package org.yoooo.se1;

import java.util.Set;
import java.util.*;

/**
 * The root interface in the graph hierarchy.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public interface Graph<V, E> {
    /**
     * Returns an edge connecting source vertex to target vertex if such vertices and such edge
     * exists in this graph. Others returns null.
     *
     * @param source source vertex of the edge
     * @param target target vertex of the edge
     * @return an edge connecting source to target
     */
    E getEdge(V source, V target);

    /**
     * Creates a new edge in this graph, going from source vertex to target vertex, and returns
     * the created edge.
     *
     * @param source source vertex of the edge
     * @param target target vertex of the edge
     * @return created edge, otherwise null
     */
    E addEdge(V source, V target);

    /**
     * Adds vertex to this graph if not present.
     *
     * @param v vertex to be added
     * @return true if vertex was added
     */
    boolean addVertex(V v);

    /**
     * Returns a set of all edges outgoing from the vertex.
     *
     * @param v the vertex
     * @return the set of edges
     */
    Set<E> outgoingEdgesOf(V v);

    /**
     * Returns the number of outgoing edges from the specified vertex.
     *
     * @param v vertex whose degree is queried
     * @return the degree of the vertex
     */
    int outDegreeOf(V v);

    /**
     * Returns the source vertex of an edge.
     *
     * @param e edge
     * @return source vertex
     */
    V getEdgeSource(E e);

    /**
     * Returns the target vertex of an edge.
     *
     * @param e edge
     * @return target vertex
     */
    V getEdgeTarget(E e);

    /**
     * Returns the weight of an edge.
     *
     * @param e edge
     * @return edge weight
     */
    default double getEdgeWeight(E e) {
        return 1;
    }

    /**
     * Returns a set of the vertices contained in this graph.
     *
     * @return set of vertices
     */
    Set<V> vertexSet();
}
