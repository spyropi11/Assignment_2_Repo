/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.ufo.controllers;

import edu.vanier.ufo.engine.GameEngine;
import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.ui.GameWorld;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
        GameEngine.laserPickingConstant = 0;
        GameEngine.levelString = "Level 1, Easy";
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 1");
        gameWorld.setNumberOfEnemies(15);
        gameWorld.setEnemySpeedConstant(0.5);
        gameWorld.setMaxEnemyShipPicker(2);
        gameWorld.setMinEnemyShipPicker(0);
        gameWorld.setNumberOfLasers(1);
        gameWorld.setExplosionSoundPicker("explosionSound1");
        gameWorld.setLaserSoundPicker("laserSound1");
        
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
        GameEngine.laserPickingConstant = 1;
        GameEngine.levelString = "Level 2, Difficult";
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 2");
        gameWorld.setNumberOfEnemies(20);
        gameWorld.setEnemySpeedConstant(2.5);
        gameWorld.setMaxEnemyShipPicker(5);
        gameWorld.setMinEnemyShipPicker(3);
        gameWorld.setNumberOfLasers(2);
        gameWorld.setExplosionSoundPicker("explosionSound2");
        gameWorld.setLaserSoundPicker("laserSound2");

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
        GameEngine.laserPickingConstant = 2;
        GameEngine.levelString = "Level 3, Extreme";
        gameWorld = new GameWorld(ResourcesManager.FRAMES_PER_SECOND, "Level 3");
        gameWorld.setNumberOfEnemies(25);
        gameWorld.setEnemySpeedConstant(5);
        gameWorld.setMaxEnemyShipPicker(8);
        gameWorld.setMinEnemyShipPicker(6);
        gameWorld.setNumberOfLasers(3);
        gameWorld.setExplosionSoundPicker("explosionSound3");
        gameWorld.setLaserSoundPicker("laserSound3");

        // Setup title, scene, stats, controls, and actors.
        gameWorld.initialize(stage);
        // kick off the game loop
        gameWorld.beginGameLoop();
        // display window
        stage.show();
        
    }
    
    public void handleControlsButtonClicked() throws IOException{
        
        ControlsMenuController CM =  new ControlsMenuController(stage);
        
        
    }
    
}
