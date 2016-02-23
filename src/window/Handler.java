package window;

import framework.GameObject;
import framework.ObjectId;
import objects.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by aolo2 on 5/22/15.
 */

public class Handler {
    public LinkedList<GameObject> objectList = new LinkedList<>();
    public ArrayList<Player> playerList = new ArrayList<>();

    private int objectNum = 0, playerNum = 0;
    private static boolean allowedToTick = true;
    private GameObject tmpObject;
    private int spawnx = 0, spawny = 0;

    public void tick() {
        /* Game objects (not players) tick here */
        for (int i = 0; i < objectNum && allowedToTick; i++) {
            tmpObject = objectList.get(i);
            if (tmpObject.isTickable())
                if (tmpObject.getId() == ObjectId.Popup) {
                    Popup popup = (Popup) tmpObject;
                    popup.tick(playerList, true);
                }

            tmpObject.tick(objectList);

            if (!allowedToTick) {
                break;
            }
        }

        objectNum = objectList.size(); // Recalculate the number of objects

        /* Player(s) tick(s) here */
        for (int i = 0; i < playerNum && allowedToTick; i++) {
            playerList.get(i).tick(objectList);
        }

        playerNum = playerList.size();// Recalculate the number of player

        if (!allowedToTick) {
            return;
        }
    }

    public int getSpawnx() {
        return spawnx;
    }

    public int getSpawny() {
        return spawny;
    }

    public void render(Graphics g) {
        /* Render all game objects, but player(s) */
        for (int i = 0; i < objectNum; i++) {
            objectList.get(i).render(g);
        }

        /* Render player(s) */
        for (int i = 0; i < playerNum; i++) {
            playerList.get(i).render(g);
        }
    }

    public void addObject(GameObject object) {
        this.objectList.add(object);
    }

    public void addPlayer(Player p) {
        this.playerList.add(p);
    }

    public void removeObject(GameObject object) {
        this.objectList.remove(object);
    }

//    public void pauseGame(Camera cam) {
//        allowedToTick = false;
//    }

    public void endGame(Camera cam) {
        allowedToTick = false;
        cam.setCameraFixedOnPlayer(false);

        cam.setX(0);
        cam.setY(0);

        this.clearLevel();
        this.addObject(new GraphicalText(Game.WINDOW_WIDTH / 2, Game.WINDOW_HEIGHT / 2, ObjectId.GraphicalText));
    }

    public void createLevel(int level) {
        try {
            FileInputStream f = new FileInputStream(System.getProperty("user.dir") + "/src/levels/level" + String.valueOf(level));

            int data, width = 0, height = 0;
            char c;

            data = f.read();
            while (data != -1) {
                c = (char) data;

                width++;
                if (c == '\n') {         //Get height and width of the level
                    height++;
                    width = 0;
                }

                data = f.read();
            }

            height++;
            f.close();

            Game.HEIGHT = height * 24;
            Game.WIDTH = width * 24;

            //Start building level itself
            f = new FileInputStream(System.getProperty("user.dir") + "/src/levels/level" + String.valueOf(level));
            data = f.read();
            for (int i = 0; i <= height; i++) {
                for (int k = 0; k <= width; k++) {
                    c = (char) data;
                    switch (c) {
                        case '1':
                            this.addObject(new Block(k * 24, i * 24, 1, ObjectId.Block));
                            break;
                        case '2':
                            this.addObject(new Spike(k * 24, i * 24, ObjectId.Spike));
                            break;
                        case '3':
                            this.addObject(new GhostBlock(k * 24, i * 24, ObjectId.GhostBlock));
                            break;
                        case '5':
                            this.addObject(new Piston(k * 24, i * 24, ObjectId.Piston));
                            break;
                        case '6':
                            this.addObject(new Popup(k * 24, i * 24, 24, "TEXT", false, ObjectId.Popup));
                            break;
                        case '7':
                            this.addObject(new SquareSaw(k * 24, i * 24, 72, ObjectId.SquareSaw));
                        case 'l':
                            this.addObject(new Block(k * 24, i * 24, 3, ObjectId.Block));
                            break;
                        case 'r':
                            this.addObject(new Block(k * 24, i * 24, 2, ObjectId.Block));
                            break;
                        case 't':
                            this.addObject(new Block(k * 24, i * 24, 4, ObjectId.Block));
                            break;
                        case 'c':
                            this.addObject(new Block(k * 24, i * 24, 5, ObjectId.Block));
                            break;
                        case 's':
                            spawnx = k * 24;
                            spawny = i * 24;
                            break;
                    }
                    data = f.read();
                }
            }

            f.close();
            Game.setZoom(.6);

        } catch (IOException e) {
            System.out.println("\nThere is no such level! Here are some exceptions, maybe THEY will make you happy:");
            e.printStackTrace();
        }

    }

    public Player getPlayerInstance(int number) {
        return playerList.get(number);
    }

    public void clearLevel() {
        playerList.clear();
        playerNum = 0;
        objectList.clear();
        objectNum = 0;
    }
}
