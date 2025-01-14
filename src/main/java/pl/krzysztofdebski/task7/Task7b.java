package pl.krzysztofdebski.task7;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.Files.readAllLines;

public class Task7b {

    public static void main(String[] args) throws IOException {
        BigDecimal result = BigDecimal.ZERO;
        List<String> lines = readAllLines(Path.of("src/main/resources/task7/7-task.input"));
        for (String line : lines) {
            String[] split = line.split(":");
            long r = Long.parseLong(split[0]);
            Scanner scanner = new Scanner(split[1]);
            List<Long> numbers = new ArrayList<>();
            while (scanner.hasNextLong()) {
                numbers.add(scanner.nextLong());
            }
            if (found(r, numbers.getFirst(), numbers, 1)) {
                result = result.add(BigDecimal.valueOf(r));
            }
        }

        System.out.println(result);
    }

    static boolean found(long searched, long soFar, List<Long> numbers, int index) {
        if (index == numbers.size()) {
            return searched == soFar;
        }
        Long first = numbers.get(index);
        return found(searched, soFar + first, numbers, index + 1)
            || found(searched, soFar * first, numbers, index + 1)
            || found(searched, Long.parseLong(String.valueOf(soFar) + first), numbers, index + 1);
    }
}
