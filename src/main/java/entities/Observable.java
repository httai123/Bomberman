package entities;

/**
 * interface để định hướng khả năng có thể được thấy của một thực thể.
 */
public interface Observable {

    /**
     * render lại Entity mỗi game loop.
     */
    default void update() {

    }

    /**
     * Gọi khi mà một thực thể bị phá hủy.
     */
    default void destroy() {

    }

    int compareTo(Entity o);
}
