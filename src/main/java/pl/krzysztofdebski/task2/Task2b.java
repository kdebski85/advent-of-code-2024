package pl.krzysztofdebski.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task2b {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner myInput = new Scanner(new File("src/main/resources/task2/2-task.input"));
        int safe = 0;
        while (myInput.hasNextLine()) {
            String line = myInput.nextLine();
            Scanner lineScanner = new Scanner(line);

            List<Integer> numbers = new ArrayList<>();
            while (lineScanner.hasNextInt()) {
                numbers.add(lineScanner.nextInt());
            }
            if (isSafe(numbers)) {
                safe++;
            }
        }

        System.out.println(safe);
    }

    private static boolean isSafe(List<Integer> numbers) {
        if (isSafe2(numbers)) {
            return true;
        }

        for (int i = 0; i < numbers.size(); i++) {
            List<Integer> n = new ArrayList<>(numbers);
            n.remove(i);
            if (isSafe2(n)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSafe2(List<Integer> numbers) {
        Integer last = null;
        Boolean desc = null;
        for (Integer number : numbers) {
            if (last != null) {
                int abs = Math.abs(last - number);
                //  Any two adjacent levels differ by at least one and at most three.
                if (abs < 1 || abs > 3) {
                    return false;
                }
                int diff = last - number;
                if (desc == null) {
                    desc = diff > 0;
                } else {
                    if (desc ^ (diff > 0)) {
                        return false;
                    }
                }
            }
            last = number;
        }

        return true;
    }
}
