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

  public static void main(String[] args) {
    HashMap<String, BigInteger> registers = new HashMap<>();
    ArrayList<Instruction> instructions = new ArrayList<>();
    processInput("resources/day18.txt", registers, instructions);

    System.out.println(
        "Part 1: " + processInstructions(instructions, new HashMap<>(registers), false).getLast()
            .intValue());
    System.out.println("Part 2: " + part2(instructions, new HashMap<>(registers)));

  }

  public static void processInput(String file, HashMap<String, BigInteger> registers,
      ArrayList<Instruction> instructions) {
    try (Stream<String> fileStream = Files.lines(Paths.get(file))) {

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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static boolean isNumeric(String value) {
    return value != null && value.matches("[-+]?\\d*\\.?\\d+");
  }


  public static LinkedList<BigInteger> processInstructions(ArrayList<Instruction> instructions,
      Map<String, BigInteger> registers, boolean day23) {
    LinkedList<BigInteger> sounds = new LinkedList<>();

    int programCounter = 0;
    int mulCounter = 0;
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
        case "sub":
          if (isNumeric(instruction.value)) {
            registers.merge(instruction.registerOrValue, new BigInteger(instruction.value),
                BigInteger::subtract);
          } else {
            registers.merge(instruction.registerOrValue, registers.get(instruction.value),
                BigInteger::subtract);
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
          mulCounter++;
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
        case "jnz":
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
          if (programCounter < 0 || programCounter >= instructions.size()) {
            break loop;
          } else {
            jump = true;
            instruction = instructions.get(programCounter);
          }

          break;
        default:
          System.out.println("Command not found: " + instruction.command);
      }

      if (jump) {
        jump = false;
      } else {
        programCounter++;
        if (programCounter < 0 || programCounter >= instructions.size()) {
          break loop;
        }
        instruction = instructions.get(programCounter);
      }

    }
    if (day23) {
      sounds.clear();
      sounds.add(BigInteger.valueOf(mulCounter));
    }
    return sounds;
  }

  private static int part2(ArrayList<Instruction> instructions,
      HashMap<String, BigInteger> registers) {

    Day18Program program0 = new Day18Program(instructions, new HashMap<>(registers),
        BigInteger.ZERO);
    Day18Program program1 = new Day18Program(instructions, new HashMap<>(registers),
        BigInteger.ONE);

    program0.setReceivingList(program1.getReceivingList());
    program1.setReceivingList(program0.getReceivingList());

    while (!((program0.isTerminated() && program1.isTerminated()) ||
        (program0.isWaitingForSound() && program1.isWaitingForSound()))) {
      System.out.println(program0.getMessagesSent());
      System.out.println(program1.getMessagesSent());
      while (!program0.isTerminated() && !program0.isWaitingForSound()) {
        program0.executeSoundCommand();
      }
      while (!program1.isTerminated() && !program1.isWaitingForSound()) {
        program1.executeSoundCommand();
      }
    }

    return program1.getMessagesSent();
  }


  public static class Instruction {

    public String command;
    public String registerOrValue;
    public String value;
  }
}
