package be.inglease.java7;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TryWithResources {

	public static void printFileJava7() throws IOException {
		
		try (FileInputStream input = new FileInputStream("file.txt");
				BufferedInputStream bufferedInput = new BufferedInputStream(input)) {

			int data = bufferedInput.read();

			while (data != -1) {
				System.out.print((char) data);
				data = bufferedInput.read();
			}
		}
	}
}
