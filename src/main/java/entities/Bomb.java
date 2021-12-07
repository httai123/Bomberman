package entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Bomb extends Tile {

    private Player player;
    private BufferedImage[][] sprites;
    private int spriteIndex;
    private int spriteTimer;
    private int bombRadius;  // do dai bomb
    private int explosionTime; // thoi gian bom no
    private int time;

    public Bomb(Point2D.Float position, int bombRadius, int explosionTime, Player player) {
        super(position, FileInput.SpriteMap.BOMB.getSprites()[0][0]);
        this.getCollide().setRect(this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        this.sprites = FileInput.SpriteMap.BOMB.getSprites();
        this.bombRadius = bombRadius;
        this.explosionTime = explosionTime;
        this.player = player;
        this.time = 0;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.breakable = true;
    }

   // bomb no
    private void explode() {
        this.reUpdate();
        GameTile.act(new ExplosionHorizontal(this.getPosition(), this.bombRadius));
        GameTile.act(new ExplosionVertical(this.getPosition(), this.bombRadius));
        this.player.restoreBomb();
        // them sound bomb no cho nay
    }


    @Override
    public void update() {
        // khoi tao va cham
        this.getCollide().setRect(this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.sprites[0].length) {
            this.spriteIndex = 0;
        }
        setSprite(this.sprites[0][this.spriteIndex]);
        // bomb sau khi no
        if (this.time++ >= this.explosionTime) {
            this.disappear();
        }
    }

    @Override
    public void destroy() {
        this.explode();
    }

    @Override
    public void startCollision(Entity entity) {
        entity.handleCollision(this);
    }

    @Override
    public void handleCollision(Doll doll) {
    }

    @Override
    public void handleCollision(Wall wall) {
        this.hardCollision(wall);
    }

    @Override
    public void handleCollision(Bomb bomb) {
        this.hardCollision(bomb);
    }

    @Override
    public void handleCollision(Explosion explosion) {
        this.disappear();
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

}

