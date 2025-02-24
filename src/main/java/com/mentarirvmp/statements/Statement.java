package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.viewcreators.StatementViewCreator;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Statement {
  //I COULD HAVE A FLATTENED ARRAY OF ALL THE CHILDREN EXPENSES UPON SETUP FOR OPTIMIZATION AND EASE OF ADDING AND DELETING MAYBE 

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private int uniqueCounter = 0; 
  private Expenses rootExpense;
  //Id to Expense 
  private LinkedHashMap<String, Expenses> idToExpenseMap = new LinkedHashMap<>();
  private LinkedHashMap<Expenses, Set<Expenses>> parentToChildMap = new LinkedHashMap<>(); 
   
  private String id = ""; 


  public Statement(String statementName){
    this.statementName = statementName; 
    setId(UUID.randomUUID().toString());
    //automatically makes a root expense 
    Expenses rootExpense = new Expenses("root");
    setIdFor(rootExpense);
    this.idToExpenseMap.put(rootExpense.getId(), rootExpense);
    this.parentToChildMap.put(rootExpense, new LinkedHashSet<Expenses>());
    this.rootExpense=rootExpense;
  } 

  public Object[] getAllUnderlyingStatementData(){
    return this.idToExpenseMap.values().toArray();
  } 

  public String getName(){
    return this.statementName; 
  } 

  public Expenses getRoot(){
    return this.rootExpense;
  } 

  public LinkedHashMap<String, Expenses> getExpensesMap(){
    return this.idToExpenseMap;
  } 

  public LinkedHashMap<Expenses, Set<Expenses>> getParentChildExpenses(){
    return parentToChildMap;
  }   

  public Expenses getExpenseById(String ID){
    if(this.idToExpenseMap.get(ID) == null){
      return Expenses.INVALID_EXPENSE;
    }else{
      return this.idToExpenseMap.get(ID); 
    }
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

   //when Adding a new expense, always put it in the root Expense. 
   public void addExpense(Expenses newExpense){
    addExpenseToParent(newExpense, this.rootExpense);
  }

  //do I need to check for duplicate entries? 
  public void addExpenseToParent(Expenses newExpense, Expenses parentExpense){
    if(newExpense.getId().equals("")) setIdFor(newExpense);
    this.idToExpenseMap.put(newExpense.getId(), newExpense);
    //also immediately add the children into the parentChildMap 
    this.parentToChildMap.put(newExpense, new LinkedHashSet<Expenses>());
    if(!this.parentToChildMap.containsKey(parentExpense)){
      LinkedHashSet<Expenses> childMap = new LinkedHashSet<Expenses>();
      childMap.add(newExpense);
      this.parentToChildMap.put(parentExpense,childMap);
    }else{
      this.parentToChildMap.get(parentExpense).add(newExpense); 
    }
  } 



  public void deleteExpense(Expenses targetExpense){
    Set<Expenses> childrenSet = getAllChildren(targetExpense);
    for(Expenses parent: this.parentToChildMap.keySet()){
      if(this.parentToChildMap.get(parent).contains(targetExpense)){
        this.parentToChildMap.remove(targetExpense); 
        this.parentToChildMap.get(parent).remove(targetExpense);
        break; 
      }
    }

    //HAVE THE OPTION TO JUST MOVE SOME EXPENSES UP A LEVEL INSTEAD OF DELETING ALL OF THE EXPENSE CHILDREN. 
    this.idToExpenseMap.remove(targetExpense.getId()); 
    if(childrenSet.size() > 0){
      for(Expenses child: childrenSet){
        deleteExpense(child);
      }
    }
  }

  private Set<Expenses> getAllChildren(Expenses targetExpenses){
    Set<Expenses> childrenSet = new LinkedHashSet<Expenses>();
    for(Expenses child: this.parentToChildMap.get(targetExpenses)){
      childrenSet.add(child); 
    }
    return childrenSet;
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



  // private Expenses recursiveGetExpenseById(LinkedHashMap<String,Expenses> childMap, String ID){
  //   if(childMap.containsKey(ID)) return childMap.get(ID);
  
  //   for(Map.Entry<String, Expenses> mapElement : childMap.entrySet()){
  //     Expenses expense = mapElement.getValue(); 
  //     if(expense.hasChildren()){
  //       if(expense.getChildMap().containsKey(ID)) return expense.getChildMap().get(ID); 

  //       Expenses foundExpense = recursiveGetExpenseById(expense.getChildMap(), ID);
  //       if(foundExpense != Expenses.INVALID_EXPENSE){
  //         return foundExpense; 
  //       }
  //     }
  //   }

  //   return Expenses.INVALID_EXPENSE;
  // } 

}

//Might want to check how uniqueCounter might revert back to zero everytime you load the Statement from the database. This will crash the uniqueId Process

//