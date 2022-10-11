
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
    // find k-vertex cover
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

        // array list storing vertices in order from least # edges to greatest # edges
        ArrayList<Integer> sortedVertices = new ArrayList<Integer>();

        for (int i = 0; i < adjMatrix.length; i++) {
            sortedVertices.add(i);
        }

        int count = 0;
        int numBackTracks = 0;
        int numShuffle = 0;

        // n can be adjusted to accomodate larger graphs. In this case, 100 is big
        // enough that the probability of finding the optimal solution is
        // high enough to produce it every time without slowing down the system
        // substantially
        int n = 100;

        // while we don't have a vertex cover, try removing vertices
        // if you checked all the vertices in one order, take front and move to back.
        // check again
        // if this still doesn't work, get a new random ordering of vertices and repeat
        // above process
        while (!isVertexCover(cover, k) && count < sortedVertices.size() && numShuffle < n) {
            // if you have backtracked through the entire list, get new random list ordering
            // and do the process again
            if (numBackTracks == sortedVertices.size() - 1) {
                // get list in random order
                Collections.shuffle(sortedVertices);
                numBackTracks = 0;
                numShuffle++;
                // if a min vertex cover starting at index 0 and continuing to n can't be found,
                // reorder list by putting index 0 element at back until you use all options
            } else if ((count == sortedVertices.size() - 1) && (numBackTracks < sortedVertices.size())) {
                // remove index 0
                int first = sortedVertices.remove(0);
                // put index 0 element at back of list
                sortedVertices.add(first);
                // reset cover list (since starting over)
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

    // determine if # of true vertices = k. If so, is there is a vertex cover
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
                // prevent further lines from being read if there are no more vertices
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
                String delimiter = ",";
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