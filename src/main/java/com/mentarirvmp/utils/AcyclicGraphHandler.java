package com.mentarirvmp.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class AcyclicGraphHandler {
  //E2 = SUM(E3, E4) that means E2 depends on the values of E3 and E4, which means 
  //E2 has an indegree of 2: E3 and E4 if directed edges from E3 and E4 to E2 mean that E2 depends on the values E3 and E4 to get its own value. 
  //that means a node with indegree zero, which is what we want to find, will have an adjacency list of size 0 
  //we'll loop through the map and find a node that has an empty adjacency list. 
  //if such a node does not exist, then the dependency is circular. 
  private ArrayList<Expenses> validExpensesInEquation; 
  private Map<Expenses, Vertex> expenseToVertexMap = new HashMap<>();
  private Expenses[] topSortArray;
  private int NUMBER_OF_VERTICES; 


  public AcyclicGraphHandler(){
  }

  public void notifyOfValidExpenses(ArrayList<Expenses> validExpensesArray){
    this.validExpensesInEquation = validExpensesArray;
  } 

  public void putExpenseIntoDependencyGraph(Expenses expense){
    if(this.expenseToVertexMap.get(expense) == null) this.expenseToVertexMap.put(expense, new Vertex(expense));

    for(int i = 0; i < this.validExpensesInEquation.size() ; i++){
      Expenses independentExpense = validExpensesInEquation.get(i);
      Vertex newVertex = this.expenseToVertexMap.get(independentExpense) == null? new Vertex(independentExpense) : this.expenseToVertexMap.get(independentExpense);

      newVertex.addDirectedEdgeToward(this.expenseToVertexMap.get(expense));
      this.expenseToVertexMap.put(independentExpense, newVertex);
    }

    this.NUMBER_OF_VERTICES = expenseToVertexMap.size();

  } 

  // private  Map<Expenses, Vertex> getDeepCopyMap(Map<Expenses, Vertex> expenseToVertexMap){
  //   if(expenseToVertexMap.size() == 0) return null;
  //   Map<Expenses, Vertex> deepCopy = new HashMap<>();
  //   //we need recursion 
  //   for(Expenses expense: expenseToVertexMap.keySet()){
  //     Vertex oriVertex = expenseToVertexMap.get(expense);
  //     Vertex copyVertex = new Vertex(oriVertex.getData());
  //     // copyVertex.setIndigree(oriVertex.getIndegree());
  //     deepCopy.put(expense, copyVertex);
  //   }

  //   for(Expenses expense: expenseToVertexMap.keySet()){
  //     Vertex oriVertex = expenseToVertexMap.get(expense);
  //     Vertex copyVertex = deepCopy.get(oriVertex.getData());
  //     for(Vertex oriAdjVertex: oriVertex.getAdjacentVertexSet()){
  //       copyVertex.addDirectedEdgeToward(deepCopy.get(oriAdjVertex.getData()));
  //     }
  //   } 


  //   return deepCopy;
  // } 

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
        //System.out.println("ADJ VERTEX: " + adjacentVertex + ", INDEGREE: " + adjacentVertex.getIndegree());
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
        System.out.println("THE VALUE OF: " + currentExpense.getName() + "HAS BEEN UPDATED TO: " + currentExpense.getValue());
      }
      if(currentExpense == expense){
        startingPointFound = true; 
      } 
    }

  }

  private void printMapContents(Map<Expenses, Vertex> map, String name){
    int total = 0; 
    for(Expenses expense: map.keySet()){
      System.out.println("MAP NAME: " + name);
      System.out.println("KEY: " + expense);
      Vertex vertex = map.get(expense);
      System.out.println("-Vertex: " + vertex);
      System.out.println("--Indigree: " + vertex.getIndegree());
      total += vertex.getIndegree();
      System.out.println("---adjList: " + vertex.getAdjacentVertexSet().toString());
      System.out.println();
    }

    System.out.println("TOTAL "+ name + " INDEGREE: " + total);
  } 


  
}
 