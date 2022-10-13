
/**
 * Program 1 - IT 328
 * 10/9/2022
 * 
 * Authors: Audra Heistand (aeheis1), Matt Tobeck (mtobec1), and Evan Hazzard (ewhazza)
 * @author Matt Tobeck (mtobec1)
 * @author Evan Hazzard (ewhazza)
 * @author Audra Heistand (aeheis1)
 */
import java.io.*;
import java.util.*;

public class find3SAT
{
    /**
     * builds a graph equivalent of a CNF using an adjacency matrix
     * @param graph the 2D array/adjacency matrix that has been iniitalized with the proper
     * @param cnfArray int array that holds the literla from the cnf
     * @param indexArray an array whose elements match the axes of the matrix (literals first then clauses)
     * @param numLiterals equivalent to two times literal with the largest absolute value
     * @param numNodes the number of vertices in the graph (equivalent to numLiterals + length of CNF)
     * @return the same adjacency matrix but now with 1s to represent edges between vertices
     */
    public static int[][] fillGraph(int[][] graph, int[] cnfArray, int [] indexArray, int numLiterals, int numNodes)
    {
        // connect literals to themsleves
        for (int i = 0; i < numLiterals; i += 2) {
            graph[i][i] = 1;
            graph[i][i + 1] = 1;
            graph[i + 1][i] = 1;
            graph[i + 1][i + 1] = 1;
        }

        // connect calsues to themselves
        for (int offset = numLiterals; offset < numNodes; offset += 3) // determine starting point (after literals)
        {
            for (int i = offset; i < (offset + 3); i++) // loop through rows of 3 x 3
            {
                for (int j = offset; j < offset + 3; j++) // loop through columns of 3 x 3
                {
                    graph[i][j] = 1;
                }
            }
        }

        // connect clauses to literals
        for (int i = 0; i < numLiterals; i++) // loop through literals
        {
            for (int j = numLiterals; j < numNodes; j++) // lopp through clause literals
            {
                if (indexArray[i] == indexArray[j]) // if literal at i matches literal in clause at j add an edge
                                                    // between them
                {
                    graph[i][j] = 1; // add edge between literal and clause
                    graph[j][i] = 1; // add mirror edge
                }
            }
        }
        // returns a grpah of the CNF represented via an adjacency matrix
        return graph;
    }

    /**
    * the first numLiterals spots in this array contian the literals and their
    * negations of the CNF
    * the rmeinaing indices will be populated by the CNF itslef as read from the
    * file
    * the array indices of the literals will match their indices in the matrix
    *@param cnfArray int array that holds the literla from the cnf
    *@param numLiterals equivalent to two times the literal with the largest absolute value
    *@return an array whose elements match the axes of the matrix (literals first then clauses)
    */
    public static int[] createIndexArray(int[] cnfArray, int numLiterals)
    {
        int[] indexArray = new int[numLiterals + cnfArray.length];

        int counter = 1;
        for (int i = 0; i < numLiterals; i++)// place literals in indexArray
        {
            if (i % 2 == 0) // fill literal
            {
                indexArray[i] = counter;
            } else // fill negation of literal
            {
                indexArray[i] = counter * -1;
                counter++;
            }
        }

        for (int i = numLiterals; i < indexArray.length; i++) // add CNF to the end of the index array
        {
            indexArray[i] = cnfArray[i - numLiterals];
        }

        return indexArray;
    }

    /** converts line from cnfs2022.txt to an array of ints representing te clauses of the CNF 
     * @param input the line read in fomr the file
     * @return cnfArray the array representation of the CNF where each index continas a literal
     */ 
    public static int[] lineIntoArray(String input) {
        String[] parsedLine = input.split(" ", 0);
        int[] cnfArray = new int[parsedLine.length];
        for (int i = 0; i < parsedLine.length; i++) {
            cnfArray[i] = Integer.parseInt(parsedLine[i]);
        }
        return cnfArray;
    }

    
    /** takes the array form of the CNF and finds the intger with the highest
     *absolute value
     * @param cnfArray int array that holds the literla from the cnf
     * @return the largest absolute value in the cnfArray
     */
    public static int findMaxLiteral(int[] cnfArray) {
        int max = 0;
        for (int i = 0; i < cnfArray.length; i++) {
            if (max < Math.abs(cnfArray[i])) {
                max = Math.abs(cnfArray[i]);
            }
        }
        return max;
    }

