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

public class GhostBlock extends GameObject {

    private Texture tex = Game.getTexture();

    public GhostBlock(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
       g.drawImage(tex.block[16], (int) x, (int) y, null);
    }

    public boolean isTickable() {
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 0, 0);
    }
}
