package org.example.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double[] thresholds;
  private final int trials;
  
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Grid size and number of trials must be greater than 0.");
    }
    
    this.trials = trials;
    thresholds = new double[trials];
    
    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      while (!percolation.percolates()) {
        int row = StdRandom.uniform(n) + 1;
        int col = StdRandom.uniform(n) + 1;
        percolation.open(row, col);
      }
      thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
    }
  }
  
  public double mean() {
    return StdStats.mean(thresholds);
  }
  
  public double stddev() {
    return StdStats.stddev(thresholds);
  }
  
  public double confidenceLo() {
    return mean() - (1.96 * stddev() / Math.sqrt(trials));
  }
  
  public double confidenceHi() {
    return mean() + (1.96 * stddev() / Math.sqrt(trials));
  }
  
  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java PercolationStats <grid_size> <number_of_trials>");
      return;
    }
  
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
  
    if (n <= 0 || trials <= 0) {
      System.err.println("Grid size and number of trials must be greater than 0.");
      return;
    }
  
    PercolationStats stats = new PercolationStats(n, trials);
    System.out.println("Mean                    = " + stats.mean());
    System.out.println("Stddev                  = " + stats.stddev());
    System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }
  
  
}
