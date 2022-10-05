import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;

public class findVCover {

    public static ArrayList<Integer> findVertexCover(int adjMatrix[][], int k) {

        ArrayList<Integer> vertexCover = new ArrayList<Integer>();

        // find the index with the largest sum
        int numRows = adjMatrix.length;
        int numColumns = numRows;

        int count = 0;
        while (count < k) {

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
            vertexCover.add(largest);

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

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (adjMatrix[i][j] == 1) {
                    System.out.println("Not empty");
                    return null;
                }
            }
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
            // Each graph is a loop of the scanner
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

                // Start time of calculating the vertex cover
                long startTime = System.nanoTime();

                ArrayList<Integer> vertexCover = new ArrayList<Integer>();
                ArrayList<Integer> previous = new ArrayList<Integer>();
                int k = vertexCount - 1;
                while (vertexCover != null && k > 0) {
                    previous = vertexCover;
                    vertexCover = findVertexCover(adjMatrix, k);
                    k--;
                }

                if (k == 0) {
                    System.out.println("No vertex cover available");
                }

                k += 1;
                vertexCover = previous;

                for (int i = 0; i < vertexCover.size(); i++) {
                    System.out.print(vertexCover.get(i) + " ");
                }

                long endTime = System.nanoTime();
                long timeElapsed = (endTime - startTime) / 1000000;
                System.out.print("G" + counter + " (" + vertexCount + ", " + edgeCount + ")");
                System.out.print("(size = " + k + " ms = " + timeElapsed + ") {");
                for (int i = 0; i < vertexCover.size(); i++) {
                    System.out.print(vertexCover.get(i) + ",");
                }
                System.out.print("}\n");
                counter++;
            }
            scan.close();
        } else {
            System.err.println("Invalid argument");
            System.exit(0);
        }
    }
}
