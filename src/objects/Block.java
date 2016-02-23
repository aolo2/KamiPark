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

public class Block extends GameObject {
    Texture tex = Game.getTexture();
    private int type;

    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id);
        this.type = type;
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
        switch (type) {
            case 1:
                g.drawImage(tex.block[0], (int) x, (int) y, null);
                break;
            case 2:
                g.drawImage(tex.block[1], (int) x, (int) y, null);
                break;
            case 3:
                g.drawImage(tex.block[2], (int) x, (int) y, null);
                break;
            case 4:
                g.drawImage(tex.block[3], (int) x, (int) y, null);
                break;
            case 5:
                g.drawImage(tex.block[4], (int) x, (int) y, null);
                break;
        }
    }

    public boolean isTickable() {
        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y - 1, 24, 25);
    }


}
