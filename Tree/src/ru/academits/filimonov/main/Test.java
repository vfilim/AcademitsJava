package ru.academits.filimonov.main;

import ru.academits.filimonov.tree.Tree;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(Arrays.asList(8, 3, 10, 1, 6, 14, 4, 7, 13));

        System.out.println("The tree has " + tree.getCount() + " elements");

        System.out.println("Let's walk around in depth:");

        Tree<Integer>.DepthWalker depthWalker = tree.getDepthWalker();

        for (int i = 0; i < tree.getCount(); i++) {
            depthWalker.moveNext();

            System.out.println(depthWalker.getData());
        }

        System.out.println("Let's walk around in width:");

        Tree<Integer>.WidthWalker widthWalker = tree.getWidthWalker();

        for (int i = 0; i < tree.getCount(); i++) {
            widthWalker.moveNext();

            System.out.println(widthWalker.getData());
        }

        tree.findNode(9);

        System.out.println("Let's remove element 3 and walk in depth:");

        tree.remove(3);

        Tree<Integer>.DepthWalker depthWalker2 = tree.getDepthWalker();

        for (int i = 0; i < tree.getCount(); i++) {
            depthWalker2.moveNext();

            System.out.println(depthWalker2.getData());
        }

        System.out.println("Let's walk in depth recursively:");

        tree.walkTreeInDepthRecursively();
    }
}