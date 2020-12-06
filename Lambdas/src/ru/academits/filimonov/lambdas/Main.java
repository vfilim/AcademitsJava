package ru.academits.filimonov.lambdas;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        int[] array = new int[]{1, 2, 3, 4};

        List<String> array2 = Arrays.asList("a", "b");

        array2.forEach(System.out::println);
    }
}
