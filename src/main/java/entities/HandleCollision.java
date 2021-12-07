package entities;


public interface HandleCollision {
    public void startCollision(Entity entity);

    default void handleCollision(Bomb bomb) {

    }

    default void handleCollision(Wall wall) {

    }

    default void handleCollision(Explosion explosion) {

    }

    default void handleCollision(Enemy enemy) {

    }

    default void handleCollision(Oneal oneal) {

    }

    default void handleCollision(Doll doll) {

    }

    default void handleCollision(Player player) {

    }


    default void handleCollision(Powerup powerup) {

    }
}
