package entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Tile extends Entity {
    public boolean breakable;
    public Explosion explosion;

    public Tile(Point2D.Float position, BufferedImage sprite) {
        super(position, sprite);
        this.reUpdate();
    }

    public abstract boolean isBreakable();

    public boolean checkExplosion() {
        return this.isBreakable() && this.explosion != null && this.explosion.isDestroyed();
    }

    public void reUpdate() {
        // dat bomb vao vi tri.
        float x = Math.round(this.getPosition().getX() / 32) * 32;
        float y = Math.round(this.getPosition().getY() / 32) * 32;
        this.getPosition().setLocation(x, y);
    }

    /**
     * Xủ lí va chạm vụ nổ
     */
    @Override
    public void handleCollision(Explosion collidingObj) {
        if (this.isBreakable()) {
            if (this.explosion == null) {
                this.explosion = collidingObj;
            }
        }
    }

}

