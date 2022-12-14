package edu.vanier.ufo.engine;

import edu.vanier.ufo.controllers.MainMenuController;
import edu.vanier.ufo.game.Atom;
import edu.vanier.ufo.game.Missile;
import edu.vanier.ufo.game.Ship;
import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.ui.MainMenu;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.util.Duration;


//HAS BEEN LOOKED AT

/**
 * This application demonstrates a JavaFX Game Loop. Shown below are the methods
 * which comprise of the fundamentals to a simple game loop in JavaFX:
 * <pre>
 *  <b>initialize()</b> - Initialize the game world.
 *  <b>beginGameLoop()</b> - Creates a JavaFX Timeline object containing the game life cycle.
 *  <b>updateSprites()</b> - Updates the sprite objects each period (per frame)
 *  <b>checkCollisions()</b> - Method will determine objects that collide with each other.
 *  <b>cleanupSprites()</b> - Any sprite objects needing to be removed from play.
 * </pre>
 *
 * @author cdea
 */
public abstract class GameEngine {

    /**
     * The JavaFX Scene as the game surface
     */
    private Scene gameSurface;
    /**
     * All nodes to be displayed in the game window.
     */
    private Group sceneNodes;
    /**
     * The game loop using JavaFX's <code>Timeline</code> API.
     */
    private static Timeline gameLoop;

    /**
     * Number of frames per second.
     */
    private final int framesPerSecond;
    
    /**
     * Default number of enemies created
     */
    public int numberOfEnemies = 15;
    
    /**
     * Default enemy speed constant that multiplies the random speed
     */
    public double enemySpeedConstant = 1;

    /**
     * Max enemyShipPicker
     */
    public int maxEnemyShipPicker = 2;
    
    /**
     * Min enemyShipPicker
     */
    public int minEnemyShipPicker = 0;
    
    /**
     * Picks the ship for the level
     */
    public static int shipPicker = 0;
    
    /**
     * is a constant that allows for each level to have its own set of lasers
     */
    public static int laserPickingConstant = 0;
    
    /**
     * The name of the current level
     */
    public static String levelString;
    
    /**
     * how many enemy ships have been killed
     */
    public int score = 0;
    
    /**
     * number of lasers for the current level
     */
    public int numberOfLasers = 1;
    
    /**
     * Explosion sound picker
     */
    public String explosionSoundPicker = "explosionSound1";
    
    /**
     * laser sound picker
     */
    public String laserSoundPicker = "laserSound1";
    
    public Stage stage;
    
    public Button gameOverButton = new Button("Retry");
    public Button winButton = new Button("You Won! Return To Main Menu?");
    public Label livesLabel = new Label("Lives: " + (3));
    public Label levelLabel = new Label(levelString);
    public Label scoreLabel = new Label("Score: " + score);
    public Label shieldHealthLabel = new Label("Shield Health: " + 100);
    
    /**
     * Title in the application window.
     */
    private final String windowTitle;

    /**
     * The sprite manager.
     */
    private final SpriteManager spriteManager;

    private final SoundManager soundManager;
    
    public Ship ship;

    /**
     * Constructor that is called by the derived class. This will set the frames
     * per second, title, and setup the game loop.
     *
     * @param fps - Frames per second.
     * @param title - Title of the application window.
     */
    public GameEngine(final int fps, final String title) {
        
        gameOverButton.setVisible(false);
        gameOverButton.setDisable(true);
        
        winButton.setVisible(false);
        winButton.setDisable(true);
        
        gameOverButton.setOnAction((event) -> {

            try {
                
                stage.close();
                
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
            } catch (IOException ex) {
                
            }
            
        });
        
        winButton.setOnAction((event) -> {

            try {
                
                stage.close();
                
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
            } catch (IOException ex) {
                
            }
            
        });
        
        
        framesPerSecond = fps;
        windowTitle = title;
        spriteManager = new SpriteManager();
        soundManager = new SoundManager(3);
        
        getSoundManager().loadSoundEffects("explosionSound1", getClass().getClassLoader().getResource(ResourcesManager.EXPLOSION_SOUND_PATHS[0]));
        getSoundManager().loadSoundEffects("explosionSound2", getClass().getClassLoader().getResource(ResourcesManager.EXPLOSION_SOUND_PATHS[1]));
        getSoundManager().loadSoundEffects("explosionSound3", getClass().getClassLoader().getResource(ResourcesManager.EXPLOSION_SOUND_PATHS[2]));

        getSoundManager().loadSoundEffects("laserSound1", getClass().getClassLoader().getResource(ResourcesManager.LASER_SOUND_PATHS[0]));
        getSoundManager().loadSoundEffects("laserSound2", getClass().getClassLoader().getResource(ResourcesManager.LASER_SOUND_PATHS[1]));
        getSoundManager().loadSoundEffects("laserSound3", getClass().getClassLoader().getResource(ResourcesManager.LASER_SOUND_PATHS[2]));
        
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }

