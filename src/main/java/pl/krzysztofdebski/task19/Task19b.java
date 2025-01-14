package pl.krzysztofdebski.task19;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;


public class Task19b {

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<String> lines = readAllLines(Path.of("src/main/resources/task19/19-task.input"));
        String[] patterns = lines.getFirst().split(", ");
        Set<String> pat = new HashSet<>(Arrays.asList(patterns));

        for (int i = 2; i < lines.size(); i++) {
            result += countPossible(lines.get(i), pat);
        }

        System.out.println(result);
    }

    static long countPossible(String design, Set<String> towels) {
        long[] prefixPossibilities = new long[design.length() + 1];
        prefixPossibilities[0] = 1;

        for (int i = 1; i < design.length() + 1; i++) {
            for (String towel : towels) {
                int towelLength = towel.length();
                if (i >= towelLength && design.startsWith(towel, i - towelLength)) {
                    prefixPossibilities[i] += prefixPossibilities[i - towelLength];
                }
            }
        }

        return prefixPossibilities[design.length()];
    }
}
