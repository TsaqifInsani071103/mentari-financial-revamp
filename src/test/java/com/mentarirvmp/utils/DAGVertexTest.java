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
    assertNull(sortedArray2);
  
  } 





}
