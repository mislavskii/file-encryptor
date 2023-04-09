package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

    static void wipeDir(String path) {
        Path directory = Paths.get(path);
        try (Stream<Path> stream = Files.walk(directory)) {
            stream.sorted((p1, p2) -> -p1.compareTo(p2)).forEach(Utils::deleteFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
