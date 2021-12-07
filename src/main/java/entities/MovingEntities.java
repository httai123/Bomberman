package entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class MovingEntities extends Entity {

    public boolean moveUp = false;
    public boolean moveDown = false;
    public boolean moveLeft = false;
    public boolean moveRight = false;
    public boolean bombAct = false;

    public MovingEntities(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
    }

    public void toggleUpPressed() {
        this.moveUp = true;
    }
    public void toggleDownPressed() {
        this.moveDown = true;
    }
    public void toggleLeftPressed() {
        this.moveLeft = true;
    }
    public void toggleRightPressed() {
        this.moveRight = true;
    }
    public void toggleActionPressed() {
        this.bombAct = true;
    }

    public void unToggleUpPressed() {
        this.moveUp = false;
    }
    public void unToggleDownPressed() {
        this.moveDown = false;
    }
    public void unToggleLeftPressed() {
        this.moveLeft = false;
    }
    public void unToggleRightPressed() {
        this.moveRight = false;
    }
    public void unToggleActionPressed() {
        this.bombAct = false;
    }

    public void autoMove() {
        int keyMove = (int) (Math.random() * 3);
        if (keyMove == 0) {
            this.toggleDownPressed();
        }
        if (keyMove == 1) {
            this.toggleUpPressed();
        }
        if (keyMove == 2) {
            this.toggleLeftPressed();
        }
        if (keyMove == 3) {
            this.toggleRightPressed();
        }
    }

    public void unMove() {
        this.unToggleUpPressed();
        this.unToggleDownPressed();
        this.unToggleRightPressed();
        this.unToggleLeftPressed();
    }

}