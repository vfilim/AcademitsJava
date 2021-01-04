package ru.academits.filimonov.lambdas;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> personsList = new ArrayList<>();

        personsList.add(new Person("Peter", 23));
        personsList.add(new Person("Alice", 26));
        personsList.add(new Person("John", 24));
        personsList.add(new Person("Elizabeth", 28));
        personsList.add(new Person("Mary", 30));
        personsList.add(new Person("Andrew", 32));
        personsList.add(new Person("John", 32));
        personsList.add(new Person("Mike", 15));
        personsList.add(new Person("Michele", 17));
        personsList.add(new Person("Peter", 47));
        personsList.add(new Person("Mary", 60));
        personsList.add(new Person("Alice", 12));
        personsList.add(new Person("Andrew", 13));

        var uniqueNamesString = personsList.stream()
                .map(x -> x.getName())
                .distinct()
                .collect(Collectors.joining(", "));

        System.out.println("Unique names are: " + uniqueNamesString + ".");

        var teensAverageAge = personsList.stream()
                .filter(x -> x.getAge() < 18)
                .mapToInt(x -> x.getAge())
                .average()
                .getAsDouble();

        System.out.println("The average age of people 18 and less year old is " + teensAverageAge);

        var personsMap = personsList.stream()
                .sorted((x, y) -> x.getName().compareToIgnoreCase(y.getName()))
                .collect(Collectors.groupingBy(x -> x.getName(), Collectors.averagingInt((Person::getAge))));

        System.out.println("Groups of people by name and their average age: ");

        for (var n : personsMap.entrySet()) {
            System.out.println(n.getKey() + " " + n.getValue());
        }

        var chosenAgePeopleNameList = personsList.stream()
                .filter(x -> x.getAge() >= 20 && x.getAge() <= 45)
                .sorted((x, y) -> x.getAge() - y.getAge())
                .map(x -> x.getName())
                .collect(Collectors.toList());

        System.out.println("The list of people 20-45 year old in ascendant order:");

        for (var e : chosenAgePeopleNameList) {
            System.out.println(e);
        }
    }
}
