package com.capgemini.java8;

import java.util.Optional;

public class Nesting {

    /*
     * Return the string foo of the inner class.
     * If it's null, return a default value.
     */
    public static String getFoo(Outer outer) {
        return Optional.ofNullable(outer)
                .map(Outer::getNested)
                .map(Nested::getInner)
                .map(Inner::getFoo)
                .orElse("");
    }

    /*
     * Print out foo if it's not null
     */
    public static void printFoo(Outer outer) {
        Optional.ofNullable(outer).ifPresent(System.out::println);
    }

    /*
     * Throw an exception if foo is null
     */
    public static void throwFoo(Outer outer) throws Exception {
        System.out.println(Optional.ofNullable(outer)
                .map(Outer::getNested)
                .map(Nested::getInner)
                .map(Inner::getFoo)
                .orElseThrow(() -> new NullPointerException("Foo is null")));
    }
}

class Outer {
    Nested nested = new Nested();

    Nested getNested() {
        return nested;
    }
}

class Nested {
    Inner inner = new Inner();

    Inner getInner() {
        return inner;
    }
}

class Inner {
    String foo = "foo";

    String getFoo() {
        return foo;
    }
}
