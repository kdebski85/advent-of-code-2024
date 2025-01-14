package pl.krzysztofdebski.task4;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static pl.krzysztofdebski.utils.Direction.ALL_DIRECTIONS;

public class Task4 {
    public static void main(String[] args) throws IOException {
        char[][] chars = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task4/4-task.input"));
        int cols = chars[0].length;

        long result = 0;

        for (int y = 0; y < chars.length; y++) {
            for (int x = 0; x < cols; x++) {
                if (chars[y][x] == 'X') {
                    Coord coord = new Coord(y, x);
                    for (Direction dir: ALL_DIRECTIONS) {
                        if (Character.valueOf('M').equals(coord.relative(dir).valueIfInBounds(chars))
                            && Character.valueOf('A').equals(coord.relative(dir, 2).valueIfInBounds(chars))
                            && Character.valueOf('S').equals(coord.relative(dir, 3).valueIfInBounds(chars))) {
                            result++;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
}
