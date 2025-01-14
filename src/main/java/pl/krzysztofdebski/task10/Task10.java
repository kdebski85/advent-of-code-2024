package pl.krzysztofdebski.task10;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static pl.krzysztofdebski.utils.Direction.CARDINAL_DIRECTIONS;
import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readOneDigitTwoDimensionsArray;

public class Task10 {

    public static void main(String[] args) throws IOException {
        long result = 0;
        int[][] map = readOneDigitTwoDimensionsArray(Path.of("src/main/resources/task10/10-task.input"));

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 0) {
                    Set<Coord> reached = new HashSet<>();
                    reached.add(new Coord(y, x));
                    for (int k = 1; k <= 9; k++) {
                        Set<Coord> reachedNew = new HashSet<>();
                        for (Coord point : reached) {
                            for (Direction direction: CARDINAL_DIRECTIONS) {
                                addIfValid(map, point.relative(direction), k, reachedNew);
                            }
                        }
                        reached = reachedNew;
                    }
                    result += reached.size();
                }
            }
        }

        System.out.println(result);
    }

    private static void addIfValid(int[][] map, Coord coord, Integer k, Set<Coord> reachedNew) {
        if (k.equals(coord.valueIfInBounds(map))) {
            reachedNew.add(coord);
        }
    }
}
