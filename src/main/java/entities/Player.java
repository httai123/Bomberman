package entities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends MovingEntities {

    private Bomb bomb;
    private boolean dead;
    private BufferedImage[][] sprites;
    private int moveDirect;
    private int spriteIndex;
    private int spriteTimer;
    private float moveSpeed;
    private int bombRadius;
    private int maxBombs;
    private int currentBombs;
    private int explosionTime;
    private boolean nextLevel;

    /**
     * Khoi tao bomber.
     */
    public Player(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap[1][0]);
        this.getCollide().setRect(this.getPosition().x + 3, this.getPosition().y + 16 + 3,
                this.getWidth() - 6, this.getHeight() - 16 - 6);
        this.sprites = spriteMap;
        this.moveDirect = 3;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.moveSpeed = 2;
        this.bombRadius = 1;
        this.maxBombs = 1;
        this.currentBombs = this.maxBombs;
        this.explosionTime = 150;
        this.nextLevel = false;
    }

    private void moveUp() {
        this.moveDirect = 0;
        this.getPosition().setLocation(this.getPosition().x, this.getPosition().y - this.moveSpeed);
    }
    private void moveDown() {
        this.moveDirect = 1;
        this.getPosition().setLocation(this.getPosition().x, this.getPosition().y + this.moveSpeed);
    }
    private void moveLeft() {
        this.moveDirect = 2;
        this.getPosition().setLocation(this.getPosition().x - this.moveSpeed, this.getPosition().y);
    }
    private void moveRight() {
        this.moveDirect = 3;
        this.getPosition().setLocation(this.getPosition().x + this.moveSpeed, this.getPosition().y);
    }
    // dat bomb
    private void bombAction() {
        float x = Math.round(this.getPosition().getX() / 32) * 32;
        float y = Math.round((this.getPosition().getY() + 16) / 32) * 32;
        Point2D.Float bombLocation = new Point2D.Float(x, y);
        for (int i = 0; i < GameTile.tiles.size(); i++) {
            Entity entity = GameTile.tiles.get(i);
            if (entity.getCollide().contains(bombLocation)) {
                return;
            }
        }
        this.bomb = new Bomb(bombLocation, this.bombRadius, this.explosionTime, this);
        GameTile.act(bomb);
        this.currentBombs--;
    }

    public void restoreBomb() {
        this.currentBombs = Math.min(this.maxBombs, this.currentBombs + 1);
    }
    public void addBombs(int bomb) {
        System.out.println("Current bombs: " + maxBombs);
        maxBombs = Math.min(4, maxBombs + bomb);
        currentBombs = Math.min(maxBombs, currentBombs + 1);
        System.out.println("Up to: " + maxBombs + " bombs");
    }
    public void increaseBombRadius(int radius) {
        System.out.println("Current Rate: " + bombRadius);
        bombRadius = Math.min(4, bombRadius + radius);
        System.out.println("Up to: " + bombRadius + " radius");
    }
    public void speedUp(float speed) {
        System.out.println("Current speed: " + moveSpeed + " m/s");
        moveSpeed = Math.min(4, moveSpeed + speed);
        System.out.println("Speed up to: " + moveSpeed + " m/s");
    }
    public void increaseTimeExplore(int time) {
        System.out.println("Time Explore: " + this.explosionTime);
        explosionTime = Math.max(160, explosionTime + time);
        System.out.println("Increse : " + explosionTime);
    }
    public BufferedImage getStartSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.dead;
    }

    public void setDead() {
        this.dead = true;
    }

    public void setNextLevel(boolean isSupreme) {
        this.nextLevel = isSupreme;
    }

    public boolean isNextLevel() {
        return this.nextLevel;
    }

    @Override
    public void update() {
        this.getCollide().setRect(this.getPosition().x + 3, this.getPosition().y + 16 + 3,
                this.getWidth() - 6, this.getHeight() - 16 - 6);
        if (!this.dead) {
            if ((this.spriteTimer += this.moveSpeed) >= 12) {
                this.spriteIndex++;
                this.spriteTimer = 0;
            }
            if ((!this.moveUp && !this.moveDown && !this.moveLeft && !this.moveRight)
                    || (this.spriteIndex >= this.sprites[0].length)) {
                this.spriteIndex = 0;
            }
            setSprite(this.sprites[this.moveDirect][this.spriteIndex]);
            if (this.moveUp) {
                this.moveUp();
            }
            if (this.moveDown) {
                this.moveDown();
            }
            if (this.moveLeft) {
                this.moveLeft();
            }
            if (this.moveRight) {
                this.moveRight();
            }
            if (this.bombAct && this.currentBombs > 0) {
                this.bombAction();
            }
        } else {
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    setSprite(this.sprites[4][this.spriteIndex]);
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 250) {
                    this.disappear();
                }
            }
        }
    }
    @Override
    public void startCollision(Entity entity) {
        entity.handleCollision(this);
    }

    @Override
    public void handleCollision(Wall wall) {
        this.hardCollision(wall);
    }

    @Override
    public void handleCollision(Explosion explosion) {
        if (!this.dead) {
            // sound dead here
            this.dead = true;
            this.spriteIndex = 0;
        }
    }
    @Override
    public void handleCollision(Bomb bomb) {
        Rectangle2D intersection = this.getCollide().createIntersection(bomb.getCollide());
        if (intersection.getWidth() >= intersection.getHeight() && intersection.getHeight() <= 6
                && Math.abs(this.getCollide().getCenterX() - bomb.getCollide().getCenterX()) <= 8) {
            this.hardCollision(bomb);
        }
        if (intersection.getHeight() >= intersection.getWidth() && intersection.getWidth() <= 6
                && Math.abs(this.getCollide().getCenterY() - bomb.getCollide().getCenterY()) <= 8) {
            this.hardCollision(bomb);
        }
    }
    @Override
    public void handleCollision(Powerup powerup) {
        // sound an power up
        powerup.grantBonus(this);
        powerup.disappear();
    }
}