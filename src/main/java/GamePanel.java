import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GamePanel extends JPanel implements Runnable {
    private Thread thread;
    public static int REAL_WIDTH;
    public static int REAL_HEIGHT;
    private boolean running;
    public int screenDelay;
    private BufferedImage level;
    private Graphics2D graphics2D;
    private BufferedImage grass;
    private HUD gameHUD;
    private int mapWidth;
    private int mapHeight;
    private ArrayList<ArrayList<String>> mapLayout;
    private ArrayList<ArrayList<ArrayList<String>>> map;
    private BufferedReader[] br;
    private HashMap<Integer, Keyboard> control;
    private int key;
    private boolean winning;
    public static BufferedImage SoftWall = FileInput.Images.BRICK.getImage();

    public BufferedImage getSoftWall() {
        return SoftWall;
    }

    public GamePanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.setControl();
        this.setKey();
        this.grass = FileInput.Images.GRASS.getImage();
        this.loadMapFile();
        this.addKeyListener(new KeyControl(this));
    }

    public void init() {
        this.screenDelay = 0;
        GameTile.init();
        this.gameHUD = new HUD();
        this.generateMap(0);
        this.gameHUD.init();
        this.setPreferredSize(new Dimension(this.mapWidth * 32, (this.mapHeight * 32) + Display.HEIGHT));
        System.gc();
        this.running = true;
        this.winning = false;
    }

    private void loadMapFile() {
        try {
            this.br = new BufferedReader[4];
            this.br[0] = new BufferedReader(FileInput.Files.LEVEL1.getFile());
            this.br[1] = new BufferedReader(FileInput.Files.LEVEL2.getFile());
            this.br[2] = new BufferedReader(FileInput.Files.LEVEL3.getFile());
            this.br[3] = new BufferedReader(FileInput.Files.LEVEL4.getFile());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        this.map = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.mapLayout = new ArrayList<>();
            try {
                String currentLine;
                while ((currentLine = br[i].readLine()) != null) {
                    if (currentLine.isEmpty()) {
                        continue;
                    }
                    mapLayout.add(new ArrayList<>(Arrays.asList(currentLine.split(" "))));
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
            map.add(mapLayout);
        }
    }

    /**
     * Khoi tao map.
     *
     * @param id
     */
    private void generateMap(int id) {
        // Kích thước map
        this.mapLayout = this.map.get(id);
        this.mapWidth = mapLayout.get(0).size();
        this.mapHeight = mapLayout.size();
        REAL_WIDTH = this.mapWidth * 32;
        REAL_HEIGHT = this.mapHeight * 32;
        if (id % 4 == 0) this.grass = FileInput.Images.GRASS.getImage();
        else if (id % 4 == 1) this.grass = FileInput.Images.WOODEN.getImage();
        else if (id == 2) this.grass = FileInput.Images.SNOW.getImage();
        else this.grass = FileInput.Images.GRASS.getImage();
        this.level = new BufferedImage(this.mapWidth * 32, this.mapHeight * 32, BufferedImage.TYPE_INT_RGB);
        // doc map.
        for (int y = 0; y < this.mapHeight; y++) {
            for (int x = 0; x < this.mapWidth; x++) {
                switch (mapLayout.get(y).get(x)) {
                    case ("B"):     // brick
                        BufferedImage brick = getSoftWall();
                        Wall softWall = new Wall(new Point2D.Float(x * 32, y * 32), brick, true);
                        GameTile.act(softWall);
                        break;

                    case ("W"):    // wall
                        BufferedImage wall = FileInput.Images.WALL.getImage();
                        Wall hardWall = new Wall(new Point2D.Float(x * 32, y * 32), wall, false);
                        GameTile.act(hardWall);
                        break;

                    case ("1"):     // Bomber
                        BufferedImage[][] sprMapP1 = FileInput.SpriteMap.PLAYER.getSprites();
                        Player player1 = new Player(new Point2D.Float(x * 32, y * 32 - 16), sprMapP1);
                        PlayerController playerController1 = new PlayerController(player1, this.control);
                        this.addKeyListener(playerController1);
                        this.gameHUD.setPlayer(player1);
                        GameTile.act(player1);
                        break;


                    case ("o"): // Oneal
                        BufferedImage[][] enemy1 = FileInput.SpriteMap.ONEAL.getSprites();
                        Oneal oneal = new Oneal(new Point2D.Float(x * 32, y * 32 - 16), enemy1);
                        GameTile.act(oneal);
                        break;

                    case ("m"): // Minvo
                        BufferedImage[][] minvo = FileInput.SpriteMap.MINVO.getSprites();
                        Minvo minvo1 = new Minvo(new Point2D.Float(x * 32, y * 32 - 16), minvo);
                        GameTile.act(minvo1);
                        break;
                    case ("k"): // kondoria
                        BufferedImage[][] enemy3 = FileInput.SpriteMap.KONDORIA.getSprites();
                        Kondoria kondoria = new Kondoria(new Point2D.Float(x * 32, y * 32 - 16), enemy3);
                        GameTile.act(kondoria);
                        break;
                    case ("b"): // balloom
                        BufferedImage[][] enemy4 = FileInput.SpriteMap.BALLOOM.getSprites();
                        Balloom balloom = new Balloom(new Point2D.Float(x * 32, y * 32 - 16), enemy4);
                        GameTile.act(balloom);
                        break;
                    case ("d"): // Doll
                        BufferedImage[][] enemy2 = FileInput.SpriteMap.DOLL.getSprites();
                        Doll doll = new Doll(new Point2D.Float(x * 32, y * 32 - 16), enemy2);
                        GameTile.act(doll);
                        break;

                    case ("T"):     // Tree; unbreakable
                        BufferedImage treeWall = FileInput.Images.TREE.getImage();
                        Wall tree = new Wall(new Point2D.Float(x * 32, y * 32), treeWall, false);
                        GameTile.act(tree);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    private void setControl() {
        this.control = new HashMap<>();
        this.control.put(KeyEvent.VK_UP, Keyboard.up);
        this.control.put(KeyEvent.VK_DOWN, Keyboard.down);
        this.control.put(KeyEvent.VK_LEFT, Keyboard.left);
        this.control.put(KeyEvent.VK_RIGHT, Keyboard.right);
        this.control.put(KeyEvent.VK_ENTER, Keyboard.bombAct);
    }

    private void setKey() {
        this.key = (int) (Math.random() * 3);
    }

    public void exit() {
        this.running = false;
    }

    public void resetGame() {
        this.init();
        Powerup.randomPortal = 0;
    }

    public void nextMap() {
        if (this.gameHUD.getLevel() < 4) {
            MusicPlayer.SFX(5);
            this.gameHUD.setLevel(this.gameHUD.getLevel() + 1);
            this.resetMap(this.gameHUD.getLevel() - 1);
            Powerup.randomPortal = 0;
        } else {
            System.out.println("WE HAD A WINNER!");
            this.exit();
            this.winning = true;
            System.gc();
        }
    }

    private void resetMap(int id) {
        if (id < 4) {
            int real_id = id % 4;
            GameTile.init();
            this.generateMap(real_id);
            Powerup.randomPortal = 0;
            System.gc();
        } else {
            System.out.println("WE HAD A WINNER!");
            this.exit();
            this.winning = true;
            System.gc();
        }
    }

    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double nanoPerSec = 1000000000.0 / 60.0;
        double delta = 0;
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nanoPerSec;
            lastTime = currentTime;
            if (delta >= 1) {
                try {
                    this.update();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                delta--;
            }
            this.repaint();
            if (System.currentTimeMillis() - timer > 1000) {
                timer = System.currentTimeMillis();
            }
        }
        while (!this.running && this.winning) {
            // nhac len level.
            try {
                Thread.sleep(5000);
                this.winning = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void addNotify() {
        super.addNotify();

        if (this.thread == null) {
            this.thread = new Thread(this, "GameThread");
            this.thread.start();
        }
    }

    /**
     * Ham cap nhat moi khi game start.
     *
     * @throws IndexOutOfBoundsException
     */
    private void update() throws IndexOutOfBoundsException {
        GameTile.sortBomberObjects();
        // Khi enimies died het thi xuat hien portal.
        if (GameTile.enemies.size() == 0 && GameTile.oneals.size() == 0
                && GameTile.dolls.size() == 0 && Powerup.randomPortal != 2 && Powerup.numberOfPortals == 0) {
            Powerup.randomPortal = 2;
        }
        for (int i = 0; i < GameTile.gameTile.size(); i++) {
            for (int j = 0; j < GameTile.gameTile.get(i).size(); ) {
                Entity entity = GameTile.gameTile.get(i).get(j);
                entity.update();
                if (entity.isDestroyed()) {
                    entity.destroy();
                    GameTile.gameTile.get(i).remove(entity);
                } else {
                    try {
                        for (int k = 0; k < GameTile.gameTile.size(); k++) {
                            for (int t = 0; t < GameTile.gameTile.get(k).size(); t++) {
                                Entity other = GameTile.gameTile.get(k).get(t);
                                if (entity == other) {
                                    continue;
                                }
                                if (entity.getCollide().intersects(other.getCollide())) {
                                    other.startCollision(entity);
                                }
                            }
                        }
                        j++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!this.gameHUD.matchSet) {
            this.gameHUD.update();
        } else {
            boolean checkSupreme = false;
            for (Player player : GameTile.players) {
                if (player.isNextLevel()) {
                    for (Enemy enemy : GameTile.enemies) enemy.setDead();
                    for (Oneal oneal : GameTile.oneals) oneal.setDead();
                    for (Doll doll : GameTile.dolls) doll.setDead();
                    for (Kondoria kondoria : GameTile.kondorias) kondoria.setDead();
                    for (Balloom balloom : GameTile.ballooms) balloom.setDead();
                    for (Minvo minvo : GameTile.minvos) minvo.setDead();
                    checkSupreme = true;
                    break;
                }
            }
            if (checkSupreme) {
                MusicPlayer.SFX(5);
                this.resetMap(this.gameHUD.getLevel() - 1);
                this.gameHUD.matchSet = false;
                System.out.println("NEXT LEVEL");
            } else if (!checkSupreme) {
                this.resetMap(0);
                this.gameHUD.reset();
            }
        }
        this.screenDelay++;

        try {
            Thread.sleep(500/144);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.graphics2D = this.level.createGraphics();
        this.graphics2D.clearRect(0, 0, this.level.getWidth(), this.level.getHeight());
        this.gameHUD.draw();
        for (int i = 0; i < this.level.getWidth(); i += this.grass.getWidth()) {
            for (int j = 0; j < this.level.getHeight(); j += this.grass.getHeight()) {
                this.graphics2D.drawImage(this.grass, i, j, null);
            }
        }
        try {
            for (int i = 0; i < GameTile.gameTile.size(); i++) {
                for (int j = 0; j < GameTile.gameTile.get(i).size(); j++) {
                    Entity entity = GameTile.gameTile.get(i).get(j);
                    entity.drawImage(this.graphics2D);
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }
        int infoBoxWidth = REAL_WIDTH / 2;
        g2.drawImage(this.gameHUD.getPlayerInfo(), infoBoxWidth * 0, 0, null);
        g2.drawImage(this.level, 0, Display.HEIGHT, null);
        g2.dispose();
        this.graphics2D.dispose();
    }

}

