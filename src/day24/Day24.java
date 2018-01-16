package day24;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day24 {


  private static int longest;
  private static int strongestLongest;
  private static int strongest;

  public static void main(String[] args) {
    getMaxStrength(0, getInput(), 0, 0);
    System.out.println("Part 1: " + strongest);
    System.out.println("Part 2: " + strongestLongest);
  }

  private static Map<Integer, List<Day24Component>> getInput() {
    Map<Integer, List<Day24Component>> input = new HashMap<>();

    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day24.txt"))) {
      fileStream.forEach(line -> {
        String[] splitLine = line.split("/");
        Day24Component component = new Day24Component(Integer.parseInt(splitLine[0]),
            Integer.parseInt(splitLine[1]));

        addToList(component, component.getPortA(), input);
        addToList(component, component.getPortB(), input);
      });
    } catch (IOException e) {
      e.printStackTrace();
    }

    return input;
  }

  private static void addToList(Day24Component component, int port,
      Map<Integer, List<Day24Component>> input) {
    if (!input.containsKey(port)) {
      input.put(port, new ArrayList<>());
    }
    input.get(port).add(component);
  }

  private static void getMaxStrength(int port, Map<Integer, List<Day24Component>> input,
      int length, int strength) {
    strongest = Math.max(strength, strongest);
    if (length > longest) {
      longest = length;
      strongestLongest = strength;
    } else if (length == longest) {
      strongestLongest = Math.max(strength, strongestLongest);
    }
    for (Day24Component component : input.get(port)) {
      if (!component.getUsed()) {
        component.setUsed();
        getMaxStrength(component.switchPort(port), input,
            length + 1, strength + component.getStrength());
        component.setUnused();
      }
    }
  }

}

