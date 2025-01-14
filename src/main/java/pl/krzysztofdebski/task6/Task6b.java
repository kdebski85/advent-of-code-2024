package pl.krzysztofdebski.task6;

import pl.krzysztofdebski.utils.Coord;
import pl.krzysztofdebski.utils.Direction;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Direction.right90;
import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Task6b {

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task6/6-task.input"));
        int width = lines.getFirst().length();
        int height = lines.size();
        boolean[][] obstacles = new boolean[width][height];

        int wb = 0;
        int hb = 0;
        Direction db = Direction.N;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                switch (c) {
                    case '#': obstacles[i][j] = true; break;
                    case '^': hb = i; wb = j; db = Direction.N; break;
                    case '>': hb = i; wb = j; db = Direction.E; break;
                    case '<': hb = i; wb = j; db = Direction.W; break;
                    case 'v': hb = i; wb = j; db = Direction.S; break;
                }
            }
        }

        long result = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (obstacles[y][x] || (y == hb && x == wb)) {
                    continue;
                }

                Direction d = db;
                obstacles[y][x] = true;
                int steps = 0;

                Coord coord = new Coord(hb, wb);
                while (true) {
                    steps++;
                    if (steps > height * width * 4) {
                        result++;
                        break;
                    }

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

                obstacles[y][x] = false;
            }
        }

        System.out.println(result);
    }
}
