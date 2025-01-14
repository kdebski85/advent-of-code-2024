package pl.krzysztofdebski.task12;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.inBounds;
import static pl.krzysztofdebski.utils.Utils.readTwoDimensionsCharArray;

public class Task12b {

    public static void main(String[] args) throws IOException {
        long result = 0;

        char[][] board = readTwoDimensionsCharArray(Path.of("src/main/resources/task12/12-task.input"));

        Integer[][] groups = new Integer[board.length][board[0].length];
        boolean[][] fenceLeft = new boolean[board.length][board[0].length];
        boolean[][] fenceRight = new boolean[board.length][board[0].length];
        boolean[][] fenceDown = new boolean[board.length][board[0].length];
        boolean[][] fenceUp = new boolean[board.length][board[0].length];

        List<Integer> groupArea = new ArrayList<>();

        int nextGroup = 0;
        List<Integer> groupFence = new ArrayList<>();

        for (int y = 0; y < board.length; y++) {
            char[] line = board[y];
            for (int x = 0; x < line.length; x++) {
                char c = line[x];
                boolean upSame = inBounds(x, y - 1, groups) && board[y - 1][x] == c;
                boolean leftSame = inBounds(x - 1, y, groups) && board[y][x - 1] == c;
                if (upSame && leftSame) {
                    int g1 = groups[y - 1][x];
                    int g2 = groups[y][x - 1];
                    if (g1 != g2) {
                        for (int y1 = 0; y1 <= y; y1++) {
                            Integer[] group = groups[y1];
                            for (int x1 = 0; x1 < group.length; x1++) {
                                if (group[x1] != null && group[x1] == g2) {
                                    group[x1] = g1;
                                }
                            }
                        }
                        groupArea.set(g1, groupArea.get(g1) + groupArea.get(g2));
                        groupArea.set(g2, 0);
                    }
                }

                if (upSame) {
                    int g = groups[y - 1][x];
                    groups[y][x] = g;
                    groupArea.set(g, groupArea.get(g) + 1);
                } else if (leftSame) {
                    int g = groups[y][x - 1];
                    groups[y][x] = g;
                    groupArea.set(g, groupArea.get(g) + 1);
                } else {
                    groupArea.add(1);
                    groupFence.add(0);
                    groups[y][x] = nextGroup;
                    nextGroup++;
                }
            }

        }

        for (int y = 0; y < board.length; y++) {
            char[] line = board[y];
            for (int x = 0; x < line.length; x++) {
                int g = groups[y][x];

                Integer f = groupFence.get(g);
                if (x == 0 || (inBounds(x - 1, y, groups) && groups[y][x - 1] != g)) {
                    fenceLeft[y][x] = true;
                    if (!(inBounds(x, y - 1, groups) && groups[y - 1][x] == g && fenceLeft[y - 1][x])) {
                        f = f + 1;
                    }
                }
                if (!inBounds(x + 1, y, groups) || (inBounds(x + 1, y, groups) && groups[y][x + 1] != g)) {
                    fenceRight[y][x] = true;
                    if (!(inBounds(x, y - 1, groups) && groups[y - 1][x] == g && fenceRight[y - 1][x])) {
                        f = f + 1;
                    }
                }
                if (!inBounds(x, y - 1, groups) || (inBounds(x, y - 1, groups) && groups[y - 1][x] != g)) {
                    fenceUp[y][x] = true;
                    if (!(inBounds(x - 1, y, groups) && groups[y][x - 1] == g && fenceUp[y][x - 1])) {
                        f = f + 1;
                    }
                }
                if (!inBounds(x, y + 1, groups) || (inBounds(x, y + 1, groups) && groups[y + 1][x] != g)) {
                    fenceDown[y][x] = true;
                    if (!(inBounds(x - 1, y, groups) && groups[y][x - 1] == g && fenceDown[y][x - 1])) {
                        f = f + 1;
                    }
                }
                groupFence.set(groups[y][x], f);
            }
        }

        for (int i = 0; i < groupArea.size(); i++) {
            result += ((long) groupArea.get(i)) * ((long) groupFence.get(i));
        }

        System.out.println(result);
    }
}
