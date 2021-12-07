package entities;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Powerup extends Tile {
    public static float randomPortal;
    public static float randomStrongItem;

    public enum Items {
        Bomb(FileInput.Images.POWER_BOMB.getImage()) {
            @Override
            protected void grantBonus(Player player) {
                player.addBombs(1);
            }
        },
        BombRadius(FileInput.Images.POWER_RADIUS.getImage()) {
            @Override
            protected void grantBonus(Player player) {
                player.increaseBombRadius(1);
            }
        },
        BonusTime(FileInput.Images.POWER_TIME.getImage()) {
            @Override
            protected void grantBonus(Player player) {
                player.increaseTimeExplore(15);
            }
        },
        SpeedUp(FileInput.Images.POWER_SPEED.getImage()) {
            @Override
            protected void grantBonus(Player player) {
                player.speedUp(0.5f);
            }
        },
        Portal(FileInput.Images.PORTAL.getImage()) {
            @Override
            protected void grantBonus(Player player) {
                player.setNextLevel(true);
            }
        };

        private BufferedImage sprite;

        Items(BufferedImage sprite) {
            this.sprite = sprite;
        }

        protected abstract void grantBonus(Player player);

    }

    private Items items;

    /**
     * Khoi tao powerup ngau nhien.
     * @param position
     * @param items
     */
    public Powerup(Point2D.Float position, Items items) {
        super(position, items.sprite);
        setCollide(new Rectangle2D.Float(position.x + 8, position.y + 8,
                this.getWidth() - 16, this.getHeight() - 16));
        this.items = items;
        this.breakable = true;
    }

    private static Items[] powerups = Items.values();
    private static Random random = new Random();

    /**
     * Random powerup.
     */
    public static final Items randomPower() {
        randomStrongItem = (float) Math.random();
        System.out.println(randomStrongItem);
        if (randomPortal == 2) return powerups[4];
        else return powerups[random.nextInt(powerups.length - 2)];
    }

    /**
     * Bomberman dc tang suc manh.
     * @param player
     */
    public void grantBonus(Player player) {
        this.items.grantBonus(player);

    }

    @Override
    public void update() {
        if (this.checkExplosion()) {
            // chen sound an powerup here.
            this.disappear();
        }
    }

    @Override
    public void startCollision(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void handleCollision(Bomb collidingObj) {
        this.disappear();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}