package framework;

import objects.Player;
import window.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class PlayerMovement {
//    private int playerNum;
    private Player p;
    private boolean[] keysPressed; // keysPressed[KeyEvent.VK_...]
    private final int MAX_KEY_NUMBER = 256;
    private boolean playerMovingLeft = false, playerMovingRight = false;
    // camMovingLeft = false, camMovingRight = false, camMovingUp = false, camMovingDown = false;
    private long lastNanoTime = System.nanoTime();

    public void setPlayer(Player p) {
        this.p = p;
    }

    public PlayerMovement(int playerNum) {
//        this.playerNum = playerNum;
        keysPressed = new boolean[MAX_KEY_NUMBER];
    }

    public void setKeyPressed(int key) {
        if (key < MAX_KEY_NUMBER) {

            keysPressed[key] = true;
//            System.out.println(key + "(" + KeyEvent.getKeyText(key) + ")");

            this.ProcessMovement(p, key);

        }
    }

    public void setKeyReleased(int key) {
        if (key < MAX_KEY_NUMBER) {

            keysPressed[key] = false;

            this.ProcessMovement(p, key);
        }
    }

    private void ProcessMovement(Player player, int causeKey) {
        switch (causeKey) {
            case KeyEvent.VK_W:
                //TODO: fix eternal isFalling bug
                if (keysPressed[causeKey] && !player.isJumping() ) {
                    player.setVelY(-8);
                }
                break;

            case KeyEvent.VK_A:
                if (keysPressed[causeKey]) {
                    player.setDesiredVelX(-5);
                    playerMovingLeft = true;
                } else if (playerMovingRight) {
                    playerMovingLeft = false;
                    player.setDesiredVelX(5);
                } else {
                    player.setDesiredVelX(0);
                    playerMovingLeft = false;

                }
                break;

            case KeyEvent.VK_D:
                if (keysPressed[causeKey]) {
                    player.setDesiredVelX(5);
                    playerMovingRight = true;
                } else if (playerMovingLeft) {
                    playerMovingRight = false;
                    player.setDesiredVelX(-5);
                } else {
                    player.setDesiredVelX(0);
                    playerMovingRight = false;
                }
                break;

            case KeyEvent.VK_S:
                if (keysPressed[causeKey]) {
                    player.setCenterCrouch();
                } else if (player.canStand()) {
                    player.setStand();
                }
                break;
        }
    }
}
