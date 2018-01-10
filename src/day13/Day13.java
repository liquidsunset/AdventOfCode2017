package day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day13 {

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day13.txt"))) {
      HashMap<Integer, Integer> fireWall = new HashMap<>();

      fileStream.forEach(line -> {
        String[] wallEntry = line.split(" ");
        fireWall
            .put(Integer.parseInt(wallEntry[0].replace(":", "")), Integer.parseInt(wallEntry[1]));
      });

      int severity = fireWall.entrySet().stream()
          .filter(layer -> layer.getKey() % (2 * (layer.getValue() - 1)) == 0)
          .mapToInt(layer -> layer.getKey() * layer.getValue()).sum();

      System.out.println("Part 1: " + severity);

      AtomicInteger delay = new AtomicInteger(0);

      while (fireWall.entrySet().stream().anyMatch(layer ->
          (layer.getKey() + delay.get()) % (2 * (layer.getValue() - 1)) == 0)) {
        delay.incrementAndGet();
      }

      System.out.println("part 2: " + delay);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
