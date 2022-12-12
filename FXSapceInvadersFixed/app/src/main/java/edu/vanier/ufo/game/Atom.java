package edu.vanier.ufo.game;

import edu.vanier.ufo.engine.GameEngine;
import edu.vanier.ufo.engine.Sprite;
import edu.vanier.ufo.helpers.ResourcesManager;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * A spherical looking object (Atom) with a random radius, color, and velocity.
 * When two atoms collide each will fade and become removed from the scene. The
 * method called implode() implements a fade transition effect.
 *
 * @author cdea
 */
public class Atom extends Sprite {
    
    private Group atomFlipBook = new Group();
    
    private Circle hitBounds;
    
    private double Width;
    private double Height;

    /**
     * Constructor will create a optionally create a gradient fill circle shape.
     * This sprite will contain a JavaFX Circle node.
     *
     * @param imagePath the path of the image to be embedded in the node object.
     */
    public Atom(String imagePath) {
        ImageView newAtom = new ImageView();
        Image shipImage = new Image(imagePath);  
        System.out.println(shipImage.getWidth());
        
        newAtom.setImage(shipImage);        
        setNode(atomFlipBook);
        atomFlipBook.getChildren().add(0,newAtom);

        this.collidingNode = newAtom;
        initHitZone();
    }
    
    /**
     * Initialize the collision region for the space ship. It's just an
     * inscribed circle.
     */
    public void initHitZone() {
        // build hit zone
        if (hitBounds == null) {
            double hZoneCenterX = 170/2;
            double hZoneCenterY = 170/2;
            hitBounds = new Circle();
            hitBounds.setCenterX(hZoneCenterX);
            hitBounds.setCenterY(hZoneCenterY);
            hitBounds.setStroke(Color.PINK);
            hitBounds.setFill(Color.RED);
            hitBounds.setRadius(25);
            hitBounds.setOpacity(0.3);
            atomFlipBook.getChildren().add(1,hitBounds);
            setCollisionBounds(hitBounds);
        }
    }

    /**
     * Change the velocity of the current atom particle.
     */
    @Override
    public void update() {
        getAtomFlipBook().setTranslateX(getAtomFlipBook().getTranslateX() + vX);
        getAtomFlipBook().setTranslateY(getAtomFlipBook().getTranslateY() + vY);
    }

    /**
     * Returns a node casted as a JavaFX Circle shape.
     *
     * @return Circle shape representing JavaFX node for convenience.
     */
    public ImageView getImageViewNode() {
        return (ImageView) getNode();
    }

    /**
     * Animate an implosion. Once done remove from the game world
     *
     * @param gameWorld - game world
     */
    public void implode(final GameEngine gameWorld) {
        vX = vY = 0;
        Node currentNode = getNode();
        /* TODO: fix this code to add explosing effect*/
        //Sprite explosion = new Atom(ResourcesManager.ROCKET_FIRE);                
        //gameWorld.getSceneNodes().getChildren().add(explosion.getNode());
        FadeTransition ft = new FadeTransition(Duration.millis(300), currentNode);
        ft.setFromValue(vX);
        ft.setToValue(0);
        ft.setOnFinished((ActionEvent event) -> {
            isDead = true;
            gameWorld.getSceneNodes().getChildren().remove(currentNode);
        });
        ft.play();
    }

    @Override
    public void handleDeath(GameEngine gameWorld) {
        implode(gameWorld);
        super.handleDeath(gameWorld);
    }

    public Circle getHitBounds() {
        return this.hitBounds;
    }

    public void setHitBounds(Circle hitBounds) {
        this.hitBounds = hitBounds;
    }

    public Group getAtomFlipBook() {
        return this.atomFlipBook;
    }

    public void setAtomFlipBook(Group atomFlipBook) {
        this.atomFlipBook = atomFlipBook;
    }
    
    
    
}
