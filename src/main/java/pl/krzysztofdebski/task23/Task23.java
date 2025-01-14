package pl.krzysztofdebski.task23;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static java.nio.file.Files.readAllLines;

public class Task23 {

    public static void main(String[] args) throws IOException {
        Set<Set<String>> rel = new HashSet<>();

        List<String> lines = readAllLines(Path.of("src/main/resources/task23/23-task.input"));
        Multimap<String, String> conn = ArrayListMultimap.create();
        for (String line : lines) {
            String c1 = line.substring(0, 2);
            String c2 = line.substring(3);
            conn.put(c1, c2);
            conn.put(c2, c1);
        }

        for (Entry<String, Collection<String>> e : conn.asMap().entrySet()) {
            String c1 = e.getKey();
            Collection<String> c2 = e.getValue();
            if (c1.startsWith("t")) {
                for (String s : c2) {
                    Collection<String> strings = conn.get(s);
                    for (String c3 : strings) {
                        if (!c3.equals(c1) && conn.get(c3).contains(c1)) {
                            System.out.println(c1 + "-" + s + "-" + c3);
                            rel.add(Set.of(c1, s, c3));
                        }
                    }
                }
            }
        }

        System.out.println(rel.size());
    }
}
