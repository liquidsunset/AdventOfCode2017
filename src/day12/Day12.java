package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class Day12 {

  public static void main(String[] args) {
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day12.txt"))) {

      Map<Integer, ArrayList<Integer>> communicationMap = new HashMap<>();

      fileStream.forEach(line -> {
        String[] programCommunication = line.split(" ");
        ArrayList<Integer> connectedPrograms = new ArrayList<>();
        for (int i = 2; i < programCommunication.length; i++) {
          connectedPrograms.add(Integer.parseInt(programCommunication[i].replace(",", "")));
        }

        communicationMap.put(Integer.parseInt(programCommunication[0]), connectedPrograms);

      });

      int groups = 0;
      while (!communicationMap.isEmpty()) {
        getConnectedNodes(communicationMap.keySet().iterator().next(), communicationMap);
        groups++;
      }

      System.out.println("Part 2: " + groups);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getConnectedNodes(int startNode,
      Map<Integer, ArrayList<Integer>> communicationMap) {

    LinkedList<Integer> nodesToCheck = new LinkedList<>();
    ArrayList<Integer> checkedNodes = new ArrayList<>();

    nodesToCheck.add(startNode);

    while (!nodesToCheck.isEmpty()) {
      int nodeToCheck = nodesToCheck.removeFirst();
      if (!checkedNodes.contains(nodeToCheck)) {
        checkedNodes.add(nodeToCheck);
        nodesToCheck.addAll(communicationMap.get(nodeToCheck));
      }
    }

    checkedNodes.forEach(communicationMap::remove);

    if (startNode == 0) {
      System.out.println("Part 1: " + checkedNodes.size());
    }

  }

}
