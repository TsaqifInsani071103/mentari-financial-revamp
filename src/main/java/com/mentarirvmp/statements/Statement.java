package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.viewcreators.StatementViewCreator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Statement {
  //I COULD HAVE A FLATTENED ARRAY OF ALL THE CHILDREN EXPENSES UPON SETUP FOR OPTIMIZATION AND EASE OF ADDING AND DELETING MAYBE 

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private int uniqueCounter = 0; 
  //Id to Expense 
  private LinkedHashMap<String, Expenses> idToExpenseMap = new LinkedHashMap<>(); 
  private String id = ""; 


  public Statement(String statementName){
    this.statementName = statementName; 
    setId(UUID.randomUUID().toString());
    //automatically makes a root expense 
    Expenses rootExpense = new Expenses("root");
    setIdFor(rootExpense);
    this.idToExpenseMap.put(rootExpense.getId(), rootExpense);
  } 

  public String getName(){
    return this.statementName; 
  } 

  public Expenses getRoot(){
    //does this mutate the iterator? 
    String firstKey = this.idToExpenseMap.keySet().iterator().next(); 
    return this.idToExpenseMap.get(firstKey);
  } 

  public void setName(String newName){
    this.statementName = newName; 
  } 

  public void setId(String id){
    this.id = id; 
  } 

  public String getId(){
    return this.id; 
  } 

  public void addExpenseToParent(Expenses newExpense, Expenses parentExpense){
    // System.out.println("ADDING: " + newExpense + " To " + parentExpense);
    addExpense(newExpense, parentExpense.getChildMap());
  } 

  //do I need to check for duplicate entries? 
  public void addExpense(Expenses newExpense, LinkedHashMap<String, Expenses> childMap){
    if(newExpense.getId().equals("")) setIdFor(newExpense);
    childMap.put(newExpense.getId(), newExpense);
  } 

 //when Adding a new expense, always put it in the root Expense. 
  public void addExpense(Expenses newExpense){
    if(newExpense.getId().equals("")) setIdFor(newExpense);
    getRoot().getChildMap().put(newExpense.getId(), newExpense);
  }

  public void deleteExpense(Expenses targetExpense){
    

  }


  
  public void setIdToExpenseMap(LinkedHashMap<String, Expenses> expenseMap){
    this.idToExpenseMap = expenseMap;
  }

  //setting the id of each expense by Statement id. 
  private void setIdFor(Expenses expense){
    String newId = "E" + this.uniqueCounter + "_" + this.id;
    expense.setId(newId);
    this.uniqueCounter++; 
  }

  public LinkedHashMap<String, Expenses> getExpensesMap(){
    return this.idToExpenseMap;
  } 


  // public Expenses getExpenseByName(String name){
  //   return recursiveGetExpenseByName(this.expenseMap, name);
  // }

  // private Expenses recursiveGetExpenseByName(){

  // }

  public Expenses getExpenseById(String ID){
    return recursiveGetExpenseById(this.idToExpenseMap, ID);
  } 

  private Expenses recursiveGetExpenseById(LinkedHashMap<String,Expenses> childMap, String ID){
    if(childMap.containsKey(ID)) return childMap.get(ID);
  
    for(Map.Entry<String, Expenses> mapElement : childMap.entrySet()){
      Expenses expense = mapElement.getValue(); 
      if(expense.hasChildren()){
        if(expense.getChildMap().containsKey(ID)) return expense.getChildMap().get(ID); 

        Expenses foundExpense = recursiveGetExpenseById(expense.getChildMap(), ID);
        if(foundExpense != Expenses.INVALID_EXPENSE){
          return foundExpense; 
        }
      }
    }

    return Expenses.INVALID_EXPENSE;
  } 

}

//Might want to check how uniqueCounter might revert back to zero everytime you load the Statement from the database. This will crash the uniqueId Process

//