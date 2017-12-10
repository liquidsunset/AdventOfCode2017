package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

  public static void main(String[] args) {

    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day8.txt"))) {
      Map<String, Integer> registers = new HashMap<>();
      List<Integer> collectAllValues = new ArrayList<>();
      List<String> instructions = fileStream.collect(Collectors.toList());

      for (String instruction : instructions) {
        String[] splitInstructions = instruction.split(" ");

        String destReg = splitInstructions[0];
        String register = splitInstructions[4];
        String operation = splitInstructions[5];
        int value = Integer.parseInt(splitInstructions[6]);

        if (!registers.containsKey(destReg)) {
          registers.put(destReg, 0);
        }
        if (!registers.containsKey(register)) {
          registers.put(register, 0);
        }

        if (evaluateInstruction(operation, registers.get(register), value)) {
          int oldValue = registers.get(destReg);
          int newValue = Integer.parseInt(splitInstructions[2]);
          if (splitInstructions[1].equals("inc")) {
            oldValue += newValue;
          } else {
            oldValue -= newValue;
          }
          collectAllValues.add(oldValue);
          registers.put(destReg, oldValue);
        }

      }

      System.out.println("Part 1: " + registers.values().stream().max(Integer::compare).get());
      System.out.println("Part 2: " + collectAllValues.stream().max(Integer::compare).get());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean evaluateInstruction(String operation, int a, int b) {
    boolean evaluation;
    switch (operation) {
      case ">":
        evaluation = a > b;
        break;
      case "<":
        evaluation = a < b;
        break;
      case "==":
        evaluation = a == b;
        break;
      case "!=":
        evaluation = a != b;
        break;
      case ">=":
        evaluation = a >= b;
        break;
      case "<=":
        evaluation = a <= b;
        break;
      default:
        evaluation = false;

    }

    return evaluation;
  }

}
