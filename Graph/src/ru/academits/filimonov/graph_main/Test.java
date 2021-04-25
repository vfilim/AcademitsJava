package ru.academits.filimonov.graph_main;

import ru.academits.filimonov.graph.Graph;

public class Test {
    public static void main(String[] args) {
        int[][] connections = {
                {0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 1},
                {0, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 1, 0, 1},
                {0, 0, 1, 0, 0, 1, 0},
        };

        Graph graph = new Graph(connections);

        System.out.println("Let's walk the graph in width:");
        graph.walkInWidth(System.out::println);

        System.out.println("Let's walk the graph in depth:");
        graph.walkInDepth(System.out::println);

        System.out.println("Let's walk the graph in depth recursively:");
        graph.walkInDepthRecursively(System.out::println);
    }
}