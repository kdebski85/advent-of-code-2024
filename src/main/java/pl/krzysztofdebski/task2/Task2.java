package pl.krzysztofdebski.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Task2 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner myInput = new Scanner(new File("src/main/resources/task2/2-task.input"));
        int safe = 0;
        mainLoop: while (myInput.hasNextLine()) {
            String line = myInput.nextLine();
            Scanner lineScanner = new Scanner(line);

            Integer last = null;
            Boolean desc = null;

            while (lineScanner.hasNextInt()) {
                int number = lineScanner.nextInt();
                if (last != null) {
                    int abs = Math.abs(last - number);
                    //  Any two adjacent levels differ by at least one and at most three.
                    if (abs < 1 || abs > 3) {
                        continue mainLoop;
                    }
                    int diff = last - number;
                    if (desc == null) {
                        desc = diff > 0;
                    } else {
                        if (desc ^ (diff > 0)) {
                            continue mainLoop;
                        }
                    }
                }
                last = number;
            }
            safe++;
        }

        System.out.println(safe);
    }
}
