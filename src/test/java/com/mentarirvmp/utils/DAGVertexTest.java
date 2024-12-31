package com.mentarirvmp.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.AcyclicGraphHandler;

public class DAGVertexTest {
  
  

  //under the prequisite that the equation is VALID!! 
  @Test 
  public void topologicalSortTest(){
    //if no valid Expense ID I wont make a graph out of it. \
    Map<Expenses, Vertex> expenseToVertexMap = new HashMap<>();
    Expenses expense1= new Expenses("1");
    Expenses expense2= new Expenses("2");
    Expenses expense3= new Expenses("3");
    Expenses expense4= new Expenses("4");

    Vertex vertex1 = new Vertex(expense1);
    Vertex vertex2 = new Vertex(expense2);
    Vertex vertex3 = new Vertex(expense3);
    Vertex vertex4 = new Vertex(expense4);
 

    expenseToVertexMap.put(expense1, vertex1);
    expenseToVertexMap.put(expense2, vertex2);
    expenseToVertexMap.put(expense3, vertex3);
    expenseToVertexMap.put(expense4, vertex4);


    // first graph = 1 --> 2, 3. 2--> 4. 4 --> 3
    //this should be an acylic graph where the topological sort is 
    //1,2,4,3 
    vertex1.addDirectedEdgeToward(vertex2);
    vertex1.addDirectedEdgeToward(vertex3);
    vertex2.addDirectedEdgeToward(vertex4);
    vertex4.addDirectedEdgeToward(vertex3);

    //REMEMBER THE INDEGREES HOMIE 
    Expenses[] expectedSort = new Expenses[]{expense1,expense2,expense4,expense3};
    AcyclicGraphHandler dag = new AcyclicGraphHandler(expenseToVertexMap);
    // dag.makeSortedArrayIfAcyclic();
    Expenses[] sortedArray = dag.getTopSortArray();
    assertNotNull(sortedArray);
    for(int i =0; i<sortedArray.length;i++){
      assertEquals(sortedArray[i].getName(), expectedSort[i].getName());
    }


    // //this is a cyclical sort 
    // AcyclicGraphHandler dag2 = new AcyclicGraphHandler(expenseToVertexMap);
    // // dag2.makeSortedArrayIfAcyclic();
    // Expenses[] sortedArray2 = dag2.getTopSortArray();
    // // System.out.println(Arrays.toString(sortedArray2));
    // assertNull(sortedArray2);
  
  } 





}
