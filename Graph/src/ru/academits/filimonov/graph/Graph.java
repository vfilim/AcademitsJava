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

        connections = new boolean[nodesCount + 1][nodesCount + 1];
        visitedNodes = new boolean[nodesCount + 1];
    }

    public Graph(boolean[][] connections) {
        if (connections.length < 3) {
            throw new IllegalArgumentException("The connection matrix must have at least 3 rows");
        }

        for (boolean[] row : connections) {
            if (row.length != connections.length) {
                throw new IllegalArgumentException("The connections matrix must be square");
            }
        }

        for (int i = 1; i < connections.length; i++) {
            for (int j = i; j < connections.length; j++) {
                if (connections[i][j] != connections[j][i]) {
                    throw new IllegalArgumentException("The connections matrix must be symmetrical " + i + " and " + j);
                }
            }
        }

        this.connections = connections;

        nodesCount = connections.length - 1;
        visitedNodes = new boolean[nodesCount + 1];
    }

    public boolean areNodesConnected(int firstNodeNumber, int secondNodeNumber) {
        return connections[firstNodeNumber][secondNodeNumber];
    }

    public void setConnection(int firstNodeNumber, int secondNodeNumber, boolean areConnected) {
        if (firstNodeNumber < 1 || firstNodeNumber > nodesCount) {
            throw new IndexOutOfBoundsException("Node number must be between 1 and nodes count " + nodesCount);
        }

        if (secondNodeNumber < 1 || secondNodeNumber > nodesCount) {
            throw new IndexOutOfBoundsException("Node number must be between 1 and nodes count " + nodesCount);
        }

        connections[firstNodeNumber][secondNodeNumber] = areConnected;
        connections[secondNodeNumber][firstNodeNumber] = areConnected;
    }

    public boolean isNodeVisited(int nodeNumber) {
        if (nodeNumber < 1 || nodeNumber > nodesCount) {
            throw new IndexOutOfBoundsException("Node number must be between 1 and nodes count " + nodesCount);
        }

        return visitedNodes[nodeNumber];
    }

    private void setNodeVisited(int nodeNumber) {
        if (nodeNumber < 1 || nodeNumber > nodesCount) {
            throw new IndexOutOfBoundsException("Node number must be between 1 and nodes count " + nodesCount);
        }

        visitedNodes[nodeNumber] = true;
    }

    public void walkInWidth() {
        Queue<Integer> queue = new LinkedList<>();

        for (int nodeNumber = 1; nodeNumber <= nodesCount; nodeNumber++) {
            queue.add(nodeNumber);

            while (!queue.isEmpty()){
                int polledNumber = queue.poll();

                if (visitedNodes[polledNumber]) {
                    continue;
                }

                System.out.println(polledNumber);

                visitedNodes[polledNumber] = true;

                for (int j = 1; j <= nodesCount; j++){
                    if (j == polledNumber){
                        continue;
                    }

                    if (connections[polledNumber][j]){
                        queue.add(j);
                    }
                }
            }
        }
    }

    public void walkInDepth() {
        Stack<Integer> stack = new Stack<>();

        for (int nodeNumber = 1; nodeNumber <= nodesCount; nodeNumber++) {
            if (visitedNodes[nodeNumber]) {
                continue;
            }

            stack.push(nodeNumber);

            while (!stack.isEmpty()){
                int poppedNumber = stack.pop();

                if (visitedNodes[poppedNumber]) {
                    continue;
                }

                System.out.println(poppedNumber);

                visitedNodes[poppedNumber] = true;

                for (int j = 1; j <= nodesCount; j++){
                    if (j == poppedNumber){
                        continue;
                    }

                    if (connections[poppedNumber][j]){
                        stack.push(j);
                    }
                }
            }
        }
    }

    public void eraseVisits(){
        for (int i = 1; i <= nodesCount; i++){
            visitedNodes[i] = false;
        }
    }
}