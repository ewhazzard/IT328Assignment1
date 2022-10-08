import java.io.*;
import java.util.*;

public class find3SAT
{
    /* takes a 2D array that will serve as an adjacency representation of the grpah of the CNF,
     * an array of integers representing the CNF,
     * the number of literals ( |highest number in CNF| * 2),
     * and the number of nodes (number of literals + length of the CNF)
    */
    public static int[][] fillGraph(int[][] graph, int[] cnfArray, int numLiterals, int numNodes)
    {
        //initialize graph to have zeroes
        for(int i = 0; i < numNodes; i++)
        {
            for(int j = i; j < numNodes; j++)
            {
                graph[i][j] = 0;
            }
        }
        /*the first numLiterals spots in this array contian the literals and their negations of the CNF
         * the rmeinaing indices will be populated by the CNF itslef as read from the file
         * the array indices of the literals will match their indices in the matrix
        */
        int [] indexArray = new int[numLiterals + cnfArray.length];
        
        int counter = 1;
        for(int i = 0; i < numLiterals; i++)//place literals in index_array
        {
            if(i % 2 == 0) //fill literal
            {
                indexArray[i] = counter;
            }
            else //fill negation of literal
            {
                indexArray[i] = counter * -1;
                counter++;
            }
        }

        for(int i = numLiterals; i < indexArray.length; i++ ) //add clause to the end of the index array
        {
            indexArray[i] = cnfArray[i - numLiterals];
        }

        System.out.println(Arrays.toString(indexArray));

        //connect literals to themsleves
        for(int i = 0; i < numLiterals; i+=2)
        {
            graph[i][i] = 1;
            graph[i][i + 1] = 1; 
            graph[i + 1][i] = 1;
            graph[i + 1][i + 1] = 1;
        }

        //connect calsues to themselves
        for(int offset = numLiterals; offset < numNodes; offset += 3) //determine starting point (after literals)
        {
            for(int i = offset; i < (offset + 3); i++) //loop through rows of 3 x 3
            {
                for(int j = offset; j < offset + 3; j++) //loop through columns of 3 x 3
                {
                    graph[i][j] = 1;
                }
            }
        }

        //connect clauses to literals
        for(int i = 0; i < numLiterals; i++) //loop through literals
        {
            for(int j = numLiterals; j < numNodes; j++) //lopp through clause literals
            {
                if(indexArray[i] == indexArray[j]) // if literal at i matches literal in clause at j add an edge between them
                {
                    graph[i][j] = 1; //add edge between literal and clause
                    graph[j][i] = 1; // add mirror edge
                }
            }
        }

        for(int i = 0; i < numNodes; i++)
        {
            for(int j = 0; j < numNodes; j++)
            {
                System.out.print(graph[i][j] + " \t");
            }
            System.out.println("\n");
        }
        //returns a grpah of the CNF represented via an adjacency matrix
        return graph;
    }

    //takes a line read from the file and stores each integer in an index of an array
    public static int[] lineIntoArray(String input)
    {
        String [] parsedLine = input.split(" ", 0);
        int[] cnfArray = new int[parsedLine.length];
        for(int i = 0; i < parsedLine.length; i++)
        {
            cnfArray[i] = Integer.parseInt(parsedLine[i]);
        }
        return cnfArray;
    }

    //takes the array form of the CNF and finds the intger with the highest absolute value
    public static int findMaxLiteral(int [] cnfArray)
    {
        int max = 0;
        for(int i = 0; i < cnfArray.length; i++) {
            if (max < Math.abs(cnfArray[i]))
            {
                max = Math.abs(cnfArray[i]);
            }
        }
        return max;
    }
    public static void main(String args[]) throws FileNotFoundException
    {
        if(args.length > 0)
        {
            File file = new File(args[0]);
            Scanner scan = new Scanner(file);

            System.out.println("* Solve 3CNF in cnfs2022.txt: (reduced to K-Vertex Cover) *");
            System.out.println("X means either T or F");
            int lineCount = 0;
            while(scan.hasNextLine())
            {
                lineCount++;
                int [] cnfArray = lineIntoArray(scan.nextLine());
                int maxValue = findMaxLiteral(cnfArray);
                int dimension = (maxValue * 2) + cnfArray.length; //number of rows and columns for the matrix
                int[][] graph = new int[dimension][dimension];
                long startTime = System.nanoTime();
                graph = fillGraph(graph, cnfArray, (maxValue * 2), dimension);
                long endTime = System.nanoTime();
                long elapsedTime = (endTime - startTime) / 1000000;
                System.out.println("3CNF No. " + lineCount + ": [n=" + maxValue + " k = "+ dimension + "] (" + elapsedTime + " ms) Solution: ");
            }

            scan.close();
            
        }
        else
        {
            System.err.println("Invalid Arguments");
            System.exit(0);
        }
    }
}
