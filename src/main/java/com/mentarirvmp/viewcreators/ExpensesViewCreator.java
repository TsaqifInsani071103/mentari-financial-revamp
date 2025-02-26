package com.mentarirvmp.viewcreators;

import javafx.application.Platform;

// import com.mentari.statements.Statement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ArrayList;

import javafx.scene.Parent;
// import com.mentari.utils.ExpenseType;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import javafx.scene.control.ContextMenu;

import java.util.ArrayList;
import javafx.scene.layout.StackPane;

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
  private transient TextField expandedTextFieldRef; 
  private transient HBox boxReference; 
  private IndivProjectViewController controller; 
  //either use statement or statementExpenseHandler, we dont know yet. 
  private ExpenseStatementHandler dataHandler; 

  private ArrayList<ExpensesViewCreator> ExpensesToHighlightFocused = new ArrayList<>();

  protected static boolean changedByListener = false; 
  private VBox alreadyMadeView; 

  @Override
  public void setParentController(ChildControllers moduleController) {
    this.controller = (IndivProjectViewController) moduleController; 
  }


  public ExpensesViewCreator(Expenses expense, ExpenseStatementHandler dataHandler){
    this.currentExpense = expense; 
    this.dataHandler = dataHandler; 


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

  private HBox getInnerContainer(VBox overallContainer){
    Control[] descriptionFieldArray = getDescriptionAndButtonArray();
    HBox box = getBox(); 
    box.getChildren().add(0, descriptionFieldArray[1]);

    //temporarytextFieldMake 
    TextField expandedTextField = new TextField(); 
    expandedTextField.setVisible(false); 
    expandedTextField.setManaged(false); 
    expandedTextField.getStyleClass().addAll("yellow-text-field", "black-underline"); 
    addFocusListenerExpanded(expandedTextField);
    addExpandedTextListener(expandedTextField);
    this.expandedTextFieldRef = expandedTextField; 

    overallContainer.getChildren().addAll(box, expandedTextField, descriptionFieldArray[0]);



    return box; 
  } 


  public HBox getBox(){
    HBox box = new HBox(5);
    Label idLabel = new Label(String.valueOf(currentExpense.getParsedId()) + ")"); 
    Control nameTextField = getNameTextField(); 
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);


    box.getChildren().addAll(idLabel,nameTextField,spacer, getValueTextField()); 

    this.boxReference = box;

    
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

      //listeners 

  public void addListener(TextField textField) {
    textField.textProperty().addListener(new ChangeListener<String>(){
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
        // System.out.println(currentExpense.getName() + " CHANGED BY LISTENER: " + changedByListener);
        if(!changedByListener){
          // System.out.println("CHANGED BY expanded");
          textField.getStyleClass().removeAll("red-underline", "black-underline");
          expandedTextFieldRef.getStyleClass().removeAll("red-underline", "black-underline");

          //NEW SHID 
          expandedTextFieldRef.setText(newValue);


          if(dataHandler.ifEquationValidSetExpenseValue(currentExpense, newValue)){
            textField.getStyleClass().add("black-underline");
            expandedTextFieldRef.getStyleClass().add("black-underline");
          }else{
            expandedTextFieldRef.getStyleClass().add("red-underline"); 
            textField.getStyleClass().add("red-underline");
          }
        }
      }
    });
  } 

  public void addExpandedTextListener(TextField expandedTextField){    expandedTextField.textProperty().addListener(new ChangeListener<String>(){
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
        if(!changedByListener && !textFieldReference.isFocused()){
          // System.out.println("CHANGING MAIN TEXT");
          textFieldReference.setText(newValue);

        }
      } 
    });
  } 


    //this is only to toggle value and equation UI not to set the value of the expense itself. 
  private void addFocusListener(TextField textField) {
    textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      toggleChangedByListener();
      if (newValue) { // Gains focus
        // System.out.println("main in focus: " + focusedOnMain);
        textField.setText(this.currentExpense.getEquation());
        expandedTextFieldRef.setText(textField.getText());
        expandedTextFieldRef.setVisible(true); 
        expandedTextFieldRef.setManaged(true); 

        if(!this.ExpensesToHighlightFocused.isEmpty()){
          for (ExpensesViewCreator expensesViewCreator : ExpensesToHighlightFocused) {
            expensesViewCreator.highlightBox(1);
          }
        }

      } else { // Loses focus
        // getAssociatedStatement().updateFormulas();
        // System.out.println("main in focus: " + focusedOnMain);
        if(!expandedTextFieldRef.isFocused()){
          String value = this.currentExpense.getValue(); 
          textField.setText(value);
          expandedTextFieldRef.setVisible(false); 
          expandedTextFieldRef.setManaged(false); 
  
        }

        if(!this.ExpensesToHighlightFocused.isEmpty()){
          for (ExpensesViewCreator expensesViewCreator : ExpensesToHighlightFocused) {
            expensesViewCreator.highlightBox(0);
          }
        }
        
        
      }
      toggleChangedByListener();
    });
    }

    private void addFocusListenerExpanded(TextField expandedTextField){
      expandedTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
        toggleChangedByListener();
        if (newValue) { // When expanded field is focused
          // textFieldReference.setText(currentExpense.getEquation()); // Show equation in main field
          if(!this.ExpensesToHighlightFocused.isEmpty()){
            for (ExpensesViewCreator expensesViewCreator : ExpensesToHighlightFocused) {
              expensesViewCreator.highlightBox(1);
            }
          }
        } else { // When expanded field loses focus
          if(!textFieldReference.isFocused()){
            textFieldReference.setText(this.currentExpense.getValue());
            if(!this.ExpensesToHighlightFocused.isEmpty()){
              for (ExpensesViewCreator expensesViewCreator : ExpensesToHighlightFocused) {
                expensesViewCreator.highlightBox(0);
              }
            }
          }
          
          expandedTextField.setVisible(false); // Hide expanded text 
          expandedTextField.setManaged(false); 
        }
        toggleChangedByListener();
    });
    } 
  

    private void addNameListener(TextField textField){
      textField.textProperty().addListener(new ChangeListener<String>(){
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
          textField.getStyleClass().removeAll("red-underline");
          // if(dataHandler.expenseNameUnique(newValue)){
          //   changeTextOnValue(textField, newValue);
          // }else{
          //   textField.getStyleClass().add("red-underline");
          // }
          changeTextOnValue(textField, newValue);
        }
      });
    } 

    private void addKeyListener(TextField textField){
      textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
        if (event.getCode() == KeyCode.TAB) {
            String spaces = "     "; // 5 spaces
            textField.insertText(textField.getCaretPosition(), spaces);
            event.consume();
        }
      });
    }

    private void addTextAreaListener(TextArea textArea, Expenses expense){
      textArea.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          expense.setDescription(newValue);
        }
      });
  
    } 
  

  //functional methods 

  private void highlightBox(int i){
    boxReference.getStyleClass().removeAll("white-box", "green-box");
    if(i == 1){
      boxReference.getStyleClass().add("green-box");
    }else{
      boxReference.getStyleClass().add("white-box");
      this.textFieldReference.setText(this.currentExpense.getValue()); 
    }
  } 

  public void updateCorrectEquationDisplay(){
    if(textFieldReference != null) {
      textFieldReference.getStyleClass().removeAll("red-underline", "black-underline");
      expandedTextFieldRef.getStyleClass().removeAll("red-underline", "black-underline");
      textFieldReference.getStyleClass().add("black-underline");
      expandedTextFieldRef.getStyleClass().add("black-underline");
      toggleChangedByListener();
      textFieldReference.setText(this.currentExpense.getValue());
      toggleChangedByListener();
    }
  }
  public void updateFalseEquationDisplay(){
    if(textFieldReference != null) {
      textFieldReference.getStyleClass().removeAll("red-underline", "black-underline");
      expandedTextFieldRef.getStyleClass().removeAll("red-underline", "black-underline");
      textFieldReference.getStyleClass().add("red-underline");
      expandedTextFieldRef.getStyleClass().add("red-underline");
      toggleChangedByListener();
      textFieldReference.setText(this.currentExpense.getValue());
      toggleChangedByListener();
    }
  }

  public void populateHighlightMap(ArrayList<ExpensesViewCreator> expenseToHighlightMap){
    if(this.ExpensesToHighlightFocused.size() > 0 && this.ExpensesToHighlightFocused.size() != expenseToHighlightMap.size()){
      this.ExpensesToHighlightFocused.forEach((vc) -> {
        vc.highlightBox(0);
      });
    }
    this.ExpensesToHighlightFocused = expenseToHighlightMap; 
  } 
  

  private void toggleChangedByListener(){
    changedByListener = !changedByListener;
  } 


  
  private void changeTextOnValue(TextField textField, String value){
    this.currentExpense.setName(value); 
    textField.setPrefWidth(getTextWidth(value,textField)); // Set TextField width (add some padding)
  }

  public ContextMenu getContextMenuItems(){
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(addExpense(), deleteExpense());
    return menu; 
  }

  public MenuItem addExpense(){
    MenuItem item = new MenuItem("Add new expense here");
    item.setOnAction(event -> {
      // ModalMenu.addExpenseModal();
      this.dataHandler.addNewDefaultExpense(this.currentExpense);
      this.controller.refreshStatementView();
    });

    return item; 
  } 
  public MenuItem deleteExpense(){
    MenuItem item = new MenuItem("delete this expense");
    item.setOnAction(event -> {
      this.dataHandler.deleteExpense(currentExpense);
      this.controller.refreshStatementView();
    });

    return item; 
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




  
}
