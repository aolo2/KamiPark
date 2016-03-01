package framework;

import window.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    PlayerMovement movementClass;

    public KeyInput(Handler handler) {
        this.movementClass = new PlayerMovement(0);
        movementClass.setPlayer(handler.getPlayerInstance(0));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.movementClass.setKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.movementClass.setKeyReleased(e.getKeyCode());
    }
}
