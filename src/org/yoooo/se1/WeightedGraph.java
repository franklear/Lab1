package org.yoooo.se1;

/**
 * A graph whose edges have weights.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public interface WeightedGraph<V, E> extends Graph<V, E> {
    /**
     * Assigns a weight to an edge.
     *
     * @param e edge on which to set weight
     * @param weight weight for edge
     */
    void setEdgeWeight(E e, double weight);
}
