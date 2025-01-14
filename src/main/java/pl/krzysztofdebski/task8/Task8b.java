package pl.krzysztofdebski.task8;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import pl.krzysztofdebski.utils.Coord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.MathUtils.gcd;
import static pl.krzysztofdebski.utils.Utils.inBounds;

public class Task8b {

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<String> lines = readAllLines(Path.of("src/main/resources/task8/8-task.input"));
        Multimap<Character, Coord> locations = ArrayListMultimap.create();
        int xMax = lines.getFirst().length() - 1;
        int yMax = lines.size() - 1;

        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c1 = line.charAt(x);
                if (c1 != '.') {
                    locations.put(c1, new Coord(y, x));
                }
            }
        }

        boolean[][] anti = new boolean[yMax + 1][xMax + 1];

        for (Character c : locations.keySet()) {
            for (Coord loc : locations.get(c)) {
                for (Coord loc2 : locations.get(c)) {
                    if (!loc.equals(loc2)) {
                        int difx = Math.abs(loc.x() - loc2.x());
                        int dify = Math.abs(loc.y() - loc2.y());
                        int gcd = gcd(difx, dify);

                        int stepx = (loc.x() - loc2.x()) / gcd;
                        int stepy = (loc.y() - loc2.y()) / gcd;

                        for (int i = -100; i < 100; i++) {
                            int nx = loc.x() + i * stepx;
                            int ny = loc.y() + i * stepy;

                            if (inBounds(nx, ny, anti) && !anti[ny][nx]) {
                                anti[ny][nx] = true;
                                result++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(result);
    }
}
