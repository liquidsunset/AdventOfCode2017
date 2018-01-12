package day18;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class Day18 {

  private static int programCounter = 0;

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day18.txt"))) {
      Map<String, BigInteger> registers = new HashMap<>();
      ArrayList<Instruction> instructions = new ArrayList<>();

      fileStream.forEach(line -> {
        String[] splitInstruction = line.split(" ");
        Instruction instruction = new Instruction();
        instruction.command = splitInstruction[0];
        instruction.registerOrValue = splitInstruction[1];
        if (!isNumeric(instruction.registerOrValue) && !registers
            .containsKey(instruction.registerOrValue)) {
          registers.put(instruction.registerOrValue, BigInteger.ZERO);
        }
        if (splitInstruction.length == 3) {
          instruction.value = splitInstruction[2];
        }

        instructions.add(instruction);
      });

      System.out.println(getSounds(instructions, registers).getLast().intValue());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isNumeric(String value) {
    return value != null && value.matches("[-+]?\\d*\\.?\\d+");
  }


  private static LinkedList<BigInteger> getSounds(ArrayList<Instruction> instructions,
      Map<String, BigInteger> registers) {
    LinkedList<BigInteger> sounds = new LinkedList<>();

    Instruction instruction = instructions.get(programCounter);
    boolean jump = false;
    loop:
    while (true) {
      switch (instruction.command) {
        case "snd":
          if (isNumeric(instruction.registerOrValue)) {
            sounds.add(new BigInteger(instruction.registerOrValue));
          } else {
            sounds.add(registers.get(instruction.registerOrValue));
          }
          break;
        case "set":
          if (isNumeric(instruction.value)) {
            registers.put(instruction.registerOrValue, new BigInteger(instruction.value));
          } else {
            registers.put(instruction.registerOrValue, registers.get(instruction.value));
          }
          break;
        case "add":
          if (isNumeric(instruction.value)) {
            registers.merge(instruction.registerOrValue, new BigInteger(instruction.value),
                BigInteger::add);
          } else {
            registers.merge(instruction.registerOrValue, registers.get(instruction.value),
                BigInteger::add);
          }
          break;
        case "mul":
          if (isNumeric(instruction.value)) {
            registers.merge(instruction.registerOrValue, new BigInteger(instruction.value),
                BigInteger::multiply);
          } else {
            registers.merge(instruction.registerOrValue, registers.get(instruction.value),
                BigInteger::multiply);
          }
          break;
        case "mod":
          if (isNumeric(instruction.value)) {
            registers.merge(instruction.registerOrValue, new BigInteger(instruction.value),
                BigInteger::mod);
          } else {
            registers.merge(instruction.registerOrValue, registers.get(instruction.value),
                BigInteger::mod);
          }
          break;
        case "rcv":
          if (!registers.get(instruction.registerOrValue).equals(BigInteger.ZERO)) {
            break loop;
          }
          break;
        case "jgz":
          int offset;
          if (isNumeric(instruction.registerOrValue)
              || !registers.get(instruction.registerOrValue).equals(BigInteger.ZERO)) {
            if (isNumeric(instruction.value)) {
              offset = Integer.parseInt(instruction.value);
            } else {
              offset = registers.get(instruction.value).intValue();
            }
          } else {
            break;
          }

          programCounter += offset;

          if (programCounter < 0 || programCounter > instructions.size()) {
            break loop;
          } else {
            jump = true;
            instruction = instructions.get(programCounter);
          }

          break;
        default:
          System.out.println("Command nor found: " + instruction.command);
      }

      if (jump) {
        jump = false;
      } else {
        instruction = instructions.get(++programCounter);
      }

    }

    return sounds;
  }


  static class Instruction {

    String command;
    String registerOrValue;
    String value;
  }

}
