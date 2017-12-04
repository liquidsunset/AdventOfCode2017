package day3;

import java.util.Arrays;
import java.util.LinkedList;

public class Day3 {

  private static final int INPUT = 368078;

  public static void main(String[] args) {
    System.out.println(getFirstSum());
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

  enum Moves {
    UP, DOWN, LEFT, RIGHT, INIT
  }

  //part 2
  private static int getFirstSum() {

    int[][] matrix = new int[51][51];
    Moves move = Moves.INIT;
    int actualSquareSum = 0;
    int i = 25, j = 25;
    matrix[i][j] = 1;
    printMatrix(matrix);

    int ring = 1;
    int filledSquares = 0;
    int numberSquares = ring * 8;
    int numberPerRow = numberSquares / 4;
    while (INPUT > actualSquareSum) {
      filledSquares++;
      switch (move) {
        case INIT:
          j++;
          break;
        case UP:
          i--;
          break;
        case RIGHT:
          j--;
          break;
        case DOWN:
          i++;
          break;
        case LEFT:
          j++;
          break;
      }

      if (Moves.INIT.equals(move)) {
        move = Moves.UP;
      }

      actualSquareSum = sumUpNeighbours(matrix, i, j);
      matrix[i][j] = actualSquareSum;

      if (filledSquares % numberPerRow == 0) {
        int side = filledSquares / numberPerRow;
        switch (side) {
          case 3:
            move = Moves.LEFT;
            break;
          case 2:
            move = Moves.DOWN;
            break;
          case 1:
            move = Moves.RIGHT;
            break;
          default:
            move = Moves.INIT;

        }
      }

      if (filledSquares == numberSquares) {
        ring++;
        filledSquares = 0;
        numberSquares = ring * 8;
        numberPerRow = numberSquares / 4;
      }

    }

    return actualSquareSum;
  }

  private static int sumUpNeighbours(int[][] matrix, int i, int j) {
    return matrix[i][j + 1] + matrix[i - 1][j + 1] + matrix[i - 1][j] + matrix[i - 1][j - 1]
        + matrix[i][j - 1] + matrix[i + 1][j - 1] + matrix[i + 1][j] + matrix[i + 1][j + 1];
  }

  private static void printMatrix(int[][] matrix) {
    Arrays.stream(matrix)
        .forEach(
            (row) -> {
              System.out.print("[");
              Arrays.stream(row)
                  .forEach((el) -> System.out.print(" " + el + " "));
              System.out.println("]");
            }
        );
  }

}
