package com.mentarirvmp.utils;

import java.util.ArrayList;

public class Vertex {
  private Expenses data; 
  private int indegree = 0; 
  private ArrayList<Vertex> adjacentVertexList = new ArrayList<Vertex>();

  public Vertex(Expenses expense){
    this.data = expense; 
  } 

  public void addDirectedEdgeToward(Vertex expenseVertex){
    this.adjacentVertexList.add(expenseVertex);
    expenseVertex.incrementIndegree();
  } 

  public ArrayList<Vertex> getAdjacentVertexList(){
    return this.adjacentVertexList;
  }

  protected void setAdjacentVertexList(ArrayList<Vertex> adjacentVertexList ){
    this.adjacentVertexList = adjacentVertexList; 
  } 

  public Expenses getData(){
    return this.data; 
  }

  public int getIndegree(){
    return this.indegree;
  } 

  public void incrementIndegree(){
    this.indegree++; 
  } 

  public void decrementIndegree(){
    this.indegree--; 
  }

  public String toString(){
    return this.data.getName(); 
  }

  public void setIndigree(int num){
    this.indegree = num; 
  } 


}
