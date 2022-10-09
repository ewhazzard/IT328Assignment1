import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.time.Clock;
import java.util.*;

class vertexCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static ArrayList<Integer> findVertexCover(int adjMatrix[][], int k) {
        ArrayList<Integer> vertexCover = new ArrayList<Integer>();
        int numVertices = adjMatrix.length;

        boolean[] cover = new boolean[numVertices];

        // initialize boolean cover
        for (int i = 0; i < cover.length; i++) {
            cover[i] = true;
        }

        // store column sums as # edges
        int edgeCount[] = new int[numVertices];

        for (int i = 0; i < numVertices; i++) {
            int counter = 0;
            for (int j = 0; j < numVertices; j++) {
                counter += adjMatrix[i][j];
            }
            edgeCount[i] = counter;
        }

        // for (int i = 0; i < edgeCount.length; i++) {
        // System.out.println(edgeCount[i]);
        // }

        // array list storing vertices in order from least # edges to greatest # edges
        ArrayList<Integer> sortedVertices = new ArrayList<Integer>();

        // populate sortedVertices by continuing to find smallest element
        while (sortedVertices.size() != numVertices) {
            int largest = 0;
            for (int i = 0; i < edgeCount.length; i++) {
                if (edgeCount[i] > edgeCount[largest]) {
                    largest = i;
                }
            }
            sortedVertices.add(largest);
            edgeCount[largest] = 0;
        }

        Collections.reverse(sortedVertices);

        // int[] tempEdgeCount = new int[edgeCount.length];
        // int countTemp = 0;
        // for (int i = edgeCount.length - 1; i <= 0; i--) {
        // tempEdgeCount[countTemp] = edgeCount[i];
        // countTemp++;
        // }

        // edgeCount = tempEdgeCount;
        // for (int i = 0; i < edgeCount.length; i++) {
        // System.out.print(edgeCount[i]);
        // }

        // for (int i = 0; i < sortedVertices.size(); i++) {
        // System.out.println(sortedVertices.get(i));
        // }

        int count = 0;
        // while we don't have a vertex cover, try removing vertices
        while (!isVertexCover(cover, k) && count < sortedVertices.size()) {
            int current = sortedVertices.get(count);
            // remove current element from cover
            cover[current] = false;

            // check edges (i,j) in adjMatrix where j = current. if cover[i] = false, you
            // can't remove. change cover[current] back to true, increment count and
            // continue
            for (int i = 0; i < adjMatrix.length; i++) {
                if (adjMatrix[i][current] == 1 && cover[i] == false) {
                    cover[current] = true;
                    break;
                }
            }
            count++;

        }

        // for (int i = 0; i < cover.length; i++) {
        // System.out.print(cover[i]);
        // }

        boolean coverStatus = isVertexCover(cover, k);
        if (coverStatus) {
            for (int i = 0; i < cover.length; i++) {
                if (cover[i] == true) {
                    vertexCover.add(i);
                }
            }
        }
        return vertexCover;

    }

    // determine if # of true vertices = k. If so, is vertex cover
    public static boolean isVertexCover(boolean[] cover, int k) {
        int count = 0;
        // get # of true values
        for (int i = 0; i < cover.length; i++) {
            if (cover[i] == true) {
                count++;
            }
        }

        // if # true values is k, there is a vertex cover
        if (count == k) {
            return true;
        }

        return false;
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
                ArrayList<Integer> priorCover = new ArrayList<Integer>();

                int k = vertexCount;
                long startTime = 0;
                long endTime = 0;

                // startTime = System.nanoTime();
                // vertexCover = findVertexCover(adjMatrix, 3);
                // endTime = System.nanoTime();
                vertexCover = findVertexCover(adjMatrix, k);
                while (!vertexCover.isEmpty() && k > 0) {
                    // for (int[] x : adjMatrix) {
                    // for (int y : x) {
                    // System.out.print(y + " ");
                    // }
                    // System.out.println();
                    // }
                    startTime = System.nanoTime();
                    priorCover = vertexCover;
                    vertexCover = findVertexCover(adjMatrix, k);
                    endTime = System.nanoTime();
                    k--;
                }

                vertexCover = priorCover;

                k = vertexCover.size();
                Collections.sort(vertexCover);

                // time vertexCover operation

                // find time in ms

                long timeElapsed = (endTime - startTime) / 1000000;
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