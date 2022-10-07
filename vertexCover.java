import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.time.Clock;
import java.util.*;

class vertexCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static ArrayList<Integer> findVertexCover(int adjMatrix[][], int k) {

        ArrayList<Integer> vertexCover = new ArrayList<Integer>();

        // for (int[] x : adjMatrix) {
        // for (int y : x) {
        // System.out.print(y + " ");
        // }
        // System.out.println();
        // }

        while (k > 0) {

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

            int count = 0;
            // System.out.println("k: " + k);
            for (int i = 0; i < columnTotal.length; i++) {
                // System.out.print(columnTotal[i]);
                count += columnTotal[i];
            }
            // System.out.println();

            if (count == 0) {
                break;
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
            k--;
        }

        // for (int i = 0; i < vertexCover.size(); i++) {
        // System.out.print(vertexCover.get(i) + " ");
        // }

        return vertexCover;
    }

    public static void main(String args[]) throws FileNotFoundException {
        if (0 < args.length) {
            // get file to scan
            File file = new File(args[0]);
            Scanner scan = new Scanner(file);
            scan.useDelimiter("[\\s,]+");

            System.out.println("*A Minimum Vertex Cover of every graph in graphs2022.txt*");
            System.out.println("\t(|V|,|E|) (size, ms used) Vertex Cover");
            int counter = 1;
            // scan until there are no more lines
            while (scan.hasNextInt()) {
                // n is the number of rows and columns of the adj matrix
                int n = scan.nextInt();
                if (n == 0) {
                    break;
                }
                int[][] adjMatrix = new int[n][n];

                int edgeCount = 0;

                // count the edges
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        // get next int in each line and add to adj matrix
                        int next = scan.nextInt();
                        adjMatrix[i][j] = next;
                        // add value (0 or 1) to the edge count
                        edgeCount += next;
                    }
                }
                int vertexCount = n;
                edgeCount = (edgeCount - vertexCount) / 2;

                // set diagonal to 0 for easier processing
                for (int i = 0; i < adjMatrix.length; i++) {
                    adjMatrix[i][i] = 0;
                }

                // how do I calculate k?!
                ArrayList<Integer> vertexCover = new ArrayList<Integer>();

                Clock clock = Clock.systemDefaultZone();
                long startTime = 0;
                long endTime = 0;

                int k = vertexCount;
                while (vertexCover.isEmpty() && k > 0) {
                    // for (int[] x : adjMatrix) {
                    // for (int y : x) {
                    // System.out.print(y + " ");
                    // }
                    // System.out.println();
                    // }
                    startTime = clock.millis();
                    vertexCover = findVertexCover(adjMatrix, k);
                    endTime = clock.millis();
                    k--;
                }

                k = vertexCover.size();
                Collections.sort(vertexCover);

                // time vertexCover operation

                // find time in ms
                long timeElapsed = endTime - startTime;
                // long timeElapsed = (endTime - startTime) / 1000000;
                System.out.print("G" + counter + " (" + vertexCount + ", " + edgeCount + ")");
                System.out.print("(size = " + k + " ms = " + timeElapsed + ") {");
                String delimiter = ", ";
                StringJoiner joiner = new StringJoiner(delimiter);
                vertexCover.forEach(item -> joiner.add(item.toString()));
                System.out.print(joiner.toString());
                // for (int i = 0; i < vertexCover.size(); i++) {
                // System.out.print(vertexCover.get(i) + ",");
                // }
                System.out.print("}\n");
                counter++;
            }
            System.out.print("***");
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