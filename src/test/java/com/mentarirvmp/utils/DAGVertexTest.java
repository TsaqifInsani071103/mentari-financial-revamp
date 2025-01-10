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
    //E2 = SUM(E1) 
    //E3 = SUM(E1, E4)
    //E1 = none 
    //E4 = SUM(E2)
    
    ArrayList<Expenses> dependencies2 = new ArrayList<>(Arrays.asList(E1)); 
    ArrayList<Expenses> dependencies3 = new ArrayList<>(Arrays.asList(E1, E4)); 
    ArrayList<Expenses> dependencies4 = new ArrayList<>(Arrays.asList(E2));
    dag.initializeExpenseAndDependencies(E1, new ArrayList<>());
    dag.initializeExpenseAndDependencies(E2, dependencies2); 
    dag.initializeExpenseAndDependencies(E3, dependencies3); 
    dag.initializeExpenseAndDependencies(E4, dependencies4); 

    Expenses[] expectedSort = new Expenses[]{E1, E2, E4, E3};

    Expenses[] sortedArray = dag.getTopSortArray();
    assertNotNull(sortedArray);
    for(int i =0; i<sortedArray.length;i++){
      assertEquals(sortedArray[i].getName(), expectedSort[i].getName());
    }

    // //this is a cyclical sort 
    ArrayList<Expenses> dependencies1 = new ArrayList<>(Arrays.asList(E2));
    dag.initializeExpenseAndDependencies(E1, dependencies1);
    Expenses[] sortedArray2 = dag.getTopSortArray();
    assertEquals(0, sortedArray2.length);

    // Test 5: Cyclic graph with a complex cycle
    AcyclicGraphHandler dag5 = new AcyclicGraphHandler();
    Expenses M = new Expenses("M");
    Expenses N = new Expenses("N");
    Expenses O = new Expenses("O");

    dag5.initializeExpenseAndDependencies(M, new ArrayList<>(Arrays.asList(N)));
    dag5.initializeExpenseAndDependencies(N, new ArrayList<>(Arrays.asList(O)));
    dag5.initializeExpenseAndDependencies(O, new ArrayList<>(Arrays.asList(M)));

    Expenses[] sortedArray5 = dag5.getTopSortArray();
    assertEquals(0,sortedArray5.length);


    // Test 4: Complex acyclic graph
    AcyclicGraphHandler dag4 = new AcyclicGraphHandler();
    Expenses P = new Expenses("P");
    Expenses Q = new Expenses("Q");
    Expenses R = new Expenses("R");
    Expenses S = new Expenses("S");

    dag4.initializeExpenseAndDependencies(P, new ArrayList<>());
    dag4.initializeExpenseAndDependencies(Q, new ArrayList<>(Arrays.asList(P)));
    dag4.initializeExpenseAndDependencies(R, new ArrayList<>(Arrays.asList(P)));
    dag4.initializeExpenseAndDependencies(S, new ArrayList<>(Arrays.asList(Q, R)));

    // Expected order: P, Q, R, S
    Expenses[] expectedSort4 = new Expenses[]{P, Q, R, S};
    Expenses[] sortedArray4 = dag4.getTopSortArray();
    assertNotNull(sortedArray4);

  
  } 





}
