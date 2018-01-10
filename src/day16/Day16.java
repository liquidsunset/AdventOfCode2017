package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Day16 {

  public static void main(String[] args) {
    try {
      String programs = "abcdefghijklmnop";
      String danceInstructions = Files.lines(Paths.get("resources/day16.txt")).findFirst().get();
      System.out.println("Part 1: " + dance(danceInstructions.split(","), programs.toCharArray()));

      ArrayList<String> dances = new ArrayList<>();
      for (int i = 0; i < 1000000000; i++) {
        programs = dance(danceInstructions.split(","), programs.toCharArray());
        if (dances.contains(programs)) {
          System.out.println("Part 2: " + dances.get(1000000000 % i));
          break;
        } else {
          dances.add(programs);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static String dance(String[] instructions, char[] programs) {
    int len = programs.length;
    for (String instruction : instructions) {
      switch (instruction.charAt(0)) {
        case 's':
          int spin = Integer.parseInt(instruction.substring(1));
          for (int j = 0; j < spin; j++) {
            char temp = programs[len - 1];
            System.arraycopy(programs, 0, programs, 1, len - 1);
            programs[0] = temp;
          }
          break;
        case 'x':
          String[] positions = instruction.substring(1).split("/");
          programs = switchPos(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]),
              programs);
          break;
        case 'p':
          int pos1 = 0;
          int pos2 = 0;
          for (int j = 0; j < len; j++) {
            if (programs[j] == instruction.charAt(1)) {
              pos1 = j;
            }
            if (programs[j] == instruction.charAt(3)) {
              pos2 = j;
            }
          }
          programs = switchPos(pos1, pos2, programs);
          break;
      }
    }

    return new String(programs);
  }

  private static char[] switchPos(int pos1, int pos2, char[] programs) {
    char temp = programs[pos1];
    programs[pos1] = programs[pos2];
    programs[pos2] = temp;

    return programs;
  }

}
