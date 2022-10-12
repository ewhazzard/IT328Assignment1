
/**
 * Program 1 - IT 328
 * 10/9/2022
 * Authors: Audra Heistand (aeheis1), Matt Tobeck (mtobec1), and Evan Hazzard (ewhazza)
 */
import java.io.*;
import java.util.*;

public class findClique {
   
    // Given the compliment of a graph, call findVCover and return the vertex cover
    public static void constructClique(int[][] adjMatrix, int vertexCount, int edgeCount, int counter) {
        // Start k at the number of vertices, the maximum clique
        int k = vertexCount;
        // Initialize timing variables
        long startTime = 0;
        long endTime = 0;
        // Initialize clique variables to find min clique
        ArrayList<Integer> clique = findVCover.findVertexCover(adjMatrix, k);
        ArrayList<Integer> priorClique = new ArrayList<Integer>();
        // While the clique is not empty, decrease k, find a VCover of size k for adjMatrix and save it to clique. Once we break this loop we will go back to the last answer because that is the min vcover
        while(!clique.isEmpty() && k > 0){
            startTime = System.nanoTime();
            priorClique = clique;
            clique = findVCover.findVertexCover(adjMatrix, k);
            endTime = System.nanoTime();
            k--;
            System.out.println(clique);
        }

        // Save minimum clique
        clique = priorClique;
        k = clique.size();
        // Sort the clique for human reading
        Collections.sort(clique);

        long timeElapsed = (endTime - startTime) / 1000000;
        // Print out results
        System.out.print("G" + counter + " (" + vertexCount + ", " + edgeCount + ")");
        System.out.print("(size = " + k + " ms = " + timeElapsed + ") {");
        String delimiter = ", ";
        StringJoiner joiner = new StringJoiner(delimiter);
        clique.forEach(item -> joiner.add(item.toString()));
        System.out.print(joiner.toString());
        System.out.print("}\n");
    }


    public static void main(String args[]) throws FileNotFoundException {
        if (args.length > 0) {
            // Read in the file that findVCover stored
            File file = new File(args[0]);

            Scanner scan = new Scanner(file);
            scan.useDelimiter("[\\s,]+");
            
            // Count 
            int counter = 0;
            System.out.println("* Max Cliques in graphs in graphs2022.txt (reduced to K-Vertex Cover) *");
            System.out.println("\t(|V|,|E|) (size, ms used) Vertex Cover");
            while (scan.hasNextInt()) {
                counter++;
                // n is the number of rows and columns of the adj matrix
                int n = scan.nextInt();
                if (n == 0) {
                    break;
                }
                int[][] adjMatrix = new int[n][n];

                int edgeCount = 0;


                // count the edges of the original graph
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        // get next int in each line and add to adj matrix
                        int next = scan.nextInt();
                        adjMatrix[i][j] = next;
                        // add value (0 or 1) to the edge count
                        edgeCount += next;
                    }
                }
                // Find the compliment of the given graph by flipping 0's to 1's and 1's to 0's
                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        if(adjMatrix[i][j] == 0){
                            adjMatrix[i][j] = 1;
                        }
                        else {
                            adjMatrix[i][j] = 0;
                        }
                    }
                }               
                // set diagonal to 1 to correctly connect vertices with themselves in the complement graph
                for (int i = 0; i < adjMatrix.length; i++) {
                    adjMatrix[i][i] = 1;
                }
                // Set the number of vertices to the first number read in this file 
                int vertexCount = n;
                // Account for symetry of the graph and vertices being connected to themselves
                edgeCount = (edgeCount - vertexCount) / 2;
                constructClique(adjMatrix, vertexCount, edgeCount, counter);
                break;
            }
            System.out.print("***");
            scan.close();
        }
        else {
            System.err.println("Invalid argument");
            System.exit(0);
        }
    }
}
