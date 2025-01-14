package pl.krzysztofdebski.task10;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readOneDigitTwoDimensionsArray;

public class Task10Alternative {

    record Point(int x, int y) {}

    public static void main(String[] args) throws IOException {
        long result = 0;
        int[][] map = readOneDigitTwoDimensionsArray(Path.of("src/main/resources/task10/10-task.input"));

        Map<Point, Set<Point>> reached = new HashMap<>(); //point -> starting points

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) {
                    Set<Point> starting = new HashSet<>();
                    starting.add(new Point(x, y));
                    reached.put(new Point(x, y), starting);
                }
            }
        }

        for (int k = 1; k <= 9; k++) {
            Map<Point, Set<Point>> reachedNew = new HashMap<>();
            for (Entry<Point, Set<Point>> e : reached.entrySet()) {
                Point point = e.getKey();
                Set<Point> startingPoints = e.getValue();
                int px = point.x;
                int py = point.y;
                addIfValid(map, px - 1, py, k, reachedNew, startingPoints);
                addIfValid(map, px + 1, py, k, reachedNew, startingPoints);
                addIfValid(map, px, py - 1, k, reachedNew, startingPoints);
                addIfValid(map, px, py + 1, k, reachedNew, startingPoints);
            }
            reached = reachedNew;
        }

        for (Set<Point> startingPoints : reached.values()) {
            result += startingPoints.size();
        }

        System.out.println(result);
    }

    private static void addIfValid(int[][] map, int px, int py, int k, Map<Point, Set<Point>> reachedNew, Set<Point> startingPointsNew) {
        if (inBounds(px, py, map) && map[py][px] == k) {
            reachedNew.compute(new Point(px, py),
                (p, startingPoints) -> {
                    Set<Point> set = startingPoints == null ? new HashSet<>() : startingPoints;
                    set.addAll(startingPointsNew);
                    return set;
                });
        }
    }
}
