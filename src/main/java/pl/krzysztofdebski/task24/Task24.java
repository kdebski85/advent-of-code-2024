package pl.krzysztofdebski.task24;

import pl.krzysztofdebski.utils.Tuple;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.nio.file.Files.readAllLines;
import static pl.krzysztofdebski.utils.Utils.partitionByNewLines;

public class Task24 {

    record Rule(String g1, String g2, String t, String out) {

    }

    public static void main(String[] args) throws IOException {
        Map<String, Boolean> g = new HashMap<>();
        List<Rule> rules = new ArrayList<>();

        List<String> lines = readAllLines(Path.of("src/main/resources/task24/24-task.input"));
        List<List<String>> lists = partitionByNewLines(lines);
        for (String list : lists.get(0)) {
            String[] split = list.split(": ");
            g.put(split[0], split[1].equals("1"));
        }

        for (String list : lists.get(1)) {
            String[] split = list.split(" ");
            rules.add(new Rule(split[0], split[2], split[1], split[4]));
        }

        for (int i = 0; i < 10000; i++) {
            boolean anyRuleApplied = false;
            for (Rule rule : rules) {
                if (g.containsKey(rule.g1) && g.containsKey(rule.g2) && !g.containsKey(rule.out)) {
                    Boolean b1 = g.get(rule.g1);
                    Boolean b2 = g.get(rule.g2);
                    Boolean out = switch (rule.t) {
                        case "AND" -> b1 && b2;
                        case "OR" -> b1 || b2;
                        case "XOR" -> b1 ^ b2;
                        default -> throw new IllegalStateException("Unexpected value: " + rule.t);
                    };

                    anyRuleApplied = true;
                    g.put(rule.out, out);
                }
            }
            if (!anyRuleApplied) {
                break;
            }
        }
        String z = g.entrySet().stream()
                    .filter(e -> e.getKey().startsWith("z"))
                    .sorted((c1, c2) -> c2.getKey().compareTo(c1.getKey()))
                    .map(Entry::getValue)
                    .map(b -> b ? "1" : "0")
                    .collect(Collectors.joining(""));

        System.out.println(Long.parseLong(z, 2));
    }
}
