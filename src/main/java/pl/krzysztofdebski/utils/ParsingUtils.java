package pl.krzysztofdebski.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParsingUtils {

    public static List<Integer> ints(String s) {
        Scanner sc = new Scanner(s);
        List<Integer> result = new ArrayList<>();
        String number;
        while ((number = sc.findInLine("-?\\d+")) != null) {
            result.add(Integer.parseInt(number));
        }
        return result;
    }

    public static Integer[] integerArray(String s) {
        return ints(s).toArray(Integer[]::new);
    }

    public static int[] intsArray(String s) {
        List<Integer> ints = ints(s);
        int[] result = new int[ints.size()];
        for (int i = 0; i < ints.size(); i++) {
            result[i] = ints.get(i);
        }

        return result;
    }

    public static List<Long> longs(String s) {
        Scanner sc = new Scanner(s);
        List<Long> result = new ArrayList<>();
        String number;
        while ((number = sc.findInLine("-?\\d+")) != null) {
            result.add(Long.parseLong(number));
        }
        return result;
    }

    public static long[] longsArray(String s) {
        List<Long> longs = longs(s);
        long[] result = new long[longs.size()];
        for (int i = 0; i < longs.size(); i++) {
            result[i] = longs.get(i);
        }

        return result;
    }
}
