package ru.academits.filimonov.tree;

public class TreeNode<T extends Comparable<T>> {
    private final T data;
    private TreeNode<T> parent;
    private TreeNode<T> lesserChild;
    private TreeNode<T> biggerChild;

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public T getData() {
        return data;
    }

    public TreeNode<T> getLesserChild() {
        return lesserChild;
    }

    public TreeNode<T> getBiggerChild() {
        return biggerChild;
    }

    public void setLesserChild(TreeNode<T> child) {
        lesserChild = child;

        if (child != null) {
            child.parent = this;
        }
    }

    public void setBiggerChild(TreeNode<T> child) {
        biggerChild = child;

        if (child != null) {
            child.parent = this;
        }
    }

    public TreeNode<T> getParent() {
        return parent;
    }
}