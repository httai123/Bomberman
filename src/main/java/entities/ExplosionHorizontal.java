package entities;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ExplosionHorizontal extends Explosion {
    /**
     * No theo chieu ngang.
     * @param position
     * @param bombRadius
     */
    public ExplosionHorizontal(Point2D.Float position, int bombRadius) {
        super(position);
        // toa do toi da ben trai va phai.
        float leftX = this.checkHorizontal(this.getPosition(), bombRadius, -32);
        float rightX = this.checkHorizontal(this.getPosition(), bombRadius, 32);
        // vi tri no trung tam.
        this.centerOffset = position.x - leftX;
        Rectangle2D.Float rect = new Rectangle2D.Float(leftX, this.getPosition().y, rightX - leftX + 32, 32);
        this.init(rect);
        this.animation = this.drawSprite((int) this.getWidth(), (int) this.getHeight());
        setSprite(this.animation[0]);
    }

    /**
     * Ham ktra pham vi no tra ve do dai bomb no theo chieu ngang.
     */
    private float checkHorizontal(Point2D.Float position, int bombRadius, int blockWidth) {
        float value = position.x;
        for (int i = 1; i <= bombRadius; i++) {
            value += blockWidth;
            for (int index = 0; index < GameTile.tiles.size(); index++) {
                Tile tile = GameTile.tiles.get(index);
                if (tile.getCollide().contains(value, position.y)) {
                    if (!tile.isBreakable()) {
                        // ko the no tuong nen do rong giam.
                        value -= blockWidth;
                    }
                }
            }
        }

        return value;
    }

    /**
     * Ve su no.
     * @param width
     * @param height
     * @return
     */
    private BufferedImage[] drawSprite(int width, int height) {
        BufferedImage[] spriteAnimation = new BufferedImage[FileInput.SpriteMap.EXPLOSION_SPRITEMAP.getImage().getWidth() / 32];
        for (int i = 0; i < spriteAnimation.length; i++) {
            spriteAnimation[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        for (int i = 0; i < spriteAnimation.length; i++) {
            Graphics2D g2 = spriteAnimation[i].createGraphics();
            g2.setColor(new Color(0, 0, 0, 0));
            g2.fillRect(0, 0, spriteAnimation[i].getWidth(), spriteAnimation[i].getHeight());

            for (int j = 0; j < spriteAnimation[i].getWidth() / 32; j++) {
                if (spriteAnimation[i].getWidth() / 32 == 1 || this.centerOffset == j * 32) {
                    g2.drawImage(this.sprites[0][i], j * 32, 0, null);
                } else if (j == 0) {
                    g2.drawImage(this.sprites[3][i], j * 32, 0, null);
                } else if (j == (spriteAnimation[i].getWidth() / 32) - 1) {
                    g2.drawImage(this.sprites[4][i], j * 32, 0, null);
                } else {
                    g2.drawImage(this.sprites[1][i], j * 32, 0, null);
                }
            }

            g2.dispose();
        }

        return spriteAnimation;
    }

}

