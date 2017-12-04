package day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day4 {

  public static void main(String[] args) {
    long sum = 0;
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day4.txt"))) {
      sum = fileStream.mapToInt(Day4::isValidString).sum();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(sum);
  }

  //Part 1
  private static int isValidString(String s) {
    String[] line = s.split(" ");
    long count = Arrays.stream(line).distinct().count();
    if (count == line.length) {
      return 1;
    } else {
      return 0;
    }
  }

}
