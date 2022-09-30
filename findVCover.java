import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;

public class findVCover {
    
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

                
                int vertexCover[] = {};
                int k = 0;
                while(vertexCover.length == 0){
                    vertexCover = findVertexCover(adjMatrix, k);
                }

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
        }
        else {
            System.err.println("Invalid argument");
            System.exit(0);
        }
    }
}
