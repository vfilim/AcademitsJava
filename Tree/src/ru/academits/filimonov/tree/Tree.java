package ru.academits.filimonov.tree;

import java.util.*;
import java.util.function.Consumer;

public class Tree<T> {
    private TreeNode<T> root;
    private int count;
    private Comparator<T> comparator;

    public Tree() {
    }

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Tree(Collection<? extends T> collection) {
        for (T e : collection) {
            add(e);
        }
    }

    public Tree(Collection<? extends T> collection, Comparator<T> comparator) {
        this.comparator = comparator;

        for (T e : collection) {
            add(e);
        }
    }

    public int getCount() {
        return count;
    }

    public void add(T data) {
        if (count == 0) {
            root = new TreeNode<>(data);

            count++;

            return;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            if (compareData(data, currentNode.getData()) >= 0) {
                if (currentNode.getRight() == null) {
                    currentNode.setRight(new TreeNode<>(data));

                    count++;

                    return;
                }

                currentNode = currentNode.getRight();

                continue;
            }

            if (currentNode.getLeft() == null) {
                currentNode.setLeft(new TreeNode<>(data));

                count++;

                return;
            }

            currentNode = currentNode.getLeft();
        }
    }

    public boolean findNode(T data) {
        return findNodeWithParent(data) != null;
    }

    private NodeWithParent findNodeWithParent(T data) {
        if (count == 0) {
            return null;
        }

        TreeNode<T> parent = null;
        TreeNode<T> currentNode = root;

        while (true) {
            double comparingResult = compareData(data, currentNode.getData());

            if (comparingResult == 0) {
                return new NodeWithParent(currentNode, parent);
            }

            if (comparingResult > 0) {
                if (currentNode.getRight() == null) {
                    return null;
                }

                parent = currentNode;
                currentNode = currentNode.getRight();

                continue;
            }

            if (currentNode.getLeft() == null) {
                return null;
            }

            parent = currentNode;
            currentNode = currentNode.getLeft();
        }
    }

    public boolean remove(T data) {
        NodeWithParent dataNodeWithParent = findNodeWithParent(data);

        if (dataNodeWithParent == null) {
            return false;
        }

        TreeNode<T> dataNode = dataNodeWithParent.node;
        TreeNode<T> dataNodeParent = dataNodeWithParent.parent;

        if (dataNode.getRight() == null && dataNode.getLeft() == null) {
            if (dataNode == root) {
                root = null;

                count--;

                return true;
            }

            if (dataNodeWithParent.isNodeLeft()) {
                dataNodeParent.setLeft(null);

                return true;
            }

            dataNodeParent.setRight(null);

            count--;

            return true;
        }

        if (dataNode.getRight() == null) {
            if (dataNode == root) {
                root = dataNode.getLeft();
            } else if (dataNodeWithParent.isNodeLeft()) {
                dataNodeParent.setLeft(dataNode.getLeft());
            } else {
                dataNodeParent.setRight(dataNode.getLeft());
            }

            count--;

            return true;
        }

        if (dataNode.getLeft() == null) {
            if (dataNode == root) {
                root = dataNode.getRight();
            } else if (dataNodeWithParent.isNodeLeft()) {
                dataNodeParent.setLeft(dataNode.getRight());
            } else {
                dataNodeParent.setRight(dataNode.getRight());
            }

            count--;

            return true;
        }

        TreeNode<T> currentNode = dataNode.getRight();

        NodeWithParent currentNodeWithParent = new NodeWithParent(currentNode, dataNode);

        while (currentNode.getLeft() != null) {
            currentNodeWithParent.parent = currentNodeWithParent.node;
            currentNode = currentNode.getLeft();
            currentNodeWithParent.node = currentNode;
        }

        if (currentNodeWithParent.parent != dataNode) {
            currentNodeWithParent.parent.setLeft(currentNode.getRight());
        }

        if (dataNode.getRight() != currentNode) {
            currentNode.setRight(dataNode.getRight());
        }

        if (dataNode.getLeft() != null) {
            currentNode.setLeft(dataNode.getLeft());
        }

        if (dataNode == root) {
            root = currentNode;
        } else if (dataNodeWithParent.isNodeLeft()) {
            dataNodeParent.setLeft(currentNode);
        } else {
            dataNodeParent.setRight(currentNode);
        }

        count--;

        return true;
    }

    public void walkInWidth(Consumer<T> consumer) {
        if (count == 0) {
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> currentNode = queue.poll();
            consumer.accept(currentNode.getData());

            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
    }

    public void walkInDepth(Consumer<T> consumer) {
        if (count == 0) {
            return;
        }

        Deque<TreeNode<T>> stack = new LinkedList<>();

        stack.addLast(root);

        while (!stack.isEmpty()) {
            TreeNode<T> currentNode = stack.removeLast();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.addLast(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                stack.addLast(currentNode.getLeft());
            }
        }
    }

    public void walkTreeInDepthRecursively(Consumer<T> consumer) {
        walkInDepthRecursively(root, consumer);
    }

    private void walkInDepthRecursively(TreeNode<T> startNode, Consumer<T> consumer) {
        if (startNode == null) {
            return;
        }

        consumer.accept(startNode.getData());

        walkInDepthRecursively(startNode.getLeft(), consumer);
        walkInDepthRecursively(startNode.getRight(), consumer);
    }

    private double compareData(T data1, T data2) {
        if (comparator != null) {
            return Objects.compare(data1, data2, comparator);
        }

        if (data1 == null && data2 == null) {
            return 0;
        }

        if (data1 == null) {
            return -1;
        }

        if (data2 == null) {
            return 1;
        }

        //noinspection unchecked
        return ((Comparable<T>) data1).compareTo(data2);
    }

    private class NodeWithParent {
        private TreeNode<T> node;
        private TreeNode<T> parent;

        private NodeWithParent(TreeNode<T> node, TreeNode<T> parent) {
            this.node = node;
            this.parent = parent;
        }

        private boolean isNodeLeft() {
            return parent.getLeft() == node;
        }
    }
}