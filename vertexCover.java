import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;

class vertexCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static int[] findVertexCover(int adjMatrix[][], int k) {
        int numRows = adjMatrix.length;
        int numColumns = adjMatrix[0].length;

        // store column sums
        int columnTotal[] = new int[numRows];

        for (int i = 0; i < numRows; i++) {
            int count = 0;
            for (int j = 0; j < numColumns; j++) {
                count += adjMatrix[i][j];
            }
            columnTotal[i] = count;
        }

        // print column sums
        for (int i = 0; i < columnTotal.length; i++) {
            System.out.println(columnTotal[i]);
        }

        int[] vertexCover = new int[k];

        return vertexCover;
    }

    public static void main(String args[]) {
        int adjMatrix[][] = new int[4][4];

        adjMatrix[0][0] = 0;
        adjMatrix[0][1] = 1;
        adjMatrix[0][2] = 1;
        adjMatrix[0][3] = 1;

        adjMatrix[1][0] = 1;
        adjMatrix[1][1] = 0;
        adjMatrix[1][2] = 1;
        adjMatrix[1][3] = 0;

        adjMatrix[2][0] = 1;
        adjMatrix[2][1] = 1;
        adjMatrix[2][2] = 0;
        adjMatrix[2][3] = 0;

        adjMatrix[3][0] = 1;
        adjMatrix[3][1] = 0;
        adjMatrix[3][2] = 0;
        adjMatrix[3][3] = 0;

        for (int[] x : adjMatrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        int vertexCover[] = findVertexCover(adjMatrix, 2);

    }
}