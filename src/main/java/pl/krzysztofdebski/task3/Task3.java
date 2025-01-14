package pl.krzysztofdebski.task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {

    public static void main(String[] args) throws IOException {
        String string = Files.readString(Path.of("src/main/resources/task3/3-task.input"));
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(string);
        Long result = 0L;
        while(matcher.find()) {
            result += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }

        System.out.println(result);
    }
}

