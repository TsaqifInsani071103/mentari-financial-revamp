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
  public void testGetRoot(){
    //getting root by calling next() on iterator 
    Statement dummyStatement = new Statement("dummyStatement"); 
    Expenses rootExpense = dummyStatement.getRoot(); 
    assertEquals(rootExpense, dummyStatement.getRoot());
  } 

  @Test 
  public void testRecursion(){
    Statement dummyStatement = new Statement("dummyStatement"); 
    Expenses newExpense1 = new Expenses("1");
    Expenses newExpense2 = new Expenses("2");
    Expenses newExpense3 = new Expenses("3");

    dummyStatement.addExpenseToParent(newExpense2, newExpense3);
    dummyStatement.addExpense(newExpense1);
    dummyStatement.addExpense(newExpense3);
    dummyStatement.addExpenseToParent(new Expenses("a"), newExpense1);

    assertEquals(dummyStatement.getExpenseById(newExpense2.getId()), newExpense2);

    Statement dummyStatement2 = MockObjects.getDummyStatementObject();
    Object[] dummyStatement2Data = dummyStatement2.getAllUnderlyingStatementData();

    for(Object expenseObject : dummyStatement2Data){
      Expenses expense= (Expenses) expenseObject; 
      assertEquals(expense, dummyStatement2.getExpenseById(expense.getId())); 
    }

  }

  @Test 
  public void addAndDeleteExpense(){
    //this is a statement with only root expense 
    Statement dummyStatement = new Statement("dummyStatement");
    Expenses newExpense1 = new Expenses("newExpense1");
    Expenses newExpense2 = new Expenses("newExpense2"); 

    //addExpense method works and gives Id 
    dummyStatement.addExpense(newExpense1);
    assertEquals(dummyStatement.getExpenseById(newExpense1.getId()), newExpense1);

    //addExpenseToParent method works and gives ID 
    dummyStatement.addExpenseToParent(newExpense2, newExpense1); 
    assertEquals(dummyStatement.getExpenseById(newExpense2.getId()), newExpense2);

    dummyStatement.deleteExpense(newExpense1);
    assertEquals(dummyStatement.getExpenseById(newExpense1.getId()), Expenses.INVALID_EXPENSE);
    assertEquals(dummyStatement.getExpenseById(newExpense2.getId()), Expenses.INVALID_EXPENSE);

  } 

  @Test 
  public void statementDataTest(){
    Statement dummyStatement = new Statement("dummyStatement");
    Expenses newExpense1 = new Expenses("newExpense1");
    Expenses newExpense2 = new Expenses("newExpense2"); 
    dummyStatement.addExpense(newExpense1);
    dummyStatement.addExpense(newExpense2);

    assertEquals(dummyStatement.getAllUnderlyingStatementData().length, dummyStatement.getParentChildExpenses().size());

    dummyStatement.deleteExpense(newExpense2);
    assertEquals(dummyStatement.getAllUnderlyingStatementData().length, dummyStatement.getParentChildExpenses().size());

    dummyStatement.deleteExpense(newExpense1);
    assertEquals(dummyStatement.getAllUnderlyingStatementData().length, dummyStatement.getParentChildExpenses().size());

  } 

  @Test
public void addAndDeleteMultipleExpenses() {
    // Create a dummy statement with a root expense
    Statement dummyStatement = new Statement("dummyStatement");
    
    // Create multiple expenses
    Expenses rootExpense = new Expenses("Root Expense");
    Expenses childExpense1 = new Expenses("Child Expense 1");
    Expenses childExpense2 = new Expenses("Child Expense 2");
    Expenses grandChildExpense1 = new Expenses("GrandChild Expense 1");
    Expenses grandChildExpense2 = new Expenses("GrandChild Expense 2");
    Expenses greatGrandChild = new Expenses("grandgrand");

    // Add root expense
    dummyStatement.addExpense(rootExpense);
    assertEquals(dummyStatement.getExpenseById(rootExpense.getId()), rootExpense);

    // Add children to root expense
    dummyStatement.addExpenseToParent(childExpense1, rootExpense);
    dummyStatement.addExpenseToParent(childExpense2, rootExpense);
    assertEquals(dummyStatement.getExpenseById(childExpense1.getId()), childExpense1);
    assertEquals(dummyStatement.getExpenseById(childExpense2.getId()), childExpense2);

    // Add grandchildren to childExpense1
    dummyStatement.addExpenseToParent(grandChildExpense1, childExpense1);
    dummyStatement.addExpenseToParent(grandChildExpense2, childExpense1);
    dummyStatement.addExpenseToParent(greatGrandChild, grandChildExpense2);
    assertEquals(dummyStatement.getExpenseById(grandChildExpense1.getId()), grandChildExpense1);
    assertEquals(dummyStatement.getExpenseById(grandChildExpense2.getId()), grandChildExpense2);
    assertEquals(dummyStatement.getExpenseById(greatGrandChild.getId()), greatGrandChild);

    // Now delete childExpense1 (should delete its children too)
    dummyStatement.deleteExpense(childExpense1);
    assertEquals(dummyStatement.getExpenseById(childExpense1.getId()), Expenses.INVALID_EXPENSE);
    assertEquals(dummyStatement.getExpenseById(grandChildExpense1.getId()), Expenses.INVALID_EXPENSE);
    assertEquals(dummyStatement.getExpenseById(grandChildExpense2.getId()), Expenses.INVALID_EXPENSE);
    assertEquals(dummyStatement.getExpenseById(greatGrandChild.getId()), Expenses.INVALID_EXPENSE);

    // Ensure childExpense2 still exists
    assertEquals(dummyStatement.getExpenseById(childExpense2.getId()), childExpense2);

    // Delete the entire root expense (should remove all)
    dummyStatement.deleteExpense(rootExpense);
    assertEquals(dummyStatement.getExpenseById(rootExpense.getId()), Expenses.INVALID_EXPENSE);
    assertEquals(dummyStatement.getExpenseById(childExpense2.getId()), Expenses.INVALID_EXPENSE);
}


  // @Test 
  // public void deleteExpense(){
  //   //this is a statement with only root expense 
  //   Statement dummyStatement = new Statement("dummyStatement");
  //   Expenses newExpense1 = new Expenses("newExpense1");
  //   Expenses newExpense2 = new Expenses("newExpense2"); 

  //   //addExpense method works and gives Id 
  //   dummyStatement.addExpense(newExpense1);
  //   dummyStatement.addExpense(newExpense2);

  //   assertEquals(dummyStatement.getExpensesMap().size(), 1);
  // } 

  
}
