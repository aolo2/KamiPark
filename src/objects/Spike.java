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
public class Spike extends GameObject {

    Texture tex = Game.getTexture();

    public Spike(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> objectList) {

    }

    public boolean isTickable() {
        return false;
    }

    public void render(Graphics g) {
        g.drawImage(tex.block[5], (int) x, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 18, 18);
    }
}
