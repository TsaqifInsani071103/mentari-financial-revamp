package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;


import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.viewcreators.ExpensesViewCreator;

import javafx.application.Platform;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// import java.math.BigDecimal;
// import java.util.ArrayList;
import com.mentarirvmp.controllers.IndivProjectViewController;
public class ExpenseUIFlowTest {
  Project projectObj;
  

  // @Test 
  // public void runTest(){
  //   Platform.startup(() -> {
  //     expenseFlowTest();
  //   });
  // } 

  // public void expenseFlowTest(){
  //   Project project = new Project("dumProj");
  //   IndivProjectViewController controller = new IndivProjectViewController();
  //   controller.setProjectObject(project);
    
  //   Statement defaultStatement = project.getFirstStatement(); 
  //   ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(defaultStatement);
    

  //   Expenses expense1 = new Expenses("Expense1");
  //   Expenses expense2 = new Expenses("Expense2");
  //   expense2.setValue("100");
  //   Expenses expense3 = new Expenses("Expense3");
  //   expense3.setValue("110");
  //   Expenses expense4 = new Expenses("Expense4");
  //   expense4.setValue("101");

  //   defaultStatement.addExpense(expense1);
  //   defaultStatement.addExpenseToParent(expense2, expense1);
  //   defaultStatement.addExpenseToParent(expense3, expense4);

  //   dataHandler.traverseThroughAllData((e,p) -> {
  //     ExpensesViewCreator vc = new ExpensesViewCreator(e, dataHandler);
  //     vc.setParentController(controller);
  //     dataHandler.addExpenseView(e, vc);
  //   });
    
  //   //-------------- SETUP ENDS 

  //   int[] dataCount = {0}; 
  //   dataHandler.traverseThroughAllData((e, p) -> {
  //     dataCount[0]++; 
  //   });

  //   assertEquals(8, dataCount[0]);

  //   //add another expense in expense4
  //   dataHandler.addNewDefaultExpense(expense4);
  //   dataHandler.getViewGreatorByExpense(expense4).getParentController().refreshStatementView();
    
  //   assertNotEquals(9, dataCount[0]);




    

  // } 

}
