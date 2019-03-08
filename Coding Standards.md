## Naming Convention:



+ **Ref: [Java 8 Pocket Guide by Patricia Liguori, Robert Liguori](https://www.oreilly.com/library/view/java-8-pocket/9781491901083/ch01.html)**

  

## Class Names

Class names should be nouns, as they represent “things” or “objects.” They should be mixed case (camel case) with only the first letter of each word capitalized, as in the following:

```java
public class Fish {...}
```

## Interface Names

Interface names should be adjectives. They should end with “able” or “ible” whenever the interface provides a capability; otherwise, they should be nouns. Interface names follow the same capitalization convention as class names:

```java
public interface Serializable {...}
public interface SystemPanel {...}
```

## Method Names

Method names should contain a verb, as they are used to make an object take action. They should be mixed case, beginning with a lowercase letter, and the first letter of each subsequent word should be capitalized. Adjectives and nouns may be included in method names:

```java
public void locate() {...} // verb
public String getWayPoint() {...} // verb and noun
```

## Instance and Static Variable Names

Instance and static variable names should be nouns and should follow the same capitalization convention as method names:

```java
private String wayPoint;
```

## Parameter and Local Variable Names

Parameter and local variable names should be descriptive lowercase single words, acronyms, or abbreviations. If multiple words are necessary, they should follow the same capitalization convention as method names:

```java
public void printHotSpots(ArrayList spotList) {
  int counter = 0;
  for (String hotSpot : spotList) {
    System.out.println("Hot Spot #"
      + ++counter + ": " + hotSpot);
  }
}
```

Temporary variable names may be single letters such as `i`, `j`, `k`, `m`, and `n` for integers and `c`, `d`, and `e` for characters.

## Generic Type Parameter Names

Generic type parameter names should be uppercase single letters. The letter `T`for type is typically recommended.

The Collections Framework makes extensive use of generics. `E` is used for collection elements, `S` is used for service loaders, and `K` and `V` are used for map keys and values:

```java
public interface Map <K,V> {
   V put(K key, V value);
}
```

## Constant Names

Constant names should be all uppercase letters, and multiple words should be separated by underscores:

```java
public static final int MAX_DEPTH = 200;
```

## Enumeration Names

Enumeration names should follow the conventions of class names. The enumeration set of objects (choices) should be all uppercase letters:

```java
enum Battery {CRITICAL, LOW, CHARGED, FULL}
```

## Package Names

Package names should be unique and consist of lowercase letters. Underscores may be used if necessary:

```java
package com.oreilly.fish_finder;
```

Publicly available packages should be the reversed Internet domain name of the organization, beginning with a single-word top-level domain name (e.g., *com, net, org*, or *edu*), followed by the name of the organization and the project or division. (Internal packages are typically named according to the project.)

Package names that begin with `java` and `javax` are restricted and can be used only to provide conforming implementations to the Java class libraries.

## Annotation Names

Annotation names have been presented several ways in the Java SE API for predefined annotation types; [adjective|verb][noun]:

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionalInterface {}
```