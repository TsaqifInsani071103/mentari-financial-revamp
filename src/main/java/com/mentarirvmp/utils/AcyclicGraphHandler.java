package com.mentarirvmp.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class AcyclicGraphHandler {
  //the graph is going to have a list of the topological sort 
  //and methods for topsort 
  //methods to confirm acyclic graph? 
  //to make the directed edges between vertices. 
  private Map<Expenses, Vertex> expenseToVertexMap;
  private Expenses[] topSortArray;
  private int NUMBER_OF_VERTICES; 


  public AcyclicGraphHandler(Map<Expenses, Vertex> expenseToVertexMap){
    this.expenseToVertexMap = getDeepCopyMap(expenseToVertexMap);
    this.NUMBER_OF_VERTICES = expenseToVertexMap.size();
  }

  private  Map<Expenses, Vertex> getDeepCopyMap(Map<Expenses, Vertex> expenseToVertexMap){
    if(expenseToVertexMap.size() == 0) return null;
    Map<Expenses, Vertex> deepCopy = new HashMap<>();
    //we need recursion 
    for(Expenses expense: expenseToVertexMap.keySet()){
      Vertex oriVertex = expenseToVertexMap.get(expense);
      Vertex copyVertex = getDeepCopyVertex(oriVertex);
      deepCopy.put(expense, copyVertex);
    }
    printMapContents(expenseToVertexMap, "ORIGINAL");
    printMapContents(deepCopy, "COPY");
    return deepCopy;
  } 

  private Vertex getDeepCopyVertex(Vertex vertex){
    Vertex oriVertex = vertex; 
    Vertex copyVertex = new Vertex(vertex.getData());
    copyVertex.setIndigree(copyVertex.getIndegree());
    for(Vertex adjVertex: oriVertex.getAdjacentVertexSet()){
      Vertex copyAdjVertex = getDeepCopyVertex(adjVertex);
      copyVertex.addDirectedEdgeToward(copyAdjVertex);
    }

    return copyVertex;

  } 

  private void printMapContents(Map<Expenses, Vertex> map, String name){
    for(Expenses expense: map.keySet()){
      System.out.println("MAP NAME: " + name);
      System.out.println("KEY: " + expense);
      Vertex vertex = map.get(expense);
      System.out.println("-Vertex: " + vertex);
      System.out.println("--Indigree: " + vertex.getIndegree());
      System.out.println("---adjList: " + vertex.getAdjacentVertexSet().toString());
      System.out.println();
    }
  } 

  // public Expenses[] getTopSortArray(){
  //   return this.topSortArray;
  // } 

  //E2 = SUM(E3, E4) that means E2 depends on the values of E3 and E4, which means 
  //E2 has an indegree of 2: E3 and E4 if directed edges from E3 and E4 to E2 mean that E2 depends on the values E3 and E4 to get its own value. 
  //that means a node with indegree zero, which is what we want to find, will have an adjacency list of size 0 
  //we'll loop through the map and find a node that has an empty adjacency list. 
  //if such a node does not exist, then the dependency is circular. 

  public Expenses[] getTopSortArray(){ 
    if(this.topSortArray != null){
      return this.topSortArray;
    }else{
      this.topSortArray = new Expenses[this.NUMBER_OF_VERTICES];
    }
    int counter = 0; 
    Queue<Vertex> queue = new ArrayDeque<>();
    for(Expenses expense: expenseToVertexMap.keySet()){
      Vertex vertex = expenseToVertexMap.get(expense);
      if(vertex.getIndegree() == 0){
        queue.offer(vertex); 
      };
    }

    while(!queue.isEmpty()){
      Vertex vertex = queue.poll(); 
      topSortArray[counter] = vertex.getData(); 
      counter++; 
      for(Vertex adjacentVertex: vertex.getAdjacentVertexSet()){
        adjacentVertex.decrementIndegree();
        if(adjacentVertex.getIndegree() == 0){
          queue.offer(adjacentVertex);
        }
      }
    } 

    //this means that the graph is cyclic. 
    if(counter != this.NUMBER_OF_VERTICES){
      return null; 
    }else{
      return this.topSortArray;
    }


  } 

  public void refreshExpenseValuesProceeding(Expenses expense, ExpenseStatementHandler dataHandler){
    boolean startingPointFound = false; 
    for(int i = 0; i < this.topSortArray.length;i++){
      Expenses currentExpense = topSortArray[i]; 
      if(startingPointFound){
        // topSortArray[i].
        dataHandler.ifEquationValidSetExpenseValue(currentExpense, currentExpense.getEquation());
      }
      if(currentExpense == expense){
        startingPointFound = true; 
      } 
    }

  }


  
}
 