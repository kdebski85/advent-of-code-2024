package pl.krzysztofdebski.task9;

import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.sumInts;

public class Task9 {

    public static void main(String[] args) throws IOException {
        long result = 0;

        int[] diskRaw = Utils.readOneDigitOneDimensionArray(Path.of("src/main/resources/task9/9-task.input"));

        int sum = (int) sumInts(diskRaw);

        Integer[] disk = new Integer[sum];
        int pos = 0;
        for (int i = 0; i < diskRaw.length; i++) {
            for (int j = 0; j < diskRaw[i]; j++) {
                if (i % 2 == 0) {
                    disk[pos] = i / 2;
                }
                pos++;
            }
        }

        int startIndex = 0;
        int endIndex = disk.length - 1;

        while (startIndex < endIndex) {
            while (disk[startIndex] != null && startIndex < endIndex) {
                startIndex++;
            }
            while (disk[endIndex] == null && startIndex < endIndex) {
                endIndex--;
            }
            if (startIndex < endIndex) {
                disk[startIndex] = disk[endIndex];
                disk[endIndex] = null;
                startIndex++;
                endIndex--;
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
