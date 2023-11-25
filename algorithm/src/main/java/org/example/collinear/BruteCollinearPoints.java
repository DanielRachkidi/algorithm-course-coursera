package org.example.collinear;

import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class BruteCollinearPoints
{
  private LineSegment[] segments;
  private int numberOfSegments;
  
  public BruteCollinearPoints(Point[] points)
  {
    if (points == null)
    {
      throw new IllegalArgumentException("Points array cannot be null");
    }
    
    int n = points.length;
    Point[] copy = Arrays.copyOf(points, n);
    
    Arrays.sort(copy);
    
    checkDuplicate(copy);
    
    segments = new LineSegment[n];
    numberOfSegments = 0;
    
    findCollinearSegments(copy);
  }
  
  private void findCollinearSegments(Point[] points)
  {
    int n = points.length;
    
    for (int i = 0; i < n; i++)
    {
      for (int j = i + 1; j < n; j++)
      {
        for (int k = j + 1; k < n; k++)
        {
          for (int l = k + 1; l < n; l++)
          {
            double slope1 = points[i].slopeTo(points[j]);
            double slope2 = points[i].slopeTo(points[k]);
            double slope3 = points[i].slopeTo(points[l]);
            
            if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0)
            {
              segments[numberOfSegments++] = new LineSegment(points[i], points[l]);
            }
          }
        }
      }
    }
  }
  
  private void checkDuplicate(Point[] points)
  {
    int n = points.length;
    for (int i = 0; i < n - 1; i++)
    {
      if (points[i].compareTo(points[i + 1]) == 0)
      {
        throw new IllegalArgumentException("Duplicate points are not allowed");
      }
    }
  }
  
  public int numberOfSegments()
  {
    return numberOfSegments;
  }
  

  
  public LineSegment[] segments() {
    return Arrays.copyOf(segments, numberOfSegments);
  }
  
  
  public static void main(String[] args)
  {
    Point[] points = {
      new Point(1, 1),
      new Point(2, 2),
      new Point(3, 3),
      new Point(4, 4)
    };
    
    BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
    
    System.out.println("Number of segments: " + collinearPoints.numberOfSegments());
    
    for (LineSegment segment : collinearPoints.segments())
    {
      System.out.println("Segment: " + segment);
    }
    
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    
    for (Point p : points)
    {
      p.draw();
    }
    
    StdDraw.show();
  }
}
