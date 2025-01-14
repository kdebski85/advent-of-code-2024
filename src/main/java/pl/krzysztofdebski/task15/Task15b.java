package pl.krzysztofdebski.task15;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task15b {

    record Point(int x, int y) {

    }


    public static void main(String[] args) throws IOException {
        long result = 0;
        boolean[][] walls;
        boolean[][] crates;
        boolean[][] cratesLeft;

        List<List<String>> data = partitionByNewLines(readAllLines(Path.of("src/main/resources/task15/15-task.input")));
        List<String> maze = data.getFirst();
        List<String> movements = data.get(1);

        walls = new boolean[maze.size()][2 * maze.getFirst().length()];
        crates = new boolean[maze.size()][2 * maze.getFirst().length()];
        cratesLeft = new boolean[maze.size()][2 * maze.getFirst().length()];

        int px = 0;
        int py = 0;

        for (int y = 0; y < maze.size(); y++) {
            String line = maze.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                if (c == '#') {
                    walls[y][2 * x] = true;
                    walls[y][2 * x + 1] = true;
                } else {
                    if (c == 'O') {
                        crates[y][2 * x] = true;
                        cratesLeft[y][2 * x] = true;
                        crates[y][2 * x + 1] = true;
                    } else if (c == '@') {
                        px = 2 * x;
                        py = y;
                    }
                }
            }
        }
        //<^^>>>vv<v>>v<<

        for (String line : movements) {
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

                            boolean left = true;

                            for (int j = px - cr - 1; j < px - 1; j++) {
                                cratesLeft[py][j] = left;
                                left ^= true;
                            }
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

                            boolean left = true;

                            for (int j = px + 2; j <= px + cr + 1; j++) {
                                cratesLeft[py][j] = left;
                                left ^= true;
                            }
                        }

                        if (!walls[py][px + 1] && !crates[py][px + 1]) {
                            px++;
                        }

                        break;
                    case '^': {
                        List<Point> moved = new ArrayList<>();
                        Queue<Point> toMoveCheck = new LinkedList<>();
                        if (crates[py - 1][px]) {
                            if (cratesLeft[py - 1][px]) {
                                toMoveCheck.add(new Point(px, py - 1));
                                toMoveCheck.add(new Point(px + 1, py - 1));
                            } else {
                                toMoveCheck.add(new Point(px - 1, py - 1));
                                toMoveCheck.add(new Point(px, py - 1));
                            }
                        }

                        boolean possible = true;

                        while (!toMoveCheck.isEmpty()) {
                            Point p = toMoveCheck.poll();
                            if (crates[p.y][p.x]) {
                                if (walls[p.y - 1][p.x]) {
                                    possible = false;
                                    break;
                                }
                                moved.add(p);

                                boolean left = cratesLeft[p.y][p.x];

                                if (left) {
                                    toMoveCheck.add(new Point(p.x, p.y - 1));
                                    if (crates[p.y - 1][p.x] && !cratesLeft[p.y - 1][p.x]) {
                                        toMoveCheck.add(new Point(p.x - 1, p.y - 1));
                                    }
                                    toMoveCheck.add(new Point(p.x + 1, p.y - 1));
                                    if (crates[p.y - 1][p.x + 1] && cratesLeft[p.y - 1][p.x + 1]) {
                                        toMoveCheck.add(new Point(p.x + 2, p.y - 1));
                                    }
                                } else {
                                    toMoveCheck.add(new Point(p.x - 1, p.y - 1));
                                    if (crates[p.y - 1][p.x - 1] && !cratesLeft[p.y - 1][p.x - 1]) {
                                        toMoveCheck.add(new Point(p.x - 2, p.y - 1));
                                    }
                                    toMoveCheck.add(new Point(p.x, p.y - 1));
                                    if (crates[p.y - 1][p.x] && cratesLeft[p.y - 1][p.x]) {
                                        toMoveCheck.add(new Point(p.x + 1, p.y - 1));
                                    }
                                }
                            }
                        }

                        if (possible) {
                            for (int j = moved.size() - 1; j >= 0; j--) {
                                Point p = moved.get(j);
                                crates[p.y - 1][p.x] = true;
                                cratesLeft[p.y - 1][p.x] = cratesLeft[p.y][p.x];
                                crates[p.y][p.x] = false;
                            }
                        }

                        if (!walls[py - 1][px] && !crates[py - 1][px]) {
                            py--;
                        }
                    }

                    break;
                    case 'v': {
                        List<Point> moved = new ArrayList<>();
                        Queue<Point> toMoveCheck = new LinkedList<>();
                        if (crates[py + 1][px]) {
                            if (cratesLeft[py + 1][px]) {
                                toMoveCheck.add(new Point(px, py + 1));
                                toMoveCheck.add(new Point(px + 1, py + 1));
                            } else {
                                toMoveCheck.add(new Point(px - 1, py + 1));
                                toMoveCheck.add(new Point(px, py + 1));
                            }
                        }

                        boolean possible = true;

                        while (!toMoveCheck.isEmpty()) {
                            Point p = toMoveCheck.poll();
                            if (crates[p.y][p.x]) {
                                if (walls[p.y + 1][p.x]) {
                                    possible = false;
                                    break;
                                }
                                moved.add(p);

                                boolean left = cratesLeft[p.y][p.x];
                                if (left) {
                                    toMoveCheck.add(new Point(p.x, p.y + 1));
                                    if (crates[p.y + 1][p.x] && !cratesLeft[p.y + 1][p.x]) {
                                        toMoveCheck.add(new Point(p.x - 1, p.y + 1));
                                    }
                                    toMoveCheck.add(new Point(p.x + 1, p.y + 1));
                                    if (crates[p.y + 1][p.x + 1] && cratesLeft[p.y + 1][p.x + 1]) {
                                        toMoveCheck.add(new Point(p.x + 2, p.y + 1));
                                    }
                                } else {
                                    toMoveCheck.add(new Point(p.x - 1, p.y + 1));
                                    if (crates[p.y + 1][p.x - 1] && !cratesLeft[p.y + 1][p.x - 1]) {
                                        toMoveCheck.add(new Point(p.x - 2, p.y + 1));
                                    }
                                    toMoveCheck.add(new Point(p.x, p.y + 1));
                                    if (crates[p.y + 1][p.x] && cratesLeft[p.y + 1][p.x]) {
                                        toMoveCheck.add(new Point(p.x + 1, p.y + 1));
                                    }
                                }
                            }
                        }

                        if (possible) {
                            for (int j = moved.size() - 1; j >= 0; j--) {
                                Point p = moved.get(j);
                                crates[p.y + 1][p.x] = true;
                                cratesLeft[p.y + 1][p.x] = cratesLeft[p.y][p.x];
                                crates[p.y][p.x] = false;
                            }
                        }

                        if (!walls[py + 1][px] && !crates[py + 1][px]) {
                            py++;
                        }
                    }

                    break;
                }
            }
        }

        for (int y = 0; y < crates.length; y++) {
            for (int x = 0; x < crates[y].length; x++) {
                if (crates[y][x] && cratesLeft[y][x]) {
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

    static void print(boolean[][] crates, boolean[][] cratesLeft, boolean[][] walls, int px, int py) {
        for (int y = 0; y < crates.length; y++) {
            for (int x = 0; x < crates[y].length; x++) {
                if (crates[y][x]) {
                    System.out.print(cratesLeft[y][x] ? '[' : ']');
                } else if (walls[y][x]) {
                    System.out.print('#');
                } else if (x == px && y == py) {
                    System.out.print('@');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }
}
