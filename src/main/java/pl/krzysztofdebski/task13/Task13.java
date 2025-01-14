package pl.krzysztofdebski.task13;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task13 {

    static Pattern patternA = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)");
    static Pattern patternB = Pattern.compile("Button B: X\\+(\\d+), Y\\+(\\d+)");
    static Pattern patternPrize = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    record Machine(long ax, long ay, long bx, long by, long px, long py) {

    }

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<List<String>> linesPartitioned = partitionByNewLines(readAllLines(Path.of("src/main/resources/task13/13-task.input")));

/*Button A: X+38, Y+33
Button B: X+11, Y+47
Prize: X=1461, Y=2879*/

        List<Machine> machines = new ArrayList<>();

        for (List<String> lines : linesPartitioned) {
            String a = lines.get(0);
            Matcher matcherA = patternA.matcher(a);
            if (!matcherA.matches()) {
                throw new IllegalArgumentException(a);
            }
            String b = lines.get(1);
            Matcher matcherB = patternB.matcher(b);
            if (!matcherB.matches()) {
                throw new IllegalArgumentException(b);
            }

            String p = lines.get(2);
            Matcher matcherP = patternPrize.matcher(p);
            if (!matcherP.matches()) {
                throw new IllegalArgumentException(b);
            }

            machines.add(
                new Machine(
                    Long.parseLong(matcherA.group(1)), Long.parseLong(matcherA.group(2)),
                    Long.parseLong(matcherB.group(1)), Long.parseLong(matcherB.group(2)),
                    Long.parseLong(matcherP.group(1)), Long.parseLong(matcherP.group(2))
                ));
        }

        for (Machine machine : machines) {
            long min = Long.MAX_VALUE;
            for (int a = 0; a <= 100; a++) {
                for (int b = 0; b <= 100; b++) {
                    if (machine.px == a * machine.ax + b * machine.bx
                        && machine.py == a * machine.ay + b * machine.by) {
                        int c = 3 * a + b;
                        if (c < min) {
                            min = c;
                        }
                    }
                }
            }
            if (min < Long.MAX_VALUE) {
                result += min;
            }
        }

        System.out.println(result);
    }
}
