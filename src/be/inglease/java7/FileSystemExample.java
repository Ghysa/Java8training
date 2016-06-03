package be.inglease.java7;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author D-KK07MJ
 * @since 2/06/2016
 */
public class FileSystemExample {
    public static void main(String [] args) throws Throwable {
        usingPath();
    }

    private static void usingPath() throws IOException {
        Path path = Paths.get("test.zip");
        try (FileSystem zipfs = FileSystems.newFileSystem(path, null)) {
            zipfs.getRootDirectories();
            Path pathInZipfile = zipfs.getPath("/text.txt");
            System.out.println(Files.lines(pathInZipfile)
            		.collect(Collectors.joining("\n")));
        }
    }

    private static void usingURI() throws IOException {
        URI uri = URI.create("jar:file:/RADWorkspace/Projects/java8-training/test.zip");
        Map<String, Object> env = Collections.emptyMap(); //Filesystem options
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
            zipfs.getRootDirectories();
            Path pathInZipfile = zipfs.getPath("/text.txt");
            System.out.println(Files.lines(pathInZipfile).collect(Collectors.joining("\n")));
        }
    }
}