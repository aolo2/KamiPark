package framework;

import objects.Player;
import window.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class PlayerMovement {
    private int playerNum;
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
        this.playerNum = playerNum;
        keysPressed = new boolean[MAX_KEY_NUMBER];
    }

    public void setKeyPressed(int key) {
        if (key < MAX_KEY_NUMBER) {

            keysPressed[key] = true;
//            System.out.println(key + "(" + KeyEvent.getKeyText(key) + ")");

            this.ProcessMovement(p, key);

        } else {
            // Out of range key
        }
    }

    public void setKeyReleased(int key) {
        if (key < MAX_KEY_NUMBER) {

            keysPressed[key] = false;

            this.ProcessMovement(p, key);

        } else {
            // Out of range key
        }
    }

    private void ProcessMovement(Player player, int causeKey) {
        /* First of all, do we need to QUIT (TODO: pause) */
        if (keysPressed[KeyEvent.VK_ESCAPE]) {
            System.out.println('\n' + "Exiting now, thanks for playing.");
            System.exit(130);

        }

        // TODO: proper zoom
        if (keysPressed[KeyEvent.VK_CONTROL]) {
            Game.setZoom(3); // "Zoom in"
        } else {
            Game.setZoom(1); // "Zoom out"
        }


        if (keysPressed[KeyEvent.VK_A] && (causeKey == KeyEvent.VK_A)) {
            if (!player.isJumping()) {
                player.setDesiredVelX(-5);
                playerMovingLeft = true;
            }
        } else if (causeKey == KeyEvent.VK_A) {
            if (!playerMovingRight && playerMovingLeft) {
                player.setDesiredVelX(0);
            } else if (playerMovingRight) {
                player.setDesiredVelX(5);
            }
            playerMovingLeft = false;
        }


        if (keysPressed[KeyEvent.VK_D] && (causeKey == KeyEvent.VK_D)) { //if we are here because of the 'D', not because of the if's order of appearance
            if (!player.isJumping()) {
                player.setDesiredVelX(5);
                playerMovingRight = true;
            }
        } else if (causeKey == KeyEvent.VK_D) {
            if (!playerMovingLeft && playerMovingRight) {
                player.setDesiredVelX(0);
            } else if (playerMovingLeft) {
                player.setDesiredVelX(-5);
            }
            playerMovingRight = false;
        }

        if (keysPressed[KeyEvent.VK_W] || keysPressed[KeyEvent.VK_SPACE]) {
            if (!player.isJumping()) {
                if (!player.isCrouching()) {
                    player.setJumping(true);
                    player.setVelY(-7);
                } else {
                    if (player.canStand()) {
                        player.setStand();
                        player.setJumping(true);
                        player.setVelY(-10);
                    }
                }
            }
        }

        if (keysPressed[KeyEvent.VK_S]) {
            if (!player.isCrouching()) {
                player.setCenterCrouch();
            }
        } else {
            if (player.isCrouching() && player.canStand()) {
                player.setStand();
            }
        }
    }
}
