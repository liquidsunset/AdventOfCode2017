package day23;

import static day18.Day18.processInput;
import static day18.Day18.processInstructions;

import day18.Day18.Instruction;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Day23 {

  public static void main(String[] args) {
    HashMap<String, BigInteger> registers = new HashMap<>();
    ArrayList<Instruction> instructions = new ArrayList<>();
    processInput("resources/day23.txt", registers, instructions);

    LinkedList<BigInteger> solution = processInstructions(instructions, new HashMap<>(registers),
        true);

    System.out.println("Part 1: " + solution.getFirst().intValue());

    registers.put("a", BigInteger.ONE);
    //processInstructions(instructions, registers, true);

    System.out.println("Part 2: " + countPrimes());

  }

  private static int countPrimes() {
    int notPrime = 0;
    int b = 67;
    for (int i = b * 100 + 100000; i <= b * 100 + 117000; i += 17) {
      if (!BigInteger.valueOf(i).isProbablePrime(1)) {
        notPrime++;
      }
    }

    return notPrime;
  }


}
