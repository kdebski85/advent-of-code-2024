package pl.krzysztofdebski.task16;

import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Direction.left90;
import static pl.krzysztofdebski.utils.Direction.right90;
import static pl.krzysztofdebski.utils.Utils.readTwoDimensionsCharArray;


public class Task16b {

    static Map<Direction, Integer> directionMap = new HashMap<>();
    static {
        for (int i = 0; i < Direction.CARDINAL_DIRECTIONS.size(); i++) {
            directionMap.put(Direction.CARDINAL_DIRECTIONS.get(i), i);
        }
    }

    record Move(
        int r,
        int c,
        Direction direction,
        int score
    ) implements Comparable<Move> {

        @Override
        public int compareTo(Move o) {
            return score - o.score;
        }
    }

    static char[][] map;
    static boolean[][] seats;
    static int[][][] minDistance;

    public static void main(String[] args) throws IOException {
        map = readTwoDimensionsCharArray(Path.of("src/main/resources/task16/16-task.input"));
        seats = new boolean[map.length][map[0].length];
        minDistance = new int[map.length][map[0].length][4];

        PriorityQueue<Move> pq = new PriorityQueue<>();
        // start row, start column
        int sr = 0, sc = 0;

        for (int r = 0; r < minDistance.length; r++) {
            for (int c = 0; c < minDistance[0].length; c++) {
                for (int d = 0; d < 4; d++) {
                    minDistance[r][c][d] = Integer.MAX_VALUE;
                }
                if (map[r][c] == 'S') {
                    sr = r;
                    sc = c;
                }
            }
        }

        pq.add(new Move(sr, sc, Direction.E, 0));

        int score = 0;

        while (!pq.isEmpty()) {
            Move bestMove = pq.poll();
            int r = bestMove.r;
            int c = bestMove.c;
            Direction direction = bestMove.direction;
            score = bestMove.score;
            if (map[r][c] == 'E') {
                break;
            }
            if (map[r][c] == '#') {
                continue;
            }
            if (score >= minDistance[r][c][directionMap.get(direction)]) {
                continue;
            }
            minDistance[r][c][directionMap.get(direction)] = score;
            pq.add(new Move(r, c, right90(direction), score + 1000));
            pq.add(new Move(r, c, left90(direction), score + 1000));
            pq.add(new Move(r + direction.Δr(), c + direction.Δc(), direction, score + 1));
        }

        System.out.println(score);
        int numSeats = 0;
        markBestPaths(sr, sc, Direction.E, 0, score);
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[r].length; c++) {
                if (seats[r][c]) {
                    System.out.print("0");
                    numSeats++;
                } else {
                    System.out.print(map[r][c]);
                }
            }
            System.out.println();
        }
        System.out.println(numSeats);
    }


    static boolean markBestPaths(int r, int c, Direction direction, int score, int bestScore) {
        if (map[r][c] == '#') {
            return false;
        }
        if (score == bestScore && map[r][c] == 'E') {
            seats[r][c] = true;
            return true;
        }
        if (score >= bestScore) {
            return false;
        }
        if (score > minDistance[r][c][directionMap.get(direction)]) {
            return false;
        }
        if (minDistance[r][c][directionMap.get(direction)] != score) {
            throw new IllegalArgumentException(); //should never happen
        }

        //Not shortcutting alternative `|` is used here to mark all paths
        if (markBestPaths(r + direction.Δr(), c + direction.Δc(), direction, score + 1, bestScore)
            | markBestPaths(r, c, left90(direction), score + 1000, bestScore)
            | markBestPaths(r, c, right90(direction), score + 1000, bestScore)) {
            seats[r][c] = true;
            return true;
        }

        return false;
    }
}
