package day10;

import Utils.Helper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day10 {

  private static int skipSize = 0;
  private static int position = 0;

  public static void main(String[] args) {

    try {
      String lengths = Files.lines(Paths.get("resources/day10.txt")).findFirst().get();
      List<Integer> lengthsList = Helper.getIntegerListFromString(lengths, ",");

      int[] hashCycle = new int[256];

      for (int i = 0; i < hashCycle.length; i++) {
        hashCycle[i] = i;
      }

      calcPart1(hashCycle.clone(), lengthsList);
      skipSize = 0;
      position = 0;

      List<Integer> asciiLengthList = new ArrayList<>();

      for(char ch : lengths.toCharArray()) {
        asciiLengthList.add((int) ch);
      }

      asciiLengthList.addAll(Arrays.asList(17, 31, 73, 47, 23));
      calcPart2(hashCycle, asciiLengthList);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static void calcPart1(int[] hashCycle, List<Integer> lengthsList) {
    knotInput(hashCycle, lengthsList);
    System.out.println("Part 1: " + hashCycle[0] * hashCycle[1]);
  }

  private static void calcPart2(int[] hashCycle, List<Integer> lengthsList) {
    for(int i = 0; i < 64; i++) {
      knotInput(hashCycle, lengthsList);
    }

    AtomicInteger count = new AtomicInteger(-1);
    List<Integer> list = Arrays.stream(hashCycle).boxed().collect(Collectors.toList());
    Collection<List<Integer>> batchedLists = list.stream()
        .collect(Collectors.groupingBy(value ->
        Math.floorDiv(count.incrementAndGet(), 16))).values();

    List<Integer> xorList = batchedLists.stream().mapToInt(batchList -> batchList.stream()
        .reduce((a,b) -> a^b).get()).boxed()
        .collect(Collectors.toList());

    List<String> hexList = xorList.stream().map(value -> String.format("%02X", value).toLowerCase()).collect(Collectors.toList());
    System.out.print("Part 2: ");
    hexList.forEach(System.out::print);
  }


  private static void knotInput(int[] hashCycle, List<Integer> lengthsList) {
    List<Integer> subList = new ArrayList<>();
    for (int length : lengthsList) {

      for (int i = 0; i < length; i++) {
        subList.add(hashCycle[(i + position) % hashCycle.length]);
      }

      Collections.reverse(subList);
      for (int i = 0; i < length; i++) {
        hashCycle[(i + position) % hashCycle.length] = subList.get(i);
      }
      position += length + skipSize;
      subList.clear();
      skipSize += 1;
    }
  }

}
