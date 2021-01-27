package ru.academits.filimonov.lambdas;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ArrayList<Person> personsList = new ArrayList<>(Arrays.asList(
                new Person("Peter", 23),
                new Person("Alice", 26),
                new Person("John", 24),
                new Person("Elizabeth", 28),
                new Person("Mary", 30),
                new Person("Andrew", 32),
                new Person("John", 32),
                new Person("Mike", 15),
                new Person("Michele", 17),
                new Person("Peter", 47),
                new Person("Mary", 60),
                new Person("Alice", 12),
                new Person("Andrew", 13)
        ));

        String uniqueNamesString = personsList.stream()
                .map(Person::getName)
                .distinct()
                .collect(Collectors.joining(", ", "Names: ", "."));

        System.out.println(uniqueNamesString);

        OptionalDouble teensAverageAge = personsList.stream()
                .filter(x -> x.getAge() < 18)
                .mapToInt(Person::getAge)
                .average();

        if (teensAverageAge.isPresent()) {
            System.out.println("The average age of people 18 and less year old is " + teensAverageAge.getAsDouble());
        } else {
            System.out.println("There are no people younger than 18");
        }

        Map<String, Double> personsMap = personsList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingInt(Person::getAge)));

        System.out.println("Groups of people by name and their average age: ");

        for (Map.Entry<String, Double> n : personsMap.entrySet()) {
            System.out.println(n.getKey() + " " + n.getValue());
        }

        List<String> chosenAgePeopleNameList = personsList.stream()
                .filter(x -> x.getAge() >= 20 && x.getAge() <= 45)
                .sorted(Comparator.comparingInt(Person::getAge))
                .map(Person::getName)
                .collect(Collectors.toList());

        System.out.println("The list of people 20-45 year old in ascendant order:");

        for (var e : chosenAgePeopleNameList) {
            System.out.println(e);
        }
    }
}
