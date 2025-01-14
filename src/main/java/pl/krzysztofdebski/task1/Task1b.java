package pl.krzysztofdebski.task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Task1b {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner myInput = new Scanner(new File("src/main/resources/task1/1-task.input"));
        List<Integer> left = new ArrayList<>();
        Map<Integer, Integer> rights = new HashMap<>();

        while (myInput.hasNext()) {
            left.add(myInput.nextInt());

            int right = myInput.nextInt();
            rights.compute(right, (k, v) -> v == null ? 1 : v + 1);
        }

        long sum = 0;
        for (int l : left) {
            sum += Math.abs(l * Objects.requireNonNullElse(rights.get(l), 0));
        }

        System.out.println(sum);
    }
}
