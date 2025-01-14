package pl.krzysztofdebski.task14;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.Files.readAllLines;

public class Task14 {

    static Pattern patternPrize = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");

    static class Robot {
        int px; int py; int vx; int vy;
    };
    //p=95,29 v=-82,-32

    public static void main(String[] args) throws IOException {
        long result = 0;
        //101 tiles wide and 103 tiles tall.

        // 11 tiles wide and 7 tiles tall
        int H = 103;
        int W = 101;
      /*  int H = 7;
        int W = 11;*/
        int HM = H / 2;
        int WM = W / 2;

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

            for (int i = 0; i < 100; i++) {
                robot.px = (robot.px + robot.vx + W) % W;
                robot.py = (robot.py + robot.vy + H) % H;
            }
        }

        long upLeft = 0;
        long upRight = 0;
        long downRight = 0;
        long downLeft = 0;

        for (Robot robot : robots) {
            int px = robot.px;
            int py = robot.py;
            if (px < WM && py < HM) {
                upLeft++;
            } else if (px > WM && py > HM) {
                downRight++;
            } else if (px > WM && py < HM) {
                downLeft++;
            } else if (px < WM && py > HM) {
                upRight++;
            }
        }

        System.out.println(upLeft * upRight * downRight * downLeft);
    }
}
