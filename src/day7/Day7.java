package day7;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

  private static Map<String, Vertex> nodeMap = new HashMap<>();

  public static void main(String[] args) {

    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day7.txt"))) {
      fileStream.forEach(line -> {
        String[] splitLine = line.split(" ");
        String programName = splitLine[0];
        int nodeWeight = Integer.parseInt(splitLine[1].replaceAll("[()]", ""));

        if (nodeMap.containsKey(programName)) {
          nodeMap.get(programName).nodeWeight = nodeWeight;
        } else {
          Vertex vertex = new Vertex(programName);
          vertex.nodeWeight = nodeWeight;
          nodeMap.put(programName, vertex);
        }

        if (splitLine.length > 3) {
          for (int i = 3; i < splitLine.length; i++) {
            String node = splitLine[i].replace(",", "");
            if (nodeMap.containsKey(node)) {
              nodeMap.get(node).incoming.add(programName);
            } else {
              Vertex vertex = new Vertex(node);
              vertex.incoming.add(programName);
              nodeMap.put(node, vertex);
            }

            nodeMap.get(programName).outgoing.add(node);
          }

        }
      });
      try {
        calcWeights(nodeMap.get(getRoot()));
      } catch (Exception e) {

      }
      System.out.println("Root: " + getRoot());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void calcWeights(Vertex vertex) throws Exception {
    List<Integer> weightList = new ArrayList<>();
    if (vertex.outgoing.size() > 0) {
      for (String node : vertex.outgoing) {
        calcWeights(nodeMap.get(node));
        weightList.add(nodeMap.get(node).calculatedWeight);
      }
    }

    if (weightList.stream().distinct().collect(Collectors.toList()).size() > 1) {
      vertex.outgoing.forEach(node -> System.out.println(nodeMap.get(node).toString()));
      throw new Exception();
    } else {
      weightList.add(vertex.nodeWeight);
    }
    vertex.calculatedWeight = weightList.stream().mapToInt(Integer::intValue).sum();
  }

  private static String getRoot() {
    return nodeMap.values().stream().filter(vertex ->
        vertex.incoming.size() == 0).findFirst().get().name;
  }

  static class Vertex {

    List<String> incoming = new ArrayList<>();
    List<String> outgoing = new ArrayList<>();
    int nodeWeight = 0;
    int calculatedWeight = 0;
    String name;

    Vertex(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();

      builder.append("Parent: ").append(name).append(" Calc-Weight: ").append(calculatedWeight)
          .append(" Weight: ").append(nodeWeight);

      outgoing.forEach(node -> {
        Vertex vertex = nodeMap.get(node);
        builder.append("\n").append("Child: ").append(vertex.name).append(" Calc-Weight: ")
            .append(vertex.calculatedWeight)
            .append(" Weight:").append(vertex.nodeWeight);
      });

      return builder.toString();
    }
  }

}
