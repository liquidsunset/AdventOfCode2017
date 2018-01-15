package day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Day19 {

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day19.txt"))) {
      List<List<String>> map = new ArrayList<>();
      fileStream.forEach(line -> {
        List<String> splitLine = Arrays.asList(line.split(""));
        map.add(splitLine);
      });

      Point point = new Point(map.get(0).indexOf("|"), 0);
      StringBuilder letters = new StringBuilder();
      int steps = 0;

      Direction direction = Direction.SOUTH;
      while (true) {
        char move = map.get(point.y).get(point.x).charAt(0);
        if (move == ' ') {
          break;
        } else if (move == '+') {
          direction = updateDirection(map, point, direction);
        } else if (Character.isLetter(move)) {
          letters.append(move);
        }

        assert direction != null;
        updatePoint(point, direction);

        steps++;
      }

      System.out.println("Part 1: " + letters);
      System.out.println("Part 2: " + steps);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Direction updateDirection(List<List<String>> map, Point point,
      Direction direction) {
    int row = point.y;
    int column = point.x;
    switch (direction) {
      case NORTH:
      case SOUTH:
        char move = map.get(row).get(column - 1).charAt(0);
        if (move == ' ') {
          return Direction.EAST;
        } else {
          return Direction.WEST;
        }
      case EAST:
      case WEST:
        List<String> mapRow = map.get(row - 1);
        if (mapRow.size() > column && mapRow.get(column).charAt(0) != ' ') {
          return Direction.NORTH;
        } else {
          return Direction.SOUTH;
        }
    }
    return null;
  }

  private static void updatePoint(Point point, Direction direction) {
    switch (direction) {
      case NORTH:
        point.addPoint(new Point(0, -1));
        break;
      case EAST:
        point.addPoint(new Point(1, 0));
        break;
      case SOUTH:
        point.addPoint(new Point(0, 1));
        break;
      case WEST:
        point.addPoint(new Point(-1, 0));
        break;
    }
  }

  public enum Direction {
    NORTH, EAST, SOUTH, WEST
  }

  public static class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public void addPoint(Point move) {
      this.x += move.x;
      this.y += move.y;
    }

    @Override
    public boolean equals(Object obj) {
      return Point.class.isInstance(obj) &&
          ((Point) obj).x == this.x &&
          ((Point) obj).y == this.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    public Point turnLeft() {
      return new Point(this.y, -this.x);
    }

    public Point turnRight() {
      return new Point(-this.y, this.x);
    }

    public Point turnOpposite() {
      return new Point(this.x * -1, this.y * -1);
    }
  }

}
