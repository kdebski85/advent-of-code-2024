package pl.krzysztofdebski.task18;

import pl.krzysztofdebski.utils.DistanceUtils;
import pl.krzysztofdebski.utils.ParsingUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Task18b {

    public static void main(String[] args) throws IOException {
        int size = 71;

        List<String> lines = readAllLines(Path.of("src/main/resources/task18/18-task.input"));

        boolean[][] walls = new boolean[size][size];
        for (int i = 0; i < 4000; i++) {
            int[] split = ParsingUtils.intsArray(lines.get(i));
            walls[split[1]][split[0]] = true;

            Integer[][] dist = DistanceUtils.distances(0, 0, walls);

            if (dist[size - 1][size - 1] == null) {
                System.out.println(i);
                System.out.println(lines.get(i));
                break;
            }
        }
    }
}
