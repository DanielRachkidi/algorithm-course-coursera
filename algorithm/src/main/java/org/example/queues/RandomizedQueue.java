package org.example.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int size;
  
  public RandomizedQueue() {
    items = (Item[]) new Object[2];
    size = 0;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public int size() {
    return size;
  }
  
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    if (size == items.length) {
      resize(2 * items.length);
    }
    items[size++] = item;
  }
  
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("RandomizedQueue is empty");
    }
    int randomIndex = StdRandom.uniform(size);
    Item item = items[randomIndex];
    items[randomIndex] = items[--size];
    items[size] = null; // avoid loitering
    if (size > 0 && size == items.length / 4) {
      resize(items.length / 2);
    }
    return item;
  }
  
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("RandomizedQueue is empty");
    }
    int randomIndex = StdRandom.uniform(size);
    return items[randomIndex];
  }
  
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }
  
  private class RandomizedQueueIterator implements Iterator<Item> {
    private int current;
    private final int[] shuffledIndices;
    
    public RandomizedQueueIterator() {
      current = 0;
      shuffledIndices = new int[size];
      for (int i = 0; i < size; i++) {
        shuffledIndices[i] = i;
      }
      StdRandom.shuffle(shuffledIndices);
    }
    
    public boolean hasNext() {
      return current < size;
    }
    
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the randomized queue");
      }
      return items[shuffledIndices[current++]];
    }
    
    public void remove() {
      throw new UnsupportedOperationException("Remove operation is not supported");
    }
  }
  
  private void resize(int capacity) {
    Item[] newArray = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) {
      newArray[i] = items[i];
    }
    items = newArray;
  }
  
  public static void main(String[] args) {
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    
    randomizedQueue.enqueue("Item 1");
    randomizedQueue.enqueue("Item 2");
    randomizedQueue.enqueue("Item 3");
    
    System.out.println("RandomizedQueue size: " + randomizedQueue.size());
    
    System.out.println("RandomizedQueue items (sampled):");
    for (String item : randomizedQueue) {
      System.out.println(randomizedQueue.sample());
    }
    
    System.out.println("Removed item: " + randomizedQueue.dequeue());
    
    System.out.println("Updated RandomizedQueue size: " + randomizedQueue.size());
  
    for (String item : randomizedQueue) {
      System.out.println(randomizedQueue.sample());
    }
  }
  

}
