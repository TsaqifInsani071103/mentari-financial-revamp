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
        this.expenseToVertexMap.get(expense).removeDirectedEdgeToward(targetVertex);
      }
    }
  }


  public Expenses[] getTopSortArray(Expenses anchorExpense){ 
    Map<Expenses, Vertex> expenseToVertexMapCopy = getDeepCopyMap(this.expenseToVertexMap);
    int counter = 0; 
    int numberOfLeftOutStandaloneVertices = 0; 


    Queue<Vertex> queue = new ArrayDeque<>();
    for(Expenses expense: expenseToVertexMapCopy.keySet()){
      Vertex vertex = expenseToVertexMapCopy.get(expense);
      if(vertex.getIndegree() ==0 && vertex.getAdjacentVertexSet().size() == 0){
        numberOfLeftOutStandaloneVertices++;
        continue; 
      }

      if(vertex.getIndegree() == 0){
        queue.offer(vertex); 
      };
    }

    this.topSortArray = new Expenses[this.NUMBER_OF_VERTICES - numberOfLeftOutStandaloneVertices];
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
    if(counter != this.NUMBER_OF_VERTICES - numberOfLeftOutStandaloneVertices){
      return null; 

    }else{
      Vertex anchorVertex = this.expenseToVertexMap.get(anchorExpense);
      Map<Vertex, Boolean> narrowedVerticesFromAnchor = populateMapWithVertices(anchorVertex, new HashMap<Vertex, Boolean>());
      // Expenses[] newTopSort = new Expenses[narrowedVerticesFromAnchor.size() + 1];
      // newTopSort[0] = anchorExpense;
      // int position = 1; 
      // for(Expenses item:this.topSortArray){
      //   if(narrowedVerticesFromAnchor.get(this.expenseToVertexMap.get(item))){
      //     newTopSort[position] = item; 
      //     position++; 
      //   }
      // }
      // this.topSortArray = newTopSort;
    } 

    return this.topSortArray;
    

  } 

  private Map<Vertex, Boolean> populateMapWithVertices(Vertex parentVertex, Map<Vertex, Boolean> map){
    for(Vertex adj: parentVertex.getAdjacentVertexSet()){
      map.put(adj, true);
      populateMapWithVertices(adj, map); 
    }
    

    return map; 
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
 