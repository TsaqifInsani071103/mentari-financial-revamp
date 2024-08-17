package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;
import java.util.LinkedHashMap;
import java.util.Map;

public class Statement {
  //I COULD HAVE A FLATTENED ARRAY OF ALL THE CHILDREN EXPENSES UPON SETUP FOR OPTIMIZATION AND EASE OF ADDING AND DELETING MAYBE 

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private int uniqueCounter = 0; 
  private LinkedHashMap<String, Expenses> expenseMap = new LinkedHashMap<>();
  //make like an array of hashmaps basically. 
  // private ArrayList<Expenses> expenseArrayList = new ArrayList<Expenses>(); 

  public Statement(String statementName){
    this.statementName = statementName; 
  } 

  public String getName(){
    return this.statementName; 
  } 

  public void setName(String newName){
    this.statementName = newName; 
  } 

  public void addExpenseToParent(Expenses newExpense, Expenses parentExpense){
    addExpense(newExpense, parentExpense.getChildMap());
  } 

  public void addExpense(Expenses newExpense){
    if(newExpense.getId().equals("")) setIdFor(newExpense);
    this.expenseMap.put(newExpense.getId(), newExpense);
  }

  public void addExpense(Expenses newExpense, LinkedHashMap<String, Expenses> childMap){
    if(newExpense.getId().equals("")) setIdFor(newExpense);
    childMap.put(newExpense.getId(), newExpense);
  } 
  
  public void setExpenseMap(LinkedHashMap<String, Expenses> expenseMap){
    this.expenseMap = expenseMap;
  }

  private void setIdFor(Expenses expense){
    String newId = "E" + this.uniqueCounter + "_" + this.statementName;
    expense.setId(newId);
    this.uniqueCounter++; 
  }

  public LinkedHashMap<String, Expenses> getExpensesMap(){
    return this.expenseMap;
  } 

  //Might have to change this into a linked HashMap Anyways.
  public Expenses getExpenseById(String ID){
    return recursiveGetExpenseById(this.expenseMap, ID);
  } 

  private Expenses recursiveGetExpenseById(LinkedHashMap<String,Expenses> childMap, String ID){
    if(childMap.containsKey(ID)) return childMap.get(ID);

    for(Map.Entry<String, Expenses> mapElement : childMap.entrySet()){
      Expenses expense = mapElement.getValue(); 
      if(expense.hasChildren()){
        if(expense.getChildMap().containsKey(ID)) return expense.getChildMap().get(ID);
        recursiveGetExpenseById(expense.getChildMap(), ID);
      }
    }

    return Expenses.INVALID_EXPENSE;
  } 

}
