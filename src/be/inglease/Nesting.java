package be.inglease;

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
				       .orElse("Default foo");
	}
	
	/*
	 * Print out foo if it's not null
	 */
	public static void printFoo(Outer outer) {
		Optional.ofNullable(outer)
		        .map(Outer::getNested)
		        .map(Nested::getInner)
		        .map(Inner::getFoo)
		        .ifPresent(System.out::println);
	}
	
	/*
	 * Throw an exception if foo is null
	 */
	public static void throwFoo(Outer outer) throws Exception {
		Optional.ofNullable(outer)
		        .map(Outer::getNested)
		        .map(Nested::getInner)
		        .map(Inner::getFoo)
		        .orElseThrow(Exception::new);
	}
}

class Outer {
    Nested nested;
    Nested getNested() {
        return nested;
    }
}
class Nested {
    Inner inner;
    Inner getInner() {
        return inner;
    }
}
class Inner {
    String foo;
    String getFoo() {
        return foo;
    }
}
