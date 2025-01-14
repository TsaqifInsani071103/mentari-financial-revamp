package com.mentarirvmp.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {
  private Expenses data; 
  private int indegree = 0; 
  private Set<Vertex> adjacentVertexSet = new HashSet<Vertex>();

  public Vertex(Expenses expense){
    this.data = expense; 
  } 

  public void addDirectedEdgeToward(Vertex expenseVertex){
    if(!adjacentVertexSet.contains(expenseVertex)){
      this.adjacentVertexSet.add(expenseVertex);
      expenseVertex.incrementIndegree();
    }
  } 

  public void removeDirectedEdgeToward(Vertex expenseVertex){
    if(adjacentVertexSet.contains(expenseVertex)){
      this.adjacentVertexSet.remove(expenseVertex);
      expenseVertex.decrementIndegree();
    }
  } 

  public Set<Vertex> getAdjacentVertexSet(){
    return this.adjacentVertexSet;
  }

  public void resetAdjacentVertexSet(){
    this.adjacentVertexSet = new HashSet<Vertex>(); 
    this.indegree = 0; 
  } 

  protected void setAdjacentVertexSet(Set<Vertex> adjacentVertexList ){
    this.adjacentVertexSet = adjacentVertexList; 
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
