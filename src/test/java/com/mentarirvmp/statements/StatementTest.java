package com.mentarirvmp.statements;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

public class StatementTest {

  // // Now I need a recursion test that would test if I can query ALL the children for ALL the expenses in the mock statement. 
  @Test
  public void getExpenseByIDTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject(); 

    // //we flatten the nested arrays, so we can check on them easier. 
    ArrayList<Expenses> allParentAndChildExpenses = populateAllNestedExpensesIntoArray(dummyStatement); 

    // //were testing for successful querying 
    for(Expenses expense: allParentAndChildExpenses){
      assertEquals(expense, dummyStatement.getExpenseById(expense.getId()));
    }

    //were testing for invalid ID 
    assertEquals(Expenses.INVALID_EXPENSE, dummyStatement.getExpenseById("120301"));
  } 

  private ArrayList<Expenses> populateAllNestedExpensesIntoArray(Statement dummyStatement){
    ArrayList<Expenses> allParentAndChildExpenses = new ArrayList<Expenses>();
    for(Map.Entry<String, Expenses> mapElement: dummyStatement.getExpensesMap().entrySet()){
      recursiveAllNestedExpensesIntoArray(allParentAndChildExpenses, mapElement.getValue());
      // System.out.println("===========");
      // for(Expenses expense : allParentAndChildExpenses){
      //   System.out.println(expense.getName());
      // }
      // System.out.println("===========");
    }
    return allParentAndChildExpenses;

  } 

  private ArrayList<Expenses> recursiveAllNestedExpensesIntoArray(ArrayList<Expenses> allParentAndChildExpenses, Expenses expense){
    allParentAndChildExpenses.add(expense);
    if(expense.hasChildren()){
      for(Map.Entry<String, Expenses> mapElement:expense.getChildMap().entrySet()){
        recursiveAllNestedExpensesIntoArray(allParentAndChildExpenses, mapElement.getValue());
      }
    } 

    return allParentAndChildExpenses;
    
  } 
  
}
