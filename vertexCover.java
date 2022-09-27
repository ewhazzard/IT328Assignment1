import java.beans.IndexedPropertyChangeEvent;
import java.io.*;
import java.util.*;

class vertexCover {

    // graph is the undirected graph G and k is the number in k-vertex cover
    public static int[] findVertexCover(LinkedList<Integer>[] graph, int k) {
        // check that graph is not null
        if (graph == null) {
            System.out.println("Graph is null");
        }

        // check that k is not bigger than graph length
        if (k > graph.length) {
            System.out.println("Invalid value k: too large");
        }

        // keep track of which nodes are included in vertex cover. keys are extra nodes
        // added to the graph, nodes are from the clauses
        // int[] keysVisited = new int[3];
        // int[] nodesVisited = new int[k - 3];
        // // int count = 0;
        // int originalIndex = graph.length - 6;

        // int[] aValues = new int[] { originalIndex, originalIndex + 1 };
        // int[] bValues = new int[] { originalIndex + 2, originalIndex + 3 };
        // int[] cValues = new int[] { originalIndex + 4, originalIndex + 5 };

        // Set<Integer> visitedKeys = new HashSet<>();

        // Set<Integer> aValues = new HashSet<>();
        // aValues.add(originalIndex);
        // aValues.add(originalIndex + 1);

        // Set<Integer> bValues = new HashSet<>();
        // aValues.add(originalIndex + 2);
        // aValues.add(originalIndex + 3);

        // Set<Integer> cValues = new HashSet<>();
        // aValues.add(originalIndex + 4);
        // aValues.add(originalIndex + 5);

        // while (count < 3) {
        // int indexOfLongest = graph.length - 6;
        // // find longest key list
        // for (int i = originalIndex; i < graph.length; i++) {
        // if (graph[i].size() > graph[indexOfLongest].size()) {
        // System.out.println("Index: " + i + " longest: " + indexOfLongest);
        // System.out.println("In if. current size: " + graph[i].size() + " largest
        // size: "
        // + graph[indexOfLongest].size());
        // // check to make sure only one key node is visited (ex. if a is visited,
        // don't
        // // visit ~a)
        // // if (i%2 == 0) {
        // // for (int j = 0; j < keysVisited.length; j++) {
        // // if ( (i - 1) != keysVisited[j]) {

        // // }
        // // }
        // // }
        // // for (int j = 0; j < 2; j++) {
        // // if (visitedKeys.contains(aValues[j]) || visitedKeys.contains(bValues[j])
        // ||
        // // visitedKeys.contains(cValues[j]) ) }{

        // // }
        // // }
        // switch (i) {
        // case (i = originalIndex):
        // break;
        // }
        // indexOfLongest = i;
        // // for (int j = 0; j < keysVisited.length; j++) {
        // // if (i != keysVisited[j] && ((keysVisited[j] - 1) == i || (keysVisited[j] +
        // 1)
        // // == i)) {
        // // indexOfLongest = i;
        // // }
        // // }
        // }
        // }
        // keysVisited[count] = indexOfLongest;
        // // iterate through longest k list and remove all subsequent edges
        // Iterator<Integer> iterator = graph[indexOfLongest].iterator();
        // while (iterator.hasNext()) {
        // // int index = iterator.next();
        // graph[indexOfLongest].remove(0);
        // // graph[index].remove(graph[index].indexOf(indexOfLongest));
        // }
        // count++;
        // }

        // // repeat process for the nodes -- don't need to check if it is already
        // visited

        // int lenKeysVisited = keysVisited.length;
        // int lenNodesVisited = nodesVisited.length;
        // int newArrLen = lenKeysVisited + lenNodesVisited;
        // int[] vertexCover = new int[newArrLen];

        // // merge arrays
        // for (int i = 0; i < lenKeysVisited; i++) {
        // vertexCover[i] = keysVisited[i];
        // }
        // for (int i = 0; i < lenNodesVisited; i++) {
        // vertexCover[lenKeysVisited] = nodesVisited[i];
        // lenKeysVisited++;
        // }
        int[] vertexCover = new int[k];

        int count = 0;
        int indexOfLongest = 0;

        while (count < k) {

            for (int i = 0; i < vertexCover.length; i++) {
                if (indexOfLongest == vertexCover[i]) {
                    indexOfLongest++;
                    continue;
                }
            }

            // while (graph[indexOfLongest] == null) {
            // System.out.println("Test - " + indexOfLongest);
            // indexOfLongest++;
            // }
            for (int i = 0; i < graph.length; i++) {
                if (graph[i] != null && graph[i].size() > graph[indexOfLongest].size()) {
                    indexOfLongest = i;
                }
            }

            vertexCover[count] = indexOfLongest;

            Iterator<Integer> iterator = graph[indexOfLongest].iterator();
            while (iterator.hasNext()) {
                int index = iterator.next();
                iterator.remove();
                graph[index].remove(Integer.valueOf(indexOfLongest));
            }

            // vertexCover[count] = indexOfLongest;
            count++;
        }

        return vertexCover;
    }

    public static void main(String args[]) {
        LinkedList<Integer> graph[] = new LinkedList[15];

        graph[0] = new LinkedList();
        graph[1] = new LinkedList();
        graph[2] = new LinkedList();
        graph[3] = new LinkedList();

        graph[0].add(1);
        graph[0].add(2);
        graph[0].add(3);

        graph[1].add(0);
        graph[1].add(2);

        graph[2].add(0);
        graph[2].add(1);

        graph[3].add(0);

        // graph[4] = new LinkedList();
        // graph[5] = new LinkedList();
        // graph[6] = new LinkedList();
        // graph[7] = new LinkedList();
        // graph[8] = new LinkedList();
        // graph[9] = new LinkedList();
        // graph[10] = new LinkedList();
        // graph[11] = new LinkedList();
        // graph[12] = new LinkedList();
        // graph[13] = new LinkedList();
        // graph[14] = new LinkedList();

        // graph[0].add(9);
        // graph[0].add(3);
        // graph[0].add(1);

        // graph[1].add(11);
        // graph[1].add(0);
        // graph[1].add(3);

        // graph[2].add(11);
        // graph[2].add(7);
        // graph[2].add(5);

        // graph[3].add(13);
        // graph[3].add(0);
        // graph[3].add(1);

        // graph[4].add(13);
        // graph[4].add(6);
        // graph[4].add(8);

        // graph[5].add(13);
        // graph[5].add(7);
        // graph[5].add(2);

        // graph[6].add(10);
        // graph[6].add(4);
        // graph[6].add(8);

        // graph[7].add(10);
        // graph[7].add(2);
        // graph[7].add(5);

        // graph[8].add(12);
        // graph[8].add(4);
        // graph[8].add(6);

        // graph[9].add(10);
        // graph[9].add(0);

        // graph[10].add(9);
        // graph[10].add(6);
        // graph[10].add(7);

        // graph[11].add(12);
        // graph[11].add(1);
        // graph[11].add(2);

        // graph[12].add(11);
        // graph[12].add(8);

        // graph[13].add(14);
        // graph[13].add(3);
        // graph[13].add(4);
        // graph[13].add(5);

        // graph[14].add(13);

        // print array lists
        // for (int i = 0; i < graph.length; i++) {
        // for (int j = 0; j < graph[i].size(); j++) {
        // System.out.print(graph[i].get(j) + " -> ");
        // }
        // System.out.print("\n");
        // }

        int k = 3;
        int[] vertexCover = findVertexCover(graph, k);
        for (int i = 0; i < vertexCover.length; i++) {
            System.out.println(vertexCover[i]);
        }
    }
}