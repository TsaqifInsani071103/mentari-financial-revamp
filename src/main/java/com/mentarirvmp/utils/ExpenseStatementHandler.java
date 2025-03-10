package com.mentarirvmp.utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.Formula;
import com.mentarirvmp.viewcreators.ExpensesViewCreator;


public class ExpenseStatementHandler implements DataHandler{
  private Statement handledStatement; 
  // private Formula formulaObject = new Formula(this); 

  private ArrayList<Expenses> validExpensesInEquation = new ArrayList<>();
  private Map<Expenses, ExpensesViewCreator> expenseToViewMap = new HashMap<>();
  protected AcyclicGraphHandler dependencyResolver = new AcyclicGraphHandler();
  


  public ExpenseStatementHandler(Statement statement){
    this.handledStatement = statement;
  }

  public void addExpenseView(Expenses expense, ExpensesViewCreator view){
    this.expenseToViewMap.put(expense, view);
  } 

  private void removeExpenseView(Expenses expense){
    this.expenseToViewMap.remove(expense); 
  }

  public ExpensesViewCreator getViewGreatorByExpense(Expenses expense){
    return this.expenseToViewMap.get(expense); 
  }

  public Expenses getRootExpense(){
    return this.handledStatement.getRoot(); 
  } 

  public Expenses getParentExpenseFromChild(Expenses child){
    for(Expenses parent: this.handledStatement.getParentChildExpenses().keySet()){  
      if(this.handledStatement.getParentChildExpenses().get(parent).contains(child)){
        return parent;
      }
    }
    return Expenses.INVALID_EXPENSE; 
  } 

  public void removeExpenseFromParent(Expenses child, Expenses parent){


    this.handledStatement.getParentChildExpenses().get(parent).remove(child);
    
    // System.out.println(parent);
    // System.out.println(child);
  } 

public void addExpenseToParentAtIndex(Expenses child, Expenses parent, int index){
    LinkedHashSet<Expenses> expensesSet = (LinkedHashSet<Expenses>) this.handledStatement.getParentChildExpenses().get(parent);
    
    List<Expenses> expenseList = new ArrayList<>(expensesSet);  // Convert set to list

    // If index is -1 or out of bounds, insert at the end
    if (index < 0 || index > expenseList.size()) {
        index = expenseList.size();
    }

    expenseList.add(index, child);  // Insert at the calculated index
    this.handledStatement.getParentChildExpenses().put(parent, new LinkedHashSet<>(expenseList));  // Update the set
}

  public int getExpenseIndexInParentSet(Expenses child, Expenses parent){
   ArrayList<Expenses> list = new ArrayList<Expenses>(this.handledStatement.getParentChildExpenses().get(parent));
   return list.indexOf(child); 
  } 

  public Expenses getExpenseById(String id){
    return this.handledStatement.getExpenseById(id);
  } 

  @Override
  public String getValueById(String ID) {
    String actualExpenseValue = "";
    Expenses checkedExpense; 

    checkedExpense=this.handledStatement.getExpenseById(ID);
    if(checkedExpense != Expenses.INVALID_EXPENSE){
      //SIDE EFFECT 
      this.validExpensesInEquation.add(checkedExpense); 

      actualExpenseValue = checkedExpense.getValue(); 
      return actualExpenseValue; 
    }else{
      return null;
    }
  }



  public boolean ifEquationValidSetExpenseValue(Expenses expense, String equation){
    //RESETTING VALID EXPENSES STACK
    this.validExpensesInEquation = new ArrayList<>(); 
    ArrayList<ExpensesViewCreator> expenseToHighlightMap = new ArrayList<>();
    Formula formulaObject = new Formula(this);
    if(formulaObject.isFormulaValid(equation)){
      // System.out.println("EXPENSE + " + expense.getName() + "VALID EQUATION : " + equation);

      this.dependencyResolver.initializeExpenseAndDependencies(expense, validExpensesInEquation);
      Expenses[] topSort = dependencyResolver.getTopSortArray();

      if(topSort!=null){
        calculateAndSetExpenseValue(expense,formulaObject, equation);
        if(this.getViewGreatorByExpense(expense) !=null && !this.validExpensesInEquation.contains(expense)){
          this.validExpensesInEquation.forEach((validExpense) -> {
            expenseToHighlightMap.add(this.getViewGreatorByExpense(validExpense)); 
          });
          this.getViewGreatorByExpense(expense).populateHighlightMap(expenseToHighlightMap);
        }
        refreshExpenseViewsProceeding(expense);
        // System.out.println("THIS IS TOP SORT: " + Arrays.toString(topSort));
        // System.out.println(Arrays.toString(topSort));
      }else{
        if(validExpensesInEquation.size() ==0){
          calculateAndSetExpenseValue(expense, formulaObject, equation);
        }else{
          setExpenseValueByFalseEquation(expense, equation);
          return false; 
        }
        // dependencyResolver.resetDependencyGraph();
        //if the graph is cyclic, well set the equation to be the previous equation 
        // this.expenseToViewMap.get(expense).updateFalseValue();


      }
      return true; 
    }
    setExpenseValueByFalseEquation(expense, equation);
    refreshExpenseViewsProceeding(expense);
    return false; 
  } 

