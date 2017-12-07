package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

  public static void main(String[] args) {

    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day5.txt"))) {
      String s = fileStream.collect(Collectors.joining(" "));
      int[] array = Utils.Helper.getIntArrayFromString(s, " ");

      int index = 0;
      int stepCount = 0;
      boolean part2 = true;
      while (index < array.length && index >= 0) {
        int offset = array[index];
        if (part2 && offset >= 3) {
          array[index] -= 1;
        } else {
          array[index] += 1;
        }
        index += offset;
        stepCount++;
      }

      System.out.println(stepCount);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
