package com.mentarirvmp.utils;
import com.mentarirvmp.controllers.IndivProjectViewController;

import javafx.scene.Parent;

public interface ViewCreator {
  public Parent getView();

  public void setParentController(IndivProjectViewController indivProjectViewController);
  
}
