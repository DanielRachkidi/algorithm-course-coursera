package org.example.collinear;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints
{
  private final Point[] points;
  private int numberOfSegments;
  private LineSegment[] segments;
  
  public FastCollinearPoints(Point[] points)
  {
    if (points == null)
    {
      throw new IllegalArgumentException("Points array cannot be null.");
    }
    
    this.points = Arrays.copyOf(points, points.length);
    checkNullPoints();
    checkDuplicatePoints();
    
    numberOfSegments = 0;
    segments = new LineSegment[2];
    findCollinearSegments();
  }
  
  public int numberOfSegments()
  {
    return numberOfSegments;
  }
  
  public LineSegment[] segments()
  {
    return Arrays.copyOf(segments, numberOfSegments);
  }
  
  private void findCollinearSegments()
  {
    int n = points.length;
    Point[] sortedPoints = Arrays.copyOf(points, n);
    
    for (int i = 0; i < n; i++)
    {
      Point p = points[i];
      Arrays.sort(sortedPoints, p.slopeOrder());
      
      for (int j = 1; j < n - 2; )
      {
        double slopeRef = p.slopeTo(sortedPoints[j]);
        int count = 1;
        
        while (j + count < n && Double.compare(slopeRef, p.slopeTo(sortedPoints[j + count])) == 0)
        {
          count++;
        }
        
        if (count >= 3)
        {
          Point[] collinearPoints = new Point[count + 1];
          collinearPoints[0] = p;
          for (int k = 0; k < count; k++)
          {
            collinearPoints[k + 1] = sortedPoints[j + k];
          }
          
          Arrays.sort(collinearPoints);
          if (p == collinearPoints[0])
          {
            addSegment(new LineSegment(collinearPoints[0], collinearPoints[count]));
          }
        }
        
        j += count;
      }
    }
  }
  
  private void addSegment(LineSegment segment)
  {
    if (numberOfSegments == segments.length)
    {
      resizeSegmentsArray();
    }
    segments[numberOfSegments++] = segment;
  }
  
  private void resizeSegmentsArray()
  {
    segments = Arrays.copyOf(segments, segments.length * 2);
  }
  
  private void checkNullPoints()
  {
    for (Point point : points)
    {
      if (point == null)
      {
        throw new IllegalArgumentException("One of the points in the array is null.");
      }
    }
  }
  
  private void checkDuplicatePoints()
  {
    Arrays.sort(points);
    for (int i = 1; i < points.length; i++)
    {
      if (points[i - 1].compareTo(points[i]) == 0)
      {
        throw new IllegalArgumentException("Array contains duplicate points.");
      }
    }
  }
  
  
  
  public static void main(String[] args)
  {
    
    Point[] points = {
      new Point(1, 1),
      new Point(2, 2),
      new Point(3, 3),
      new Point(4, 4),
      new Point(1, 2),
      new Point(2, 1),
      new Point(3, 1),
      new Point(1, 3)
    };
    
    FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
    for (LineSegment segment : collinearPoints.segments())
    {
      StdOut.println(segment);
      segment.draw();
    }
    
    StdDraw.show();
  }
}
