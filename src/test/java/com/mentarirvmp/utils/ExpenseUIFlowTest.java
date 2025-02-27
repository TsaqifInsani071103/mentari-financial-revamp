package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;


import com.mentarirvmp.statements.Statement;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

// import java.math.BigDecimal;
// import java.util.ArrayList;
import com.mentarirvmp.controllers.IndivProjectViewController;
public class ExpenseUIFlowTest {
  Project projectObj;
  

  @Test 
  public void expenseFlowTest(){
    Project project = new Project("dumProj");
    Statement defaultStatement = project.getFirstStatement(); 
    Expenses expense1 = new Expenses("Expense1");
    Expenses expense2 = new Expenses("Expense2");
    expense2.setValue("100");
    Expenses expense3 = new Expenses("Expense3");
    expense3.setValue("110");
    Expenses expense4 = new Expenses("Expense4");
    expense4.setValue("101");

    defaultStatement.addExpense(expense1);
    defaultStatement.addExpenseToParent(expense2, expense1);
    defaultStatement.addExpenseToParent(expense2, expense1);


    
    
    

  } 

}
