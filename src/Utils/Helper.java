package Utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Helper {
  public static List<Integer> getIntegerListFromString(String s, String delimiter) {
    return Arrays.stream(s.split(delimiter))
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  public static int[] getIntArrayFromString(String s, String delimiter) {
    return getIntegerListFromString(s, delimiter).stream().mapToInt(Integer::intValue).toArray();
  }
}
