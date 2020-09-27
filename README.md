# Java Functional Collectors

## Common Operators

Given some people:

```java
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
```

### filter
Allows us to pick some values and not pick
other values within a stream pipeline.
It takes a `Predicate` which returns a boolean 
of either `true` or `false` value.

* Get people over the age of 30 only:

```java
List<Person> over30s = createPeople()
        .stream()
        .filter(person -> person.getAge() > 30)
        .collect(toList());
```

### map
This will transform your output in your stream pipeline to be whatever
type you map your element to in the `.map()` operation.

* Get all people's first names:

```java
List<String> namesOfOver30s = createPeople()
        .stream()
        .map(Person::getName)
        .collect(toList());
```

### reduce
Reduce take the collection and reduces it to a single value.
Reduce converts a Stream to something more concrete.
Java has reduce in two forms: 
- `.reduce()`
- `.collect()`

* Get the total age of every person:

```java
Integer totalAgeOfEveryone = createPeople()
        .stream()
        .map(Person::getAge)
        .reduce(0, Integer::sum);
``` 

## Functional Programming

Object-Oriented Programming: Polymorphism
Functional Programming: Functional Composition + Lazy Evaluation
- Lazy evaluation requires purity of functions(a function without side-effects).
Pure function
- Return the same result any number of times we call it with the same input(idempotency).
- Pure functions do not have side effects.

1. Pure functions do not change anything.
2. Pure functions do not depend on anything that may change. 

## Collectors

* Get the list of names, in uppercase, of those who are older than 30

#### The wrong way: 
```java
List<String> over30sNamesUpperCased = createPeople()
        .stream()
        .filter(person -> person.getAge() > 30)
        .map(Person::getName)
        .map(String::toUpperCase)
        .reduce(
                new ArrayList<>(),
                (names, name) -> {
                    names.add(name);
                    return names;
                },
                (names1, names2) -> {
                    names1.addAll(names2);
                    return names1;
                }
        );
``` 

#### The right way:
```java
List<String> over30sNamesUpperCased = createPeople()
        .stream()
        .filter(person -> person.getAge() > 30)
        .map(Person::getName)
        .map(String::toUpperCase)
        .collect(toList());
```

- It is our responsibility to keep the functions pure otherwise we will not be able to
achieve lazy-evaluation.
- Collectors are a group of utility functions written to make our life solely easy.
- The `.collect()` is a `reduce` operation.
- `Collectors` is a utility class.

### toMap()

* Get names as key and age as value

```java
Map<String, Integer> expectedOutput = Map.of("Bob", 20, "Bill", 3, "Nancy", 22, "Sara", 20, "Paula", 32, "Jack", 72, "Jill", 11, "Paul", 32);

Map<String, Integer> nameAndAge = createPeople().stream()
        .collect(toMap(Person::getName, Person::getAge));

assertThat(nameAndAge).
        isNotEmpty()
        .containsExactlyInAnyOrderEntriesOf(expectedOutput);
```
