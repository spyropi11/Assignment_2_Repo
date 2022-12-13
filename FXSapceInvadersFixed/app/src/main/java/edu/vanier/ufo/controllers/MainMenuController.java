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
    Button levelOneButton;
    
    @FXML
    Button levelTwoButton;
    
    @FXML
    Button levelThreeButton;
    
    @FXML
    Button controlsButton;
    
    public Stage stage;
    private Scene scene;
    
    public GameEngine gameWorld;

    public MainMenuController(Stage stage) {
        this.stage = stage;
    }
    
    
    public void handleLevelOneButtonClicked(){
        
        stage.close();
        GameEngine.shipPicker = 0;
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 1");
        gameWorld.setNumberOfEnemies(15);
        gameWorld.setEnemySpeedConstant(0.5);
        gameWorld.setMaxEnemyShipPicker(2);
        gameWorld.setMinEnemyShipPicker(0);
        
        // Setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);
        // kick off the game loop
        gameWorld.beginGameLoop();
        // display window
        stage.show();
        
    }
    
    public void handleLevelTwoButtonClicked(){
        
        stage.close();
        GameEngine.shipPicker = 1;
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 2");
        gameWorld.setNumberOfEnemies(20);
        gameWorld.setEnemySpeedConstant(2.5);
        gameWorld.setMaxEnemyShipPicker(5);
        gameWorld.setMinEnemyShipPicker(3);
        

        // Setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);
        // kick off the game loop
        gameWorld.beginGameLoop();
        // display window
        stage.show();
        
    }
    
    public void handleLevelThreeButtonClicked(){
        
        stage.close();
        GameEngine.shipPicker = 2;
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 3");
        gameWorld.setNumberOfEnemies(25);
        gameWorld.setEnemySpeedConstant(5);
        gameWorld.setMaxEnemyShipPicker(8);
        gameWorld.setMinEnemyShipPicker(6);
        

        // Setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);
        // kick off the game loop
        gameWorld.beginGameLoop();
        // display window
        stage.show();
        
    }
    
}
