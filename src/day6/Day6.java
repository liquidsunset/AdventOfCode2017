package day6;

import Utils.Helper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {

  public static void main(String[] args) {
    try {

      String line = Files.lines(Paths.get("resources/day6.txt")).findFirst().get();

      int[] blocks = Helper.getIntArrayFromString(line, "\\s+");
      List<int[]> blocksList = getListWithBlocks(blocks);
      System.out.println("Day6 Part1: " + blocksList.size());

      blocks = blocksList.get(blocksList.size() - 1);
      blocksList = getListWithBlocks(blocks);
      System.out.println("Day6 Part2: " + blocksList.size());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private static boolean checkForCycle(List<int[]> blocksLists, int[] newBlock) {
    for (int[] block : blocksLists) {
      if (Arrays.equals(block, newBlock)) {
        return true;
      }
    }
    blocksLists.add(newBlock.clone());
    return false;
  }

  private static int getMaximumIndex(int[] blocks) {
    int index = 0;
    int max = blocks[index];
    for (int i = 0; i < blocks.length; i++) {
      if (blocks[i] > max) {
        index = i;
        max = blocks[i];
      }
    }
    return index;
  }

  private static List<int[]> getListWithBlocks(int[] blocks) {
    List<int[]> blocksLists = new ArrayList<>();
    blocksLists.add(blocks.clone());
    do {
      int maxIndex = getMaximumIndex(blocks);
      int maxValue = blocks[maxIndex];
      blocks[maxIndex] = 0;
      for (int i = 1; i <= maxValue; i++) {
        blocks[(maxIndex + i) % blocks.length]++;
      }
    } while (!checkForCycle(blocksLists, blocks));

    return blocksLists;
  }

}
