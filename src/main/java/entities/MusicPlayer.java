package entities;

public class MusicPlayer {
    static Sound s;

    static {
        s = new Sound();
    }

    public static void playBGM() {
        s.setFile(0);
        s.start();
        s.loop();
    }

    public static void stopBGM() {
        s.stop();
    }

    public static void SFX(int songNumber) {
        s.setFile(songNumber);
        s.start();
    }
}
