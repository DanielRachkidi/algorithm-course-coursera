package org.example.queues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("Please provide the number of items to sample");
    }
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      randomizedQueue.enqueue(item);
    }
    for (int i = 0; i < k; i++) {
      System.out.println(randomizedQueue.dequeue());
    }
  }
}