    /**
     * Builds and sets the game loop ready to be started.
     */
    protected final void buildAndSetGameLoop() {

        final Duration frameDuration = Duration.millis(1000 / (float) getFramesPerSecond());
        EventHandler<ActionEvent> onFinished = (event) -> {
            // update actors
            updateSprites();
            // check for collision.
            checkCollisions();
            
            // removed dead sprites.
            cleanupSprites();
            
        };
        final KeyFrame gameFrame = new KeyFrame(frameDuration, onFinished);
        // sets the game world's game loop (Timeline)
        gameLoop = new Timeline(gameFrame);
        gameLoop.setCycleCount(Animation.INDEFINITE);
        setGameLoop(gameLoop);
    }

    /**
     * Initialize the game world by update the JavaFX Stage.
     *
     * @param primaryStage The main window containing the JavaFX Scene.
     */
    public abstract void initialize(final Stage primaryStage);

    /**
     * Kicks off (plays) the Timeline objects containing one key frame that
     * simply runs indefinitely with each frame invoking a method to update
     * sprite objects, check for collisions, and cleanup sprite objects.
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }

    /**
     * Updates each game sprite in the game world. This method will loop through
     * each sprite and passing it to the handleUpdate() method. The derived
     * class should override handleUpdate() method.
     */
    protected void updateSprites() {
        for (Sprite sprite : spriteManager.getAllSprites()) {
            handleUpdate(sprite);
        }
    }

    /**
     * Updates the sprite object's information to position on the game surface.
     *
     * @param sprite - The sprite to update.
     */
    
    //SUPPOSED TO BE ABSTRACT
    protected void handleUpdate(Sprite sprite) {
    }

