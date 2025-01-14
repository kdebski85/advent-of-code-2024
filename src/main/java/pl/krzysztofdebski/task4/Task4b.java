package pl.krzysztofdebski.task4;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;
import pl.krzysztofdebski.utils.Utils;

import java.io.IOException;
import java.nio.file.Path;

import static pl.krzysztofdebski.utils.Direction.ORDINAL_DIRECTIONS;
import static pl.krzysztofdebski.utils.Direction.left90;

public class Task4b {

    public static void main(String[] args) throws IOException {
        char[][] chars = Utils.readTwoDimensionsCharArray(Path.of("src/main/resources/task4/4-task.input"));
        int cols = chars[0].length;

        long result = 0;

        for (int y = 1; y < chars.length - 1; y++) {
            for (int x = 1; x < cols - 1; x++) {
                if (chars[y][x] == 'A') {
                    Coord coord = new Coord(y, x);
                    for (Direction dir : ORDINAL_DIRECTIONS) {
                        Direction dir2 = left90(dir);
                        if (coord.relative(dir).value(chars) == 'M'
                            && coord.relative(dir, -1).value(chars) == 'S'
                            && coord.relative(dir2).value(chars) == 'M'
                            && coord.relative(dir2, -1).value(chars) == 'S') {
                            result++;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
}
