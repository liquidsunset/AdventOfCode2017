package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day11 {

  public static void main(String[] args) {
    try {
      String moveLine = Files.lines(Paths.get("resources/day11.txt")).findFirst().get();
      String[] moves = moveLine.split(",");
      int x = 0, y = 0, furthestStep = 0;
      for (String move : moves) {

        switch (move) {
          case "n":
            y += 2;
            break;
          case "ne":
            y++;
            x++;
            break;
          case "se":
            y--;
            x++;
            break;
          case "s":
            y -= 2;
            break;
          case "sw":
            y--;
            x--;
            break;
          case "nw":
            y++;
            x--;
            break;
          default:
            System.out.println("Move Instruction not defined " + move);
        }

        int newDistance = calcDistance(x, y);

        if (newDistance > furthestStep) {
          furthestStep = newDistance;
        }
      }

      System.out.println("Steps away: " + calcDistance(x, y));
      System.out.println("Furthest step away: " + furthestStep);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static int calcDistance(int x, int y) {
    x = Math.abs(x);
    y = Math.abs(y);

    if (x < y) {
      return (x + y) / 2;
    } else {
      return x;
    }
  }

}
