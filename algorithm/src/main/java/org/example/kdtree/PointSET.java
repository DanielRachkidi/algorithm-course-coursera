package org.example.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET
{
  private final SET<Point2D> points;
  
  public PointSET()
  {
    points = new SET<>();
  }
  
  public boolean isEmpty()
  {
    return points.isEmpty();
  }
  
  public int size()
  {
    return points.size();
  }
  
  public void insert(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    points.add(p);
  }
  
  public boolean contains(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    return points.contains(p);
  }
  
  public void draw()
  {
    for (Point2D point : points)
    {
      point.draw();
    }
  }
  
  public Iterable<Point2D> range(RectHV rect)
  {
    if (rect == null)
    {
      throw new IllegalArgumentException("Rectangle cannot be null");
    }
    
    SET<Point2D> rangePoints = new SET<>();
    for (Point2D point : points)
    {
      if (rect.contains(point))
      {
        rangePoints.add(point);
      }
    }
    return rangePoints;
  }
  
  public Point2D nearest(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    
    Point2D nearestPoint = null;
    double minDistance = Double.POSITIVE_INFINITY;
    
    for (Point2D point : points)
    {
      double distance = p.distanceSquaredTo(point);
      if (distance < minDistance)
      {
        minDistance = distance;
        nearestPoint = point;
      }
    }
    
    return nearestPoint;
  }
  
  public static void main(String[] args)
  {
    PointSET pointSet = new PointSET();
    
    // Insert some points
    pointSet.insert(new Point2D(0.7, 0.2));
    pointSet.insert(new Point2D(0.5, 0.4));
    pointSet.insert(new Point2D(0.2, 0.3));
    pointSet.insert(new Point2D(0.4, 0.7));
    pointSet.insert(new Point2D(0.9, 0.6));
    
    // Check if a point is in the set
    Point2D queryPoint = new Point2D(0.5, 0.4);
    System.out.println("Contains " + queryPoint + "? " + pointSet.contains(queryPoint));
    
    // Range search
    RectHV queryRect = new RectHV(0.3, 0.3, 0.8, 0.8);
    System.out.println("Points in range: ");
    for (Point2D point : pointSet.range(queryRect))
    {
      System.out.println(point);
    }
    
    // Nearest neighbor
    Point2D nearestPoint = pointSet.nearest(new Point2D(0.6, 0.5));
    System.out.println("Nearest point: " + nearestPoint);
  }
}