package pl.krzysztofdebski.task22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Files.readAllLines;

public class Task22bTwoPhasesUniqueHashKey {

    private static final long MASK = 16777216 - 1; //16777216 is 2^24, so "% 16777216" is same as "& (16777216 - 1)"
    private static final int ITERATIONS = 2000;
    private static final int DIFFS = ITERATIONS - 4;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        //PHASE 1 - parse input, compute prices and price changes

        List<String> lines = readAllLines(Path.of("src/main/resources/task22/22-task.input"));
        final int sellerCount = lines.size();
        final byte[][] prices = new byte[sellerCount][ITERATIONS];
        final Map<Integer, Byte>[] changeSequenceToPricePerSeller = new Map[sellerCount]; //for each seller: encoded price change sequence (the first occurrence) -> price
        for (int i = 0; i < sellerCount; i++) {
            changeSequenceToPricePerSeller[i] = new HashMap<>(ITERATIONS);
        }

        final Set<Integer> changesToCheck = new HashSet<>(); //encoded changes with "key" method

        for (int seller = 0; seller < sellerCount; seller++) {
            String line = lines.get(seller);

            byte[] pricesForSeller = prices[seller];
            long price = Long.parseLong(line);
            for (int i = 0; i < ITERATIONS; i++) {
                price = (price ^ (price << 6)) & MASK;
                price = (price ^ (price >> 5)); // "& MASK" is not needed since "price" and "price >> 5" already have all high bytes 0
                price = (price ^ (price << 11)) & MASK;
                pricesForSeller[i] = (byte) (price % 10);
            }

            Map<Integer, Byte> changeSequenceToPrice = changeSequenceToPricePerSeller[seller];

            for (int i = 0; i < DIFFS; i++) {
                byte p0 = pricesForSeller[i];
                byte p1 = pricesForSeller[i + 1];
                byte p2 = pricesForSeller[i + 2];
                byte p3 = pricesForSeller[i + 3];
                byte p4 = pricesForSeller[i + 4];
                Integer key = key((byte) (p1 - p0), (byte) (p2 - p1), (byte) (p3 - p2), (byte) (p4 - p3));
                changeSequenceToPrice.computeIfAbsent(key, k -> {
                    if (p4 - p3 > 0) { //if the last change was not positive, the previous sequence was better or the same, so we do not have to check this one
                        changesToCheck.add(key);
                    }
                    return p4;
                });
            }
        }

        //PHASE 2 - find the best solution
        long result = 0;

        for (Integer change : changesToCheck) {
            long sum = 0;
            for (int seller = 0; seller < sellerCount; seller++) {
                Byte price = changeSequenceToPricePerSeller[seller].get(change);
                if (price != null) {
                    sum += price;
                }
            }
            result = Math.max(result, sum);
        }

        System.out.printf("Result: %s\n", result);
        System.out.printf("Time: %s ms\n", System.currentTimeMillis() - startTime);
    }

    //each input is in range <-9, 9>, so the method produces an unique value for each tuple
    private static int key(byte a, byte b, byte c, byte d) {
        return a + b * 100 + c * 10_000 + d * 1_0000_000;
    }
}
