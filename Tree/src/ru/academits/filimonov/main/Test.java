package ru.academits.filimonov.main;

import ru.academits.filimonov.tree.Tree;

import java.util.Arrays;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args) {
        Tree<Integer> tree = new Tree<>(Arrays.asList(8, 3, 10, 1, 6, 14, 4, 7, 13));

        System.out.println("The tree has " + tree.getCount() + " elements");

        System.out.println("Let's walk around in depth:");
        tree.walkInDepth(System.out::println);

        System.out.println("Let's walk around in width:");
        tree.walkInWidth(System.out::println);

        System.out.println("Does tree contain the element 7? " + tree.findNode(7));

        System.out.println("Let's remove element 3 and walk in depth:");
        tree.remove(3);
        tree.walkInDepth(System.out::println);

        System.out.println("Let's walk in depth recursively:");
        tree.walkTreeInDepthRecursively(System.out::println);

        Tree<Integer> tree2 = new Tree<>(Arrays.asList(18, 10, 4, 14, 12, 16, 13));

        System.out.println("Let's walk new tree in depth recursively:");
        tree2.walkTreeInDepthRecursively(System.out::println);

        System.out.println("Let's remove 10 and walk again:");
        tree2.remove(10);
        tree2.walkInDepth(System.out::println);

        System.out.println("Let's remove 8 of first tree and walk again:");
        tree.remove(8);
        tree.walkInDepth(System.out::println);
    }
}