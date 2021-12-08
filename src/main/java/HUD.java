import entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    private Player players;
    boolean matchSet;
    private int level;
    private BufferedImage playerInfo;

    public HUD() {
        this.matchSet = false;
        this.level = 1;
    }

    public void reset() {
        level = 1;
        this.matchSet = false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPlayer(Player player) {
        this.players = player;
    }

    public void init() {
        playerInfo = new BufferedImage(GamePanel.REAL_WIDTH / 1, Display.HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getPlayerInfo() {
        return playerInfo;
    }

    public void update() {
        try {
            if (players.isDead()) {
                this.matchSet = true;
            }
            if (this.players.isNextLevel()) {
                if (!this.players.isDead()) {
                    this.players.setDead();
                }
                this.matchSet = true;
                level++;
            }

        } catch (Exception e) {
            System.out.println("Ko load dc next level");
        }
//        try {
//
//                if(this.players.isNextLevel()) {
//                    if (!this.players.isDead()) {
//                        this.players.setDead();
//                    } else if (!this.players.isDead()) {
//                        this.playerScore += 200;
//                    }
//                }
//                    this.matchSet = true;
//                    level++;
//
//            if (deadPlayers == this.players.length - 1) {
//                    if (!this.players[i].isDead()) {
//                        // level
//                        this.matchSet = true;
//                    }
//                }
//            } else if (deadPlayers >= this.players.length) {
//                // This should only be reached two or more of the last players die at the same time
//                this.matchSet = true;
//            }
//
//        } catch (Exception e) {
//            System.out.println("Loi nhieu qua");
//        }
    }

    public void draw() {
        Graphics playerGraphics = this.playerInfo.createGraphics();

        playerGraphics.clearRect(0, 0, playerInfo.getWidth(), playerInfo.getHeight());
        playerGraphics.setColor(Color.ORANGE);    // Player 1 info box border color

        Font font = new Font("Arial", Font.BOLD, 24);
        playerGraphics.drawRect(1, 1, this.playerInfo.getWidth() - 2, this.playerInfo.getHeight() - 2);
        playerGraphics.drawImage(this.players.getStartSprite(), 0, 0, null);

        // Draw score
        playerGraphics.setFont(font);
        playerGraphics.setColor(Color.ORANGE);
        playerGraphics.drawString("Level: " + this.level,
                this.playerInfo.getWidth() / 2 - 45, 32);

        playerGraphics.dispose();
    }
}
