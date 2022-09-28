import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;

class vertexCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static int[] findVertexCover(int adjMatrix[][], int k) {

        int[] vertexCover = new int[k];

        int count = 0;
        while (count < k) {

            // find the index with the largest sum
            int numRows = adjMatrix.length;
            int numColumns = adjMatrix[0].length;

            // store column sums
            int columnTotal[] = new int[numRows];

            for (int i = 0; i < numRows; i++) {
                int counter = 0;
                for (int j = 0; j < numColumns; j++) {
                    counter += adjMatrix[i][j];
                }
                columnTotal[i] = counter;
            }

            // add index to vertex cover array
            int largest = 0;
            for (int i = 0; i < columnTotal.length; i++) {
                if (columnTotal[i] > columnTotal[largest]) {
                    largest = i;
                }
            }

            // add vertex with most edges to vertex cover
            vertexCover[count] = columnTotal[largest];

            // turn subsequent rows to 0
            for (int i = 0; i < numRows; i++) {
                adjMatrix[largest][i] = 0;
            }

            // turn subsequent columns to 0
            for (int i = 0; i < numColumns; i++) {
                adjMatrix[i][largest] = 0;
            }

            // increment count
            count++;
        }

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

        int numVertex = 4;
        int numEdge = 4;
        int k = 0;

        if (numVertex == numEdge) {
            k = 2;
        } else {
            k = numEdge - numVertex;
        }

        for (int[] x : adjMatrix) {
            for (int y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }

        int vertexCover[] = findVertexCover(adjMatrix, k);
        for (int i = 0; i < vertexCover.length; i++) {
            System.out.print(i + " ");
        }

    }
}