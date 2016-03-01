package framework;

import window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {
    SpriteSheet bs, ps;
    private BufferedImage block_sheet = null, playerAnimation = null;

    public BufferedImage[] block = new BufferedImage[100];
    public BufferedImage[] playerFrames = new BufferedImage[100];

    public Texture() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {

            block_sheet = loader.loadImage(System.getProperty("user.dir") + "/src/textures/Sprites_inv.png");
            playerAnimation = loader.loadImage(System.getProperty("user.dir") + "/src/textures/Player_inv.png");

        } catch (Exception e) {
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(playerAnimation);

        getTextures();
    }

    private void getTextures() {
        block[0] = bs.grabImage(1, 1, 24, 24); //floor
        block[1] = bs.grabImage(2, 1, 24, 24); //lwall
        block[2] = bs.grabImage(3, 1, 24, 24); //rwall
        block[3] = bs.grabImage(4, 1, 24, 24); //ceiling
        block[4] = bs.grabImage(5, 1, 24, 24); //corner
        block[5] = bs.grabImage(6, 1, 24, 24); //spike
        block[6] = bs.grabImage(7, 1, 24, 24); //piston head
        block[7] = bs.grabImage(1, 2, 24, 24); //big squarish saw
        block[8] = bs.grabImage(2, 2, 24, 24); // ...
        block[9] = bs.grabImage(3, 2, 24, 24);
        block[10] = bs.grabImage(1, 3, 24, 24);
        block[11] = bs.grabImage(2, 3, 24, 24);
        block[12] = bs.grabImage(3, 3, 24, 24); // ...
        block[13] = bs.grabImage(1, 4, 24, 24);
        block[14] = bs.grabImage(2, 4, 24, 24);
        block[15] = bs.grabImage(3, 4, 24, 24); // ... it ends here
        block[16] = bs.grabImage(10, 1, 24, 24); //ghost block
        block[17] = bs.grabImage(9, 1, 24, 24); //air
//        block[18] = bs.grabImage(3, 4, 24, 24);
        block[50] = bs.grabImage(1, 10, 24, 24); // G
        block[51] = bs.grabImage(2, 10, 24, 24); // A
        block[52] = bs.grabImage(3, 10, 24, 24); // M
        block[53] = bs.grabImage(4, 10, 24, 24); // E
        block[54] = bs.grabImage(6, 10, 24, 24); // O
        block[55] = bs.grabImage(7, 10, 24, 24); // V
        block[56] = bs.grabImage(8, 10, 24, 24); // E
        block[57] = bs.grabImage(9, 10, 24, 24); // R

        playerFrames[0] = ps.grabImage(1, 1, 24, 48);
        playerFrames[1] = ps.grabImage(2, 1, 24, 48);
    }
}