package org.functional.collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FilterMapReduceExample {
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
    @DisplayName("Get people over the age of 30")
    void getOver30s() {
        List<Person> expectedPeople = List.of(
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 72)
        );

        List<Person> over30s = createPeople()
                .stream()
                .filter(person -> person.getAge() > 30)
                .collect(toList());

        assertThat(over30s)
                .isNotEmpty()
                .hasSize(3)
                .containsAnyElementsOf(expectedPeople);
    }

    @Test
    @DisplayName("Get the names only")
    void getNames() {
        List<String> expectedNames = List.of(
                "Sara",
                "Sara",
                "Bob",
                "Paula",
                "Paul",
                "Jack",
                "Jack",
                "Jill"
        );

        List<String> namesOfOver30s = createPeople()
                .stream()
                .map(Person::getName)
                .collect(toList());

        assertThat(namesOfOver30s)
                .isNotEmpty()
                .hasSize(8)
                .containsAnyElementsOf(expectedNames);
    }

    @Test
    @DisplayName("Get the total age of everyone")
    void getTotalAge() {
        Integer totalAgeOfEveryone = createPeople()
                .stream()
                .map(Person::getAge)
                .reduce(0, Integer::sum);

        assertThat(totalAgeOfEveryone)
                .isNotZero()
                .isEqualTo(212);
    }
    
    @Test
    @DisplayName("Get all people's ages")
    void getAllAges() {
        List<Integer> listOfAges = createPeople().stream()
                .map(Person::getAge)
                .collect(toList());
        
        listOfAges.add(99); // valid as list returned is still mutable

        assertThat(listOfAges)
                .isNotEmpty()
                .contains(99);

        // Honour Immutability
        List<Integer> unmodifiableListOfAllAges = createPeople().stream()
                .map(Person::getAge)
                .collect(toUnmodifiableList());

        assertThatThrownBy(() -> unmodifiableListOfAllAges.add(99))
                .isExactlyInstanceOf(UnsupportedOperationException.class);
    }
}
