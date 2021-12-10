package entities;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Balloom extends Enemy{
    private static int keyMove;
    private boolean isDead;
    private double speed;
    private int moveDirect;
    private int spriteIndex;
    private int spriteTimer;
    private String enemies;
    private BufferedImage[][] sprites;

    public Balloom(Point2D.Float position, BufferedImage[][] spriteMap) {
        super(position, spriteMap);
        this.getCollide().setRect(this.getPosition().x + 3, this.getPosition().y + 16 + 3,
                this.getWidth() - 6, this.getHeight() - 16 - 6);
        this.sprites = spriteMap;
        this.moveDirect = 3;
        this.spriteIndex = 0;
        this.spriteTimer = 0;
        this.speed = 2.0;
        keyMove = 0;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    private void moveUp() {
        this.moveDirect = 0;
        this.getPosition().setLocation(this.getPosition().x, this.getPosition().y - this.speed);
    }

    private void moveDown() {
        this.moveDirect = 1;
        this.getPosition().setLocation(this.getPosition().x, this.getPosition().y + this.speed);
    }

    private void moveLeft() {
        this.moveDirect = 2;
        this.getPosition().setLocation(this.getPosition().x - this.speed, this.getPosition().y);
    }

    private void moveRight() {
        this.moveDirect = 3;
        this.getPosition().setLocation(this.getPosition().x + this.speed, this.getPosition().y);
    }

    public BufferedImage getStartSprite() {
        return this.sprites[1][0];
    }

    public boolean isDead() {
        return this.isDead;
    }

    public void setDead() {
        this.isDead = true;
    }

    @Override
    public void update() {
        this.autoMove();
        this.getCollide().setRect(this.getPosition().x + 3, this.getPosition().y + 16 + 3,
                this.getWidth() - 6, this.getHeight() - 16 - 6);
        if (!this.isDead) {
            if ((this.spriteTimer += this.speed) >= 12) {
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

        } else {
            this.setDead();
            if (this.spriteTimer++ >= 30) {
                this.spriteIndex++;
                if (this.spriteIndex < this.sprites[4].length) {
                    setSprite(this.sprites[4][this.spriteIndex]);
                    this.spriteTimer = 0;
                } else if (this.spriteTimer >= 120) {
                    this.disappear();
                }
            }
        }
        this.unMove();
    }

    @Override
    public void autoMove() {
        if (keyMove == 0) {
            this.toggleDownPressed();
        }
        if (keyMove == 1) {
            this.toggleUpPressed();
        }
        if (keyMove == 2) {
            this.toggleLeftPressed();
        }
        if (keyMove == 3) {
            this.toggleRightPressed();
        }
    }

    @Override
    public void startCollision(Entity entity) {
        entity.handleCollision(this);
    }

    @Override
    public void handleCollision(Player player) {
        if(!this.isDead) player.setDead();
    }

    @Override
    public void handleCollision(Wall wall) {
        int keyRandom = (int) Math.round(Math.random() * 3);
        this.hardCollision(wall);
        if (keyMove == 0) {
            keyMove = keyRandom;
        } else if (keyMove == 1) {
            keyMove = keyRandom;
        } else if (keyMove == 2) {
            keyMove = keyRandom;
        } else keyMove = keyRandom;
    }

    @Override
    public void handleCollision(Explosion explosion) {
        if (!this.isDead) {
            MusicPlayer.SFX(3);
            this.isDead = true;
            this.spriteIndex = 0;
        }
    }

    @Override
    public void handleCollision(Bomb bomb) {
        int keyRandom = (int) Math.round(Math.random() * 3);
        this.hardCollision(bomb);
        if (keyMove == 0) {
            keyMove = keyRandom;
        }
        else if (keyMove == 1) {
            keyMove = keyRandom;
        }
        else if (keyMove == 2) {
            keyMove = keyRandom;
        }
        else if (keyMove == 3) {
            keyMove = keyRandom;
        }
    }

    @Override
    public void handleCollision(Powerup powerup) {
        this.speed += 0.25;
        powerup.disappear();
    }

    public void setEnemies(String enemies) {
        this.enemies = enemies;
    }

    public String getEnemies() {
        return enemies;
    }
}
