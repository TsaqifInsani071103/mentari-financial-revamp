package com.mentarirvmp.servicehandlers;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Formula;
import com.mentarirvmp.utils.Expenses;
import java.util.ArrayList;


public class StatementsAndExpenseHandler {
  //hold STATEMENT variable here with its EXPENSES ARRAY 
  //and hold a FORMULA object here for the calculations and whatnot 

  private Statement currentStatement; 

  public StatementsAndExpenseHandler(Statement currentStatement){
    this.currentStatement = currentStatement; 
  }

  public Expenses getExpenseByID(String ID){
    return currentStatement.getExpenseById(ID); 
  }

  public int getValueIfExpenseIdValid(String id){
    Expenses currentExpense = getExpenseByID(id);
    if(currentExpense != Expenses.INVALID_EXPENSE){
      return Integer.parseInt(currentExpense.getValue());
    }
    return 0; 

  } 





  //cant formula just be a static class at this point? 


  
  


  //I think we can make an instance of this handler by each Statement made or something 

  //I think this should also take care of the StatementViewHandler like a middle man between the POJOS and the viewHandler, but I think the view itself will be controlled by indivProjectViewcontroller no?  

  //updateExpenseFormulaFields()? This immediately concerns the view so it should be in the view handler  

  //potential things this could do 
  //updateFormulas()
  //findExpenseByName() using while loop and not recursion hopefully 
  //findExpenseById() I think is better. 

  //we also need to make FormulaObject so I could test this correctly. 



  
  
}
