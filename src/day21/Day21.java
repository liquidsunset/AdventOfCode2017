package day21;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class Day21 {

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day21.txt"))) {
      HashMap<String, String> rules = new HashMap<>();
      fileStream.forEach(line -> {
        String[] patterns = line.split(" => ");
        String[] splitRule = patterns[0].split("/");
        if (splitRule.length == 2) {
          for (int r = 0; r < 4; r++) {
            rules.put(String.join("/", new String[]{splitRule[0], splitRule[1]}), patterns[1]);
            rules.put(String.join("/", new String[]{flip(splitRule[0]), flip(splitRule[1])}),
                patterns[1]);
            rules.put(String.join("/", new String[]{splitRule[1], splitRule[0]}), patterns[1]);
            rules.put(String.join("/", new String[]{flip(splitRule[1]), flip(splitRule[0])}),
                patterns[1]);
            splitRule = rotate(splitRule);
          }
        } else {
          for (int r = 0; r < 4; r++) {
            rules.put(String
                .join("/", new String[]{splitRule[0], splitRule[1], splitRule[2]}), patterns[1]);
            rules.put(String
                    .join("/",
                        new String[]{flip(splitRule[0]), flip(splitRule[1]), flip(splitRule[2])}),
                patterns[1]);
            rules.put(String.join("/", new String[]{splitRule[2], splitRule[1], splitRule[0]}),
                patterns[1]);
            rules.put(String.join("/",
                new String[]{flip(splitRule[2]), flip(splitRule[1]), flip(splitRule[0])}),
                patterns[1]);
            splitRule = rotate(splitRule);
          }
        }
      });

      String[] grid = new String[]{".#.", "..#", "###"};
      String[] enhancedGrid;

      for (int i = 0; i < 18; i++) {

        int splitRule, splitAmount;
        if (grid.length % 2 == 0) {
          splitRule = 2;
        } else {
          splitRule = 3;
        }

        splitAmount = grid.length / splitRule;
        enhancedGrid = new String[(splitRule + 1) * splitAmount];

        for (int j = 0; j < splitAmount; j++) {
          for (int k = 0; k < splitAmount; k++) {
            enhanceGrid(enhancedGrid, j,
                rules.get(String.join("/", getSplitLine(grid, k, j, splitRule))).split("/"));
          }
        }
        grid = enhancedGrid;

        if (i == 4) {
          System.out.println("Part 1: " + countPixels(grid));
        }
      }

      System.out.println("Part 1: " + countPixels(grid));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static int countPixels(String[] box) {
    return Stream.of(box).mapToInt(pixelLine -> pixelLine.replace(".", "").length()).sum();
  }

  private static String[] getSplitLine(String[] box, int j, int k, int splitAmount) {
    String[] line = new String[splitAmount];
    for (int i = 0; i < splitAmount; i++) {
      line[i] = box[k * splitAmount + i].substring(j * splitAmount, (j + 1) * splitAmount);
    }
    return line;
  }

  private static void enhanceGrid(String[] box, int k, String[] rule) {
    int length = rule.length;
    for (int i = 0; i < length; i++) {
      if (box[k * length + i] == null) {
        box[k * length + i] = "";
      }
      box[k * length + i] += rule[i];
    }
  }

  private static String flip(String box) {
    return new StringBuilder(box).reverse().toString();
  }

  private static String[] rotate(String[] box) {
    String[] rotate;
    if (box.length == 2) {
      rotate = new String[]{"", ""};
      rotate[0] += box[1].charAt(0);
      rotate[0] += box[0].charAt(0);
      rotate[1] += box[1].charAt(1);
      rotate[1] += box[0].charAt(1);
    } else {
      rotate = new String[]{"", "", ""};
      rotate[0] += box[2].charAt(0);
      rotate[0] += box[1].charAt(0);
      rotate[0] += box[0].charAt(0);
      rotate[1] += box[2].charAt(1);
      rotate[1] += box[1].charAt(1);
      rotate[1] += box[0].charAt(1);
      rotate[2] += box[2].charAt(2);
      rotate[2] += box[1].charAt(2);
      rotate[2] += box[0].charAt(2);
    }
    return rotate;
  }


}
