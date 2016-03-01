package window;

import framework.GameObject;

/**
 * Created by aolo2 on 5/23/15.
 */

public class Camera {
    private float x, y;
    private float velX;

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    private float velY;
    private boolean cameraFixedOnPlayer = true;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
        velX = 0;
        velY = 0;
    }

    public void tick(GameObject player) {
        if (isCameraFixedOnPlayer()) {
            float xd, yd;

            xd = this.x + player.getX() - Game.WINDOW_WIDTH / 2;
            yd = this.y + player.getY() - Game.WINDOW_HEIGHT / 2;

            if (xd != 0) {
                x -= (xd / 10);
            }
            if (yd != 0) {
                y -= (yd / 10);
            }
        } else {
            x += velX;
            y += velY;
        }
    }

    public boolean isCameraFixedOnPlayer() {
        return cameraFixedOnPlayer;
    }

    public void setCameraFixedOnPlayer(boolean cameraFixedOnPlayer) {
        this.cameraFixedOnPlayer = cameraFixedOnPlayer;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