  public void deleteExpense(Expenses targetExpense){
    removeExpenseView(targetExpense);
    this.handledStatement.deleteExpense(targetExpense);
    refreshExpenseViewsProceeding(targetExpense);
    this.dependencyResolver.deleteExpenseFromGraph(targetExpense);
  } 

  public void addNewDefaultExpense(Expenses parentExpense){
    Expenses newExpense = new Expenses("defaultExpense");
    this.handledStatement.addExpenseToParent(newExpense, parentExpense);
    traverseThroughAllData((e, p) -> {
      if(e.getEquation().contains(newExpense.getId())){
        updateViewAccordingly(e);
      }
    });
  } 

  private void refreshExpenseViewsProceeding(Expenses expense){
    ArrayList<Expenses> chronologicalArrays = dependencyResolver.getValuesProceeding(expense);
    if(chronologicalArrays.size() > 0){
      for(Expenses item : chronologicalArrays){
        updateViewAccordingly(item);
      }
    } 
  } 

  private void updateViewAccordingly(Expenses item){
    ExpensesViewCreator itemView = this.expenseToViewMap.size() > 0? this.expenseToViewMap.get(item) : null;
    if(ifEquationValidSetExpenseValue(item, item.getEquation()) && itemView != null){
      itemView.updateCorrectEquationDisplay();
    }else if (itemView != null){
      itemView.updateFalseEquationDisplay();
    }
  } 

  private void calculateAndSetExpenseValue(Expenses expense,Formula formulaObject, String equation){
    String value = formulaObject.turnIntoCommadString(formulaObject.getValueWhenFormulaValid());
    expense.setValue(value); 
    try {
      BigDecimal trueIfProperNumberFormat = new BigDecimal(equation);
      expense.setEquation(value);
    } catch (NumberFormatException e) {
      expense.setEquation(equation);
    }
  } 

  public void setExpenseValueByFalseEquation(Expenses expense, String equation){
    Formula formulaObject = new Formula(this);
    expense.setEquation(equation);
    expense.setValue(formulaObject.DEFAULT_VALUE.toString()); 
  } 

  //with the prerequisite that the equation is valid in the first place. 
  // protected ArrayList<Expenses> getExpenseArrayFromEquation(String equation){
  //   ArrayList<Expenses> expenseArray = new ArrayList<>(); 
  //   String regex = "\\bE\\d+\\b";
  //   Pattern pattern = Pattern.compile(regex); 
  //   Matcher matcher = pattern.matcher(equation); 
  //   while(matcher.find()){
  //     expenseArray.add(handledStatement.getExpenseById(matcher.group()));
  //   }

  //   return expenseArray;


  // } 

  public boolean verifyEquationIntoFormulaNode(String equation){
    Formula formulaObject = new Formula(this);
    return formulaObject.isFormulaValid(equation);
  } 


  // public boolean expenseNameUnique(String name){
  //   for(Object expense:this.handledStatement.getAllUnderlyingStatementData()){
  //     Expenses object = (Expenses) expense;
  //     if(object.getName().equals(name)) return false; 
      
  //   }
  //   return true; 
  // } 


  //maybe you can return a true or a false from here to stop traversing through all the data 
  public void traverseThroughAllData(BiConsumer<Expenses, Expenses> expenseConsumer) {
      traverse(handledStatement.getRoot(), null, expenseConsumer); 
  }

  private void traverse(Expenses expense, Expenses parentExpense, BiConsumer<Expenses, Expenses> expenseConsumer) {

      expenseConsumer.accept(expense, parentExpense); // Process the current expense 
      for(Expenses childExpense: this.handledStatement.getParentChildExpenses().get(expense)){
        // System.out.println(childExpense.getName());
        traverse(childExpense, expense, expenseConsumer);
      }
  }





  //SHOULD BE RESPONSIBLE FOR MAKING A NEW EXPENSE AND ADDING IT TO THE STATEMENT 
  //SHOULD BE RESPONSIBLE TO QUERYING AN EXPENSE TO DELETE AND SIGNALING STATEMENT TO DELETE IT. 

  
}
