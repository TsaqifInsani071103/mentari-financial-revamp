package com.mentarirvmp.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

//since expenses is just one line of data entry, I think its fine to inject associated statement into this? 

//but hide away the operations of Statement from this class. 

// since Statement and Expenses should just be POJO classes that means I'm going to have to make a service class to define how Statement.java and Expenses interact. 

//I think I could make the formula object to act like a static class that's juggled by the Statements and Expense Handler Test.java. 

public class Expenses {
  public static Expenses INVALID_EXPENSE = new Expenses("invalid"); 
  
  private String name; 
  private String description = ""; 
  private String value = "0"; 
  //added a formulaString in Expenses so they can switch between formula and actual value in the UI. 
  private String formulaString =""; 
  private String id = ""; 
  private LinkedHashMap<String, Expenses> childMap = new LinkedHashMap<>();
  private ArrayList<Expenses> relatedExpensesByFormula = new ArrayList<Expenses>(); //maybe make this into a set of arrays in Statement.java? 

  
  public ArrayList<Expenses> getRelatedExpensesArray(){
    return this.relatedExpensesByFormula; 
  } 

  public Expenses(String name){
    this.name = name; 
  } 

  public String getName(){
    return this.name; 
  } 

  public void setName(String newName){
    this.name = newName; 
  }

  public String getValue(){
    return this.value; 
  }

  //set default value to be 0.0 aja 
  public void setValue(String newValue){
    this.value = newValue; 
  }

  public String getDescription(){
    return this.description; 
  } 

  public void setDescription(String newDescription){
    this.description = newDescription; 
  }


  //this needs to be completely unique 
  public String getId(){
    return this.id; 
  } 

  public void setId(String newId){
    this.id = newId;
  } 

  //since this is a POJO and not a like a singular data type we can still include things like addChild and removeChild that is tightly related to this here. 
  public void addChild(Expenses newChild){
    this.childMap.put(newChild.getId(), newChild); 
  } 

  public void deleteChild(Expenses childToDelete){
    this.childMap.remove(childToDelete.getId()); 
  } 

  public LinkedHashMap<String, Expenses> getChildMap(){
    return this.childMap; 
  } 

  public boolean hasChildren(){
    return this.childMap.size() > 0? true: false; 
  }


}

//Expense objects will have nested children baye.
