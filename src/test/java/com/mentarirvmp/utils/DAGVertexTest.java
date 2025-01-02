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

    Expenses E1= new Expenses("1");
    Expenses E2= new Expenses("2");
    Expenses E3= new Expenses("3");
    Expenses E4= new Expenses("4");

    AcyclicGraphHandler dag = new AcyclicGraphHandler();

 


    // first graph = 1 --> 2, 3. 2--> 4. 4 --> 3
    //this should be an acylic graph where the topological sort is 
    //1,2,4,3 
    ArrayList<Expenses> e1Dependencies = new ArrayList<>(Arrays.asList(E2, E3));
    dag.initializeExpenseAndDependencies(E1, e1Dependencies);
    ArrayList<Expenses> e2Dependencies = new ArrayList<>(Arrays.asList(E4));
    dag.initializeExpenseAndDependencies(E2, e2Dependencies);
    ArrayList<Expenses> e4Dependencies = new ArrayList<>(Arrays.asList(E3));
    dag.initializeExpenseAndDependencies(E4, e4Dependencies);

    Expenses[] expectedSort = new Expenses[]{E1, E2, E4, E3};

    Expenses[] sortedArray = dag.getTopSortArray();
    System.out.println(Arrays.toString(sortedArray));
    assertNotNull(sortedArray);
    for(int i =0; i<sortedArray.length;i++){
      assertEquals(sortedArray[i].getName(), expectedSort[i].getName());
    }

    // //this is a cyclical sort 
    // vertex2.addDirectedEdgeToward(vertex1);
    // AcyclicGraphHandler dag2 = new AcyclicGraphHandler(expenseToVertexMap);
    // Expenses[] sortedArray2 = dag2.getTopSortArray();
    // System.out.println(Arrays.toString(sortedArray2));
    // assertNull(sortedArray2);
  
  } 





}
