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
  private Map<Expenses, Vertex> expenseToVertexMap = new HashMap<>();
  private Expenses[] topSortArray;
  private int NUMBER_OF_VERTICES; 


  public AcyclicGraphHandler(){
  }


  public void initializeExpenseAndDependencies(Expenses expense, ArrayList<Expenses> independentExpenses){
    Vertex currentVertex = this.expenseToVertexMap.get(expense);
    if( currentVertex == null) {
      currentVertex = new Vertex(expense);
      this.expenseToVertexMap.put(expense, currentVertex);
    }else{
      resetAdjacencyLists(currentVertex);
    }

    //current vertex is E3. IndependentExpenses was E1. 
    //right now, E1 -> E3, 
    //but now since current vertex is E3, and IndependentExpenses is [] empty array, 
    //I want E1, to be rid of its arrows to E3. 
    //therefore I need to also refresh the expenseToVertexMap no? 
    
    if(independentExpenses.size() > 0){
      for(int i = 0; i < independentExpenses.size() ; i++){
        Expenses independentExpense = independentExpenses.get(i);
        Vertex newVertex = this.expenseToVertexMap.get(independentExpense) == null? new Vertex(independentExpense) : this.expenseToVertexMap.get(independentExpense);
        
        newVertex.addDirectedEdgeToward(currentVertex);
        this.expenseToVertexMap.put(independentExpense, newVertex);
      }
    }

    this.NUMBER_OF_VERTICES = expenseToVertexMap.size();
  } 

  private void resetAdjacencyLists(Vertex targetVertex){
    for(Expenses expense: this.expenseToVertexMap.keySet()){
      if(this.expenseToVertexMap.get(expense).getAdjacentVertexSet().contains(targetVertex)){
        this.expenseToVertexMap.get(expense).getAdjacentVertexSet().remove(targetVertex);
      }
    }
  }

  //only delete an entry to expenseToVertexMap IF we either add or delete an expense 
  public void resetDependencyGraph(){
    // this.expenseToVertexMap = new HashMap<>();
    this.topSortArray = new Expenses[NUMBER_OF_VERTICES];
  } 



  public Expenses[] getTopSortArray(){ 
    
    Map<Expenses, Vertex> expenseToVertexMapCopy = getDeepCopyMap(this.expenseToVertexMap);
    this.topSortArray = new Expenses[this.NUMBER_OF_VERTICES];
    int counter = 0; 
    Queue<Vertex> queue = new ArrayDeque<>();
    for(Expenses expense: expenseToVertexMapCopy.keySet()){
      Vertex vertex = expenseToVertexMapCopy.get(expense);
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
      this.topSortArray = new Expenses[]{};
    }

    return this.topSortArray;
    

  } 

  private  Map<Expenses, Vertex> getDeepCopyMap(Map<Expenses, Vertex> expenseToVertexMap){
    if(expenseToVertexMap.size() == 0) return null;
    Map<Expenses, Vertex> deepCopy = new HashMap<>();
    //we need recursion 
    for(Expenses expense: expenseToVertexMap.keySet()){
      Vertex oriVertex = expenseToVertexMap.get(expense);
      Vertex copyVertex = new Vertex(oriVertex.getData());
      // copyVertex.setIndigree(oriVertex.getIndegree());
      deepCopy.put(expense, copyVertex);
    }

    for(Expenses expense: expenseToVertexMap.keySet()){
      Vertex oriVertex = expenseToVertexMap.get(expense);
      Vertex copyVertex = deepCopy.get(oriVertex.getData());
      for(Vertex oriAdjVertex: oriVertex.getAdjacentVertexSet()){
        copyVertex.addDirectedEdgeToward(deepCopy.get(oriAdjVertex.getData()));
      }
    } 


    return deepCopy;
  } 

  public void refreshExpenseValuesProceeding(Expenses expense, ExpenseStatementHandler dataHandler){
    boolean startingPointFound = false; 
    for(int i = 0; i < this.topSortArray.length;i++){
      Expenses currentExpense = topSortArray[i]; 
      if(startingPointFound && this.expenseToVertexMap.get(expense).getAdjacentVertexSet().contains(this.expenseToVertexMap.get(currentExpense))){
        // topSortArray[i].
        dataHandler.ifEquationValidSetExpenseValue(currentExpense, currentExpense.getEquation());
      }

      if(currentExpense == expense){
        startingPointFound = true; 
      } 
    }

  }

  public ArrayList<Expenses> getValuesProceeding(Expenses expense){
    boolean startingPointFound = false; 
    ArrayList<Expenses> valuesProceedingArray = new ArrayList<>();
    for(int i = 0; i < this.topSortArray.length;i++){
      Expenses currentExpense = topSortArray[i]; 
      if(startingPointFound && this.expenseToVertexMap.get(expense).getAdjacentVertexSet().contains(this.expenseToVertexMap.get(currentExpense))){
        // topSortArray[i].
        // dataHandler.ifEquationValidSetExpenseValue(currentExpense, currentExpense.getEquation());
        valuesProceedingArray.add(currentExpense);
        // System.out.println("THE VALUE OF: " + currentExpense.getName() + "HAS BEEN UPDATED TO: " + currentExpense.getValue());
      }
      if(currentExpense == expense){
        startingPointFound = true; 
      } 
    }
    return valuesProceedingArray;
  } 

  private void printMapContents(Map<Expenses, Vertex> map, String name){
    int total = 0; 
    System.out.println("MAP NAME: " + name);
    for(Expenses expense: map.keySet()){
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
 