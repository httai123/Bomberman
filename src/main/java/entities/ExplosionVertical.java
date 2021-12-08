package entities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ExplosionVertical extends Explosion {
    /**
     * No theo chieu doc.
     * No theo chieu doc.
     *
     * @param position
     * @param bomRadius
     */
    public ExplosionVertical(Point2D.Float position, int bomRadius) {
        super(position);
        float topY = this.checkVertical(this.getPosition(), bomRadius, -32);
        float bottomY = this.checkVertical(this.getPosition(), bomRadius, 32);
        this.centerOffset = position.y - topY;
        Rectangle2D.Float rect = new Rectangle2D.Float(this.getPosition().x, topY, 32, bottomY - topY + 32);
        this.init(rect);
        this.animation = this.drawSprite((int) this.getWidth(), (int) this.getHeight());
        setSprite(this.animation[0]);
    }

    private float checkVertical(Point2D.Float position, int bombRadius, int blockHeight) {
        float value = position.y;

        for (int i = 1; i <= bombRadius; i++) {
            value += blockHeight;
            for (int index = 0; index < GameTile.tiles.size(); index++) {
                Tile tile = GameTile.tiles.get(index);
                if (tile.getCollide().contains(position.x, value)) {
                        if(tile.isBreakable()){
                            return value;
                        }
                        value -= blockHeight;
                }
            }
        }

        return value;
    }


    private BufferedImage[] drawSprite(int width, int height) {
        BufferedImage[] spriteAnimation = new BufferedImage[FileInput.SpriteMap.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
        for (int i = 0; i < spriteAnimation.length; i++) {
            spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        for (int i = 0; i < spriteAnimation.length; i++) {
            Graphics2D g2 = spriteAnimation[i].createGraphics();
            g2.setColor(new Color(0, 0, 0, 0));
            g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

            for (int j = 0; j < spriteAnimation[i].getHeight() / 32; j++) {
                if (spriteAnimation[i].getHeight() / 32 == 1 || this.centerOffset == j * 32) {
                    g2.drawImage(this.sprites[0][i], 0, j * 32, null);
                } else if (j == 0) {
                    g2.drawImage(this.sprites[5][i], 0, j * 32, null);
                } else if (j == (spriteAnimation[i].getHeight() / 32) - 1) {
                    g2.drawImage(this.sprites[6][i], 0, j * 32, null);
                } else {
                    g2.drawImage(this.sprites[2][i], 0, j * 32, null);
                }
            }
            g2.dispose();
        }
        return spriteAnimation;
    }
}