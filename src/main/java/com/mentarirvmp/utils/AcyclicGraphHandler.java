package com.mentarirvmp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AcyclicGraphHandler {
  //the graph is going to have a list of the topological sort 
  //and methods for topsort 
  //methods to confirm acyclic graph? 
  //to make the directed edges between vertices. 
  private Map<Expenses, ArrayList<Expenses>> expenseVertexMap;
  private Expenses[] topSortArray;

  public AcyclicGraphHandler(Map<Expenses, ArrayList<Expenses>> expenseVertexMap){
    this.expenseVertexMap = expenseVertexMap;
    this.topSortArray = new Expenses[expenseVertexMap.size()]; 
  }

  //E2 = SUM(E3, E4) that means E2 depends on the values of E3 and E4, which means 
  //E2 has an indegree of 2: E3 and E4 if directed edges from E3 and E4 to E2 mean that E2 depends on the values E3 and E4 to get its own value. 
  //that means a node with indegree zero, which is what we want to find, will have an adjacency list of size 0 
  //we'll loop through the map and find a node that has an empty adjacency list. 
  //if such a node does not exist, then the dependency is circular. 

  public void initializeTopSort(){

  } 


  
}
 