package org.example.kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.SET;

public class KdTree
{
  private Node root;
  private int size;
  
  private static class Node
  {
    private Point2D point;
    private RectHV rect;
    private Node left;
    private Node right;
    
    public Node(Point2D point, RectHV rect)
    {
      this.point = point;
      this.rect = rect;
    }
  }
  
  public KdTree()
  {
    root = null;
    size = 0;
  }
  
  public boolean isEmpty()
  {
    return size == 0;
  }
  
  public int size()
  {
    return size;
  }
  
  public void insert(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    root = insert(root, p, true, new RectHV(0, 0, 1, 1));
  }
  
  private Node insert(Node node, Point2D p, boolean useX, RectHV rect)
  {
    if (node == null)
    {
      size++;
      return new Node(p, rect);
    }
    
    if (node.point.equals(p))
    {
      return node;
    }
    
    int cmp = useX ? Double.compare(p.x(), node.point.x()) : Double.compare(p.y(), node.point.y());
    
    if (cmp < 0)
    {
      RectHV leftRect = useX ? new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax())
        : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
      node.left = insert(node.left, p, !useX, leftRect);
    }
    else
    {
      RectHV rightRect = useX ? new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax())
        : new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
      node.right = insert(node.right, p, !useX, rightRect);
    }
    
    return node;
  }
  
  public boolean contains(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    return contains(root, p, true);
  }
  
  private boolean contains(Node node, Point2D p, boolean useX)
  {
    while (node != null)
    {
      if (node.point.equals(p))
      {
        return true;
      }
      
      int cmp = useX ? Double.compare(p.x(), node.point.x()) : Double.compare(p.y(), node.point.y());
      
      if (cmp < 0)
      {
        node = node.left;
      }
      else
      {
        node = node.right;
      }
      
      useX = !useX;
    }
    
    return false;
  }
  
  public void draw()
  {
    draw(root, true);
  }
  
  private void draw(Node node, boolean useX)
  {
    if (node != null)
    {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      node.point.draw();
      
      StdDraw.setPenRadius();
      
      if (useX)
      {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
      }
      else
      {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
      }
      
      draw(node.left, !useX);
      draw(node.right, !useX);
    }
  }
  
  public Iterable<Point2D> range(RectHV rect)
  {
    if (rect == null)
    {
      throw new IllegalArgumentException("Rectangle cannot be null");
    }
    
    SET<Point2D> rangePoints = new SET<>();
    range(root, rect, rangePoints);
    return rangePoints;
  }
  
  private void range(Node node, RectHV rect, SET<Point2D> rangePoints)
  {
    if (node != null && rect.intersects(node.rect))
    {
      if (rect.contains(node.point))
      {
        rangePoints.add(node.point);
      }
      
      range(node.left, rect, rangePoints);
      range(node.right, rect, rangePoints);
    }
  }
  
  public Point2D nearest(Point2D p)
  {
    if (p == null)
    {
      throw new IllegalArgumentException("Point cannot be null");
    }
    
    if (isEmpty())
    {
      return null;
    }
    
    return nearest(root, p, true, root.point);
  }
  
  private Point2D nearest(Node node, Point2D queryPoint, boolean useX, Point2D champion)
  {
    if (node == null)
    {
      return champion;
    }
    
    if (node.rect.distanceSquaredTo(queryPoint) < champion.distanceSquaredTo(queryPoint))
    {
      if (node.point.distanceSquaredTo(queryPoint) < champion.distanceSquaredTo(queryPoint))
      {
        champion = node.point;
      }
      
      int cmp = useX ? Double.compare(queryPoint.x(), node.point.x()) : Double.compare(queryPoint.y(), node.point.y());
      
      if (cmp < 0)
      {
        champion = nearest(node.left, queryPoint, !useX, champion);
        champion = nearest(node.right, queryPoint, !useX, champion);
      }
      else
      {
        champion = nearest(node.right, queryPoint, !useX, champion);
        champion = nearest(node.left, queryPoint, !useX, champion);
      }
    }
    
    return champion;
  }
  
  public static void main(String[] args)
  {
    KdTree kdTree = new KdTree();
    
    // Insert some points
    kdTree.insert(new Point2D(0.7, 0.2));
    kdTree.insert(new Point2D(0.5, 0.4));
    kdTree.insert(new Point2D(0.2, 0.3));
    kdTree.insert(new Point2D(0.4, 0.7));
    kdTree.insert(new Point2D(0.9, 0.6));
    
    // Check if a point is in the set
    Point2D queryPoint = new Point2D(0.5, 0.4);
    System.out.println("Contains " + queryPoint + "? " + kdTree.contains(queryPoint));
    
    // Range search
    RectHV queryRect = new RectHV(0.3, 0.3, 0.8, 0.8);
    System.out.println("Points in range: ");
    for (Point2D point : kdTree.range(queryRect))
    {
      System.out.println(point);
    }
    
    // Nearest neighbor
    Point2D nearestPoint = kdTree.nearest(new Point2D(0.6, 0.5));
    System.out.println("Nearest point: " + nearestPoint);
  }
}