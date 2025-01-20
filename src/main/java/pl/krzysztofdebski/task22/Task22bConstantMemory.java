package pl.krzysztofdebski.task22;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.lines;

public class Task22bConstantMemory {

    private static final long MASK = 16777216 - 1; //16777216 is 2^24, so "% 16777216" is same as "& (16777216 - 1)"
    private static final int ITERATIONS = 2000;

    @SuppressWarnings({"resource"})
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        int[][][][] changeSequenceToPriceSum = new int[19][19][19][10]; //price change sequence (the first occurrence) -> price
        short[][][][] changeSequenceSeenLastSeller = new short[19][19][19][10]; //price change sequence -> last sellerIdx that had this sequence

        int result = 0;
        short sellerIdx = 0;

        for (String line: (Iterable<String>) lines(Path.of("src/main/resources/task22/22-task.input"))::iterator) {
            sellerIdx++;

            byte p0 = -1;
            byte p1 = -1;
            byte p2 = -1;
            byte p3 = -1;
            byte p4;

            long price = Long.parseLong(line);

            for (int i = 0; i < ITERATIONS; i++) {
                price = (price ^ (price << 6)) & MASK;
                price = (price ^ (price >> 5)); // "& MASK" is not needed since "price" and "price >> 5" already have all high bytes 0
                price = (price ^ (price << 11)) & MASK;
                p4 = (byte) (price % 10);

                if (i > 3) {
                    byte diff4 = (byte) (p4 - p3);
                    if (diff4 > 0) { //if the last change was not positive, the previous sequence was better or the same, so we do not have to check this one
                        byte diff1 = (byte) (p1 - p0 + 9);
                        byte diff2 = (byte) (p2 - p1 + 9);
                        byte diff3 = (byte) (p3 - p2 + 9);

                        if (changeSequenceSeenLastSeller[diff1][diff2][diff3][diff4] < sellerIdx) {
                            changeSequenceSeenLastSeller[diff1][diff2][diff3][diff4] = sellerIdx;
                            result = Math.max(result, changeSequenceToPriceSum[diff1][diff2][diff3][diff4] += p4);
                        }
                    }
                }

                p0 = p1;
                p1 = p2;
                p2 = p3;
                p3 = p4;
            }
        }

        System.out.printf("Result: %s\n", result);
        System.out.printf("Time: %s ms\n", System.currentTimeMillis() - startTime);
    }
}
