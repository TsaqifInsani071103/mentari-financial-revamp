package com.mentarirvmp.statements;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StatementTest {

  @Test
  public void getExpenseByIDTest(){
    Statement dummyStatement = MockObjects.initializeDummyStatement(); 

    // //checking top level Expense's id 
    // Expenses expense1 = dummyStatement.getExpenseArray().get(0);
    // int Id1 = expense1.getId(); 
    // assertEquals(expense1, dummyStatement.getExpenseById(Id1));

    // //checking second levelExpense's id 
    // Expenses expense2 = expense1.getChildArray().get(0);
    // int Id2 = expense2.getId(); 
    // assertEquals(expense2, dummyStatement.getExpenseById(Id2));

    // //checking third levelExpense's id 
    // Expenses expense3 = expense2.getChildArray().get(0);
    // int Id3 = expense3.getId(); 
    // assertEquals(expense3, dummyStatement.getExpenseById(Id3));
  } 
  
}
