package pl.krzysztofdebski.task11;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Task11b {

    public static void main(String[] args) throws IOException {
        long result = 0;

        Map<Long, Long> stones = new HashMap<>();

        Scanner scanner = new Scanner(Path.of("src/main/resources/task11/11-task.input"));
        while (scanner.hasNextInt()) {
            stones.put(scanner.nextLong(), 1L);
        }

        for (int i = 0; i < 75; i++) {
            Map<Long, Long> stonesNew = new HashMap<>();
            for (Entry<Long, Long> e : stones.entrySet()) {
                Long stone = e.getKey();
                Long occ = e.getValue();

                if (stone == 0L) {
                    addStones(stonesNew, 1L, occ);
                } else {
                    String string = String.valueOf(stone);
                    if (string.length() % 2 == 0) {
                        addStones(stonesNew, Long.parseLong(string.substring(0, string.length() / 2)), occ);
                        addStones(stonesNew, Long.parseLong(string.substring(string.length() / 2)), occ);
                    } else {
                        addStones(stonesNew, stone * 2024L, occ);
                    }
                }
            }
            stones = stonesNew;
        }

        for (Long value : stones.values()) {
            result += value;
        }

        System.out.println(result);
    }

    private static void addStones(Map<Long, Long> stonesNew, Long value, Long occ) {
        stonesNew.compute(value,
            (key, existingValue) -> (existingValue == null ? 0L : existingValue) + occ);
    }
}
