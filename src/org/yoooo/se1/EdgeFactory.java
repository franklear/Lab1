package org.yoooo.se1;

/**
 * An edge factory for creating edges used by graphs.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public interface EdgeFactory<V, E> {
    /**
     * Creates a new edge from source to target
     *
     * @param source the source vertex
     * @param target the target vertex
     * @return created edge
     */
    E createEdge(V source, V target);
}
