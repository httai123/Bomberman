package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameTile {

    public static List<List<? extends Entity>> gameTile;
    public static ArrayList<Tile> tiles;
    public static ArrayList<Explosion> explosions;
    public static ArrayList<Player> players;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Oneal> oneals;
    public static ArrayList<Doll> dolls;
    public static void init() {
        gameTile = new ArrayList<>();
        tiles = new ArrayList<>();
        explosions = new ArrayList<>();
        players = new ArrayList<>();
        enemies = new ArrayList<>();
        oneals = new ArrayList<>();
        dolls = new ArrayList<>();

        gameTile.add(tiles);
        gameTile.add(explosions);
        gameTile.add(players);
        gameTile.add(enemies);
        gameTile.add(oneals);
        gameTile.add(dolls);
    }
    public static void act(Tile tile) {
        tiles.add(tile);
    }
    public static void act(Explosion explosion) {
        explosions.add(explosion);
    }
    public static void act(Player player) {
        players.add(player);
    }
    public static void act(Enemy enemy) {
        enemies.add(enemy);
    }
    public static void act(Oneal oneal) {
        oneals.add(oneal);
    }
    public static void act(Doll doll) {
        dolls.add(doll);
    }
    public static void sortBomberObjects() {
        players.sort(Comparator.comparing(Entity::getPositionY));
    }
}

