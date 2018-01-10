package day14;

import day10.Day10;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day14 {

  public static void main(String[] args) {

    String key = "stpzcrnm-";
    int bitCount = 0;
    int regionCount = 0;
    char[][] disk = new char[128][128];
    int[] hashCycle = new int[256];

    for (int i = 0; i < hashCycle.length; i++) {
      hashCycle[i] = i;
    }

    for (int i = 0; i < 128; i++) {
      String hexKeys = Day10
          .calcPart2(hashCycle.clone(), getCharList(key + String.valueOf(i)))
          .stream().map(String::toString).reduce("", String::concat);
      BigInteger hashValue = new BigInteger(hexKeys, 16);
      bitCount += hashValue.bitCount();

      StringBuilder row = new StringBuilder(hashValue.toString(2));

      while (row.length() != 128) {
        row.insert(0, "0");
      }

      disk[i] = row.toString().toCharArray();
    }

    for (int i = 0; i < 128; i++) {
      for (int j = 0; j < 128; j++) {
        if (disk[i][j] == '1') {
          regionCount++;
          checkRegions(disk, i, j);
        }
      }
    }

    System.out.println("Part 1: " + bitCount);
    System.out.println("Part 2: " + regionCount);
  }

  private static void checkRegions(char[][] disk, int row, int column) {
    if (row < 0 || column < 0 || row >= disk.length ||
        column >= disk[0].length || disk[row][column] != '1') {
      return;
    }

    disk[row][column] = 'X';

    checkRegions(disk, row - 1, column);
    checkRegions(disk, row, column + 1);
    checkRegions(disk, row + 1, column);
    checkRegions(disk, row, column - 1);
  }

  private static List<Integer> getCharList(String key) {
    List<Integer> asciiLengthList = new ArrayList<>();

    for (char ch : key.toCharArray()) {
      asciiLengthList.add((int) ch);
    }

    return asciiLengthList;
  }

}
