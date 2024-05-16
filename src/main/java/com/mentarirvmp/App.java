package com.mentarirvmp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.mentarirvmp.controllers.MainLayoutController;

/**
 * JavaFX App
 */

 //REMEMBER DskipTests!! 
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        loadApp(stage);
    }

        private static void loadApp(Stage stage) throws IOException{
        FXMLLoader loader = getLoaderFXML("mainLayout"); 
        Parent newView = loader.load(); 
        scene = new Scene(newView, 1080, 1080);
        // scene.getStylesheets().add(getCssString("firstStyle.css"));
        stage.setScene(scene);
        stage.setMaximized(true); 
        stage.show();

        MainLayoutController controller = loader.getController(); 
        controller.setStage(stage);
        controller.navigateToHome(); 
    } 


    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = getLoaderFXML("mainLayout"); 
        Parent newView = loader.load(); 
        scene.setRoot(newView);
    }

    private static FXMLLoader getLoaderFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }

}