package ru.academits.filimonov.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.IntConsumer;

public class Graph {
    private final int[][] connections;

    public Graph(int[][] connections) {
        if (connections.length < 2) {
            throw new IllegalArgumentException("The connection matrix must have at least 2 rows");
        }

        for (int[] row : connections) {
            if (row.length != connections.length) {
                throw new IllegalArgumentException("The connections matrix must be square");
            }
        }

        this.connections = connections;
    }

    public void walkInWidth(IntConsumer consumer) {
        boolean[] visitedNodes = new boolean[connections.length];

        Queue<Integer> queue = new LinkedList<>();

        for (int nodeIndex = 0; nodeIndex < connections.length; nodeIndex++) {
            if (visitedNodes[nodeIndex]) {
                continue;
            }

            queue.add(nodeIndex);

            while (!queue.isEmpty()) {
                int polledIndex = queue.poll();

                if (visitedNodes[polledIndex]) {
                    continue;
                }

                consumer.accept(polledIndex);

                visitedNodes[polledIndex] = true;

                for (int i = 0; i < connections.length; i++) {
                    if (i != polledIndex && connections[polledIndex][i] > 0) {
                        queue.add(i);
                    }
                }
            }
        }
    }

    public void walkInDepth(IntConsumer consumer) {
        boolean[] visitedNodes = new boolean[connections.length];

        Deque<Integer> stack = new LinkedList<>();

        for (int nodeIndex = 0; nodeIndex < connections.length; nodeIndex++) {
            if (visitedNodes[nodeIndex]) {
                continue;
            }

            stack.addLast(nodeIndex);

            while (!stack.isEmpty()) {
                int poppedIndex = stack.removeLast();

                if (visitedNodes[poppedIndex]) {
                    continue;
                }

                consumer.accept(poppedIndex);

                visitedNodes[poppedIndex] = true;

                for (int i = connections.length - 1; i >= 0; i--) {
                    if (i != poppedIndex && connections[poppedIndex][i] > 0) {
                        stack.addLast(i);
                    }
                }
            }
        }
    }

    public void walkInDepthRecursively(IntConsumer consumer) {
        boolean[] visitedNodes = new boolean[connections.length];

        walkInDepthRecursively(0, visitedNodes, consumer);
    }

    private void walkInDepthRecursively(int index, boolean[] visitedNodes, IntConsumer consumer) {
        visitedNodes[index] = true;
        consumer.accept(index);

        for (int i = 0; i < connections.length; i++) {
            if (!visitedNodes[i] && connections[index][i] > 0) {
                walkInDepthRecursively(i, visitedNodes, consumer);
            }
        }
    }
}