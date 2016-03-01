package objects;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Camera;
import window.Game;
import window.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by aolo2 on 5/22/15.
 */

public class Player extends GameObject {

    private float desiredVelX;
    private double xGravity = 0.2; // player's ability to accelerate (base value)
    private double xGravityBackup = 0.2;
    private double xGravityGravity = 0.01; // player's ability to accelerate "faster" (can be upgraded?)
    private double SLOWDOWN_CONST = 3; // player slows down this (<----) times faster than speeds up
    private float width = 24, height = 64;
    private float gravity = 0.5f;
    private float MAX_SPEED = 15f;
//    private int forcedVelX = 0; // <--- for pistons
    private Camera cam;
    private boolean isCrouching = false;
    private boolean forcedCrouching = false;
    private Handler handler;

    public Player(float x, float y, float desiredVelX, Handler handler, Camera cam, ObjectId id) {
        super(x, y, id);
        this.desiredVelX = desiredVelX;
        this.handler = handler;
        this.cam = cam;
    }

    public void tick(LinkedList<GameObject> objectList) {
        calculateCrouching();
        calculateSlowDownConst();
        calculateVelX();
        applyVelX();
        Collision(objectList);
        calculateHeightDeath();
        // Can be dead by now (because collision and height)
        applyCam();
    }

    private void applyCam() {
        if (!dead) {
            cam.tick(this);
        } else {
            return;
        }
    }

    private void calculateHeightDeath() {
        if (y > Math.abs(Game.HEIGHT) + 500) { //You fell down :(
            dead = true;
            handler.endGame(cam);
        }
    }

    private void calculateCrouching() {
        if (isCrouching() && !forcedCrouching)
            if (canStand())
                setStand();
    }

    private void calculateSlowDownConst() {
        if (jumping || falling) {
            velY += gravity;
            SLOWDOWN_CONST = 1;
        } else {
            SLOWDOWN_CONST = 3;
        }
    }

    private void calculateVelX() {
        if (Math.abs(velX) < Math.abs(desiredVelX)) {
            // if we're here it means, that we are yet to reach the desired speed (moving TOO SLOW)
            if (desiredVelX >= 0) {
                // need to move to the right
                velX += xGravity;
                if (velX >= desiredVelX) {
                    velX = desiredVelX;
                }
            } else {
                // need to move to the left (negative velocity)
                velX -= xGravity;
                if (velX <= desiredVelX) {
                    velX = desiredVelX;
                }
            }
            //xGravity += xGravityGravity;
        } else if (Math.abs(velX) != Math.abs(desiredVelX)) {
            // if we're here it means, that we need to stop or slow down (moving TOO FAST)
            if (velX >= 0) {
                // positive velX
                velX -= xGravity * SLOWDOWN_CONST;
                if (velX < desiredVelX) {
                    velX = desiredVelX;
                }
            } else {
                // negative velX
                velX += xGravity * SLOWDOWN_CONST;
                if (-velX < desiredVelX) {
                    velX = desiredVelX;
                }
            }
            //xGravity += xGravityGravity;
        } else {
            if (desiredVelX != 0 && velX == -desiredVelX) {
                if (velX < 0) {
                    velX += xGravity;
                } else {
                    velX -= xGravity;
                }
            }
        }
    }

    private void applyVelX() {
        x += velX;
        y += velY;

        if (velX > MAX_SPEED) {
            velX = MAX_SPEED;
        } else if (velX < -MAX_SPEED) {
            velX = -MAX_SPEED;
        }
    }

    private void Collision(LinkedList<GameObject> objectList) {
        for (GameObject tmpObject : objectList) {
            if (tmpObject.getId() == ObjectId.Block || tmpObject.getId() == ObjectId.Piston || tmpObject.getId() == ObjectId.SquareSaw) {

                //Top side collision
                if (tmpObject.getId() != ObjectId.Piston) {
                    if (getBoundsTop().intersects(tmpObject.getBounds())) {
                        velY = 0;
                        y = tmpObject.getY() + 24;
                        if (tmpObject.getId() == ObjectId.SquareSaw) {
                            y += ((SquareSaw) tmpObject).getSide() / 3;
                        }
                    }
                } else {
                    if (getBoundsTop().intersects(tmpObject.getBounds())) {
                        dead = true;
                        handler.endGame(cam);
                    }
                }

                if (dead) {
                    return;
                }


                //Bottom side collision
                if (getBoundsBottom().intersects(tmpObject.getBounds())) {
                    y = tmpObject.getY() - height;
                    if (tmpObject.getId() == ObjectId.SquareSaw) {
                        y -= ((SquareSaw) tmpObject).getSide() / 3;

                    }
                    velY = 0;

                    jumping = falling = false;

                } else {
                    falling = true;
                }

                //Left side collision
                if (getBoundsRight().intersects(tmpObject.getBounds())) {
                    x = tmpObject.getX() + 24;
                    velX = 0;
                    if (tmpObject.getId() == ObjectId.SquareSaw) {
                        x += ((SquareSaw) tmpObject).getSide() / 3;
                    }
                }

                //Right side collision
                if (getBoundsLeft().intersects(tmpObject.getBounds())) {
                    x = tmpObject.getX() - 25;
                    velX = 0;
                    if (tmpObject.getId() == ObjectId.SquareSaw) {
                        x -= ((SquareSaw) tmpObject).getSide() / 3;
                    }
                }


                //Spike collision
            } else if (tmpObject.getId() == ObjectId.Spike) {
                if (getBoundsBottom().intersects(tmpObject.getBounds())) {
                    dead = true;
                    handler.endGame(cam);
                }
            }
        }

//        if (!forcedSpeed) {
//            forcedVelX = 0;
//        }
    }

    public void setCenterCrouch() {
        height = 32;

        forcedCrouching = true;
        isCrouching = true;
    }

    public void setRightCrouch() {
        float tmp = height;
        height = width;
        width = tmp;
    }

    public boolean isCrouching() {
        return isCrouching;
    }


    public boolean canStand() {
        forcedCrouching = false;

        for (GameObject tmpObject : handler.objectList) {
            if (tmpObject.getBounds().intersects(new Rectangle((int) x, (int) y - 32, (int) width, (int) height))) {
                return false;               //Check if full-sized player would intersect with  ceiling
            }
        }

        return true;
    }

    public void setStand() {
        if (canStand()) {
            isCrouching = false;
            y -= 32;
            height = 64;
        }
    }

    public void setDesiredVelX(float desiredVelX) {
        this.desiredVelX = desiredVelX;
    }

    public boolean isTickable() {
        return true;
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawRect((int) x, (int) y, (int) width, (int) height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle((int) (x + 5), (int) ((y + height) - (height / 3) + 1), (int) width - 10, (int) height / 3 + 1);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x + 5), (int) (y), (int) width - 10, (int) height / 3);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) (x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

}
