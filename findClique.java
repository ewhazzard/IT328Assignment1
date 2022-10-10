
/**
 * Program 1 - IT 328
 * 10/9/2022
 * Authors: Audra Heistand (aeheis1), Matt Tobeck (mtobec1), and Evan Hazzard (ewhazza)
 */
import java.io.*;
import java.util.*;

public class findClique {
   
    // Given the compliment of a graph, call findVCover and return the vertex cover
    public static void constructClique(int adjMatrix[][], int vertexCount, int edgeCount, int counter) {
        int k = vertexCount;
        long startTime = 0;
        long endTime = 0;

        ArrayList<Integer> clique = findVCover.findVertexCover(adjMatrix, k);
        ArrayList<Integer> priorClique = new ArrayList<Integer>();

        while(clique.isEmpty() && k > 0){
            startTime = System.nanoTime();
            priorClique = clique;
            clique = findVCover.findVertexCover(adjMatrix, k);
            endTime = System.nanoTime();
            k--;
        }

        clique = priorClique;
        k = clique.size();
        Collections.sort(clique);

        long timeElapsed = (endTime - startTime) / 1000000;
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
            //Read in argument to find n clique
            // int cliqueCount = Integer.parseInt(args[1]);

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

                // count the edges
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        // get next int in each line and add to adj matrix
                        int next = scan.nextInt();
                        // Construct the compliment of the adjMatrix given by the file
                        if(next == 0){
                            adjMatrix[i][j] = 1;
                            edgeCount += next;
                        }
                        else {
                            adjMatrix[i][j] = 0;
                        }
                    }
                }
                int vertexCount = n;
                edgeCount = (edgeCount - vertexCount) / 2;

                constructClique(adjMatrix, vertexCount, edgeCount, counter);
            }
            scan.close();
        }
    }
}
