# Coding Standards:

+ **<span id = "top">[Name convention](#name_convention)</span>**

+ **[Controller Structures](#control_structure)**

+ **[Formatting](#formatting)**

+ **[Documentation](#documentation)**

  

## <span id="name_convention">Class Names</span>

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

## GUI controllers

JavaFX controllers used in views should be concatenation of two parts: abbreviation of controller type and nouns followed by above convention concatenated with underscore.

```java
ListView: lsw_ownedCountries
Button: btn_nextStep
Label: lbl_playerInformation
```

**[Return to top](#top)**

##<span id = "control_structure">Controller Structures</span>

Control structures include if, for, while, switch, etc. Here is a sample if statement, since it is the most complicated of them:JavaFX controllers used in views should be concatenation of two parts: abbreviation of controller type and nouns followed by above convention concatenated with underscore.

```java
if (condition1 || condition2) {
  action1;
}
elseif (condition3 && condition4) {
  action2;
}
else {
  defaultaction;
}
```

Control statements should have one space between the control keyword and opening parenthesis, to distinguish them from function calls.

Always use curly braces even in situations where they are technically optional. Having them increases readability and decreases the likelihood of logic errors being introduced when new lines are added. The opening curly should be on the same line as the opening statement, preceded by one space. The closing curly should be on a line by itself and indented to the same level as the opening statement.

For switch statements:.

```java
switch (condition) {
  case 1:
    action1;
    break;

  case 2:
    action2;
    break;

  default:
    defaultaction;
}
```

For do-while statements:

```java
do {
  actions;
} while ($condition);
  
```

**[Return to top](#top)**

## <span id = "formatting">Formatting</span>

#### Use line breaks wisely

There are generally two reasons to insert a line break:

1. Your statement exceeds the column limit.
2. You want to logically separate a thought.
   Writing code is like telling a story. Written language constructs like chapters, paragraphs, and punctuation (e.g. semicolons, commas, periods, hyphens) convey thought hierarchy and separation. We have similar constructs in programming languages; you should use them to your advantage to effectively tell the story to those reading the code.

#### Indent style

Indent size is 4 spaces.

```java
// Like this.
if (x < 0) {
    negative(x);
} else {
    nonnegative(x);
}

// Not like this.
if (x < 0)
  negative(x);

// Also not like this.
if (x < 0) negative(x);
```

Don't break up a statement unnecessarily.

```java
// Bad.
final String value =
    otherValue;

// Good.
final String value = otherValue;
```

##### Chained method calls

```java
// Bad.
//   - Line breaks are based on line length, not logic.
Iterable<Module> modules = ImmutableList.<Module>builder().add(new LifecycleModule())
    .add(new AppLauncherModule()).addAll(application.getModules()).build();

// Better.
//   - Calls are logically separated.
//   - However, the trailing period logically splits a statement across two lines.
Iterable<Module> modules = ImmutableList.<Module>builder().
    add(new LifecycleModule()).
    add(new AppLauncherModule()).
    addAll(application.getModules()).
    build();

// Good.
//   - Method calls are isolated to a line.
//   - The proper location for a new method call is unambiguous.
Iterable<Module> modules = ImmutableList.<Module>builder()
    .add(new LifecycleModule())
    .add(new AppLauncherModule())
    .addAll(application.getModules())
    .build();
```

**[Return to top](#top)**

## <span id = "documentation">Documentation</span>

Documentation for a class may range from a single sentence to paragraphs with code examples. Documentation should serve to disambiguate any conceptual blanks in the API, and make it easier to quickly and *correctly* use your API. A thorough class doc usually has a one sentence summary and, if necessary, a more detailed explanation.

```java
/**
 * A volatile storage for objects based on a key, which may be invalidated and discarded.
 */
class Cache {
  ...
}

/**
 * Splits a string on whitespace.
 *
 * @param s The string to split.  An {@code null} string is treated as an empty string.
 * @return A list of the whitespace-delimited parts of the input.
 */
List<String> split(String s);
```

**[Return to top](#top)**

