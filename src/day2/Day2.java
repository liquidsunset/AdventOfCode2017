package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

  public static void main(String[] args) {
    int sum = 0;
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day2.txt"))) {
      sum = fileStream.mapToInt(Day2::getMinMaxDiff).sum();
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(sum);
  }

  //part1
  private static int getMinMaxDiff(String s) {
    List<Integer> rowList = Arrays.stream(s.split("\\s+"))
        .map(Integer::parseInt)
        .collect(Collectors.toList());

    int min = rowList.stream().min(Integer::compare).get();
    int max = rowList.stream().max(Integer::compare).get();

    return max - min;
  }

  //part2
  private static int getDivisionResult(String s) {
    List<Integer> rowList = Arrays.stream(s.split("\\s+"))
        .map(Integer::parseInt)
        .sorted(Integer::compare)
        .collect(Collectors.toList());

    for (int i = 0; i < rowList.size() - 1; i++) {
      for (int j = i + 1; j < rowList.size(); j++) {
        if (rowList.get(j) % rowList.get(i) == 0) {
          return rowList.get(j) / rowList.get(i);
        }
      }
    }

    return 0;
  }
}

