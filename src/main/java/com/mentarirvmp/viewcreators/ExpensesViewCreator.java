package com.mentarirvmp.viewcreators;

import javafx.application.Platform;

// import com.mentari.statements.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.Parent;
// import com.mentari.utils.ExpenseType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import java.util.ArrayList;

import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.controllers.IndivProjectViewController;
import com.mentarirvmp.utils.ExpenseStatementHandler;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.Formula;
import com.mentarirvmp.utils.ViewCreator;
import com.mentarirvmp.statements.Statement;

import javafx.scene.control.Control;


public class ExpensesViewCreator implements ViewCreator {
  public Expenses currentExpense; 
  private transient TextField textFieldReference; 
  private ChildControllers controller; 
  //either use statement or statementExpenseHandler, we dont know yet. 
  private ExpenseStatementHandler dataHandler; 

  protected static boolean changedByListener = false; 
  private VBox alreadyMadeView; 

  public ExpensesViewCreator(Expenses expense, ExpenseStatementHandler dataHandler){
    this.currentExpense = expense; 
    // this.dataHandler = dataHandler; 
    // dataHandler.addExpenseView(expense, this);

    //we'll initialize an expense dependency map here in dataHandler. 
    // dataHandler.ifEquationValidSetExpenseValue(expense, expense.getEquation());

  } 

  @Override
   public Parent getView() {
    if(alreadyMadeView == null){
      VBox overallContainer = new VBox();
      getInnerContainer(overallContainer);
      this.alreadyMadeView = overallContainer;
    }
    return alreadyMadeView; 
  } 

  public HBox getBox(){
    HBox box = new HBox(5);
    Label idLabel = new Label(String.valueOf(currentExpense.getParsedId()) + ")"); 
    Control nameTextField = getNameTextField(); 
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    box.getChildren().addAll(idLabel,nameTextField,spacer, getValueTextField()); 
    return box; 
  } 

  private Control getValueTextField(){
    TextField textField = new TextField(this.currentExpense.getValue()); 
    textField.getStyleClass().addAll("text-field", "black-underline"); 
    textField.setId(this.currentExpense.getId());
    // textField.setStyle("-fx-font-weight: bold");
    addListener(textField);
    addFocusListener(textField);
    textFieldReference = textField;
    return textField; 
  }

