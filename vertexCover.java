import java.io.*;
import java.util.*;
import java.util.LinkedList;

class vertexCover {

    public static int[] findVertexCover(LinkedList<Integer>[] graph) {
        int[] test = new int[10];
        return test;
    }

    public static void main(String args[]) {
        System.out.println("Hello World!");
        LinkedList<Integer> graph[] = new LinkedList[3];
        graph[0] = new LinkedList();
        graph[1] = new LinkedList();
        graph[2] = new LinkedList();
        graph[0].add(2);
        graph[0].add(3);
        graph[1].add(1);
        graph[1].add(3);
        graph[2].add(1);
        graph[2].add(2);

        // print array lists
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                System.out.print(graph[i].get(j) + " -> ");
            }
            System.out.print("\n");
        }
    }
}