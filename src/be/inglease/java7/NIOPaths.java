package be.inglease.java7;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NIOPaths {

	public static void createPathsJava7() throws IOException {
		
		// Creating a path
		Path p1 = Paths.get("/temp/foo");		
		Path p2 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");
		
		// Converting paths
		Path p3 = p1.toAbsolutePath();
		Path p4 = p2.toRealPath();
		
		// Joining paths
		Path p5 = p3.resolve("bar");
		
		// Path between two paths
		Path p6 = p3.relativize(p5);
	}
}
