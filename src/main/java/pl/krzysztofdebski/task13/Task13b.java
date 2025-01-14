package pl.krzysztofdebski.task13;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task13b {

    static Pattern patternA = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)");
    static Pattern patternB = Pattern.compile("Button B: X\\+(\\d+), Y\\+(\\d+)");
    static Pattern patternPrize = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

    record Machine(BigInteger ax, BigInteger ay, BigInteger bx, BigInteger by, BigInteger px, BigInteger py) {

    }

    public static void main(String[] args) throws IOException {
        BigInteger result = BigInteger.ZERO;

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
                    new BigInteger(matcherA.group(1)), new BigInteger(matcherA.group(2)),
                    new BigInteger(matcherB.group(1)), new BigInteger(matcherB.group(2)),
                    new BigInteger(matcherP.group(1)).add(BigInteger.valueOf(10000000000000L)),
                    new BigInteger(matcherP.group(2)).add(BigInteger.valueOf(10000000000000L))
                ));
        }

        for (Machine machine : machines) {
            BigInteger c = machine.py.multiply(machine.ax).subtract((machine.ay).multiply(machine.px));
            BigInteger d = machine.ax.multiply(machine.by).subtract((machine.ay).multiply(machine.bx));
            BigInteger[] divideAndRemainder = c.divideAndRemainder(d);
            if (divideAndRemainder[1].equals(BigInteger.ZERO)) {
                BigInteger b = divideAndRemainder[0];
                BigInteger ac = machine.px.subtract(b.multiply(machine.bx));
                BigInteger[] divideAndRemainderA = ac.divideAndRemainder(machine.ax);
                if (divideAndRemainderA[1].equals(BigInteger.ZERO)) {
                    BigInteger a = divideAndRemainderA[0];
                    result = result.add(a.multiply(BigInteger.valueOf(3))).add(b);
                }
            }
        }

        System.out.println(result);
    }
}
