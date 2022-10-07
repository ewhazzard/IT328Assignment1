import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;
import java.lang.*;

public class find3SAT
{
    public static int[][] fillGraph(int[][] graph, int[] clauseArr, int numLiterals, int numNodes, int k)
    {
        //initialize graph to have zeroes
        for(int i = 0; i < numNodes; i++)
        {
            for(int j = i; j < numNodes; j++)
            {
                graph[i][j] = 0;
            }
        }
        int [] shiftedArray = new int[numLiterals + clauseArr.length];
        
        for(int i = 0; i < k; i+=2){
            shiftedArray[i] = i;
            shiftedArray[i+1] = -i;
        }

        for(int i = k; i < shiftedArray.length; i++ )
        {
            shiftedArray[i] = clauseArr[i - k];
        }

        System.out.println(Arrays.toString(shiftedArray));

        //connect literals to themsleves
        // for(int i = 0; i < numLiterals; i+=2)
        // {
        //     graph[i][i] = 1;
        //     graph[i][i + 1] = 1;
        //     graph[i + 1][i] = 1;
        //     graph[i + 1][i + 1] = 1;
        // }

        // //connect calsues to themselves
        // for(int offset = k; offset < numNodes; offset += 3)
        // {
        //     for(int i = offset; i < (offset + 3); i++)
        //     {
        //         for(int j = offset; j < offset + 3; j++)
        //         {
        //             graph[i][j] = 1;
        //         }
        //     }
        // }

        // //connect clauses to literals
        // for(int i = 0; i < k; i++)
        // {
        //     for(int j = i; j < numNodes; j++)
        //     {
        //         if(shiftedArray[i] == shiftedArray[j])
        //         graph[i][j] = 1;
        //         graph[j][i] = 1;
        //     }
        // }

        // for(int i = 0; i < numNodes; i++)
        // {
        //     for(int j = 0; j < numNodes; j++)
        //     {
        //         System.out.print(graph[i][j] + " \t");
        //     }
        //     System.out.println("\n");
        // }

        return graph;
    }

    public static int[] lineIntoArray(String input)
    {
        String [] parsedLine = input.split(" ", 0);
        int[] lineArray = new int[parsedLine.length];
        for(int i = 0; i < parsedLine.length; i++) {
            lineArray[i] = Integer.parseInt(parsedLine[i]);
        }
        return lineArray;
    }

    public static int findMaxLiteral(int [] lineArray){
        int max = 0;
        for(int i = 0; i < lineArray.length; i++) {
            if (max < Math.abs(lineArray[i])){
                max = Math.abs(lineArray[i]);
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
            while(scan.hasNextLine()){

                int [] cnfArray = lineIntoArray(scan.nextLine());
                int maxValue = findMaxLiteral(cnfArray);
                int dimension = (maxValue * 2) + cnfArray.length;
                int[][] graph = new int[dimension][dimension];
                long startTime = System.nanoTime();
                graph = fillGraph(graph, cnfArray, (maxValue * 2), dimension, maxValue * 2);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                System.out.println("3CNF No. " + lineCount + ": [n=" + maxValue + " k = "+ dimension + "] (" + elapsedTime + " ms) Solution: []");
                break;
            }
            
        }
        else
        {
            System.err.println("Invalid Arguments");
            System.exit(0);
        }
    }
}
