package pl.krzysztofdebski.task17;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.ints;

public class Task17b {

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task17/17-task.input"));
        compute(ints(lines.get(4)));
    }

    static long out(long A) {
        long temp = (A % 8) ^ 2;
        return ((temp ^ (A >> temp)) ^ 7) % 8;
    }

    static List<Integer> run(long A) {
        List<Integer> out = new ArrayList<>();
        while (A > 0) {
            long temp = (A % 8) ^ 2;
            out.add((int) (((temp ^ (A >> temp)) ^ 7) % 8));
            A >>= 3;
        }
        return out;
    }

    static void compute(List<Integer> program) {
        Set<Long> possibleInputs = new HashSet<>();
        possibleInputs.add(0L);
        for (int i = program.size() - 1; i >= 0; i--) {
            Integer num = program.get(i);
            Set<Long> nextPossibleInputs = new HashSet<>();
            for (Long possibleInput : possibleInputs) {
                for (int possiblePart = 0; possiblePart < 8; possiblePart++) {
                    long newPossibleInput = (possibleInput << 3) + possiblePart;
                    if (out(newPossibleInput) == num) {
                        nextPossibleInputs.add(newPossibleInput);
                    }
                }
            }
            possibleInputs = nextPossibleInputs;
        }
        Long result = possibleInputs.stream().min(Long::compareTo).get();
        if (!run(result).equals(program)) {
            throw new IllegalStateException();
        }

        System.out.println(result);
    }
}
