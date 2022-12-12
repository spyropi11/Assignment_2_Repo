/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.ufo.ui;

import edu.vanier.ufo.controllers.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class MainMenu extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        // Load FXML file on Netbeans
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-menu.fxml"));
        

        //Instantiate the controller   (Controller is where we do our event handling)
        MainMenuController mainController = new MainMenuController(stage);

        //Set the controller to the loader
        loader.setController(mainController);

        //load the FXML
        Pane root = loader.load();


        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        
        stage.setScene(scene);
        
        stage.setTitle("Main Menu");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        stage.show();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop() {
        System.exit(0);
    }
    
}
