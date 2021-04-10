package ru.academits.filimonov.tree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree<T extends Comparable<T>> {
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
            if (data.compareTo(currentNode.getData()) == 0) {
                System.out.println("The tree has this element (" + data + ") already");

                return;
            }

            if (data.compareTo(currentNode.getData()) > 0) {
                if (currentNode.getBiggerChild() == null) {
                    currentNode.setBiggerChild(new TreeNode<>(data, currentNode));

                    count++;

                    return;
                }

                currentNode = currentNode.getBiggerChild();

                continue;
            }

            if (currentNode.getLesserChild() == null) {
                currentNode.setLesserChild(new TreeNode<>(data, currentNode));

                count++;

                return;
            }

            currentNode = currentNode.getLesserChild();
        }
    }

    public WidthWalker getWidthWalker() {
        return new WidthWalker();
    }

    public DepthWalker getDepthWalker() {
        return new DepthWalker();
    }

    public TreeNode<T> findNode(T data) {
        if (count == 0) {
            System.out.println("The tree has no elements");

            return null;
        }

        TreeNode<T> currentNode = root;

        while (true) {
            if (data.compareTo(currentNode.getData()) == 0) {
                System.out.println("The tree contains the element " + data);

                return currentNode;
            }

            if (data.compareTo(currentNode.getData()) > 0) {
                if (currentNode.getBiggerChild() == null) {
                    System.out.println("The tree doesn't contain the element " + data);

                    return null;
                }

                currentNode = currentNode.getBiggerChild();

                continue;
            }

            if (currentNode.getLesserChild() == null) {
                System.out.println("The tree doesn't contain the element " + data);

                return null;
            }

            currentNode = currentNode.getLesserChild();
        }
    }

    public boolean remove(T data) {
        TreeNode<T> dataNode = findNode(data);

        if (dataNode == null) {
            return false;
        }

        if (dataNode.getBiggerChild() == null & dataNode.getLesserChild() == null) {
            if (isNodeLesserChild(dataNode)) {
                dataNode.getParent().setLesserChild(null);

                return true;
            }

            dataNode.getParent().setBiggerChild(null);

            count--;

            return true;
        }

        if (dataNode.getBiggerChild() == null) {
            if (isNodeLesserChild(dataNode)) {
                dataNode.getParent().setLesserChild(dataNode.getLesserChild());
            } else {
                dataNode.getParent().setBiggerChild(dataNode.getLesserChild());
            }

            count--;

            return true;
        }

        if (dataNode.getLesserChild() == null) {
            if (isNodeLesserChild(dataNode)) {
                dataNode.getParent().setLesserChild(dataNode.getBiggerChild());
            } else {
                dataNode.getParent().setBiggerChild(dataNode.getBiggerChild());
            }

            count--;

            return true;
        }

        TreeNode<T> currentNode = dataNode.getBiggerChild();

        while (currentNode.getLesserChild() != null) {
            currentNode = currentNode.getLesserChild();
        }

        if (currentNode.getBiggerChild() != null) {
            currentNode.getParent().setLesserChild(currentNode.getBiggerChild());
        }

        currentNode.setLesserChild(dataNode.getLesserChild());

        if (dataNode.getBiggerChild() != currentNode) {
            currentNode.setBiggerChild(dataNode.getBiggerChild());
        }

        currentNode.getParent().setLesserChild(null);

        if (isNodeLesserChild(dataNode)) {
            dataNode.getParent().setLesserChild(currentNode);
        } else {
            dataNode.getParent().setBiggerChild(currentNode);
        }

        count--;

        return true;
    }

    public class WidthWalker {
        TreeNode<T> currentNode;
        Queue<TreeNode<T>> queue = new LinkedList<>();

        private WidthWalker() {
            queue.add(root);
        }

        public void moveNext() {
            currentNode = queue.poll();

            putChildren();
        }

        public T getData() {
            return currentNode.getData();
        }

        private void putChildren() {
            if (currentNode.getLesserChild() != null) {
                queue.add(currentNode.getLesserChild());
            }

            if (currentNode.getBiggerChild() != null) {
                queue.add(currentNode.getBiggerChild());
            }
        }
    }

    public class DepthWalker {
        TreeNode<T> currentNode;
        Stack<TreeNode<T>> stack = new Stack<>();

        private DepthWalker() {
            stack.push(root);
        }

        public void moveNext() {
            currentNode = stack.pop();

            pushChildren();
        }

        public T getData() {
            return currentNode.getData();
        }

        private void pushChildren() {
            if (currentNode.getBiggerChild() != null) {
                stack.push(currentNode.getBiggerChild());
            }

            if (currentNode.getLesserChild() != null) {
                stack.push(currentNode.getLesserChild());
            }
        }
    }

    private boolean isNodeLesserChild(TreeNode<T> node) {
        return node.getData().compareTo(node.getParent().getData()) < 0;
    }

    public void walkTreeInDepthRecursively() {
        walkInDepthRecursively(root);
    }

    private void walkInDepthRecursively(TreeNode<T> startNode) {
        if (startNode == null) {
            return;
        }

        System.out.println(startNode.getData());

        walkInDepthRecursively(startNode.getLesserChild());
        walkInDepthRecursively(startNode.getBiggerChild());
    }
}