package com.mentarirvmp.utils;

import java.util.ArrayList;

public class Vertex {
  Expenses data; 
  ArrayList<Vertex> adjacentVertices = new ArrayList<>();
  public Vertex(Expenses expense){
    this.data = expense; 
  }

  public void addDirectedEdgeToVertex(Vertex vertex){
    this.adjacentVertices.add(vertex); 
  } 


}
