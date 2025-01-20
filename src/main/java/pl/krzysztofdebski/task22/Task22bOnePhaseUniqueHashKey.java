package pl.krzysztofdebski.task22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Files.readAllLines;

public class Task22bOnePhaseUniqueHashKey {

    private static final long MASK = 16777216 - 1; //16777216 is 2^24, so "% 16777216" is same as "& (16777216 - 1)"
    private static final int ITERATIONS = 2000;
    private static final int DIFFS = ITERATIONS - 4;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        //PHASE 1 - parse input, compute prices and price changes
        final Map<Integer, Integer> changeSequenceToPriceSum = new HashMap<>(); //for each seller: encoded price change sequence (the first occurrence) -> price

        byte[] prices = new byte[ITERATIONS]; //values overwritten for each seller

        for (String line: readAllLines(Path.of("src/main/resources/task22/22-task.input"))) {
            long price = Long.parseLong(line);

            for (int i = 0; i < ITERATIONS; i++) {
                price = (price ^ (price << 6)) & MASK;
                price = (price ^ (price >> 5)); // "& MASK" is not needed since "price" and "price >> 5" already have all high bytes 0
                price = (price ^ (price << 11)) & MASK;
                prices[i] = (byte) (price % 10);
            }

            Set<Integer> changesSeen = new HashSet<>();

            for (int i = 0; i < DIFFS; i++) {
                byte p3 = prices[i + 3];
                byte p4 = prices[i + 4];
                if (p4 > p3) { //if the last change was not positive, the previous sequence was better or the same, so we do not have to check this one
                    byte p0 = prices[i];
                    byte p1 = prices[i + 1];
                    byte p2 = prices[i + 2];
                    Integer key = key((byte) (p1 - p0), (byte) (p2 - p1), (byte) (p3 - p2), (byte) (p4 - p3));
                    if (changesSeen.add(key)) {
                        changeSequenceToPriceSum.compute(key, (k, v) -> (v == null ? 0 : v) + p4);
                    }
                }
            }
        }

        //PHASE 2 - find the best solution
        Integer result = Collections.max(changeSequenceToPriceSum.values());

        System.out.printf("Result: %s\n", result);
        System.out.printf("Time: %s ms\n", System.currentTimeMillis() - startTime);
    }

    //each input is in range <-9, 9>, so the method produces an unique value for each tuple
    private static int key(byte a, byte b, byte c, byte d) {
        return a + b * 100 + c * 10_000 + d * 1_0000_000;
    }
}
