package be.inglease.java7;

import java.util.Locale;
import java.util.Random;

public class MultiCatch {

	public static void multiCatchJava7() throws FirstException, SecondException {
		try {
			if (new Random().nextDouble() > 0.5) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (FirstException | SecondException e) {
			throw e;
		}
	}
	
	public static void multiCatchJava7Bis() throws FirstException, SecondException {
		try {
			if (new Random().nextDouble() > 0.5) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void multiCatchJava6() throws Exception {
		try {
			if (new Random().nextDouble() > 0.5) {
				throw new FirstException();
			} else {
				throw new SecondException();
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
