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

    assertEquals(dummyStatement.getExpenseById(newExpense2.getId()), newExpense2);

  }

  @Test 
  public void addExpense(){
    //this is a statement with only root expense 
    Statement dummyStatement = new Statement("dummyStatement");
    Expenses newExpense1 = new Expenses("newExpense1");
    Expenses newExpense2 = new Expenses("newExpense2"); 
    dummyStatement.addExpense(newExpense1);
    assertEquals(dummyStatement.getRoot().getChildMap().get(newExpense1.getId()), newExpense1);

    dummyStatement.addExpenseToParent(newExpense2, newExpense1); 
    //we dont want to test according to the inner data structure of Statement.java we'll change this to the externally visible methods later. 
    assertEquals(dummyStatement.getRoot().getChildMap().get(newExpense1.getId()).getChildMap().get(newExpense2.getId()), newExpense2);


  } 

  
}
