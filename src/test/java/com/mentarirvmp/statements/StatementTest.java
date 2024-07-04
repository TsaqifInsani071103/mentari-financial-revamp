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
    for(Expenses expense:dummyStatement.getExpenseArray()){
      populateAllNestedExpensesIntoArray(allParentAndChildArray, expense);
    }
    for(Expenses expense: allParentAndChildArray){
      assertEquals(expense, dummyStatement.getExpenseById(expense.getId()));
    }
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
