/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.ufo.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Spyros
 */
public class ControlsMenuController {
    
    @FXML
    Button closeButton;
    
    public Stage owner;
    public Stage stage;

    public ControlsMenuController(Stage owner) throws IOException {
        
        this.owner = owner;
        stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/controls-menu.fxml"));
        loader.setController(this);
        AnchorPane root = loader.load();
        stage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        stage.setTitle("Controls");
        stage.show();
    
        
        
    }
    
    public void handleCloseButtonClicked(){
        
        stage.close();
        
    }
    
    
    
}
