package pl.krzysztofdebski.task23;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

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
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;

public class Task23b {

    public static void main(String[] args) throws IOException {
        List<String> lines = readAllLines(Path.of("src/main/resources/task23/23-task.input"));
        Multimap<String, String> conn2 = ArrayListMultimap.create();
        int largestSize = 0;
        Set<String> largest = null;
        for (String line : lines) {
            String c1 = line.substring(0, 2);
            String c2 = line.substring(3);
            conn2.put(c1, c2);
            conn2.put(c2, c1);
        }

        for (Entry<String, Collection<String>> e : conn2.asMap().entrySet()) {
            String c1 = e.getKey();
            Collection<String> c1conn = e.getValue();

            for (String c2 : c1conn) {
                Set<String> clique = new HashSet<>();
                clique.add(c1);
                clique.add(c2);
                candidateCheck: for (String candidate : c1conn) {
                    Collection<String> c3conn = conn2.get(candidate);
                    for (String cliqueNode : clique) {
                        if (!c3conn.contains(cliqueNode)) {
                            continue candidateCheck;
                        }
                    }
                    clique.add(candidate);
                }
                if (clique.size() > largestSize) {
                    largestSize = clique.size();
                    largest = clique;
                }
            }
        }
// /am,bc,cz,dc,gy,hk,li,qf,th,tj,wf,xk,xo
        System.out.println(largest.stream().sorted().collect(Collectors.joining(",")));
    }
}