    /**
     * Checks each game sprite in the game world to determine a collision
     * occurred. The method will loop through each sprite and passing it to the
     * handleCollision() method. The derived class should override
     * handleCollision() method.
     */
    protected void checkCollisions(){
        
        
        //int counter = 0;
        //FIXME: handle collision with the spaceship.
        // check other sprite's collisions
        spriteManager.resetCollisionsToCheck();
        // check each sprite against other sprite objects.
        for (Sprite spriteA : spriteManager.getCollisionsToCheck()) {
            for (Sprite spriteB : spriteManager.getAllSprites()) {
                
                //handleCollsion returns true if spriteA is in contact with spriteB which 
                //is defined in the class that overrides the handleUpdate method

                if(!(spriteA == spriteB)){
                    if (handleCollision(spriteA, spriteB, (Circle)spriteA.getFlipBook().getChildren().get(0),(Circle)spriteB.getFlipBook().getChildren().get(0))) {
                        
                        if(spriteA instanceof Ship && spriteB instanceof Atom){
                            if(!(spriteB instanceof Missile)){
                                ship = (Ship)spriteA;
                                Atom enemyShip = (Atom)spriteB;
                                
                                
                                Image explosion = new Image(ResourcesManager.EXPLOSION_GIF);
                                ImageView imageView = new ImageView(explosion);
                                imageView.setTranslateX(spriteB.getFlipBook().getTranslateX() + explosion.getWidth()/2);
                                imageView.setTranslateY(spriteB.getFlipBook().getTranslateY() + explosion.getHeight()/2);
                                
                                
                                getSoundManager().playSound(explosionSoundPicker);
                                
                                sceneNodes.getChildren().add(0,imageView);
                                
                                if(ship.shieldOn && ship.shieldHealth >  0){
                                    ship.shieldHealth = ship.shieldHealth - 25;
                                    shieldHealthLabel.setText("Shield Health: " + ship.shieldHealth);
                                    if(ship.shieldHealth <= 0){
                                        
                                        ship.flipBook.getChildren().remove(ship.shield);
                                        
                                    }
                                }else{
                                    
                                    ship.timesHitByEnemy++;
                                    
                                }

                                
                                livesLabel.setText("Current Lives: " + (3 - ship.timesHitByEnemy));
                                
                                score++;
                                scoreLabel.setText("Score: " + score);
                                
                                spriteManager.addSpritesToBeRemoved(enemyShip);
                                sceneNodes.getChildren().remove(enemyShip.getNode());
                                
                                
                                
                                
                                int enemyCounter = -1;
                                for(Sprite sprite : spriteManager.getAllSprites()){
                                    
                                    if(sprite instanceof Atom){
                                        if(!(sprite instanceof Missile)){
                                        
                                            enemyCounter++;
                                            
                                        }
                                        
                                    }
                                    
                                }
                                
                                if (enemyCounter == 0){
                                    
                                    for (Sprite sprite : spriteManager.getAllSprites() ){
                    
                                        spriteManager.addSpritesToBeRemoved(sprite);

                                    }

                                    sceneNodes.getChildren().removeAll(spriteManager.getAllSprites());

                                    gameLoop.stop();
                                    
                                    //This means the player has won
                                    winButton.setVisible(true);
                                    winButton.setDisable(false);
                                    
                                }
                                
                            }
                            
                        }
                       
                        if(spriteA instanceof Missile && spriteB instanceof Atom){
                            if(!(spriteB instanceof Missile)){
                                
                                Missile missile = (Missile)spriteA;
                                Atom enemyShip = (Atom)spriteB;
                                
                                Image explosion = new Image(ResourcesManager.EXPLOSION_GIF);
                                ImageView imageView = new ImageView(explosion);
                                imageView.setTranslateX(spriteB.getFlipBook().getTranslateX() + explosion.getWidth()/2);
                                imageView.setTranslateY(spriteB.getFlipBook().getTranslateY() + explosion.getHeight()/2);
                                
                                sceneNodes.getChildren().add(0,imageView);
                                

                                getSoundManager().playSound(explosionSoundPicker);
                                
                                score++;
                                scoreLabel.setText("Score: " + score);
                                
                                spriteManager.addSpritesToBeRemoved(enemyShip);
                                sceneNodes.getChildren().remove(enemyShip.getNode());
                                spriteManager.addSpritesToBeRemoved(missile);
                                sceneNodes.getChildren().remove(missile.getNode());
                                
                                int enemyCounter = -1;
                                for(Sprite sprite : spriteManager.getAllSprites()){
                                    
                                    if(sprite instanceof Atom){
                                        if(!(sprite instanceof Missile)){
                                        
                                            enemyCounter++;
                                            
                                        }
                                        
                                    }
                                    
                                }
                                
                                if (enemyCounter == 0){
                                    
                                    for (Sprite sprite : spriteManager.getAllSprites() ){
                    
                                        spriteManager.addSpritesToBeRemoved(sprite);

                                    }

                                    sceneNodes.getChildren().removeAll(spriteManager.getAllSprites());

                                    gameLoop.stop();
                                    
                                    //This means the player has won
                                    winButton.setVisible(true);
                                    winButton.setDisable(false);
                                    
                                }
                                
                                
                            }
                            
                        }
                        
                        break;
                    }
                }
                
                
            }
        }
        
        if(ship != null){
            if(ship.timesHitByEnemy >= 3){
                
                spriteManager.addSpritesToBeRemoved(ship);
                sceneNodes.getChildren().remove(ship.getNode());
                
                for (Sprite sprite : spriteManager.getAllSprites() ){
                    
                    spriteManager.addSpritesToBeRemoved(sprite);
                    
                }
                
                sceneNodes.getChildren().removeAll(spriteManager.getAllSprites());
                
                gameLoop.stop();
                
                gameOverButton.setVisible(true);
                gameOverButton.setDisable(false);
                
                
            }
            
        }
        
        

    }

