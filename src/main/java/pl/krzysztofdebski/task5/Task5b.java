package pl.krzysztofdebski.task5;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task5b {

    public static void main(String[] args) throws IOException {
        long result = 0;
        List<List<Integer>> invalid = new ArrayList<>();
        Multimap<Integer, Integer> rules = ArrayListMultimap.create();
        main: for (String line : Files.readAllLines(Path.of("src/main/resources/task5/5-task.input"))) {
            if (line.contains("|")) {
                String[] split = line.split("\\|");
                int before = Integer.parseInt(split[0]);
                int after = Integer.parseInt(split[1]);
                rules.put(after, before);
            } else if (!line.isEmpty()) {
                Set<Integer> alreadyPrinted = new HashSet<>();
                Set<Integer> cannotBePrinted = new HashSet<>();
                String[] split = line.split(",");
                for (String string : split) {
                    int parsed = Integer.parseInt(string);
                    if (cannotBePrinted.contains(parsed)) {
                        List<Integer> parsedArray = new ArrayList<>(split.length);
                        for (int j = 0; j < split.length; j++) {
                            parsedArray.add(Integer.parseInt(split[j]));
                        }
                        invalid.add(parsedArray);
                        continue main;
                    }
                    Collection<Integer> r = rules.get(parsed);
                    for (Integer mustBeBefore : r) {
                        if (!alreadyPrinted.contains(mustBeBefore)) {
                            cannotBePrinted.add(mustBeBefore);
                        }
                    }
                    alreadyPrinted.add(parsed);
                }
            }
        }

        for (List<Integer> numbers : invalid) {
            boolean updated;
            do {
                updated = false;
                for (int i = 0; i < numbers.size(); i++) {
                    Integer number = numbers.get(i);
                    Collection<Integer> r = rules.get(number);
                    int maxIndex = -1;
                    for (Integer mustBeBefore : r) {
                        maxIndex = Math.max(maxIndex, numbers.indexOf(mustBeBefore));
                    }
                    if (maxIndex > i) {
                        numbers.remove(i);
                        numbers.add(maxIndex, number);
                        updated = true;
                    }
                }
            } while (updated);

            result += numbers.get(numbers.size() / 2);
        }


        System.out.println(result);
    }
}
