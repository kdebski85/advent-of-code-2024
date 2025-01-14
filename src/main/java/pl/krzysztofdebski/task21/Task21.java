package pl.krzysztofdebski.task21;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import static java.nio.file.Files.readAllLines;

public class Task21 {

    final static char[][] NUM_KEYPAD = {
        {'7', '8', '9'},
        {'4', '5', '6'},
        {'1', '2', '3'},
        {' ', '0', 'A'},
    };

    final static char[][] DIR_KEYPAD = {
        {' ', '^', 'A'},
        {'<', 'v', '>'}
    };

    public static final int DIR_ROBOTS = 25; //2 - part1, 25 - part2
    public static final int ALL_ROBOTS = DIR_ROBOTS + 1;

    final static char[][][] KEYPADS = new char[ALL_ROBOTS][][];

    @SuppressWarnings("unchecked")
    static final Map<CacheKey, Long>[] caches = new Map[ALL_ROBOTS+1];

    static {
        KEYPADS[0] = NUM_KEYPAD;
        for (int i = 1; i <= DIR_ROBOTS; i++) {
            KEYPADS[i] = DIR_KEYPAD;
        }

        for (int i = 1; i <= ALL_ROBOTS; i++) {
            caches[i] = new HashMap<>();
        }
    }

    private static Coord keypadCoord(char c, char[][] keypad) {
        for (int y = 0; y < keypad.length; y++) {
            for (int x = 0; x < keypad[y].length; x++) {
                if (c == keypad[y][x]) {
                    return new Coord(y, x);
                }
            }
        }
        throw new IllegalArgumentException("Invalid coordinate " + c);
    }

    record CacheKey(Coord source, Coord dest) {

    }

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<String> lines = readAllLines(Path.of("src/main/resources/task21/21-task.input"));
        for (String line : lines) {
            long num = Long.parseLong(line.substring(0, line.length() - 1));
            long sum = bestAllKeys(line, ALL_ROBOTS);
            result += num * sum;
        }

        System.out.println(result);
    }

    record CoordAndKeys(Coord coord, String keys) {

    }

    static long bestAllKeys(String line, int robots) {
        if (robots == 0) {
            return line.length();
        }

        char[][] keypad = KEYPADS[ALL_ROBOTS - robots];

        long sum = 0L;
        char curr = 'A';
        for (int i = 0; i < line.length(); i++) {
            char dest = line.charAt(i);
            sum += bestMove(keypadCoord(curr, keypad), keypadCoord(dest, keypad), keypad, robots);
            curr = dest;
        }
        return sum;
    }

    static long bestMove(Coord source, Coord dest, char[][] keypad, int robots) {
        return caches[robots].computeIfAbsent(new CacheKey(source, dest), key -> {
                long result = Long.MAX_VALUE;
                Queue<CoordAndKeys> queue = new ArrayDeque<>();
                queue.add(new CoordAndKeys(source, ""));
                while (!queue.isEmpty()) {
                    CoordAndKeys coordAndKeys = queue.remove();
                    Coord cur = coordAndKeys.coord;
                    String keys = coordAndKeys.keys;

                    if (cur.equals(dest)) {
                        result = Math.min(result, bestAllKeys(keys + "A", robots - 1));
                    } else {
                        generateMoves(dest, cur, queue, keys, keypad);
                    }
                }
                return result;
            });
    }

    private static void generateMoves(Coord dest, Coord cur, Queue<CoordAndKeys> queue, String keys, char[][] keypad) {
        if (cur.x() < dest.x()) {
            addMoveIfValid(cur.relative(Direction.E), queue, keys + ">", keypad);
        }
        if (cur.x() > dest.x()) {
            addMoveIfValid(cur.relative(Direction.W), queue, keys + "<", keypad);
        }
        if (cur.y() < dest.y()) {
            addMoveIfValid(cur.relative(Direction.S), queue, keys + "v", keypad);
        }
        if (cur.y() > dest.y()) {
            addMoveIfValid(cur.relative(Direction.N), queue, keys + "^", keypad);
        }
    }

    private static void addMoveIfValid(Coord next, Queue<CoordAndKeys> queue, String keys, char[][] keypad) {
        if (next.value(keypad) != ' ') {
            queue.add(new CoordAndKeys(next, keys));
        }
    }
}
