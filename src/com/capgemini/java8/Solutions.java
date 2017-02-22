package com.capgemini.java8;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import com.capgemini.java8.Runner.Gender;

@SuppressWarnings({ "unused", "OptionalGetWithoutIsPresent", "WeakerAccess" })
public class Solutions {

	/**
	 * Count the number of female runners.
	 */
	public static int countFemaleRunners(List<Runner> runners) {
		return (int) runners.stream()
				.filter(runner -> runner.getGender() == Gender.FEMALE)
				.count();
		/* Better solution:
		 * return (int) runners.stream().map(Runner::getGender).filter(Gender.FEMALE::equals).count();
		 */
		// 2
	}

	/**
	 * Give the height of the biggest runner
	 */
	public static double biggestRunner(List<Runner> runners) {
		return runners.stream()
				.mapToDouble(Runner::getHeight)
				.max()
				.getAsDouble();
		// 1.89
	}

	/**
	 * Find the youngest runner
	 */
	public static Runner youngestRunner(List<Runner> runners) {
		return runners.stream()
				.sorted((runner1, runner2) -> runner2.getBirthDate().compareTo(runner1.getBirthDate()))
				.findFirst()
				.get();
		// Zoe
	}

	/**
	 * Find the total distance of all the runners in km.
	 */
	public static double totalDistance(List<Runner> runners) {
		return runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.mapToDouble(r -> r.getDistance() / 1000.0)
				.sum();
		// 522.964
	}

	/**
	 * Find the total distance of all {@link Gender#MALE} the runners in km.
	 */
	public static double totalDistanceMales(List<Runner> runners) {
		return runners.stream()
				.filter(r -> r.getGender() == Gender.MALE)
				.flatMap(r -> r.getRuns().stream())
				.mapToDouble(r -> r.getDistance() / 1000.0)
				.sum();
		// 396.528
	}

	/**
	 * Calculate the calories Jesus burned on all his runs. (hint:
	 * RunCalculation has a static method.)
	 */
	public static int calculateCaloriesJesus(List<Runner> runners) {
		return runners.stream()
				.filter(r -> "Jesus".equals(r.getName()))
				.flatMap(r -> r.getRuns().stream())
				.mapToInt(r -> RunCalculation.calories(r, r.getRunner()))
				.sum();
		// 4035
	}

	/**
	 * Change the speed of all the runs to m/s. Print all the runs before the
	 * change and after the change. Use a method reference for the printing.
	 * (hint: Run has a toString() method already.)
	 * 
	 * This is a nice exercise to see in which order your stream gets parsed.
	 */
	public static void setSpeedToMeterPerSecond(List<Runner> runners) {
		RunCalculation calc = r -> ((double) r.getDistance() / r.getDuration());
		runners.stream()
				.flatMap(runner -> runner.getRuns().stream())
				.peek(System.out::println)
				.forEach(run -> {
					run.setSpeed(calc);
					System.out.println(run);
				});
		// Run of 13000 meters, in 3657 seconds, speed: 12.797374897456931
		// Run of 13000 meters, in 3657 seconds, speed: 3.5548263604047032
		// Run of 13000 meters, in 3779 seconds, speed: 12.384228631913205
		// Run of 13000 meters, in 3779 seconds, speed: 3.440063508864779
		// ...
	}

	/**
	 * Find the fastest female run that is longer than 5km
	 */
	public static double fastestFemaleRun(List<Runner> runners) {
		return runners.stream()
					  .filter(r -> Gender.FEMALE.equals(r.getGender()))
					  .flatMap(r -> r.getRuns().stream())
					  .filter(r -> r.getDistance() > 5000)
					  .mapToDouble(Run::getSpeed)
					  .max()
					  .getAsDouble();
		// 11.739130434782608
	}

	/**
	 * Calculate the average speed of all Sam's runs that are less than 63
	 * minutes.
	 */
	public static double avgSpeedSam(List<Runner> runners) {
		return runners.stream()
				.filter(r -> "Sam".equals(r.getName()))
				.flatMap(r -> r.getRuns().stream())
				.filter(r -> r.getDuration() < 3780)
				.mapToDouble(Run::getSpeed)
				.average()
				.getAsDouble();
		// 12.542413487284362
	}

	/**
	 * @return a String with the names of all Runners separated with a '#'
	 *         sorted on their weight. (Print the runners to see you've sorted
	 *         correctly.) (hint: Use a collector.)
	 */
	public static String namesOfRunners(List<Runner> runners) {
		return runners.stream()
				.sorted((r1, r2) -> ((Double) r1.getWeight()).compareTo(r2.getWeight()))
				.peek(System.out::println)
				.map(Runner::getName)
				.collect(Collectors.joining("#"));
		// Zoe#Jesus#Sophie#Tony#Sam
	}

