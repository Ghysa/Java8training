
package be.inglease;

import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		List<Runner> runners = RunData.getRunnersList();
		
		System.out.println(Solutions.averageDistance(runners)); // 11.621422222222225
	}
}
