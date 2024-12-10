package com.mentarirvmp.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import com.mentarirvmp.utils.Vertex;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.AcyclicGraphHandler;

public class DAGVertexTest {
  
  @Test
  public void testAddToAdjacencyVertexList(){
    Statement dummyStatement = new Statement("dummyStatement");
    //expense one should be updated after expense 2 and 3 
    Expenses expense1 = new Expenses("1");
    dummyStatement.addExpense(expense1);
    Expenses expense2 = new Expenses("2");
    dummyStatement.addExpense(expense2);
    Expenses expense3 = new Expenses("3");
    dummyStatement.addExpense(expense3);

    expense1.setEquation("SUM(" + expense2.getId() + "," + expense3.getId()+")");
    expense2.setEquation("10");
    expense3.setEquation("11"); 

    Vertex vertex1 = new Vertex(expense1);
    Vertex vertex2 = new Vertex(expense2);
    Vertex vertex3 = new Vertex(expense3); 

    





  }

  //under the prequisite that the equation is VALID!! 
  @Test 
  public void initializeDAGGraphFromEquationsTest(){
    //if no valid Expense ID I wont make a graph out of it. 
    
  } 

}
