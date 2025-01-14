package pl.krzysztofdebski.task15;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task15 {

    public static void main(String[] args) throws IOException {
        long result = 0;
        boolean[][] walls;
        boolean[][] crates;

        List<List<String>> data = partitionByNewLines(readAllLines(Path.of("src/main/resources/task15/15-task.input")));
        List<String> maze = data.getFirst();
        List<String> movements = data.get(1);

        walls = new boolean[maze.size()][maze.getFirst().length()];
        crates = new boolean[maze.size()][maze.getFirst().length()];

        int px = 0;
        int py = 0;

        for (int y = 0; y < maze.size(); y++) {
            String line = maze.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '#') {
                    walls[y][x] = true;
                } else {
                    if (c == 'O') {
                        crates[y][x] = true;
                    } else if (c == '@') {
                        px = x;
                        py = y;
                    }
                }
            }
        }
        //<^^>>>vv<v>>v<<

        for (String line: movements) {
            for (char c : line.toCharArray()) {
                int cr = 0;
                switch (c) {
                    case '<':
                        while (crates[py][px - cr - 1]) {
                            cr++;
                        }
                        if (cr > 0 && !walls[py][px - cr - 1]) {
                            crates[py][px - cr - 1] = true;
                            crates[py][px - 1] = false;
                        }

                        if (!walls[py][px - 1] && !crates[py][px - 1]) {
                            px--;
                        }

                        break;
                    case '>':
                        while (crates[py][px + cr + 1]) {
                            cr++;
                        }
                        if (cr > 0 && !walls[py][px + cr + 1]) {
                            crates[py][px + cr + 1] = true;
                            crates[py][px + 1] = false;
                        }

                        if (!walls[py][px + 1] && !crates[py][px + 1]) {
                            px++;
                        }

                        break;
                    case '^':
                        while (crates[py - cr - 1][px]) {
                            cr++;
                        }
                        if (cr > 0 && !walls[py - cr - 1][px]) {
                            crates[py - cr - 1][px] = true;
                            crates[py - 1][px] = false;
                        }

                        if (!walls[py - 1][px] && !crates[py - 1][px]) {
                            py--;
                        }

                        break;
                    case 'v':
                        while (crates[py + cr + 1][px]) {
                            cr++;
                        }

                        if (cr > 0 && !walls[py + cr + 1][px]) {
                            crates[py + cr + 1][px] = true;
                            crates[py + 1][px] = false;
                        }

                        if (!walls[py + 1][px] && !crates[py + 1][px]) {
                            py++;
                        }

                        break;
                }

            }
        }

        for (int y = 0; y < crates.length; y++) {
            for (int x = 0; x < crates[y].length; x++) {
                if (crates[y][x]) {
                    result += 100L * y + x;
                }
            }
        }

/*##########
#.O.O.OOO#
#........#
#OO......#
#OO@.....#
#O#.....O#
#O.....OO#
#O.....OO#
#OO....OO#
##########*/

        System.out.println(result);
    }
}
