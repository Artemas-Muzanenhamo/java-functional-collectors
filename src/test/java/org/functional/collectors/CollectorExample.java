package org.functional.collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectorExample {
    public static List<Person> createPeople() {
        return List.of(
                new Person("Sara", 20),
                new Person("Sara", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 3),
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
}

// TODO: 30:41 - https://www.youtube.com/watch?v=pGroX3gmeP8
