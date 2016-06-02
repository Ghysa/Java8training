package be.inglease.java7;

public class StringInSwitch {

	public static String stringInSwitchJava7(String gender) {
		String message;
		switch (gender) {
			case "MALE":
				message = "Too many males.";
				break;
			case "FEMALE":
				message = "We need more women!";
				break;
			default:
				throw new IllegalArgumentException();
		}
		return message;
	}
}
