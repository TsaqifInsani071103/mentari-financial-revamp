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
 Formula formulaObject = new Formula(this); 

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

  public boolean ifEquationValidSetExpenseValue(Expenses expense, String equation){
    if(this.formulaObject.isFormulaValid(equation)){
      expense.setEquation(equation); 
      expense.setValue(this.formulaObject.getValueWhenFormulaValid().toString()); 
      return true; 
    }
    return false; 
  } 

  public void setExpenseValueByFalseEquation(Expenses expense, String equation){
    expense.setEquation(equation);
    expense.setValue(this.formulaObject.DEFAULT_VALUE.toString()); 
  } 

  // public boolean verifyEquationIntoFormulaNode(String equation){
  //   return this.formulaObject.isFormulaValid(equation);
  // } 

  //this one resets the Formula Field's equation node, so it shouldn't clash with formulaObject.getValueWhenFormulaValid(). 
  public BigDecimal getDecimalValueFromEquation(String formula){
    return this.formulaObject.getValueIfFormulaValid(formula);

  } 

  public boolean expenseNameUnique(String name){
    boolean[] flag = new boolean[1]; 
    flag[0] = true; 
    traverseThroughAllData((expense, expenseParent) -> {
      if(name.equals(expense.getName())){
        flag[0] = false;
      }
    });
    return flag[0]; 
  } 


  //maybe you can return a true or a false from here to stop traversing through all the data 
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
