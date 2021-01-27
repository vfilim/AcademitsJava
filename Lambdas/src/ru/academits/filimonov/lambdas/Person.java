package ru.academits.filimonov.lambdas;

public class Person {
    private final String name;
    private final int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Person(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException("The age can't be < 0, now it is " + age);
        }

        this.name = name;

        this.age = age;
    }
}