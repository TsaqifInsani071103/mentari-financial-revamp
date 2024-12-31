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
    this.expenseToVertexMap = expenseToVertexMap;
    this.NUMBER_OF_VERTICES = expenseToVertexMap.size();
    this.topSortArray = new Expenses[NUMBER_OF_VERTICES]; 
  }

  //E2 = SUM(E3, E4) that means E2 depends on the values of E3 and E4, which means 
  //E2 has an indegree of 2: E3 and E4 if directed edges from E3 and E4 to E2 mean that E2 depends on the values E3 and E4 to get its own value. 
  //that means a node with indegree zero, which is what we want to find, will have an adjacency list of size 0 
  //we'll loop through the map and find a node that has an empty adjacency list. 
  //if such a node does not exist, then the dependency is circular. 

  public Expenses[] getTopSortedArray(){
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
      for(Vertex adjacentVertex: vertex.getAdjacentVertexList()){
        adjacentVertex.decrementIndegree();
        if(adjacentVertex.getIndegree() == 0){
          queue.offer(adjacentVertex);
        }
      }
    } 

    //this means that the graph is cyclic. 
    if(counter != this.NUMBER_OF_VERTICES){
      return null; 
    }

    return this.topSortArray;

  } 


  
}
 