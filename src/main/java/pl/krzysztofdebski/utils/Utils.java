package pl.krzysztofdebski.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Utils {

    public static <T> boolean inBounds(Coord coord, T[][] array) {
        return inBounds(coord.y(), coord.x(), array);
    }

    public static <T> boolean inBounds(int x, int y, T[][] array) {
        return x >= 0 && y >= 0 && y < array.length && x < array[y].length;
    }

    public static boolean inBounds(Coord coord, int[][] array) {
        return inBounds(coord.y(), coord.x(), array);
    }

    public static boolean inBounds(int x, int y, int[][] array) {
        return x >= 0 && y >= 0 && y < array.length && x < array[y].length;
    }

    public static boolean inBounds(Coord coord, char[][] array) {
        return inBounds(coord.y(), coord.x(), array);
    }

    public static boolean inBounds(int x, int y, char[][] array) {
        return x >= 0 && y >= 0 && y < array.length && x < array[y].length;
    }

    public static boolean inBounds(Coord coord, boolean[][] array) {
        return inBounds(coord.y(), coord.x(), array);
    }

    public static boolean inBounds(int x, int y, boolean[][] array) {
        return x >= 0 && y >= 0 && y < array.length && x < array[y].length;
    }

    public static int[][] readOneDigitTwoDimensionsArray(Path path) {
        List<String> lines;
        try {
            lines = readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[][] result = new int[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                result[i][j] = Integer.parseInt(line.charAt(j) + "");
            }
        }
        return result;
    }

    public static char[][] readTwoDimensionsCharArray(Path path) {
        List<String> lines;
        try {
            lines = readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[][] result = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                result[i][j] = line.charAt(j);
            }
        }
        return result;
    }

    public static int[] readOneDigitOneDimensionArray(Path path) {
        String line;
        try {
            List<String> lines = readAllLines(path);
            line = lines.getFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int[] result = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
            result[i] = Integer.parseInt(line.charAt(i) + "");
        }
        return result;
    }

    public static List<List<String>> partitionByNewLines(List<String> lines) {
        List<List<String>> result = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                if (!current.isEmpty()) {
                    result.add(current);
                }
                current = new ArrayList<>();
            } else {
                current.add(line);
            }
        }
        if (!current.isEmpty()) {
            result.add(current);
        }
        return result;
    }

    public static long sumInts(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    public static long sumInts(Collection<Integer> collection) {
        long sum = 0;
        for (int value : collection) {
            sum += value;
        }
        return sum;
    }
}
