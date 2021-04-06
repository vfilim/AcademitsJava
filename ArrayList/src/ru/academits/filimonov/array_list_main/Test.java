package ru.academits.filimonov.array_list_main;

import ru.academits.filimonov.array_list.MyArrayList;

import java.util.Arrays;
import java.util.Collection;

public class Test {
    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>(Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven"));

        System.out.println("The list size is " + list.size());
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println("Does list contain \"five\"? " + list.contains("five"));
        System.out.println("Does list contain \"thirty\"? " + list.contains("thirty"));

        list.remove("five");

        System.out.println(list.toString());

        Collection<String> collection1 = Arrays.asList("three", "seven", "twenty");
        Collection<String> collection2 = Arrays.asList("three", "seven", "six");

        System.out.println("Does list contain all elements of first array? " + list.containsAll(collection1));
        System.out.println("Does list contain all elements of second array? " + list.containsAll(collection2));

        list.removeAll(collection2);

        System.out.println("Does list contain all elements of second array? " + list.containsAll(collection2));

        list.addAll(collection2);

        System.out.println("Does list contain all elements of second array? " + list.containsAll(collection2));

        list.retainAll(collection2);
        System.out.println("Does list contain element \"one\" " + list.contains("one"));

        System.out.println("Does list contain all elements of second array? " + list.containsAll(collection2));
    }
}