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
