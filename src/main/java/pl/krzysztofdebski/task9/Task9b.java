package pl.krzysztofdebski.task9;

import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.sumInts;

public class Task9b {

    public static void main(String[] args) throws IOException {
        long result = 0;
        int[] diskRaw = Utils.readOneDigitOneDimensionArray(Path.of("src/main/resources/task9/9-task.input"));

        int sum = (int) sumInts(diskRaw);

        Integer[] disk = new Integer[sum];
        int[] lenghts = new int[diskRaw.length / 2 + 1];
        int pos = 0;
        for (int i = 0; i < diskRaw.length; i++) {
            for (int j = 0; j < diskRaw[i]; j++) {
                if (i % 2 == 0) {
                    disk[pos] = i / 2;
                    lenghts[i / 2] = diskRaw[i];
                }
                pos++;
            }
        }

        main: for (int i = disk.length - 1; i > 0; i--) {
            Integer id = disk[i];
            if (id != null) {
                int length = lenghts[id];
                for (int j = 0; j < i; j++) {
                    if (disk[j] == null) {
                        int free = 0;
                        for (int k = j; k < j + length && k < disk.length && disk[k] == null; k++) {
                            free++;
                        }
                        if (free >= length) {
                            for (int k = 0; k < length; k++) {
                                disk[j + k] = disk[i - k];
                                disk[i - k] = null;
                            }
                            continue main;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < disk.length; i++) {
            if (disk[i] != null) {
                result += (long) i * disk[i];
            }
        }

        System.out.println(result);
    }
}
