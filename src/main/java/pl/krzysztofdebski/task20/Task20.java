package pl.krzysztofdebski.task20;

import pl.krzysztofdebski.utils.DistanceUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.inBounds;


public class Task20 {

    public static void main(String[] args) throws IOException {
        long result = 0;

        int size = 141;
        List<String> lines = readAllLines(Path.of("src/main/resources/task20/20-task.input"));

       // final int MAX_MOVES = 2; //part1
        final int MAX_MOVES = 20; //part2

        boolean[][] w = new boolean[size][size];

        int bx = 0;
        int by = 0;

        int ex = 0;
        int ey = 0;

        for (int y = 0; y < size; y++) {
            String line = lines.get(y);
            for (int x = 0; x < size; x++) {
                char c = line.charAt(x);
                if (c == 'S') {
                    bx = x;
                    by = y;
                }
                if (c == 'E') {
                    ex = x;
                    ey = y;
                }

                if (c == '#') {
                    w[y][x] = true;
                }
            }
        }

        Integer[][] distFromStart = DistanceUtils.distances(bx, by, w);
        Integer[][] distFromEnd = DistanceUtils.distances(ex, ey, w);

        int noCheatsDistance = distFromStart[ey][ex];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (distFromStart[y][x] != null) {
                    for (int y2 = Math.max(y - MAX_MOVES, 0); y2 <= y + MAX_MOVES && y2 < size; y2++) {
                        int yabs = Math.abs(y - y2);
                        for (int x2 = Math.max(x - MAX_MOVES + yabs, 0); x2 <= x + MAX_MOVES - yabs && x2 < size; x2++) {
                            if (distFromEnd[y2][x2] != null
                                && (distFromStart[y][x] + yabs + Math.abs(x2 - x) + distFromEnd[y2][x2]) <= noCheatsDistance - 100) {
                                result++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }
}
