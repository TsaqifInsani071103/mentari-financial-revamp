package com.mentarirvmp.statements;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class StatementTest {

  //Now I need a recursion test that would test if I can query ALL the children for ALL the expenses in the mock statement. 
  @Test
  public void getExpenseByIDTest(){
    Statement dummyStatement = MockObjects.initializeDummyStatement(); 

    ArrayList<Expenses> allParentAndChildArray = new ArrayList<Expenses>();
    //We're mapping All expenses and all nested expenses into allParentAndChildArray 
    for(Expenses expense:dummyStatement.getExpenseArray()){
      populateAllNestedExpensesIntoArray(allParentAndChildArray, expense);
    }

    //were testing for successful querying 
    for(Expenses expense: allParentAndChildArray){
      assertEquals(expense, dummyStatement.getExpenseById(expense.getId()));
    }

    //were testing for invalid ID 
    assertEquals(Statement.INVALID_EXPENSE_ID, dummyStatement.getExpenseById(120301));
  } 

  private ArrayList<Expenses> populateAllNestedExpensesIntoArray(ArrayList<Expenses> allParentAndChildArray, Expenses expense){
    allParentAndChildArray.add(expense);
    if(expense.hasChildren()){
      for(Expenses childExpense:expense.getChildArray()){
        populateAllNestedExpensesIntoArray(allParentAndChildArray, childExpense);
      }
    } 
    return allParentAndChildArray; 
  } 
  
}
