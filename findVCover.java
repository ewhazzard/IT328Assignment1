
/**
 * Program 1 - IT 328
 * 10/9/2022
 * Authors: Audra Heistand (aeheis1), Matt Tobeck (mtobec1), and Evan Hazzard (ewhazza)
 */
import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.time.Clock;
import java.util.*;

class findVCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static ArrayList<Integer> findVertexCover(int adjMatrix[][], int k) {

        // set diagonal to 0 for easier processing
        for (int i = 0; i < adjMatrix.length; i++) {
            adjMatrix[i][i] = 0;
        }

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

        // sort list from least number of edges to most
        Collections.reverse(sortedVertices);

        int count = 0;
        int numBackTracks = 1;
        // while we don't have a vertex cover, try removing vertices
        while (!isVertexCover(cover, k) && count < sortedVertices.size()) {
            // if ((count == sortedVertices.size() - 1) && (numBackTracks <
            // sortedVertices.size() - 1)) {
            // count = numBackTracks;
            // numBackTracks++;
            // for (int i = 0; i < cover.length; i++) {
            // cover[i] = true;
            // }
            // }
            if ((count == sortedVertices.size() - 1) && (numBackTracks < sortedVertices.size())) {
                int first = sortedVertices.remove(0);
                sortedVertices.add(first);
                for (int i = 0; i < cover.length; i++) {
                    cover[i] = true;
                }
                count = 0;
                numBackTracks++;
            }
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

        // check if there is a vertex cover. If so, add true vertices to the vertex
        // cover
        boolean coverStatus = isVertexCover(cover, k);
        if (coverStatus) {
            for (int i = 0; i < cover.length; i++) {
                if (cover[i] == true) {
                    vertexCover.add(i);
                }
            }
        } else {
            // try again
        }
        return vertexCover;

    }

    // determine if # of true vertices = k. If so, is vertex cover
    private static boolean isVertexCover(boolean[] cover, int k) {
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

                ArrayList<Integer> vertexCover = new ArrayList<Integer>();
                ArrayList<Integer> priorCover = new ArrayList<Integer>();

                int k = vertexCount;
                long startTime = 0;
                long endTime = 0;

                // populate vertexCover to run in while loop
                vertexCover = findVertexCover(adjMatrix, k);
                // try to find vertex cover. If there isn't one for k, revert to the last one
                while (!vertexCover.isEmpty() && k > 0) {
                    startTime = System.nanoTime();
                    priorCover = vertexCover;
                    vertexCover = findVertexCover(adjMatrix, k);
                    endTime = System.nanoTime();
                    k--;
                }

                vertexCover = priorCover;

                k = vertexCover.size();
                Collections.sort(vertexCover);

                long timeElapsed = (endTime - startTime) / 1000000;
                System.out.print("G" + counter + " (" + vertexCount + ", " + edgeCount + ")");
                System.out.print("(size = " + k + " ms = " + timeElapsed + ") {");
                String delimiter = ", ";
                StringJoiner joiner = new StringJoiner(delimiter);
                vertexCover.forEach(item -> joiner.add(item.toString()));
                System.out.print(joiner.toString());
                System.out.print("}\n");
                counter++;
            }
            System.out.print("***");
            scan.close();
        } else {
            System.err.println("Invalid argument");
            System.exit(0);
        }
    }
}