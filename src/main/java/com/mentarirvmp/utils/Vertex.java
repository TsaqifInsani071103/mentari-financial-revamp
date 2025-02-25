package com.mentarirvmp.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {
  private Expenses data; 
  private int indegree = 0; 
  private Set<Vertex> outgoingVertexSet = new HashSet<Vertex>();
  private Set<Vertex> incomingVerticesSet = new HashSet<Vertex>(); 

  public Vertex(Expenses expense){
    this.data = expense; 
  } 

  public void addDirectedEdgeToward(Vertex expenseVertex){
    if(!outgoingVertexSet.contains(expenseVertex)){
      this.outgoingVertexSet.add(expenseVertex);
      expenseVertex.incrementIndegree();
    }
  } 

  public void removeDirectedEdgeToward(Vertex expenseVertex){
    if(outgoingVertexSet.contains(expenseVertex)){
      this.outgoingVertexSet.remove(expenseVertex);
      expenseVertex.decrementIndegree();
    }
  } 

  public Set<Vertex> getOutgoingVertexSet(){
    return this.outgoingVertexSet;
  }

  public void resetAdjacentVertexSet(){
    this.outgoingVertexSet = new HashSet<Vertex>(); 
    this.indegree = 0; 
  } 

  protected void setOutgoingVertexSet(Set<Vertex> adjacentVertexList ){
    this.outgoingVertexSet = adjacentVertexList; 
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
