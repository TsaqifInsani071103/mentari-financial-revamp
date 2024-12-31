package com.mentarirvmp.utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.Formula;

public class ExpenseStatementHandler implements DataHandler{
  private Statement handledStatement; 
  private Formula formulaObject = new Formula(this); 

  private ArrayList<Expenses> validExpensesArray = new ArrayList<>();
  private Map<Expenses, Vertex> expenseToVertexMap = new HashMap<>();


  public ExpenseStatementHandler(Statement statement){
    this.handledStatement = statement;
  }

  //this reveals our inner data which we don't really want? But its the ExpenseStatementHandler anyways so I shouldn't mind it. 
  protected Map<Expenses, Vertex>  getExpenseToVertexMap(){
    return expenseToVertexMap;
  }

  // //I have yet to decide if I need this exclusive functionality or not. 
  // protected void resetExpenseToVertexMap(){

  // } 

  @Override
  public String getValueById(String ID) {
    String actualExpenseValue = "";
    Expenses checkedExpense; 

    checkedExpense=this.handledStatement.getExpenseById(ID);
    if(checkedExpense != Expenses.INVALID_EXPENSE){
      //SIDE EFFECT 
      this.validExpensesArray.add(checkedExpense); 

      actualExpenseValue = checkedExpense.getValue(); 
      return actualExpenseValue; 
    }else{
      return null;
    }
  }

  public boolean ifEquationValidSetExpenseValue(Expenses expense, String equation){
    //RESETTING VALID EXPENSES STACK
    this.validExpensesArray = new ArrayList<>(); 
    if(this.expenseToVertexMap.get(expense) == null) this.expenseToVertexMap.put(expense, new Vertex(expense));

    if(this.formulaObject.isFormulaValid(equation)){
      //side effect 
      // this.expenseVertexMap.put(expense, this.validExpensesArray); 
      for(int i = 0; i < validExpensesArray.size() ; i++){
        Expenses independentExpense = validExpensesArray.get(i);
        Vertex newVertex = this.expenseToVertexMap.get(independentExpense) == null? new Vertex(independentExpense) : this.expenseToVertexMap.get(independentExpense);

        newVertex.addDirectedEdgeToward(expenseToVertexMap.get(expense));
        this.expenseToVertexMap.put(independentExpense, newVertex);
      }

      expense.setEquation(equation); 
      String value = this.formulaObject.getValueWhenFormulaValid().toString();
      AcyclicGraphHandler dependencyResolver = new AcyclicGraphHandler(this.expenseToVertexMap);
      //update the expense value only if the equation does not resolve to be cyclic. 
      if(dependencyResolver.getTopSortArray() != null){
        expense.setValue(value); 
        dependencyResolver.refreshExpenseValuesProceeding(expense, this);
      }
      return true; 
    }
    return false; 
  } 


  public void setExpenseValueByFalseEquation(Expenses expense, String equation){
    expense.setEquation(equation);
    expense.setValue(this.formulaObject.DEFAULT_VALUE.toString()); 
  } 

  //with the prerequisite that the equation is valid in the first place. 
  protected ArrayList<Expenses> getExpenseArrayFromEquation(String equation){
    ArrayList<Expenses> expenseArray = new ArrayList<>(); 
    String regex = "\\bE\\d+\\b";
    Pattern pattern = Pattern.compile(regex); 
    Matcher matcher = pattern.matcher(equation); 
    while(matcher.find()){
      expenseArray.add(handledStatement.getExpenseById(matcher.group()));
    }

    return expenseArray;


  } 

  public boolean verifyEquationIntoFormulaNode(String equation){
    return this.formulaObject.isFormulaValid(equation);
  } 

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
