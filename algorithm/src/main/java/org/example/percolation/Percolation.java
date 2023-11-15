package org.example.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] grid;
  private final int gridSize;
  private int openSites;
  private final WeightedQuickUnionUF uf;
  
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Grid size must be greater than 0.");
    }
    
    gridSize = n;
    grid = new boolean[n][n]; 
    uf = new WeightedQuickUnionUF(n * n + 2);
    openSites = 0;
  }
  
  public void open(int row, int col) {
    if (!isValid(row, col)) {
      return; 
    }
    int index = indexFor(row, col);
    if (!isOpen(row, col)) {
      grid[row - 1][col - 1] = true;
      openSites++;
      if (row == 1) {
        uf.union(index, 0);
      }
      if (row == gridSize) {
        uf.union(index, gridSize * gridSize + 1);
      }
      if (row > 1 && isOpen(row - 1, col)) {
        uf.union(index, indexFor(row - 1, col));
      }
      if (row < gridSize && isOpen(row + 1, col)) {
        uf.union(index, indexFor(row + 1, col));
      }
      if (col > 1 && isOpen(row, col - 1)) {
        uf.union(index, indexFor(row, col - 1));
      }
      if (col < gridSize && isOpen(row, col + 1)) {
        uf.union(index, indexFor(row, col + 1));
      }
    }
  }
  
  public boolean isOpen(int row, int col) {
    if (!isValid(row, col)) {
      return false;
    }
    return grid[row - 1][col - 1];
  }
  
  public boolean isFull(int row, int col) {
    if (!isValid(row, col)) {
      return false;
    }
    return uf.find(0) == uf.find(indexFor(row, col));
  }
  
  public int numberOfOpenSites() {
    return openSites;
  }
  
  public boolean percolates() {
    return uf.find(0) == uf.find(gridSize * gridSize + 1);
  }
  
  private int indexFor(int row, int col) {
    return (row - 1) * gridSize + col;
  }
  
  private boolean isValid(int row, int col) {
    return row >= 1 && row <= gridSize && col >= 1 && col <= gridSize;
  }
}
