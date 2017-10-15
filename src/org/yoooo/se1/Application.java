package org.yoooo.se1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.*;

public class Application {
    private static final String HELP = String.join(System.lineSeparator(),
            "Usage:",
            " program input.txt");
    private static final String COMMAND_HELP = String.join(System.lineSeparator(),
            "help, h  show this help",
            "show-graph, sg filename  show graph from input file and save to filename.png",
            "query-bridge, qb word1 word2  query bridge words from word1 to word2",
            "generate-text, gt  generate new text with next line of input",
            "shortest-path, sp source sink  calculate shortest path from source to sink",
            "shortest-path-all, spa source  calculate shortest path from source to all other " +
                    "vertices",
            "random-walk, rw filename  walk randomly and store result to filename.txt",
            "exit, e  exit program");
    private Graph<String, Integer> mGraph;
    private Random mRandom = new Random(System.currentTimeMillis());

    /**
     * Entry point for entire application. Should only be called from main.
     *
     * @param args command line arguments
     */
    public void run(String[] args) {
        if (args.length != 1) {
            System.out.println(HELP);
            return;
        }
        String input;
        try {
            input = readFile(args[0]);
        } catch (IOException e) {
            System.err.println("IO error while reading input file: " + args[0]);
            return;
        }
        input = convertInputFileContent(input);
        mGraph = stringToGraph(input);
        Scanner scanner = new Scanner(System.in);
        System.out.println(COMMAND_HELP);
        while (scanner.hasNext()) {
            String command = scanner.next();
            if (command.equals("e") || command.equals("exit")) break;
            switch (command) {
                case "h":
                case "help":
                    System.out.println(COMMAND_HELP);
                    break;
                case "sg":
                case "show-graph":
                    Main.showDirectedGraph(getGraph(), scanner.next());
                    break;
                case "qb":
                case "query-bridge":
                    System.out.println(Main.queryBridgeWords(scanner.next(), scanner.next()));
                    break;
                case "gt":
                case "generate-text":
                    String inputText = null;
                    do {
                        if (!scanner.hasNextLine()) break;
                        inputText = convertInputFileContent(scanner.nextLine());
                    } while (inputText.isEmpty());
                    if (inputText != null)
                        System.out.println(Main.generateNewText(inputText));
                    break;
                case "sp":
                case "shortest-path":
                    System.out.println(Main.calcShortestPath(scanner.next(), scanner.next()));
                    break;
                case "spa":
                case "shortest-path-all":
                    try {
                        String source = scanner.next();
                        SingleSourcePaths<String, Integer> paths =
                                CalcShortestPath.calcShortestPath(source);
                        Map<String, Integer> predecessorMap = paths.getPredecessorMap();
                        List<String> words = new ArrayList<>(predecessorMap.keySet());
                        Collections.sort(words);
                        for (String sink : words) {
                            List<String> path = new ArrayList<>();
                            String currentVertex = sink;
                            while (!currentVertex.equals(source)) {
                                path.add(currentVertex);
                                currentVertex = mGraph.getEdgeSource(predecessorMap.get(currentVertex));
                            }
                            System.out.print(source);
                            for (ListIterator<String> iterator = path.listIterator(path.size()); iterator.hasPrevious(); )
                                System.out.print("->" + iterator.previous());
                            System.out.println();
                        }
                        Set<String> vertices = new HashSet<>(predecessorMap.keySet());
                        vertices.add(source);
                        ShowDirectedGraph.showDirectedGraph(getGraph(),
                                UUID.randomUUID().toString(), vertices,
                                new HashSet<>(predecessorMap.values()));
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "rw":
                case "random-walk":
                    String path = scanner.next() + ".txt";
                    try {
                        writeFile(path, Main.randomWalk());
                    } catch (IOException e) {
                        System.err.println("IO error while writing file: " + path);
                    }
                    break;
            }
        }
    }

    public Graph<String, Integer> getGraph() {
        return mGraph;
    }

    public Random getRandom() {
        return mRandom;
    }

    private static Application sInstance = new Application();

    public static Application getInstance() {
        return sInstance;
    }

    private Application() {
    }

    private Graph<String, Integer> stringToGraph(String string) {
        Scanner scanner = new Scanner(string);
        SimpleDirectedWeightGraph<String, Integer> graph = new SimpleDirectedWeightGraph<>(
                new EdgeFactory<String, Integer>() {
                    private int mNext;

                    @Override
                    public Integer createEdge(String source, String target) {
                        return mNext++;
                    }
                });
        if (!scanner.hasNext()) {
            return graph;
        }
        String prev = scanner.next();
        graph.addVertex(prev);
        while (scanner.hasNext()) {
            String vertex = scanner.next();
            graph.addVertex(vertex);
            Integer edge = graph.getEdge(prev, vertex);
            if (edge != null) {
                graph.setEdgeWeight(edge, graph.getEdgeWeight(edge) + 1);
            } else {
                graph.addEdge(prev, vertex);
            }
            prev = vertex;
        }
        return graph;
    }

    private String readFile(String path) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8")) {
            char[] buffer = new char[0x10000];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, count);
            }
            return builder.toString();
        }
    }

    private void writeFile(String path, String content) throws IOException {
        try {
            byte[] bytes = content.getBytes("UTF-8");
            Files.write(FileSystems.getDefault().getPath(path), bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String convertInputFileContent(String input) {
        StringBuilder builder = new StringBuilder(input.replaceAll("[^a-zA-Z]+", " ").trim());
        for (int i = 0; i < builder.length(); ++i)
            if (Character.isUpperCase(builder.charAt(i)))
                builder.setCharAt(i, Character.toLowerCase(builder.charAt(i)));
        return builder.toString();
    }
}
