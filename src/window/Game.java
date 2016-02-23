package window;

import framework.KeyInput;
import framework.ObjectId;
import framework.PlayerMovement;
import framework.Texture;
import objects.Player;
import objects.Popup;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by aolo2 on 5/22/15.
 */

public class Game extends Canvas implements Runnable {
    private static float desiredZoom = 1, zoom;
    private static boolean isRunning = false;
    private Thread thread;
    private int fps = 60;
    private int frameCount = 0;
    private int levelNumber;
    public static int WIDTH, HEIGHT;
    public static int WINDOW_WIDTH, WINDOW_HEIGHT;

    private Handler handler;
    private Camera cam;
    static Texture tex, player1Animation;


    public static boolean isRunning() {
        return isRunning;
    }

    public static void setZoom(double value) {
        desiredZoom = (float) value;
    }

    public static float getZoom() {
        return zoom;
    }

    private void init() {

//        System.out.print("Load level: ");
//
//        Scanner in = new Scanner(System.in);
//        levelNumber = in.nextInt();

        handler = new Handler();
        cam = new Camera(0, 0);
        tex = new Texture();
        player1Animation = new Texture();
        zoom = 1;

//      handler.addObject(new Popup(250, 55, 10, "", true, ObjectId.Popup)); // Player debug info
//        System.out.print("Creating level " + levelNumber + ": ");
        // TODO: LEVEL LOADING
        handler.createLevel(0); /* Later replace with 'levelnumber' */
        handler.addPlayer(new Player(handler.getSpawnx(), handler.getSpawny(), 0, handler, cam, ObjectId.Player)); // Player

        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start() {
        if (isRunning)
            return;

        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        init();
        this.requestFocus();
        gameLoop();
    }

    static public void stop() {
        isRunning = false;
    }

    public void gameLoop() {
        final double GAME_FPS = 60.0;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_FPS;
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        double lastUpdateTime, lastRenderTime;

        lastUpdateTime = System.nanoTime();

        final double TARGET_FPS = 120.0;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        int lastSecondPassedTime = (int) (lastUpdateTime / 1000000000);

        while (isRunning) {
            double now = System.nanoTime();
            int updateCount = 0;

            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                tick();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }

            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
            render(interpolation);
            lastRenderTime = now;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondPassedTime) {
//                System.out.println("Camera: " + " x=" + cam.getX() + " y=" + cam.getY());
                fps = frameCount;
                frameCount = 0;
                lastSecondPassedTime = thisSecond;
            }

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                now = System.nanoTime();
            }
        }
    }

    private void tick() {
        if (desiredZoom != zoom) {
            if (desiredZoom > zoom) {
                // need to zoom in
                zoom += (desiredZoom - zoom) / 10;
            } else {
                zoom -= (zoom - desiredZoom) / 10;
            }
        }
        handler.tick();
    }

    private void render(float interpolation) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //==========Draw here============


        Color bgColor = Color.black;
        g.setColor(bgColor);

        Color debugInfoColor = (new Color(255 - bgColor.getRed(), 255 - bgColor.getGreen(), 255 - bgColor.getBlue()));

        g.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(debugInfoColor);
        g2d.setFont(new Font(g2d.getFont().getFontName(), Font.PLAIN, (int) 10));
        g2d.drawString("fps: " + fps + " zoom: " + String.format("%.2f", zoom), 5, 15);
        g2d.scale(zoom, zoom);
        g2d.translate(cam.getX(), cam.getY()); //begin of cam

        handler.render(g);
        frameCount++;

        g2d.translate(-cam.getX(), -cam.getY()); //end of cam
        //===============================

        g.dispose();
        bs.show();
    }

    public static Texture getTexture() {
        return tex;
    }

    public static void main(String args[]) {

   /*     System.out.println("\nPlease choose from a list of available resolutions:\n");
        System.out.println("1. 640x360");
        System.out.println("2. 768x432");
        System.out.println("3. 896x504");
        System.out.println("4. 1024x576");
        System.out.println("5. 1280x720");
        System.out.println("6. 1920x1080");

        System.out.print("\nYour choice: ");
        Scanner in = new Scanner(System.in); */
        int res_num = 4;

        switch (res_num) { // Note: uncomment, and later replace with graphical stuff.
            case 1:
                WINDOW_WIDTH = 640;
                WINDOW_HEIGHT = 360;
                break;
            case 2:
                WINDOW_WIDTH = 768;
                WINDOW_HEIGHT = 432;
                break;
            case 3:
                WINDOW_WIDTH = 896;
                WINDOW_HEIGHT = 504;
                break;
            case 4:
                WINDOW_WIDTH = 1024;
                WINDOW_HEIGHT = 576;
                break;
            case 5:
                WINDOW_WIDTH = 1280;
                WINDOW_HEIGHT = 720;
                break;
            case 6:
                WINDOW_WIDTH = 1920;
                WINDOW_HEIGHT = 1080;
                break;
            default:
                System.out.println("Out of range, setting to default (1024x576)");
                WINDOW_WIDTH = 1024;
                WINDOW_HEIGHT = 576;
                break;
        }


        new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "Kamikadze Park Prototype", new Game());
    }
}