  public void addListener(TextField textField) {
    textField.textProperty().addListener(new ChangeListener<String>(){
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
        // System.out.println(currentExpense.getName() + " CHANGED BY LISTENER: " + changedByListener);
        if(!changedByListener){
          textFieldReference.getStyleClass().removeAll("red-underline", "black-underline");
          if(dataHandler.ifEquationValidSetExpenseValue(currentExpense, newValue)){
            textFieldReference.getStyleClass().add("black-underline");
          }else{
            dataHandler.setExpenseValueByFalseEquation(currentExpense, newValue);
            textFieldReference.getStyleClass().add("red-underline");
          }
        }
      }
    });
  }

  public void updateValueDisplay(){
    if(textFieldReference != null) {
      toggleChangedByListener();
      textFieldReference.setText(this.currentExpense.getValue());
      toggleChangedByListener();
    }
  }


  //this is only to toggle value and equation UI not to set the value of the expense itself. 
  private void addFocusListener(TextField textField) {
    textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      toggleChangedByListener();
      if (newValue) { // Gains focus
        textField.setText(this.currentExpense.getEquation());
      } else { // Loses focus
        // getAssociatedStatement().updateFormulas();
        String value = this.currentExpense.getValue(); 
        textField.setText(value);
       
        
      }
      toggleChangedByListener();
    });
  }

  public void toggleChangedByListener(){
    changedByListener = !changedByListener;
  } 

  private HBox getInnerContainer(VBox overallContainer){
    Control[] descriptionFieldArray = getDescriptionAndButtonArray();
    HBox box = getBox(); 
    box.getChildren().add(0, descriptionFieldArray[1]);
    overallContainer.getChildren().addAll(box, descriptionFieldArray[0]);
    return box; 
  } 


  protected Control getNameTextField(){
    TextField textField = new TextField(this.currentExpense.getName()); 
    textField.getStyleClass().addAll("text-field");
    textField.setPrefWidth(getTextWidth(this.currentExpense.getName(), textField));
    addNameListener(textField);
    addKeyListener(textField); 
    return textField; 
  } 

  private double getTextWidth(String string, TextField textField) {
    Text text = new Text(string);
    text.setFont(textField.getFont());
    double textWidth = text.getBoundsInLocal().getWidth();
    double paddingAndBordersAdjustment = 20; 
    return textWidth + paddingAndBordersAdjustment;
}
  
  private void addNameListener(TextField textField){
    textField.textProperty().addListener(new ChangeListener<String>(){
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
        textField.getStyleClass().removeAll("red-underline");
        if(dataHandler.expenseNameUnique(newValue)){
          changeTextOnValue(textField, newValue);
        }else{
          textField.getStyleClass().add("red-underline");
        }
      }
    });
  } 

  private void changeTextOnValue(TextField textField, String value){
    this.currentExpense.setName(value); 
    textField.setPrefWidth(getTextWidth(value,textField)); // Set TextField width (add some padding)
  }

  // private void checkForUniqueName(TextField textField, String newValue, String oldValue){
  //   if(nameIsUnique(newValue)){
  //     changeTextOnValue(textField, newValue);
  //   }else{
  //     Expenses.uniqueCounter++; 
  //     changeTextOnValue(textField, "newExpense" + Expenses.uniqueCounter);
  //     textField.getStyleClass().add("red-underline");
  //   }
  // }



  private void addKeyListener(TextField textField){
    textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.TAB) {
          String spaces = "     "; // 5 spaces
          textField.insertText(textField.getCaretPosition(), spaces);
          event.consume();
      }
    });
  }

  // private boolean nameIsUnique(String name){
  //   Boolean[] flag = new Boolean[1];
  //   flag[0] = true;  
  //   this.associatedStatement.traverseExpenses((expense)->{
  //     if(expense.getName().equals(name)){
  //       flag[0] = false; 
  //     }
  //   });
  //   return flag[0];
  // } 


  public Control[] getDescriptionAndButtonArray() {
    TextArea textArea = getDescriptionTextArea();
    addTextAreaListener(textArea, this.currentExpense);
    Button toggleButton = makeTriangleButton(textArea);

    Control[] elementArray = new Control[2];
    elementArray[0] = textArea;
    elementArray[1] = toggleButton;

    return elementArray;
  }

  private TextArea getDescriptionTextArea(){
    TextArea textArea = new TextArea(this.currentExpense.getDescription()); // Replace getName() with your method to get the initial text
    textArea.setWrapText(true);
    textArea.setVisible(false); // Initially hidden
    textArea.setManaged(false); // Initially not managed
    textArea.getStyleClass().addAll("text-area");
    return textArea; 
  } 

  private void addTextAreaListener(TextArea textArea, Expenses expense){
    textArea.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        expense.setDescription(newValue);
      }
    });

  } 

  //temporary
  protected Button makeTriangleButton(Control textArea){
    Line line = new Line(0, 5, 10, 5); // StartX, StartY, EndX, EndY
    line.setStrokeWidth(2); // Set the thickness of the line
    Button toggleButton = new Button("",line);
    toggleButton.getStyleClass().add("icon-button");

    toggleButton.setOnAction(event -> {
      clickAction(textArea, line);
    });
    return toggleButton; 
  } 

  private void clickAction(Control textArea, Line icon){
    textArea.setVisible(!textArea.isVisible());
    textArea.setManaged(!textArea.isManaged());
    icon.setRotate(!textArea.isVisible() ? 0 : 90);
  }

  @Override
  public void setParentController(ChildControllers moduleController) {
    this.controller = (IndivProjectViewController) moduleController; 
  }

  
}
