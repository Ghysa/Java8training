package com.capgemini.java8;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.summingDouble;

public class Solutions {
	/**
	 * Count the number of female runners.
	 */
	public static int countFemaleRunners(List<Runner> runners) {
//		return (int) runners.stream().map(Runner::getGender).filter(Gender.FEMALE::equals).count();
//		return runners.stream().collect(Collectors.partitioningBy(r -> r.getGender() == Gender.FEMALE)).get(true).size();
		return (int) runners.stream().filter(Runner.Gender.FEMALE::equals).count();
	}
	
	/**
	 * Give the height of the biggest runner
	 */
	public static double biggestRunner(List<Runner> runners) {
//		return runners.stream().sorted((a, b) -> Double.compare(a.getHeight(), b.getHeight())).findFirst();
//		return runners.stream()
//              .map(Runner::getHeight)
//				.sorted(Comparator.reverseOrder())
//				.findFirst()
//				.get();
//		return runners.stream()
//				.map(Runner::getHeight)
//				.max(Comparator.naturalOrder())
//				.get();
		return runners.stream().mapToDouble(Runner::getHeight).max().getAsDouble();
	}
	
	/**
	 * Find the youngest runner
	 */
	public static Runner youngestRunner(List<Runner> runners) {
		return runners.stream()
				.sorted((a, b) -> b.getBirthDate().compareTo(a.getBirthDate()))
				.findFirst()
				.get();
	}
	
	/**
	 * Find the total distance of all the runners in km.
	 */
	public static double totalDistance(List<Runner> runners) {
//		System.out.println(runners.stream().flatMap(r -> r.getRuns().stream()).mapToDouble(Run::getDistance).summaryStatistics());
//		System.out.println(runners.stream().flatMap(r -> r.getRuns().stream()).collect(Collectors.summarizingDouble(Run::getDistance)));

//		return runners.stream().flatMap(r -> r.getRuns().stream()).mapToDouble(Run::getDistance).sum() / 1000;
		return runners.stream().flatMap(r -> r.getRuns().stream()).collect(summingDouble(Run::getDistance)) / 1000;
	}
	
	/**
	 * Find the total distance of all {@link Runner.Gender#MALE} the runners in km.
	 */
	public static double totalDistanceMales(List<Runner> runners) {
//		return runners.stream()
//				.filter(r -> r.getGender() == Gender.MALE)
//				.flatMap(r -> r.getRuns().stream())
//				.collect(summingDouble(Run::getDistance)) / 1000;
		return runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.mapToDouble(Run::getDistance)
				.sum() / 1000;
	}
	
	/**
	 * Calculate the calories Jesus burned on all his runs.
	 * (hint: RunCalculation has a static method.)
	 */
	public static int calculateCaloriesJesus(List<Runner> runners) {
//		return runners.stream()
//				.filter(r -> "Jesus".equals(r.getName()))
//				.flatMapToInt(runner -> runner.getRuns().stream().mapToInt(run -> RunCalculation.calories(run, runner)))
//				.sum();
		return runners.stream()
				.filter(r -> "Jesus".equals(r.getName()))
				.flatMap(runner -> runner.getRuns().stream())
				.mapToInt(run -> RunCalculation.calories(run, run.getRunner()))
				.sum();

	}
	
	/**
	 * Change the speed of all the runs to m/s.
	 * Print all the runs before the change and after the change.
	 * Use a method reference for the printing.
	 * (hint: Run has a toString() method already.)
	 * 
	 * This is a nice exercise to see in which order your stream gets parsed.
	 */
	public static void setSpeedToMeterPerSecond(List<Runner> runners) {
		runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.peek(System.out::println)
				.peek(run -> run.setSpeed(run1 -> (double) run1.getDistance() / run1.getDuration()))
				.forEach(System.out::println);
	}
	
	/**
	 * Find the fastest female run that is longer than 5km
	 */
	public static double fastestFemaleRun(List<Runner> runners) {
		return runners.stream()
				.filter(Runner.Gender.FEMALE::equals)
				.flatMap(r -> r.getRuns().stream())
				.filter(run -> run.getDistance() > 5000)
				.mapToDouble(Run::getSpeed)
				.max()
				.getAsDouble();
	}

	/**
	 * Calculate the average speed of all Sam's runs that are less than 63 minutes.
	 */
	public static double avgSpeedSam(List<Runner> runners) {
		return runners.stream()
				.filter(r -> "Sam".equals(r.getName()))
				.flatMap(r -> r.getRuns().stream())
				.filter(run -> run.getDuration() < 63 * 60)
				.mapToDouble(Run::getSpeed)
				.average().getAsDouble();
	}
	
	/**
	 * @return a String with the names of all Runners separated with a '#'
	 * sorted on their weight. (Print the runners to see you've sorted correctly.)
	 * (hint: Use a collector)
	 */
	public static String namesOfRunners(List<Runner> runners) {
		return runners.stream()
				.sorted((a,b) -> Double.compare(a.getWeight(), a.getWeight()))
				.map(Runner::getName)
				.collect(Collectors.joining("#"));
//		return runners.stream()
//				.sorted((a,b) -> Double.compare(a.getWeight(), a.getWeight()))
//				.map(Runner::getName)
//				.reduce((r1, r2) -> r1 + "#" + r2)
//				.get();
	}

	/**
	 * @return a list of runs that are longer than one hour
	 */
	public static List<Run> getRuns1hour(List<Runner> runners) {
		return runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.filter(r -> r.getDuration() > 60)
				.collect(Collectors.toList());
	}

	/**
	 * @return a Map:<br>
	 * Key = Birth date<br>
	 * Entry = Name of the runner
	 */
	public static Map<LocalDateTime, String> getBirthDatesMap(List<Runner> runners) {
		return runners.stream().collect(Collectors.toMap(Runner::getBirthDate, Runner::getName));
	}

	/**
	 * Now we want to get the runners for each year of birth.
	 * @return a Map:<br>
	 * Key = Birth year<br>
	 * Entry = List of runners who were born in that year
	 */
	public static Map<Integer, List<Runner>> getRunnersMapWithBirthYearKey(List<Runner> runners) {
		return runners.stream().collect(Collectors.groupingBy(r -> r.getBirthDate().getYear()));
	}

	/**
	 * Now we want a map where we can get each runner by his/her name.
	 * The name is unique for each runner
	 * @return a Map:<br>
	 * Key = Name<br>
	 * Entry = Runner with this name
	 */
	public static Map<String, Runner> getRunnersMapByName(List<Runner> runners) {
		return runners.stream().collect(Collectors.toMap(Runner::getName, Function.identity()));
	}

	/**
	 * No we want the average without using {@linkplain DoubleStream#average()},
	 * use a custom collector: {@linkplain DoubleStream#collect(Supplier, ObjDoubleConsumer, BiConsumer)}.
	 * @return average distance of all runs in km.
	 */
	public static double averageDistance(List<Runner> runners) {
		return 0d;
	}

}
