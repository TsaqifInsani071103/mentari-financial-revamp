package com.mentarirvmp.utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

  public ExpensesViewCreator getViewGreatorByExpense(Expenses expense){
    return this.expenseToViewMap.get(expense); 
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
          System.out.println();
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
    // System.out.println(String.format("%s's new equation: %s as opposed to curret equation: , is FALSE!!! value: %s", expense.getName(), equation, expense.getEquation(), expense.getValue()));
    return false; 
  } 

  private void refreshExpenseViewsProceeding(Expenses expense){
    ArrayList<Expenses> chronologicalArrays = dependencyResolver.getValuesProceeding(expense);
    if(chronologicalArrays.size() > 0){
      for(Expenses item : chronologicalArrays){
        this.ifEquationValidSetExpenseValue(item, item.getEquation());
        // System.out.println("CHANGED THE VALUE OF: " + item.getName() + "   " + item.getValue());
        if(this.expenseToViewMap.size() > 0){
          ExpensesViewCreator dependentExpenseView = this.expenseToViewMap.get(item);
          dependentExpenseView.updateValueDisplay();
          // expenseToHighlightMap.add(dependentExpenseView);

        }
      }
      // //ANOTHER SIDE EFFECT THATS RUINING THE SRP OF THIS FUNCTION!! 

      //   if(this.expenseToViewMap.get(expense) != null){

      //     this.expenseToViewMap.get(expense).populateHighlightMap(expenseToHighlightMap); 
      //   }
   

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
