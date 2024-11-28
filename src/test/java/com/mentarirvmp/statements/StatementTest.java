package com.mentarirvmp.statements;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;
import com.mentarirvmp.utils.ExpenseStatementHandler;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

public class StatementTest {

  @Test
  public void addNestedExpensesTest() {
    Statement dummyStatement = MockObjects.getDummyStatementObject();

    Expenses parent = new Expenses("Parent");
    Expenses child = new Expenses("Child");
    dummyStatement.addExpense(parent);
    dummyStatement.addExpenseToParent(child, parent);

    // assertEquals(parent, dummyStatement.getExpenseById(parent.getId()));
    // assertEquals(child, dummyStatement.getExpenseById(child.getId()));

    ExpenseStatementHandler handler = new ExpenseStatementHandler(dummyStatement);
    handler.traverseThroughAllData(node -> {
      System.out.println(node);
    });
  }

  // // Now I need a recursion test that would test if I can query ALL the children for ALL the expenses in the mock statement. 
  @Test
  public void getExpenseByIDTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject(); 

    // //we flatten the nested arrays, so we can check on them easier. 
    ArrayList<Expenses> allParentAndChildExpenses = populateAllNestedExpensesIntoArray(dummyStatement); 

    // //were testing for successful querying 
    for(Expenses expense: allParentAndChildExpenses){
      assertEquals(expense, dummyStatement.getExpenseById(expense.getId()));
      // System.out.println("=============");
      // System.out.println(expense.getName()); 
      // System.out.println(dummyStatement.getExpenseById(expense.getId()).getName()); 
      // System.out.println("=============");
    }

    //were testing for invalid ID 
    assertEquals(Expenses.INVALID_EXPENSE, dummyStatement.getExpenseById("120301"));
  } 

  private ArrayList<Expenses> populateAllNestedExpensesIntoArray(Statement dummyStatement){
    ArrayList<Expenses> allParentAndChildExpenses = new ArrayList<Expenses>();
    for(Map.Entry<String, Expenses> mapElement: dummyStatement.getExpensesMap().entrySet()){
      recursiveAllNestedExpensesIntoArray(allParentAndChildExpenses, mapElement.getValue());
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
