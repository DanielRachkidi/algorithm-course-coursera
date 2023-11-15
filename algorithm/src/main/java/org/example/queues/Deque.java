package org.example.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
  private Node first;
  private Node last;
  private int size;
  
  private class Node {
    Item item;
    Node next;
    Node prev;
  }
  
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public int size() {
    return size;
  }
  
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    Node newNode = new Node();
    newNode.item = item;
    newNode.next = first;
    if (first != null) {
      first.prev = newNode;
    } else {
      last = newNode;
    }
    first = newNode;
    size++;
  }
  
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    Node newNode = new Node();
    newNode.item = item;
    newNode.prev = last;
    if (last != null) {
      last.next = newNode;
    } else {
      first = newNode;
    }
    last = newNode;
    size++;
  }
  
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException("Deque is empty");
    }
    Item item = first.item;
    first = first.next;
    if (first != null) {
      first.prev = null;
    } else {
      last = null;
    }
    size--;
    return item;
  }
  
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException("Deque is empty");
    }
    Item item = last.item;
    last = last.prev;
    if (last != null) {
      last.next = null;
    } else {
      first = null;
    }
    size--;
    return item;
  }
  
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }
  
  private class DequeIterator implements Iterator<Item> {
    private Node current = first;
    
    public boolean hasNext() {
      return current != null;
    }
    
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the deque");
      }
      Item item = current.item;
      current = current.next;
      return item;
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Remove operation is not supported");
    }
  }
  
  public static void main(String[] args) {
    Deque<String> deque = new Deque<>();
    
    deque.addFirst("1");
    deque.addLast("2");
    deque.addFirst("3");
    
    System.out.println("Deque size: " + deque.size());
    
    System.out.println("Deque items (from first to last):");
    for (String item : deque) {
      System.out.println(item);
    }
    
    System.out.println("Removed first item: " + deque.removeFirst());
    System.out.println("Removed last item: " + deque.removeLast());
    
    System.out.println("Updated Deque size: " + deque.size());
    for (String item : deque) {
      System.out.println(item);
    }
  }
  

}
