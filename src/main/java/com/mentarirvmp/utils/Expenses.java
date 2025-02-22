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
  private String value = "0.0"; 
  //added a formulaString in Expenses so they can switch between formula and actual value in the UI. 
  private String formulaString =""; 
  private String id = ""; 

  //I dont really need this. I could just, for optimization sake, look through each formulas in each expenses and check if this expense's id exists within their formulas and only refresh their values if it is. (If the formula for this expense is changed.)
  private ArrayList<Expenses> relatedExpensesByFormula = new ArrayList<Expenses>(); 

  
  public ArrayList<Expenses> getRelatedExpensesArray(){
    return this.relatedExpensesByFormula; 
  } 

  public Expenses(String name){
    this.name = name; 
  } 

  public String getName(){
    return this.name; 
  } 

  public String getEquation(){
    return this.formulaString; 
  } 

  public void setEquation(String equation){
    this.formulaString = equation; 
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
    if(this.id.length() == 0) return id;
    return getParsedId(); 
  } 

  public String getFullId(){
    return this.id; 
  } 

  public String getParsedId(){
    int index = this.id.indexOf("_");
    return this.id.substring(0, index); 
  } 

  public void setId(String newId){
    this.id = newId;
  } 


  public String toString(){
    return getName(); 
  } 


}