    public static void main(String args[]) throws FileNotFoundException {
        if (args.length > 0) {
            File file = new File(args[0]);
            Scanner scan = new Scanner(file);

            System.out.println("* Solve 3CNF in cnfs2022.txt: (reduced to K-Vertex Cover) *");
            System.out.println("X means either T or F");
            int lineCount = 0;
            while (scan.hasNextLine())
            {
                lineCount++;
                int[] cnfArray = lineIntoArray(scan.nextLine()); //creates an array of integers containing the CNF
                int cnfLength = cnfArray.length; //number of literals in the CNF

                int maxValue = findMaxLiteral(cnfArray); //finds the integer witht he highest absolute value in the CNF

                int [] indexArray = createIndexArray(cnfArray, (maxValue * 2)); //and array that stores literals in the first maxValue * 2 indices and stores the CNF after them

                int dimension = (maxValue * 2) + cnfArray.length; // number of rows and columns for the matrix
                int[][] graph = new int[dimension][dimension];

                long startTime = System.nanoTime();
                graph = fillGraph(graph, cnfArray, indexArray, (maxValue * 2), dimension); //create graph from the CNF represented by a matrix

                //create object of findVCover class
                findVCover findCover = new findVCover();

                //create array lists to store vertex covers
                ArrayList<Integer> vertexCover = new ArrayList<Integer>();
                ArrayList<Integer> priorCover = new ArrayList<Integer>();

                vertexCover = findCover.findVertexCover(graph, dimension); //Calling findVertexCover method form findVCover class

                int k = dimension;
                //finding smallest possible k-vertex cover by decreasing k
                while (!vertexCover.isEmpty() && k > 0) {
                    priorCover = vertexCover;
                    vertexCover = findCover.findVertexCover(graph, k);
                    k--;
                }
                long endTime = System.nanoTime();
                vertexCover = priorCover;

                k = vertexCover.size();
                Collections.sort(vertexCover);

                long elapsedTime = (endTime - startTime) / 1000000;

                System.out.print("3CNF No. " + lineCount + ": [n=" + maxValue + " k = " + (cnfArray.length/3) + "] ("
                    + elapsedTime + " ms) ");

                //an array to store truth assingments for the CNF
                String [] truthArray = new String[cnfLength];

                //assingin T, F, or X to correct indices
                for(int j = 0; j < cnfLength; j++) //loop through CNF array
                {
                    for(int i = 0; i < vertexCover.size() && vertexCover.get(i) < (maxValue * 2); i++) //loop through vertex cover indices
                    {
                        if(cnfArray[j] == indexArray[vertexCover.get(i)]) //if indexArray at vertex from cover matches cnfArray set T
                        {
                            truthArray[j] = "T";
                        }
                        else if(cnfArray[j] == indexArray[vertexCover.get(i)] * -1) //if the negation of the value in indexArray at index is equal to the value in cnfArray set F
                        {
                            truthArray[j] = "F";
                        }
                    }
                    if(truthArray[j] != "T" && truthArray[j] != "F") //if neither true nor false because of cover set X
                    {
                        truthArray[j] = "X";
                    }
                }
                
                boolean satisfiable = true;
                int i = 0;
                //determines if CNF is satisfoable by seeing if we have three Fs for nay of the clauses
                while(satisfiable && i < cnfLength)
                {
                    if(truthArray[i] == "F" && truthArray[i + 1] == "F" && truthArray[i + 2] == "F")
                    {
                        satisfiable = false;
                    }
                    i += 3;
                }
                //prints truth assingment if the CNF was satisfaiable
                if(satisfiable)
                {
                    System.out.print("Solution: [ ");
                    i = 0;
                    while(i < vertexCover.size() && vertexCover.get(i) < (maxValue * 2))
                    {
                        
                        if(indexArray[vertexCover.get(i)] > 0)
                        {
                            System.out.print(indexArray[vertexCover.get(i)] + " : T ");
                        }
                        else if(indexArray[vertexCover.get(i)] < 0)
                        {
                            System.out.print(Math.abs(indexArray[vertexCover.get(i)]) + " : F ");
                        }
                        else
                        {
                            System.out.print(Math.abs(indexArray[vertexCover.get(i)]) + " : X ");
                        }
                        i++;
                    }
                    System.out.println("]");
                }
                else //prints random assingment to show that the CNF is not satisfiable
                {
                    System.out.print("No Solution!\tRandom: [");
                    i = 0;
                    while(i < vertexCover.size() && vertexCover.get(i) < (maxValue * 2))
                    {
                        
                        if(indexArray[vertexCover.get(i)] > 0)
                        {
                            System.out.print(indexArray[vertexCover.get(i)] + " : T ");
                        }
                        else
                        {
                            System.out.print(Math.abs(indexArray[vertexCover.get(i)]) + " : F ");
                        }
                        i++;
                    }
                    System.out.println("]");
                }

                for(i = 0; i < (cnfLength - 3); i+=3)
                {
                    System.out.print("( " + cnfArray[i] + "|" + cnfArray[i+1] + "|" + cnfArray[i+2] + " )^");
                }
                System.out.println("( " + cnfArray[cnfLength- 3] + "|" + cnfArray[cnfLength - 2] + "|" + cnfArray[cnfLength - 1] + " ) ==>");

                for(i = 0; i < (cnfLength - 3); i+=3)
                {
                    System.out.print("( " + truthArray[i] + "|" + truthArray[i+1] + "|" + truthArray[i+2] + " )^");
                }
                System.out.println("( " + truthArray[cnfLength- 3] + "|" + truthArray[cnfLength - 2] + "|" + truthArray[cnfLength - 1] + " )\n");
            }

            scan.close();

        } else {
            System.err.println("Invalid Arguments");
            System.exit(0);
        }
    }
}
