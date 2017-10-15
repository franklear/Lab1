package org.yoooo.se1;

public class Main {
    public static void main(String[] args) {
        try {
            Application.getInstance().run(args);
        } catch (NoticeException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void showDirectedGraph(Graph<String, Integer> graph, String path) {
        ShowDirectedGraph.showDirectedGraph(graph, path);
    }

    public static String queryBridgeWords(String word1, String word2) {
        return QueryBridgeWords.queryBridgeWords(word1, word2);
    }

    public static String generateNewText(String inputText) {
        return GenerateNewText.generateNewText(inputText);
    }

    public static String calcShortestPath(String source, String sink) {
        return CalcShortestPath.calcShortestPath(source, sink);
    }

    public static String randomWalk() {
        return RandomWalk.randomWalk();
    }
}
