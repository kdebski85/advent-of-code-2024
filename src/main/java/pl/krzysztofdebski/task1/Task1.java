package pl.krzysztofdebski.task1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner myInput = new Scanner(new File("src/main/resources/task1/1-task.input"));
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        while (myInput.hasNext()) {
            left.add(myInput.nextInt());
            right.add(myInput.nextInt());
        }

        Collections.sort(left);
        Collections.sort(right);

        long sum = 0;
        for (int i = 0; i < left.size(); i++) {
            sum += Math.abs(left.get(i) - right.get(i));
        }

        System.out.println(sum);
    }
}
