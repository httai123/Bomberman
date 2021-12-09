package entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileInput {
    public enum Images {
        PORTAL, POWER_BOMB, POWER_SPEED,
         POWER_TIME, POWER_RADIUS, WALL, BRICK, GRASS,
        SNOW,WOODEN,TREE;

        private BufferedImage image = null;

        public BufferedImage getImage() {
            return this.image;
        }
    }

    public enum SpriteMap {
        PLAYER,
        BOMB,
        DOLL,
        MINVO,
        KONDORIA,
        BALLOOM,
        ONEAL,
        EXPLOSION_SPRITEMAP;

        private BufferedImage image = null;
        private BufferedImage[][] sprites = null;

        public BufferedImage getImage() {
            return this.image;
        }

        public BufferedImage[][] getSprites() {
            return this.sprites;
        }
    }

    public enum Files {
        LEVEL1, LEVEL2, LEVEL3, LEVEL4;

        private InputStreamReader file = null;

        public InputStreamReader getFile() {
            return this.file;
        }
    }

    public static void readFiles() {
        try {
            Images.GRASS.image = ImageIO.read(FileInput.class.getResource("/textures/Grass.png"));
            Images.SNOW.image = ImageIO.read(FileInput.class.getResource("/textures/Snow.png"));
            Images.WOODEN.image = ImageIO.read(FileInput.class.getResource("/textures/Wooden.png"));
            Images.TREE.image = ImageIO.read(FileInput.class.getResource("/textures/Tree.png"));
            Images.WALL.image = ImageIO.read(FileInput.class.getResource("/textures/Wall.png"));
            Images.BRICK.image = ImageIO.read(FileInput.class.getResource("/textures/Brick.png"));
            Images.POWER_BOMB.image = ImageIO.read(FileInput.class.getResource("/textures/powerup_bomb.png"));
            Images.POWER_RADIUS.image = ImageIO.read(FileInput.class.getResource("/textures/powerup_radius.png"));
            Images.POWER_SPEED.image = ImageIO.read(FileInput.class.getResource("/textures/powerup_speed.png"));
            Images.POWER_TIME.image = ImageIO.read(FileInput.class.getResource("/textures/powerup_time.png"));
            Images.PORTAL.image = ImageIO.read(FileInput.class.getResource("/textures/Portal.png"));
            SpriteMap.PLAYER.image = ImageIO.read(FileInput.class.getResource("/textures/bomber1.png"));
            SpriteMap.BOMB.image = ImageIO.read(FileInput.class.getResource("/textures/Bomb.png"));
            SpriteMap.EXPLOSION_SPRITEMAP.image = ImageIO.read(FileInput.class.getResource("/textures/Explosion.png"));
            SpriteMap.ONEAL.image = ImageIO.read(FileInput.class.getResource("/textures/oneal.png"));
            SpriteMap.DOLL.image = ImageIO.read(FileInput.class.getResource("/textures/doll.png"));
            SpriteMap.BALLOOM.image = ImageIO.read(FileInput.class.getResource("/textures/balloom.png"));
            SpriteMap.MINVO.image = ImageIO.read(FileInput.class.getResource("/textures/minvo.png"));
            SpriteMap.KONDORIA.image = ImageIO.read(FileInput.class.getResource("/textures/kondoria.png"));
            Files.LEVEL1.file = new InputStreamReader(FileInput.class.getResourceAsStream("/textures/maps/Level1.txt"));
            Files.LEVEL2.file = new InputStreamReader(FileInput.class.getResourceAsStream("/textures/maps/Level2.txt"));
            Files.LEVEL3.file = new InputStreamReader(FileInput.class.getResourceAsStream("/textures/maps/Level3.txt"));
            Files.LEVEL4.file = new InputStreamReader(FileInput.class.getResourceAsStream("/textures/maps/Level4.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static BufferedImage[][] cropMap(BufferedImage spriteMap, int spriteWidth, int spriteHeight) {
        int rows = spriteMap.getHeight() / spriteHeight;
        int cols = spriteMap.getWidth() / spriteWidth;
        BufferedImage[][] sprites = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sprites[row][col] = spriteMap.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
            }
        }

        return sprites;
    }
    public static void init() {
        SpriteMap.PLAYER.sprites = cropMap(SpriteMap.PLAYER.image, 32, 48);
        SpriteMap.ONEAL.sprites = cropMap(SpriteMap.ONEAL.image, 32, 48);
        SpriteMap.BALLOOM.sprites = cropMap(SpriteMap.BALLOOM.image, 32, 48);
        SpriteMap.MINVO.sprites = cropMap(SpriteMap.MINVO.image, 32, 48);
        SpriteMap.KONDORIA.sprites = cropMap(SpriteMap.KONDORIA.image, 32, 48);
        SpriteMap.DOLL.sprites = cropMap(SpriteMap.DOLL.image, 32, 48);
        SpriteMap.BOMB.sprites = cropMap(SpriteMap.BOMB.image, 32, 32);
        SpriteMap.EXPLOSION_SPRITEMAP.sprites = cropMap(SpriteMap.EXPLOSION_SPRITEMAP.image, 32, 32);
    }

}
