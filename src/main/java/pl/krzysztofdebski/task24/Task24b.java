package pl.krzysztofdebski.task24;

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

public class Task24b {

    static class Rule {

        String g1;
        String g2;
        String t;
        String out;

        public Rule(String g1, String g2, String t, String out) {
            this.g1 = g1;
            this.g2 = g2;
            this.t = t;
            this.out = out;
        }

        @Override
        public String toString() {
            return "Rule{" +
                "g1='" + g1 + '\'' +
                ", g2='" + g2 + '\'' +
                ", t='" + t + '\'' +
                ", out='" + out + '\'' +
                '}';
        }
    }

    static void swapOut(Rule r1, Rule r2) {
        String tmp = r1.out;
        r1.out = r2.out;
        r2.out = tmp;
    }

    public static void main(String[] args) throws IOException {
        List<Rule> rules = new ArrayList<>();

        List<String> lines = readAllLines(Path.of("src/main/resources/task24/24-task.input"));
        List<List<String>> lists = partitionByNewLines(lines);

        for (String list : lists.get(1)) {
            String[] split = list.split(" ");
            rules.add(new Rule(split[0], split[2], split[1], split[4]));
        }

        List<Rule> rulesWithErrors = new ArrayList<>();
        for (Rule rule : rules) {
            if (rule.t.equals("XOR")) {
                if (rule.g1.startsWith("x") || rule.g2.startsWith("x")) {
                    int n = Integer.parseInt(rule.g1.substring(1));
                    if ((rule.g1.equals("x" + formatNumber(n)) && rule.g2.equals("y" + formatNumber(n))
                        || rule.g2.equals("x" + formatNumber(n)) && rule.g1.equals("y" + formatNumber(n)))
                        && (rule.out.startsWith("z"))
                    ) {
                        if (!rule.g1.equals("x00") && !rule.g2.equals("x00")) {
                            System.out.println("A=" + rule);
                            rulesWithErrors.add(rule);
                        }
                    }
                } else {
                    if (!rule.out.startsWith("z")) {
                        rulesWithErrors.add(rule);
                        System.out.println("B=" + rule);
                    }
                }
            }

            if (rule.out.startsWith("z") && !rule.t.equals("XOR") && !rule.out.equals("z45")) {
                rulesWithErrors.add(rule);
                System.out.println("C=" + rule);
            }
        }

        List<Integer> v = List.of(47, 48, 75, 123, 144); //from previous run with fewer checks
        for (int ruleNo = 0; ruleNo < rules.size(); ruleNo++) {
            Rule rule1 = rules.get(ruleNo);

            if (v.contains(ruleNo)) {
                if (!rulesWithErrors.contains(rule1)) {
                    for (int ruleNo2 = ruleNo + 1; ruleNo2 < rules.size(); ruleNo2++) {
                        Rule rule2 = rules.get(ruleNo2);
                        if (!rulesWithErrors.contains(rule2)) {
                            List<Rule> rulesToCheck = new ArrayList<>();
                            rulesToCheck.add(rule1);
                            rulesToCheck.add(rule2);
                            rulesToCheck.addAll(rulesWithErrors);
                            final int p1a = 0;
                            for (int p1b = 1; p1b < 8; p1b++) {
                                swapOut(rulesToCheck.get(p1a), rulesToCheck.get(p1b));
                                int p2a = (p1b == 1) ? 2 : 1;
                                for (int p2b = p2a + 1; p2b < 8; p2b++) {
                                    if (p2b != p1b) {
                                        swapOut(rulesToCheck.get(p2a), rulesToCheck.get(p2b));

                                        int p3a = 1;
                                        while (p3a == p1b || p3a == p2a || p3a == p2b) {
                                            p3a++;
                                        }

                                        for (int p3b = p3a + 1; p3b < 8; p3b++) {
                                            if (p3b != p1b && p3b != p2b && p3b != p2a) {
                                                swapOut(rulesToCheck.get(p3a), rulesToCheck.get(p3b));

                                                int p4a = 1;
                                                while (p4a == p1b || p4a == p2a || p4a == p2b || p4a == p3a || p4a == p3b) {
                                                    p4a++;
                                                }

                                                int p4b = p4a + 1;
                                                while (p4b == p1b || p4b == p2a || p4b == p2b || p4b == p3a || p4b == p3b) {
                                                    p4b++;
                                                }

                                                swapOut(rulesToCheck.get(p4a), rulesToCheck.get(p4b));

                                                if (isCorrect(rules)) {
                                                    System.out.println(
                                                        "Correct " + rulesToCheck + " " + p1a + " " + p2b + " " + p2a + " " + p2b + " " + p3a + " " + " " + p3b
                                                            + " " + p4a
                                                            + " " + p4b);
                                                }

                                                swapOut(rulesToCheck.get(p4a), rulesToCheck.get(p4b));
                                                swapOut(rulesToCheck.get(p3a), rulesToCheck.get(p3b));
                                            }
                                        }

                                        swapOut(rulesToCheck.get(p2a), rulesToCheck.get(p2b));
                                    }
                                }
                                swapOut(rulesToCheck.get(p1a), rulesToCheck.get(p1b));
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isCorrect(List<Rule> rules) {
        {
            Map<String, Boolean> g = new HashMap<>();
            for (int i = 0; i < 45; i++) {
                g.put("x" + formatNumber(i), true);
                g.put("y" + formatNumber(i), true);
            }
            if (!isCorrect1(rules, g, "1111111111111111111111111111111111111111111110")) {
                return false;
            }
        }

        {
            Map<String, Boolean> g1 = new HashMap<>();
            for (int i = 0; i < 45; i++) {
                g1.put("x" + formatNumber(i), true);
                g1.put("y" + formatNumber(i), false);
            }
            if (!isCorrect1(rules, g1, "0111111111111111111111111111111111111111111111")) {
                return false;
            }
        }

        {
            Map<String, Boolean> g2 = new HashMap<>();
            for (int i = 0; i < 45; i++) {
                g2.put("x" + formatNumber(i), false);
                g2.put("y" + formatNumber(i), true);
            }
            if (!isCorrect1(rules, g2, "0111111111111111111111111111111111111111111111")) {
                return false;
            }
        }

        {
            Map<String, Boolean> g3 = new HashMap<>();
            for (int i = 0; i < 45; i++) {
                g3.put("x" + formatNumber(i), true);
                g3.put("y" + formatNumber(i), i % 2 == 1);
            }
            if (!isCorrect1(rules, g3, "1010101010101010101010101010101010101010101001")) {
                return false;
            }
        }

        {
            Map<String, Boolean> g4 = new HashMap<>();
            for (int i = 0; i < 45; i++) {
                g4.put("x" + formatNumber(i), true);
                g4.put("y" + formatNumber(i), i % 2 == 0);
            }
            if (!isCorrect1(rules, g4, "1101010101010101010101010101010101010101010100")) {
                return false;
            }
        }

        return true;
    }

    private static boolean isCorrect1(List<Rule> rules, Map<String, Boolean> g, String expected) {
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

        return expected.equals(z);
    }

    //fvw,grf,mdb,nwq,wpq,z18,z22,z36
    private static String formatNumber(int i) {
        return i < 10 ? "0" + i : i + "";
    }
}
