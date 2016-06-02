package be.inglease;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.inglease.Runner.Gender;

public class Solutions {
	
	/* 
	 * Count the number of female runners.
	 */
	public static int countFemaleRunners(List<Runner> runners) {
		return (int) runners.stream().filter(runner -> runner.getGender() == Gender.FEMALE)
							   		 .count();
	}
	
	/*
	 * Give the height of the biggest runner
	 */
	public static double biggestRunner(List<Runner> runners) {
		return runners.stream().mapToDouble(runner -> runner.getHeight())
							   .max()
							   .getAsDouble();
	}
	
	/*
	 * Find the youngest runner
	 */
	public static Runner oldestRunner(List<Runner> runners) {
		return runners.stream().sorted((runner1, runner2) -> runner2.getBirthDate().compareTo(runner1.getBirthDate()))
							   .findFirst()
							   .get();
	}
	
	/*
	 * Find the total distance of all the runners in km.
	 */
	public static double totalDistance(List<Runner> runners) {
		return runners.stream().flatMap(r -> r.getRuns().stream())
							   .mapToDouble(r -> r.getDistance() / 1000.0)
							   .sum();
	}
	
	/*
	 * Find the total distance of all MALE the runners in km.
	 */
	public static double totalDistanceMales(List<Runner> runners) {
		return runners.stream().filter(r -> r.getGender() == Gender.MALE)
							   .flatMap(r -> r.getRuns().stream())
							   .mapToDouble(r -> r.getDistance() / 1000.0)
							   .sum();
	}
	
	/*
	 * Calculate the calories Jesus burned on all his runs.
	 * (hint: RunCalculation has a default method.)
	 */
	public static int calculateCaloriesJesus(List<Runner> runners) {
		return runners.stream().filter(r -> r.getName().equals("Jesus"))
							   .flatMap(r -> r.getRuns().stream())
							   .mapToInt(r -> RunCalculation.calories(r, r.getRunner()))
							   .sum();
	}
	
	/*
	 * Change the speed of all the runs to m/s.
	 * Print all the runs before the change and after the change.
	 * Use method a method reference for the printing.
	 * (hint: Run has a toString() method already.)
	 * 
	 * This is a nice exercise to see in which order your stream get parsed.
	 */
	public static void setSpeedToMeterPerSecond(List<Runner> runners) {
		RunCalculation calc = r -> ((double) r.getDistance() / r.getDuration());
		runners.stream().flatMap(runner -> runner.getRuns().stream())
						.peek(System.out::println)
						.peek(run -> run.setSpeed(calc))
						.forEach(System.out::println);
	}
	
	/*
	 * Find the fastest female run that is longer than 5km
	 */
	public static double fastestFemaleRun(List<Runner> runners) {
		return runners.stream().filter(r -> r.getGender() == Gender.FEMALE)
							   .flatMap(r -> r.getRuns().stream())
							   .filter(r -> r.getDistance() > 5000)
							   .mapToDouble(r -> r.getSpeed())
							   .max()
							   .orElse(0.0);
	}

	/*
	 * Calculate the average speed of all Sam's runs that are less than 63 minutes.
	 */
	public static double avgSpeedSam(List<Runner> runners) {
		return runners.stream().filter(r -> r.getName().equals("Sam"))
							   .flatMap(r -> r.getRuns().stream())
							   .filter(r -> r.getDuration() < 3780)
							   .mapToDouble(r -> r.getSpeed())
							   .average()
							   .getAsDouble();
	}
	
	/*
	 * Give a String with the names of all Runners separated with a '#'
	 * sorted on their weight. (Print the runners to see you've sorted correctly.)
	 * (hint: Use the reduce function.)
	 */
	public static String namesOfRunners(List<Runner> runners) {
		return runners.stream().sorted((r1, r2) -> ((Double) r1.getWeight()).compareTo(r2.getWeight()))
							   .peek(System.out::println)
							   .map(r -> r.getName())
							   .reduce((n1, n2) -> n1 + "#" + n2)
							   .orElse("");
	}
	
	/*
	 * Return a list of runs that are longer than one hour
	 */
	public static List<Run> getRuns1hour(List<Runner> runners) {
		return runners.stream().flatMap(r -> r.getRuns().stream())
							   .filter(r -> r.getDuration() > 3600)
							   .collect(Collectors.toList());
	}
	
	/*
	 * Return average distance of all runs in km.
	 * Use a custom collector.
	 */
	public static double averageDistance(List<Runner> runners) {
		double[] av = runners.stream().flatMap(r -> r.getRuns().stream())
							   .mapToDouble(r -> r.getDistance() / 1000.0)
							   .collect(() -> new double[2],//
									   (a, d) -> {			// Accumulator
										   a[0] += d; // sum
										   a[1]++;	  // # elements
									   },
									   (a, b) -> {			// Combiner
										   a[0] += b[0];
										   a[1] += b[1];
									   });
		return av[0] / av[1];
	}
	
	/*
	 * Return a Map:
	 * Key = Birth date
	 * Entry = Name of the runner
	 */
	public static Map<LocalDateTime, String> getBirthDatesMap(List<Runner> runners) {
		return runners.stream().collect(Collectors.toMap(Runner::getBirthDate, Runner::getName));
	}

	/*
	 * Return a Map:
	 * Key = Birth year
	 * Entry = List of runners who were born in that year
	 */
	public static Map<Integer, List<Runner>> getRunnersMapWithBirthYearKey(List<Runner> runners) {
		return runners.stream().collect(Collectors.groupingBy(r -> r.getBirthDate().getYear()));
	}
}
