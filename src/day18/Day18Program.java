package day18;

import static day18.Day18.isNumeric;

import day18.Day18.Instruction;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day18Program {

  private LinkedList<BigInteger> sounds = new LinkedList<>();
  private LinkedList<BigInteger> sendSounds;
  private ArrayList<Instruction> instructions;
  private int programCounter = 0;
  private Map<String, BigInteger> registers;
  private int messagesSent;

  Day18Program(ArrayList<Instruction> instructions, HashMap<String, BigInteger> registers,
      BigInteger id) {
    this.registers = registers;
    this.registers.put("p", id);
    this.instructions = instructions;
  }

  public void executeSoundCommand() {
    Instruction instruction = instructions.get(programCounter);
    switch (instruction.command) {
      case "snd":
        if (isNumeric(instruction.registerOrValue)) {
          sendSounds.add(new BigInteger(instruction.registerOrValue));
        } else {
          sendSounds.add(registers.get(instruction.registerOrValue));
        }
        messagesSent++;
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
        registers.put(instruction.registerOrValue, sounds.poll());
        break;
      case "jgz":
        if (isNumeric(instruction.registerOrValue)
            || !registers.get(instruction.registerOrValue).equals(BigInteger.ZERO)) {
          if (isNumeric(instruction.value)) {
            programCounter += Integer.parseInt(instruction.value);
          } else {
            programCounter += registers.get(instruction.value).intValue();
          }
          return;
        }
        break;
      default:
        System.out.println("Command not found: " + instruction.command);
    }
    programCounter++;
  }

  public boolean isTerminated() {
    return programCounter < 0 || programCounter >= instructions.size();
  }

  public boolean isWaitingForSound() {
    return sounds.isEmpty() && "rcv".equals(instructions.get(programCounter).command);
  }

  public int getMessagesSent() {
    return messagesSent;
  }

  public LinkedList<BigInteger> getReceivingList() {
    return sounds;
  }

  public void setReceivingList(LinkedList<BigInteger> receivingList) {
    this.sendSounds = receivingList;
  }

}
