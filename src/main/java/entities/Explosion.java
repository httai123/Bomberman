package entities;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Explosion extends Entity {
    protected BufferedImage[][] sprites;
    protected BufferedImage[] animation;
    protected float centerOffset;
    private int spriteIndex;
    private int spriteTimer;

    Explosion(Point2D.Float position) {
        super(position);
        this.sprites = FileInput.SpriteMap.EXPLOSION_SPRITEMAP.getSprites();
        this.centerOffset = 0;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
    }
    protected void init(Rectangle2D.Float collide) {
        this.setCollide(collide);
        this.setWidth(this.getCollide().width);
        this.setHeight(this.getCollide().height);
        this.setSprite(new BufferedImage((int) this.getWidth(), (int) this.getHeight(), BufferedImage.TYPE_INT_ARGB));
    }

    @Override
    public void update() {
        if (this.spriteTimer++ >= 4) {
            this.spriteIndex++;
            this.spriteTimer = 0;
        }
        if (this.spriteIndex >= this.animation.length) {
            this.disappear();
        } else {
            setSprite(this.animation[this.spriteIndex]);
        }
    }

    @Override
    public void startCollision(Entity collidingObj) {
        collidingObj.handleCollision(this);
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getCollide().x, this.getCollide().y);
        rotation.rotate(Math.toRadians(this.getMove()), this.getCollide().width / 2.0, this.getCollide().height / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.getSprite(), rotation, null);
    }

}