	/**
	 * @return a list of runs that are longer than one hour
	 */
	public static List<Run> getRuns1hour(List<Runner> runners) {
		return runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.filter(r -> r.getDuration() > 3600)
				.collect(Collectors.toList());
		// Run of 13000 meters, in 3657 seconds, speed: 12.797374897456931
		// Run of 13000 meters, in 3779 seconds, speed: 12.384228631913205
		// Run of 13000 meters, in 3955 seconds, speed: 11.833122629582807
		// Run of 19100 meters, in 5736 seconds, speed: 11.98744769874477
		// Run of 19100 meters, in 5798 seconds, speed: 11.859261814418765
		// Run of 19100 meters, in 5866 seconds, speed: 11.721786566655302
		// Run of 22800 meters, in 6087 seconds, speed: 13.484475110892065
		// Run of 22800 meters, in 5971 seconds, speed: 13.74644113213867
		// Run of 22800 meters, in 6013 seconds, speed: 13.650424081157492
		// Run of 15700 meters, in 4022 seconds, speed: 14.052710094480359
		// Run of 15700 meters, in 4165 seconds, speed: 13.570228091236496
		// Run of 15700 meters, in 4088 seconds, speed: 13.825831702544033
		// Run of 30100 meters, in 8256 seconds, speed: 13.125
		// Run of 30100 meters, in 8391 seconds, speed: 12.913836253128352
		// Run of 30100 meters, in 8154 seconds, speed: 13.289183222958057
	}

	/**
	 * @return a Map:<br>
	 *         Key = Birth date<br>
	 *         Entry = Name of the runner
	 */
	public static Map<LocalDateTime, String> getBirthDatesMap(List<Runner> runners) {
		return runners.stream()
				.collect(Collectors.toMap(Runner::getBirthDate, Runner::getName));

		// {1991-05-10T05:05:55=Sam, 1993-01-24T12:38:47=Zoe,
		// 1983-09-12T22:32:02=Jesus, 1984-08-18T23:45:12=Sophie,
		// 1976-04-19T08:51:24=Tony}
	}

    /**
     * Now we want a map where we can get each runner by his/her name.
     * The name is unique for each runner
     *
     * @return a Map:<br>
     * Key = Name<br>
     * Entry = Runner with this name
     */
    public static Map<String, Runner> getRunnersMapByName(List<Runner> runners) {
        return runners.stream()
        		.collect(Collectors.toMap(Runner::getName, Function.identity()));
    }

	/**
	 * Now we want to get the runners for each year of birth.
	 * 
	 * @return a Map:<br>
	 *         Key = Birth year<br>
	 *         Entry = List of runners who were born in that year
	 */
	public static Map<Integer, List<Runner>> getRunnersMapWithBirthYearKey(List<Runner> runners) {
		return runners.stream()
				.collect(Collectors.groupingBy(r -> r.getBirthDate().getYear()));

		// {1984=[Sophie was born on 1984-08-18 is 1.63 meters big and weighs
		// 61.9 kgs.], 1991=[Sam was born on 1991-05-10 is 1.73 meters big and
		// weighs 75.0 kgs.], 1976=[Tony was born on 1976-04-19 is 1.89 meters
		// big and weighs 72.4 kgs.], 1993=[Zoe was born on 1993-01-24 is 1.69
		// meters big and weighs 56.7 kgs.], 1983=[Jesus was born on 1983-09-12
		// is 1.71 meters big and weighs 61.8 kgs.]}
	}

    /**
     * Print the name and height of each runner. Use a custom collector to append the Strings
     * <p>
     * Example:
     * Sam : 1.73m
     * Tony : 1.89m
     * ...
     *
     * @param runners
     * @return String containing the name and height of each runner
     */
    public static String heightStatistics(List<Runner> runners) {
        return runners.stream()
        		.collect(Collector.of(
        					StringBuilder::new,
        					(strb, runner) -> strb.append(runner.getName()).append(" : ").append(runner.getHeight()),
        					StringBuilder::append,
        					StringBuilder::toString
        				));
    }

    /**
     * If we assume that the runners are in the position of their number,
     * then get all the runners with an uneven number: 1, 3, 5, ...
     *
     * @param runners
     * @return
     */
    public static List<Runner> unevenRunners(List<Runner> runners) {
        return IntStream.iterate(1, i -> i + 2)
        		.mapToObj(runners::get)
        		.collect(Collectors.toList());
    }

	/**
	 * No we want the average without using {@linkplain DoubleStream#average()},
	 * use a custom collector:
	 * {@linkplain DoubleStream#collect(Supplier, ObjDoubleConsumer, BiConsumer)}.
	 * 
	 * @return average distance of all runs in km.
	 */
	public static double averageDistance(List<Runner> runners) {
		double[] av = runners.stream()
				.flatMap(r -> r.getRuns().stream())
				.mapToDouble(r -> r.getDistance() / 1000.0)
				.collect(
						() -> new double[2], 	// Supplier
						(a, d) -> { 			// Accumulator
							a[0] += d; 				// sum
							a[1]++; 				// # elements
						}, 
						(a, b) -> { 			// Combiner
							a[0] += b[0];
							a[1] += b[1];
						});
		return av[0] / av[1];

		// 11.621422222222225
	}
}
