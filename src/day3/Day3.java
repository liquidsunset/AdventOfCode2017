package day3;

import java.util.LinkedList;

public class Day3 {

  private static final int INPUT = 368078;

  public static void main(String[] args) {
    System.out.println(getManhattanDistance());
  }

  //Part 1
  private static int getManhattanDistance() {

    if (INPUT == 1) {
      return 0;
    }

    int sumOfSquares = 1;
    LinkedList<Integer> matrixSquaresList = new LinkedList<>();

    while (sumOfSquares < INPUT) {
      sumOfSquares += 8 * matrixSquaresList.size();
      matrixSquaresList.add(sumOfSquares);
    }

    //remove 1 cause distance from the center is zero :)
    matrixSquaresList.removeFirst();

    int squaresBetween = matrixSquaresList.get(matrixSquaresList.size() - 2);
    int oneLinePerMatrixRing = (matrixSquaresList.getLast() - squaresBetween) / 4;
    int squareCountPerBlock = 0;

    for (int i = squaresBetween + 1; i <= INPUT; i++) {
      squareCountPerBlock++;
      if (squareCountPerBlock % oneLinePerMatrixRing == 0) {
        squareCountPerBlock = 0;
      }
    }

    int middleElement = oneLinePerMatrixRing / 2;

    if (squareCountPerBlock == middleElement) {
      return matrixSquaresList.size();
    } else if (squareCountPerBlock < middleElement) {
      return matrixSquaresList.size() + middleElement - squareCountPerBlock;
    } else {
      return matrixSquaresList.size() - middleElement + squareCountPerBlock;
    }
  }

}
