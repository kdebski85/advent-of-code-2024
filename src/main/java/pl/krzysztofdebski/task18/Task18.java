package pl.krzysztofdebski.task18;

import pl.krzysztofdebski.utils.DistanceUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.ParsingUtils.intsArray;

public class Task18 {

    public static void main(String[] args) throws IOException {
        int size = 71;
        List<String> lines = readAllLines(Path.of("src/main/resources/task18/18-task-1.input"));
        boolean[][] walls = new boolean[size][size];

        for (String line : lines) {
            int[] split = intsArray(line);
            walls[split[1]][split[0]] = true;
        }

        Integer[][] dist = DistanceUtils.distances(0, 0, walls);

        System.out.println(dist[size - 1][size - 1]);
    }
}
