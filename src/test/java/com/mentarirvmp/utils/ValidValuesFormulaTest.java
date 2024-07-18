package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;
import com.mentarirvmp.statements.Statement;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
public class ValidValuesFormulaTest {
  //test Setup 
  private ArrayList<Expenses> dummyExpenses = MockObjects.getDummyExpenseArray();
  private Statement dummyStatement = MockObjects.initializeDummyStatement(dummyExpenses);
  private StatementsAndExpenseHandler dataHandler = MockObjects.initializeDummyDataHandler(dummyStatement);
  private Formula dummyFormula = MockObjects.makeDummyFormulaFrom(dummyStatement);

  @BeforeEach
  public void setDataHandler(){
    dummyFormula.setHandler(dataHandler);
  } 

  @Test 
  public void getValueFromValidExpensesTest(){
    this.dummyExpenses.clear();
    Expenses expense1 = new Expenses("asfgearfw");
    expense1.setValue("10"); 
    Expenses expense2 = new Expenses("hrsthfdg");
    expense2.setValue("10"); 
    Expenses expense3 = new Expenses("yrsthfg");
    expense3.setValue("10"); 
    dummyExpenses.add(expense1);
    dummyExpenses.add(expense2);
    dummyExpenses.add(expense3);

    //resetting dummyFormula 
    dummyFormula = MockObjects.makeDummyFormulaFrom(dummyStatement); 
    dummyFormula.setHandler(dataHandler);

    assertEquals(30, dummyFormula.calculateFormulaValue()); 

  } 

  @Test 
  public void getValidExpensesArrayTest(){
    String[] idArray = new String[dummyExpenses.size() + 3]; 
    for(int i = 0; i < dummyExpenses.size(); i++){
      idArray[i] = String.valueOf(dummyExpenses.get(i).getId()); 
    }

    //hardcoded invalidId's 
    idArray[dummyExpenses.size()] = "12b"; //String 
    idArray[dummyExpenses.size() + 1] = "1312524351231345123";//bigInt
    idArray[dummyExpenses.size() + 2] = "12.0.23";//decimal

    //we test by ensuring that the valid Id's only have the size of the valid dummyExpenses
    assertEquals(dummyExpenses.size(), dummyFormula.getValidExpensesArray(idArray).size());
  } 


}
