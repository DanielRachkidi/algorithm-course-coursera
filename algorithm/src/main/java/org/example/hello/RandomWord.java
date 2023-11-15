package org.example.hello;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void main(String[] args) {
    int i = 0;
    String word = "";
    while (!StdIn.isEmpty()) {
      String currentWord = StdIn.readString();
      i++;
      if (StdRandom.bernoulli(1.0 / i)) {
        word = currentWord;
      }
    }
    System.out.println(word);
  }
}