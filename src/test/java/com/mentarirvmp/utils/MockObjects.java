package com.mentarirvmp.utils;
import java.util.ArrayList;
import java.util.Random;
import com.mentarirvmp.statements.Statement;


//this will hold static mock Expense objects and Statement objects for testing purposes. 
public class MockObjects {
  
  public static ArrayList<Expenses> initializeDummyExpenses(){
    //use Random to make different random values 
    ArrayList<Expenses> dummyExpenses = new ArrayList<Expenses>(); 
    for(int i = 0; i < 5; i++){
      Expenses expenseDummy = new Expenses("Expense" + i);
      Random randObj = new Random(); 
      int randValue = randObj.nextInt(80);
      expenseDummy.setValue("" + randValue);

      dummyExpenses.add(expenseDummy); 
      System.out.println(expenseDummy.getValue());
    }
    return dummyExpenses; 
    
  } 

  public static Statement initializeDummyStatement(){
    Statement dummyStatement = new Statement("dummyStatement");
    
    return dummyStatement;
  } 
}
