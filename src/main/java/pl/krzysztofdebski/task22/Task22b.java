package pl.krzysztofdebski.task22;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.nio.file.Files.readAllLines;

public class Task22b {

    public static void main(String[] args) throws IOException {
        long result = 0;

      //  long startTime = System.currentTimeMillis();

        List<String> lines = readAllLines(Path.of("src/main/resources/task22/22-task.input"));
        List<Byte[]> prices = new ArrayList<>();
        List<Map<Integer, Integer>> changesToFirstIndex = new ArrayList<>(); //for each seller: encoded change -> first index for this change
        Set<Integer> changesToCheck = new HashSet<>(); //encoded changes with "key" method
        for (String line : lines) {
            Byte[] pricesForSeller = new Byte[2000];
            long v = Long.parseLong(line);
            for (int i = 0; i < 2000; i++) {
                v = (v ^ (v << 6)) % 16777216;
                v = (v ^ (v >> 5)) % 16777216;
                v = (v ^ (v << 11)) % 16777216;
                pricesForSeller[i] = (byte) (v % 10);
            }

            prices.add(pricesForSeller);

            Map<Integer, Integer> changesToFirstIndexForSeller = new HashMap<>();
            changesToFirstIndex.add(changesToFirstIndexForSeller);

            for (int i = 0; i < pricesForSeller.length - 4; i++) {
                int index = i;
                Byte p0 = pricesForSeller[i];
                Byte p1 = pricesForSeller[i + 1];
                Byte p2 = pricesForSeller[i + 2];
                Byte p3 = pricesForSeller[i + 3];
                Byte p4 = pricesForSeller[i + 4];
                Integer key = key(p1 - p0, p2 - p1, p3 - p2, p4 - p3);
                changesToFirstIndexForSeller.computeIfAbsent(key, k -> {
                    if (p4 - p3 > 0) {
                        changesToCheck.add(key);
                    }
                    return index;
                });
            }
        }

       // System.out.println(System.currentTimeMillis() - startTime);
       // startTime = System.currentTimeMillis();

        for (Integer change : changesToCheck) {
            long sum = 0;
            int sellerCount = changesToFirstIndex.size();
            for (int seller = 0; seller < sellerCount; seller++) {
                Integer index = changesToFirstIndex.get(seller).get(change);
                if (index != null) {
                    sum += prices.get(seller)[index];
                }
            }
            result = Math.max(result, sum);
        }

     //   System.out.println(System.currentTimeMillis() - startTime);
        System.out.println(result);
    }

    private static int key(int a, int b, int c, int d) {
        return a + b * 100 + c * 10_000 + d * 1_0000_000;
    }
}
