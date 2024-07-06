package com.mentarirvmp.utils;
import java.util.ArrayList;
import java.util.Random;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;
import com.mentarirvmp.statements.Statement;


//this will hold static mock Expense objects and Statement objects for testing purposes. 
public class MockObjects {
  
  public static ArrayList<Expenses> getDummyExpenseArray(){
    //use Random to make different random values 
    ArrayList<Expenses> dummyExpenses = new ArrayList<Expenses>(); 
    for(int i = 0; i < 5; i++){
      Expenses expenseDummy = new Expenses("Expense" + i);
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
      Expenses expenseDummy = new Expenses("Expense" + counter);
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
    Statement dummyStatement = new Statement("dummyStatement");
    dummyStatement.setExpenseArray(getDummyExpenseArray());

    return dummyStatement;
  } 

  public static StatementsAndExpenseHandler initializeDummyDataHandler(){
    StatementsAndExpenseHandler dataHandler = new StatementsAndExpenseHandler(initializeDummyStatement());
    return dataHandler; 
  } 
}
