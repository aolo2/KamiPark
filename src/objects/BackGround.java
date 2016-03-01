package objects;

import framework.GameObject;
import framework.ObjectId;
import window.Game;

import java.awt.*;
import java.util.LinkedList;

public class BackGround extends GameObject {
    private Color color;

    public BackGround(float x, float y, Color color, ObjectId id) {
        super(x, y, id);
        this.color = color;
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public boolean isTickable() {
        return false;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, 0, 0);
    }
}
