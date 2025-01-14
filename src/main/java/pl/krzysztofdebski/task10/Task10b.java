package pl.krzysztofdebski.task10;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readOneDigitTwoDimensionsArray;
import static pl.krzysztofdebski.utils.Utils.sumInts;

public class Task10b {

    public static void main(String[] args) throws IOException {
        long result;

        int[][] map = readOneDigitTwoDimensionsArray(Path.of("src/main/resources/task10/10-task.input"));

        Map<Coord, Integer> reached = new HashMap<>();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) {
                    reached.put(new Coord(y, x), 1);
                }
            }
        }

        for (int k = 1; k <= 9; k++) {
            Map<Coord, Integer> reachedNew = new HashMap<>();
            for (Entry<Coord, Integer> e : reached.entrySet()) {
                Coord point = e.getKey();
                Integer paths = e.getValue();
                for(Direction dir: Direction.CARDINAL_DIRECTIONS) {
                    addIfValid(map, point.relative(dir), k, reachedNew, paths);
                }
            }
            reached = reachedNew;
        }

        result = sumInts(reached.values());

        System.out.println(result);
    }

    private static void addIfValid(int[][] map, Coord coord, Integer k, Map<Coord, Integer> reachedNew, Integer paths) {
        if (k.equals(coord.valueIfInBounds(map))) {
            reachedNew.compute(coord,
                (p, occ) -> (occ == null ? 0 : occ) + paths);
        }
    }
}
