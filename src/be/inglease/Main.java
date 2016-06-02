
package be.inglease;

import java.io.IOException;
import java.util.List;

public class Main {
	
	public static void main(String[] args) throws IOException {
		List<Runner> runners = RunData.getRunnersList();

		System.out.println(Solutions.averageDistance(runners));
	}
}
