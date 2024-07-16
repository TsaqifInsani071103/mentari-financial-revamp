package com.mentarirvmp.utils;
import java.util.ArrayList;
import java.util.Random;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;
import com.mentarirvmp.statements.Statement;


//this will hold static mock Expense objects and Statement objects for testing purposes. 
public class MockObjects {
  public static final String STATEMENT_NAME = "dummyStatement"; 
  public static final String EXPENSE_NAME = "Expense";
  public static final String DUMMY_FORMULA = "SUM"; 
  public static final String FULL_DUMMY_FORMULA = "SUM()"; 
  
  public static ArrayList<Expenses> getDummyExpenseArray(){
    //use Random to make different random values 
    ArrayList<Expenses> dummyExpenses = new ArrayList<Expenses>(); 
    for(int i = 0; i < 5; i++){
      Expenses expenseDummy = new Expenses(EXPENSE_NAME + i);
      Random randObj = new Random(); 
      int randValue = randObj.nextInt(80);
      expenseDummy.setValue("" + randValue);

      dummyExpenses.add(expenseDummy); 
      // System.out.println(expenseDummy.getValue());
    }

    //adding one child to each expenses to try and see if we can check nested children's ID. 
    int counter = 101; 
    for(Expenses expense: dummyExpenses){
      //we're gonna add children 
      Expenses expenseDummy = new Expenses(EXPENSE_NAME + counter);
      Random randObj = new Random(); 
      int randValue = randObj.nextInt(80);
      expenseDummy.setValue("" + randValue);
      counter++; 

      expense.addChild(expenseDummy); 
    }

    dummyExpenses.get(0).getChildArray().get(0).addChild(new Expenses("anotha one"));
    return dummyExpenses; 
    
  } 

  public static Statement initializeDummyStatement(){
    Statement dummyStatement = new Statement(STATEMENT_NAME);
    dummyStatement.setExpenseArray(getDummyExpenseArray());

    return dummyStatement;
  } 

  public static Statement initializeDummyStatement(ArrayList<Expenses> dummyExpenseArray){
    Statement dummyStatement = new Statement(STATEMENT_NAME);
    dummyStatement.setExpenseArray(dummyExpenseArray);

    return dummyStatement;
  } 

  public static StatementsAndExpenseHandler initializeDummyDataHandler(){
    StatementsAndExpenseHandler dataHandler = new StatementsAndExpenseHandler(initializeDummyStatement());
    return dataHandler; 
  } 

  public static StatementsAndExpenseHandler initializeDummyDataHandler(Statement statement){
    StatementsAndExpenseHandler dataHandler = new StatementsAndExpenseHandler(statement);
    return dataHandler; 

  } 

  //make dummy Formula from dummyStatement; 
  public static Formula makeDummyFormulaFrom(Statement dummyStatement){
    String content = ""; 
    for(Expenses expense:getDummyExpenseArray()){
      content += expense.getId(); 
    }
    String fullFormula = DUMMY_FORMULA + "(" +content+ ")";
    return new Formula(fullFormula); 
  } 

  public static String getContentFromMockFormula(){
    
  } 
}
