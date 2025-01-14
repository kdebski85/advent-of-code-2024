package pl.krzysztofdebski.task22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Task22 {

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<String> lines = readAllLines(Path.of("src/main/resources/task22/22-task.input"));
        for (String line : lines) {
            long v = Long.parseLong(line);
            for (int i = 0; i < 2000; i++) {
                v = (v ^ (v << 6)) % 16777216;
                v = (v ^ (v >> 5)) % 16777216;
                v = (v ^ (v << 11)) % 16777216;
            }
            result += v;
        }

        System.out.println(result);
    }
}
