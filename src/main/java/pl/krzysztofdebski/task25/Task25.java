package pl.krzysztofdebski.task25;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task25 {

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<int[]> keys = new ArrayList<>();
        List<int[]> locks = new ArrayList<>();

        List<String> lines = readAllLines(Path.of("src/main/resources/task25/25-task.input"));
        for (List<String> strings : partitionByNewLines(lines)) {
            if (strings.getFirst().charAt(0) == '#') {
                int[] lock = new int[5];
                locks.add(lock);
                for (int i = 0; i < 5; i++) {
                    int j = 1;
                    while (j < 7 && strings.get(j).charAt(i) == '#') {
                        j++;
                    }
                    lock[i] = j - 1;
                }
            } else {
                int[] key = new int[5];
                keys.add(key);
                for (int i = 0; i < 5; i++) {
                    int j = 5;
                    while (j >= 0 && strings.get(j).charAt(i) == '#') {
                        j--;
                    }
                    key[i] = 7 - j - 2;
                }
            }
        }

        for (int[] key : keys) {
            l: for (int[] lock : locks) {
                for (int i = 0; i < 5; i++) {
                    if (lock[i] + key[i] > 5) {
                        continue l;
                    }
                }
                result++;
            }
        }

        System.out.println(result);
    }
}
