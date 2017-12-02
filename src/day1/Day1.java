package day1;

import Utils.Helper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {

  public static void main(String[] args) {

    try {
      String line = Files.lines(Paths.get("resources/day1.txt")).findFirst().get();

      List<Integer> captchaList = Helper.getIntegerListFromString(line, "");
      int sum = 0;
      int currentElem;
      int nextElem;
      //for part 2, for part 1 just set offset to 1
      int offset = captchaList.size() / 2;
      for (int i = 0; i < captchaList.size(); i++) {
        currentElem = captchaList.get(i);
        nextElem = captchaList.get((i + offset) % captchaList.size());
        if (currentElem == nextElem) {
          sum += currentElem;
        }
      }

      System.out.print(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
