package edu.vanier.ufo.ui;

import edu.vanier.ufo.controllers.MainMenuController;
import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.engine.*;
import edu.vanier.ufo.game.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * This is a simple game world simulating a bunch of spheres looking like atomic
 * particles colliding with each other. When the game loop begins the user will
 * notice random spheres (atomic particles) floating and colliding. The user
 * will navigate his/her ship by right clicking the mouse to thrust forward and
 * left click to fire weapon to atoms.
 *
 * @author cdea
 */
public class GameWorld extends GameEngine {

    // mouse pt label
    Label mousePtLabel = new Label();
    // mouse press pt label
    Label mousePressPtLabel = new Label();
    Ship spaceShip = new Ship();
    
    
    public static double WIDTH = 1230;
    public static double HEIGHT = 800;

    public GameWorld(int fps, String title) {
        super(fps, title);
    }

    /**
     * Initialize the game world by adding sprite objects.
     *
     * @param primaryStage The game window or primary stage.
     */
    @Override
    public void initialize(final Stage primaryStage) {
        
        stage = primaryStage;
        // Sets the window title
        primaryStage.setTitle(getWindowTitle());
        //primaryStage.setFullScreen(true);

        // Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));

        // Change the background of the main scene.
        getGameSurface().setFill(Color.BLACK);

        primaryStage.setScene(getGameSurface());

        // Setup Game input
        setupInput(primaryStage);

        // Create many spheres
        generateManySpheres(numberOfEnemies);

        getSpriteManager().addSprites(spaceShip);
        getSceneNodes().getChildren().add(0, spaceShip.getNode());
        // mouse point
        VBox stats = new VBox();

        HBox row1 = new HBox();
        livesLabel.setTextFill(Color.WHITE);
        row1.getChildren().add(livesLabel);
        HBox row2 = new HBox();
        levelLabel.setTextFill(Color.WHITE);
        row2.getChildren().add(levelLabel);
        HBox row3 = new HBox();
        scoreLabel.setTextFill(Color.WHITE);
        row3.getChildren().add(scoreLabel);
        HBox row4 = new HBox();
        shieldHealthLabel.setTextFill(Color.WHITE);
        row4.getChildren().add(shieldHealthLabel);
        HBox row5 = new HBox();
        row5.getChildren().add(gameOverButton);
        HBox row6 = new HBox();
        row6.getChildren().add(winButton);
        stats.getChildren().add(row1);
        stats.getChildren().add(row2);
        stats.getChildren().add(row3);
        stats.getChildren().add(row4);
        stats.getChildren().add(row5);
        stats.getChildren().add(row6);
        getSceneNodes().getChildren().add(0, stats);


        // load sound files
        getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.LASER_SOUND_PATHS[0]));
    }

    /**
     * Sets up the mouse input.
     *
     * @param primaryStage The primary stage (app window).
     */
    private void setupInput(Stage primaryStage) {

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            spaceShip.moveShip(event.getCode());
            
            
        });
        
        
        
        EventHandler fireOrMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            
            if (event.getButton() == MouseButton.PRIMARY) {
                
                // Aim
                spaceShip.plotCourse(event.getX(), event.getY(), false);

                
                
                // fire
                Missile missiles[] = spaceShip.fire();
                
                for (int i = 0; i < numberOfLasers; i++){
                    
                    
                    getSpriteManager().addSprites(missiles[i]);
//                    getSpriteManager().addSprites(missiles[1]);
//                    getSpriteManager().addSprites(missiles[2]);

                    

                    getSceneNodes().getChildren().add(0, missiles[i].getNode());
//                    getSceneNodes().getChildren().add(0, missiles[1].getNode());
//                    getSceneNodes().getChildren().add(0, missiles[2].getNode());
                    
                }
                
            // play sound
            getSoundManager().playSound(laserSoundPicker);    
                
            } else if (event.getButton() == MouseButton.SECONDARY) {
                // determine when all atoms are not on the game surface. Ship should be one sprite left.

                // stop ship from moving forward
                spaceShip.applyTheBrakes(event.getX(), event.getY());
                // move forward and rotate ship
                
                
                //CHANGE
                spaceShip.plotCourse(event.getX(), event.getY(), true);
                
                
            }
            
        };

        // Initialize input
        primaryStage.getScene().setOnMousePressed(fireOrMove);

        // set up stats
        EventHandler changeWeapons = (EventHandler<KeyEvent>) (KeyEvent event) -> {
            if (KeyCode.SPACE == event.getCode()) {
                spaceShip.shieldToggle();
                return;
            }
            spaceShip.changeWeapon(event.getCode());
        };
        primaryStage.getScene().setOnKeyPressed(changeWeapons);

        // set up stats
        EventHandler showMouseMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            mousePtLabel.setText("Mouse PT = (" + event.getX() + ", " + event.getY() + ")");
        };
        
        primaryStage.getScene().setOnMouseMoved(showMouseMove);
    }

    /**
     * Make some more space spheres (Atomic particles)
     *
     * @param numSpheres The number of random sized, color, and velocity atoms
     * to generate.
     */
    private void generateManySpheres(int numSpheres) {
        Random rnd = new Random();
        Scene gameSurface = getGameSurface();
        for (int i = 0; i < numSpheres; i++) {
            
            
            int enemyShipPicker = rnd.nextInt(maxEnemyShipPicker - minEnemyShipPicker + 1) + minEnemyShipPicker;
            String enemyShipPath = ResourcesManager.ENEMY_SHIP_PATHS[enemyShipPicker];
            
            Image enemyShipImage = (new Image(enemyShipPath));

            ImageView atomImage = new ImageView();
            atomImage.setImage(enemyShipImage);
            Atom atom = new Atom(enemyShipPath);
            
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            // Randomize the location of each newly generated atom.
            atom.setVelocityX((rnd.nextInt(2) + rnd.nextDouble()*enemySpeedConstant) * (rnd.nextBoolean() ? 1 : -1));
            atom.setVelocityY((rnd.nextInt(2) + rnd.nextDouble()*enemySpeedConstant) * (rnd.nextBoolean() ? 1 : -1));

            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth() - 100);

            if (newX > (gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2))) {
                newX = gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2);
            }

            double newY = rnd.nextInt((int) (gameSurface.getHeight() - 300));
            if (newY > (gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2))) {
                newY = gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2);
            }

            
            atom.getFlipBook().setTranslateX(newX);
            atom.getFlipBook().setTranslateY(newY);
            atomImage.setVisible(true);
            atomImage.setId("invader");
            atomImage.setCache(true);
            atomImage.setCacheHint(CacheHint.SPEED);
            atomImage.setManaged(false);

            // add to actors in play (sprite objects)
            getSpriteManager().addSprites(atom);

            // add sprite's 
            getSceneNodes().getChildren().add(atom.getNode());
        }
    }

    /**
     * Each sprite will update it's velocity and bounce off wall borders.
     *
     * @param sprite - An atomic particle (a sphere).
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        
        
//        if(GameEngine.gameOver == true){
//            stage.close();
//            
//            GameEngine.gameOver = false;
//            System.out.println(gameOver);
//            
//        }
        // advance object
        sprite.update();
        if (sprite instanceof Missile) {
            removeMissiles((Missile) sprite);
        } else {
            bounceOffWalls(sprite);
        }
        
    }

    /**
     * Change the direction of the moving object when it encounters the walls.
     *
     * @param sprite The sprite to update based on the wall boundaries. TODO The
     * ship has got issues.
     */
    private void bounceOffWalls(Sprite sprite) {
        // bounce off the walls when outside of boundaries

        Node displayNode;
        if (sprite instanceof Ship) {
            displayNode = sprite.getNode();//((Ship)sprite).getCurrentShipImage();
        } else {
            displayNode = sprite.getNode();
        }
        // Get the group node's X and Y but use the ImageView to obtain the width.
        
        if (sprite.getNode().getTranslateX() > (getGameSurface().getWidth() - displayNode.getBoundsInParent().getWidth())
                || displayNode.getTranslateX() < 0) {

            // bounce the opposite direction
            sprite.setVelocityX(sprite.getVelocityX() * -1);

            
        }
        // Get the group node's X and Y but use the ImageView to obtain the height.
        if (sprite.getNode().getTranslateY() > getGameSurface().getHeight() - displayNode.getBoundsInParent().getHeight()
                || sprite.getNode().getTranslateY() < 0) {
            sprite.setVelocityY(sprite.getVelocityY() * -1);
        }
    }

    /**
     * Remove missiles when they reach the wall boundaries.
     *
     * @param missile The missile to remove based on the wall boundaries.
     */
    private void removeMissiles(Missile missile) {
        // bounce off the walls when outside of boundaries
        if (missile.getNode().getTranslateX() > (getGameSurface().getWidth()
                - missile.getNode().getBoundsInParent().getWidth())
                || missile.getNode().getTranslateX() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());

        }
        if (missile.getNode().getTranslateY() > getGameSurface().getHeight()
                - missile.getNode().getBoundsInParent().getHeight()
                || missile.getNode().getTranslateY() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());
        }
    }

    /**
     * How to handle the collision of two sprite objects. Stops the particle by
     * zeroing out the velocity if a collision occurred. /** How to handle the
     * collision of two sprite objects. Stops the particle by
     *
     *
     * @param spriteA Sprite from the first list.
     * @param spriteB Sprite from the second list.
     * @param circleA spriteA's hitbox.
     * @param circleB spriteB's hitbox.
     * 
     * @return boolean returns a true if the two sprites have collided otherwise
     * false.
     */
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB, Circle circleA, Circle circleB) {
        
        if((distanceBetweenTwoPoints(spriteA,spriteB)) <= (circleA.getRadius() + circleB.getRadius())){
            
            
            //System.out.println(circleA.getRadius()+circleB.getRadius());
            return true;
        
        }
        
        
        
        return false;
    }
    
    public double distanceBetweenTwoPoints(Sprite spriteA,Sprite spriteB){
        
        double distance = 
                Math.sqrt(Math.pow((spriteA.flipBook.getTranslateX() - spriteB.flipBook.getTranslateX()),2)
                + Math.pow((spriteA.flipBook.getTranslateY() - spriteB.flipBook.getTranslateY()),2));
        
        //System.out.println(distance);
        return distance;
    }
    
  
    
}
