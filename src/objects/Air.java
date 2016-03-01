package objects;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Game;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by aolo2 on 5/22/15.
 */

public class Air extends GameObject {
    Texture tex = Game.getTexture();
    private int type;

    public Air(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
        g.drawImage(tex.block[17], (int) x, (int) y, null);
    }

    public boolean isTickable() {
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 0, 0);
    }
}
