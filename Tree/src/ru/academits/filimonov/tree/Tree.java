package ru.academits.filimonov.tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

public class Tree<T> {
    private TreeNode<T> root;
    private int count;

    public Tree(Collection<? extends T> collection) {
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
            if (data.hashCode() > currentNode.getData().hashCode()) {
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
        return findNode(data, x -> {
        }) != null;
    }

    private TreeNode<T> findNode(T data, Consumer<TreeNode<T>> consumer) {
        if (count == 0) {
            return null;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            consumer.accept(currentNode);

            int comparingResult = data.hashCode() - currentNode.getData().hashCode();

            if (comparingResult == 0) {
                return findEqualNodeAmongHashEquals(currentNode, data, consumer);
            }

            if (comparingResult > 0) {
                if (currentNode.getRight() == null) {
                    return null;
                }

                currentNode = currentNode.getRight();

                continue;
            }

            if (currentNode.getLeft() == null) {
                return null;
            }

            currentNode = currentNode.getLeft();
        }
    }

    private TreeNode<T> findEqualNodeAmongHashEquals(TreeNode<T> equalHashNode, T data, Consumer<TreeNode<T>> consumer) {
        TreeNode<T> currentNode = equalHashNode;

        while (currentNode.getData().hashCode() == data.hashCode()) {
            if (data.equals(currentNode.getData())) {
                return currentNode;
            }

            if (currentNode.getLeft() != null) {
                currentNode = currentNode.getLeft();
                consumer.accept(currentNode);
            } else {
                return null;
            }
        }

        return null;
    }

    public boolean remove(T data) {
        class NodeWithParent {
            TreeNode<T> node;
            TreeNode<T> parent;

            boolean isNodeLeft() {
                return parent.getData().hashCode() > node.getData().hashCode();
            }
        }

        NodeWithParent dataNodeWithParent = new NodeWithParent();

        TreeNode<T> dataNode = findNode(data, currentNode -> {
            dataNodeWithParent.parent = dataNodeWithParent.node;
            dataNodeWithParent.node = currentNode;
        });

        if (dataNode == null) {
            return false;
        }

        if (dataNode.getRight() == null && dataNode.getLeft() == null) {
            if (dataNodeWithParent.isNodeLeft()) {
                dataNode.setLeft(null);

                return true;
            }

            dataNode.setRight(null);

            count--;

            return true;
        }

        if (dataNode.getRight() == null) {
            if (dataNodeWithParent.isNodeLeft()) {
                dataNodeWithParent.parent.setLeft(dataNode.getLeft());
            } else {
                dataNodeWithParent.parent.setRight(dataNode.getLeft());
            }

            count--;

            return true;
        }

        if (dataNode.getLeft() == null) {
            if (dataNodeWithParent.isNodeLeft()) {
                dataNodeWithParent.parent.setLeft(dataNode.getRight());
            } else {
                dataNodeWithParent.parent.setRight(dataNode.getRight());
            }

            count--;

            return true;
        }

        TreeNode<T> currentNode = dataNode.getRight();

        NodeWithParent currentNodeWithParent = new NodeWithParent();
        currentNodeWithParent.node = currentNode;
        currentNodeWithParent.parent = dataNode;

        while (currentNode.getLeft() != null) {
            currentNodeWithParent.parent = currentNodeWithParent.node;
            currentNode = currentNode.getLeft();
            currentNodeWithParent.node = currentNode;
        }

        if (currentNode.getRight() != null) {
            currentNodeWithParent.parent.setLeft(currentNode.getRight());
        } else {
            currentNodeWithParent.parent.setLeft(null);
        }

        if (dataNode.getRight() != currentNode) {
            currentNode.setRight(dataNode.getRight());
        }

        if (dataNode.getLeft() != null) {
            currentNode.setLeft(dataNode.getLeft());
        }

        if (dataNodeWithParent.isNodeLeft()) {
            dataNodeWithParent.parent.setLeft(currentNode);
        } else {
            dataNodeWithParent.parent.setRight(currentNode);
        }

        count--;

        return true;
    }

    public void walkInWidth(Consumer<T> consumer) {
        if (count == 0) {
            return;
        }

        TreeNode<T> currentNode;
        Queue<TreeNode<T>> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            currentNode = queue.poll();
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

        TreeNode<T> currentNode;
        Stack<TreeNode<T>> stack = new Stack<>();

        stack.push(root);

        while (!stack.isEmpty()) {
            currentNode = stack.pop();
            consumer.accept(currentNode.getData());

            if (currentNode.getRight() != null) {
                stack.push(currentNode.getRight());
            }

            if (currentNode.getLeft() != null) {
                stack.push(currentNode.getLeft());
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
}