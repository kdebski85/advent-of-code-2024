package pl.krzysztofdebski.utils;

import java.util.ArrayList;
import java.util.List;

import static pl.krzysztofdebski.utils.Utils.inBounds;

public class DistanceUtils {

    public static Integer[][] distances(int bx, int by, boolean[][] walls) {
        int ySize = walls.length;
        int xSize = walls[0].length;
        Integer[][] dist = new Integer[ySize][xSize];

        List<Coord> cur = new ArrayList<>();
        cur.add(new Coord(by, bx));
        dist[by][bx] = 0;

        while (!cur.isEmpty()) {
            List<Coord> next = new ArrayList<>();
            for (Coord point : cur) {
                int d = dist[point.y()][point.x()];

                for (Direction direction : Direction.CARDINAL_DIRECTIONS) {
                    Coord c = point.relative(direction);
                    int x2 = c.x();
                    int y2 = c.y();
                    if (inBounds(x2, y2, walls) && dist[y2][x2] == null && !walls[y2][x2]) {
                        dist[y2][x2] = d + 1;
                        next.add(new Coord(y2, x2));
                    }
                }
            }
            cur = next;
        }

        return dist;
    }
}
