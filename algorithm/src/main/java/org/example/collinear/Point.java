package org.example.collinear;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {
  private final int x;
  private final int y;
  
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public void draw() {
    StdDraw.point(x, y);
  }
  
  public void drawTo(Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }
  
  public int compareTo(Point that) {
    if (this.y == that.y) {
      return Integer.compare(this.x, that.x);
    }
    return Integer.compare(this.y, that.y);
  }
  
  public double slopeTo(Point that) {
    if (this.x == that.x) {
      if (this.y == that.y) {
        return Double.NEGATIVE_INFINITY;
      }
      return Double.POSITIVE_INFINITY;
    }
    if (this.y == that.y) {
      return 0.0;
    }
    return (double) (that.y - this.y) / (that.x - this.x);
  }
  
  public Comparator<Point> slopeOrder() {
    return Comparator.comparingDouble(this::slopeTo);
  }
  
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
  
  public static void main(String[] args) {
    Point point1 = new Point(1, 2);
    Point point2 = new Point(6, 5);
    
    StdOut.println("Point 1: " + point1);
    StdOut.println("Point 2: " + point2);
    
    double slope = point1.slopeTo(point2);
    StdOut.println("Slope between Point 1 and Point 2: " + slope);
  }
}
