package day22;

import day19.Day19.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day22 {

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day22.txt"))) {
      List<String> input = fileStream.collect(Collectors.toList());
      HashMap<Point, State> map = getMap(input);
      Point start = new Point((int) Math.ceil(input.get(0).length() / 2),
          (int) Math.ceil(input.size() / 2));

      System.out.println("Part 1: " + processBurstsPart1(new HashMap<>(map), start));
      System.out.println("Part 2: " + processBurstsPart2(new HashMap<>(map), start));


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static int processBurstsPart1(HashMap<Point, State> map, Point start) {
    int infectedBurstCount = 0;

    Point current = start;
    Point move = new Point(0, -1);

    for (int i = 0; i < 10000; i++) {
      if (map.containsKey(current)) {
        map.remove(current);
        move = move.turnRight();
      } else {
        map.put(current, State.INFECTED);
        infectedBurstCount++;
        move = move.turnLeft();
      }

      current = new Point(current.x, current.y);
      current.addPoint(move);
    }

    return infectedBurstCount;
  }

  private static int processBurstsPart2(HashMap<Point, State> map, Point start) {
    int infectedBurstCount = 0;

    Point current = start;
    Point move = new Point(0, -1);

    for (int i = 0; i < 10000000; i++) {
      State state = map.getOrDefault(current, State.CLEAN);

      switch (state) {
        case CLEAN:
          move = move.turnLeft();
          state = State.WEAKENED;
          break;
        case WEAKENED:
          infectedBurstCount++;
          state = State.INFECTED;
          break;
        case INFECTED:
          move = move.turnRight();
          state = State.FLAGGED;
          break;
        case FLAGGED:
          move = move.turnOpposite();
          state = State.CLEAN;
          break;
      }

      map.put(current, state);
      current = new Point(current.x, current.y);
      current.addPoint(move);
    }

    return infectedBurstCount;
  }


  private static HashMap<Point, State> getMap(List<String> input) {
    HashMap<Point, State> map = new HashMap<>();

    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(0).length(); j++) {
        if (input.get(i).charAt(j) == '#') {
          map.put(new Point(j, i), State.INFECTED);
        }
      }
    }

    return map;
  }

  enum State {
    CLEAN, WEAKENED, INFECTED, FLAGGED
  }

}
