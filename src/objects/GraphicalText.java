package objects;

import framework.GameObject;
import framework.ObjectId;
import framework.Texture;
import window.Game;

import java.awt.*;
import java.util.LinkedList;

public class GraphicalText extends GameObject {
    Texture tex = Game.getTexture();

    public GraphicalText(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> objectList) {

    }

    public boolean isTickable() {
        return false;
    }

    public void render(Graphics g) {
        g.drawImage(tex.block[50], (int) x, (int) y, null);
        g.drawImage(tex.block[51], (int) x + 24, (int) y, null);
        g.drawImage(tex.block[52], (int) x + 48, (int) y, null);
        g.drawImage(tex.block[53], (int) x + 72, (int) y, null);

        g.drawImage(tex.block[54], (int) x + 120, (int) y, null);
        g.drawImage(tex.block[55], (int) x + 144, (int) y, null);
        g.drawImage(tex.block[56], (int) x + 168, (int) y, null);
        g.drawImage(tex.block[57], (int) x + 192, (int) y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 0, 0);
    }
}
