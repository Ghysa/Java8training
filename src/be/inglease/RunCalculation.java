package be.inglease;

import be.inglease.Runner.Gender;

@FunctionalInterface
public interface RunCalculation {

	double speed(Run run);
	
	default RunCalculation andThen(RunCalculation after) {
		return r -> {speed(r); return after.speed(r); };
	}
	
	public static int calories(Run run, Runner runner) {
		if (runner.getGender() == Gender.MALE) {
			return (int) (((run.getDistance() / 1000.0) * (0.94 * runner.getWeight())));
		} else {
			return (int) (((run.getDistance() / 1000.0) * (0.78 * runner.getWeight())));
		}
	}
}
