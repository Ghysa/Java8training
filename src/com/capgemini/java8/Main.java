
package com.capgemini.java8;

import java.io.IOException;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws IOException {
		List<Runner> runners = RunData.getRunnersList();

		System.out.println(Exercises.namesOfRunners(runners));
	}
}
