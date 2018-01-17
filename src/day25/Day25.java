package day25;


import java.util.HashMap;
import java.util.Map;

public class Day25 {

  private static char currentState = 'A';
  private static Map<Integer, Boolean> tape = new HashMap<>();
  private static int currentPosition = 0;

  public static void main(String[] args) {

    for (int i = 0; i < 12683008; i++) {
      switch (currentState) {
        case 'A':
          processStateA();
          break;
        case 'B':
          processStateB();
          break;
        case 'C':
          processStateC();
          break;
        case 'D':
          processStateD();
          break;
        case 'E':
          processStateE();
          break;
        case 'F':
          processStateF();
          break;
      }
    }

    System.out.println("Part 1: " + tape.values().stream().filter(Boolean::booleanValue).count());
  }

  private static void processStateA() {
    boolean current = tape.getOrDefault(currentPosition, false);

    if (current) {
      tape.put(currentPosition, false);
      currentPosition--;
    } else {
      tape.put(currentPosition, true);
      currentPosition++;
    }

    currentState = 'B';
  }

  private static void processStateB() {
    boolean current = tape.getOrDefault(currentPosition, false);

    if (current) {
      tape.put(currentPosition, false);
      currentPosition++;
      currentState = 'E';
    } else {
      tape.put(currentPosition, true);
      currentPosition--;
      currentState = 'C';
    }
  }

  private static void processStateC() {
    boolean current = tape.getOrDefault(currentPosition, false);

    if (current) {
      tape.put(currentPosition, false);
      currentPosition--;
      currentState = 'D';
    } else {
      tape.put(currentPosition, true);
      currentPosition++;
      currentState = 'E';
    }
  }

  private static void processStateD() {
    tape.put(currentPosition, true);
    currentPosition--;
    currentState = 'A';
  }

  private static void processStateE() {
    boolean current = tape.getOrDefault(currentPosition, false);
    tape.put(currentPosition, false);
    currentPosition++;
    if (current) {
      currentState = 'F';
    } else {
      currentState = 'A';
    }
  }

  private static void processStateF() {
    boolean current = tape.getOrDefault(currentPosition, false);
    tape.put(currentPosition, true);
    currentPosition++;
    if (current) {
      currentState = 'A';
    } else {
      currentState = 'E';
    }
  }

}
