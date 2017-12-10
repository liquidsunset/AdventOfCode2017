package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {

  public static void main(String[] args) {
    try {
      String groupLine = Files.lines(Paths.get("resources/day9.txt")).findFirst().get();
      groupLine = groupLine.replaceAll("!.", "");

      Pattern pattern = Pattern.compile("<.*?>");
      Matcher matcher = pattern.matcher(groupLine);
      
      int removedGarbage = 0;
      while (matcher.find()) {
        removedGarbage += matcher.group().length() - 2;
      }

      groupLine = groupLine.replaceAll(pattern.pattern(), "");

      int depth = 0;
      int score = 0;
      for (int i = 0; i < groupLine.length(); i++) {
        if (groupLine.charAt(i) == '{') {
          depth++;
        } else if (groupLine.charAt(i) == '}') {
          score += depth;
          depth--;
        }
      }

      System.out.println("Part 1: " + score + " Part 2: " + removedGarbage);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
