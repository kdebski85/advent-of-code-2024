package pl.krzysztofdebski.task6;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Direction.right90;
import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Task6 {

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task6/6-task.input"));
        int width = lines.getFirst().length();
        int height = lines.size();
        boolean[][] obstacles = new boolean[width][height];
        boolean[][] visited = new boolean[width][height];
        int w = 0;
        int h = 0;
        Direction d = Direction.N;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                switch (c) {
                    case '#': obstacles[i][j] = true; break;
                    case '^': h = i; w = j; d = Direction.N; break;
                    case '>': h = i; w = j; d = Direction.E; break;
                    case '<': h = i; w = j; d = Direction.W; break;
                    case 'v': h = i; w = j; d = Direction.S; break;
                }
            }
        }

        Coord coord = new Coord(h, w);
        while (true) {
            visited[coord.y()][coord.x()] = true;

            Coord newCoord = coord.relative(d);
            if (!inBounds(newCoord, obstacles)) {
                break;
            }

            if (newCoord.value(obstacles)) {
                d = right90(d);
            } else {
                coord = newCoord;
            }
        }

        long result = 0;
        for (boolean[] booleans : visited) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    result++;
                }
            }
        }

        System.out.println(result);
    }
}
