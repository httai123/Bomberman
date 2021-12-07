package entities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class Entity implements Observable, HandleCollision {
    private Point2D.Float position;
    private BufferedImage sprite;
    private Rectangle2D.Float collide;
    private float move;
    private float width;
    private float height;
    private boolean destroyed;

    public Entity(Point2D.Float position) {
        this.position = new Point2D.Float(position.x, position.y);
        this.move = 0;
    }

    public Entity(Point2D.Float position, BufferedImage sprite) {
        this(sprite);
        this.position = new Point2D.Float(position.x, position.y);
        this.move = 0;
        this.collide = new Rectangle2D.Float(position.x, position.y, this.width, this.height);
    }

    private Entity(BufferedImage sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
    }

    public void setCollide(Rectangle2D.Float collide) {
        this.collide = collide;
    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }

    public float getMove() {
        return move;
    }

    public void setMove(float move) {
        this.move = move;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public float getHeight() {
        return height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void disappear() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Xu ly va cham voi vat the cung.
     */
    public void hardCollision(Entity obj) {
        Rectangle2D intersection = this.collide.createIntersection(obj.collide);
        if (intersection.getWidth() >= intersection.getHeight()) {
            if (intersection.getMaxY() >= this.collide.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y - intersection.getHeight());
            }
            if (intersection.getMaxY() >= obj.collide.getMaxY()) {
                this.position.setLocation(this.position.x, this.position.y + intersection.getHeight());
            }
            if (intersection.getWidth() < 16) {
                if (intersection.getMaxX() >= this.collide.getMaxX()) {
                    this.position.setLocation(this.position.x - 0.5, this.position.y);
                }
                if (intersection.getMaxX() >= obj.collide.getMaxX()) {
                    this.position.setLocation(this.position.x + 0.5, this.position.y);
                }
            }
        }
        if (intersection.getHeight() >= intersection.getWidth()) {
            if (intersection.getMaxX() >= this.collide.getMaxX()) {
                this.position.setLocation(this.position.x - intersection.getWidth(), this.position.y);
            }
            if (intersection.getMaxX() >= obj.collide.getMaxX()) {
                this.position.setLocation(this.position.x + intersection.getWidth(), this.position.y);
            }
            if (intersection.getHeight() < 16) {
                if (intersection.getMaxY() >= this.collide.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y - 0.5);
                }
                if (intersection.getMaxY() >= obj.collide.getMaxY()) {
                    this.position.setLocation(this.position.x, this.position.y + 0.5);
                }
            }
        }
    }

    public Rectangle2D.Float getCollide() {
        return this.collide;
    }


    public float getPositionY() {
        return this.position.y + this.height;
    }

    /**
     * ve su dich chuyen hoat anh.
     */
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.position.getX(), this.position.getY());
        rotation.rotate(Math.toRadians(this.move), this.sprite.getWidth() / 2.0, this.sprite.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.sprite, rotation, null);
    }

    @Override
    public int compareTo(Entity o) {
        return Float.compare(this.position.y, o.position.y);
    }
}


