/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.ufo.controllers;

import edu.vanier.ufo.engine.GameEngine;
import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.ui.GameWorld;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class MainMenuController {
    
    @FXML
    Button startButton;
    
    public Stage stage;
    private Scene scene;
    
    public GameEngine gameWorld;

    public MainMenuController(Stage stage) {
        this.stage = stage;
    }
    
    
    public void handleStartButtonClicked(){
        
        System.out.println("start button clicked");
        stage.close();
        
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "JavaFX Space Invaders");
        // Setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);
        // kick off the game loop
        gameWorld.beginGameLoop();
        // display window
        stage.show();
        
    }
    
}
