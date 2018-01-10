package day15;

public class day15 {

  public static void main(String[] args) {

    int matchesPart1 = 0;
    int matchesPart2 = 0;
    long inputGen1 = 618;
    long inputGen2 = 814;
    int factorGen1 = 16807;
    int factorGen2 = 48271;

    for (int i = 0; i < 40000000; i++) {
      inputGen1 = (inputGen1 * factorGen1) % Integer.MAX_VALUE;
      inputGen2 = (inputGen2 * factorGen2) % Integer.MAX_VALUE;

      if ((short) inputGen1 == (short) inputGen2) {
        matchesPart1++;
      }
    }

    inputGen1 = 618;
    inputGen2 = 814;

    for (int i = 0; i < 5000000; i++) {
      do {
        inputGen1 = (inputGen1 * factorGen1) % Integer.MAX_VALUE;
      } while (inputGen1 % 4 != 0);
      do {
        inputGen2 = (inputGen2 * factorGen2) % Integer.MAX_VALUE;
      } while (inputGen2 % 8 != 0);

      if ((short) inputGen1 == (short) inputGen2) {
        matchesPart2++;
      }
    }

    System.out.println("Part 1: " + matchesPart1);
    System.out.println("Part 2: " + matchesPart2);

  }

}
