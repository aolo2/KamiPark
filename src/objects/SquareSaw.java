package objects;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Game;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by aolo2 on 5/23/15.
 */
public class SquareSaw extends GameObject {

    Texture tex = Game.getTexture();

    public int getSide() {
        return side;
    }

    private int side;

    public SquareSaw(float x, float y, int side, ObjectId id) {
        super(x, y, id);
        this.side = side;
    }

    public void tick(LinkedList<GameObject> objectList) {

    }

    public boolean isTickable() {
        return false;
    }

    public void render(Graphics g) {
        g.drawImage(tex.block[7], (int) (x - side / 3), (int) (y - side / 3), side, side, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x - side / 3), (int) (y - side / 3), side, side);
    }
}
