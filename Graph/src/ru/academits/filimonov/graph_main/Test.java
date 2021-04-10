package ru.academits.filimonov.graph_main;

import ru.academits.filimonov.graph.Graph;

public class Test {
    public static void main(String[] args) {
        boolean[][] connections = {
                {false, true, false, false, false, false, false},
                {true, false, false, true, true, true, false},
                {false, false, false, true, false, false, true},
                {false, true, true, false, false, false, false},
                {false, true, false, false, false, true, false},
                {false, true, false, false, true, false, true},
                {false, false, true, false, false, true, false},
        };

        Graph graph = new Graph(connections);

        System.out.println("Let's walk the graph in width:");
        graph.walkInWidth();

        graph.eraseVisits();

        System.out.println("Let's walk the graph in depth:");
        graph.walkInDepth();
    }
}