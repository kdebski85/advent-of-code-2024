package pl.krzysztofdebski.task11;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task11 {

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<Long> stones = new ArrayList<>();

        Scanner scanner = new Scanner(Path.of("src/main/resources/task11/11-task.input"));
        while (scanner.hasNextLong()) {
            stones.add(scanner.nextLong());
        }

        for (int i = 0; i < 25; i++) {
            List<Long> stonesNew = new ArrayList<>();
            for (Long stone : stones) {
                if (stone == 0L) {
                    stonesNew.add(1L);
                } else {
                    String string = String.valueOf(stone);
                    if (string.length() % 2 == 0) {
                        stonesNew.add(Long.parseLong(string.substring(0, string.length() / 2)));
                        stonesNew.add(Long.parseLong(string.substring(string.length() / 2)));
                    } else {
                        stonesNew.add(stone * 2024L);
                    }
                }
            }
            stones = stonesNew;
        }

        System.out.println(stones.size());
    }
}
