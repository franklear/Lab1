package org.yoooo.se1;

import java.security.AllPermission;
import java.util.*;

public class QueryBridgeWords {
    /**
     * Returns bridge words of sourceWord -> targetWord
     *
     * @param sourceWord source word
     * @param targetWord target word
     * @return List of bridge words
     */
    public static List<String> getBridgeWords(String sourceWord, String targetWord) {
        Graph<String, Integer> graph = Application.getInstance().getGraph();
        Set<String> vertexSet = graph.vertexSet();
        if (!vertexSet.contains(sourceWord) || !vertexSet.contains(targetWord))
            return null;
        List<String> bridgeWords = new ArrayList<>();
        Set<Integer> outgoingEdges1 = graph.outgoingEdgesOf(sourceWord);
        for (int currentEgde : outgoingEdges1) {
            String edgeTarget = graph.getEdgeTarget(currentEgde);
            if (graph.getEdge(edgeTarget, targetWord) != null)
                bridgeWords.add(edgeTarget);
        }
        return bridgeWords;
    }

    /**
     * Returns result string of bridge words query of word1 -> word2
     *
     * @param word1 source word
     * @param word2 target word
     * @return result String
     */
    public static String queryBridgeWords(String word1, String word2) {
        Graph<String, Integer> graph = Application.getInstance().getGraph();
        boolean exist1 = graph.vertexSet().contains(word1), exist2 = graph.vertexSet().contains(word2);
        if (!exist1 && !exist2)
            return String.format("No \"%s\" and \"%s\" in the graph!", word1, word2);
        if (!exist1 && exist2)
            return String.format("No \"%s\" in the graph!", word1);
        if (exist1 && !exist2)
            return String.format("No \"%s\" in the graph!", word2);
        List<String> bridgeWords = getBridgeWords(word1, word2);
        if (bridgeWords == null)
            return String.format("No \"%s\" or \"%s\" in the graph!", word1, word2);
        if (bridgeWords.isEmpty())
            return String.format("No bridge words from \"%s\" to \"%s\"!", word1, word2);
        if (bridgeWords.size() == 1)
            return String.format("The bridge word from \"%s\" to \"%s\" is: %s", word1, word2, bridgeWords.get(0));
        StringBuilder result = new StringBuilder(String.format("The bridge words from \"%s\" to \"%s\" are: ", word1, word2));
        ListIterator<String> i = bridgeWords.listIterator();
        while (i.nextIndex() < bridgeWords.size() - 1) {
            result.append(i.next());
            result.append(", ");
        }
        result.append("and ");
        result.append(i.next());
        result.append(".");
        return result.toString();
    }
}
