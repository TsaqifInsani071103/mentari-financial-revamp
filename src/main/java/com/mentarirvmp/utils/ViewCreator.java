package com.mentarirvmp.utils;
import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.controllers.IndivProjectViewController;

import javafx.scene.Parent;

public interface ViewCreator {
  public Parent getView();

  public void setParentController(ChildControllers viewController);
  
}
