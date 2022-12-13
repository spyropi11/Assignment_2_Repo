package edu.vanier.ufo.helpers;

import java.util.HashMap;
import javafx.scene.image.Image;



/**
 * A resource manager providing useful resource definitions used in this game.
 *
 * @author Sleiman
 */
public class ResourcesManager {
    


    /**
     * Used to control the speed of the game.
     */
    public static final int FRAMES_PER_SECOND = 60;
    
    private static final String RESOURCES_FOLDER = "";
    private static final String IMAGES_FOLDER = RESOURCES_FOLDER + "images/";
    private static final String SOUNDS_FOLDER = RESOURCES_FOLDER + "sounds/";
    // Ship images.
    public static final String SPACE_STAR_SHIP_L1 = IMAGES_FOLDER + "levelOneShip.png";
    public static final String SPACE_STAR_SHIP_L2 = IMAGES_FOLDER + "levelTwoShip.png";
    public static final String SPACE_STAR_SHIP_L3 = IMAGES_FOLDER + "levelThreeShip.png";
    
    // Rocket images
    public static final String LASER_L11 = IMAGES_FOLDER + "LevelOneM1.png";
    public static final String LASER_L12 = IMAGES_FOLDER + "LevelOneM2.png";
    
    public static final String LASER_L21 = IMAGES_FOLDER + "LevelTwoM1.png";
    public static final String LASER_L22 = IMAGES_FOLDER + "LevelTwoM2.png";
    
    public static final String LASER_L31 = IMAGES_FOLDER + "LevelThreeM1.png";
    public static final String LASER_L32 = IMAGES_FOLDER + "LevelThreeM2.png";

    // Invader sprites.
    public static final String ENEMY_SHIP_L11 = IMAGES_FOLDER + "levelOneES1.png";
    public static final String ENEMY_SHIP_L12 = IMAGES_FOLDER + "levelOneES2.png";
    public static final String ENEMY_SHIP_L13 = IMAGES_FOLDER + "levelOneES3.png";
    
    public static final String ENEMY_SHIP_L21 = IMAGES_FOLDER + "LevelTwoES1.png";
    public static final String ENEMY_SHIP_L22 = IMAGES_FOLDER + "LevelTwoES2.png";
    public static final String ENEMY_SHIP_L23 = IMAGES_FOLDER + "LevelTwoES3.png";
    
    public static final String ENEMY_SHIP_L31 = IMAGES_FOLDER + "LevelThreeES1.png";
    public static final String ENEMY_SHIP_L32 = IMAGES_FOLDER + "LevelThreeES2.png";
    public static final String ENEMY_SHIP_L33 = IMAGES_FOLDER + "LevelThreeES3.png";

    // Sound effect files
    public static final String SOUND_LASER = SOUNDS_FOLDER + "laser_2.mp3";    
    
    //Array holding all paths for enemy ships
    public static final String[] ENEMY_SHIP_PATHS = {ENEMY_SHIP_L11,ENEMY_SHIP_L12,ENEMY_SHIP_L13,
                                                    ENEMY_SHIP_L21,ENEMY_SHIP_L22,ENEMY_SHIP_L23,
                                                    ENEMY_SHIP_L31,ENEMY_SHIP_L32,ENEMY_SHIP_L33};
    
    
    //Array holding all paths for ships
    public static final String[] SHIP_PATHS = {SPACE_STAR_SHIP_L1,
                                                SPACE_STAR_SHIP_L2,
                                                SPACE_STAR_SHIP_L3};
    
    //Array holding all paths for lasers
    public static final String[] LASER_PATHS = {LASER_L11,LASER_L12,
                                                LASER_L21,LASER_L22,
                                                LASER_L31,LASER_L32};
    

}
