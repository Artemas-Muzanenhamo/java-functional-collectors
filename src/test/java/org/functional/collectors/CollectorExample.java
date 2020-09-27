package org.functional.collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
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
}

// TODO: 30:41 - https://www.youtube.com/watch?v=pGroX3gmeP8
