import entities.Keyboard;
import entities.MovingEntities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class PlayerController implements KeyListener {

    private MovingEntities movingEntities;
    private HashMap<Integer, Keyboard> controls;

    public PlayerController(MovingEntities obj, HashMap<Integer, Keyboard> controls) {
        this.movingEntities = obj;
        this.controls = controls;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.controls.get(e.getKeyCode()) == Keyboard.up) {
            this.movingEntities.toggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.down) {
            this.movingEntities.toggleDownPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.left) {
            this.movingEntities.toggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.right) {
            this.movingEntities.toggleRightPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.bombAct) {
            this.movingEntities.toggleActionPressed();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.controls.get(e.getKeyCode()) == Keyboard.up) {
            this.movingEntities.unToggleUpPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.down) {
            this.movingEntities.unToggleDownPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.left) {
            this.movingEntities.unToggleLeftPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.right) {
            this.movingEntities.unToggleRightPressed();
        }
        if (this.controls.get(e.getKeyCode()) == Keyboard.bombAct) {
            this.movingEntities.unToggleActionPressed();
        }
    }
}