package org.functional.collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectorExample {
    public static List<Person> createPeople() {
        return List.of(
                new Person("Sara", 20),
                new Person("Nancy", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Bill", 3),
                new Person("Jack", 72),
                new Person("Jill", 11)
        );
    }

    @Test
    @DisplayName("Get the list of names, in uppercase, of those who are older than 30")
    void getNamesOfOver30sUpperCased() {
        List<String> namesUpperCased = List.of("PAULA", "PAUL", "JACK");

        List<String> over30sNamesUpperCased = createPeople()
                .stream()
                .filter(person -> person.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(toList());

        assertThat(over30sNamesUpperCased)
                .isNotEmpty()
                .containsExactlyElementsOf(namesUpperCased);
    }

    @Test
    @DisplayName("Get names as key and age as value")
    void getUpperCasedNamesOfOver30s() {
//        Bob=20, Bill=3, Nancy=22, Sara=20, Paula=32, Jack=72, Jill=11, Paul=32
        Map<String, Integer> expectedOutput = Map.of("Bob", 20, "Bill", 3, "Nancy", 22, "Sara", 20, "Paula", 32, "Jack", 72, "Jill", 11, "Paul", 32);

        Map<String, Integer> nameAndAge = createPeople().stream()
                .collect(toMap(Person::getName, Person::getAge));

        assertThat(nameAndAge).
                isNotEmpty()
                .containsExactlyInAnyOrderEntriesOf(expectedOutput);
    }

    @Test
    @DisplayName("Map people over the age of 21 by age")
    void getPeopleByAge() {
        List<Person> expectedFalsePartition = List.of(
                new Person("Sara", 20),
                new Person("Bob", 20),
                new Person("Bill", 3),
                new Person("Jill", 11)
        );

        List<Person> expectedTruePartition = List.of(
                new Person("Nancy", 22),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 72)
        );

        Map<Boolean, List<Person>> peopleByAge = createPeople().stream()
                .collect(partitioningBy(person -> person.getAge() > 21));

        // Creates a map of two partitions: TRUE and FALSE based our partition predicate `person.getAge() > 21`

        // false=[Person{name='Sara', age=20}, Person{name='Bob', age=20}, Person{name='Bill', age=3}, Person{name='Jill', age=11}]
        assertThat(peopleByAge)
                .isNotEmpty()
                .extractingByKey(false)
                .isEqualTo(expectedFalsePartition);

        // true=[Person{name='Nancy', age=22}, Person{name='Paula', age=32}, Person{name='Paul', age=32}, Person{name='Jack', age=72}]
        assertThat(peopleByAge)
                .isNotEmpty()
                .extractingByKey(true)
                .isEqualTo(expectedTruePartition);
    }

    @Test
    @DisplayName("Group people by age")
    void groupPeopleByAge() {
        Map<Integer, List<Person>> expectedOutcome = Map.of(
                20, List.of(new Person("Sara", 20), new Person("Bob", 20)),
                22, List.of(new Person("Nancy", 22)),
                32, List.of(new Person("Paula", 32), new Person("Paul", 32)),
                3, List.of(new Person("Bill", 3)),
                72, List.of(new Person("Jack", 72)),
                11, List.of(new Person("Jill", 11))
        );

        Map<Integer, List<Person>> peopleGroupedByAge = createPeople().stream()
                .collect(groupingBy(Person::getAge));

        assertThat(peopleGroupedByAge)
                .isNotEmpty()
                .hasSize(6)
                .isEqualTo(expectedOutcome);
    }
}

// TODO: 39:53 - https://www.youtube.com/watch?v=pGroX3gmeP8
