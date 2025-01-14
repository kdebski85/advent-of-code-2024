package pl.krzysztofdebski.task16;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.task16.Task16.Dir.E;
import static pl.krzysztofdebski.task16.Task16.Dir.N;
import static pl.krzysztofdebski.task16.Task16.Dir.S;
import static pl.krzysztofdebski.task16.Task16.Dir.W;
import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Task16 {

    enum Dir {N, E, S, W}

    record Point(int x, int y) {

    }

    public static void main(String[] args) throws IOException {
        long result = 0;

        List<String> lines = readAllLines(Path.of("src/main/resources/task16/16-task.input"));
        boolean[][] walls = new boolean[lines.size()][lines.getFirst().length()];
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                walls[y][x] = line.charAt(x) == '#';
            }
        }

        int px = 1;
        int py = walls.length - 2;
        Dir dir = E;
        Set<Point> v = new HashSet<>();
        long[][] mins = new long[lines.size()][lines.getFirst().length()];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                mins[y][x] = Long.MAX_VALUE;
            }
        }
        v.add(new Point(px, py));

        result = best(walls, mins, px, py, dir, v, false, 0L);

        System.out.println(result);
    }

    static long best(boolean[][] walls, long[][] mins, int px, int py, Dir dir, Set<Point> visited, boolean turned, long soFar) {
        if (px == walls[0].length - 2 && py == 1) {
            return soFar;
        }
        if (mins[py][px] > soFar) {
            mins[py][px] = soFar;
        }


        switch (dir) {
            case N -> {
                return Math.min(
                    Math.min(
                        Math.min(
                            inBounds(px, py - 1, walls) && !walls[py-1][px] && !visited.contains(new Point(px, py - 1)) && mins[py - 1][px] > soFar + 1  ? best(walls, mins, px, py - 1, dir, newVisited(visited, px, py - 1),
                                false, soFar + 1) : Long.MAX_VALUE,
                            !turned ? best(walls, mins, px, py, E, visited, true, soFar + 1000) : Long.MAX_VALUE
                        ),
                        !turned ? best(walls, mins, px, py, W, visited, true, soFar + 1000) : Long.MAX_VALUE),
                    !turned ? best(walls, mins, px, py, S, visited, true, soFar + 2000) : Long.MAX_VALUE);
            }
            case S -> {
                return Math.min(
                    Math.min(
                        Math.min(
                            inBounds(px, py + 1, walls) && !walls[py+1][px] && !visited.contains(new Point(px, py + 1)) && mins[py + 1][px] > soFar + 1   ? best(walls, mins, px, py + 1, dir, newVisited(visited, px, py + 1),
                                false, soFar + 1) : Long.MAX_VALUE,
                            !turned ? best(walls, mins, px, py, E, visited, true, soFar + 1000) : Long.MAX_VALUE
                        ),
                        !turned ? best(walls, mins,  px, py, W, visited, true, soFar + 1000) : Long.MAX_VALUE),
                    !turned ? best(walls, mins,  px, py, N, visited, true, soFar + 2000) : Long.MAX_VALUE);
            }
            case W -> {
                return Math.min(
                    Math.min(
                        Math.min(
                            inBounds(px - 1, py, walls) && !walls[py][px-1] && !visited.contains(new Point(px - 1, py))  && mins[py][px - 1] > soFar + 1 ? best(walls, mins, px - 1, py, dir, newVisited(visited, px - 1, py),
                                false, soFar + 1) : Long.MAX_VALUE,
                            !turned ? best(walls, mins,  px, py, N, visited, true, soFar + 1000) : Long.MAX_VALUE
                        ),
                        !turned ? best(walls, mins, px, py, S, visited, true, soFar + 1000) : Long.MAX_VALUE),
                    !turned ? best(walls, mins, px, py, E, visited, true, soFar + 2000) : Long.MAX_VALUE);
            }
            case E -> {
                return Math.min(
                    Math.min(
                        Math.min(
                            inBounds(px + 1, py, walls) && !walls[py][px+1] && !visited.contains(new Point(px + 1, py)) && mins[py][px + 1] > soFar + 1 ? best(walls, mins, px + 1, py, dir, newVisited(visited, px + 1, py),
                                false, soFar + 1) : Long.MAX_VALUE,
                            !turned ? best(walls, mins, px, py, N, visited, true, soFar + 1000) : Long.MAX_VALUE
                        ),
                        !turned ? best(walls, mins, px, py, S, visited, true, soFar + 1000) : Long.MAX_VALUE),
                    !turned ? best(walls, mins,  px, py, W, visited, true, soFar + 2000) : Long.MAX_VALUE);
            }
        }

        return soFar;
    }

    static Set<Point> newVisited(Set<Point> v, int px, int py) {
        Set<Point> visited = new HashSet<>(v);
        visited.add(new Point(px, py));
        return visited;
    }
}
