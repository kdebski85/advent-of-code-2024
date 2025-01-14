package pl.krzysztofdebski.task14;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task14b {

    static Pattern patternPrize = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");

    static class Robot {
        int px;
        int py;
        int vx;
        int vy;
    }

    record Point(int x, int y) {

    }

    public static void main(String[] args) throws IOException {
        //101 tiles wide and 103 tiles tall.

        // 11 tiles wide and 7 tiles tall
        int H = 103;
        int W = 101;
      /*  int H = 7;
        int W = 11;*/

        List<Robot> robots = new ArrayList<>();
        List<String> lines = readAllLines(Path.of("src/main/resources/task14/14-task.input"));
        for (String line : lines) {
            Matcher matcher = patternPrize.matcher(line);
            if (!matcher.matches()) {
                throw new IllegalArgumentException();
            }
            Robot robot = new Robot();
            robots.add(robot);
            robot.px = Integer.parseInt(matcher.group(1));
            robot.py = Integer.parseInt(matcher.group(2));
            robot.vx = Integer.parseInt(matcher.group(3));
            robot.vy = Integer.parseInt(matcher.group(4));
        }

        for (int i = 0; i < 10000; i++) {
            Set<Point> points = new HashSet<>();

            for (Robot robot : robots) {
                robot.px = (robot.px + robot.vx + W) % W;
                robot.py = (robot.py + robot.vy + H) % H;
                points.add(new Point(robot.px, robot.py));
            }

            int score = 0;
            for (Robot robot : robots) {
                if (points.contains(new Point(robot.px - 1, robot.py - 1))
                    || points.contains(new Point(robot.px + 1, robot.py - 1))) {
                    //two diagonal stars - tree indicator
                    score++;
                }
            }

            if (score > 160) {
                System.out.println(i + 1);
                for (int y = 0; y < H; y++) {
                    for (int x = 0; x < W; x++) {
                        if (points.contains(new Point(x, y))) {
                            System.out.print('*');
                        } else {
                            System.out.print('.');
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}
