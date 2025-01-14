package pl.krzysztofdebski.task5;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Task5 {

    public static void main(String[] args) throws IOException {
        long result = 0;
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
                result += Integer.parseInt(split[split.length / 2]);
            }
        }
        System.out.println(result);
    }
}
