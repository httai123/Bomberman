package entities;

import entities.Entity;
import entities.GameTile;
import entities.Tile;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Wall extends Tile {

    public Wall(Point2D.Float position, BufferedImage sprite, boolean isBreakable) {
        super(position, sprite);
        this.breakable = isBreakable;
    }

    @Override
    public void update() {
        if (this.checkExplosion()) {
            this.disappear();
        }
    }

    @Override
    public void destroy() {
        double random = Math.random();
        if (random < 0.3) {
            Powerup powerup = new Powerup(this.getPosition(), Powerup.randomPower());
            GameTile.act(powerup);
        }
    }

    @Override
    public void startCollision(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }
    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}