package objects;

import framework.GameObject;
import framework.ObjectId;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by aolo2 on 5/23/15.
 */

public class Popup extends GameObject {

    private int height = 50;
    private String text;
    private boolean isDynamic = false;

    public Popup(float x, float y, int height, String text, boolean isDynamic, ObjectId id) {
        super(x, y, id);
        this.height = height;
        this.text = text;
        this.isDynamic = isDynamic;
    }

    public void tick(LinkedList<GameObject> objectList) {

    }

    public void tick(ArrayList<Player> playersList, boolean isPlayerList) {
        if (isDynamic) {
            text = "";

            float x_val = 0, y_val = 0, velX_val = 0, velY_val = 0;
            boolean isFalling = false, isJumping = false;
            for (Player tmpObject : playersList) {
                x_val = tmpObject.getX();
                y_val = tmpObject.getY();
                velX_val = tmpObject.getVelX();
                velY_val = tmpObject.getVelY();
//                isFalling = tmpObject.isFalling(); Fix falling, then add it to the deb
                isJumping = tmpObject.isJumping();

                x = tmpObject.getX() + 150;
                y = tmpObject.getY() + 5;

                break;
            }

            text += ("X: " + String.format("%.3f", x_val) + "   Y: " + String.format("%.3f", y_val) + "   velX: " +
                    String.format("%.1f", velX_val) + "   velY: " + String.format("%.1f", velY_val) + " isJumping: " +
                    isJumping);

        }
    }

    public boolean isTickable() {
        return isDynamic;
    }

    public void render(Graphics g) {
        printText(g, height);
    }

    private void printText(Graphics g, int height) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, height));
        g.drawString(text, (int) ((x - 120)), (int) (y));
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 0, 0);
    }
}
