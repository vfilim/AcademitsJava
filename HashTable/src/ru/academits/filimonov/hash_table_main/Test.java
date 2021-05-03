package ru.academits.filimonov.hash_table_main;

import ru.academits.filimonov.hash_table.HashTable;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        HashTable<Integer> hashTable = new HashTable<>(10);

        for (int i = 0; i < 20; i++) {
            hashTable.add(i);
        }

        System.out.println(hashTable);

        System.out.println("Let's remove elements 17, 19. The result table is:");
        hashTable.removeAll(Arrays.asList(17, 19));

        System.out.println(hashTable);

        System.out.println("Let's add them back:");
        hashTable.addAll(Arrays.asList(17, 19));

        System.out.println(hashTable);

        System.out.println("Let's retain elements 3, 5, 6. The result table is:");
        hashTable.retainAll(Arrays.asList(3, 5, 6));

        System.out.println(hashTable);

        System.out.println("Let's make an array. The 0 index element is:");

        System.out.println(hashTable.toArray()[0]);
    }
}