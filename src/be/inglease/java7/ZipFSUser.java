package be.inglease.java7;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author D-KK07MJ
 * @since 2/06/2016
 */
public class ZipFSUser {
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
        try (FileSystem smbfs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
            smbfs.getRootDirectories();
            Path pathInZipfile = smbfs.getPath("/text.txt");
            System.out.println(Files.lines(pathInZipfile).collect(Collectors.joining("\n")));
        }
    }
}