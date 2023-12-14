package org.example.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdOut;

public class Board
{
  private final int[][] tiles;
  private final int dimension;
  
  public Board(int[][] tiles)
  {
    if (tiles == null)
    {
      throw new IllegalArgumentException("Tiles array cannot be null");
    }
    this.dimension = tiles.length;
    this.tiles = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        this.tiles[i][j] = tiles[i][j];
      }
    }
  }
  
  public int dimension()
  {
    return dimension;
  }
  
  public int hamming()
  {
    int hammingDistance = 0;
    int goalValue = 1;
    
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        if (tiles[i][j] != goalValue++ && tiles[i][j] != 0)
        {
          hammingDistance++;
        }
      }
    }
    
    return hammingDistance;
  }
  
  public int manhattan()
  {
    int manhattanDistance = 0;
    
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        if (tiles[i][j] != 0)
        {
          int expectedRow = (tiles[i][j] - 1) / dimension;
          int expectedCol = (tiles[i][j] - 1) % dimension;
          manhattanDistance += Math.abs(i - expectedRow) + Math.abs(j - expectedCol);
        }
      }
    }
    
    return manhattanDistance;
  }
  
  public boolean isGoal()
  {
    return hamming() == 0;
  }
  
  public boolean equals(Object y)
  {
    if (this == y)
    {
      return true;
    }
    if (y == null || getClass() != y.getClass())
    {
      return false;
    }
    
    Board other = (Board) y;
    
    return Arrays.deepEquals(tiles, other.tiles);
  }
  
  public Iterable<Board> neighbors()
  {
    List<Board> neighbors = new ArrayList<>();
    
    int[] zeroPosition = findZeroPosition();
    
    int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    for (int[] direction : directions)
    {
      int newRow = zeroPosition[0] + direction[0];
      int newCol = zeroPosition[1] + direction[1];
      
      if (isValid(newRow, newCol))
      {
        neighbors.add(createNeighbor(newRow, newCol, zeroPosition[0], zeroPosition[1]));
      }
    }
    
    return neighbors;
  }
  
  public Board twin()
  {
    int[][] twinTiles = copyTiles();
    if (tiles[0][0] != 0 && tiles[0][1] != 0)
    {
      swap(twinTiles, 0, 0, 0, 1);
    }
    else
    {
      swap(twinTiles, 1, 0, 1, 1);
    }
    return new Board(twinTiles);
  }
  
  public String toString()
  {
    StringBuilder s = new StringBuilder();
    s.append(dimension).append("\n");
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        s.append(String.format("%2d ", tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }
  
  private int[] findZeroPosition()
  {
    int[] position = new int[2];
    for (int i = 0; i < dimension; i++)
    {
      for (int j = 0; j < dimension; j++)
      {
        if (tiles[i][j] == 0)
        {
          position[0] = i;
          position[1] = j;
          return position;
        }
      }
    }
    return position;
  }
  
  private boolean isValid(int row, int col)
  {
    return row >= 0 && row < dimension && col >= 0 && col < dimension;
  }
  
  private Board createNeighbor(int newRow, int newCol, int zeroRow, int zeroCol)
  {
    int[][] neighborTiles = copyTiles();
    swap(neighborTiles, newRow, newCol, zeroRow, zeroCol);
    return new Board(neighborTiles);
  }
  
  private int[][] copyTiles()
  {
    int[][] copy = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++)
    {
      System.arraycopy(tiles[i], 0, copy[i], 0, dimension);
    }
    return copy;
  }
  
  private void swap(int[][] array, int row1, int col1, int row2, int col2)
  {
    int temp = array[row1][col1];
    array[row1][col1] = array[row2][col2];
    array[row2][col2] = temp;
  }
  
  public static void main(String[] args) {
    int[][] tiles = {{1, 2, 3}, {4, 0, 6}, {8, 7, 5}};
    Board board = new Board(tiles);
    
    StdOut.println("Original Board:");
    StdOut.println(board);
    
    StdOut.println("Hamming Distance: " + board.hamming());
    StdOut.println("Manhattan Distance: " + board.manhattan());
    StdOut.println("Is Goal: " + board.isGoal());
    
    StdOut.println("\nNeighboring Boards:");
    for (Board neighbor : board.neighbors()) {
      StdOut.println(neighbor);
    }
    
    StdOut.println("\nTwin Board:");
    Board twin = board.twin();
    StdOut.println(twin);
  }
}