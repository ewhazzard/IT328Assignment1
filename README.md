# Assignment 1 IT 328 Fall 2022
  * Group Members:
    * Audra Heistand
    * Matthew Tobeck
    * Evan Hazzard
  * Assignment Description:
    * Create three seperate Java programs that acheive the three requirements listed below
      1. Write a method that takes an undirected graph G : (V, E) (in whatever presentation the team decided) and an integer 1 ≤ k ≤ |V | as inputs of the method and returns a k-vertex
        cover, if such k-vertex cover exists in G.
      2. Write a method that takes an 3CNF φ (in whatever presentation the team decided)
          as the input and returns an assignment that makes φ true, if such assignment exists. However, Student
          B should not solve the problem directly. Instead, he/she should reduce the problem in polynomial
          time to a corresponding k-vertex cover problem for Student A’s method to solve and use the results
          (some k-vertex cover, if exists) to answer the original 3CNF problem. In other words, Student B
          should implement the Cook-reduction that reduces 3SAT to Vertex-Cover.
      3. Write a method that takes an undirected graph G : (V, E) (in whatever presenta-
          tion the team decided) and an integer 1 ≤ n ≤ |V | as inputs of the method and return a n-clique, if
          such n-clique exists in G. As Student B, this method should not solve the problem directly. Instead,
          he/she should reduce the problem in polynomial time to a corresponding k-vertex cover problem
          for Student A’s method to solve and use the results (some k-vertex cover, if exists) to answer the
          original n-clique problem. In other words, Student C should implement the Cook-reduction that
          reduces Clique to Vertex-Cover.
    * There are more instructions in the PDF located in the repository
  * File Formats:
    * There are two text files provided
      1. Graph file: graphs2022.txt
         In this text file, there are a sequence of graphs. A graph G : (V, E) with |V | = n is stored in (n + 1)
          lines. For each graph, the first line contains a number n to indicate the size of V . The following n
          lines represent the adjacency matrix m of G (i.e., m is an n × n two dimensional array). If the entry
          m[i][j] = 1, that means (i, j) ∈ E; if m[i][j] = 0 means (i, j) 6 ∈ E.
          One can see that all matrices in this file are symmetric, i.e., for every 0 ≤ i, j < n we have m[i][j] =
          m[j][i], which mean undirected. By convention of undirected graphs, if m[i][j] = m[j][i] = 1, we
          count as one edge in E. Also note that, the diagonal of the matrix is set to 1, i.e. m[i][i] = 1 as we
          consider every vertex links to itself but we do not count it as an edges, thus (v, v) does not contribute
          to the edge count, |E|. The file is ended by an empty graph, i.e., n = 0.
    2. 3CNF file: cnfs2022.txt
        Each line in this file is a 3CNF, where positive numbers 1, 2, . . . representing boolean variables a1, a2,
        . . . and negative numbers −1, −2, . . . representing negation of boolean variables, ¬a1, ¬a2, . . .. These
        numbers are grouped 3 by 3, and each group is a clause. For example, a line
        1 3 -2 3 1 4 -4 5 1
        represents 3-CNF:
        (a1 ∨ a3 ∨ ¬a2) ∧ (a3 ∨ a1 ∨ a4) ∧ (¬a4 ∨ a5 ∨ a1).
        Hint: you can use the largest index to indicate the number of variables. In the example above, it
        is a5, and therefore the number of variables is 5. It should not be a problem if some variables with
        smaller indices are missing from the formula.
        
