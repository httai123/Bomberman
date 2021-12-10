import entities.FileInput;
import entities.MusicPlayer;
public class BombermanGame {
    static Display window;

    public static void main(String[] args) {
        FileInput.readFiles();
        FileInput.init();

        GamePanel game = new GamePanel();
        game.init();
        MusicPlayer.playBGM();
        window = new Display(game);

        System.gc();
    }

}

