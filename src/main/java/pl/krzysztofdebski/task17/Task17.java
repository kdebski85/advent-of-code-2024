package pl.krzysztofdebski.task17;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.intsArray;
import static pl.krzysztofdebski.utils.ParsingUtils.longs;


public class Task17 {

    static long a;
    static long b;
    static long c;

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task17/17-task.input"));

        a = longs(lines.get(0)).getFirst();
        b = longs(lines.get(1)).getFirst();
        c = longs(lines.get(2)).getFirst();

        int[] program = intsArray(lines.get(4));

        int pointer = 0;
        List<Long> result = new ArrayList<>();

        while (pointer < program.length) {
            int i = program[pointer];
            int op = program[pointer + 1];
            switch (i) {
                case 0 -> {
                    a = a / (2 << (combo(op) - 1));
                    pointer += 2;
                }
                case 1 -> {
                    b = b ^ op;
                    pointer += 2;
                }
                case 2 -> {
                    b = combo(op) % 8;
                    pointer += 2;
                }
                case 3 -> {
                    if (a != 0) {
                        pointer = op;
                    } else {
                        pointer += 2;
                    }
                }
                case 4 -> {
                    b = b ^ c;
                    pointer += 2;
                }
                case 5 -> {
                    result.add(combo(op) % 8);
                    pointer += 2;
                }
                case 6 -> {
                    b = a / (2 << (combo(op) - 1));
                    pointer += 2;
                }
                case 7 -> {
                    c = a / (2 << (combo(op) - 1));
                    pointer += 2;
                }
            }
        }

        System.out.println(result.stream().map(x -> Long.toString(x)).collect(Collectors.joining(",")));
    }

    private static long combo(int op) {
        if (op <= 3) {
            return op;
        }
        return switch (op) {
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalStateException();
        };
    }
}
