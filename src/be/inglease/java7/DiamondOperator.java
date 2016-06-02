package be.inglease.java7;

import java.util.ArrayList;
import java.util.List;

public class DiamondOperator {

	public static void diamondOperatorJava7() {
		List<String> list = new ArrayList<>();
		list.add("A");
	
		  // The following statement should fail since addAll expects
		  // Collection<? extends String>
	
		  // Since Java 8 this also compiles without problems
		list.addAll(new ArrayList<>()); 
	}
	
}
