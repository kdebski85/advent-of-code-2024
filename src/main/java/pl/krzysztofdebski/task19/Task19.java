package pl.krzysztofdebski.task19;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;


public class Task19 {

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<String> lines = readAllLines(Path.of("src/main/resources/task19/19-task.input"));
        String[] patterns = lines.getFirst().split(", ");
        Set<String> pat = new HashSet<>(Arrays.asList(patterns));

        for (int i = 2; i < lines.size(); i++) {
            if (isPossible(lines.get(i), pat)) {
                result ++;
            }
        }

        System.out.println(result);
    }

    static boolean isPossible(String design, Set<String> towels) {
        boolean[] prefixPossible = new boolean[design.length() + 1];
        prefixPossible[0] = true;

        for (int i = 1; i < design.length() + 1; i++) {
            for (String towel : towels) {
                int towelLength = towel.length();
                if (i >= towelLength && design.startsWith(towel, i - towelLength)) {
                    if (prefixPossible[i - towelLength]) {
                        prefixPossible[i] = true;
                        break;
                    }
                }
            }
        }

        return prefixPossible[design.length()];
    }
}
