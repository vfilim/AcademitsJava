package ru.academits.filimonov.graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private int nodesCount;

    private boolean[][] connections;
    private boolean[] visitedNodes;

    public Graph(int nodesCount) {
        if (nodesCount < 2) {
            throw new IllegalArgumentException("The graph must have at least 2 nodes");
        }

        this.nodesCount = nodesCount;

        connections = new boolean[nodesCount][nodesCount];
        visitedNodes = new boolean[nodesCount];
    }

    public Graph(boolean[][] connections) {
        if (connections.length < 2) {
            throw new IllegalArgumentException("The connection matrix must have at least 2 rows");
        }

        for (boolean[] row : connections) {
            if (row.length != connections.length) {
                throw new IllegalArgumentException("The connections matrix must be square");
            }
        }

        for (int i = 0; i < connections.length; i++) {
            for (int j = i; j < connections.length; j++) {
                if (connections[i][j] != connections[j][i]) {
                    throw new IllegalArgumentException("The connections matrix must be symmetrical. The problem indexes are " + i + " and " + j);
                }
            }
        }

        this.connections = connections;

        nodesCount = connections.length;
        visitedNodes = new boolean[nodesCount];
    }

    public boolean areNodesConnected(int firstNodeIndex, int secondNodeIndex) {
        if (firstNodeIndex < 0 || firstNodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + firstNodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        if (secondNodeIndex < 0 || secondNodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + secondNodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        return connections[firstNodeIndex][secondNodeIndex];
    }

    public void setConnection(int firstNodeIndex, int secondNodeIndex, boolean areConnected) {
        if (firstNodeIndex < 0 || firstNodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + firstNodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        if (secondNodeIndex < 0 || secondNodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + secondNodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        connections[firstNodeIndex][secondNodeIndex] = areConnected;
        connections[secondNodeIndex][firstNodeIndex] = areConnected;
    }

    public boolean isNodeVisited(int nodeIndex) {
        if (nodeIndex < 0 || nodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + nodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        return visitedNodes[nodeIndex];
    }

    private void setNodeVisited(int nodeIndex) {
        if (nodeIndex < 0 || nodeIndex > nodesCount - 1) {
            throw new IndexOutOfBoundsException("The node index " + nodeIndex + " can't be less than 0 and bigger than " + (nodesCount - 1));
        }

        visitedNodes[nodeIndex] = true;
    }

    public void walkInWidth() {
        Queue<Integer> queue = new LinkedList<>();

        for (int nodeIndex = 0; nodeIndex < nodesCount; nodeIndex++) {
            queue.add(nodeIndex);

            while (!queue.isEmpty()) {
                int polledIndex = queue.poll();

                if (visitedNodes[polledIndex]) {
                    continue;
                }

                System.out.println(polledIndex);

                visitedNodes[polledIndex] = true;

                for (int j = 0; j < nodesCount; j++) {
                    if (j == polledIndex) {
                        continue;
                    }

                    if (connections[polledIndex][j]) {
                        queue.add(j);
                    }
                }
            }
        }
    }

    public void walkInDepth() {
        Stack<Integer> stack = new Stack<>();

        for (int nodeIndex = 0; nodeIndex < nodesCount; nodeIndex++) {
            if (visitedNodes[nodeIndex]) {
                continue;
            }

            stack.push(nodeIndex);

            while (!stack.isEmpty()) {
                int poppedIndex = stack.pop();

                if (visitedNodes[poppedIndex]) {
                    continue;
                }

                System.out.println(poppedIndex);

                visitedNodes[poppedIndex] = true;

                for (int j = 0; j < nodesCount; j++) {
                    if (j == poppedIndex) {
                        continue;
                    }

                    if (connections[poppedIndex][j]) {
                        stack.push(j);
                    }
                }
            }
        }
    }

    public void eraseVisits() {
        for (int i = 0; i < nodesCount; i++) {
            visitedNodes[i] = false;
        }
    }
}