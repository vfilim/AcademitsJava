package ru.academits.filimonov.tree;

public class TreeNode<T> {
    private final T data;
    private TreeNode<T> left;
    private TreeNode<T> right;

    public TreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> child) {
        left = child;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> child) {
        right = child;
    }
}