package org.example.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver
{
  
  private MinPQ<SearchNode> pq;
  private MinPQ<SearchNode> twinPq;
  private boolean solvable;
  private int moves;
  private Stack<Board> solution;
  
  public Solver(Board initial)
  {
    if (initial == null)
    {
      throw new IllegalArgumentException("Initial board cannot be null");
    }
    
    pq = new MinPQ<>();
    twinPq = new MinPQ<>();
    solvable = false;
    moves = -1;
    solution = new Stack<>();
    
    SearchNode initialNode = new SearchNode(initial, null, 0);
    pq.insert(initialNode);
    
    Board twinBoard = initial.twin();
    SearchNode twinInitialNode = new SearchNode(twinBoard, null, 0);
    twinPq.insert(twinInitialNode);
    
    solve();
  }
  
  private void solve()
  {
    while (!pq.isEmpty() && !twinPq.isEmpty())
    {
      SearchNode current = pq.delMin();
      SearchNode twinCurrent = twinPq.delMin();
      
      if (current.board.isGoal())
      {
        solvable = true;
        moves = current.moves;
        reconstructSolution(current);
        break;
      }
      
      if (twinCurrent.board.isGoal())
      {
        solvable = false;
        moves = -1;
        break;
      }
      
      enqueueNeighbors(current, pq);
      enqueueNeighbors(twinCurrent, twinPq);
    }
  }
  
  private void enqueueNeighbors(SearchNode node, MinPQ<SearchNode> priorityQueue)
  {
    for (Board neighbor : node.board.neighbors())
    {
      if (node.previous == null || !neighbor.equals(node.previous.board))
      {
        priorityQueue.insert(new SearchNode(neighbor, node, node.moves + 1));
      }
    }
  }
  
  private void reconstructSolution(SearchNode node)
  {
    Stack<Board> reverseSolution = new Stack<>();
    while (node != null)
    {
      reverseSolution.push(node.board);
      node = node.previous;
    }
    
    while (!reverseSolution.isEmpty())
    {
      solution.push(reverseSolution.pop());
    }
  }
  
  public boolean isSolvable()
  {
    return solvable;
  }
  
  public int moves()
  {
    return moves;
  }
  
  public Iterable<Board> solution()
  {
    return solution;
  }
  
  private static class SearchNode
    implements Comparable<SearchNode>
  {
    private final Board board;
    private final SearchNode previous;
    private final int moves;
    private final int priority;
    
    public SearchNode(Board board, SearchNode previous, int moves)
    {
      this.board = board;
      this.previous = previous;
      this.moves = moves;
      this.priority = board.manhattan() + moves;
    }
    
    @Override
    public int compareTo(SearchNode other)
    {
      return Integer.compare(this.priority, other.priority);
    }
  }
  
  public static void main(String[] args)
  {
    int[][] initialTiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
    Board initialBoard = new Board(initialTiles);
    
    Solver solver = new Solver(initialBoard);
    
    if (solver.isSolvable())
    {
      System.out.println("Minimum number of moves = " + solver.moves());
      System.out.println("Solution:");
      for (Board board : solver.solution())
      {
        System.out.println(board);
      }
    }
    else
    {
      System.out.println("No solution possible");
    }
  }
}

