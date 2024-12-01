package com.mentarirvmp.utils;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.Formula;

public class ExpenseStatementHandler implements DataHandler{
  Statement handledStatement; 

  public ExpenseStatementHandler(Statement statement){
    this.handledStatement = statement;
  }

  @Override
  public String getValueById(String ID) {
    String actualExpenseValue = "";
    Expenses checkedExpense; 
    checkedExpense=this.handledStatement.getExpenseById(ID);
    if(checkedExpense != Expenses.INVALID_EXPENSE){
      actualExpenseValue = checkedExpense.getValue(); 
      return actualExpenseValue; 
    }else{
      return null;
    }
  }

  public void setExpenseValueByNewEquation(Expenses expense, String equation){
    expense.setEquation(equation); 
    expense.setValue(getDecimalValueFromEquation(equation).toString()); 


    
  } 

  public BigDecimal getDecimalValueFromEquation(String formula){
    Formula formulaObject = new Formula(this);
    return formulaObject.getValueIfFormulaValid(formula);

  } 



  public void traverseThroughAllData(BiConsumer<Expenses, Expenses> expenseConsumer) {
      traverse(handledStatement.getRoot(), null, expenseConsumer); 
  }

  private void traverse(Expenses expense, Expenses parentExpense, BiConsumer<Expenses, Expenses> expenseConsumer) {

      expenseConsumer.accept(expense, parentExpense); // Process the current expense
      
      for(Map.Entry<String, Expenses> mapElement : expense.getChildMap().entrySet()){
        Expenses childExpense = mapElement.getValue(); 
        traverse(childExpense, expense, expenseConsumer); 
      }
  }


  //SHOULD BE RESPONSIBLE FOR MAKING A NEW EXPENSE AND ADDING IT TO THE STATEMENT 
  //SHOULD BE RESPONSIBLE TO QUERYING AN EXPENSE TO DELETE AND SIGNALING STATEMENT TO DELETE IT. 

  
}
