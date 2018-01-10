package day17;

import java.util.ArrayList;

public class Day17 {

  public static void main(String[] args) {
    int input = 359;

    ArrayList<Integer> spinLocks = new ArrayList<>();
    spinLocks.add(0);
    int position = 0;

    for (int i = 1; i < 2018; i++) {
      position = (position + input) % i + 1;
      spinLocks.add(position, i);
    }

    System.out.println("Part 1: " + spinLocks.get(position + 1));

    position = 0;

    int value = 0;
    for (int i = 1; i < 50000001; i++) {
      position = (position + input) % i + 1;
      if (position == 1) {
        value = i;
      }
    }

    System.out.println("Part 2:" + value);
  }

}
