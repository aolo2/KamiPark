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

public class Piston extends GameObject {

    private static int direction = 2;
    private float width = 24, height = 24;
    private int xs[], ys[];
    private final int MAX_SIZE = 120, MIN_SIZE = 24;

    Texture tex = Game.getTexture();

    public Piston(float x, float y, ObjectId id) {
        super(x, y, id);

        xs = new int[3];
        ys = new int[3];

        xs[0] = (int) x;
        xs[1] = (int) (x + (width / 2));
        xs[2] = (int) (x + width);

        ys[0] = ys[2] = (int) (y + height / 2 - 2);
        ys[1] = (int) (y + height);
    }

    public void tick(LinkedList<GameObject> object) {

        if (height == MAX_SIZE)
            direction = -2;
        else if (height == MIN_SIZE)
            direction = 2;

        height += direction;

        for (int i = 0; i < 3; i++) {
            ys[i] += direction;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect((int) x, (int) y, 24, (int) height - 15);
        g.setColor(Color.white);
        g.drawRect((int) x, (int) y, 24, (int) height - 15);

        g.drawImage(tex.block[6], (int) x, (int) y + (int) height - 24, null);
    }

    public boolean isTickable() {
        return true;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 24, (int) height);
    }
}