    /**
     * When two objects collide this method can handle the passed in sprite
     * objects. By default it returns false, meaning the objects do not collide.
     *
     * @param spriteA - called from checkCollision() method to be compared.
     * @param spriteB - called from checkCollision() method to be compared.
     * @return boolean True if the objects collided, otherwise false.
     */
    
    //NEEDS TO BE OVERRIDDEN
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB,Circle circleA, Circle circleB) {
        return false;
    }

    /**
     * Sprites to be cleaned up.
     */
    protected void cleanupSprites() {
        spriteManager.cleanupSprites();
    }

    /**
     * Returns the frames per second.
     *
     * @return int The frames per second.
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Returns the game's window title.
     *
     * @return String The game's window title.
     */
    public String getWindowTitle() {
        return windowTitle;
    }

    /**
     * The game loop (Timeline) which is used to update, check collisions, and
     * cleanup sprite objects at every interval (fps).
     *
     * @return Timeline An animation running indefinitely representing the game
     * loop.
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }

    /**
     * The sets the current game loop for this game world.
     *
     * @param gameLoop Timeline object of an animation running indefinitely
     * representing the game loop.
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameEngine.gameLoop = gameLoop;
    }

    /**
     * Returns the sprite manager containing the sprite objects to manipulate in
     * the game.
     *
     * @return SpriteManager The sprite manager.
     */
    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    /**
     * Returns the JavaFX Scene. This is called the game surface to allow the
     * developer to add JavaFX Node objects onto the Scene.
     *
     * @return Scene The JavaFX scene graph.
     */
    public Scene getGameSurface() {
        return gameSurface;
    }

    /**
     * Sets the JavaFX Scene. This is called the game surface to allow the
     * developer to add JavaFX Node objects onto the Scene.
     *
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }

    /**
     * All JavaFX nodes which are rendered onto the game surface(Scene) is a
     * JavaFX Group object.
     *
     * @return Group The root containing many child nodes to be displayed into
     * the Scene area.
     */
    public Group getSceneNodes() {
        return sceneNodes;
    }

    /**
     * Sets the JavaFX Group that will hold all JavaFX nodes which are rendered
     * onto the game surface(Scene) is a JavaFX Group object.
     *
     * @param sceneNodes The root container having many children nodes to be
     * displayed into the Scene area.
     */
    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }

    protected SoundManager getSoundManager() {
        return soundManager;
    }

    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    public void setNumberOfEnemies(int numberOfEnemies) {
        this.numberOfEnemies = numberOfEnemies;
    }

    public double getEnemySpeedConstant() {
        return this.enemySpeedConstant;
    }

    public void setEnemySpeedConstant(double enemySpeedConstant) {
        this.enemySpeedConstant = enemySpeedConstant;
    }

    public int getMaxEnemyShipPicker() {
        return this.maxEnemyShipPicker;
    }

    public void setMaxEnemyShipPicker(int maxEnemyShipPicker) {
        this.maxEnemyShipPicker = maxEnemyShipPicker;
    }

    public int getMinEnemyShipPicker() {
        return this.minEnemyShipPicker;
    }

    public void setMinEnemyShipPicker(int minEnemyShipPicker) {
        this.minEnemyShipPicker = minEnemyShipPicker;
    }

    public static int getShipPicker() {
        return shipPicker;
    }

    public static void setShipPicker(int shipPicker) {
        GameEngine.shipPicker = shipPicker;
    }

    public int getNumberOfLasers() {
        return this.numberOfLasers;
    }

    public void setNumberOfLasers(int numberOfLasers) {
        this.numberOfLasers = numberOfLasers;
    }

    public String getExplosionSoundPicker() {
        return explosionSoundPicker;
    }

    public void setExplosionSoundPicker(String explosionSoundPicker) {
        this.explosionSoundPicker = explosionSoundPicker;
    }

    public String getLaserSoundPicker() {
        return this.laserSoundPicker;
    }

    public void setLaserSoundPicker(String laserSoundPicker) {
        this.laserSoundPicker = laserSoundPicker;
    }
    
    


    
    

    
    
    
    

    /**
     * Stop threads and stop media from playing.
     */
    public void shutdown() {
        // Stop the game's animation.
        getGameLoop().stop();
        getSoundManager().shutdown();
    }
    
}
