
/**
 * Program 1 - IT 328
 * 10/9/2022
 * Authors: Audra Heistand (aeheis1), Matt Tobeck (mtobec1), and Evan Hazzard (ewhazza)
 */
import java.io.*;
import java.util.*;

public class findClique {

    // Public method to take in the vertex cover from findVCover.java
    // in the form of an adjacency matrix and find the corresponding clique
    // (compliment of the provided adjacency matrix)
    public static int[][] createClique(int adjMatrix[][]) {
        // Create a blank matrix that is the same dimensions of adjMatrix
        int[][] cliqueMatrix = new int[adjMatrix.length][adjMatrix.length];
        // Loop through each row
        for (int i = 0; i < adjMatrix.length; i++) {
            // Loop through each column in the nested row
            for (int j = 0; j < adjMatrix.length; j++) {
                // Invert. If we have 0, make it a 1
                if (adjMatrix[i][j] == 0) {
                    cliqueMatrix[i][j] = 1;
                }
                // Make a 1 into a 0
                else
                    cliqueMatrix[i][j] = 0;
            }
        }
        return cliqueMatrix;
    }

    public static void main(String args[]) throws FileNotFoundException {
        if (args.length > 0) {
            // Read in the file that findVCover stored
            File file = new File(args[0]);
            Scanner scan = new Scanner(file);
            scan.useDelimiter("[\\s,]+");

            while (scan.hasNext()) {
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
            }

            // Start printing to console
            System.out.println("* Max Cliques in graphs in graphs2022.txt (reduced to K-Vertex Cover) *");
            System.out.println("\t(|V|,|E|) (size, ms used) Vertex Cover");
            scan.close();
        }

    }
}
