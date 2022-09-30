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
            int numColumns = numRows;

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
            vertexCover[count] = largest;

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

    public static void main(String args[]) throws FileNotFoundException {
        if (0 < args.length) {
            File file = new File(args[0]);
            Scanner scan = new Scanner(file);
            scan.useDelimiter("[\\s,]+");

            System.out.println("*A Minimum Vertex Cover of every graph in graphs2022.txt*");
            System.out.println("\t(|V|,|E|) (size, ms used) Vertex Cover");
            int counter = 1;
            while (scan.hasNextInt()) {
                int n = scan.nextInt();
                int[][] adjMatrix = new int[n][n];

                int edgeCount = 0;

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        int next = scan.nextInt();
                        adjMatrix[i][j] = next;
                        edgeCount += next;
                    }
                }
                int vertexCount = n;
                edgeCount = (edgeCount - vertexCount) / 2;

                for (int i = 0; i < adjMatrix.length; i++) {
                    adjMatrix[i][i] = 0;
                }

                // how do I calculate k?!
                int k = 3;

                long startTime = System.nanoTime();
                int vertexCover[] = findVertexCover(adjMatrix, k);
                long endTime = System.nanoTime();

                long timeElapsed = (endTime - startTime) / 1000000;
                System.out.print("G" + counter + " (" + vertexCount + ", " + edgeCount + ")");
                System.out.print("(size = " + k + " ms = " + timeElapsed + ") {");
                for (int i = 0; i < vertexCover.length; i++) {
                    System.out.print(vertexCover[i] + ",");
                }
                System.out.print("}\n");
                counter++;
            }

            scan.close();
        } else {
            System.err.println("Invalid argument");
            System.exit(0);
        }

        // int[][] adjMatrix = new int[5][5];

        // for (int i = 0; i < 5; i++) {
        // adjMatrix[0][i] = 1;
        // adjMatrix[4][i] = 1;
        // }

        // adjMatrix[1][0] = 1;
        // adjMatrix[1][1] = 1;
        // adjMatrix[1][2] = 0;
        // adjMatrix[1][3] = 1;
        // adjMatrix[1][4] = 1;

        // adjMatrix[2][0] = 1;
        // adjMatrix[2][1] = 0;
        // adjMatrix[2][2] = 1;
        // adjMatrix[2][3] = 0;
        // adjMatrix[2][4] = 1;

        // adjMatrix[3][0] = 1;
        // adjMatrix[3][1] = 1;
        // adjMatrix[3][2] = 0;
        // adjMatrix[3][3] = 1;
        // adjMatrix[3][4] = 1;

        // for (int i = 0; i < adjMatrix.length; i++) {
        // adjMatrix[i][i] = 0;
        // }

        // for (int[] x : adjMatrix) {
        // for (int y : x) {
        // System.out.print(y + " ");
        // }
        // System.out.println();
        // }

        // int k = 3;
        // int[] vertexCover = findVertexCover(adjMatrix, k);

        // for (int i = 0; i < vertexCover.length; i++) {
        // System.out.println(vertexCover[i]);
        // }

    }
}